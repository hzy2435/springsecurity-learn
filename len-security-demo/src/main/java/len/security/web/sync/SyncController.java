package len.security.web.sync;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import javax.annotation.Resource;
import java.util.concurrent.Callable;

/**
 * 异步处理请求 <br/>
 * Created by len on 2020-07-22 16:35
 */
@RestController
@RequestMapping("/order")
@Slf4j
public class SyncController {

  @Resource
  private MockQueue mockQueue;

  @Resource
  private DeferredOrderHolder deferredOrderHolder;

  @GetMapping("/callable")
  public Callable<String> callableOrder() {
    log.info("主线程接收订单");

    Callable<String> result = () -> {
      log.info("副线程开始处理订单");
      Thread.sleep(1000);
      log.info("副线程完成订单处理");
      return "success";
    };

    log.info("主线程完成订单");
    return result;
  }

  @GetMapping("/deferred")
  public DeferredResult<String> deferredOrder() {
    log.info("主线程接收订单");

    String orderNumber = RandomStringUtils.randomNumeric(8);
    mockQueue.setPlaceOrder(orderNumber);
    mockQueue.processOrder();

    DeferredResult<String> deferredResult = new DeferredResult<>();
    deferredOrderHolder.getMap().put(orderNumber, deferredResult);

    log.info("主线程完成订单");
    return deferredResult;
  }

}
