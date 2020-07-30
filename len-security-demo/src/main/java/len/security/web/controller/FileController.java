package len.security.web.controller;

import len.security.dto.FileInfo;
import org.apache.commons.io.IOUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Date;

/**
 * Created by len on 2020-07-22 15:24
 */
@RestController
@RequestMapping("/file")
public class FileController {

  private final String SAVE_FOLDER = "D:\\Projects\\upload";

  @PostMapping
  public FileInfo upload(MultipartFile file) throws IOException {
    System.out.println("file name: " + file.getName());
    System.out.println("file origin name: " + file.getOriginalFilename());
    System.out.println("file size: " + file.getSize());

    File newFile = new File(SAVE_FOLDER, new Date().getTime() + ".txt");
    file.transferTo(newFile);
    return new FileInfo(newFile.getAbsolutePath());
  }

  @GetMapping("/{id}")
  public void download(@PathVariable("id") String id, HttpServletResponse response) throws IOException {
    File file = new File(SAVE_FOLDER, id + ".txt");
    try(InputStream inputStream = new FileInputStream(file);
        OutputStream outputStream = response.getOutputStream()) {
      response.setContentType("application/x-download");
      response.addHeader("Content-Disposition", "attachment;filename=test.txt");
      IOUtils.copy(inputStream, outputStream);
      outputStream.flush();
    }
  }
}
