package com.tinder.service.like;

import com.tinder.dao.like.LIkeDao;
import com.tinder.dao.user.UserDao;
import com.tinder.exception.DaoException;
import com.tinder.model.User;

import java.util.ArrayList;
import java.util.List;

public class LikeServiceImpl implements LikeService {
    private final LIkeDao userLikeDao;
    private final UserDao userProfileDao;

    public LikeServiceImpl(LIkeDao userLikeDao, UserDao userProfileDao) {
        this.userLikeDao = userLikeDao;
        this.userProfileDao = userProfileDao;
    }

    @Override
    public void setLikeStatus(int userId, int targetUserId, Boolean liked) throws DaoException {
        userLikeDao.setLikeStatus(userId, targetUserId, liked);
    }

    @Override
    public Boolean getLikeStatus(int userId, int targetUserId) throws DaoException {
        return userLikeDao.getLikeStatus(userId, targetUserId);
    }

    @Override
    public List<User> getLikedProfiles(int userId) throws DaoException {
        List<Integer> likedIds = userLikeDao.getLikedUserIds(userId);
        List<User> likedProfiles = new ArrayList<>();

        for (Integer id : likedIds) {
            User profile = userProfileDao.findById(id);
            if (profile != null) {
                likedProfiles.add(profile);
            }
        }
        return likedProfiles;
    }
}
