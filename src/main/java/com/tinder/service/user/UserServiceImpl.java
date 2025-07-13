package com.tinder.service.user;

import com.tinder.dao.user.UserDao;
import com.tinder.exception.DaoException;
import com.tinder.model.User;

import java.util.List;

public class UserServiceImpl implements UserService {
    private final UserDao userProfileDao;

    public UserServiceImpl(UserDao userProfileDao) {
        this.userProfileDao = userProfileDao;
    }

    @Override
    public List<User> getAllProfiles() throws DaoException {
        return userProfileDao.findAll();
    }

    @Override
    public User getProfileById(int id) throws DaoException {
        return userProfileDao.findById(id);
    }
}
