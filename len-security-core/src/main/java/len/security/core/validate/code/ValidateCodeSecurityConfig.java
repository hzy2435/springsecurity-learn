package len.security.core.validate.code;

import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.Filter;

/**
 * Created by len on 2020-07-30 10:46
 */
@Component
public class ValidateCodeSecurityConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

  @Resource
  private Filter validateCodeFilter;

  @Override
  public void configure(HttpSecurity http) throws Exception {
    http.addFilterBefore(validateCodeFilter, AbstractPreAuthenticatedProcessingFilter.class);
  }
}
