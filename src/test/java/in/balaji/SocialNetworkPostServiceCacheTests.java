package in.balaji;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cache.CacheManager;

import in.balaji.entity.PostCategory;
import in.balaji.entity.SocialNetworkPost;
import in.balaji.repo.SocialNetworkPostRepository;
import in.balaji.service.SocialNetworkPostService;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
public class SocialNetworkPostServiceCacheTests {

    @Autowired
    private SocialNetworkPostService postService;

    @MockBean
    private SocialNetworkPostRepository postRepository;

    @Autowired
    private CacheManager cacheManager; // To check if cache is actually being used

    @Test
    void testCachingOfTop10PostsByCategory() {
        PostCategory category = PostCategory.MUSIC;
        List<SocialNetworkPost> posts = Arrays.asList(
            new SocialNetworkPost(LocalDate.of(2024, 1, 15), category, "Alice", "Check out my new song!", 1570L),
            new SocialNetworkPost(LocalDate.of(2024, 5, 5), category, "Eve", "My concert was amazing last night!", 2850L)
        );

        when(postRepository.findTop10ByPostCategoryOrderByViewCountDesc(category)).thenReturn(posts);

        // First call - should hit the database and cache the result
        List<SocialNetworkPost> result1 = postService.getTop10PostsByCategory(category);
        assertNotNull(result1);
        assertEquals(2, result1.size());

        // Second call - should use the cached result
        List<SocialNetworkPost> result2 = postService.getTop10PostsByCategory(category);
        assertNotNull(result2);
        assertEquals(2, result2.size());

        Mockito.verify(postRepository, Mockito.times(1)).findTop10ByPostCategoryOrderByViewCountDesc(category);

        // Check cache content
        assertNotNull(cacheManager.getCache("topPostsCache").get(category));
    }
}
