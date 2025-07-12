package com.tinder.service;

import com.tinder.dao.model.UserLikeDao;
import com.tinder.dao.model.UserProfileDao;
import com.tinder.exception.DaoException;
import com.tinder.model.UserProfile;
import java.util.ArrayList;
import java.util.List;

public class LikeServiceImpl implements  LikeService {
    private final UserLikeDao userLikeDao;
    private final UserProfileDao userProfileDao;

    public LikeServiceImpl(UserLikeDao userLikeDao, UserProfileDao userProfileDao) {
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
    public List<UserProfile> getLikedProfiles(int userId) throws DaoException {
        List<Integer> likedIds = userLikeDao.getLikedUserIds(userId);
        List<UserProfile> likedProfiles = new ArrayList<>();

        for (Integer id : likedIds) {
            UserProfile profile = userProfileDao.findById(id);
            if (profile != null) {
                likedProfiles.add(profile);
            }
        }
        return likedProfiles;
    }
}
