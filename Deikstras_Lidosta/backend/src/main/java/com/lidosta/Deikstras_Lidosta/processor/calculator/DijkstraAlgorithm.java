package com.lidosta.Deikstras_Lidosta.processor.calculator;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class DijkstraAlgorithm {

    private static int INF = Integer.MAX_VALUE / 2;

    private int n; // количество вершин в орграфе
    private ArrayList<Integer>[] adj; // список смежности
    private ArrayList<Integer>[] weight; // вес ребра в орграфе
    private boolean[] used; // массив для хранения информации о пройденных и не пройденных вершинах
    private int[] dist; // массив для хранения расстояния от стартовой вершины
    // массив предков, необходимых для восстановления кратчайшего пути из стартовой вершины
    private int[] pred;
    private int start; // стартовая вершина, от которой ищется расстояние до всех других

    // процедура запуска алгоритма Дейкстры из стартовой вершины
    public List<PriceDistanceInfo>[][] dijkstra(List<PriceDistanceInfo>[][] exe, int s) {
        n = exe.length; // количество вершин в орграфе
        adj = new ArrayList[n];
        weight = new ArrayList[n];
        used = new boolean[n];
        dist = new int[n];
        pred = new int[n];

        for (int i = 0; i < n; i++) {
            adj[i] = new ArrayList<>();
            weight[i] = new ArrayList<>();
            used[i] = false;
            dist[i] = INF;
            pred[i] = -1;
        }

        start = s;
        dist[start] = 0; // кратчайшее расстояние до стартовой вершины равно 0

        for (int iter = 0; iter < n; ++iter) {
            int v = -1;
            int distV = INF;

            // выбираем вершину, кратчайшее расстояние до которого еще не найдено
            for (int i = 0; i < n; ++i) {
                if (!used[i] && dist[i] <= distV) {
                    v = i;
                    distV = dist[i];
                }
            }

            if (distV == INF) {
                break; // все оставшиеся вершины недостижимы из start
            }

            used[v] = true;

            // рассматриваем все смежные вершины с вершиной v
            for (int i = 0; i < n; ++i) {
                if (exe[v][i].size() > 0) {
                    int u = i;
                    int weightU = exe[v][i].get(0).getDistance();

                    // релаксация вершины
                    if (dist[v] + weightU < dist[u]) {
                        dist[u] = dist[v] + weightU;
                        pred[u] = v;
                    }
                }
            }
        }

        List<PriceDistanceInfo>[][] res = new List[n][n];

        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < n; ++j) {
                if(i==s) {
                    res[i][j] = new ArrayList<>();
                    res[i][j].add(exe[i][j].get(0));
                    res[i][j].get(0).setDistance(dist[j]);
                } else {
                    res[i][j] = new ArrayList<>();
                    res[i][j].add(exe[i][j].get(0));
                }
            }
        }

        return res;
    }
}