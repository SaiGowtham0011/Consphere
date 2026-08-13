package com.project.consphere.repository;

import com.project.consphere.model.Hashtag;
import com.project.consphere.model.Post;
import com.project.consphere.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findAllByUserIdOrderByCreatedAtDesc(Long userId);

    List<Post> findAllByUserUsernameOrderByCreatedAtDesc(String username);

    List<Post> findDistinctByHashtagsInOrderByCreatedAtDesc(Collection<Hashtag> hashtags);

    List<Post> findDistinctByUserInOrUserOrderByCreatedAtDesc(List<User> followedUsers, User currentUser);

    List<Post> findAllByOrderByCreatedAtDesc();
}
