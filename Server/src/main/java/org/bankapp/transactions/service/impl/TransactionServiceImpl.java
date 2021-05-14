package org.bankapp.transactions.service.impl;

import org.bankapp.accounts.dao.impl.AccountDAOImpl;
import org.bankapp.accounts.model.Account;
import org.bankapp.accounts.service.AccountService;
import org.bankapp.accounts.service.impl.AccountServiceImpl;
import org.bankapp.transactions.dao.TransactionDAO;
import org.bankapp.transactions.model.Transaction;
import org.bankapp.transactions.service.TransactionService;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

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
    public Account createTransaction(Transaction transaction, int accountToTransferToId) throws SQLException {
        BigDecimal positiveAmount = transaction.getAmount().negate();
        Transaction otherAccountTransaction = new Transaction();
        otherAccountTransaction.setType("deposit");
        otherAccountTransaction.setAmount(positiveAmount);
        otherAccountTransaction.setAccountId(accountToTransferToId);

        dao.saveTransaction(transaction);
        Account account = accountService.updateBalance(transaction.getAccountId(), transaction.getAmount());
        dao.saveTransaction(otherAccountTransaction);
        accountService.updateBalance(otherAccountTransaction.getAccountId(), otherAccountTransaction.getAmount());

        return account;
    }

    @Override
    public List<Transaction> getAllTransactions() throws SQLException {
        return dao.getAllTransactions();
    }

    @Override
    public List<Transaction> getAllTransactions(int accountId) throws SQLException {
        return dao.getAllTransactions(accountId);
    }
}
