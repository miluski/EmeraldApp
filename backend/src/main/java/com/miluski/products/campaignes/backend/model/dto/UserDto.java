package com.miluski.products.campaignes.backend.model.dto;

public class UserDto {
    private Long id;
    private String username;
    private String password;
    private Double accountBalance;

    public UserDto() {
    }

    public UserDto(Long id, String username, Double accountBalance) {
        this.id = id;
        this.username = username;
        this.accountBalance = accountBalance;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return this.id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return this.username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return this.password;
    }

    public void setAccountBalance(Double accountBalance) {
        this.accountBalance = accountBalance;
    }

    public Double getAccountBalance() {
        return this.accountBalance;
    }

    @Override
    public String toString() {
        return "ID: " + Long.toString(id) + " Username: " + this.username + " AccountBalance: "
                + Double.toString(accountBalance);
    }
}
