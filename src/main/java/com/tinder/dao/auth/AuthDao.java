package com.tinder.dao.auth;

import com.tinder.dao.ListDao;
import com.tinder.model.Auth;

import java.util.Optional;

public interface AuthDao extends ListDao<Auth> {
    Optional<Auth> readByLoginPass(String login, String password);
}
