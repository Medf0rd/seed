package andrii.seed.controller;

import andrii.seed.domain.Post;
import andrii.seed.domain.User;
import andrii.seed.repository.PostRepository;
import andrii.seed.service.PostService;
import andrii.seed.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static andrii.seed.controller.ControllerUtil.getErrors;

@Controller
public class MainController {
    @Autowired
    private PostService postService;

    @Autowired
    private UserService userService;

    @Autowired
    private PostRepository postRepository;

    @Value("${upload.path}")
    private String uploadPath;

    @GetMapping("/")
    public String greeting(
            @AuthenticationPrincipal User currentUser,
            @RequestParam(required = false) String filter,
            Model model
    ) {
        List<Post> posts = postService.getPosts(filter);

        model.addAttribute("posts", posts);
        model.addAttribute("filter", filter);
        model.addAttribute("isCurrentUser", false);
        model.addAttribute("currentUser", currentUser);

        return "main";
    }

    @GetMapping("/profile/{user}")
    public String userProfile(
            @AuthenticationPrincipal User currentUser,
            @PathVariable User user,
            @RequestParam(required = false) String filter,
            Model model
    ) {
        List<Post> posts = postService.getPosts(user, false, filter);

        model.addAttribute("postsCount", user.getPosts().size());
        model.addAttribute("subscribersCount", user.getSubscribers().size());
        model.addAttribute("subscriptionsCount", user.getSubscriptions().size());
        model.addAttribute("isSubscriber", user.getSubscribers().contains(currentUser));
        model.addAttribute("userId", user.getId());
        model.addAttribute("posts", posts);
        model.addAttribute("filter", filter);
        model.addAttribute("isCurrentUser", user.equals(currentUser));
        model.addAttribute("currentUser", currentUser);

        return "profile";
    }

    @GetMapping("/main")
    public String main(
            @AuthenticationPrincipal User currentUser,
            @RequestParam(required = false) String filter,
            Model model
    ) {
        List<Post> posts = postService.getPosts(currentUser, true, filter);

        model.addAttribute("posts", posts);
        model.addAttribute("filter", filter);
        model.addAttribute("isCurrentUser", true);
        model.addAttribute("currentUser", currentUser);

        return "main";
    }

    @PostMapping("/main")
    public String add(
            @AuthenticationPrincipal User currentUser,
            @RequestParam String tagString,
            @RequestParam("imageFile") MultipartFile imageFile,
            @Valid Post post,
            BindingResult bindingResult,
            Model model
    ) throws IOException {
        post.setAuthor(currentUser);
        if (imageFile != null && !imageFile.isEmpty() && !imageFile.getContentType().matches("\\.(jpeg|jpg|gif|png)$")) {
            model.addAttribute("fileError", "Bad file extension");
            model.addAttribute("post", post);
        } else if (bindingResult.hasErrors()) {
            Map<String, String> errorsMap = getErrors(bindingResult);

            model.mergeAttributes(errorsMap);
            model.addAttribute("post", post);
        } else {
            saveFile(imageFile, post);

            model.addAttribute("post", null);

            postService.save(post, tagString);
        }

        List<Post> posts = postRepository.findByAuthor(currentUser);

        model.addAttribute("posts", posts.stream().sorted(Comparator.comparingLong(Post::getId).reversed()).collect(Collectors.toList()));
        model.addAttribute("isCurrentUser", true);

        return "main";
    }

    private void saveFile(@RequestParam("imageFile") MultipartFile imageFile, @Valid Post post) throws IOException {
        if (imageFile != null && !imageFile.getOriginalFilename().isEmpty()) {
            File uploadDir = new File(uploadPath);

            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }

            String uuidImage = UUID.randomUUID().toString();
            String resultImageName = uuidImage + "." + imageFile.getOriginalFilename();

            imageFile.transferTo(new File(uploadPath + "/" + resultImageName));

            post.setImage(resultImageName);
        }
    }

    @GetMapping("/posts/{post}/like")
    public String like(
            @AuthenticationPrincipal User currentUser,
            @PathVariable Post post,
            @RequestHeader(required = false) String referer
    ) {
        Set<User> likes = post.getLikes();

        if (likes.contains(currentUser)) {
            likes.remove(currentUser);
        } else {
            likes.add(currentUser);
        }

        postRepository.save(post);

        UriComponents components = UriComponentsBuilder.fromHttpUrl(referer).build();

        return "redirect:" + components.getPath();
    }


}
