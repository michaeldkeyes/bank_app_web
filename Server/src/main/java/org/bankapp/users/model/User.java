package org.bankapp.users.model;

import org.bankapp.accounts.model.Account;

import java.sql.Date;
import java.util.List;

public class User {
    private int id;
    private String username;
    private String password;
    private Date dateCreated;
    private String type;
    private List<Account> accounts;

    public User() {
    }

    public User(int id, String username, String password, Date dateCreated) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.dateCreated = dateCreated;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", dateCreated=" + dateCreated +
                ", type='" + type + '\'' +
                ", accounts=" + accounts +
                '}';
    }
}
