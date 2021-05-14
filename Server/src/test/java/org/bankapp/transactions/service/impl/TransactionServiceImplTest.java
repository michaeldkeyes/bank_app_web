package org.bankapp.transactions.service.impl;

import org.bankapp.accounts.model.Account;
import org.bankapp.accounts.service.impl.AccountServiceImpl;
import org.bankapp.transactions.dao.impl.TransactionDAOImpl;
import org.bankapp.transactions.model.Transaction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

class TransactionServiceImplTest {

    @Mock
    private TransactionDAOImpl dao;

    @Mock
    private AccountServiceImpl accountService;

    @InjectMocks
    private TransactionServiceImpl transactionService;

    private Transaction transaction;
    private List<Transaction> transactions;
    private Account account;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        transaction = new Transaction();
        transactions = new ArrayList<>();
        transactions.add(transaction);
        account = new Account();
    }

    @Test
    void createTransaction() throws SQLException {
        doNothing().when(dao).saveTransaction(transaction);
        account.setBalance(new BigDecimal("100"));
        when(accountService.updateBalance(0, new BigDecimal("100"))).thenReturn(account);
        Assertions.assertEquals(new BigDecimal("100"), account.getBalance());
    }

    @Test
    void getAllTransactions() throws SQLException {
        when(dao.getAllTransactions(1)).thenReturn(transactions);
        transactionService = new TransactionServiceImpl(dao);
        Assertions.assertEquals(transactionService.getAllTransactions(1).size(), transactions.size());
    }
}