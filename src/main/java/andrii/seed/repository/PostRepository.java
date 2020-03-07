package andrii.seed.repository;

import andrii.seed.domain.Post;
import andrii.seed.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByAuthorAndTagsName(User author, String tag);
    List<Post> findByTagsName(String tag);
    List<Post> findByAuthor(User author);
}
