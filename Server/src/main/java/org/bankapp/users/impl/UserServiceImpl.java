package org.bankapp.users.impl;

import org.apache.log4j.Logger;
import org.bankapp.security.PasswordHasher;
import org.bankapp.users.model.User;
import org.bankapp.users.UserDAO;
import org.bankapp.users.UserService;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.SQLException;
import java.util.List;

public class UserServiceImpl implements UserService {
    private final UserDAO dao;
    private final Logger logger = Logger.getLogger(UserServiceImpl.class);

    public UserServiceImpl(UserDAO dao) {
        this.dao = dao;
    }

    @Override
    public User getUser(int id) throws SQLException {
        return dao.findUser(id);
    }

    @Override
    public User getUser(String username) throws SQLException {
        return dao.findUser(username);
    }

    @Override
    public List<User> getUsers() throws SQLException {
        return dao.getAllUsers();
    }

    @Override
    public List<User> getEmployees() throws SQLException {
        return dao.getEmployees();
    }

    @Override
    public void createUser(User user) throws SQLException {
        try {
            user.setPassword(PasswordHasher.generateStrongPasswordHash(user.getPassword()));
            dao.saveUser(user);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            logger.fatal(e);
        }
    }

    @Override
    public User updateUser(User user) throws SQLException {
        try {
            user.setPassword(PasswordHasher.generateStrongPasswordHash(user.getPassword()));
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            e.printStackTrace();
        }
        return dao.updateUser(user);
    }

    @Override
    public void deleteUser(int id) throws SQLException {
        dao.deleteUser(id);
    }

    @Override
    public boolean authorizeUser(String typedPassword, String storedPassword) throws Exception {
        try {
            return PasswordHasher.validatePassword(typedPassword, storedPassword);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new NoSuchAlgorithmException(e);
        }
    }
}
