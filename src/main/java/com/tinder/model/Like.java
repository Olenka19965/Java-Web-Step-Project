package com.tinder.model;

import com.tinder.shared.Identifiable;

public record Like(int id, int senderId, int receiverId, boolean isLiked) implements Identifiable {
    @Override
    public int getId() {
        return id;
    }
}