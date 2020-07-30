package len.security.core.properties;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created by len on 2020-07-27 09:56
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ImageCodeProperties extends SmsCodeProperties {
  private int width = 67; // 验证码图片宽度
  private int height = 30; // 验证码图片长度

  public ImageCodeProperties() {
    setLength(4);
  }
}
