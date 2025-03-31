package org.exalt.cssr.reservations;

import java.util.List;

public interface ReservationService {
    Reservation createReservation(Reservation reservation);
    Reservation confirmReservation(String reservationId);
    List<Reservation> getReservationsByDriver(String driverId);
}
