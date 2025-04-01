package org.exalt.cssr.cars;

import org.exalt.cssr.users.User;
import org.exalt.cssr.users.UserService;
import org.exalt.cssr.users.UserType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CarServiceImplTest {

    private final Car sampleCar = Car.builder().id("1000").year(2020).make("Toyota").ownerId("1").hourly_rate(50.5).model("Alentra").available(true).build();
    private final User carOwner = User.builder().id("1").username("Mohamed mossad").email("test@test.test").phone("01061218341").type(UserType.OWNER).build();

    @Mock
    private UserService userService;

    @Mock
    private CarRepository carRepository;

    @InjectMocks
    private CarServiceImpl carService;

    @Test
    void should_insert_new_car_to_db(){
        when(userService.findUserById(sampleCar.getOwnerId())).thenReturn(Optional.of(carOwner));
        when(carRepository.save(sampleCar)).thenReturn(sampleCar);
        Optional<Car> addedCar = carService.addCar(sampleCar);
        assertTrue(addedCar.isPresent());
        assertEquals(sampleCar.getId(),addedCar.get().getId());
        assertEquals(sampleCar.getOwnerId(),addedCar.get().getOwnerId());
        assertEquals(sampleCar.getMake(),addedCar.get().getMake());
        assertEquals(sampleCar.getModel(),addedCar.get().getModel());
        assertEquals(sampleCar.getHourly_rate(),addedCar.get().getHourly_rate());
    }
    @Test
    void should_returns_list_of_cars_to_user(){
        when(carRepository.findByOwnerId(sampleCar.getOwnerId())).thenReturn(List.of(sampleCar));
        List<Car> carsByOwner = carService.getCarsByOwner(sampleCar.getOwnerId());
        assertThat(carsByOwner).hasSize(1);
    }
    @Test
    void should_return_list_of_available_cars(){
        when(carRepository.findByAvailable(true)).thenReturn(List.of(sampleCar));
        List<Car> cars = carService.getAvailableCars();
        assertThat(cars).hasSize(1);
    }
}