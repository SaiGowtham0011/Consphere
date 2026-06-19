package com.project.consphere.service;

import com.project.consphere.model.Like;
import com.project.consphere.model.Post;
import com.project.consphere.model.User;

import java.util.List;

public interface LikeService {
    Like addLike(Like like);

    Like deleteLike(Like like);

    List<User> getUsersWhoLikedPost(Post post);

    long likesCount(Post post);

}
