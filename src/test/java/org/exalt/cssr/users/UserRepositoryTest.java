package org.exalt.cssr.users;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;
    private final User sampleUser = User.builder().id("1000").username("Mohamed mossad").email("test@test.test").phone("01061218341").type(UserType.DRIVER).build();

    @Test
    void should_insert_user_to_db(){
        User savedUser = userRepository.save(sampleUser);
        assertNotNull(savedUser);
        assertEquals(sampleUser.getId(), savedUser.getId());
        assertEquals(sampleUser.getUsername(), savedUser.getUsername());
        assertEquals(sampleUser.getEmail(), savedUser.getEmail());
        assertEquals(sampleUser.getPhone(), savedUser.getPhone());
    }
    @Test
    void should_finding_user_by_id_in_db(){
        Optional<User> optionalUser = userRepository.findById(sampleUser.getId());
        assertTrue(optionalUser.isPresent());
        assertEquals(optionalUser.get().getId(),sampleUser.getId());
        assertEquals(optionalUser.get().getUsername(),sampleUser.getUsername());
        assertEquals(optionalUser.get().getEmail(),sampleUser.getEmail());
        assertEquals(optionalUser.get().getPhone(),sampleUser.getPhone());
    }
    @Test
    void should_finding_user_by_email_in_db(){
        Optional<User> optionalUser = userRepository.findByEmail(sampleUser.getEmail());
        assertTrue(optionalUser.isPresent());
        assertEquals(optionalUser.get().getId(),sampleUser.getId());
        assertEquals(optionalUser.get().getUsername(),sampleUser.getUsername());
        assertEquals(optionalUser.get().getEmail(),sampleUser.getEmail());
        assertEquals(optionalUser.get().getPhone(),sampleUser.getPhone());
    }
    @Test
    void should_finding_user_by_username_in_db(){
        Optional<User> optionalUser = userRepository.findByUsername(sampleUser.getUsername());
        assertTrue(optionalUser.isPresent());
        assertEquals(optionalUser.get().getId(),sampleUser.getId());
        assertEquals(optionalUser.get().getUsername(),sampleUser.getUsername());
        assertEquals(optionalUser.get().getEmail(),sampleUser.getEmail());
        assertEquals(optionalUser.get().getPhone(),sampleUser.getPhone());
    }

}