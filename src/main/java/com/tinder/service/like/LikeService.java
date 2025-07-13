package com.tinder.service.like;

import com.tinder.exception.DaoException;
import com.tinder.model.User;

import java.util.List;

public interface LikeService {
    void setLikeStatus(int userId, int targetUserId, Boolean liked);
    Boolean getLikeStatus(int userId, int targetUserId);
    List<User> getLikedProfiles(int userId) throws DaoException;


}
