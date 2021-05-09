package org.bankapp.users.controllers;

import io.javalin.http.Context;
import org.apache.log4j.Logger;
import org.bankapp.users.User;
import org.bankapp.users.UserService;
import org.bankapp.users.impl.UserServiceImpl;

public class CustomerController {
    private static final UserService userService = new UserServiceImpl();
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
            ctx.status(201).json("Successfully posted new Customer");
    }
}
