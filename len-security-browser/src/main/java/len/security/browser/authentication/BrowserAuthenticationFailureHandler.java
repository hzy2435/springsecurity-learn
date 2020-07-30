package len.security.browser.authentication;

import com.fasterxml.jackson.databind.ObjectMapper;
import len.security.browser.support.SimpleResponse;
import len.security.core.properties.LoginResponseType;
import len.security.core.properties.SecurityProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by len on 2020-07-25 20:59
 */
@Component
@Slf4j
public class BrowserAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

  @Resource
  private SecurityProperties securityProperties;

  @Resource
  private ObjectMapper objectMapper;

  @Override
  public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
    log.info("Login failure.");
    if (LoginResponseType.JSON.equals(securityProperties.getBrowser().getLoginType())) {
      response.setContentType("application/json;charset=UTF-8");
      response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
      log.error("The exception is: ", exception);
      response.getWriter().write(objectMapper.writeValueAsString(new SimpleResponse(exception.getMessage())));
    } else {
      super.onAuthenticationFailure(request, response, exception);
    }
  }
}
