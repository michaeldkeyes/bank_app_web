package org.bankapp;

import io.javalin.Javalin;
import io.javalin.core.JavalinConfig;
import org.bankapp.accounts.controller.AccountController;
import org.bankapp.transactions.controller.TransactionController;
import org.bankapp.users.model.User;
import org.bankapp.users.UserService;
import org.bankapp.users.controllers.CustomerController;
import org.bankapp.users.impl.UserServiceImpl;

import static io.javalin.apibuilder.ApiBuilder.*;
import static io.javalin.core.security.SecurityUtil.roles;

public class BankAppMain {
    public static void main(String[] args) {
        Javalin app = Javalin.create(JavalinConfig::enableCorsForAllOrigins).start(7000);

        app.routes(() -> {
            UserService userService = new UserServiceImpl();
            path("/customer", () -> {
                post(CustomerController::post);
                put(ctx -> {
                    User user = ctx.bodyAsClass(User.class);
                    ctx.status(204).json(userService.updateUser(user));
                });
                path("/login", () -> {
                   post(CustomerController::login);
                });
                path("/id/:id", () -> {
                    get(CustomerController::getOne);
                    delete(ctx -> {
                        userService.deleteUser(Integer.parseInt(ctx.pathParam("id")));
                        ctx.status(204);
                    });
                });
            });
            path("/customers", () -> get(ctx -> ctx.json(userService.getUsers())));
            path("/account", () -> {
               post(AccountController::postAccount);
               put(AccountController::update);

            });
            path("/accounts", () -> {
                path("/:ownerId", () -> get(AccountController::getAll));
            });
            path("/transaction", () -> {
                post(TransactionController::postTransaction);
            });

        });
    }
}
