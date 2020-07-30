package len.security.browser;

import len.security.browser.support.SimpleResponse;
import len.security.core.properties.SecurityConstants;
import len.security.core.properties.SecurityProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by len on 2020-07-24 10:48
 */
@RestController
@Slf4j
public class BrowserSecurityController {

  private RequestCache requestCache = new HttpSessionRequestCache();
  private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

  @Resource
  private SecurityProperties securityProperties;

  @RequestMapping(SecurityConstants.DEFAULT_UNAUTHENTICATION_URL)
  @ResponseStatus(HttpStatus.UNAUTHORIZED)
  public SimpleResponse authentication(HttpServletRequest request, HttpServletResponse response) throws IOException {

    SavedRequest savedRequest = requestCache.getRequest(request, response);

    if (savedRequest != null) {
      String targetUrl = savedRequest.getRedirectUrl();
      log.info("The request url is: " + targetUrl);
      if (StringUtils.endsWithIgnoreCase(targetUrl, ".html")) {
        redirectStrategy.sendRedirect(request, response, securityProperties.getBrowser().getLoginUrl());
      }
    }

    return new SimpleResponse("需要进行权限校验，请先登录");
  }

}
