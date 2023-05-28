package com.lidosta.Deikstras_Lidosta.service;

import com.lidosta.Deikstras_Lidosta.dao.Dao;
import com.lidosta.Deikstras_Lidosta.dao.FlightAccessService;
import com.lidosta.Deikstras_Lidosta.model.Flight;
import com.lidosta.Deikstras_Lidosta.processor.calculator.Calculation;
import com.lidosta.Deikstras_Lidosta.processor.calculator.response.FlightsExternalInfo;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class FlightService {
    private final Dao dao;
    public static int count = 0;
    private final FlightAccessService flightAccessService;

    public FlightService(@Qualifier("postgres_flight") Dao dao, FlightAccessService flightAccessService) {
        this.dao = dao;
        this.flightAccessService = flightAccessService;
    }

    public Flight addFlight(Flight flight) {
        Flight tmpFlight = checkIfFlightExist(flight);
        if (tmpFlight != null) {
            return tmpFlight;
        }
        flight.setFlightId(UUID.randomUUID());
        return (Flight) dao.insert(flight);
    }

    public List<Flight> getAllFlights() {
        return dao.selectAll();
    }

    private Flight checkIfFlightExist(Flight flight) {
        return (Flight) dao.checkIfExist(flight);
    }


    @Cacheable(value = "flightInput", key = "#flightFrom.toString() + '_' + #flightTo.toString() + '_' + #name + '_' + #parameter")
    public List<FlightsExternalInfo> getAllPaths(UUID flightFrom, UUID flightTo, String name, int parameter) {
        System.out.println(count++);
        return flightAccessService.selectPaths(Calculation.getInstance().getAllPathsFromTo(flightFrom, flightTo, name, parameter));
    }

}
