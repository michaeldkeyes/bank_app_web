package org.bankapp.users.impl;

import org.bankapp.dbutils.PostgresConnection;
import org.bankapp.users.model.User;
import org.bankapp.users.UserDAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAOImpl implements UserDAO {

    @Override
    public User findUser(int id) throws SQLException {
        User user = new User();
        try (Connection connection = PostgresConnection.getConnection()) {
            String sql = "SELECT id, username, \"password\", date_created, type FROM bank_schema.users WHERE id = ?;\n";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1, id);

                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    user.setId(resultSet.getInt("id"));
                    user.setUsername(resultSet.getString("username"));
                    user.setPassword(resultSet.getString("password"));
                    user.setDateCreated(resultSet.getDate("date_created"));
                    user.setType(resultSet.getString("type"));
                }
            }
        } catch (SQLException e) {
            throw new SQLException(e);
        }

        return user;
    }

    @Override
    public User findUser(String username) throws SQLException {
        User user = new User();
        try (Connection connection = PostgresConnection.getConnection()) {
            String sql = "SELECT id, username, \"password\", date_created, type FROM bank_schema.users WHERE username = ?;\n";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, username);

                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    user.setId(resultSet.getInt("id"));
                    user.setUsername(resultSet.getString("username"));
                    user.setPassword(resultSet.getString("password"));
                    user.setDateCreated(resultSet.getDate("date_created"));
                    user.setType(resultSet.getString("type"));
                }
            }
        } catch (SQLException e) {
            throw new SQLException(e);
        }

        return user;
    }

    @Override
    public List<User> getAllUsers() throws SQLException {
        List<User> users = new ArrayList<>();
        try (Connection connection = PostgresConnection.getConnection()) {
            String sql = "SELECT id, username, \"password\", date_created, type FROM bank_schema.users;\n";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                ResultSet resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) {
                    User user = new User();
                    user.setId(resultSet.getInt("id"));
                    user.setUsername(resultSet.getString("username"));
                    user.setPassword(resultSet.getString("password"));
                    user.setDateCreated(resultSet.getDate("date_created"));
                    user.setType(resultSet.getString("type"));
                    users.add(user);
                }
            }
        } catch (SQLException e) {
            throw new SQLException(e);
        }
        return users;
    }

    public void saveUser(User user) throws SQLException {
        try (Connection connection = PostgresConnection.getConnection()) {
            String sql = "INSERT INTO bank_schema.users (username, \"password\", date_created, type) VALUES(?, ?, ?, ?);\n";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                preparedStatement.setString(1, user.getUsername());
                preparedStatement.setString(2, user.getPassword());
                preparedStatement.setDate(3, java.sql.Date.valueOf(java.time.LocalDate.now()));
                preparedStatement.setString(4, user.getType());

                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public User updateUser(User user) throws SQLException {
        try (Connection connection = PostgresConnection.getConnection()) {
            String sql = "UPDATE bank_schema.users SET username=?, \"password\"=? WHERE id=?;\n";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, user.getUsername());
                preparedStatement.setString(2, user.getPassword());
                preparedStatement.setInt(3, user.getId());

                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new SQLException(e);
        }

        return user;
    }

    @Override
    public void deleteUser(int id) throws SQLException {
        try (Connection connection = PostgresConnection.getConnection()) {
            String sql = "DELETE FROM bank_schema.users WHERE id=?;\n";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1, id);

                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }
}
