package org.exalt.cssr.users;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(controllers = {UserController.class})
class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockitoBean
    UserServiceImpl userService;
    private final User sampleUser = User.builder().id("1000").username("Mohamed mossad").email("test@test.test").phone("01061218341").type(UserType.DRIVER).build();
    private final ObjectMapper objectMapper = new ObjectMapper();
    @Test
    void should_register_user_successfully() throws Exception {
        when(userService.registerUser(sampleUser)).thenReturn(Optional.of(sampleUser));
        mockMvc.perform(post("/api/users").contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(sampleUser))
                        ).andExpect(status().isCreated())
                         .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                         .andExpect(jsonPath("$.responseStatus").value(ResponseStatusType.SUCCESS.toString()))
                         .andExpect(jsonPath("$.code").value(HttpStatus.CREATED.value()))
                         .andExpect(jsonPath("$.data").exists())
                         .andExpect(jsonPath("$.message").value("User Registered successfully."));
    }
    @Test
    void should_find_user_by_id() throws Exception {
        when(userService.findUserById(sampleUser.getId())).thenReturn(Optional.of(sampleUser));
        mockMvc.perform(get("/api/users/"+sampleUser.getId())).andExpect(status().isFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.responseStatus").value(ResponseStatusType.SUCCESS.toString()))
                .andExpect(jsonPath("$.code").value(HttpStatus.FOUND.value()))
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.message").doesNotExist());
    }
}