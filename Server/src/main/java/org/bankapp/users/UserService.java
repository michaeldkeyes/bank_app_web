package org.bankapp.users;

import org.bankapp.users.model.User;

import java.sql.SQLException;
import java.util.List;

public interface UserService {
    User getUser(int id) throws SQLException;
    User getUser(String username) throws SQLException;
    List<User> getUsers() throws SQLException;
    void createUser(User user) throws SQLException;
    User updateUser(User user) throws SQLException;
    void deleteUser(int id) throws SQLException;
    boolean authorizeUser(String typedPassword, String storedPassword) throws Exception;
}
