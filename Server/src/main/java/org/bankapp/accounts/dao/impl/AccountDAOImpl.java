package org.bankapp.accounts.dao.impl;

import org.bankapp.accounts.dao.AccountDAO;
import org.bankapp.accounts.model.Account;
import org.bankapp.dbutils.PostgresConnection;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;

public class AccountDAOImpl implements AccountDAO {
    @Override
    public void saveAccount(Account account) throws SQLException {
        try (Connection connection = PostgresConnection.getConnection()) {
            String sql = "INSERT INTO bank_schema.accounts (\"type\", balance, owner_id, pending, created_at) VALUES(?, ?, ?, ?, ?);\n";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                preparedStatement.setString(1, account.getType());
                preparedStatement.setBigDecimal(2, account.getBalance());
                preparedStatement.setInt(3, account.getOwnerId());
                preparedStatement.setBoolean(4, account.isPending());
                preparedStatement.setDate(5, java.sql.Date.valueOf(java.time.LocalDate.now()));

                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public Set<Account> getAllAccounts(int ownerId) throws SQLException {
        Set<Account> accounts = new HashSet<>();
        try (Connection connection = PostgresConnection.getConnection()) {
            String sql = "SELECT account_id, \"type\", balance, owner_id, pending, created_at FROM bank_schema.accounts WHERE owner_id = ?;\n";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1, ownerId);

                ResultSet resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) {
                    Account account = new Account();
                    account.setAccountId(resultSet.getInt("account_id"));
                    account.setType(resultSet.getString("type"));
                    account.setBalance(resultSet.getBigDecimal("balance"));
                    account.setOwnerId(resultSet.getInt("owner_id"));
                    account.setPending(resultSet.getBoolean("pending"));
                    account.setCreatedAt(resultSet.getDate("created_at"));
                    accounts.add(account);
                }
            }
        } catch (SQLException e) {
            throw new SQLException(e);
        }

        return accounts;
    }
}
