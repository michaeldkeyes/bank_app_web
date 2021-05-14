package org.bankapp.transactions.service;

import org.bankapp.accounts.model.Account;
import org.bankapp.transactions.model.Transaction;

import java.sql.SQLException;
import java.util.List;

public interface TransactionService {
    Account createTransaction(Transaction transaction) throws SQLException;
    Account createTransaction(Transaction transaction, int accountToTransferToId) throws SQLException;
    List<Transaction> getAllTransactions() throws SQLException;
    List<Transaction> getAllTransactions(int accountId) throws SQLException;
}
