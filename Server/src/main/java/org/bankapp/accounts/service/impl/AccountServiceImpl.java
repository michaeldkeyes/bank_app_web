package org.bankapp.accounts.service.impl;

import org.bankapp.accounts.dao.AccountDAO;
import org.bankapp.accounts.dao.impl.AccountDAOImpl;
import org.bankapp.accounts.model.Account;
import org.bankapp.accounts.service.AccountService;

import java.sql.SQLException;
import java.util.Set;

public class AccountServiceImpl implements AccountService {
    private final AccountDAO dao = new AccountDAOImpl();

    @Override
    public void createAccount(Account account) throws SQLException {
        dao.saveAccount(account);
    }

    @Override
    public Set<Account> getAllAccounts(int ownerId) throws SQLException {
        return dao.getAllAccounts(ownerId);
    }
}
