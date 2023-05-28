package com.lidosta.Deikstras_Lidosta.processor.parser;

import com.lidosta.Deikstras_Lidosta.model.Airplane;
import com.lidosta.Deikstras_Lidosta.model.Airport;
import com.lidosta.Deikstras_Lidosta.model.Flight;
import com.lidosta.Deikstras_Lidosta.processor.calculator.Calculation;
import com.lidosta.Deikstras_Lidosta.processor.calculator.response.FlightsInternalInfo;
import com.lidosta.Deikstras_Lidosta.service.AirplaneService;
import com.lidosta.Deikstras_Lidosta.service.AirportService;
import com.lidosta.Deikstras_Lidosta.service.FlightService;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * This class is to parse tsv document and put data into DB.
 * uses calculation class to create graph
 */

public class ParserTsv {

    public void parse(File file,
                      AirportService airportService,
                      AirplaneService airplaneService,
                      FlightService flightService) {
        Calculation calculation = Calculation.getInstance();
        final String pattern = "yyyy-mm-dd";
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
        try (BufferedReader TSVReader = new BufferedReader(new FileReader(file))) {
            String line = null;
            while ((line = TSVReader.readLine()) != null) {
                String[] row = line.split("\t");
                Airplane tmpAirplane = getPlane(new Airplane(row[0], row[1], Integer.parseInt(row[2])), airplaneService);
                Airport tmpAirportFrom = getAirport(new Airport(row[5], row[3], row[4]), airportService);
                Airport tmpAirportTo = getAirport(new Airport(row[8], row[6], row[7]), airportService);
                Date dateArrival = dateFormat.parse(row[11]);
                Date dateDeparture = dateFormat.parse(row[12]);
                float price = Float.parseFloat(row[10]);
                int distance = Integer.parseInt(row[9]);
                Flight tmpFlight = getFlight(new Flight(tmpAirportFrom.getAirportId(),
                        tmpAirportTo.getAirportId(),
                        distance,
                        price,
                        dateArrival, dateDeparture,
                        row[13],
                        tmpAirplane.getAirplaneId(),
                        row[14]), flightService);
                FlightsInternalInfo info = new FlightsInternalInfo(tmpFlight.getFlightId(), price, distance);
                calculation.addRecordToGraph(tmpAirportFrom.getAirportId(), tmpAirportTo.getAirportId(), info);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public Airplane getPlane(Airplane airplane, AirplaneService airplaneService) {
        return airplaneService.addAirplane(airplane);
    }

    public Airport getAirport(Airport airport, AirportService airportService) {
        return airportService.addAirport(airport);
    }

    public Flight getFlight(Flight flight, FlightService flightService) {
        return flightService.addFlight(flight);
    }

}
