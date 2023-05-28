package com.lidosta.Deikstras_Lidosta.processor.calculator;

import com.lidosta.Deikstras_Lidosta.processor.calculator.response.FlightsExternalInfo;
import com.lidosta.Deikstras_Lidosta.processor.calculator.response.FlightsInternalInfo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * This class is to provide calculation logic.
 */
@Service
public class Calculation {
    private static int size = 20;
    private static Calculation calculation = null;
    private static List<FlightsInternalInfo>[][] allPaths = null;
    int verticleCount;
    UUID[] verticles = new UUID[size];
    private static final List<FlightsInternalInfo>[][] pDInfo = new List[size][size];

    public static Calculation getInstance() {
        if (calculation == null) {
            calculation = new Calculation();
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    pDInfo[i][j] = new ArrayList<FlightsInternalInfo>();
                    System.out.println("pDInfo[i][j] " + pDInfo[i][j]);
                }
            }
        }

        return calculation;
    }

    boolean addAirport(UUID airportId) {
        for (int i = 0; i < verticleCount; i++) {
            if (verticles[i].equals(airportId)) {
                return false;
            }
        }
        if (verticleCount == size) {
            return false;
        }
        verticles[verticleCount++] = airportId;
        return true;
    }

    boolean addAirportEdge(UUID airportFromId, UUID airportToId, FlightsInternalInfo flightsInternalInfo) {
        int v1pos = -1, v2pos = -1;
        for (int i = 0; i < verticleCount; i++) {
            if (airportFromId.equals(verticles[i]))
                v1pos = i;
            if (airportToId.equals(verticles[i]))
                v2pos = i;
        }
        if (v1pos != -1 && v2pos != -1) {
            pDInfo[v1pos][v2pos].add(flightsInternalInfo);
            pDInfo[v1pos][v1pos].add(new FlightsInternalInfo(flightsInternalInfo.getFlightId(), 0f, flightsInternalInfo.getDistance()));
            return true;
        }
        return false;
    }

    public void addRecordToGraph(UUID airportFrom, UUID airportTo, FlightsInternalInfo info) {
        addAirport(airportFrom);
        addAirport(airportTo);
        addAirportEdge(airportFrom, airportTo, info);
    }

    public void calculateAllPaths() {
        AllPathsAlgorithm allPathsAlgorithm = new AllPathsAlgorithm();
        allPaths = allPathsAlgorithm.getAllFlights(pDInfo);
    }


    public List<FlightsExternalInfo> getAllPathsFromTo(UUID from, UUID to, String parameterName, int parameter) {
        int x = 0;
        int y = 0;
        for (int i = 0; i < verticleCount; i++) {
            if (verticles[i].equals(from)) {
                x = i;
            }
            if (verticles[i].equals(to)) {
                y = i;
            }
        }
        List<FlightsExternalInfo> paths = new ArrayList<>();

        switch (parameterName) {
            case "distance":
                if (allPaths[x][y].size() > 0) {
                    allPaths[x][y].forEach(info -> {
                        if (info.getDistance() <= parameter) {
                            paths.add(new FlightsExternalInfo(info.getDistance(), info.getPrice(), info.getPath(), null));
                        }
                    });
                }
                return paths;
            case "price":
                if (allPaths[x][y].size() > 0) {
                    allPaths[x][y].forEach(info -> {
                        if (info.getPrice() <= parameter) {
                            paths.add(new FlightsExternalInfo(info.getDistance(), info.getPrice(), info.getPath(), null));
                        }
                    });
                }
                return paths;
            case "stop":
                if (allPaths[x][y].size() > 0) {
                    allPaths[x][y].forEach(info -> {
                        if (info.getPath().size() <= parameter) {
                            paths.add(new FlightsExternalInfo(info.getDistance(), info.getPrice(), info.getPath(), null));
                        }
                    });
                }
                return paths;
        }
        return null;
    }
}
