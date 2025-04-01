package org.exalt.cssr.cars;

import org.exalt.cssr.reservations.Reservation;
import org.reservations.cssr.*;

import java.util.List;

/**
 * Web Service 'SOAP' for car rental service
 */
public interface CarRentalSoapService {
    CreateReservationResponse createReservation(CreateReservationRequest createReservation);

    GetAvailableCarsResponse getAvailableCars();

    ConfirmReservationResponse confirmReservation(ConfirmReservationRequest request);
}
