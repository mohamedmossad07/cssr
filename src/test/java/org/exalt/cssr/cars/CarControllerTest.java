package org.exalt.cssr.cars;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(CarController.class)
class CarControllerTest {
    private final Car sampleCar = Car.builder().id("1000").year(2020).make("Toyota").ownerId("1").hourly_rate(50.5).model("Alentra").available(true).build();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    MockMvc mockMvc;

    @MockitoBean
    CarServiceImpl carService;

    @Test
    void should_add_car_when_pass_car_details() throws Exception {
        when(carService.addCar(sampleCar)).thenReturn(Optional.of(sampleCar));
        mockMvc.perform(post("/api/cars").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(sampleCar)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.data.id").value(sampleCar.getId()));

    }

    @Test
    void should_return_status_ok_when_getting_cars_owned_by_user_id() throws Exception {
        when(carService.getCarsByOwner(sampleCar.getOwnerId())).thenReturn(List.of(sampleCar));
        mockMvc.perform(get("/api/cars/owner/"+sampleCar.getOwnerId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data").isNotEmpty());

    }

    @Test
    void should_returns_list_of_available_cars() throws Exception {
        when(carService.getAvailableCars()).thenReturn(List.of(sampleCar));
        mockMvc.perform(get("/api/cars/available"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data").isNotEmpty());
    }

}