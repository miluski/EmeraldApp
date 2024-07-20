package com.miluski.products.campaignes.backend;

import com.miluski.products.campaignes.backend.model.dto.UserDto;
import com.miluski.products.campaignes.backend.model.entities.User;
import com.miluski.products.campaignes.backend.model.mappers.UserMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class UserMapperTest {

    private UserMapper userMapper;

    @BeforeEach
    void setUp() {
        userMapper = new UserMapper();
    }

    @Test
    void convertToUser_ValidUserDto_ReturnsUserObject() {
        UserDto userDto = new UserDto(1L, "john.doe", 100.0);
        User user = userMapper.convertToUser(userDto);
        assertNotNull(userDto);
        assertEquals(user.getUsername(), userDto.getUsername());
        assertEquals(user.getPassword(), userDto.getPassword());
    }

    @Test
    void convertToUserDto_ValidUser_ReturnsUserDtoObject() {
        User user = new User("john.doe", "password");
        UserDto userDto = userMapper.convertToUserDto(user);
        assertNotNull(user);
        assertEquals(userDto.getId(), user.getId());
        assertEquals(userDto.getUsername(), user.getUsername());
        assertEquals(user.getAccountBalance(), userDto.getAccountBalance());
    }
}