package com.tinder.service.user;

import com.tinder.exception.DaoException;
import com.tinder.model.User;
import java.util.List;

public interface UserService {
    List<User> getAllProfiles() throws DaoException;
    User getProfileById(int id) throws DaoException;


}

