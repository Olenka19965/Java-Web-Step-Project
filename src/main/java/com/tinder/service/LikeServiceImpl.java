package com.tinder.service;

import com.tinder.dao.model.UserLikeDao;
import com.tinder.exception.DaoException;
import com.tinder.model.UserProfile;

import java.util.List;

public class LikeServiceImpl implements  LikeService {
    private final UserLikeDao userLikeDao;
    public LikeServiceImpl(UserLikeDao userLikeDao) {
        this.userLikeDao = userLikeDao;
    }

    @Override
    public void setLikeStatus(int userId, int targetUserId, Boolean liked)throws  DaoException {
        userLikeDao.setLikeStatus(userId, targetUserId, liked);
    }

    @Override
    public Boolean getLikeStatus(int userId, int targetUserId) {
        return userLikeDao.getLikeStatus(userId, targetUserId);
    }
    @Override
    public List<UserProfile> getLikedProfiles(int userId) throws DaoException {
        return userLikeDao.getLikedProfiles(userId);
    }
}
