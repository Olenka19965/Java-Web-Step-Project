package com.tinder.service;

import com.tinder.dao.model.UserProfileDao;
import com.tinder.exception.DaoException;
import com.tinder.model.UserProfile;

import java.util.ArrayList;
import java.util.List;

public class UserProfileServiceImpl implements  UserProfileService {
    private final UserProfileDao userProfileDao;

    public UserProfileServiceImpl(UserProfileDao userProfileDao) {
        this.userProfileDao = userProfileDao;
    }

    @Override
    public List<UserProfile> getAllProfiles() throws DaoException {
        return userProfileDao.findAll();
    }

    @Override
    public UserProfile getProfileById(int id) throws DaoException {
        return userProfileDao.findById(id);
    }
}
