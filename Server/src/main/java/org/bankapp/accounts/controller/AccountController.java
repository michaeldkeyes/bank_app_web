package org.bankapp.accounts.controller;

import io.javalin.http.Context;
import org.apache.log4j.Logger;
import org.bankapp.accounts.model.Account;
import org.bankapp.accounts.service.AccountService;
import org.bankapp.accounts.service.impl.AccountServiceImpl;

import java.sql.SQLException;

public class AccountController {
    private static final AccountService accountService = new AccountServiceImpl();
    private static final Logger logger = Logger.getLogger(AccountController.class);
    private static final String sqlError = "Internal server error";

    private AccountController() {}

    public static void postAccount(Context ctx) {
        Account account = ctx.bodyAsClass(Account.class);
        try {
            accountService.createAccount(account);
            ctx.status(201).json("Successfully posted new Account");
            logger.info("New account created: " + account.toString());
        } catch (SQLException e) {
            ctx.status(500).json(sqlError);
            logger.error(e);
        }
    }

    public static void getAll(Context ctx) {
        try {
            ctx.json(accountService.getAllAccounts(Integer.parseInt(ctx.pathParam("ownerId"))));
            logger.info("Retrieved accounts for User Id: " + ctx.pathParam("ownerId"));
        } catch (SQLException e) {
            ctx.status(500).json(sqlError);
            logger.error(e);
        }
    }

    public static void update(Context ctx) {
        Account account = ctx.bodyAsClass(Account.class);
        try {
            ctx.json(accountService.updateAccount(account));
            logger.info("Updated account" + account.toString());
        } catch (SQLException e) {
            ctx.status(500).json(sqlError);
            logger.error(e);
        }
    }
}
