package org.bankapp.transactions.dao;

import org.bankapp.transactions.model.Transaction;

import java.sql.SQLException;
import java.util.List;

public interface TransactionDAO {
    List<Transaction> getAllTransactions() throws SQLException;
    List<Transaction> getAllTransactions(int accountId) throws SQLException;
    void saveTransaction(Transaction transaction) throws SQLException;
}
