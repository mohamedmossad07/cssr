package org.exalt.cssr.reservations;

import org.springframework.data.aerospike.repository.AerospikeRepository;

import java.util.List;

public interface ReservationRepository extends AerospikeRepository<Reservation, String> {
    List<Reservation> findByDriverId(String driverId);
}