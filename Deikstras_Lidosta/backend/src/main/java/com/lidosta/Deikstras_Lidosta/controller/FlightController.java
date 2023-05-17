package com.lidosta.Deikstras_Lidosta.controller;

import com.lidosta.Deikstras_Lidosta.model.Flight;
import com.lidosta.Deikstras_Lidosta.service.FlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.UUID;

@RestController
public class FlightController {
    @Autowired
    FlightService flightService;

    public FlightController(FlightService flightService) {
        this.flightService = flightService;
    }

    @PostMapping(value = "/add/flights")
    public Flight addFlight(@RequestParam("timezone") String timezone,
                            @RequestParam("company") String company) {
        Flight flight = new Flight();
        flight.setTimeZone(timezone);
        flight.setCompany(company);
        return flightService.addFlight(flight);

    }


}
