package com.lidosta.Deikstras_Lidosta.processor.calculator;

import com.lidosta.Deikstras_Lidosta.processor.calculator.response.FlightsInternalInfo;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

/**
 * This class is used to find all possible ways to reach target airport.
 */
public class AllPathsAlgorithm {
    private List<FlightsInternalInfo>[][] flightGraph;

    public List<FlightsInternalInfo>[][] getAllFlights(List<FlightsInternalInfo>[][] graph) {
        flightGraph = new List[graph.length][graph.length];
        for (int i = 0; i < graph.length; i++) {
            for (int j = 0; j < graph.length; j++) {
                flightGraph[i][j] = new ArrayList<>();
            }
        }
        for (int i = 0; i < graph.length; i++) {
            boolean[] isVisited = new boolean[graph.length];
            for (int j = 0; j < graph.length; j++) {
                if (i != j) {
                    List<UUID> localPathList = new ArrayList<>();
                    int distance = 0;
                    float price = 0f;
                    getAllPaths(i, j, graph, isVisited, localPathList, distance, i, price, i, j);
                }
            }
        }
        for (int i = 0; i < graph.length; i++) {
            for (int j = 0; j < graph.length; j++) {
                flightGraph[i][j].sort(Comparator.comparing(FlightsInternalInfo::getDistance));
            }
        }
        return flightGraph;
    }

    public void getAllPaths(int s,
                            int targetIndex,
                            List<FlightsInternalInfo>[][] graph,
                            boolean[] isVisited,
                            List<UUID> localPathList,
                            int distance,
                            int rowIndex,
                            float price,
                            int previousX, int previousY) {
        if (s != rowIndex && graph[s][targetIndex].size() > 0) {
            localPathList.add(graph[previousX][previousY].get(0).getFlightId());
            System.out.println();
        }
        if (s == targetIndex) {
            List<UUID> copiedPathList = new ArrayList<>(localPathList);
            FlightsInternalInfo info = new FlightsInternalInfo(null, price, distance, copiedPathList);
            flightGraph[rowIndex][s].add(info);

            return;
        }
        isVisited[s] = true;
        for (int i = 0; i < graph.length; i++) {
            if (!isVisited[i] && graph[s][i].size() > 0 && graph[s][i].get(0).getDistance() != 0 && s != i) {

                int updatedDistance = distance + graph[s][i].get(0).getDistance();
                float updatedPrice = price + graph[s][i].get(0).getPrice();
                getAllPaths(i, targetIndex, graph, isVisited, localPathList, updatedDistance, rowIndex, updatedPrice, s, i);
                if (localPathList.size() > 0) {
                    localPathList.remove(localPathList.size() - 1);
                }
            }
        }

        isVisited[s] = false;
    }
}
