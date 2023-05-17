package com.lidosta.Deikstras_Lidosta.service;

import com.lidosta.Deikstras_Lidosta.dao.Dao;
import com.lidosta.Deikstras_Lidosta.model.Flight;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

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

    private Flight checkIfFlightExist(Flight flight) {
        return (Flight) dao.checkIfExist(flight);
    }
}
