package len.security.core.validate.code;

import len.security.core.properties.SecurityProperties;
import len.security.core.validate.code.image.ImageCodeGenerator;
import len.security.core.validate.code.sms.DefaultSmsCodeSender;
import len.security.core.validate.code.sms.SmsCodeSender;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

/**
 * Created by len on 2020-07-27 10:32
 */
@Configuration
public class ValidateCodeBeanConfig {

  @Resource
  private SecurityProperties securityProperties;

  @Bean
  @ConditionalOnMissingBean(name = "imageValidateCodeGenerator")
  public ValidateCodeGenerator imageValidateCodeGenerator() {
    ImageCodeGenerator imageCodeGenerator = new ImageCodeGenerator();
    imageCodeGenerator.setSecurityProperties(securityProperties);
    return imageCodeGenerator;
  }

  @Bean
  @ConditionalOnMissingBean(SmsCodeSender.class)
  public SmsCodeSender smsCodeSender() {
    return new DefaultSmsCodeSender();
  }

}
