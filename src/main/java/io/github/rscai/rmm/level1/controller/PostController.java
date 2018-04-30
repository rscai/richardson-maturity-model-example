package io.github.rscai.rmm.level1.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.rscai.rmm.model.Post;
import io.github.rscai.rmm.repository.PostRepository;
import java.io.IOException;
import java.nio.charset.Charset;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/level1/post")
public class PostController {

  private static final Log LOG = LogFactory.getLog(PostController.class);

  private ObjectMapper objectMapper;

  @Autowired
  private PostRepository postRepository;

  public PostController() {
    this.objectMapper = new ObjectMapper();
    this.objectMapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, false);
    this.objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
  }

  @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE,
      MediaType.APPLICATION_JSON_UTF8_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
  public ResponseWrapper<Post> doOperation(HttpServletRequest request) {
    try {
      final String payload = IOUtils.toString(request.getInputStream(), Charset.forName("UTF-8"));

      final RequestWrapper<Post> requestWrapper = objectMapper
          .readValue(payload, new TypeReference<RequestWrapper<Post>>() {
          });
      if ("create".equals(requestWrapper.getCommand())) {
        return doCreate(requestWrapper);
      }
      
      return ResponseWrapper.error(String.format("Unkown command %s", requestWrapper.getCommand()));
    } catch (IOException ex) {
      LOG.error(ex.getMessage(), ex);
      return ResponseWrapper.error(ex.getMessage());
    }
  }

  private ResponseWrapper<Post> doCreate(RequestWrapper<Post> requestWrapper) {
    final Post savedOne = postRepository.save(requestWrapper.getData());

    return ResponseWrapper.success(savedOne);
  }
}
