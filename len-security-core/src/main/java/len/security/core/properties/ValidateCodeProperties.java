package len.security.core.properties;

import lombok.Data;

/**
 * Created by len on 2020-07-27 09:57
 */
@Data
public class ValidateCodeProperties {
  private ImageCodeProperties image = new ImageCodeProperties();
  private SmsCodeProperties sms = new SmsCodeProperties();
}
