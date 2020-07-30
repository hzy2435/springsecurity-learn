package len.security.web.controller;

import len.security.SecurityDemoApplicationTest;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * Created by len on 2020-07-20 23:42
 */
public class UserControllerTest extends SecurityDemoApplicationTest {
  @Autowired
  private WebApplicationContext ctx;

  private MockMvc mockMvc;

  @Before
  public void setup() {
    mockMvc = MockMvcBuilders.webAppContextSetup(ctx).build();
  }

  @Test
  public void whenQuerySuccess() throws Exception {
    String content = mockMvc.perform(MockMvcRequestBuilders.get("/user")
        .param("username", "tom")
        .param("age", "1")
//        .param("ageTo", "11")
        .param("xxx", "123234")
        .param("page", "5")
        .param("size", "34")
        .param("sort", "xxx,age,desc")
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(3))
        .andReturn().getResponse().getContentAsString();
    System.out.println("content: " + content);
  }

  @Test
  public void whenGetInfoSuccess() throws Exception {
    String content = mockMvc.perform(MockMvcRequestBuilders.get("/user/1").contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.username").value("tom"))
        .andReturn().getResponse().getContentAsString();
    System.out.println("content: " + content);
  }

  @Test
  public void whenGetInfoError() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.get("/user/sts").contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().is4xxClientError());
  }

  @Test
  public void whenCreateUserSuccess() throws Exception {
    Date birthday = new Date();
    String content = "{\"username\": \"tom\", \"password\": null, \"birthday\": " + birthday.getTime() + "}";
    String content1 = mockMvc.perform(MockMvcRequestBuilders.post("/user").contentType(MediaType.APPLICATION_JSON).content(content))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
        .andReturn().getResponse().getContentAsString();
    System.out.println(content1);
  }

  @Test
  public void whenUpdateUserSuccess() throws Exception {
    Date birthday = new Date(LocalDateTime.now().plusYears(1).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());
    String content = "{\"id\": 1, \"username\": \"tom\", \"password\": null, \"birthday\": " + birthday.getTime() + "}";
    String content1 = mockMvc.perform(MockMvcRequestBuilders.put("/user/1").contentType(MediaType.APPLICATION_JSON).content(content))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
        .andReturn().getResponse().getContentAsString();
    System.out.println(content1);
  }

  @Test
  public void testDeleteSuccess() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.delete("/user/1").contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isOk());
  }

  @Test
  public void whenUploadSuccess() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.multipart("/file")
        .file(new MockMultipartFile("file", "test.txt", "application/form-data", "Hello File".getBytes(StandardCharsets.UTF_8))))
        .andExpect(MockMvcResultMatchers.status().isOk());
  }
}
