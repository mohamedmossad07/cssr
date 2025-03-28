package org.exalt.cssr.users;

import org.exalt.cssr.exceptions.ApiRequestException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.ResultMatcher;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    UserRepository userRepository;

    @InjectMocks
    UserServiceImpl userService;
    private final User sampleUser = User.builder().id("1000").username("Mohamed mossad").email("test@test.test").phone("01061218341").type(UserType.DRIVER).build();

    @Test
    void should_throws_exception_when_registering_username_already_exists(){
        when(userRepository.findByUsername(sampleUser.getUsername())).thenReturn(Optional.of(sampleUser));
        assertThrowsExactly(ApiRequestException.class,() -> userService.registerUser(sampleUser), "Username already exists");
    }
    @Test
    void should_throws_exception_when_registering_email_already_exists(){
        String tempUsername = sampleUser.getUsername();
        sampleUser.setUsername("Tested");
        when(userRepository.findByEmail(sampleUser.getEmail())).thenReturn(Optional.of(sampleUser));
        assertThrowsExactly(ApiRequestException.class,() -> userService.registerUser(sampleUser), "Email already exists");
        sampleUser.setUsername(tempUsername);
    }
    @Test
    void should_register_user_successfully(){
        User newUser = User.builder().id("5444").username("test").type(UserType.OWNER).phone("010105050600").build();
        when(userRepository.save(newUser)).thenReturn(newUser);
        Optional<User> registeredUser = userService.registerUser(newUser);
        assertTrue(registeredUser.isPresent());
        assertEquals(newUser.getId(), registeredUser.get().getId());
        assertEquals(newUser.getUsername(), registeredUser.get().getUsername());
        assertEquals(newUser.getEmail(), registeredUser.get().getEmail());
        assertEquals(newUser.getPhone(), registeredUser.get().getPhone());
    }
}