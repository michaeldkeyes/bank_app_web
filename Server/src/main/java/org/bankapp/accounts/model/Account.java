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
    private int approvedBy;

    public Account() {
    }

    public Account(String type, int ownerId) {
        this.type = type;
        this.balance = BigDecimal.ZERO;
        this.ownerId = ownerId;
        this.pending = true;
    }

    public Account(int accountId, String type, BigDecimal balance, int ownerId, boolean pending, int approvedBy) {
        this.accountId = accountId;
        this.type = type;
        this.balance = balance;
        this.ownerId = ownerId;
        this.pending = pending;
        this.approvedBy = approvedBy;
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

    public int getApprovedBy() {
        return approvedBy;
    }

    public void setApprovedBy(int approvedBy) {
        this.approvedBy = approvedBy;
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
                ", approvedBy=" + approvedBy +
                '}';
    }
}
