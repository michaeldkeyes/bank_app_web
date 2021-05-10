package org.bankapp.users;

import org.bankapp.users.model.User;

import java.util.List;

public interface UserService {
    User getUser(int id);
    User getUser(String username);
    List<User> getUsers();
    void createUser(User user);
    User updateUser(User user);
    void deleteUser(int id);
    boolean authorizeUser(String typedPassword, String storedPassword) throws Exception;
}
