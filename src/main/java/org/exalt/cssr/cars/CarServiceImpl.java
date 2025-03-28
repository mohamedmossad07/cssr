package org.exalt.cssr.cars;


import lombok.RequiredArgsConstructor;
import org.exalt.cssr.exceptions.ApiRequestException;
import org.exalt.cssr.users.User;
import org.exalt.cssr.users.UserService;
import org.exalt.cssr.users.UserType;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service implementation for car management operations
 */
@Service
@RequiredArgsConstructor
public class CarServiceImpl implements CarService {
    private final CarRepository carRepository;
    private final UserService userService;

    /**
     * {@inheritDoc}
     *
     * @throws ApiRequestException if owner is not valid
     */
    @Override
    public Optional<Car> addCar(Car car) {
        // Validate owner exists and is actually an owner
        Optional<User> owner = userService.findUserById(car.getOwnerId());
        if (owner.isPresent() && owner.get().getType() != UserType.OWNER) {
            throw new ApiRequestException("Only owners can add cars", HttpStatus.FORBIDDEN);
        }
        car.setAvailable(true);
        return Optional.of(carRepository.save(car));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Car> getCarsByOwner(String ownerId) {
        return carRepository.findByOwnerId(ownerId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Car> getAvailableCars() {
        return carRepository.findByAvailable(true);
    }
}