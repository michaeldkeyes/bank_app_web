package org.bankapp.transactions.service.impl;

import org.bankapp.accounts.model.Account;
import org.bankapp.accounts.service.AccountService;
import org.bankapp.accounts.service.impl.AccountServiceImpl;
import org.bankapp.transactions.dao.TransactionDAO;
import org.bankapp.transactions.dao.impl.TransactionDAOImpl;
import org.bankapp.transactions.model.Transaction;
import org.bankapp.transactions.service.TransactionService;

import java.sql.SQLException;

public class TransactionServiceImpl implements TransactionService {
    private final TransactionDAO dao = new TransactionDAOImpl();
    private final AccountService accountService = new AccountServiceImpl();

    @Override
    public Account createTransaction(Transaction transaction) throws SQLException {
        dao.saveTransaction(transaction);

        return accountService.updateBalance(transaction.getAccountId(), transaction.getAmount());
    }
}
