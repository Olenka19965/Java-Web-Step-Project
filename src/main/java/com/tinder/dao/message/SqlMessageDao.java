package com.tinder.dao.message;

import com.tinder.model.Message;
import com.tinder.exception.DaoException;
import com.tinder.utils.DbUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class SqlMessageDao implements MessageDao {
    private final Connection conn;

    public SqlMessageDao(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void create(Message m) throws DaoException {
        String sql = """
            INSERT INTO messages (
                sender_id, receiver_id, content, time
            ) VALUES (?, ?, ?, ?)
        """;

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, m.senderId());
            ps.setInt(2, m.receiverId());
            ps.setString(3, m.content());
            ps.setTimestamp(4, m.time());

            int rows = ps.executeUpdate();
            if (rows != 1) {
                throw new DaoException("Очікувалось вставлення 1 запису, але вставлено: " + rows);
            }
        } catch (SQLException e) {
            throw new DaoException("Помилка при створенні повідомлення", e);
        }

    }

    @Override
    public Optional<Message> read(int id) throws DaoException {
        String sql = """
        SELECT id, sender_id, receiver_id, content, time
        FROM messages
        WHERE id = ?
    """;

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Message msg = Message.createFromDB().apply(rs);
                    return Optional.of(msg);
                }
                return Optional.empty();
            }
        } catch (SQLException e) {
            throw new DaoException("Помилка при читанні повідомлення з id = " + id, e);
        }
    }

    @Override
    public void update(Message m) throws DaoException {
        String sql = """
        UPDATE messages
        SET sender_id = ?, receiver_id = ?, content = ?, time = ?
        WHERE id = ?
    """;

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, m.senderId());
            ps.setInt(2, m.receiverId());
            ps.setString(3, m.content());
            ps.setTimestamp(4, m.time());
            ps.setInt(5, m.id());

            int rows = ps.executeUpdate();
            if (rows != 1) {
                throw new DaoException("Не вдалося оновити повідомлення з id " + m.id());
            }
        } catch (SQLException e) {
            throw new DaoException("Помилка при оновленні повідомлення", e);
        }
    }

    @Override
    public void delete(int id) throws DaoException {
        String sql = "DELETE FROM messages WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            int rows = ps.executeUpdate();
            if (rows != 1) {
                throw new DaoException("Повідомлення з id " + id + " не було видалено");
            }
        } catch (SQLException e) {
            throw new DaoException("Помилка при видаленні повідомлення", e);
        }
    }

    @Override
    public List<Message> readSome(int sId, int rId, int offset, int limit) throws DaoException {
        String sql = """
            SELECT
                m.id,
                m.sender_id,
                s.name AS sender_name,
                s.photo_url AS sender_img,
                m.receiver_id,
                r.name AS receiver_name,
                r.photo_url AS receiver_img,
                m.content,
                m.time
            FROM messages m
            JOIN users s ON m.sender_id = s.id
            JOIN users r ON m.receiver_id = r.id
            WHERE (m.sender_id = ? AND m.receiver_id = ?)
               OR (m.sender_id = ? AND m.receiver_id = ?)
            ORDER BY m.time
            OFFSET ?
            LIMIT ?
        """;

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, sId);
            ps.setInt(2, rId);
            ps.setInt(3, rId);
            ps.setInt(4, sId);
            ps.setInt(5, offset);
            ps.setInt(6, limit);
            try (ResultSet rs = ps.executeQuery()) {
                return DbUtil.convertToList(rs, Message.createFromDB());
            }
        } catch (SQLException e) {
            throw new DaoException("Помилка при читанні повідомлень", e);
        }
    }
}
