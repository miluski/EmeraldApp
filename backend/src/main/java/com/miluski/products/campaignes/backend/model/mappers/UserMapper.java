package com.miluski.products.campaignes.backend.model.mappers;

import org.springframework.stereotype.Component;

import com.miluski.products.campaignes.backend.model.dto.UserDto;
import com.miluski.products.campaignes.backend.model.entities.User;

/**
 * Provides functionality for converting between User entities and UserDto
 * objects.
 * This class contains methods to convert a UserDto object to a User entity and
 * vice versa,
 * facilitating the transfer of data between the application's service layer and
 * its data access layer.
 * It is used to abstract the conversion logic from the service layer, ensuring
 * a separation of concerns
 * and cleaner code.
 */
@Component
public class UserMapper {

    /**
     * Converts a UserDto object to a User entity.
     * This method creates a new User entity and initializes it with the username
     * and password
     * from the provided UserDto object.
     * 
     * @param userDto The UserDto object containing the data to be converted to a
     *                User entity.
     * @return A new User entity initialized with the username and password from the
     *         UserDto.
     */
    public User convertToUser(UserDto userDto) {
        return new User(userDto.getUsername(), userDto.getPassword());
    }

    /**
     * Converts a User entity to a UserDto object.
     * This method creates a new UserDto object and initializes it with the id,
     * username,
     * and account balance from the provided User entity.
     * 
     * @param user The User entity containing the data to be converted to a UserDto
     *             object.
     * @return A new UserDto object initialized with the id, username, and account
     *         balance from the User entity.
     */
    public UserDto convertToUserDto(User user) {
        return new UserDto(user.getId(), user.getUsername(), user.getAccountBalance());
    }

}
