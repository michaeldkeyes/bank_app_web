package org.bankapp.users;

import java.util.List;

public interface UserService {
    User getUser(int id);
    List<User> getUsers();
    void createUser(User user);
    User updateUser(User user);
    void deleteUser(int id);
}
