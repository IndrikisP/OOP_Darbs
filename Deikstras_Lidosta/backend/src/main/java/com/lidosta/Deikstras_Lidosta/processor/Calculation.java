package com.lidosta.Deikstras_Lidosta.processor;

import javafx.util.Pair;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Vector;

public class Calculation {

    private int size = 100;

    /// CODE for visual representation of lidostas project.
/// Input data is in lidostas3.txt file
    int verticleCount;
    String verticles[] = new String[size];
    private Float adj[][] = new Float[size][size];

    Calculation() {

    }

    public Float[][] getAdj() {
        return adj;
    }

    Map<Integer, Vector<Pair<String, String>>> reis = new HashMap<>();

    boolean addVerticle(String v) {
        for (int i = 0; i < verticleCount; i++) {
            if (verticles[i] == v) {
                return false;
            }
        }
        if (verticleCount == size) {
            return false;
        }
        verticles[verticleCount++] = v;
        return true;
    }

    // To add an edge
    boolean addDirectedEdge(String u,
                            String v, int wt) {
        int v1pos = -1, v2pos = -1;
        for (int i = 0; i < verticleCount; i++) {
            if (u == verticles[i])
                v1pos = i;
            if (v == verticles[i])
                v2pos = i;
        }
        if (v1pos != -1 && v2pos != -1) {
            adj[v1pos][v2pos] = Float.valueOf(wt);
            return true;
        }
        return false;
    }

    // Print adjacency list representation ot graph
    void printGraph(Float adj[][], int V) {
        String nosaukums = null;
        for (int u = 0; u < V-1; u++) {
            System.out.println("No lidostas " + verticles[u] + " var nokļūt uz:");
            for (int j = 0; j < V-1; j++) {

                for (int i = 0; i < verticleCount-1; i++) {
                    if (i == j) {
                        nosaukums = verticles[i];
                        break;
                    } else
                        nosaukums = verticles[i];
                }
if(adj[u][j]!=null)
                System.out.println("\tlidostu " + nosaukums + " par " + adj[u][j] + " EUR");
            }
            System.out.println();
        }
    }


