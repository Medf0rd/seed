package andrii.seed.service;

import andrii.seed.domain.Post;
import andrii.seed.domain.Tag;
import andrii.seed.domain.User;
import andrii.seed.repository.PostRepository;
import andrii.seed.repository.TagRepository;
import andrii.seed.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class PostService {
    @Autowired
    private PostRepository postRepository;

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private UserRepository userRepository;

    public void save(Post post, String tags) {
        if (!tags.isEmpty()) {
            String[] tagArray = tags.split(" ");
            for (String el: tagArray) {
                Tag tagFromDb = tagRepository.findByName(el);
                if (tagFromDb == null) {
                    Tag tag = new Tag(el);
                    tag.getPosts().add(post);
                    post.getTags().add(tag);
                } else {
                    tagFromDb.getPosts().add(post);
                    post.getTags().add(tagFromDb);
                }
            }
        }

        postRepository.save(post);
    }

    public List<Post> getPosts(String filter) {
        List<Post> posts;

        if (filter != null && !filter.isEmpty()) {
            posts = postRepository.findByTagsName(filter);
        } else {
            posts = postRepository.findAll();
        }

        posts.sort(Comparator.comparing(Post::getId).reversed());

        return posts;
    }

    public List<Post> getPosts(User user, boolean subscriptions, String filter) {
        List<Post> posts = postRepository.findByAuthor(user);
        user = userRepository.findByUsername(user.getUsername());

        if (subscriptions) {
            user.getSubscriptions().forEach(s -> posts.addAll(s.getPosts()));
        }

        if (filter != null && !filter.isEmpty()) {
            posts.removeIf(post -> post.getTags().stream().noneMatch(tag -> tag.getName().equals(filter)));
        }

        posts.sort(Comparator.comparing(Post::getId).reversed());

        return posts;
    }
}
