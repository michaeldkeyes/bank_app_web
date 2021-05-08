package org.bankapp.users.controllers;

import io.javalin.http.Context;
import org.bankapp.users.User;
import org.bankapp.users.UserService;
import org.bankapp.users.impl.UserServiceImpl;

public class CustomerController {
    private static final UserService userService = new UserServiceImpl();

    private CustomerController() {}

    public static void getOne(Context ctx) {
        ctx.json(userService.getUser(Integer.parseInt(ctx.pathParam("id"))));
    }

    public static void getAndAuthorizeUser(Context ctx) {
        User user = new User();
        user.setUsername(ctx.pathParam("username"));
        user.setPassword(ctx.pathParam("password"));
        User storedUser = userService.getUser(user.getUsername());
        try {
            if (userService.authorizeUser(user.getPassword(), storedUser.getPassword())) {
                ctx.json(storedUser);
            } else {
                ctx.result("Wrong username/password");
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void post(Context ctx) {
            User user = ctx.bodyAsClass(User.class);
            userService.createUser(user);
            ctx.status(201).result("Successfully posted new Customer");
    }
}
