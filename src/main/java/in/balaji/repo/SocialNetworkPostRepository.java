package in.balaji.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import in.balaji.entity.PostCategory;
import in.balaji.entity.SocialNetworkPost;

@Repository
public interface SocialNetworkPostRepository extends JpaRepository<SocialNetworkPost, Long> {

    List<SocialNetworkPost> findTop10ByPostCategoryOrderByViewCountDesc(PostCategory postCategory);

    List<SocialNetworkPost> findByAuthorContainingIgnoreCase(String author);
}
