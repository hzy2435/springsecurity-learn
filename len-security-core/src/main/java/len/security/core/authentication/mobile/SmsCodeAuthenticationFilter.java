package len.security.core.authentication.mobile;

import len.security.core.properties.SecurityConstants;
import org.springframework.lang.Nullable;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by len on 2020-07-29 22:49
 */
public class SmsCodeAuthenticationFilter extends AbstractAuthenticationProcessingFilter {
  // ~ Static fields/initializers
  // =====================================================================================

  private String mobileParameter = SecurityConstants.DEFAULT_PARAMETER_NAME_MOBILE;
  private boolean postOnly = true;

  // ~ Constructors
  // ===================================================================================================

  public SmsCodeAuthenticationFilter() {
    super(new AntPathRequestMatcher(SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_MOBILE, "POST"));
  }

  // ~ Methods
  // ========================================================================================================

  public Authentication attemptAuthentication(HttpServletRequest request,
                                              HttpServletResponse response) throws AuthenticationException {
    if (postOnly && !request.getMethod().equals("POST")) {
      throw new AuthenticationServiceException(
          "Authentication method not supported: " + request.getMethod());
    }

    String mobile = obtainMobile(request);

    if (mobile == null) {
      mobile = "";
    }

    mobile = mobile.trim();

    SmsCodeAuthenticationToken authRequest = new SmsCodeAuthenticationToken(mobile);

    // Allow subclasses to set the "details" property
    setDetails(request, authRequest);

    return this.getAuthenticationManager().authenticate(authRequest);
  }

  /**
   * Enables subclasses to override the composition of the username, such as by
   * including additional values and a separator.
   *
   * @param request so that request attributes can be retrieved
   *
   * @return the username that will be presented in the <code>Authentication</code>
   * request token to the <code>AuthenticationManager</code>
   */
  @Nullable
  protected String obtainMobile(HttpServletRequest request) {
    return request.getParameter(mobileParameter);
  }

  /**
   * Provided so that subclasses may configure what is put into the authentication
   * request's details property.
   *
   * @param request that an authentication request is being created for
   * @param authRequest the authentication request object that should have its details
   * set
   */
  protected void setDetails(HttpServletRequest request,
                            SmsCodeAuthenticationToken authRequest) {
    authRequest.setDetails(authenticationDetailsSource.buildDetails(request));
  }

  /**
   * Defines whether only HTTP POST requests will be allowed by this filter. If set to
   * true, and an authentication request is received which is not a POST request, an
   * exception will be raised immediately and authentication will not be attempted. The
   * <tt>unsuccessfulAuthentication()</tt> method will be called as if handling a failed
   * authentication.
   * <p>
   * Defaults to <tt>true</tt> but may be overridden by subclasses.
   */
  public void setPostOnly(boolean postOnly) {
    this.postOnly = postOnly;
  }

  public String getMobileParameter() {
    return mobileParameter;
  }

  public void setMobileParameter(String mobileParameter) {
    this.mobileParameter = mobileParameter;
  }
}
