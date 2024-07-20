package com.miluski.products.campaignes.backend.model.mappers;

import org.springframework.stereotype.Component;

import com.miluski.products.campaignes.backend.model.dto.UserDto;
import com.miluski.products.campaignes.backend.model.entities.User;

@Component
public class UserMapper {

    public User convertToUser(UserDto userDto) {
        return new User(userDto.getUsername(), userDto.getPassword());
    }

    public UserDto convertToUserDto(User user) {
        return new UserDto(user.getId(), user.getUsername(), user.getAccountBalance());
    }

}
