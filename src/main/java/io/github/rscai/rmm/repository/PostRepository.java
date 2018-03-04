package io.github.rscai.rmm.repository;

import io.github.rscai.rmm.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {

}
