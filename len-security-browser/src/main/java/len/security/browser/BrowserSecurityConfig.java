package len.security.browser;

import len.security.core.authentication.AbstractChannelSecurityConfig;
import len.security.core.authentication.mobile.SmsCodeAuthenticationSecurityConfig;
import len.security.core.properties.SecurityConstants;
import len.security.core.properties.SecurityProperties;
import len.security.core.validate.code.ValidateCodeSecurityConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.annotation.Resource;
import javax.sql.DataSource;

/**
 * Created by len on 2020-07-23 09:50
 */
@Configuration
public class BrowserSecurityConfig extends AbstractChannelSecurityConfig {

  @Resource
  private SecurityProperties securityProperties;

  @Resource
  private DataSource dataSource;

  @Resource
  private UserDetailsService userDetailsService;

  @Resource
  private SmsCodeAuthenticationSecurityConfig smsCodeAuthenticationSecurityConfig;

  @Resource
  private ValidateCodeSecurityConfig validateCodeSecurityConfig;

  @Override
  protected void configure(HttpSecurity http) throws Exception {

    applyPasswordAuthenticationConfig(http);

    http.apply(smsCodeAuthenticationSecurityConfig)
          .and()
        .apply(validateCodeSecurityConfig)
          .and()
        .rememberMe()
          .tokenRepository(persistentTokenRepository())
          .tokenValiditySeconds(securityProperties.getBrowser().getRememberMeSeconds())
          .userDetailsService(userDetailsService)
          .and()
        .authorizeRequests()
          .antMatchers(
              SecurityConstants.DEFAULT_UNAUTHENTICATION_URL,
              SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_MOBILE,
              securityProperties.getBrowser().getLoginUrl(),
              SecurityConstants.DEFAULT_VALIDATE_CODE_URL_PREFIX + "/*"
          )
          .permitAll()
        .anyRequest()
        .authenticated()
        .and()
        .csrf()
        .disable();
  }

  /**
   * 密码加密器
   */
  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  /**
   * 记住我功能数据库配置
   */
  @Bean
  public PersistentTokenRepository persistentTokenRepository() {
    JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
    jdbcTokenRepository.setDataSource(dataSource);
//    jdbcTokenRepository.setCreateTableOnStartup(true);
    return jdbcTokenRepository;
  }

}
