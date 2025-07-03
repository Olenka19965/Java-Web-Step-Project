package com.tinder.domain;

import com.tinder.shared.Identifiable;

import java.sql.Timestamp;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public record Message(
        String id,
        String senderId,
        String senderName,
        String senderImg,
        String receiverId,
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
    public String getId() {
        return id;
    }
}
