package com.lidosta.Deikstras_Lidosta.processor.parser;

import com.lidosta.Deikstras_Lidosta.model.Airplane;
import com.lidosta.Deikstras_Lidosta.model.Airport;
import com.lidosta.Deikstras_Lidosta.model.Flight;
import com.lidosta.Deikstras_Lidosta.processor.calculator.Calculation;
import com.lidosta.Deikstras_Lidosta.processor.calculator.PriceDistanceInfo;
import com.lidosta.Deikstras_Lidosta.service.AirplaneService;
import com.lidosta.Deikstras_Lidosta.service.AirportService;
import com.lidosta.Deikstras_Lidosta.service.FlightService;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This class is to parse tsv document and put data into DB.
 * uses calculation class to create graph
 */

public class ParserTsv {

    static int rowCount = 0;

    public void parse(File file,
                      AirportService airportService,
                      AirplaneService airplaneService,
                      FlightService flightService) {
        Calculation calculation = Calculation.getInstance();
        //1 airplanetype,
        //2 airplaneModel
        //3 airplanecapacity,

        //3 airportfromcode,
        //4 airportfromcity_name,
        //5 airportfromname,

        //7 airporttocode,
        //8 airporttocity_name,
        //9 airporttoname,

        //10 flightdistance,
        //11 flightprice,
        //12 flighttime_of_arrival,
        //13 flighttime_of_departure,
        //14 flighttimezone,
        //15 flightcompany
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
                PriceDistanceInfo info = new PriceDistanceInfo(tmpFlight.getFlightId(), price, distance);
                calculation.addRecordToGraph(tmpAirportFrom.getAirportId(), tmpAirportTo.getAirportId(), info);
                rowCount++;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        System.out.println("Sum of not unique row count: " + rowCount);
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
