package org.exalt.cssr.cars;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.exalt.cssr.responses.SuccessResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/cars")
@Tag(name = "Car Management", description = "Endpoints for managing cars owned by owners")
public class CarController {
    private final CarService carService;

    /**
     * Adds a new car to the system
     *
     * @param car The car details to add (must include ownerId)
     * @return The added car with generated ID
     */
    @Operation(summary = "Add a new car", description = "Allows owners to register their cars for rental")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Car added successfully",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Car.class)) }),
            @ApiResponse(responseCode = "404", description = "Car owner doesn't exists."),
            @ApiResponse(responseCode = "403", description = "Only owners can add cars"),
            @ApiResponse(responseCode = "500", description = "Internal server error if car deleted occasionally")
    })
    @PostMapping
    public ResponseEntity<?> addCar(
            @Parameter(description = "Car object to add", required = true)
            @RequestBody @Valid Car car) {
        Optional<Car> addedCar = carService.addCar(car);
        if (addedCar.isPresent())
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new SuccessResponse<>("Car added successfully.", HttpStatus.CREATED, addedCar.get()));
        throw new IllegalStateException("Something went wrong internally.");
    }

    /**
     * Retrieves all cars owned by a specific owner
     *
     * @param ownerId The ID of the owner
     * @return List of cars owned by the specified owner
     */
    @Operation(summary = "Get cars by owner", description = "Returns all cars registered by a specific owner")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of cars found",
                    content = { @Content(mediaType = "application/json", array = @ArraySchema(schema =  @Schema(implementation = Car.class))) }),
            @ApiResponse(responseCode = "404", description = "Owner not found")
    })
    @GetMapping("/owner/{ownerId}")
    public ResponseEntity<?> getCarsByOwner(
            @Parameter(description = "ID of the owner", required = true)
            @PathVariable String ownerId) {
        return  ResponseEntity.status(HttpStatus.OK)
                .body(new SuccessResponse<>(null, HttpStatus.OK, carService.getCarsByOwner(ownerId)));
    }

    /**
     * Retrieves all available cars for rental
     *
     * @return List of available cars
     */
    @Operation(summary = "Get available cars", description = "Returns all cars currently available for rental")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of available cars",
                    content = { @Content(mediaType = "application/json"
                            , array = @ArraySchema(schema =  @Schema(implementation = Car.class))) })
    })
    @GetMapping("/available")
    public ResponseEntity<?> getAvailableCars() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(new SuccessResponse<>(null, HttpStatus.OK, carService.getAvailableCars()));
    }
}