package org.exalt.cssr.location;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class LocationEvent {
    private String carId;
    private String reservationId;
    private double latitude;
    private double longitude;
    private LocalDateTime timestamp;
}
