package com.tinder.dao.message;

import com.tinder.domain.Message;
import com.tinder.exception.DaoException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class MessageSqlDao implements MessageDao {
    private final Connection conn;

    public MessageSqlDao(Connection conn) {
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
            ps.setString(1, m.senderId());
            ps.setString(2, m.receiverId());
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
    public Optional<Message> read(String id) throws DaoException {
        // NOT IMPLEMENT YET
        return Optional.empty();
    }

    @Override
    public void update(Message entity) throws DaoException {
        // NOT IMPLEMENT YET
    }

    @Override
    public void delete(String id) throws DaoException {
        // NOT IMPLEMENT YET
    }

    @Override
    public List<Message> readSome(String senderId, String receiverId, int offset, int limit) throws DaoException {
        // NOT IMPLEMENT YET
        return List.of();
    }
}
