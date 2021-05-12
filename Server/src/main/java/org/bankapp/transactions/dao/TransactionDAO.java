package org.bankapp.transactions.dao;

import org.bankapp.transactions.model.Transaction;

import java.sql.SQLException;

public interface TransactionDAO {
    void saveTransaction(Transaction transaction) throws SQLException;
}
