/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithms;

/**
 *
 * @author kabra
 */
import java.util.*;

public class FordFulkerson {

    static final int V = 6; // Number of vertices in the graph

    // Breadth-First Search to find an augmenting path
    boolean bfs(int[][] residualGraph, int source, int sink, int[] parent) {
        boolean[] visited = new boolean[V];
        Queue<Integer> queue = new LinkedList<>();

        queue.add(source);
        visited[source] = true;
        parent[source] = -1;

        while (!queue.isEmpty()) {
            int u = queue.poll();

            for (int v = 0; v < V; v++) {
                if (!visited[v] && residualGraph[u][v] > 0) {
                    queue.add(v);
                    parent[v] = u;
                    visited[v] = true;
                }
            }
        }

        return visited[sink];
    }

    // Main method to compute max flow
    int fordFulkerson(int[][] capacityGraph, int source, int sink) {
        int u, v;

        int[][] residualGraph = new int[V][V];

        // Initialize residual graph
        for (u = 0; u < V; u++) {
            for (v = 0; v < V; v++) {
                residualGraph[u][v] = capacityGraph[u][v];
            }
        }

        int[] parent = new int[V];
        int maxFlow = 0;

        // Augment the flow while there is a path from source to sink
        while (bfs(residualGraph, source, sink, parent)) {
            // Find minimum residual capacity in the found path
            int pathFlow = Integer.MAX_VALUE;
            for (v = sink; v != source; v = parent[v]) {
                u = parent[v];
                pathFlow = Math.min(pathFlow, residualGraph[u][v]);
            }

            // Update residual capacities of the edges and reverse edges
            for (v = sink; v != source; v = parent[v]) {
                u = parent[v];
                residualGraph[u][v] -= pathFlow;
                residualGraph[v][u] += pathFlow;
            }

            // Add path flow to overall flow
            maxFlow += pathFlow;
        }

        return maxFlow;
    }

    public static void main(String[] args) {
        FordFulkerson ff = new FordFulkerson();

        // Example graph represented as capacity matrix
        int[][] graph = {
            {0, 16, 13, 0, 0, 0},
            {0, 0, 10, 12, 0, 0},
            {0, 4, 0, 0, 14, 0},
            {0, 0, 9, 0, 0, 20},
            {0, 0, 0, 7, 0, 4},
            {0, 0, 0, 0, 0, 0}
        };

        int source = 0, sink = 5;
        int maxFlow = ff.fordFulkerson(graph, source, sink);

        System.out.println("The maximum possible flow is " + maxFlow);
    }
}
