package org.exalt.cssr.cars;


import org.springframework.data.aerospike.repository.AerospikeRepository;

import java.util.List;

public interface CarRepository extends AerospikeRepository<Car, String> {
    /**
     * Find All cars belongs to an owner
     * @param ownerId owner id
     * @return cars belongs to the owner
     */
    List<Car> findByOwnerId(String ownerId);

    /**
     * find all available cars
     * @param available status
     * @return available cars
     */
    List<Car> findByAvailable(boolean available);
}