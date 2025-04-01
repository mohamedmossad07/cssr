package org.exalt.cssr.location;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LocationEvent {
    private String carId;
    private String reservationId;
    private double latitude;
    private double longitude;
    private LocalDateTime timestamp;
}
