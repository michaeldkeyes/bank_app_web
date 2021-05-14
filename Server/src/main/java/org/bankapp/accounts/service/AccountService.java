package org.bankapp.accounts.service;

import org.bankapp.accounts.model.Account;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Set;

public interface AccountService {
    void createAccount(Account account) throws SQLException;
    Set<Account> getAllAccounts() throws SQLException;
    Set<Account> getAllAccounts(int ownerId) throws SQLException;
    Account updateAccount(Account account) throws SQLException;
    Account updateBalance(int id, BigDecimal amount) throws SQLException;
    void updatePending(int id, boolean pending, int approvedBy) throws SQLException;
}
