package com.miluski.products.campaignes.backend;
import org.junit.jupiter.api.Test;

import com.miluski.products.campaignes.backend.model.dto.UserDto;

import static org.junit.jupiter.api.Assertions.*;

class UserDtoTest {

    @Test
    void testGetId() {
        Long id = 1L;
        UserDto userDto = new UserDto(id, "john", 100.0);
        assertEquals(id, userDto.getId());
    }

    @Test
    void testGetUsername() {
        String username = "john";
        UserDto userDto = new UserDto(1L, username, 100.0);
        assertEquals(username, userDto.getUsername());
    }

    @Test
    void testGetAccountBalance() {
        Double accountBalance = 100.0;
        UserDto userDto = new UserDto(1L, "john", accountBalance);
        assertEquals(accountBalance, userDto.getAccountBalance());
    }

    @Test
    void testSetId() {
        Long id = 1L;
        UserDto userDto = new UserDto();
        userDto.setId(id);
        assertEquals(id, userDto.getId());
    }

    @Test
    void testSetUsername() {
        String username = "john";
        UserDto userDto = new UserDto();
        userDto.setUsername(username);
        assertEquals(username, userDto.getUsername());
    }

    @Test
    void testSetAccountBalance() {
        Double accountBalance = 100.0;
        UserDto userDto = new UserDto();
        userDto.setAccountBalance(accountBalance);
        assertEquals(accountBalance, userDto.getAccountBalance());
    }
}