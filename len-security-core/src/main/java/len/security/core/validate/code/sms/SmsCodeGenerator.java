package len.security.core.validate.code.sms;

import len.security.core.properties.SecurityProperties;
import len.security.core.validate.code.ValidateCode;
import len.security.core.validate.code.ValidateCodeGenerator;
import lombok.Setter;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

import javax.annotation.Resource;

/**
 * Created by len on 2020-07-28 17:00
 */
@Component("smsValidateCodeGenerator")
@Setter
public class SmsCodeGenerator implements ValidateCodeGenerator {

  @Resource
  private SecurityProperties securityProperties;

  @Override
  public ValidateCode generate(ServletWebRequest request) {
    String code = RandomStringUtils.randomNumeric(securityProperties.getCode().getSms().getLength());
    return new ValidateCode(code, securityProperties.getCode().getSms().getExpireIn());
  }
}
