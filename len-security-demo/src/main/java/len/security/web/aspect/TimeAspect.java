package len.security.web.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Created by len on 2020-07-22 14:31
 */
//@Aspect
//@Component
public class TimeAspect {

  @Around("execution(* len.security.web.controller.UserController.*(..))")
  public Object logTime(ProceedingJoinPoint joinPoint) throws Throwable {
    long start = new Date().getTime();
    Object result = joinPoint.proceed();
    Object[] args = joinPoint.getArgs();
    System.out.println("args: ");
    for (Object arg : args) {
      System.out.println(arg);
    }
    System.out.println("TimeAspect spend: " + (new Date().getTime() - start));
    return result;
  }

}
