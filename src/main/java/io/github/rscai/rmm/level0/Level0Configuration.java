package io.github.rscai.rmm.level0;

import io.github.rscai.rmm.level0.controller.CreateCommentHandler;
import io.github.rscai.rmm.level0.controller.CreatePostHandler;
import io.github.rscai.rmm.level0.controller.Handler;
import java.util.LinkedList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Level0Configuration {

  @Autowired
  private CreatePostHandler createPostHandler;
  @Autowired
  private CreateCommentHandler createCommentHandler;

  @Bean
  @Qualifier("level0Handlers")
  public List<Handler> handlers() {
    List<Handler> list = new LinkedList<>();
    list.add(createPostHandler);
    list.add(createCommentHandler);

    return list;
  }
}
