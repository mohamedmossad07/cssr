package org.exalt.cssr.cars;

import org.assertj.core.api.Assertions;
import org.exalt.cssr.users.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class CarRepositoryTest {
    @Autowired
    private CarRepository carRepository;
    private final Car sampleCar = Car.builder().id("1000").year(2020).make("Toyota").ownerId("1").hourly_rate(50.5).model("Alentra").build();
    @Test
    void should_car_to_db(){
        Car savedCar = carRepository.save(sampleCar);
        assertNotNull(savedCar);
        assertEquals(sampleCar.getId(), savedCar.getId());
        assertEquals(sampleCar.getMake(), savedCar.getMake());
        assertEquals(sampleCar.getModel(), savedCar.getModel());
        assertEquals(sampleCar.getHourly_rate(), savedCar.getHourly_rate());
    }
    @Test
    void should_find_owner_list_of_cars_in_db(){
        List<Car> cars = carRepository.findByOwnerId(sampleCar.getOwnerId());
        assertNotNull(cars);
        assertThat(cars).hasSizeGreaterThanOrEqualTo(1);
    }
//    @Test
    void should_find_at_least_one_available_cars_in_db(){
        List<Car> cars = carRepository.findByAvailable(true);
        assertNotNull(cars);
        assertThat(cars).hasSizeGreaterThanOrEqualTo(1);
    }



}