package org.bankapp.users;

import java.util.List;

public interface UserDAO {
    User findUserById(int id);
    List<User> getAllUsers();
    void saveUser(User user);
    User updateUser(User user);
    void deleteUser(int id);
}
