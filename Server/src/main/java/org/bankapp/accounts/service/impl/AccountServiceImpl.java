package org.bankapp.accounts.service.impl;

import org.bankapp.accounts.dao.AccountDAO;
import org.bankapp.accounts.model.Account;
import org.bankapp.accounts.service.AccountService;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Set;

public class AccountServiceImpl implements AccountService {
    private final AccountDAO dao;

    public AccountServiceImpl(AccountDAO dao) {
        this.dao = dao;
    }

    @Override
    public void createAccount(Account account) throws SQLException {
        dao.saveAccount(account);
    }

    @Override
    public Set<Account> getAllAccounts(int ownerId) throws SQLException {
        return dao.getAllAccounts(ownerId);
    }

    public Set<Account> getAllAccounts() throws SQLException {
        return dao.getAllAccounts();
    }

    @Override
    public Account updateAccount(Account account) throws SQLException {
        return dao.updateAccount(account);
    }

    @Override
    public Account updateBalance(int id, BigDecimal amount) throws SQLException {
        Account account = dao.findAccount(id);
        BigDecimal newBalance = account.getBalance().add(amount);
        account.setBalance(newBalance);
        dao.updateBalance(id, newBalance);

        return account;
    }

    @Override
    public void updatePending(int id, boolean pending) throws SQLException {
        dao.updatePending(id, pending);
    }


}
