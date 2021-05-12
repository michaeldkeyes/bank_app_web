package org.bankapp.accounts.service.impl;

import org.bankapp.accounts.dao.AccountDAO;
import org.bankapp.accounts.dao.impl.AccountDAOImpl;
import org.bankapp.accounts.model.Account;
import org.bankapp.accounts.service.AccountService;

import java.math.BigDecimal;
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

    @Override
    public Account updateAccount(Account account) throws SQLException {
        return dao.updateAccount(account);
    }

    @Override
    public Account updateBalance(int id, BigDecimal amount) throws SQLException {
        Account account = dao.findAccount(id);
        System.out.println(account.getBalance());
        BigDecimal newBalance = account.getBalance().add(amount);
        System.out.println("newBalance: " +  newBalance);
        account.setBalance(newBalance);
        System.out.println("Account after update: " + account);
        dao.updateBalance(id, newBalance);

        return account;
    }


}
