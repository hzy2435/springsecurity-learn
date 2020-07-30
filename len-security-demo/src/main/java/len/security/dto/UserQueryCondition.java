package len.security.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by len on 2020-07-21 12:17
 */
@Data
@ApiModel("用户查询参数")
public class UserQueryCondition {
  @ApiModelProperty("用户名")
  private String username;
  @ApiModelProperty("查询起始年龄")
  private int age;
  @ApiModelProperty("查询结束年龄")
  private int ageTo;
  private String xxx;
}
