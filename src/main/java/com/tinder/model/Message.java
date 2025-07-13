package com.tinder.model;

import com.tinder.shared.Identifiable;
import com.tinder.utils.FunctionEX;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public record Message(
        Integer id,
        int senderId,
        String senderName,
        String senderImg,
        int receiverId,
        String receiverName,
        String receiverImg,
        String content,
        Timestamp time
) implements Identifiable {
    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("MMM dd, h:mm a");

    public String prettyTime() {
        return time.toLocalDateTime()
                .atZone(ZoneId.systemDefault())
                .format(FORMATTER);
    }
    @Override
    public int getId() {
        return id;
    }

    public static FunctionEX<ResultSet, Message> createFromDB() {
        return (ResultSet rs) -> new Message(rs.getInt("id"), rs.getInt("sender_id"), rs.getString("sender_name"), rs.getString("sender_img"), rs.getInt("receiver_id"), rs.getString("receiver_name"), rs.getString("receiver_img"), rs.getString("content"), rs.getTimestamp("time"));
    }
}
