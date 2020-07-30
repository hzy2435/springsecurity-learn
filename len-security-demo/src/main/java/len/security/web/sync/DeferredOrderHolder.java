package len.security.web.sync;

import lombok.Data;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by len on 2020-07-22 16:51
 */
@Component
@Data
public class DeferredOrderHolder {

  public Map<String, DeferredResult<String>> map = new HashMap<>();

}
