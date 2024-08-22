package in.balaji.service;

import java.util.List;


import org.springframework.stereotype.Service;

import in.balaji.entity.PostCategory;
import in.balaji.entity.SocialNetworkPost;
import in.balaji.repo.SocialNetworkPostRepository;
import org.springframework.cache.annotation.Cacheable;

@Service
public class SocialNetworkPostService {

    private final SocialNetworkPostRepository postRepository;

  
    public SocialNetworkPostService(SocialNetworkPostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Cacheable(value = "topPostsCache", key = "#postCategory")
    public List<SocialNetworkPost> getTop10PostsByCategory(PostCategory postCategory) {
        return postRepository.findTop10ByPostCategoryOrderByViewCountDesc(postCategory);
    }

    @Cacheable(value = "authorPostsCache", key = "#author")
    public List<SocialNetworkPost> searchPostsByAuthor(String author) {
        return postRepository.findByAuthorContainingIgnoreCase(author);
    }
}
