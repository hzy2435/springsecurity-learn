package len.security.code;

import len.security.core.validate.code.image.ImageCode;
import len.security.core.validate.code.ValidateCodeGenerator;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * Created by len on 2020-07-27 10:37
 */
//@Component("imageCodeGenerator")
public class DemoImageCodeGenerator implements ValidateCodeGenerator {

  @Override
  public ImageCode generate(ServletWebRequest request) {
    System.out.println("这是 demo 自定义的图形验证码生成器");
    return null;
  }
}
