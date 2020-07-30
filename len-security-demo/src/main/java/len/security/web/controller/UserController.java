package len.security.web.controller;

import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import len.security.dto.User;
import len.security.dto.UserQueryCondition;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by len on 2020-07-21 12:06
 */
@RestController
@RequestMapping("/user")
public class UserController {

  @GetMapping("/me/context")
  public Object getLoginInfo() {
    return SecurityContextHolder.getContext().getAuthentication();
  }

  @GetMapping("/me/authentication")
  public Object getLoginInfo1(Authentication authentication) {
    return authentication;
  }

  @GetMapping("/me/userDetails")
  public Object getLoginInfo2(@AuthenticationPrincipal UserDetails userDetails) {
    return userDetails;
  }

  @GetMapping
  @JsonView(User.SimpleUserInfo.class)
  @ApiOperation(value = "获取用户列表")
  public List<User> query(UserQueryCondition userQueryCondition,
                          @PageableDefault(
                              page = 1,
                              size = 20,
                              sort = {"username", "age"},
                              direction = Sort.Direction.DESC) Pageable pageable) {
    System.out.println(ReflectionToStringBuilder.toString(userQueryCondition, ToStringStyle.MULTI_LINE_STYLE));

    System.out.println(pageable.getPageNumber());
    System.out.println(pageable.getPageSize());
    System.out.println(pageable.getSort());

    List<User> users = new ArrayList<>();
    users.add(new User());
    users.add(new User());
    users.add(new User());
    return users;
  }

  @GetMapping("/{id:\\d+}")
  @JsonView(User.UserInfoDetail.class)
  public User userInfo(@ApiParam("用户id") @PathVariable String id) {
    System.out.println("进入 userInfo");
    User user = new User();
    user.setUsername("tom");
    return user;
  }

  @PostMapping
  public User createUser(@Valid @RequestBody User user, BindingResult errors) {
    if (errors.hasErrors()) {
      errors.getAllErrors().forEach(error -> System.out.println(error.getDefaultMessage()));
    }
    System.out.println("backend: " + ReflectionToStringBuilder.toString(user, ToStringStyle.MULTI_LINE_STYLE));
    user.setId(1L);
    return user;
  }

  @PutMapping("/{id:\\d+}")
  public User updateUser(@Valid @RequestBody User user, BindingResult errors) {
    if (errors.hasErrors()) {
      errors.getAllErrors().forEach(error -> {
//        String message = ((FieldError) error).getField() + " " + error.getDefaultMessage();
//        System.out.println(message);
        System.out.println(error.getDefaultMessage());
      });
    }
    System.out.println("backend: " + ReflectionToStringBuilder.toString(user, ToStringStyle.MULTI_LINE_STYLE));
    user.setUsername("nick");
    return user;
  }

  @DeleteMapping("/{id:\\d}")
  public void deleteUser(@PathVariable("id") Long id) {
    System.out.println(id);
  }

  @GetMapping("/error")
  public User getError() {
    throw new RuntimeException("内部错误");
  }
}
