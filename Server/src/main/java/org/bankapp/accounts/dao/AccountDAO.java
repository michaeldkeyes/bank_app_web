package org.bankapp.accounts.dao;

import org.bankapp.accounts.model.Account;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Set;

public interface AccountDAO {
    void saveAccount(Account account) throws SQLException;
    Account findAccount(int id) throws SQLException;
    Set<Account> getAllAccounts() throws SQLException;
    Set<Account> getAllAccounts(int ownerId) throws SQLException;
    Account updateAccount(Account account) throws SQLException;
    void updateBalance(int id, BigDecimal newBalance) throws SQLException;
    void updatePending(int id, boolean pending) throws SQLException;
}
