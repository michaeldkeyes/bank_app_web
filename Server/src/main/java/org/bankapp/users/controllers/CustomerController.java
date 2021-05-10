package org.bankapp.users.controllers;

import io.javalin.http.Context;
import org.apache.log4j.Logger;
import org.bankapp.accounts.model.Account;
import org.bankapp.accounts.service.AccountService;
import org.bankapp.accounts.service.impl.AccountServiceImpl;
import org.bankapp.users.model.User;
import org.bankapp.users.UserService;
import org.bankapp.users.impl.UserServiceImpl;

import java.util.Set;

public class CustomerController {
    private static final UserService userService = new UserServiceImpl();
    private static final AccountService accountService = new AccountServiceImpl();
    private static final Logger logger = Logger.getLogger(CustomerController.class);

    private CustomerController() {}

    public static void getOne(Context ctx) {
        ctx.json(userService.getUser(Integer.parseInt(ctx.pathParam("id"))));
    }

    public static void login(Context ctx) {
        User user = ctx.bodyAsClass(User.class);
        User storedUser = userService.getUser(user.getUsername());
        try {
            if (storedUser.getPassword() != null && userService.authorizeUser(user.getPassword(), storedUser.getPassword())) {
                logger.info("Successful login: " + storedUser);
                Set<Account> accounts =  accountService.getAllAccounts(storedUser.getId());
                storedUser.setAccounts(accounts);
                ctx.json(storedUser);
            } else {
                logger.info("Failed login");
                ctx.status(401).json("Wrong username/password");
            }
        } catch (Exception e) {
            logger.fatal(e);
        }

    }

    public static void post(Context ctx) {
            User user = ctx.bodyAsClass(User.class);
            userService.createUser(user);
            logger.info("Successfully created a new customer");
            ctx.status(201).json("Successfully posted new Customer");
    }
}
