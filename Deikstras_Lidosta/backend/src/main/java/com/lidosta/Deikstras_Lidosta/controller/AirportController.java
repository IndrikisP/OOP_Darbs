package com.lidosta.Deikstras_Lidosta.controller;

import com.lidosta.Deikstras_Lidosta.model.Airport;
import com.lidosta.Deikstras_Lidosta.service.AirportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AirportController {
    @Autowired
    AirportService airportService;

    public AirportController(AirportService airportService) {
        this.airportService = airportService;
    }

    @GetMapping(value = "/get/allAirports")
    public List<Airport> getAirports() {
        return airportService.getAllAirports();
    }
}
