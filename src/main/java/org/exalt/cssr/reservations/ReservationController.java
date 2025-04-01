package org.exalt.cssr.reservations;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.exalt.cssr.responses.SuccessResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reservations")
@Tag(name = "Reservation Management", description = "Endpoints for managing car reservations")
@RequiredArgsConstructor
public class ReservationController {
    private final ReservationService reservationService;

    /**
     * Creates a new reservation request
     *
     * @param reservation The reservation details (carId, driverId, startTime, endTime)
     * @return The created reservation with PENDING status
     */
    @Operation(summary = "Create a reservation", description = "Creates a new reservation request in PENDING state")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reservation created",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Reservation.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "409", description = "Car not available for requested time")
    })
    @PostMapping
    public ResponseEntity<?> createReservation(
            @Parameter(description = "Reservation details", required = true)
            @RequestBody Reservation reservation) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(new SuccessResponse<>(reservationService.createReservation(reservation)));
    }

    /**
     * Confirms a pending reservation
     *
     * @param id The ID of the reservation to confirm
     * @return The confirmed reservation
     */
    @Operation(summary = "Confirm a reservation", description = "Confirms a PENDING reservation by the car owner")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reservation confirmed",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Reservation.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid reservation ID"),
            @ApiResponse(responseCode = "404", description = "Reservation or Car not found"),
            @ApiResponse(responseCode = "409", description = "Reservation already confirmed or cancelled")
    })
    @PutMapping("/{id}/confirm")
    public ResponseEntity<?> confirmReservation(
            @Parameter(description = "ID of the reservation to confirm", required = true)
            @PathVariable String id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(new SuccessResponse<>(reservationService.confirmReservation(id)));
    }

    /**
     * Retrieves all reservations for a specific driver
     *
     * @param driverId The ID of the driver
     * @return List of reservations for the driver
     */
    @Operation(summary = "Get driver reservations", description = "Returns all reservations for a specific driver")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of reservations found",
                    content = {@Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = Reservation.class)))}),
    })
    @GetMapping("/driver/{driverId}")
    public List<Reservation> getDriverReservations(
            @Parameter(description = "ID of the driver", required = true)
            @PathVariable String driverId) {

        return reservationService.getReservationsByDriver(driverId);
    }
}