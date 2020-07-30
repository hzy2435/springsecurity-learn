package len.security.core.validate.code;

import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;

/**
 * Created by len on 2020-07-30 09:31
 */
@Component
public class ValidateCodeProcessorHolder {
  @Resource
  private Map<String, ValidateCodeProcessor> validateCodeProcessors;

  public ValidateCodeProcessor findValidateCodeProcessor(ValidateCodeType type) {
    return validateCodeProcessors.get(type.toString().toLowerCase() + ValidateCodeProcessor.class.getSimpleName());
  }

  public ValidateCodeProcessor findValidateCodeProcessor(String type) {
    String name = type.toLowerCase() + ValidateCodeProcessor.class.getSimpleName();
    ValidateCodeProcessor processor = validateCodeProcessors.get(name);
    if (processor == null) {
      throw new ValidateCodeException("验证码处理器 " + name + " 不存在");
    }
    return processor;
  }

}
