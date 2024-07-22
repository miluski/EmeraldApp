package com.miluski.products.campaignes.backend.model.entities;

import jakarta.persistence.*;

/**
 * Represents a user entity in the database.
 * This class is an entity model for users, containing properties such as
 * user ID, username, password, account balance, and refresh token.
 * It is mapped to the "users" table in the database.
 */
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "account_balance", nullable = true)
    private Double accountBalance = 200d;

    @Column(name = "refresh_token", nullable = true)
    private String refreshToken;

    /**
     * Default constructor.
     */
    public User() {
    }

    /**
     * Constructs a User with specified username and password.
     * 
     * @param username The username of the user.
     * @param password The password of the user.
     */
    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    /**
     * Gets the unique identifier of the user.
     * 
     * @return The unique identifier of the user.
     */
    public Long getId() {
        return this.id;
    }

    /**
     * Sets the username of the user.
     * 
     * @param username The username to set for the user.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Gets the username of the user.
     * 
     * @return The username of the user.
     */
    public String getUsername() {
        return this.username;
    }

    /**
     * Sets the password of the user.
     * 
     * @param password The password to set for the user.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Gets the password of the user.
     * 
     * @return The password of the user.
     */
    public String getPassword() {
        return this.password;
    }

    /**
     * Sets the account balance of the user.
     * 
     * @param accountBalance The account balance to set for the user.
     */
    public void setAccountBalance(Double accountBalance) {
        this.accountBalance = accountBalance;
    }

    /**
     * Gets the account balance of the user.
     * 
     * @return The account balance of the user.
     */
    public Double getAccountBalance() {
        return this.accountBalance;
    }

    /**
     * Sets the refresh token for the user's session.
     * 
     * @param refreshToken The refresh token to set for the user.
     */
    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    /**
     * Gets the refresh token for the user's session.
     * 
     * @return The refresh token of the user.
     */
    public String getRefreshToken() {
        return this.refreshToken;
    }

}
