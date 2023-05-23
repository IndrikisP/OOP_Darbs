package com.lidosta.Deikstras_Lidosta.controller;

import com.lidosta.Deikstras_Lidosta.model.Flight;
import com.lidosta.Deikstras_Lidosta.service.FlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;
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

    @GetMapping(value = "/get/flights")
    public List<Flight> getShortestFlights(@RequestParam("fromid") UUID fromId,
                                           @RequestParam("toid") UUID toId) {
       return flightService.getShortestFlight(fromId,toId);

    }

    @GetMapping(value = "/get/paths")
    public List<List<Flight>> getAllPathsFromTo(@RequestParam("fromid") UUID fromId,
                                           @RequestParam("toid") UUID toId) {
        return flightService.getAllPaths(fromId,toId);

    }

    @GetMapping(value = "/get/allFlights")
    public List<Flight> getFlights() {
        return flightService.getAllFlights();
    }
}
