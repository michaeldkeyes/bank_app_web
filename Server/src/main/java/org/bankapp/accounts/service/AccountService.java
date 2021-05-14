package org.bankapp.accounts.service;

import org.bankapp.accounts.model.Account;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import java.util.Set;

public interface AccountService {
    void createAccount(Account account) throws SQLException;
    List<Account> getAllAccounts() throws SQLException;
    List<Account> getAllAccounts(int ownerId) throws SQLException;
    Account updateAccount(Account account) throws SQLException;
    Account updateBalance(int id, BigDecimal amount) throws SQLException;
    void updatePending(int id, boolean pending, int approvedBy) throws SQLException;
}
