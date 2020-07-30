package len.security.core.validate.code;

import len.security.core.properties.SecurityConstants;
import len.security.core.properties.SecurityProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by len on 2020-07-26 22:33
 */
@Component
@Slf4j
public class ValidateCodeFilter extends OncePerRequestFilter implements InitializingBean {

  /**
   * 验证码校验失败处理器
   */
  @Resource
  private AuthenticationFailureHandler authenticationFailureHandler;

  /**
   * 系统配置信息
   */
  @Resource
  private SecurityProperties securityProperties;

  /**
   * 系统中的校验码处理器
   */
  @Resource
  private ValidateCodeProcessorHolder validateCodeProcessorHolder;

  /**
   * 存放所有需要校验验证码的url
   */
  private Map<String, ValidateCodeType> urlMap = new HashMap<>();

  /**
   * 验证请求url与配置的url是否匹配的工具类
   */
  private AntPathMatcher pathMatcher = new AntPathMatcher();

  /**
   * 初始化要拦截的url配置信息
   */
  @Override
  public void afterPropertiesSet() throws ServletException {
    super.afterPropertiesSet();

    urlMap.put(SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_FORM, ValidateCodeType.IMAGE);
    addUrlToMap(securityProperties.getCode().getImage().getUrls(), ValidateCodeType.IMAGE);

    urlMap.put(SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_MOBILE, ValidateCodeType.SMS);
    addUrlToMap(securityProperties.getCode().getSms().getUrls(), ValidateCodeType.SMS);
  }

  /**
   * 将系统中配置的需要校验验证码的URL根据校验的类型放入map
   */
  private void addUrlToMap(String urls, ValidateCodeType type) {
    if (StringUtils.isNotBlank(urls)) {
      String[] filterUrls = StringUtils.splitByWholeSeparatorPreserveAllTokens(urls, ",");
      for (String filterUrl : filterUrls) {
        urlMap.put(filterUrl, type);
      }
    }
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
    ValidateCodeType type = getValidateCodeType(request);
    if (type != null) {
      log.info("校验请求(" + request.getRequestURI() + ")中的验证码，验证码类型为 " + type);
      try {
        validateCodeProcessorHolder.findValidateCodeProcessor(type).validate(new ServletWebRequest(request, response));
        log.info("验证码校验通过");
      } catch (ValidateCodeException e) {
        authenticationFailureHandler.onAuthenticationFailure(request, response, e);
        return;
      }
    }
    filterChain.doFilter(request, response);
  }

  /**
   * 获取校验码的类型，如果当前请求不需要校验，则返回null
   */
  private ValidateCodeType getValidateCodeType(HttpServletRequest request) {
    if (!StringUtils.equalsIgnoreCase("get", request.getMethod())) {
      for (String url : urlMap.keySet()) {
        if (pathMatcher.match(url, request.getRequestURI())) {
          return urlMap.get(url);
        }
      }
    }
    return null;
  }

}
