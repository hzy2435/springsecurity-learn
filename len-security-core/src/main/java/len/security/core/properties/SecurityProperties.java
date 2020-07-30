package len.security.core.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Created by len on 2020-07-24 10:59
 */
@Data
@ConfigurationProperties(prefix = "len.security")
public class SecurityProperties {
  private BrowserProperties browser = new BrowserProperties();
  private ValidateCodeProperties code = new ValidateCodeProperties();
}
