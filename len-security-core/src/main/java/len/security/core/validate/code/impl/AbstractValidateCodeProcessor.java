package len.security.core.validate.code.impl;

import len.security.core.validate.code.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;

import javax.annotation.Resource;
import java.util.Map;

/**
 * Created by len on 2020-07-28 17:16
 */
@Slf4j
public abstract class AbstractValidateCodeProcessor<C extends ValidateCode> implements ValidateCodeProcessor {

  /**
   * 操作session的工具类
   */
  private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();

  /**
   * 收集系统中所有的 {@link ValidateCodeGenerator} 接口的实现。
   */
  @Resource
  private Map<String, ValidateCodeGenerator> validateCodeGenerators;

  @Override
  public void create(ServletWebRequest request) throws Exception {
    // 生成验证码
    C validateCode = generateCode(request);
    // 保存到 session
    save(request, validateCode);
    // 返回响应
    send(request, validateCode);
  }

  /**
   * 生成验证码
   */
  @SuppressWarnings("unchecked")
  protected C generateCode(ServletWebRequest request) {
    String type = getValidateCodeType().toString().toLowerCase();
    String generatorName = type + ValidateCodeGenerator.class.getSimpleName();
    ValidateCodeGenerator validateCodeGenerator = validateCodeGenerators.get(generatorName);
    if (validateCodeGenerator == null) {
      throw new ValidateCodeException("验证码生成器 " + generatorName + " 不存在");
    }
    return (C) validateCodeGenerator.generate(request);
  }

  /**
   * 保存验证码
   */
  private void save(ServletWebRequest request, C validateCode) {
    sessionStrategy.setAttribute(request, getSessionKey(), validateCode);
  }

  /**
   * 发送验证码，由子类实现
   */
  protected abstract void send(ServletWebRequest request, C validateCode) throws Exception;

  /**
   * 校验验证码逻辑
   */
  @SuppressWarnings("unchecked")
  @Override
  public void validate(ServletWebRequest request) {

    ValidateCodeType processorType = getValidateCodeType();
    String sessionKey = getSessionKey();

    C codeInSession = (C) sessionStrategy.getAttribute(request, sessionKey);

    String codeInRequest;
    try {
      codeInRequest = ServletRequestUtils.getStringParameter(request.getRequest(), processorType.getParamNameOnValidate());
    } catch (ServletRequestBindingException e) {
      throw new ValidateCodeException("获取验证码的值失败");
    }

    log.info("codeInSession: {}", codeInSession.getCode());
    log.info("codeInRequest: {}", codeInRequest);

    if (StringUtils.isBlank(codeInRequest)) {
      throw new ValidateCodeException("验证码的值不能为空");
    }

    if (codeInSession == null) {
      throw new ValidateCodeException("验证码不存在");
    }

    if (codeInSession.isExpired()) {
      sessionStrategy.removeAttribute(request, sessionKey);
      throw new ValidateCodeException("验证码已过期");
    }

    if (!StringUtils.equalsIgnoreCase(codeInRequest, codeInSession.getCode())) {
      throw new ValidateCodeException("验证码不匹配");
    }

    sessionStrategy.removeAttribute(request, sessionKey);
  }

  /**
   * 构建验证码放入session时的key
   */
  private String getSessionKey() {
    return ValidateCodeProcessor.SESSION_KEY_PREFIX + getValidateCodeType().toString().toUpperCase();
  }

  /**
   * 根据请求的url获取校验码的类型
   */
  private ValidateCodeType getValidateCodeType() {
    String type = StringUtils.substringBefore(getClass().getSimpleName(), "CodeProcessor");
    return ValidateCodeType.valueOf(type.toUpperCase());
  }

}
