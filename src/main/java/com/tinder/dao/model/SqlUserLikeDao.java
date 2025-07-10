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
    public void setLikeStatus(int userId, int targetUserId, Boolean liked) {
        String sql = """
            INSERT INTO user_likes (user_id, target_user_id, liked)
            VALUES (?, ?, ?)
            ON CONFLICT (user_id, target_user_id)
            DO UPDATE SET liked = excluded.liked
        """;
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ps.setInt(2, targetUserId);
            ps.setBoolean(3, liked);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @Override
    public Boolean getLikeStatus(int userId, int targetUserId) {
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
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<UserProfile> getLikedProfiles(int userId) throws DaoException {
        List<UserProfile> likedProfiles = new ArrayList<>();
        String sql = """
            SELECT u.id, u.name, u.photo_url
            FROM users u
            JOIN user_likes ul ON u.id = ul.target_user_id
            WHERE ul.user_id = ? AND ul.liked = TRUE
        """;

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    likedProfiles.add(new UserProfile(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getString("photo_url")
                    ));
                }
            }
        } catch (SQLException e) {
            throw new DaoException("Помилка при отриманні лайкнутих профілів", e);
        }
        return likedProfiles;
    }
}
