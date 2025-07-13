package com.tinder.model;

import com.tinder.shared.Identifiable;
import com.tinder.utils.FunctionEX;

import java.sql.ResultSet;

public class Auth implements Identifiable {
    private int id;
    private int userId;
    private String login;
    private String password;

    public Auth(int id, int userId, String login, String password) {
        this.id = id;
        this.userId = userId;
        this.login = login;
        this.password = password;
    }

    public int getUserId() {
        return userId;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public int getId() {
        return this.id;
    }

    public static FunctionEX<ResultSet, Auth> createFromDB() {
        return (ResultSet rs) -> new Auth(
                rs.getInt("id"),
                rs.getInt("user_id"),
                rs.getString("login"),
                rs.getString("password")
        );
    }
}
