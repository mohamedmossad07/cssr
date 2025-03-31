package org.exalt.cssr.users;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.exalt.cssr.exceptions.ApiRequestException;
import org.exalt.cssr.responses.AbstractResponse;
import org.exalt.cssr.responses.SuccessResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/users")
@Tag(name = "User Management", description = "Endpoints for managing users (drivers and owners)")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    /**
     * Registers a new user (either driver or owner)
     *
     * @param user The user to register with username, password, email, and type (DRIVER/OWNER)
     * @return The registered user with generated ID
     */
    @Operation(summary = "Register a new user", description = "Creates a new user account (driver or owner)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User registered successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = User.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "409", description = "Username or email already exists")
    })
    @PostMapping("")
    public ResponseEntity<AbstractResponse> registerUser(@Parameter(description = "User object to register", required = true) @Valid @RequestBody User user) {
        Optional<User> registeredUser = userService.registerUser(user);
        if (registeredUser.isPresent()) {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new SuccessResponse<>("User Registered successfully.",registeredUser.get()));
        }
        throw new IllegalStateException("Something went wrong internally.");
    }

    /**
     * Retrieves user details by ID
     *
     * @param id The ID of the user to retrieve
     * @return The user details
     */
    @Operation(summary = "Get user by ID", description = "Returns a single user by their ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = User.class))}),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<AbstractResponse> findUserById(
            @Parameter(description = "ID of the user to retrieve", required = true)
            @PathVariable String id) {
        User user = userService.findUserById(id).orElseThrow(() -> new ApiRequestException("User not found",HttpStatus.NOT_FOUND));
        return ResponseEntity.status(HttpStatus.OK).
                body(new SuccessResponse<>( user));
    }
}