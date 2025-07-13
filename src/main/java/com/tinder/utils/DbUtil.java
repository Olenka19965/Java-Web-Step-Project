package com.tinder.utils;

import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class DbUtil {
    private static final String URL = "jdbc:postgresql://localhost:5432/db";
    private static final String USER = "user";
    private static final String PASSWORD = "password";

    public static Connection getConnection() throws SQLException {
        return getDataSource().getConnection();
    }

    public static DataSource getDataSource() {
        PGSimpleDataSource ds = new PGSimpleDataSource();
        ds.setUrl(URL);
        ds.setUser(USER);
        ds.setPassword(PASSWORD);
        return ds;
    }

    public static <A> List<A> convertToList(ResultSet rs, FunctionEX<ResultSet, A> f) throws SQLException {
        LinkedList<A> list = new LinkedList<>();
        while (rs.next()) {
            A item = f.apply(rs);
            list.add(item);
        }
        return list;
    }

    public static <A> Optional<A> convertToOptional(ResultSet rs, FunctionEX<ResultSet, A> f) throws SQLException {
        if (rs.next()) {
            return Optional.of(f.apply(rs));
        }
        return Optional.empty();
    }
}