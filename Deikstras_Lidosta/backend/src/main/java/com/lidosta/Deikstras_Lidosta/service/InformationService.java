package com.lidosta.Deikstras_Lidosta.service;

import com.lidosta.Deikstras_Lidosta.processor.calculator.Calculation;
import com.lidosta.Deikstras_Lidosta.processor.parser.ParserTsv;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class InformationService {
    private AirplaneService airplaneService;
    private FlightService flightService;
    private AirportService airportService;

    public InformationService(AirplaneService airplaneService,
                              FlightService flightService,
                              AirportService airportService) {
        this.airplaneService = airplaneService;
        this.flightService = flightService;
        this.airportService = airportService;
    }

    public void uploadInformation(File file) {
        ParserTsv parserTsv = new ParserTsv();
        parserTsv.parse(file, airportService, airplaneService, flightService);
        Calculation.getInstance().calculateAllPaths();
    }
}
