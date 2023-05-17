package com.lidosta.Deikstras_Lidosta.service;

import com.lidosta.Deikstras_Lidosta.processor.parser.ParserTsv;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class InformationUploadService {
    private AirplaneService airplaneService;
    private FlightService flightService;
    private AirportService airportService;

    @Autowired
    public InformationUploadService(AirplaneService airplaneService,
                                    FlightService flightService,
                                    AirportService airportService) {
        this.airplaneService = airplaneService;
        this.flightService = flightService;
        this.airportService = airportService;
    }

    public void uploadInformation(File file) {
        ParserTsv parserTsv = new ParserTsv();
        parserTsv.parse(file, airportService, airplaneService, flightService);
    }
}
