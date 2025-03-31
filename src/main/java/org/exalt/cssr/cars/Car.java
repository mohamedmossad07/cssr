package org.exalt.cssr.cars;

import com.aerospike.client.query.IndexType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.aerospike.annotation.Indexed;
import org.springframework.data.aerospike.mapping.Document;
import org.springframework.data.annotation.Id;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

/**
 * Represents a car available for rental
 */
@Document(collection = "cars")
@Schema(description = "Car entity available for rental")
@Data
@Builder
public class Car {
    @Id
    @Schema(description = "Unique identifier of the car", example = "car456")
    private String id;

    @Schema(description = "ID of the owner user", example = "usr123", requiredMode = REQUIRED)
    @Indexed(type = IndexType.STRING)
    @NotBlank
    private String ownerId;

    @Schema(description = "Make of the car", example = "Toyota", requiredMode = REQUIRED)
    @NotBlank
    private String make;

    @Schema(description = "Model of the car", example = "Camry", requiredMode = REQUIRED)
    @NotBlank
    private String model;

    @Schema(description = "Manufacturing year", example = "2020", requiredMode = REQUIRED)
    private int year;

    @Schema(description = "Daily rental rate in USD", example = "45.99", requiredMode = REQUIRED)
    private double hourly_rate;

    @Schema(description = "Availability status", example = "true")
    @Indexed(type = IndexType.NUMERIC)
    private boolean available;
}
