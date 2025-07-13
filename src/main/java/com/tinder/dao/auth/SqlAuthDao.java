package com.tinder.dao.auth;

import com.tinder.dao.ListDao;
import com.tinder.exception.DaoException;
import com.tinder.model.Auth;
import com.tinder.utils.DbUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class SqlAuthDao implements AuthDao {
    private final Connection connection;

    public SqlAuthDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void create(Auth auth) throws DaoException {
        String sql = "INSERT INTO auth(id, user_id, login, password) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            connection.setAutoCommit(false);
            ps.setInt(1, auth.getId());
            ps.setInt(2, auth.getUserId());
            ps.setString(3, auth.getLogin());
            ps.setString(4, auth.getPassword());

            int result = ps.executeUpdate();
            connection.commit();

            if (result != 1) {
                throw new DaoException("Очікувалось вставлення 1 запису, але вставлено: " + result);
            }
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException rollbackEx) {
                throw new DaoException("Помилка rollback після помилки вставки", rollbackEx);
            }
            throw new DaoException("Помилка при створенні облікових даних", e);
        }
    }

    @Override
    public Optional<Auth> read(int id) throws DaoException {
        String sql = "SELECT id, user_id, login, password FROM auth WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                return DbUtil.convertToOptional(rs, Auth.createFromDB());
            }
        } catch (SQLException e) {
            throw new DaoException("Помилка при читанні облікових даних", e);
        }
    }

    @Override
    public void update(Auth entity) throws DaoException {
        String sql = "UPDATE auth SET user_id = ?, login = ?, password = ? WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, entity.getUserId());
            ps.setString(2, entity.getLogin());
            ps.setString(3, entity.getPassword());
            ps.setInt(4, entity.getId());

            int rows = ps.executeUpdate();
            if (rows != 1) {
                throw new DaoException("Не вдалося оновити облікові дані з id " + entity.getId());
            }
        } catch (SQLException e) {
            throw new DaoException("Помилка при оновленні облікових даних", e);
        }
    }


    @Override
    public void delete(int id) throws DaoException {
        String sql = "DELETE FROM auth WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            int rows = ps.executeUpdate();
            if (rows != 1) {
                throw new DaoException("Не вдалося видалити облікові дані з id " + id);
            }
        } catch (SQLException e) {
            throw new DaoException("Помилка при видаленні облікових даних", e);
        }
    }

    @Override
    public Optional<Auth> readByLoginPass(String login, String password) throws DaoException {
        String sql = "SELECT id, user_id, login, password FROM auth WHERE login = ? AND password = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, login);
            ps.setString(2, password);
            try (ResultSet rs = ps.executeQuery()) {
                return DbUtil.convertToOptional(rs, Auth.createFromDB());
            }
        } catch (SQLException e) {
            throw new DaoException("Помилка при авторизації", e);
        }
    }
}
