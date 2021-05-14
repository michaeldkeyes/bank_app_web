package org.bankapp.users.controllers;

import io.javalin.http.Context;
import org.apache.log4j.Logger;
import org.bankapp.users.UserService;
import org.bankapp.users.impl.UserDAOImpl;
import org.bankapp.users.impl.UserServiceImpl;

import java.sql.SQLException;

public class EmployeeController {
    private static final UserService userService = new UserServiceImpl(new UserDAOImpl());
    private static final Logger logger = Logger.getLogger(EmployeeController.class);

    private EmployeeController() {}

    public static void getAll(Context ctx) {
        try {
            ctx.json(userService.getEmployees());
            logger.info("All employees retrieved");
        } catch (SQLException e) {
            ctx.status(500).json("Internal Server Error");
            logger.error(e);
        }
    }
}
