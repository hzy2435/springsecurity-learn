package len.security.core.validate.code.image;

import len.security.core.validate.code.ValidateCode;
import lombok.Getter;
import lombok.Setter;

import java.awt.image.BufferedImage;
import java.time.LocalDateTime;

/**
 * Created by len on 2020-07-26 21:41
 */
@Getter
@Setter
public class ImageCode extends ValidateCode {

  private BufferedImage image;

  public ImageCode(BufferedImage image, String code, LocalDateTime expireTime) {
    super(code, expireTime);
    this.image = image;
  }

  public ImageCode(BufferedImage image, String code, int seconds) {
    super(code, seconds);
    this.image = image;
  }

}
