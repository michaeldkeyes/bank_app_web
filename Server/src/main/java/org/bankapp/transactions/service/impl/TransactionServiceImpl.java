package org.bankapp.transactions.service.impl;

import org.bankapp.accounts.dao.impl.AccountDAOImpl;
import org.bankapp.accounts.model.Account;
import org.bankapp.accounts.service.AccountService;
import org.bankapp.accounts.service.impl.AccountServiceImpl;
import org.bankapp.transactions.dao.TransactionDAO;
import org.bankapp.transactions.model.Transaction;
import org.bankapp.transactions.service.TransactionService;

import java.sql.SQLException;
import java.util.Set;

public class TransactionServiceImpl implements TransactionService {
    private final TransactionDAO dao;
    private final AccountService accountService = new AccountServiceImpl(new AccountDAOImpl());

    public TransactionServiceImpl(TransactionDAO dao) {
        this.dao = dao;
    }

    @Override
    public Account createTransaction(Transaction transaction) throws SQLException {
        dao.saveTransaction(transaction);

        return accountService.updateBalance(transaction.getAccountId(), transaction.getAmount());
    }

    @Override
    public Set<Transaction> getAllTransactions(int accountId) throws SQLException {
        return dao.getAllTransactions(accountId);
    }
}
