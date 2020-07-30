package len.security.web.filter;

import org.springframework.stereotype.Component;

import javax.servlet.*;
import java.io.IOException;
import java.util.Date;

/**
 * 请求时间过滤器<br/>
 * Created by len on 2020-07-22 11:05
 */
//@Component // 对于第三方过滤器，由于无法直接在类上添加该注解，所以可以使用第二种方式
public class TimeFilter implements Filter {
  @Override
  public void init(FilterConfig filterConfig) throws ServletException {
    System.out.println("TimeFilter init.");
  }

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
    System.out.println("TimeFilter start.");
    long start = new Date().getTime();
    chain.doFilter(request, response);
    System.out.println("TimeFilter 耗时: " + (new Date().getTime() - start));
    System.out.println("TimeFilter finish");
  }

  @Override
  public void destroy() {
    System.out.println("TimeFilter destroy.");
  }
}
