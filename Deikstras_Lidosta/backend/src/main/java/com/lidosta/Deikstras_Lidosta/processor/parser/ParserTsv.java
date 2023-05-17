package com.lidosta.Deikstras_Lidosta.processor.parser;

import com.lidosta.Deikstras_Lidosta.model.Airplane;
import com.lidosta.Deikstras_Lidosta.model.Airport;
import com.lidosta.Deikstras_Lidosta.model.Flight;
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
import java.util.UUID;

public class ParserTsv {
    public MultiClassRepository parse(File file,
                                      AirportService airportService,
                                      AirplaneService airplaneService,
                                      FlightService flightService) {
        List<Airplane> airplanes = new ArrayList<>();
        List<Flight> flights = new ArrayList<>();
        List<Airport> airports = new ArrayList<>();
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
        //14 flighttimezone,flightcompany
        String pattern = "dd-MM-yyyy";

        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
        try (BufferedReader TSVReader = new BufferedReader(new FileReader(file))) {
            String line = null;
            while ((line = TSVReader.readLine()) != null) {
                String[] row = line.split("\t");
                Airplane tmpAirplane = getPlane(new Airplane(row[0],row[1],Integer.parseInt(row[2])), airplaneService);
                Airport tmpAirportFrom = getAirport(new Airport(row[5], row[3], row[4]),airportService);
                Airport tmpAirportTo = getAirport(new Airport(row[8], row[6], row[7]),airportService);
                Date dateArrival = dateFormat.parse(row[12]);
                Date dateDeparture = dateFormat.parse(row[13]);
                Flight flight = getFlight(new Flight(tmpAirportFrom.getAirportId(),
                        tmpAirportTo.getAirportId(),
                        Integer.parseInt(row[10]),
                        Float.parseFloat(row[11]),
                        dateArrival, dateDeparture,
                        row[14],
                        tmpAirplane.getAirplaneId(),
                        row[15]),flightService);
                airplanes.add(tmpAirplane);
                airports.add(tmpAirportTo);
                airports.add(tmpAirportFrom);
                flights.add(flight);
            }
        } catch (Exception e) {
            System.out.println("Something went wrong");
        }
        return new MultiClassRepository(airplanes,flights,airports);
    }

    private Airplane getPlane(Airplane airplane, AirplaneService service) {
        return service.addAirplane(airplane);
    }
    private Airport getAirport(Airport airport, AirportService service) {
        return service.addAirport(airport);
    }
    private Flight getFlight(Flight flight, FlightService service) {
        return service.addFlight(flight);
    }
}
