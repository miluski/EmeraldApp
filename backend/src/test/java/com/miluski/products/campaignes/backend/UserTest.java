package com.miluski.products.campaignes.backend;

import org.junit.jupiter.api.Test;

import com.miluski.products.campaignes.backend.model.entities.User;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class UserTest {

    @Test
    void testGetId() {
        User user = new User();
        assertNull(user.getId());
    }

    @Test
    void testGetUsername() {
        User user = new User("john", "password");
        assertEquals("john", user.getUsername());
    }

    @Test
    void testGetPassword() {
        User user = new User("john", "password");
        assertEquals("password", user.getPassword());
    }

    @Test
    void testGetAccountBalance() {
        User user = new User("john", "password");
        assertEquals(200d, user.getAccountBalance());
    }

    @Test
    void testGetRefreshToken() {
        User user = new User("john", "password");
        assertNull(user.getRefreshToken());
    }

    @Test
    void testSetUsername() {
        User user = new User();
        user.setUsername("john");
        assertEquals("john", user.getUsername());
    }

    @Test
    void testSetPassword() {
        User user = new User();
        user.setPassword("password");
        assertEquals("password", user.getPassword());
    }

    @Test
    void testSetAccountBalance() {
        User user = new User();
        user.setAccountBalance(300d);
        assertEquals(300d, user.getAccountBalance());
    }

    @Test
    void testSetRefreshToken() {
        User user = new User();
        user.setRefreshToken("token");
        assertEquals("token", user.getRefreshToken());
    }
}