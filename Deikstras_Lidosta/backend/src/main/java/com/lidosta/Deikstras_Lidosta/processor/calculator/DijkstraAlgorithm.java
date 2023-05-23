package com.lidosta.Deikstras_Lidosta.processor.calculator;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class DijkstraAlgorithm {

    private static int INF = Integer.MAX_VALUE / 2;
    private int n;
    private ArrayList<Integer>[] adj;
    private ArrayList<Integer>[] weight;
    private boolean[] used;
    private int[] dist;
    private int[] pred;
    private int start;

    public List<PriceDistanceInfo>[][] dijkstra(List<PriceDistanceInfo>[][] exe, int s) {
        n = exe.length;
        adj = new ArrayList[n];
        weight = new ArrayList[n];
        used = new boolean[n];
        dist = new int[n];
        pred = new int[n];
        List<List<UUID>> paths = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            adj[i] = new ArrayList<>();
            weight[i] = new ArrayList<>();
            used[i] = false;
            dist[i] = INF;
            pred[i] = -1;
            paths.add(new ArrayList<>()); // Add a new path for each vertex
        }

        start = s;
        dist[start] = 0;

        for (int iter = 0; iter < n; ++iter) {
            int v = -1;
            int distV = INF;

            for (int i = 0; i < n; ++i) {
                if (!used[i] && dist[i] <= distV) {
                    v = i;
                    distV = dist[i];
                }
            }

            if (distV == INF) {
                break;
            }

            used[v] = true;

            for (int i = 0; i < n; ++i) {
                if (exe[v][i].size() > 0) {
                    int u = i;
                    int weightU = exe[v][i].get(0).getDistance();

                    if (dist[v] + weightU < dist[u]) {
                        dist[u] = dist[v] + weightU;
                        pred[u] = v;
                        paths.get(u).clear();
                        paths.get(u).addAll(paths.get(v)); // Update the path from v to u
                        paths.get(u).add(exe[v][i].get(0).getFlightId());
                    }
                }
            }
        }

        List<PriceDistanceInfo>[][] res = new List[n][n];

        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < n; ++j) {
                if (i == s) {
                    res[i][j] = new ArrayList<>();
                    if (exe[i][j].size() > 0) {
                        res[i][j].add(exe[i][j].get(0));
                        res[i][j].get(0).setDistance(dist[j]);
                        res[i][j].get(0).setPath(paths.get(j)); // Use the correct index
                    }
                } else {
                    res[i][j] = new ArrayList<>();
                    if (exe[i][j].size() > 0) {
                        res[i][j].add(exe[i][j].get(0));
                    }
                }
            }
        }

        return res;
    }
}
