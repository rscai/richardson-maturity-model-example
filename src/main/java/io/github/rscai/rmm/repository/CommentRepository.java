package io.github.rscai.rmm.repository;

import io.github.rscai.rmm.model.Comment;
import io.github.rscai.rmm.model.Post;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {

  List<Comment> findByPost(final Post post);
}
