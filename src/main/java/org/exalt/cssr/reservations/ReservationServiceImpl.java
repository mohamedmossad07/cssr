package org.exalt.cssr.reservations;

import lombok.RequiredArgsConstructor;
import org.exalt.cssr.cars.Car;
import org.exalt.cssr.cars.CarRepository;
import org.exalt.cssr.cars.CarService;
import org.exalt.cssr.exceptions.ApiRequestException;
import org.exalt.cssr.location.LocationEvent;
import org.exalt.cssr.location.LocationProducer;
import org.exalt.cssr.location.ReservationThreadFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Service implementation for reservation management operations
 */
@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService {
    private final ReservationRepository reservationRepository;
    private final CarService carService;
    private final CarRepository carRepository;
    private final LocationProducer locationProducer;
    private final ExecutorService defaultExecutorService = Executors.newCachedThreadPool();
    private final ExecutorService customExecutorService = Executors.newCachedThreadPool(ReservationThreadFactory.getInstance());
    /**
     * Creates a new reservation in PENDING state
     *
     * @param reservation the reservation to create
     * @return the created reservation
     * @throws ApiRequestException if car is not available or dates are invalid
     */
    @Override
    public Reservation createReservation(Reservation reservation) {
        // Validate reservation dates
        if (reservation.getStartTime().isBefore(LocalDateTime.now())) {
            throw new ApiRequestException("Start time must be in the future", HttpStatus.BAD_REQUEST);
        }
        if (reservation.getEndTime().isBefore(reservation.getStartTime())) {
            throw new ApiRequestException("End time must be after start time",HttpStatus.BAD_REQUEST);
        }

        // Check car availability
        Optional<Car> car = carRepository.findById(reservation.getCarId());
        if (car.isEmpty() || !car.get().isAvailable()) {
            throw new ApiRequestException("Car is not available for rental",HttpStatus.CONFLICT);
        }

        reservation.setStatus(ReservationStatus.PENDING);
        return reservationRepository.save(reservation);
    }

    /**
     * Confirms a PENDING reservation and starts location tracking
     *
     * @param reservationId the ID of the reservation to confirm
     * @return the confirmed reservation
     * @throws ApiRequestException if reservation not found or not in PENDING state
     */
    @Override
    public Reservation confirmReservation(String reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new ApiRequestException("Reservation not found",HttpStatus.NOT_FOUND));

        if (reservation.getStatus() != ReservationStatus.PENDING) {
            throw new ApiRequestException("Only PENDING reservations can be confirmed",HttpStatus.CONFLICT);
        }

        reservation.setStatus(ReservationStatus.CONFIRMED);
        Reservation confirmedReservation = reservationRepository.save(reservation);

        // Mark car as unavailable
        Optional<Car> car = carRepository.findById(reservation.getCarId());
        if (car.isEmpty())
            throw new ApiRequestException("Car is not found.",HttpStatus.NOT_FOUND);

        car.get().setAvailable(false);
        carRepository.save(car.get());

        // Start sending location updates
        //option 1
        defaultExecutorService.execute(() -> startLocationUpdates(confirmedReservation));
        //option 2
//        customExecutorService.execute(() -> startLocationUpdates(confirmedReservation));

        return confirmedReservation;
    }

    /**
     * Starts a timer task to send location updates every second for an active reservation
     *
     * @param reservation the confirmed reservation to track
     */
    private void startLocationUpdates(Reservation reservation) {

        while (LocalDateTime.now().isBefore(reservation.getEndTime())){

            // Simulate getting car location (in real app, this would come from GPS)
            LocationEvent event = LocationEvent.builder()
                    .carId(reservation.getCarId())
                    .reservationId(reservation.getId())
                    .latitude(Math.random() * 180 - 90)
                    .longitude(Math.random() * 360 - 180)
                    .timestamp(LocalDateTime.now()).build();

            locationProducer.sendLocationEvent(event);
        }

        // Update reservation status
        reservation.setStatus(ReservationStatus.COMPLETED);
        reservationRepository.save(reservation);

        // Mark car as available again
        Optional<Car> car = carRepository.findById(reservation.getCarId());
        if (car.isEmpty())
            throw new ApiRequestException("Car not found.",HttpStatus.NOT_FOUND);
        car.get().setAvailable(true);
        carRepository.save(car.get());

    }

    /**
     * Retrieves all reservations for a specific driver
     *
     * @param driverId the ID of the driver
     * @return list of reservations for the driver
     */
    @Override
    public List<Reservation> getReservationsByDriver(String driverId) {
        return reservationRepository.findByDriverId(driverId);
    }
}