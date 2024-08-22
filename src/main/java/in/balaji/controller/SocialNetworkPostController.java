package in.balaji.controller;

import java.util.List;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import in.balaji.entity.PostCategory;
import in.balaji.entity.SocialNetworkPost;
import in.balaji.service.SocialNetworkPostService;

@RestController
@RequestMapping("/api/posts")
public class SocialNetworkPostController {

    private final SocialNetworkPostService postService;

  
    public SocialNetworkPostController(SocialNetworkPostService postService) {
        this.postService = postService;
    }

    @GetMapping("/top10")
    public ResponseEntity<List<SocialNetworkPost>> getTop10Posts(
            @RequestParam PostCategory postCategory) {
        return ResponseEntity.ok(postService.getTop10PostsByCategory(postCategory));
    }

    @GetMapping("/search")
    public ResponseEntity<List<SocialNetworkPost>> searchPostsByAuthor(
            @RequestParam String author) {
        return ResponseEntity.ok(postService.searchPostsByAuthor(author));
    }
}
