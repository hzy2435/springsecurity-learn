package len.security.core.authentication;

import len.security.core.properties.SecurityConstants;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.annotation.Resource;

/**
 * Created by len on 2020-07-30 10:39
 */
public class AbstractChannelSecurityConfig extends WebSecurityConfigurerAdapter {

  @Resource
  private AuthenticationSuccessHandler customAuthenticationSuccessHandler;

  @Resource
  private AuthenticationFailureHandler customAuthenticationFailureHandler;

  protected void applyPasswordAuthenticationConfig(HttpSecurity http) throws Exception {
    http.formLogin()
        .loginPage(SecurityConstants.DEFAULT_UNAUTHENTICATION_URL)
        .loginProcessingUrl(SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_FORM)
        .successHandler(customAuthenticationSuccessHandler)
        .failureHandler(customAuthenticationFailureHandler);
  }
}
