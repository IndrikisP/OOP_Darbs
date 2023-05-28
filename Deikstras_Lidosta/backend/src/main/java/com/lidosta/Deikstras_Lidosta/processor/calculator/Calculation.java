package com.lidosta.Deikstras_Lidosta.processor.calculator;

import com.lidosta.Deikstras_Lidosta.processor.calculator.response.FlightsInfo;
import com.lidosta.Deikstras_Lidosta.processor.calculator.response.PriceDistanceInfo;
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
    private static List<PriceDistanceInfo>[][] shortestDistance = null;
    private static List<PriceDistanceInfo>[][] allPaths = null;
    int verticleCount;
    UUID[] verticles = new UUID[size];
    private static final List<PriceDistanceInfo>[][] pDInfo = new List[size][size];

    public static Calculation getInstance() {
        if (calculation == null) {
            calculation = new Calculation();
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    pDInfo[i][j] = new ArrayList<PriceDistanceInfo>();
                    System.out.println("pDInfo[i][j] " + pDInfo[i][j]);
                }
            }
        }

        return calculation;
    }

    UUID[] getVerticles() {
        return verticles;
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

    boolean addAirportEdge(UUID airportFromId, UUID airportToId, PriceDistanceInfo priceDistanceInfo) {
        int v1pos = -1, v2pos = -1;
        for (int i = 0; i < verticleCount; i++) {
            if (airportFromId.equals(verticles[i]))
                v1pos = i;
            if (airportToId.equals(verticles[i]))
                v2pos = i;
        }
        if (v1pos != -1 && v2pos != -1) {
            pDInfo[v1pos][v2pos].add(priceDistanceInfo);
            //todo set airport
            pDInfo[v1pos][v1pos].add(new PriceDistanceInfo(priceDistanceInfo.getFlightId(), 0f, priceDistanceInfo.getDistance()));
            // pDInfo[v2pos][v2pos].add(new PriceDistanceInfo(priceDistanceInfo.getFlightId(),0f,priceDistanceInfo.getDistance(), null));

            return true;
        }
        return false;
    }

    // Print adjacency list representation ot graph
    public void printGraph() {
        UUID nosaukums = null;
        for (int u = 0; u < size - 1; u++) {
            StringBuilder sb = new StringBuilder();
            sb.append("No lidostas ").append(verticles[u]).append(" var nokļūt uz:\n");
            for (int j = 0; j < size - 1; j++) {
                for (int i = 0; i < verticleCount - 1; i++) {
                    if (i == j) {
                        nosaukums = verticles[i];
                        break;
                    } else
                        nosaukums = verticles[i];
                }
                if (nosaukums == null) {
                    return;
                }
                if (!pDInfo[u][j].isEmpty()) {
                    sb.append("\tlidostu ").append(nosaukums).append(" par\n");
                    for (PriceDistanceInfo info : pDInfo[u][j]) {
                        if (info.getDistance() > 0) {
                            sb.append("id: ")
                                    .append("->")
                                    .append(" | ")
                                    .append(info.getPrice())
                                    .append(" EUR ")
                                    .append(info.getDistance())
                                    .append(" KM\n");
                        }
                    }
                    System.out.println(sb);
                    sb = new StringBuilder();
                }
            }
        }
    }

    public void addRecordToGraph(UUID airportFrom, UUID airportTo, PriceDistanceInfo info) {
        addAirport(airportFrom);
        addAirport(airportTo);
        addAirportEdge(airportFrom, airportTo, info);
    }

    public void calculateShortestPathGraph() {
        DijkstraAlgorithm algorithm = new DijkstraAlgorithm();
        shortestDistance = pDInfo;
        for (int i = 0; i < pDInfo.length; i++) {
            shortestDistance = algorithm.dijkstra(shortestDistance, i);
        }
        printShortestGraph();
    }

    public void calculateAllPaths() {
        AllPathsAlgorithm allPathsAlgorithm = new AllPathsAlgorithm();
        allPaths = allPathsAlgorithm.getAllFlights(pDInfo);
    }

    public void printShortestGraph() {
        for (int i = 0; i < shortestDistance.length; i++) {
            for (int j = 0; j < shortestDistance.length; j++) {
                if (shortestDistance[i][j].size() > 0) {
                    System.out.print(shortestDistance[i][j].get(0).getDistance() + " ");
                    System.out.println(shortestDistance[i][j].get(0).getPath());
                }
            }
            System.out.println();
        }
    }

    public List<UUID> getShortestDistanceFromTo(UUID from, UUID to) {
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
        return shortestDistance[x][y].size() > 0 ? shortestDistance[x][y].get(0).getPath() : null;
    }

    public List<FlightsInfo> getAllPathsFromTo(UUID from, UUID to, String parameterName, int parameter) {
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
        List<FlightsInfo> paths = new ArrayList<>();

        switch (parameterName) {
            case "distance":
                if (allPaths[x][y].size() > 0) {
                    allPaths[x][y].forEach(info -> {
                        if (info.getDistance() <= parameter) {
                            paths.add(new FlightsInfo(info.getDistance(),info.getPrice(),info.getPath(),null));
                        }
                    });
                }
                return paths;
            case "price":
                if (allPaths[x][y].size() > 0) {
                    allPaths[x][y].forEach(info -> {
                        if (info.getPrice() <= parameter) {
                            paths.add(new FlightsInfo(info.getDistance(),info.getPrice(),info.getPath(),null));
                        }
                    });
                }
                return paths;
            case "stop":
                if (allPaths[x][y].size() > 0) {
                    allPaths[x][y].forEach(info -> {
                        if (info.getPath().size() <= parameter) {
                            paths.add(new FlightsInfo(info.getDistance(),info.getPrice(),info.getPath(),null));
                        }
                    });
                }
                return paths;
        }
        return null;
    }
}
