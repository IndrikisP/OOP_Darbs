package com.lidosta.Deikstras_Lidosta.service;

import com.lidosta.Deikstras_Lidosta.dao.Dao;
import com.lidosta.Deikstras_Lidosta.model.Airplane;
import com.lidosta.Deikstras_Lidosta.model.Airport;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AirportService {

    private final Dao dao;

    public AirportService(@Qualifier("postgres_airport") Dao dao) {
        this.dao = dao;
    }

    public Airport addAirport(Airport airport) {
        Airport tmpAirplane = checkIfFlightExist(airport);
        if (tmpAirplane != null) {
            return tmpAirplane;
        }
        Airport airportDB = new Airport(UUID.randomUUID(),
                airport.getName(),
                airport.getCode(),
                airport.getCityName()
        );
        return (Airport) dao.insert(airportDB);

    }

    private Airport checkIfFlightExist(Airport airport) {
        return (Airport) dao.checkIfExist(airport);
    }
}
