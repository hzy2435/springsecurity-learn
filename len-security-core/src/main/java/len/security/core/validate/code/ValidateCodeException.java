package len.security.core.validate.code;

import org.springframework.security.core.AuthenticationException;

/**
 * Created by len on 2020-07-26 22:39
 */
public class ValidateCodeException extends AuthenticationException {

  public ValidateCodeException(String message) {
    super(message);
  }

}
