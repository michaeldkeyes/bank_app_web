package org.bankapp.accounts.service;

import org.bankapp.accounts.model.Account;

import java.sql.SQLException;
import java.util.Set;

public interface AccountService {
    void createAccount(Account account) throws SQLException;
    Set<Account> getAllAccounts(int ownerId) throws SQLException;
}
