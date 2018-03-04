package io.github.rscai.rmm.level0;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
    @Sql(scripts = "/io/github/rscai/rmm/level0/CommentTest.before.sql", executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
})
public class CommentTest {

  @Autowired
  private WebApplicationContext context;

  private MockMvc mockMvc;
  private String url = "/api/level0/operation";

  @Before
  public void setUp() throws Exception {
    mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
  }

  @Test
  public void testCreateComment() throws Exception {
    final String input = "{\"command\":\"createComment\",\"parameters\":{\"postId\":1,\"content\":\"comment 1\"}}";
    mockMvc.perform(
        post(url).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
            .content(input)).andExpect(status().isOk())
        .andExpect(jsonPath("$.status", is("SUCCESS")))
        .andExpect(jsonPath("$.parameters.postId", is(1)))
        .andExpect(jsonPath("$.parameters.id", notNullValue()))
        .andExpect(jsonPath("$.parameters.content", is("comment 1")))
        .andExpect(jsonPath("$.parameters.createdAt", notNullValue()));
  }
}
