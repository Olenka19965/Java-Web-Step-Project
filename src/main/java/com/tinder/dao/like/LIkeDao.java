package com.tinder.dao.like;

import com.tinder.exception.DaoException;

import java.util.List;

public interface LIkeDao {
    void setLikeStatus(int userId, int targetUserId, Boolean liked);
    Boolean getLikeStatus(int userId, int targetUserId);
    List<Integer> getLikedUserIds(int userId) throws DaoException;
}
