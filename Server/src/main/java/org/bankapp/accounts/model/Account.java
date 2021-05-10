package org.bankapp.accounts.model;

import java.math.BigDecimal;
import java.sql.Date;

public class Account {
    private int accountId;
    private String type;
    private BigDecimal balance;
    private int ownerId;
    private boolean pending;
    private Date createdAt;

    public Account() {
    }

    public Account(String type, BigDecimal balance, int ownerId, boolean pending) {
        this.type = type;
        this.balance = balance;
        this.ownerId = ownerId;
        this.pending = pending;
    }

    public Account(int accountId, String type, BigDecimal balance, int ownerId, boolean pending) {
        this.accountId = accountId;
        this.type = type;
        this.balance = balance;
        this.ownerId = ownerId;
        this.pending = pending;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public int getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }

    public boolean isPending() {
        return pending;
    }

    public void setPending(boolean pending) {
        this.pending = pending;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "Account{" +
                "accountId=" + accountId +
                ", type='" + type + '\'' +
                ", balance=" + balance +
                ", ownerId=" + ownerId +
                ", pending=" + pending +
                ", createdAt=" + createdAt +
                '}';
    }
}
