package com.tinder.service.auth;

import com.tinder.dao.auth.AuthDao;
import com.tinder.dao.auth.SqlAuthDao;
import com.tinder.model.Auth;
import com.tinder.model.User;

import java.util.Optional;

public class AuthService {
    AuthDao dao;

    public AuthService(AuthDao dao) {
        this.dao = dao;
    }

    public Optional<Auth> readByLoginPass(String login, String password) {
        try {
            return this.dao.readByLoginPass(login, password);
        } catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    public Optional<Auth> login(String login, String password) {
        return dao.readByLoginPass(login, password);
    }
}
