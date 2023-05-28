package com.lidosta.Deikstras_Lidosta.service;

import com.lidosta.Deikstras_Lidosta.dao.Dao;
import com.lidosta.Deikstras_Lidosta.model.Flight;
import com.lidosta.Deikstras_Lidosta.processor.calculator.Calculation;
import com.lidosta.Deikstras_Lidosta.processor.calculator.response.FlightsInfo;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class FlightService {
    private final Dao dao;
    public static int count = 0;

    public FlightService(@Qualifier("postgres_flight") Dao dao) {
        this.dao = dao;
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

    public List<Flight> getShortestFlight(UUID flightFrom, UUID flightTo) {
        return (List<Flight>) dao.selectByIds(Calculation.getInstance().getShortestDistanceFromTo(flightFrom, flightTo));
    }

    @Cacheable(value = "flightInput", key = "#flightFrom.toString() + '_' + #flightTo.toString() + '_' + #name + '_' + #parameter")
    public List<FlightsInfo> getAllPaths(UUID flightFrom, UUID flightTo, String name, int parameter) {
        System.out.println(count++);
        return (List<FlightsInfo>) dao.selectPaths2(Calculation.getInstance().getAllPathsFromTo(flightFrom, flightTo, name, parameter));
    }

}
