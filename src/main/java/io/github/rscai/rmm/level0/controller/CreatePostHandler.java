package io.github.rscai.rmm.level0.controller;

import com.fasterxml.jackson.databind.JsonNode;
import io.github.rscai.rmm.model.Post;
import io.github.rscai.rmm.repository.PostRepository;
import java.io.IOException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CreatePostHandler extends AbstractHandler {

  private static final Log LOG = LogFactory.getLog(CreatePostHandler.class);
  @Autowired
  private PostRepository postRepository;

  @Override
  protected String command() {
    return "createPost";
  }

  @Override
  public String handle(String input) {
    try {
      final JsonNode jsonTree = mapper.readTree(input);
      final String content = jsonTree.at("/parameters/content").asText("");

      Post newOne = new Post();
      newOne.setContent(content);
      Post savedOne = postRepository.save(newOne);

      return String.format(
          "{\"status\":\"SUCCESS\",\"message\":\"\",\"parameters\":{\"id\":%d,\"content\":\"%s\",\"createdAt\":\"%s\"}}",
          savedOne.getId(), savedOne.getContent(), savedOne.getCreatedAt().toString());
    } catch (IOException ex) {
      LOG.warn(ex.getMessage(), ex);
      return String.format("{\"status\":\"ERROR\",\"message\":\"%s\"}", ex.getMessage());
    }
  }
}
