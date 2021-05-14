package org.bankapp.users.impl;

import io.javalin.http.Context;
import org.bankapp.users.UserDAO;
import org.bankapp.users.UserService;
import org.bankapp.users.controllers.CustomerController;
import org.bankapp.users.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


class UserServiceImplTest {

    private final User user = new User(1, "test", "test", null);
    private final List<User> users = new ArrayList<>();
    UserDAO dao;
    UserService userService;

    @BeforeEach
    void setUp() {
        dao = mock(UserDAO.class);
        userService = new UserServiceImpl(dao);
    }

    @Test
    void getUser() throws SQLException {
        when(dao.findUser(1)).thenReturn(user);
        Assertions.assertEquals(userService.getUser(1).getId(), user.getId());
    }

    @Test
    void testGetUser() throws SQLException {
        when(dao.findUser("test")).thenReturn(user);
        Assertions.assertEquals(userService.getUser("test").getId(), user.getId());
    }

    @Test
    void getUsers() throws SQLException {
        users.add(user);
        when(dao.getAllUsers()).thenReturn(users);
        Assertions.assertEquals(userService.getUsers().size(), users.size());
    }

    @Test
    void updateUser() throws SQLException {
        when(dao.updateUser(user)).thenReturn(user);
        assertEquals(userService.updateUser(user).getId(), user.getId());
    }

}