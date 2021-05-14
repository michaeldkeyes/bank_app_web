package org.bankapp.accounts.service.impl;

import org.bankapp.accounts.dao.AccountDAO;
import org.bankapp.accounts.dao.impl.AccountDAOImpl;
import org.bankapp.accounts.model.Account;
import org.bankapp.accounts.service.AccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
class AccountServiceImplTest {

    private final Account account = new Account(1, "Checking", BigDecimal.ZERO, 1, true, 0 );
    private final Set<Account> accounts = new HashSet<>();

    @BeforeEach
    void setUp() {
        accounts.add(account);
    }

    @Test
    void getAllAccounts() throws SQLException {
        AccountDAO dao = mock(AccountDAO.class);
        AccountService accountService = new AccountServiceImpl(dao);
        when(dao.getAllAccounts()).thenReturn(accounts);
        assertEquals(accountService.getAllAccounts().size(), accounts.size());
    }

    @Test
    void updateAccount() throws SQLException {
        AccountDAO dao = mock(AccountDAOImpl.class);
        when(dao.updateAccount(account)).thenReturn(account);
        AccountService accountService = new AccountServiceImpl(dao);
        accountService.updateAccount(account);
        assertNotNull(account);
    }

    @Test
    void updateBalance() throws SQLException {
        account.setBalance(new BigDecimal("10.15"));
        AccountDAO dao = mock(AccountDAOImpl.class);
        when(dao.findAccount(1)).thenReturn(account);
        AccountService accountService = new AccountServiceImpl(dao);
        doNothing().when(dao).updateBalance(1, new BigDecimal("10.15"));
        assertEquals(new BigDecimal("10.15"), account.getBalance());
    }
}