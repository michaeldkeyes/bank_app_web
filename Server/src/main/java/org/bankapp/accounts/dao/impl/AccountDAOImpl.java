package org.bankapp.accounts.dao.impl;

import org.bankapp.accounts.dao.AccountDAO;
import org.bankapp.accounts.model.Account;
import org.bankapp.dbutils.PostgresConnection;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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
    public Account findAccount(int id) throws SQLException {
        Account account = new Account();
        try (Connection connection = PostgresConnection.getConnection()) {
            String sql = "SELECT account_id, \"type\", balance, owner_id, pending, created_at FROM bank_schema.accounts WHERE account_id = ?;\n";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1, id);

                ResultSet resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {
                    account.setAccountId(resultSet.getInt("account_id"));
                    account.setType(resultSet.getString("type"));
                    account.setBalance(resultSet.getBigDecimal("balance"));
                    account.setOwnerId(resultSet.getInt("owner_id"));
                    account.setCreatedAt(resultSet.getDate("created_at"));
                }
            }
        } catch (SQLException e) {
            throw new SQLException(e);
        }
        return account;
    }

    @Override
    public List<Account> getAllAccounts(int ownerId) throws SQLException {
        List<Account> accounts = new ArrayList<>();
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

    public List<Account> getAllAccounts() throws SQLException {
        List<Account> accounts = new ArrayList<>();
        try (Connection connection = PostgresConnection.getConnection()) {
            String sql = "SELECT account_id, \"type\", balance, owner_id, pending, created_at FROM bank_schema.accounts;\n";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

                ResultSet resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) {
                    Account account = new Account();
                    account.setAccountId(resultSet.getInt("account_id"));
                    account.setType(resultSet.getString("type"));
                    account.setBalance(resultSet.getBigDecimal("balance"));
                    account.setOwnerId(resultSet.getInt("owner_id"));
                    account.setPending(resultSet.getBoolean("pending"));
                    account.setCreatedAt(resultSet.getDate("created_at"));

                    String sql1 = "SELECT account_id, approved_by FROM bank_schema.approved_accounts WHERE account_id = ?;\n";
                    try (PreparedStatement ps = connection.prepareStatement(sql1)) {
                        ps.setInt(1, account.getAccountId());
                        ResultSet rs = ps.executeQuery();
                        if (rs.next()) {
                            account.setApprovedBy(rs.getInt("approved_by"));
                        }
                    }

                    accounts.add(account);
                }
            }
        } catch (SQLException e) {
            throw new SQLException(e);
        }

        return accounts;
    }

    @Override
    public Account updateAccount(Account account) throws SQLException {
        try (Connection connection = PostgresConnection.getConnection()) {
            String sql = "UPDATE bank_schema.accounts SET \"type\"=?, balance=?, pending=? WHERE account_id=?;\n";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, account.getType());
                preparedStatement.setBigDecimal(2, account.getBalance());
                preparedStatement.setBoolean(3, account.isPending());
                preparedStatement.setInt(4, account.getAccountId());

                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new SQLException(e);
        }

        return account;
    }

    @Override
    public void updateBalance(int id, BigDecimal newBalance) throws SQLException {
        try (Connection connection = PostgresConnection.getConnection()) {
            String sql = "UPDATE bank_schema.accounts SET balance=? WHERE account_id=?;\n";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setBigDecimal(1, newBalance);
                preparedStatement.setInt(2, id);

                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public void updatePending(int id, boolean pending, int approvedBy) throws SQLException {
        try (Connection connection = PostgresConnection.getConnection()) {
            String sql = "UPDATE bank_schema.accounts SET pending=? WHERE account_id=?;\n";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setBoolean(1, pending);
                preparedStatement.setInt(2, id);

                preparedStatement.executeUpdate();
            }
            sql = "INSERT INTO bank_schema.approved_accounts (account_id, approved_by) VALUES(?, ?);\n";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1, id);
                preparedStatement.setInt(2, approvedBy);

                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }
}
