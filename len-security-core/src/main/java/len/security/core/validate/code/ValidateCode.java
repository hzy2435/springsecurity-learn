package len.security.core.validate.code;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * Created by len on 2020-07-28 16:33
 */
@Data
@AllArgsConstructor
public class ValidateCode {
  private String code;
  private LocalDateTime expireTime;

  public ValidateCode(String code, int seconds) {
    this(code, LocalDateTime.now().plusSeconds(seconds));
  }

  public boolean isExpired() {
    return LocalDateTime.now().isAfter(expireTime);
  }
}
