package com.tinder.service;

import com.tinder.exception.DaoException;
import com.tinder.model.UserProfile;

import java.util.List;

public interface LikeService {
    void setLikeStatus(int userId, int targetUserId, Boolean liked);
    Boolean getLikeStatus(int userId, int targetUserId);
    List<UserProfile> getLikedProfiles(int userId) throws DaoException;
}
