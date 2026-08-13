package com.project.consphere.service;

import com.project.consphere.dto.LikeResponse;

public interface LikeService {

    LikeResponse likePost(Long postId);

    LikeResponse unlikePost(Long postId);

    LikeResponse getLikeStatus(Long postId);
}
