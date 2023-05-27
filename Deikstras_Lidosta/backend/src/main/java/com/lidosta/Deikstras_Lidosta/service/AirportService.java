package com.lidosta.Deikstras_Lidosta.service;

import com.lidosta.Deikstras_Lidosta.dao.Dao;
import com.lidosta.Deikstras_Lidosta.model.Airport;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
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
        airport.setAirportId(UUID.randomUUID());
        return (Airport) dao.insert(airport);

    }

    private Airport checkIfFlightExist(Airport airport) {
        return (Airport) dao.checkIfExist(airport);
    }

    public List<Airport> getAllAirports(){
        return dao.selectAll();
    }
}
