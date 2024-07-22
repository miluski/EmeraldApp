package com.miluski.products.campaignes.backend.model.dto;

/**
 * Represents a Data Transfer Object (DTO) for a user.
 * This class encapsulates user-related data including the user's ID, username,
 * password, and account balance.
 */
public class UserDto {
    private Long id;
    private String username;
    private String password;
    private Double accountBalance;

    /**
     * Default constructor.
     */
    public UserDto() {
    }

    /**
     * Constructs a UserDto with specified ID, username, and account balance.
     * 
     * @param id             The unique identifier for the user.
     * @param username       The username of the user.
     * @param accountBalance The account balance of the user.
     */
    public UserDto(Long id, String username, Double accountBalance) {
        this.id = id;
        this.username = username;
        this.accountBalance = accountBalance;
    }

    /**
     * Sets the user's ID.
     * 
     * @param id The unique identifier to set for the user.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Returns the user's ID.
     * 
     * @return The unique identifier of the user.
     */
    public Long getId() {
        return this.id;
    }

    /**
     * Sets the user's username.
     * 
     * @param username The username to set for the user.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Returns the user's username.
     * 
     * @return The username of the user.
     */
    public String getUsername() {
        return this.username;
    }

    /**
     * Sets the user's password.
     * Note: In real applications, ensure the password is encrypted/hashed before
     * storing.
     * 
     * @param password The password to set for the user.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Returns the user's password.
     * 
     * @return The password of the user.
     */
    public String getPassword() {
        return this.password;
    }

    /**
     * Sets the user's account balance.
     * 
     * @param accountBalance The account balance to set for the user.
     */
    public void setAccountBalance(Double accountBalance) {
        this.accountBalance = accountBalance;
    }

    /**
     * Returns the user's account balance.
     * 
     * @return The account balance of the user.
     */
    public Double getAccountBalance() {
        return this.accountBalance;
    }

    /**
     * Returns a string representation of the UserDto.
     * This includes the user's ID, username, and account balance.
     * 
     * @return A string representation of the UserDto.
     */
    @Override
    public String toString() {
        return "ID: " + Long.toString(id) + " Username: " + this.username + " AccountBalance: "
                + Double.toString(accountBalance);
    }
}
