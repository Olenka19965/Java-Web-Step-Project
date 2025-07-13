package com.tinder.model;

import com.tinder.shared.Identifiable;

public class User implements Identifiable {
    private int id;
    private String name;
    private String photo;

    public User(int id, String name, String photo) {
        this.id = id;
        this.photo = photo;
        this.name = name;
    }
    @Override
    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public String getPhoto() {
        return photo;
    }
    public void setPhoto(String photo) {
        this.photo = photo;
    }
    public void setId(int id) {
        this.id = id;
    }
    public void setName(String name) {
        this.name = name;
    }
}
