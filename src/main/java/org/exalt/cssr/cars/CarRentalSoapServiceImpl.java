package org.exalt.cssr.cars;

import lombok.RequiredArgsConstructor;
import org.exalt.cssr.reservations.Reservation;
import org.exalt.cssr.reservations.ReservationMapper;
import org.exalt.cssr.reservations.ReservationService;
import org.reservations.cssr.CarSoap;
import org.reservations.cssr.ReservationSoap;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Soap car rental service
 */
@Service
@RequiredArgsConstructor
public class CarRentalSoapServiceImpl implements CarRentalSoapService {

    private final CarService carService;
    private final ReservationService reservationService;
    private final CarMapper carMapper;
    private final ReservationMapper reservationMapper;

    /**
     * get list of available cars
     * @return List of available cars
     */
    @Override
    public List<CarSoap> getAvailableCars() {
        return carService.getAvailableCars().stream()
                .map(carMapper::mapCarToCarSoap).toList();
    }

    /**
     * create reservation
     * @param reservation details to bbe created
     * @return the saved reservation
     */
    @Override
    public ReservationSoap createReservation(Reservation reservation) {
        return reservationMapper.mapReservationToReservationSoap(reservationService.createReservation(reservation));
    }

    /**
     * confirm reservation by id
     * @param reservationId to be confirmed
     * @return reservation status after trying to confirm
     */
    @Override
    public boolean confirmReservation(String reservationId) {
        return reservationService.confirmReservation(reservationId) != null;
    }
}