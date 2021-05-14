package org.bankapp.transactions.dao.impl;

import org.bankapp.dbutils.PostgresConnection;
import org.bankapp.transactions.dao.TransactionDAO;
import org.bankapp.transactions.model.Transaction;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;

public class TransactionDAOImpl implements TransactionDAO {

    @Override
    public Set<Transaction> getAllTransactions(int accountId) throws SQLException {
        Set<Transaction> transactions = new HashSet<>();
        try (Connection connection = PostgresConnection.getConnection()) {
            String sql = "SELECT id, amount, account_id, \"timestamp\", \"type\" FROM bank_schema.transactions WHERE account_id=?;\n";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1, accountId);

                ResultSet resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) {
                    Transaction transaction = new Transaction();
                    transaction.setId(resultSet.getInt(("id")));
                    transaction.setAmount(resultSet.getBigDecimal("amount"));
                    transaction.setAccountId(resultSet.getInt("account_id"));
                    transaction.setTimestamp(resultSet.getTimestamp("timestamp"));
                    transaction.setType(resultSet.getString("type"));
                    transactions.add(transaction);
                }
            }
        } catch (SQLException e) {
            throw new SQLException(e);
        }

        return transactions;
    }

    @Override
    public void saveTransaction(Transaction transaction) throws SQLException {
        try (Connection connection = PostgresConnection.getConnection()) {
            String sql = "INSERT INTO bank_schema.transactions (amount, account_id, \"timestamp\", type) VALUES(?, ?, ?, ?);\n";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                preparedStatement.setBigDecimal(1, transaction.getAmount());
                preparedStatement.setInt(2, transaction.getAccountId());
                preparedStatement.setTimestamp(3, java.sql.Timestamp.valueOf(java.time.LocalDateTime.now()));
                preparedStatement.setString(4, transaction.getType());

                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }
}
