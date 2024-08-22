package in.balaji;

import in.balaji.controller.SocialNetworkPostController;
import in.balaji.entity.PostCategory;
import in.balaji.entity.SocialNetworkPost;
import in.balaji.service.SocialNetworkPostService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;


import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class SocialNetworkPostControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private SocialNetworkPostService postService;

    @InjectMocks
    private SocialNetworkPostController postController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(postController).build();
    }

    @Test
    public void testGetTop10Posts() throws Exception {
        // Setup mock data
        SocialNetworkPost post = new SocialNetworkPost(
                LocalDate.now(), 
                PostCategory.MUSIC, 
                "Alice", 
                "Content", 
                100L
        );
        List<SocialNetworkPost> posts = Collections.nCopies(10, post);

        // Mock service
        given(postService.getTop10PostsByCategory(eq(PostCategory.MUSIC))).willReturn(posts);

        // Perform the test
        mockMvc.perform(get("/api/posts/top10")
                .param("postCategory", "MUSIC")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(10)));
    }

    @Test
    public void testSearchPostsByAuthor() throws Exception {
        // Setup mock data
        SocialNetworkPost post = new SocialNetworkPost(
                LocalDate.now(), 
                PostCategory.MUSIC, 
                "Alice", 
                "Content", 
                100L
        );
        List<SocialNetworkPost> posts = Collections.singletonList(post);

        // Mock service
        given(postService.searchPostsByAuthor(eq("Alice"))).willReturn(posts);

        // Perform the test
        mockMvc.perform(get("/api/posts/search")
                .param("author", "Alice")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
    }
}
