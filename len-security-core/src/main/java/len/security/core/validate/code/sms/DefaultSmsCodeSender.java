package len.security.core.validate.code.sms;

import lombok.extern.slf4j.Slf4j;

/**
 * Created by len on 2020-07-28 16:55
 */
@Slf4j
public class DefaultSmsCodeSender implements SmsCodeSender {
  @Override
  public void send(String mobile, String code) {
    log.info("Sending code({}) to mobile: {}", code, mobile);
  }
}
