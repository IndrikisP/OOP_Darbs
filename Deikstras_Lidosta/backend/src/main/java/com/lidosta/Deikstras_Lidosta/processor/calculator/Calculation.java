package com.lidosta.Deikstras_Lidosta.processor.calculator;

import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

/**
 * This class is to provide calculation logic.
 */
@Service
public class Calculation {
    private static int size = 1000;
    private static Calculation calculation = null;
    private static List<PriceDistanceInfo>[][] shortestDistance = null;
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
            pDInfo[v1pos][v1pos].add(new PriceDistanceInfo(priceDistanceInfo.getFlightId(),0f,0, null));
            pDInfo[v2pos][v2pos].add(new PriceDistanceInfo(priceDistanceInfo.getFlightId(),0f,0, null));

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
                        sb.append("id: ")
                                .append(info.getFlightId())
                                .append(" | ")
                                .append(info.getPrice())
                                .append(" EUR ")
                                .append(info.getDistance())
                                .append(" KM\n");
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
        return shortestDistance[x][y].size()>0 ? shortestDistance[x][y].get(0).getPath() : null;
    }

    // No real usage for task
    public void printVertices() {
        for (int i = 0; i < verticleCount; i++) {
            System.out.print(verticles[i] + " ");
            System.out.print(i);
        }
        System.out.println();
    }

    // Validation for circle
    public boolean checkNotFound(List<String> g, String name) {
        Iterator<String> it = g.iterator();
        while (it.hasNext()) {
            String element = it.next();
            if (element.equals(name))
                return false;
        }
        return true;
    }

    // Kur var nokļūt no lidostas X ja ir N naudas
//    public int XNquery(String root, float n, int money, List<String> f) {
//        int rootIndex = -1;
//        for (int i = 0; i < verticleCount; i++) {
//            if (verticles[i].equals(root)) {
//                rootIndex = i;
//                break;
//            }
//        }
//        f.add(root);
//        int count = 0;
//        //for (Pair<Integer, Float> pair : adj[rootIndex]) {
//        for (Float price : adj[rootIndex]) {
//            int adjacentVertex = count++;
//            if (price != null) {
//
//                if (price <= n) {
//                    if (checkNotFound(f, verticles[adjacentVertex])) {
//                        money += price;
//                        System.out.print(f.get(0) + "->" + verticles[adjacentVertex] + " Price: " + money + " ");
//                        if (verticles[rootIndex].equals(f.get(0))) {
//                            System.out.println("Tiešais");
//                        } else {
//                            System.out.println();
//                        }
//                        XNquery(verticles[adjacentVertex], n - money, money, f);
//                        money -= price;
//                    }
//                }
//            }
//        }
//        return money;
//    }

    // Prints out list
    public void showList(List<String> list) {
        for (int i = 0; i < list.size(); i++) {
            String element = list.get(i);
            System.out.print(element);
            if (i < list.size() - 1) {
                System.out.print("->");
            }
        }
        System.out.println();
    }

// XYquery, ir pieprasījums- Vai var nokļūt no lidostas X uz lidostu Y ar tiešo reisu

    // Vai var nokļūt no lidostas X uz lidostu Y
    public boolean XYquery(String lidosta_no, String lidosta_uz) {
        int indexLidosta_no = -1;
        int indexLidosta_uz = -1;
        for (int i = 0; i < verticleCount; i++) {
            if (verticles[i].equals(lidosta_no)) {
                indexLidosta_no = i;
            }
            if (verticles[i].equals(lidosta_uz)) {
                indexLidosta_uz = i;
            }
        }
        return pDInfo != null;
    }


    // No lidostas X, atgriezites lidostā X, un iztērējot ne vairāk, kā N naudas
//    public int XXNquery(String root, int n, int money, List<String> f) {
//        boolean noLoop = true;
//        int rootIndex = -1;
//        f.add(root);
//        for (int i = 0; i < verticleCount; i++) {
//            if (verticles[i].equals(root)) {
//                rootIndex = i;
//                break;
//            }
//        }
//        if (rootIndex != -1) {
//            for (List<PriceDistanceInfo> value : pDInfo[rootIndex]) {
//                if (value != null) {
//                    if (value <= n) {
//                        if (verticles[rootIndex].equals(f.get(0))) {
//                            f.add(verticles[rootIndex]);
//                            showList(f);
//                            f.remove(f.size() - 1);
//                            money -= value;
//                            break;
//                        }
//                        money += value;
//                        XXNquery(verticles[rootIndex], n - money, money, f);
//                        money -= value;
//                    }
//                }
//            }
//        }
//        return money;
//    }


    public static void main(String[] args) throws FileNotFoundException {
        Calculation c = new Calculation();
        long tic = System.currentTimeMillis();

//        c.createGraph();
//        c.printGraph(c.getAdj(), 100);
//
//        List<String> am = new ArrayList<>();
//        //c.addReisies();
//
//        System.out.println("--------XNquery------");
//
//        c.XNquery("Tallinn/TLL", 50.00f, 0, am); // prints answer
//        am = new ArrayList<>();
//
//        c.XNquery("Stockholm/ARN", 500.00f, 0, am);
//        System.out.println("--------XXNquery--------");
//
//        c.XXNquery("Tallinn/TLL", 5000000, 0, am);    // prints all ways that is possible for N money
//
//        c.XXNquery("ZAG", 10000, 0, am);
        // print nothing, because can't make x->x fly
        System.out.println("--------XYquery--------");

        boolean result = c.XYquery("Tallinn/TLL", "Helsinki/HEL");

        if (result)
            System.out.println("var nokļūt ar tiešo reisu");
        else
            System.out.println("nevar nokļūt ar tiešo reisu");
        long toc = System.currentTimeMillis();

        System.out.printf("Elapsed: %f seconds\n", (toc - tic) / 1000.0);
    }

}
