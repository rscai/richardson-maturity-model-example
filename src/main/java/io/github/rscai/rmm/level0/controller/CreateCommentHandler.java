package io.github.rscai.rmm.level0.controller;

import com.fasterxml.jackson.databind.JsonNode;
import io.github.rscai.rmm.model.Comment;
import io.github.rscai.rmm.model.Post;
import io.github.rscai.rmm.repository.CommentRepository;
import io.github.rscai.rmm.repository.PostRepository;
import java.io.IOException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CreateCommentHandler extends AbstractHandler {

  private static final Log LOG = LogFactory.getLog(CreateCommentHandler.class);
  @Autowired
  private PostRepository postRepository;
  @Autowired
  private CommentRepository commentRepository;

  @Override
  protected String command() {
    return "createComment";
  }

  @Override
  public String handle(String input) {
    try {
      final JsonNode jsonTree = mapper.readTree(input);
      long postId = jsonTree.at("/parameters/postId").asLong(0L);
      String content = jsonTree.at("/parameters/content").asText("");
      Post post = postRepository.getOne(postId);
      Comment newOne = new Comment();
      newOne.setContent(content);
      newOne.setPost(post);

      Comment savedOne = commentRepository.save(newOne);

      return String.format(
          "{\"status\":\"SUCCESS\",\"message\":\"\",\"parameters\":{\"id\":%d,\"content\":\"%s\",\"createdAt\":\"%s\",\"postId\":%d}}",
          savedOne.getId(), savedOne.getContent(), savedOne.getCreatedAt().toString(),
          savedOne.getPost().getId());
    } catch (IOException ex) {
      LOG.warn(ex.getMessage(), ex);
      return String.format("{\"status\":\"ERROR\",\"message\":\"%s\"}", ex.getMessage());

    }

  }
}
