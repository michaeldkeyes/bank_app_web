package org.bankapp.users.impl;

import org.apache.log4j.Logger;
import org.bankapp.security.PasswordHasher;
import org.bankapp.users.User;
import org.bankapp.users.UserDAO;
import org.bankapp.users.UserService;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;

public class UserServiceImpl implements UserService {
    private final UserDAO dao = new UserDAOImpl();
    private final Logger logger = Logger.getLogger(UserServiceImpl.class);

    @Override
    public User getUser(int id) {
        return dao.findUserById(id);
    }

    @Override
    public List<User> getUsers() {
        return dao.getAllUsers();
    }

    @Override
    public void createUser(User user) {
        try {
            user.setPassword(PasswordHasher.generateStrongPasswordHash(user.getPassword()));
            dao.saveUser(user);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            logger.fatal(e);
        }
    }

    @Override
    public User updateUser(User user) {
        try {
            user.setPassword(PasswordHasher.generateStrongPasswordHash(user.getPassword()));
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            e.printStackTrace();
        }
        return dao.updateUser(user);
    }

    @Override
    public void deleteUser(int id) {
        dao.deleteUser(id);
    }
}