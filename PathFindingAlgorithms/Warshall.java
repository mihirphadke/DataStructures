package PathFindingAlgorithms;

/**
 * This code is part of solution to Assignment 3
 * CPSC281 - Winter 2023 Semester
 *
 * Warshall's Algorithm, is used to find the shortest paths between all pairs of vertices in a weighted graph. However,
 * you mentioned that you want to find which nodes are reachable from all nodes.
 * In this case, we can modify the algorithm to find the reachability matrix.
 *
 * The code below defines a method 'warshall(int[][] graph)' which takes in a 2D array representing the adjacency matrix
 * of a graph and returns a 2D array representing the reachability matrix. The reachability matrix indicates which nodes
 * are reachable from all nodes in the graph.
 *
 * @author Mihir Phadke
 * @version 1
 */
public class Warshall {

    public static void main(String[] args) {
        int[][] graph = {
                //   A  B  C  D  E  F  G  H  I  J  K
                /*A*/{0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                /*B*/{0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0},
                /*C*/{0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0},
                /*D*/{1, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0},
                /*E*/{0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0},
                /*F*/{0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0},
                /*G*/{0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1},
                /*H*/{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                /*I*/{0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0},
                /*J*/{0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1},
                /*K*/{0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0}
        };

        int[][] result = warshall(graph);

        for (int[] intArray : result) {
            for (int thisInt : intArray) {
                System.out.print(thisInt + " ");
            }
            System.out.println();
        }
    }

    /**
     * 1. Initialization:
     * Create a new 2D array called 'reachability' of the same size as the input 'graph'. Copy the values from
     * 'graph' to 'reachability'. Include the diagonal (self-reachable nodes) by setting 'reachability[i][j] = 1' when
     * 'i == j'
     *
     * 2. The Algorithm:
     * The core idea of the algorithm is to iteratively check if a node 'k' can be used as an intermediate node to
     * connect nodes 'i' and 'j'. For each value of 'k' (0 to n-1), update the 'reachability' matrix if there's a
     * path between 'i' and 'j' using 'k' as an intermediate node.
     *
     * The triple nested loop works as follows:
     * The outer loop iterates through all possible intermediate nodes 'k'
     * The middle loop iterates through all possible source nodes 'i'
     * The inner loop iterates through all possible destination nodes 'j'
     *
     * In each iteration, we update the reachability matrix entry 'reachability[i][j]' based on the following condition:
     * If 'reachability[i][j]' is already '1', then there's a direct path between nodes 'i' and 'j', and we keep the
     * value '1'.
     * If there's a path between nodes 'i' and 'k' , and also between nodes 'j' and 'k', then there's an indirect path
     * between nodes 'i' and 'j' using node 'k' as an intermediate node, and we set 'reachability[i][j]' to '1'.
     * If there's no path between nodes 'i' and 'j', either directly or through node 'k', we keep the value '0'.
     *
     * 3. Return the reachability matrix:
     * After the algorithm completes, the 'reachability' matrix contains the information about the reachability between
     * all parts of nodes in the graph. The method returns this matrix.
     *
     * @param graph 2D array representation of the adjacency matrix of the graph
     * @return reachability matrix
     */
    public static int[][] warshall(int[][] graph) {
        int n = graph.length;
        int[][] reachability = new int[n][n];

        // Initialise the reachability matrix
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                reachability[i][j] = graph[i][j];

                // Include the diagonal (self-reachable nodes)
                if (i == j) {
                    reachability[i][j] = 1;
                }
            }
        }

        // Warshall's Algorithm
        for (int k = 0; k < n; k++) {
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    reachability[i][j] = reachability[i][j] == 1 || (reachability[i][k] == 1 &&
                            reachability[k][j] == 1) ? 1 : 0;
                }
            }
        }

        return reachability;
    }
}
