package len.security.web.sync;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * Created by len on 2020-07-22 16:57
 */
@Component
@Slf4j
public class QueueListener implements ApplicationListener<ContextRefreshedEvent> {

  @Resource
  private MockQueue mockQueue;

  @Resource
  private DeferredOrderHolder deferredOrderHolder;

  @Override
  public void onApplicationEvent(ContextRefreshedEvent event) {
    new Thread(() -> {
      while (true) {
        if (StringUtils.isNotBlank(mockQueue.getCompleteOrder())) {
//          Thread.sleep(1000);
          String orderNumber = mockQueue.getCompleteOrder();
          log.info("返回订单处理结果: " + orderNumber);
          deferredOrderHolder.getMap().get(orderNumber).setResult("place order success");
          mockQueue.setCompleteOrder(null);
        } else {
          try {
            Thread.sleep(100);
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
        }
      }
    }).start();
  }
}
