package org.bankapp;

import io.javalin.Javalin;
import io.javalin.core.JavalinConfig;
import org.bankapp.users.User;

import static io.javalin.apibuilder.ApiBuilder.*;

public class BankappMain {
    public static void main(String[] args) {
        Javalin app = Javalin.create(JavalinConfig::enableCorsForAllOrigins).start(7000);

        app.routes(() -> {
            path("/customer", () -> {
                post(ctx -> {
                    System.out.println(ctx);
                    User user = ctx.bodyAsClass(User.class);

                    ctx.status(201);
                    ctx.json(user);
                });
                put(ctx -> ctx.result("Hello, Put!"));
            });
            path("/customer/:id", () -> {
                get(ctx -> {});
                delete(ctx -> ctx.result("Hello, Delete!"));
            });
            path("/customers", () -> get(ctx -> {}));
            path("/employee", () -> {
               get(ctx -> {});
               post(ctx -> {});
               put(ctx -> {});
               delete(ctx -> {});
            });
            path("/employees", () -> get(ctx -> {}));

        });

        System.out.println("Running 7000!");
    }
}
