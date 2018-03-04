package io.github.rscai.rmm.level1;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.rscai.rmm.level1.controller.RequestWrapper;
import io.github.rscai.rmm.model.Comment;
import io.github.rscai.rmm.model.Post;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = {
    "spring.datasource.url=jdbc:h2:mem:test;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE"})
@SqlGroup({
    @Sql(scripts = "/io/github/rscai/rmm/level1/CommentTest.before.sql", executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
})
public class CommentTest {

  @Autowired
  private WebApplicationContext context;

  private MockMvc mockMvc;
  private ObjectMapper objectMapper;
  private String url = "/api/level1/comment";

  @Before
  public void setUp() {
    mockMvc = MockMvcBuilders.webAppContextSetup(context).build();

    objectMapper = new ObjectMapper();
    objectMapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, false);
    objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
  }

  @Test
  public void testCreatePost() throws Exception {

    final Post post = new Post();
    post.setId(1L);

    final Comment comment = new Comment();
    comment.setContent("Test Comment 123");
    comment.setPost(post);
    final RequestWrapper<Comment> requestWrapper = new RequestWrapper<>();
    requestWrapper.setCommand("create");
    requestWrapper.setData(comment);

    mockMvc.perform(
        post(url).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(requestWrapper))).andExpect(status().isOk())
        .andExpect(jsonPath("$.status", is("SUCCESS")))
        .andExpect(jsonPath("$.data.id", notNullValue()))
        .andExpect(jsonPath("$.data.content", is("Test Comment 123")))
        .andExpect(jsonPath("$.data.createdAt", notNullValue()))
        .andExpect(jsonPath("$.data.post.id", is(1)));
  }
}
