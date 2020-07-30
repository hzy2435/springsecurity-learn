package len.security;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Created by len on 2020-06-10 23:54
 */
@SpringBootApplication
@RestController
@EnableSwagger2
public class SecurityDemoApplication {

  public static void main(String[] args) {
    SpringApplication.run(SecurityDemoApplication.class, args);
  }

  @RequestMapping("/hello")
  public String hello() {
    return "Hello Spring Security";
  }

}
