package com.miluski.products.campaignes.backend.model.services;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import com.miluski.products.campaignes.backend.model.repositories.UserRepository;

/**
 * Generates the signing key from the secret.
 * This method creates a new signing key using the specified secret and the
 * HmacSHA256 algorithm.
 * 
 * @return A Key object to be used for signing or verifying JWT tokens.
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    /**
     * Constructs a UserDetailsServiceImpl with a UserRepository.
     * 
     * @param userRepository The UserRepository for accessing user data from the
     *                       database.
     */
    @Autowired
    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Loads the user details by username.
     * This method is used by Spring Security to perform authentication.
     * It fetches the user from the database using the provided username. If the
     * user is found,
     * it returns a User object (from Spring Security) containing the username,
     * password, and an empty list of authorities.
     * If the user is not found, it throws a UsernameNotFoundException.
     * 
     * @param username The username of the user to load.
     * @return UserDetails object containing the user's information.
     * @throws UsernameNotFoundException if the user with the given username is not
     *                                   found.
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        com.miluski.products.campaignes.backend.model.entities.User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User " + username + " was not founded.");
        }
        return new User(user.getUsername(), user.getPassword(), new ArrayList<>());
    }

}
