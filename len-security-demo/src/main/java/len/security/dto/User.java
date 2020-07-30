package len.security.dto;

import com.fasterxml.jackson.annotation.JsonView;
import len.security.validator.MyValidator;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import java.util.Date;

/**
 * Created by len on 2020-07-21 12:07
 */
@Data
public class User {
  public interface SimpleUserInfo {};
  public interface UserInfoDetail extends SimpleUserInfo {};

  @JsonView(SimpleUserInfo.class)
  private Long id;

  @JsonView(SimpleUserInfo.class)
  @MyValidator(message = "名字自定义校验器")
  private String username;

  @JsonView(UserInfoDetail.class)
  @NotBlank(message = "密码不能为空")
  private String password;

  @JsonView(SimpleUserInfo.class)
  @Past(message = "出生日期必须是过去的")
  private Date birthday;
}
