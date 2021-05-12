package org.bankapp.transactions.model;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class Transaction {
    int id;
    BigDecimal amount;
    int accountId;
    Timestamp timestamp;
    String type;

    public Transaction() {
    }

    public Transaction(BigDecimal amount, int accountId, Timestamp timestamp, String type) {
        this.amount = amount;
        this.accountId = accountId;
        this.timestamp = timestamp;
        this.type = type;
    }

    public Transaction(int id, BigDecimal amount, int accountId, Timestamp timestamp, String type) {
        this.id = id;
        this.amount = amount;
        this.accountId = accountId;
        this.timestamp = timestamp;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", amount=" + amount +
                ", accountId=" + accountId +
                ", timestamp=" + timestamp +
                ", type='" + type + '\'' +
                '}';
    }
}
