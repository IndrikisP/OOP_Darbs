package com.lidosta.Deikstras_Lidosta.controller;

import com.lidosta.Deikstras_Lidosta.processor.calculator.response.FlightsExternalInfo;
import com.lidosta.Deikstras_Lidosta.service.FlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
public class FlightController {
    @Autowired
    FlightService flightService;

    public FlightController(FlightService flightService) {
        this.flightService = flightService;
    }

    @GetMapping(value = "/get/paths")
    public List<FlightsExternalInfo> getAllPathsFromTo(@RequestParam("cityFrom") UUID cityFromId,
                                                       @RequestParam("cityTo") UUID cityToId,
                                                       @RequestParam("filterName") String filterName,
                                                       @RequestParam("filterValue") int filterValue) {
        if(filterValue == 0) filterValue = 9999;
        return flightService.getAllPaths(cityFromId, cityToId, filterName,filterValue);
    }
}
