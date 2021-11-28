package ua.com.alevel.taskthird;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ShortestPath {

    int cities = 0;
    static final String input_file = "src/main/resources/cities/citiesIn.txt";
    static final String output_file = "src/main/resources/cities/citiesOut.txt";

    public void run() throws IOException {
        BufferedReader fileReader = new BufferedReader(new FileReader(input_file));
        String current;
        current = fileReader.readLine();
        cities = Integer.parseInt(current);
        List<String> cityList = new ArrayList<>();
        Graph g = new Graph(cities);
        int currentCity = 0;
        while (true) {
            current = fileReader.readLine();
            cityList.add(current);
            int neighbours = Integer.parseInt(fileReader.readLine());
            for (int j = 0; j < neighbours; ++j) {
                current = fileReader.readLine();
                String[] edge = current.split(" ");
                g.addEdge(currentCity, Integer.parseInt(edge[0]) - 1, Integer.parseInt(edge[1]));
            }
            currentCity++;
            if (currentCity == cities) {
                break;
            }
        }
        int distance[] = new int[g.getVertices()];
        int roadCount = Integer.parseInt(fileReader.readLine());
        BufferedWriter fileWrite = new BufferedWriter(new FileWriter(output_file));
        for (int i = 0; i < roadCount; ++i) {
            current = fileReader.readLine();
            String[] road = current.split(" ");
            BellmanFordShortestPath(g, cityList.indexOf(road[0]), distance);
            fileWrite.write(String.valueOf(distance[cityList.indexOf(road[1])]) + "\n");
            System.out.println(distance[cityList.indexOf(road[1])]);
        }
        System.out.println("Результат так же был записан в: " + output_file);
        fileReader.close();
        fileWrite.close();
    }

    //реализация алгоритма Беллмана-Форда для поиска кратчайших путей
    public void BellmanFordShortestPath(Graph graph, int start, int[] distance) {

        for (int i = 0; i < cities; ++i) {
            distance[i] = Integer.MAX_VALUE;
        }
        distance[start] = 0;
        for (int i = 0; i < cities; ++i) {
            for (Graph.Edge edge : graph.getEdges()) {
                int u = edge.getU();
                int v = edge.getV();
                int weight = edge.getWeight();
                if (distance[u] != Integer.MAX_VALUE && distance[v] > distance[u] + weight) {
                    distance[v] = distance[u] + weight;
                }
            }
        }
    }
}
