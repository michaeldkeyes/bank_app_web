package org.bankapp;

import io.javalin.Javalin;
import io.javalin.core.security.Role;
import io.javalin.core.util.Header;
import org.bankapp.accounts.controller.AccountController;
import org.bankapp.security.AUTH;
import org.bankapp.users.model.User;
import org.bankapp.users.UserService;
import org.bankapp.users.controllers.CustomerController;
import org.bankapp.users.impl.UserServiceImpl;

import java.util.HashSet;
import java.util.Set;

import static io.javalin.apibuilder.ApiBuilder.*;
import static io.javalin.core.security.SecurityUtil.roles;

public class BankAppMain {
    public static void main(String[] args) {
        Javalin app = Javalin.create(javalinConfig -> {
            javalinConfig.enableCorsForAllOrigins();
            javalinConfig.accessManager(((handler, ctx, permittedRoles) -> {
                if (permittedRoles.contains(AUTH.ANYONE)) {
                    handler.handle(ctx);
                } else {
                    ctx.status(401).header(Header.WWW_AUTHENTICATE, "Basic");
                }
            }));
        }).start(7000);

        Set<Role> myRoles = new HashSet<>();
        myRoles.add(AUTH.ANYONE);
        myRoles.add(AUTH.LOGGED_IN);

        app.routes(() -> {
            UserService userService = new UserServiceImpl();
            path("/customer", () -> {
                post(CustomerController::post, roles(AUTH.ANYONE));
                put(ctx -> {
                    User user = ctx.bodyAsClass(User.class);
                    ctx.status(204).json(userService.updateUser(user));
                });
                path("/login", () -> {
                   post(CustomerController::login, roles(AUTH.ANYONE));
                });
                path("/id/:id", () -> {
                    get(CustomerController::getOne, roles(AUTH.ANYONE));
                    delete(ctx -> {
                        userService.deleteUser(Integer.parseInt(ctx.pathParam("id")));
                        ctx.status(204);
                    });
                });
            });
            path("/account", () -> {
               post(AccountController::postAccount, roles(AUTH.ANYONE));
            });
            path("/accounts", () -> {
                path("/:ownerId", () -> get(AccountController::getAll, roles(AUTH.ANYONE)));
            });
            path("/customers", () -> get(ctx -> ctx.json(userService.getUsers()), roles(AUTH.ANYONE)));


        });
    }
}
