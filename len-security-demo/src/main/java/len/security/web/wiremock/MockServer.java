package len.security.web.wiremock;

import com.github.tomakehurst.wiremock.client.WireMock;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;

/**
 * Created by len on 2020-07-22 23:50
 */
public class MockServer {
  public static void main(String[] args) throws IOException {
    WireMock.configureFor(8060); // 指定服务端口
    WireMock.removeAllMappings(); /// 移除所有的配置
    mock("/user/1", "user.txt");
  }

  private static void mock(String url, String file) throws IOException {
    ClassPathResource pathResource = new ClassPathResource(StringUtils.join("/wiremock/response/", file));
    String response = FileUtils.readFileToString(pathResource.getFile(), "UTF-8");
    WireMock.stubFor(WireMock.get(WireMock.urlPathEqualTo(url))
        .willReturn(WireMock.aResponse().withBody(response).withStatus(200)));
  }
}
