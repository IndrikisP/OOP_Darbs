package com.lidosta.Deikstras_Lidosta.service;

import com.lidosta.Deikstras_Lidosta.dao.Dao;
import com.lidosta.Deikstras_Lidosta.model.Airplane;
import com.lidosta.Deikstras_Lidosta.model.Flight;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
public class AirplaneService {
    private final Dao dao;

    public AirplaneService(@Qualifier("postgres_airplane") Dao dao) {
        this.dao = dao;
    }

    public Airplane addAirplane(Airplane airplane) {
        Airplane tmpAirplane = checkIfFlightExist(airplane);
        if (tmpAirplane != null) {
            return tmpAirplane;
        }
        Airplane airplaneDB = new Airplane(UUID.randomUUID(),
                airplane.getModel(),
                airplane.getType(),
                airplane.getPassengerCount()
        );
        return (Airplane) dao.insert(airplaneDB);

    }

    private Airplane checkIfFlightExist(Airplane airplane) {
        return (Airplane) dao.checkIfExist(airplane);
    }
}
