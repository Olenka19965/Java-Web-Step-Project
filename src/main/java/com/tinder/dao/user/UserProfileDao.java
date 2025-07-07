package com.tinder.dao.user;

import com.tinder.exception.DaoException;
import com.tinder.model.UserProfile;
import java.util.List;

public interface UserProfileDao {
    List<UserProfile> findAll() throws DaoException;
    UserProfile findById(int id) throws DaoException;
    void create(UserProfile user) throws DaoException;
    void update(UserProfile user) throws DaoException;
    void delete(int id) throws DaoException;
}
