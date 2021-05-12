package org.bankapp.transactions.service;

import org.bankapp.accounts.model.Account;
import org.bankapp.transactions.model.Transaction;

import java.sql.SQLException;

public interface TransactionService {
    Account createTransaction(Transaction transaction) throws SQLException;
}
