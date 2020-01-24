package com.gmail.zlotnikova.repository.impl;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.gmail.zlotnikova.repository.UserRepository;
import com.gmail.zlotnikova.repository.model.User;
import com.gmail.zlotnikova.repository.model.UserInformation;

public class UserRepositoryImpl extends GeneralRepositoryImpl<User> implements UserRepository {

    private static UserRepository instance;

    private UserRepositoryImpl() {
    }

    public static UserRepository getInstance() {
        if (instance == null) {
            instance = new UserRepositoryImpl();
        }
        return instance;
    }

    @Override
    public User add(Connection connection, User user) throws SQLException {
        try (
                PreparedStatement statement = connection.prepareStatement(
                        "INSERT INTO user(username, password, is_active, age) VALUES (?,?,?,?)",
                        Statement.RETURN_GENERATED_KEYS
                )
        ) {
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getPassword());
            statement.setBoolean(3, user.getActive());
            statement.setInt(4, user.getAge());
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Adding user failed, no rows affected.");
            }
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    user.setId(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Adding user failed, no ID obtained.");
                }
            }
            return user;
        }
    }

    @Override
    public List<User> findAll(Connection connection) throws SQLException {
        try (
                PreparedStatement statement = connection.prepareStatement(
                        "SELECT u.id, username, password, is_active, age, ui.address, ui.telephone FROM user AS u " +
                                "LEFT JOIN user_information AS ui on u.id = ui.user_id"
                )
        ) {
            List<User> users = new ArrayList<>();
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    User user = createUser(rs);
                    users.add(user);
                }
                return users;
            }
        }
    }

    @Override
    public int delete(Connection connection, Serializable id) throws SQLException {
        try (
                PreparedStatement statement = connection.prepareStatement(
                        "DELETE FROM user WHERE id = ?;")
        ) {
            statement.setInt(1, (Integer) id);
            return statement.executeUpdate();
        }
    }

    private User createUser(ResultSet rs) throws SQLException {
        User user = new User();
        int id = rs.getInt("id");
        user.setId(id);

        String username = rs.getString("username");
        user.setUsername(username);

        String password = rs.getString("password");
        user.setPassword(password);

        Boolean isActive = rs.getBoolean("is_active");
        user.setActive(isActive);

        Integer age = rs.getInt("age");
        user.setAge(age);

        UserInformation userInformation = new UserInformation();
        userInformation.setAddress(rs.getString("address"));
        userInformation.setTelephone(rs.getString("telephone"));
        user.setUserInformation(userInformation);

        return user;
    }

}