package org.bankapp.users.controllers;

import io.javalin.http.Context;
import org.apache.log4j.Logger;
import org.bankapp.accounts.dao.impl.AccountDAOImpl;
import org.bankapp.accounts.model.Account;
import org.bankapp.accounts.service.AccountService;
import org.bankapp.accounts.service.impl.AccountServiceImpl;
import org.bankapp.users.impl.UserDAOImpl;
import org.bankapp.users.model.User;
import org.bankapp.users.UserService;
import org.bankapp.users.impl.UserServiceImpl;

import java.sql.SQLException;
import java.util.List;

public class CustomerController {
    private static final UserService userService = new UserServiceImpl(new UserDAOImpl());
    private static final AccountService accountService = new AccountServiceImpl(new AccountDAOImpl());
    private static final Logger logger = Logger.getLogger(CustomerController.class);

    private CustomerController() {}

    public static void getOne(Context ctx) {
        try {
            ctx.json(userService.getUser(Integer.parseInt(ctx.pathParam("id"))));
        } catch (SQLException e) {
            ctx.status(500).json("Internal Server Error");
            logger.error(e);
        }
    }

    public static void login(Context ctx) {
        User user = ctx.bodyAsClass(User.class);
        try {
            User storedUser = userService.getUser(user.getUsername());
            if (storedUser.getPassword() != null && userService.authorizeUser(user.getPassword(), storedUser.getPassword())) {
                logger.info("Successful login: " + storedUser);
                List<Account> accounts =  accountService.getAllAccounts(storedUser.getId());
                storedUser.setAccounts(accounts);
                ctx.json(storedUser);
            } else {
                logger.info("Failed login");
                ctx.status(401).json("Wrong username/password");
            }
        } catch (SQLException e) {
            ctx.status(500).json("Internal Server Error");
            logger.error(e);
        } catch (Exception e) {
            logger.fatal(e);
        }

    }

    public static void post(Context ctx) {
            User user = ctx.bodyAsClass(User.class);
        try {
            userService.createUser(user);

            logger.info("Successfully created a new customer");
            ctx.status(201).json("Successfully posted new Customer");
        } catch (SQLException e) {
            ctx.status(500).json("Internal Server Error");
            logger.error(e);
        }
    }
}
