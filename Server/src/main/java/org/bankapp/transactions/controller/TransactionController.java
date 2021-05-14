package org.bankapp.transactions.controller;

import io.javalin.http.Context;
import org.apache.log4j.Logger;
import org.bankapp.accounts.model.Account;
import org.bankapp.transactions.dao.impl.TransactionDAOImpl;
import org.bankapp.transactions.model.Transaction;
import org.bankapp.transactions.service.TransactionService;
import org.bankapp.transactions.service.impl.TransactionServiceImpl;

import java.sql.SQLException;

public class TransactionController {
    private static final TransactionService transactionService = new TransactionServiceImpl(new TransactionDAOImpl());
    private static final Logger logger = Logger.getLogger(TransactionController.class);

    private TransactionController() {}

    public static void postTransaction(Context ctx) {
        Transaction transaction = ctx.bodyAsClass(Transaction.class);
        try {
            Account account = transactionService.createTransaction(transaction);
            ctx.status(201).json(account);
            logger.info("New transaction created: " + transaction.toString());
        } catch (SQLException e) {
            ctx.status(500).json("Internal server error");
            logger.error(e);
        }
    }

    public static void getAll(Context ctx) {
        try {
            ctx.json(transactionService.getAllTransactions(Integer.parseInt(ctx.pathParam("accountId"))));
            logger.info("Retrieved transactions for account Id: " + ctx.pathParam("accountId"));
        } catch (SQLException e) {
            ctx.status(500).json("Internal server error");
            logger.error(e);
        }
    }
}
