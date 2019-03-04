package repositories;

import models.Post;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends CrudRepository<Post, Long> {
    Post findByTitle(String title);
    // get a post at random
    @Query(value="SELECT * FROM posts ORDER BY RAND() LIMIT 1", nativeQuery = true)
    Post getRandom();
}
