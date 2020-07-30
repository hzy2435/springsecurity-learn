package len.security.service.impl;

import len.security.service.HelloService;
import org.springframework.stereotype.Service;

/**
 * Created by len on 2020-07-21 15:57
 */
@Service
public class HelloServiceImpl implements HelloService {
  @Override
  public String greet(String name) {
    System.out.println("HelloServiceImpl: " + name);
    return "hello" + name;
  }
}
