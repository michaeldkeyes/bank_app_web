package org.bankapp.transactions.dao.impl;

import org.bankapp.dbutils.PostgresConnection;
import org.bankapp.transactions.dao.TransactionDAO;
import org.bankapp.transactions.model.Transaction;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class TransactionDAOImpl implements TransactionDAO {


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
