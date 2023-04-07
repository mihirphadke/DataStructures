package PathFindingAlgorithms;
import java.util.Arrays;

/**
 * This code is part of solution to Assignment 3
 * CPSC281 - Winter 2023 Semester
 *
 * Dijkstra's algorithm is a graph search algorithm that finds the shortest path from a source node to all other nodes
 * in a weighted graph. The algorithm maintains a set of unvisited nodes and a distance array, which holds the shortest
 * known distance from the source node to each node in the graph.
 *
 * The following code implements Dijkstra's algorithm and reconstructs the shortest path. This program prints the
 * shortest path from the source node to the target node using the given graph.
 *
 * @author Mihir Phadke
 * ID: 230149648
 * @version 1
 */
public class Dijkstra {

    public static void main(String[] args) {
        int INF = 99999;
        int[][] graph = {
                //      A    B    C    D    E    F    G    H    I
                /*A*/{  0,   6, INF, INF, INF, INF,   4, INF, INF},
                /*B*/{INF,   0,   4, INF, INF, INF, INF, INF, INF},
                /*C*/{INF, INF,   0,   2, INF, INF, INF, INF, INF},
                /*D*/{INF, INF, INF,   0, INF,   8, INF, INF, INF},
                /*E*/{INF, INF,   3, INF,   0,  13, INF,   2,   7},
                /*F*/{INF, INF, INF, INF, INF,   0, INF, INF, INF},
                /*G*/{INF, INF, INF, INF, INF, INF,   0,   7, INF},
                /*H*/{INF, INF, INF, INF, INF, INF, INF,   0,   2},
                /*I*/{INF, INF, INF, INF, INF,   6, INF, INF,   0}
        };

        int start = 0; // Node A
        int target = 5; // Node F
        int[][] result = dijkstra(graph, start, target);
        int[] shortestPath = result[0];
        int pathIndex = result[1][0];

        System.out.println("Shortest path from node " + (char) (start + 'A') + " to node " + (char) (target + 'A')
                + ": " + Arrays.toString(shortestPath));
        System.out.print("The shortest path is ");
        for (int i = 0; i < pathIndex; i++) {
            System.out.print((char) (shortestPath[i] + 'A'));
            if (i < pathIndex - 1) {
                System.out.print(" → ");
            }
        }
        System.out.println();
    }

    /**
     * 1. Initialisation:
     * Create a distance array 'dist' of size 'n' (number of nodes) and set all distances to infinity except for the
     * source node which has a distance of 0.
     *
     * Create a 'visited' boolean array to track whether a node has been visited or not. Initially, all nodes are
     * unvisited.
     *
     * 'start' and 'target' variables represent the source and target nodes.
     *
     * 2. Main loop (iterates 'n-1' times):
     * Find the unvisited node with the smallest known distance from the source node ('minDistNode').
     *
     * Mark 'minDistNode' as visited by setting its corresponding value in the 'visited' array to 'true'.
     *
     * Iterate through all adjacent nodes of 'minDistNode'. If the distance from the source node to the current
     * adjacent node through 'minDistNode' is smaller than the currently known distance, update the distance in the
     * 'dist' array.
     *
     * 3. Path reconstruction:
     * Create an array 'path' to store the shortest path from the source to the target node.
     * Initialize 'pathIndex' to 0 and 'currentNode' to the target node.
     *
     * While 'currentNode' is not the source node:
     * Find the previous node in the shortest path ('shortestPrevNode') by checking if the distance from the source node
     * to the current node minus the weight of the edge between the previous and current nodes is equal to the distance
     * from the source node to the previous node. Update 'currentNode' to 'shortestPrevNode'. Add 'currentNode' to the
     * 'path' array and increment 'pathIndex'.
     *
     * Reverse the 'path' array to obtain the shortest path from the source to the target node.
     *
     * @param graph 2D array representation of the adjacency matrix of the graph
     * @param start source node
     * @param target target node
     * @return shortest path array
     */
    public static int[][] dijkstra(int[][] graph, int start, int target) {
        int n = graph.length;
        int[] dist = new int[n];
        boolean[] visited = new boolean[n];

        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[start] = 0;

        // Initialise the distance matrix
        for (int i = 0; i < n - 1; i++) {
            int minDistNode = -1;
            for (int j = 0; j < n; j++) {
                if (!visited[j] && (minDistNode == -1 || dist[j] < dist[minDistNode])) {
                    minDistNode = j;
                }
            }

            visited[minDistNode] = true;

            // Main loop
            for (int j = 0; j < n; j++) {
                int edgeDistance = graph[minDistNode][j];
                if (edgeDistance > 0 && ((long) dist[minDistNode] + edgeDistance) < dist[j]) {
                    dist[j] = dist[minDistNode] + edgeDistance;
                }
            }
        }

        // Path reconstruction
        int[] path = new int[n];
        int pathIndex = 0;
        int currentNode = target;
        path[pathIndex++] = currentNode;
        while (currentNode != start) {
            int shortestPrevNode = -1;
            for (int j = 0; j < n; j++) {
                if (graph[j][currentNode] > 0 && dist[currentNode] - graph[j][currentNode] == dist[j]) {
                    shortestPrevNode = j;
                    break;
                }
            }
            currentNode = shortestPrevNode;
            path[pathIndex++] = currentNode;
        }

        int[] shortestPath = Arrays.copyOf(path, pathIndex);
        for (int i = 0; i < pathIndex / 2; i++) {
            int temp = shortestPath[i];
            shortestPath[i] = shortestPath[pathIndex - 1 - i];
            shortestPath[pathIndex - 1 - i] = temp;
        }

        return new int[][] { shortestPath, { pathIndex } };
    }
}
