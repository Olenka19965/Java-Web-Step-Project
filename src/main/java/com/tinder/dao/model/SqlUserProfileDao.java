package com.tinder.dao.model;

import com.tinder.exception.DaoException;
import com.tinder.model.UserProfile;
import com.tinder.utils.DbUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class SqlUserProfileDao implements UserProfileDao {
    public SqlUserProfileDao() {}
    @Override
    public List<UserProfile> findAll() throws DaoException {
        List<UserProfile> list = new ArrayList<>();
        String sql = "SELECT id, name, photo_url FROM users";
        try (Connection conn = DbUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                list.add(new UserProfile(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("photo_url")
                ));
            }
        } catch (SQLException e) {
            throw new DaoException("Помилка отримання користувачів", e);
        }
        return list;
    }

    @Override
    public UserProfile findById(int id) throws DaoException {
        String sql = "SELECT id, name, photo_url FROM users WHERE id = ?";
        try (Connection conn = DbUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new UserProfile(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getString("photo_url")
                    );
                }
            }
        } catch (SQLException e) {
            throw new DaoException("Помилка пошуку користувача за ід", e);
        }
        return null;
    }
    @Override
    public void create(UserProfile user) throws DaoException {
        String sql = "INSERT INTO users (name, photo_url) VALUES (?, ?)";
        try (Connection conn = DbUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, user.getName());
            ps.setString(2, user.getPhoto());
            int rows = ps.executeUpdate();
            if (rows != 1) {
                throw new DaoException("Не вдалося вставити користувача");
            }
            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    user.setId(generatedKeys.getInt(1));
                }
            }
        } catch (SQLException e) {
            throw new DaoException("Помилка створення користувача", e);
        }
    }

    @Override
    public void update(UserProfile user) throws DaoException {
        String sql = "UPDATE users SET name = ?, photo_url = ? WHERE id = ?";
        try (Connection conn = DbUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, user.getName());
            ps.setString(2, user.getPhoto());
            ps.setInt(3, user.getId());
            int rows = ps.executeUpdate();
            if (rows != 1) {
                throw new DaoException("Не вдалося оновити користувача з ід " + user.getId());
            }
        } catch (SQLException e) {
            throw new DaoException("Помилка оновлення користувача", e);
        }
    }

    @Override
    public void delete(int id) throws DaoException {
        String sql = "DELETE FROM users WHERE id = ?";
        try (Connection conn = DbUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            int rows = ps.executeUpdate();
            if (rows != 1) {
                throw new DaoException("Не вдалося видалити користувача з ід " + id);
            }
        } catch (SQLException e) {
            throw new DaoException("Помилка видалення користувача", e);
        }
    }

    }

