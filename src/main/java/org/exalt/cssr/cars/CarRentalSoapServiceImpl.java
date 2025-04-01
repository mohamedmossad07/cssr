package org.exalt.cssr.cars;

import lombok.RequiredArgsConstructor;
import org.exalt.cssr.config.WebServiceConfig;
import org.exalt.cssr.reservations.ReservationMapper;
import org.exalt.cssr.reservations.ReservationService;
import org.reservations.cssr.*;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import java.util.List;

/**
 * Soap car rental service
 */
@RequiredArgsConstructor
@Endpoint
public class CarRentalSoapServiceImpl implements CarRentalSoapService {

    private final CarService carService;
    private final ReservationService reservationService;
    private final CarMapper carMapper;
    private final ReservationMapper reservationMapper;
    /**
     * get list of available cars
     *
     * @return GetAvailableCarsResponse list of available cars
     */
    @ResponsePayload
    @PayloadRoot(namespace = WebServiceConfig.TARGET_NAMESPACE,localPart = "getAvailableCars")
    @Override
    public GetAvailableCarsResponse getAvailableCars() {
        GetAvailableCarsResponse response = new GetAvailableCarsResponse();
        List<CarSoap> carSoapList = carService.getAvailableCars().stream()
                .map(carMapper::mapCarToCarSoap).toList();
        response.getCarSoap().addAll(carSoapList);
        return response;
    }

    /**
     * create reservation
     *
     * @param request details to bbe created
     * @return the saved reservation
     */
    @Override
    @ResponsePayload
    @PayloadRoot(namespace = WebServiceConfig.TARGET_NAMESPACE,localPart = "createReservationRequest")
    public CreateReservationResponse createReservation(@RequestPayload CreateReservationRequest request) {
        CreateReservationResponse response = new CreateReservationResponse();
        response.setReservationSoap(reservationMapper.mapReservationToReservationSoap(reservationService.createReservation(reservationMapper.mapReservationSoapToReservation(request.getReservationSoap()))));
        return response;
    }

    /**
     * confirm reservation by id
     *
     * @param request to confirm reservation
     * @return reservation status after trying to be confirmed
     */
    @ResponsePayload
    @PayloadRoot(namespace = WebServiceConfig.TARGET_NAMESPACE,localPart = "confirmReservationRequest")
    @Override
    public ConfirmReservationResponse confirmReservation(@RequestPayload ConfirmReservationRequest request) {
      ConfirmReservationResponse response  = new ConfirmReservationResponse();
      response.setConfirmed(reservationService.confirmReservation(request.getReservationId()) != null);
      return  response;
    }
}