    // Create graph
    public int createGraph(Vector<Pair<Integer, Float>>[] adj) throws FileNotFoundException {
        String lidostasTxt = "C:\\SVN\\OOP_Darbs\\Deikstras_Lidosta\\src\\main\\resources\\lidostas3.txt";
        String lidosta_no, lidosta_uz;
        int reisa_id;
        int biletes_cena;
        File file = new File(lidostasTxt);
        Scanner scanner = new Scanner(file);

        while (scanner.hasNext()) {
            reisa_id = scanner.nextInt();
            lidosta_no = scanner.next();
            lidosta_uz = scanner.next();
            biletes_cena = scanner.nextInt();

            addVerticle(lidosta_no);
            addVerticle(lidosta_uz);
            addDirectedEdge(lidosta_no, lidosta_uz, biletes_cena);
        }

        scanner.close();
        return 0;
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

    // Add reisies to map
    public void addReisies() throws FileNotFoundException {
        File file = new File("C:\\SVN\\OOP_Darbs\\Deikstras_Lidosta\\src\\main\\resources\\lidostas3.txt");
        Scanner scanner = new Scanner(file);
        int key;
        String text0, text1, text2;

        if (!file.exists()) {
            throw new FileNotFoundException("Cannot open file");
        }

        while (scanner.hasNext()) {
            key = scanner.nextInt();
            text0 = scanner.next();
            text1 = scanner.next();
            text2 = scanner.next();

            Pair<String, String> pair = new Pair<String, String>(text0, text1);
            reis.putIfAbsent(key, new Vector<Pair<String, String>>());
            reis.get(key).add(pair);
        }

        scanner.close();
    }

    // Print all reisies
    public void printReisies() {
        for (Map.Entry<Integer, Vector<Pair<String, String>>> mapPair : reis.entrySet()) {
            int key = mapPair.getKey();
            List<Pair<String, String>> vecPairs = mapPair.getValue();

            for (Pair<String, String> vecPair : vecPairs) {
                String first = vecPair.getKey();
                String second = vecPair.getValue();
                System.out.println(key + ": " + first + ", " + second);
            }
        }
    }


    // Prints reiss_id
    public void getReisNumber(String lidosta_no, String lidosta_uz) {
        for (Map.Entry<Integer, Vector<Pair<String, String>>> mapPair : reis.entrySet()) {
            int key = mapPair.getKey();
            List<Pair<String, String>> vecPairs = mapPair.getValue();

            for (Pair<String, String> vecPair : vecPairs) {
                String first = vecPair.getKey();
                String second = vecPair.getValue();

                if (first.equals(lidosta_no) && second.equals(lidosta_uz)) {
                    System.out.println("reiss: " + key);
                }
            }
        }
    }


    // Kur var nokļūt no lidostas X ja ir N naudas
    public int XNquery(Vector<Pair<Integer, Float>> adj[], String root, float n, int money, List<String> f) {
        int rootIndex = -1;
        for (int i = 0; i < verticleCount; i++) {
            if (verticles[i].equals(root)) {
                rootIndex = i;
                break;
            }
        }
        f.add(root);

        for (Pair<Integer, Float> pair : adj[rootIndex]) {
            int adjacentVertex = pair.getKey();
            float weight = pair.getValue();

            if (weight <= n) {
                if (checkNotFound(f, verticles[adjacentVertex])) {
                    money += weight;
                    System.out.print(f.get(0) + "->" + verticles[adjacentVertex] + " Price: " + money + " ");
                    if (verticles[rootIndex].equals(f.get(0))) {
                        System.out.print("Tiešais");
                        getReisNumber(verticles[rootIndex], verticles[adjacentVertex]);
                    } else {
                        System.out.println();
                    }

                    XNquery(adj, verticles[adjacentVertex], n - money, money, f);
                    money -= weight;
                }
            }
        }
        return money;
    }


    // Prints out list
    public void showList(List<String> g) {
        int i = 0;
        Iterator<String> it = g.iterator();
        while (it.hasNext()) {
            String element = it.next();
            System.out.print(element);
            if (!element.equals(g.get(g.size() - 1)) || i == 0) {
                System.out.print("->");
                i++;
            }
        }
        System.out.println();
    }
// XYquery, ir pieprasījums- Vai var nokļūt no lidostas X uz lidostu Y ar tiešo reisu

    // Vai var nokļūt no lidostas X uz lidostu Y
    public boolean XYquery(Vector<Pair<Integer, Float>> adj[], String lidosta_no, String lidosta_uz) {
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
        for (Pair<Integer, Float> pair : adj[indexLidosta_no]) {
            if (pair.getKey() == indexLidosta_uz) {
                return true;
            }
        }
        return false;
    }


    // No lidostas X, atgriezites lidostā X, un iztērējot ne vairāk, kā N naudas
//    public int XXNquery(String root, int n, int money, List<String> f) {
//        boolean noLoop = true;
//        int rootIndex = -1;
//        f.add(root);
//        String[] array = new String[verticleCount];
//        for (int i = 0; i < verticleCount; i++) {
//            if (verticles[i].equals(root)) {
//                rootIndex = i;
//                break;
//            }
//        }
//        for (Float value : adj[rootIndex]) {
//            if (value <= n) {
//                if (verticles[money.getKey()].equals(f.get(0))) {
//                    f.add(verticles[pair.getKey()]);
//                    showList(f);
//                    f.remove(f.size() - 1);
//                    money -= pair.getValue();
//                    break;
//                }
//                money += pair.getValue();
//                XXNquery(adj, verticles[pair.getKey()], n - money, money, f);
//                money -= pair.getValue();
//            }
//        }
//        return money;
//    }


    public static void main(String[] args) throws FileNotFoundException {
        Calculation c = new Calculation();
        long tic = System.currentTimeMillis();
        Vector<Pair<Integer, Float>>[] adj = new Vector[50000];
        for (int i = 0; i < adj.length; i++) {
            adj[i] = new Vector<>();
        }
        c.createGraph(adj);
        c.printGraph(c.getAdj(), 100);

        List<String> am = new ArrayList<>();
        c.addReisies();

        System.out.println("--------XNquery------");

        c.XNquery(adj, "Tallinn/TLL", 500.00f, 0, am); // prints answer

        c.XNquery(adj, "Stockholm/ARN", 500.00f, 0, am);
        System.out.println("--------XXNquery--------");

        //c.XXNquery(adj, "Tallinn/TLL", 50000, 0, am);    // prints all ways that is possible for N money

       // c.XXNquery(adj, "ZAG", 10000, 0, am);
        // print nothing, because can't make x->x fly
        System.out.println("--------XYquery--------");

        boolean result = c.XYquery(adj, "Tallinn/TLL", "Milan/MXP");

        if (result)
            System.out.println("var nokļūt ar tiešo reisu");
        else
            System.out.println("nevar nokļūt ar tiešo reisu");
        long toc = System.currentTimeMillis();

        System.out.printf("Elapsed: %f seconds\n", (toc - tic) / 1000.0);
    }

}
