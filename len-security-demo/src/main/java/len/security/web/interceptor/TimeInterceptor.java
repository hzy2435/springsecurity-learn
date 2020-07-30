package len.security.web.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * Created by len on 2020-07-22 11:22
 */
@Component
public class TimeInterceptor implements HandlerInterceptor {
  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
    System.out.println("TimeInterceptor preHandle");
    System.out.println("TimeInterceptor class: " + ((HandlerMethod)handler).getBean().getClass().getName());
    System.out.println("TimeInterceptor method: " + ((HandlerMethod)handler).getMethod().getName());
    request.setAttribute("start", new Date().getTime());
    return true;
  }

  @Override
  public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    long time = new Date().getTime() - (long)request.getAttribute("start");
    System.out.println("TimeInterceptor postHandle: " + time);
  }

  @Override
  public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    long time = new Date().getTime() - (long)request.getAttribute("start");
    System.out.println("TimeInterceptor afterCompletion: " + time);
    System.out.println("error is " + ex);
  }
}
