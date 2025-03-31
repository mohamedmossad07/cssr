package org.exalt.cssr.cars;

import java.util.List;
import java.util.Optional;

public interface CarService {
    Optional<Car> addCar(Car car);

    List<Car> getCarsByOwner(String ownerId);

    List<Car> getAvailableCars();
}
