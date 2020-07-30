package len.security.browser;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by len on 2020-07-23 22:26
 */
@Service
@Slf4j
public class MyUserDetailsService implements UserDetailsService {

  @Resource
  private PasswordEncoder passwordEncoder;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    log.info("username: {}", username);
    String password = passwordEncoder.encode("123456");
    log.info("password: {}", password);
    return new User(username, password, true, true, true, true,
        AuthorityUtils.commaSeparatedStringToAuthorityList("admin"));
  }
}
