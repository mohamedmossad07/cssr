package org.exalt.cssr.reservations;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.aerospike.mapping.Document;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.*;

/**
 * Represents a car reservation between a driver and an owner
 */
@Document(collection = "reservations")
@Schema(description = "Reservation entity representing a car rental booking")
@Data
@Builder
public class Reservation {
    @Id
    @Schema(description = "Unique identifier of the reservation", example = "res789")
    @NotBlank
    private String id;

    @Schema(description = "ID of the car being reserved", example = "car456", requiredMode = REQUIRED)
    @NotBlank
    private String carId;

    @Schema(description = "ID of the driver making the reservation", example = "usr123",requiredMode = REQUIRED)
    @NotBlank
    private String driverId;

    @Schema(description = "Start date and time of reservation", example = "2023-12-01T10:00:00", requiredMode = REQUIRED)
    @Future
    private LocalDateTime startTime;

    @Schema(description = "End date and time of reservation", example = "2023-12-05T10:00:00", requiredMode = REQUIRED)
    @Future
    private LocalDateTime endTime;

    @Schema(description = "Current status of the reservation", example = "PENDING")
    private ReservationStatus status;
}