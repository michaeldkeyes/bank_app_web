package org.bankapp.users;

import java.util.List;

public interface UserDAO {
    User findUser(int id);
    User findUser(String username);
    List<User> getAllUsers();
    void saveUser(User user);
    User updateUser(User user);
    void deleteUser(int id);
}
