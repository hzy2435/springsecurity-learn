package len.security.core.properties;

import lombok.Data;

/**
 * Created by len on 2020-07-27 09:56
 */
@Data
public class SmsCodeProperties {
  private int length = 6; // 验证码位数
  private int expireIn = 60; // 验证码有效时间 60s
  private String urls;
}
