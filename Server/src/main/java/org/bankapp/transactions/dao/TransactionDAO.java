package org.bankapp.transactions.dao;

import org.bankapp.transactions.model.Transaction;

import java.sql.SQLException;
import java.util.Set;

public interface TransactionDAO {
    Set<Transaction> getAllTransactions(int accountId) throws SQLException;
    void saveTransaction(Transaction transaction) throws SQLException;
}
