package org.bankapp.accounts.dao;

import org.bankapp.accounts.model.Account;

import java.sql.SQLException;
import java.util.Set;

public interface AccountDAO {
    void saveAccount(Account account) throws SQLException;
    Set<Account> getAllAccounts(int ownerId) throws SQLException;
}
