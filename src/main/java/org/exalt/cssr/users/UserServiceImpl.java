package org.exalt.cssr.users;

import lombok.RequiredArgsConstructor;
import org.exalt.cssr.exceptions.ApiRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Service implementation for user management operations
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    /**
     *
     * {@inheritDoc}
     * @throws ApiRequestException if username or email already exists
     */
    @Override
    public Optional<User> registerUser(User user) {
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new ApiRequestException("Username already exists", HttpStatus.CONFLICT);
        }
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new ApiRequestException("Email already exists", HttpStatus.CONFLICT);
        }
        return Optional.of(userRepository.save(user));
    }

    /**
     * {@inheritDoc}
     * @throws ApiRequestException if user not found
     */
    @Override
    public Optional<User> findUserById(String id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent())
           return user;
        throw  new ApiRequestException("This user doesn't exists.",HttpStatus.NOT_FOUND);
    }
}