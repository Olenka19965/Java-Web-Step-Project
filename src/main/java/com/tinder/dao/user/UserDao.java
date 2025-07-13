package com.tinder.dao.user;

import com.tinder.exception.DaoException;
import com.tinder.model.User;
import java.util.List;

public interface UserDao {
    List<User> findAll() throws DaoException;
    User findById(int id) throws DaoException;
    void create(User user) throws DaoException;
    void update(User user) throws DaoException;
    void delete(int id) throws DaoException;
}
