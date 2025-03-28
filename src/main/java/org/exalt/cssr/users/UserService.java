package org.exalt.cssr.users;

import jakarta.validation.Valid;

import java.util.Optional;

/**
 * User service contract and business login overview
 */
public interface UserService {
    /**
     * Register user (OWNER/DRIVER)
     * @param user user to registered
     * @return the registered user as a response
     */
    Optional<User> registerUser(User user);

    /**
     * Retrieves user details by ID
     *
     * @param id The user id to search for
     * @return optional user
     */
    Optional<User> findUserById(String id);
}
