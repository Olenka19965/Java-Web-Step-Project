package com.tinder.dao;

import com.tinder.exception.DaoException;
import com.tinder.shared.Identifiable;

import java.util.Optional;

public interface ListDao<T extends Identifiable> {
    void create(T entity) throws DaoException;
    Optional<T> read(int id) throws DaoException;
    void update(T entity) throws DaoException;
    void delete(int id) throws DaoException;
}
