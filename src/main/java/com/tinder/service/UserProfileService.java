package com.tinder.service;

import com.tinder.exception.DaoException;
import com.tinder.model.UserProfile;
import java.util.List;

public interface UserProfileService {
    List<UserProfile> getAllProfiles() throws DaoException;
    UserProfile getProfileById(int id) throws DaoException;


}

