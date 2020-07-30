package len.security.web.sync;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 模拟消息队列处理订单 <br/>
 * Created by len on 2020-07-22 16:45
 */
@Component
@Data
@Slf4j
public class MockQueue {
  private String placeOrder;
  private String completeOrder;

  public void processOrder() {
    new Thread(() -> {
      log.info("消息队列正在处理订单: " + placeOrder);
      try {
        Thread.sleep(1000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      completeOrder = placeOrder;
      placeOrder = null;
      log.info("消息队列完成处理订单: " + completeOrder);
    }).start();
  }
}
