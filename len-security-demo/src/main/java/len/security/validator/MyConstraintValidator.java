package len.security.validator;

import len.security.service.HelloService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Created by len on 2020-07-21 15:54
 */
public class MyConstraintValidator implements ConstraintValidator<MyValidator, Object> {

   @Autowired
   private HelloService helloService;

   public void initialize(MyValidator constraint) {
      System.out.println("MyConstraintValidator init");
   }

   public boolean isValid(Object obj, ConstraintValidatorContext context) {
      helloService.greet((String) obj);
      return false;
   }
}
