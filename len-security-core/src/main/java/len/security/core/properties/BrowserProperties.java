package len.security.core.properties;

import lombok.Data;

/**
 * Created by len on 2020-07-24 11:01
 */
@Data
public class BrowserProperties {
  private String loginUrl = SecurityConstants.DEFAULT_LOGIN_PAGE_URL;
  private LoginResponseType loginType = LoginResponseType.JSON;
  private int rememberMeSeconds = 60*60*24;
}
