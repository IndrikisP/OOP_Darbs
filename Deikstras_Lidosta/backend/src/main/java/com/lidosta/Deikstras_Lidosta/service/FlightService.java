package com.lidosta.Deikstras_Lidosta.service;

import com.lidosta.Deikstras_Lidosta.dao.Dao;
import com.lidosta.Deikstras_Lidosta.model.Flight;
import com.lidosta.Deikstras_Lidosta.processor.calculator.Calculation;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class FlightService {
    private final Dao dao;

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

    public List<Flight> getAllFlights(){
        return dao.selectAll();
    }
    private Flight checkIfFlightExist(Flight flight) {
        return (Flight) dao.checkIfExist(flight);
    }

    public List<Flight> getShortestFlight(UUID flightFrom, UUID flightTo) {
        return (List<Flight>) dao.selectByIds(Calculation.getInstance().getShortestDistanceFromTo(flightFrom, flightTo));
    }

    public List<List<Flight>> getAllPaths(UUID flightFrom, UUID flightTo) {
        return (List<List<Flight>>) dao.selectPaths(Calculation.getInstance().getAllPathsFromTo(flightFrom, flightTo));
    }
}
