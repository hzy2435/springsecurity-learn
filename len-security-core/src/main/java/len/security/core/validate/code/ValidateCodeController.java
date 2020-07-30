package len.security.core.validate.code;

import len.security.core.properties.SecurityConstants;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by len on 2020-07-26 21:46
 */
@RestController
public class ValidateCodeController {

  @Resource
  private ValidateCodeProcessorHolder validateCodeProcessorHolder;

  @GetMapping(SecurityConstants.DEFAULT_VALIDATE_CODE_URL_PREFIX + "/{type}")
  public void createCode(HttpServletRequest request, HttpServletResponse response, @PathVariable("type") String type) throws Exception {
    validateCodeProcessorHolder.findValidateCodeProcessor(type).create(new ServletWebRequest(request, response));
  }

//  @GetMapping("/code/sms")
//  public void createSmsCode(HttpServletRequest request, HttpServletResponse response) throws ServletRequestBindingException {
//    ValidateCode smsCode = smsCodeGenerator.generate(new ServletWebRequest(request));
//    sessionStrategy.setAttribute(new ServletWebRequest(request), SESSION_KEY, smsCode);
//    String mobile = ServletRequestUtils.getRequiredStringParameter(request, "mobile");
//    smsCodeSender.send(mobile, smsCode.getCode());
//  }

}
