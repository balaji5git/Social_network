package in.balaji.entity;

import java.time.LocalDate;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import jakarta.persistence.Cacheable;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;

@Entity
@Table(name = "SocialNetworkPost", indexes = {
    @Index(name = "idx_postCategory", columnList = "postCategory"),
    @Index(name = "idx_author", columnList = "author"),
    @Index(name = "idx_viewCount", columnList = "viewCount")
})
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class SocialNetworkPost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate postDate;

    @Enumerated(EnumType.STRING)
    private PostCategory postCategory;

    private String author;

    private String content;

    private Long viewCount;

    // No-args constructor required by JPA
    public SocialNetworkPost() {
    }

    // Constructor with parameters
    public SocialNetworkPost(LocalDate postDate, PostCategory postCategory, String author, String content, Long viewCount) {
        this.postDate = postDate;
        this.postCategory = postCategory;
        this.author = author;
        this.content = content;
        this.viewCount = viewCount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getPostDate() {
        return postDate;
    }

    public void setPostDate(LocalDate postDate) {
        this.postDate = postDate;
    }

    public PostCategory getPostCategory() {
        return postCategory;
    }

    public void setPostCategory(PostCategory postCategory) {
        this.postCategory = postCategory;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getViewCount() {
        return viewCount;
    }

    public void setViewCount(Long viewCount) {
        this.viewCount = viewCount;
    }
}
