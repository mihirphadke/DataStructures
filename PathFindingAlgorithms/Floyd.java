package PathFindingAlgorithms;

/**
 * The following code is part of solution to Assignment 3
 * CPSC281 - Winter 2023 Semester
 *
 * Floyd's Algorithm is used to find the shortest paths between all pairs of vertices in a weighted graph.
 * This code defines a method 'floyd(int[][] graph)' which takes in a 2D array representing the adjacency matrix of a
 * weighted graph and returns a new 2D array representing the shortest path between all pairs of nodes in the graph.
 *
 * @author Mihir Phadke
 * ID: 230149648
 * @version 1
 */
public class Floyd {

    public static void main(String[] args) {
        int INF = 99999;
        int[][] graph = {
                //      A    B    C    D    E    F    G    H    I
                /*A*/{  0,   4, INF, INF, INF, INF, INF, INF, INF},
                /*B*/{INF,   0, INF, INF, INF,   2, INF,   3, INF},
                /*C*/{INF,   4,   0,   3, INF, INF, INF, INF, INF},
                /*D*/{INF, INF, INF,   0,   6,   1, INF, INF, INF},
                /*E*/{INF, INF,   2, INF,   0, INF, INF, INF, INF},
                /*F*/{INF, INF,  10, INF, INF,   0, INF, INF, INF},
                /*G*/{INF,   2, INF, INF, INF,   6,   0, INF, INF},
                /*H*/{INF, INF, INF, INF, INF, INF, INF,   0, INF},
                /*I*/{  5, INF, INF, INF, INF, INF,   7,   8,   0}
        };

        int[][] result = floyd(graph);

        for (int[] intArray : result) {
            for (int thisInt : intArray) {
                if (thisInt == INF) {
                    System.out.print(" INF");
                } else {
                    if (thisInt / 10 == 0) {
                        System.out.print("   " + thisInt);
                    } else {
                        System.out.print("  " + thisInt);
                    }
                }
            }
            System.out.println();
        }
    }

    /**
     * 1. Initialization:
     * Create a new 2D array called 'dist' of the same size as the input 'graph'. Copy the values from
     * 'graph' to 'dist'. This 'dist' matrix will store the shortest path distances between all pairs of nodes.
     *
     * 2. The Algorithm:
     * The core idea of the algorithm is to iteratively check if a node 'k' can be used as an intermediate node to find
     * a shorter path between nodes 'i' and 'j'. For each value of 'k' (0 to n-1), update the 'dist' matrix if there's a
     * shorter path between 'i' and 'j' using 'k' as an intermediate node.
     *
     * The triple nested loop works as follows:
     * The outer loop iterates through all possible intermediate nodes 'k'
     * The middle loop iterates through all possible source nodes 'i'
     * The inner loop iterates through all possible destination nodes 'j'
     *
     * In each iteration, we update the distance matrix entry 'dist[i][j]' based on the following condition:
     * If the sum of the distances 'dist[i][k]' and 'dist[k][j]' is smaller than the current distance 'dist[i][j], it
     * means there's a shorter path between nodes 'i' and 'j' using node 'k' as an intermediate node, so we update
     * 'dist[i][j]' with the new distance.
     *
     * 3. Return the shortest path distances matrix:
     * After the algorithm completes, the 'dist' matrix contains the shortest path distances between all parts of nodes
     * in the graph. The method returns this matrix.
     *
     * @param graph 2D array representation of the adjacency matrix of the graph
     * @return shortest distance matrix
     */
    public static int[][] floyd(int[][] graph) {
        int n = graph.length;
        int[][] dist = new int[n][n];

        // Initialise the distance matrix
        for (int i = 0; i < n; i++) {
            System.arraycopy(graph[i], 0, dist[i], 0, n);
        }

        // The Algorithm
        for (int k = 0; k < n; k++) {
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if (dist[i][k] != 99999 && dist[k][j] != 99999 && dist[i][k] + dist[k][j] < dist[i][j]) {
                        dist[i][j] = dist[i][k] + dist[k][j];
                    }
                }
            }
        }

        return dist;
    }
}

