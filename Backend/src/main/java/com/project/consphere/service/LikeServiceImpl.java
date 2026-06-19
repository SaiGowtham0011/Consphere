package com.project.consphere.service;

import com.project.consphere.model.Like;
import com.project.consphere.model.Post;
import com.project.consphere.model.User;
import com.project.consphere.repository.LikeRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class LikeServiceImpl implements LikeService {

    @Autowired
    private LikeRepository likeRepository;

    @Override
    public Like addLike(Like like) {
        return likeRepository.save(like);
    }

    @Override
    public Like deleteLike(Like like) {
        likeRepository.delete(like);
        return like;
    }

    @Override
    public List<User> getUsersWhoLikedPost(Post post){
        List<Like> likedUsers = likeRepository.findAllByPost(post);
        return likedUsers.stream()
                .map(Like::getUser)
                .toList();
    }

    @Override
    public long likesCount(Post post){
        long count =  likeRepository.findAllByPost(post).size();
        return count;
    }

}
