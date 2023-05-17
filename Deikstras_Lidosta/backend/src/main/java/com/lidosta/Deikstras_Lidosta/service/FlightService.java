package com.lidosta.Deikstras_Lidosta.service;

import com.lidosta.Deikstras_Lidosta.dao.Dao;
import com.lidosta.Deikstras_Lidosta.model.Flight;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
public class FlightService {
    private final Dao dao;

    public FlightService(@Qualifier("postgres_flight") Dao dao) {
        this.dao = dao;
    }

    public Flight addFlight(Flight flight){
        UUID id = UUID.randomUUID();
        Flight flightDB = new Flight(UUID.randomUUID(),
                UUID.fromString("123e4567-e89b-12d3-a456-426614174000"),
                UUID.fromString("223e4567-e89b-12d3-a456-426614174000"),
                700,
                125f,
                new Date(),
                new Date(),
                flight.getTimeZone(),
                UUID.fromString("123e4567-e89b-12d3-a456-426614174001"),
                flight.getCompany()
        );
        return (Flight) dao.insert(flightDB);
    }
}
