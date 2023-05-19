package com.lidosta.Deikstras_Lidosta.processor.calculator;

import java.util.*;

public class DijkstraAlgorithm {
    public static List<PriceDistanceInfo>[][] dijkstra(List<PriceDistanceInfo>[][] pDInfoPrice) {
        int numRows = pDInfoPrice.length;
        int numCols = pDInfoPrice[0].length;

        List<PriceDistanceInfo>[][] pDInfonew = new List[numRows][numCols];
        int[][] distances = new int[numRows][numCols];

        // Initialize distances with infinity for all vertices
        for (int i = 0; i < numRows; i++) {
            Arrays.fill(distances[i], Integer.MAX_VALUE);
        }

        // Start from the top-left corner
        int startRow = 0;
        int startCol = 0;
        distances[startRow][startCol] = 0;

        // Dijkstra's algorithm
        while (true) {
            int minDistance = Integer.MAX_VALUE;
            int currentRow = -1;
            int currentCol = -1;

            // Find the cell with the minimum distance among the unvisited cells
            for (int row = 0; row < numRows; row++) {
                for (int col = 0; col < numCols; col++) {
                    if (distances[row][col] < minDistance && !isVisited(row, col, pDInfonew)) {
                        minDistance = distances[row][col];
                        currentRow = row;
                        currentCol = col;
                    }
                }
            }

            // If all cells have been visited or the minimum distance is infinity, exit the loop
            if (currentRow == -1 || minDistance == Integer.MAX_VALUE) {
                break;
            }

            // Mark the current cell as visited
            markVisited(currentRow, currentCol, pDInfonew);

            // Update distances of neighboring unvisited cells
            updateDistances(currentRow, currentCol, distances, pDInfoPrice, pDInfonew);
        }

        return pDInfonew;
    }

    private static boolean isVisited(int row, int col, List<PriceDistanceInfo>[][] pDInfonew) {
        return pDInfonew[row][col] != null;
    }

    private static void markVisited(int row, int col, List<PriceDistanceInfo>[][] pDInfonew) {
        pDInfonew[row][col] = new ArrayList<>();
    }

    private static void updateDistances(int row, int col, int[][] distances, List<PriceDistanceInfo>[][] pDInfoPrice, List<PriceDistanceInfo>[][] pDInfonew) {
        int distance = distances[row][col];

        // Update distances of neighboring unvisited cells
        if (row - 1 >= 0 && !isVisited(row - 1, col, pDInfonew)) {
            int newDistance = distance + pDInfoPrice[row][col].get(0).getDistance();
            if (newDistance < distances[row - 1][col]) {
                distances[row - 1][col] = newDistance;
            }
        }
        if (row + 1 < pDInfonew.length && !isVisited(row + 1, col, pDInfonew)) {
            int newDistance = distance + pDInfoPrice[row][col].get(0).getDistance();
            if (newDistance < distances[row + 1][col]) {
                distances[row + 1][col] = newDistance;
            }
        }
        if (col - 1 >= 0 && !isVisited(row, col - 1, pDInfonew)) {
            int newDistance = distance + pDInfoPrice[row][col].get(0).getDistance();
            if (newDistance < distances[row][col - 1]) {
                distances[row][col - 1] = newDistance;
            }
        }
        if (col + 1 < pDInfonew[0].length && !isVisited(row, col + 1, pDInfonew)) {
            int newDistance = distance + pDInfoPrice[row][col].get(0).getDistance();
            if (newDistance < distances[row][col + 1]) {
                distances[row][col + 1] = newDistance;
            }
        }
    }
}
