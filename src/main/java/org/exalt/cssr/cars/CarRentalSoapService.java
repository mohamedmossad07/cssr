package org.exalt.cssr.cars;

import org.exalt.cssr.reservations.Reservation;
import org.reservations.cssr.CarSoap;
import org.reservations.cssr.ReservationSoap;

import java.util.List;

/**
 * Web Service 'SOAP' for car rental service
 */
public interface CarRentalSoapService {
    ReservationSoap createReservation(Reservation reservation);
    List<CarSoap> getAvailableCars();
    boolean confirmReservation(String reservationId);
}
