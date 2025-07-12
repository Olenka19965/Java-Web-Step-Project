package com.tinder.dao.model;

import com.tinder.exception.DaoException;
import com.tinder.model.UserProfile;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SqlUserLikeDao implements UserLikeDao {
    private final Connection conn;

    public SqlUserLikeDao(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void setLikeStatus(int userId, int targetUserId, Boolean liked) throws DaoException {
        String sql = """
            INSERT INTO user_likes (user_id, target_user_id, liked)
            VALUES (?, ?, ?)
            ON CONFLICT (user_id, target_user_id)
            DO UPDATE SET liked = excluded.liked
        """;
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ps.setInt(2, targetUserId);
            if (liked == null) {
                // Якщо потрібно видалити лайк (null), можна видалити запис
                removeLike(userId, targetUserId);
                return;
            } else {
                ps.setBoolean(3, liked);
            }
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Помилка при встановленні статусу лайку", e);
        }
    }

    private void removeLike(int userId, int targetUserId) throws DaoException {
        String sql = "DELETE FROM user_likes WHERE user_id = ? AND target_user_id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ps.setInt(2, targetUserId);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Помилка при видаленні лайку", e);
        }
    }

    @Override
    public Boolean getLikeStatus(int userId, int targetUserId) throws DaoException {
        String sql = "SELECT liked FROM user_likes WHERE user_id = ? AND target_user_id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ps.setInt(2, targetUserId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getBoolean("liked");
                }
            }
        } catch (SQLException e) {
            throw new DaoException("Помилка при отриманні статусу лайку", e);
        }
        return null;
    }

    @Override
    public List<Integer> getLikedUserIds(int userId) throws DaoException {
        List<Integer> likedUserIds = new ArrayList<>();
        String sql = "SELECT target_user_id FROM user_likes WHERE user_id = ? AND liked = TRUE";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    likedUserIds.add(rs.getInt("target_user_id"));
                }
            }
        } catch (SQLException e) {
            throw new DaoException("Помилка при отриманні лайкнутих користувачів", e);
        }
        return likedUserIds;
    }
}
