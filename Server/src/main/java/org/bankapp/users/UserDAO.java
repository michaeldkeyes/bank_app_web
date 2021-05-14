package org.bankapp.users;

import org.bankapp.users.model.User;

import java.sql.SQLException;
import java.util.List;

public interface UserDAO {
    User findUser(int id) throws SQLException;
    User findUser(String username) throws SQLException;
    List<User> getAllUsers() throws SQLException;
    List<User> getEmployees() throws SQLException;
    void saveUser(User user) throws SQLException;
    User updateUser(User user) throws SQLException;
    void deleteUser(int id) throws SQLException;
}
