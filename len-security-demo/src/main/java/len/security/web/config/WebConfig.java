package len.security.web.config;

import len.security.web.filter.TimeFilter;
import len.security.web.interceptor.TimeInterceptor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.AsyncSupportConfigurer;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import javax.annotation.Resource;

/**
 * Created by len on 2020-07-22 11:11
 */
//@Configuration
public class WebConfig extends WebMvcConfigurationSupport {

  @Resource
  private TimeInterceptor timeInterceptor;

//  @Override
//  protected void configureAsyncSupport(AsyncSupportConfigurer configurer) {
//    configurer.registerCallableInterceptors();
//    configurer.setDefaultTimeout(10000);
//    configurer.registerDeferredResultInterceptors();
//  }

  /**
   * 配置拦截器
   */
  @Override
  protected void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(timeInterceptor);
  }

  /**
   * 使用 Bean 的方式配置第三方过滤器，可以自定义拦截请求
   */
  @Bean
  public FilterRegistrationBean<TimeFilter> timeFilter() {
    FilterRegistrationBean<TimeFilter> registrationBean = new FilterRegistrationBean<>();
    TimeFilter timeFilter = new TimeFilter();
    registrationBean.setFilter(timeFilter);
    // 配置拦截的路由
    String[] url = new String[1];
    url[0] = "/*";
    registrationBean.addUrlPatterns(url);
    return registrationBean;
  }
}
