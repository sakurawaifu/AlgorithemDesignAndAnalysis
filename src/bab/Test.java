package bab;

import java.util.Random;

public class Test {
    public static void main(String[] args) {
        int[][] adjMatrix = {
                {0, 3, 1, 5, 8},
                {3, 0, 6, 7, 9},
                {1, 6, 0, 4, 2},
                {5, 7, 4, 0, 3},
                {8, 9, 2, 3, 0}
        };
        adjMatrix = getRandomGraph(12);
        for (int i = 0; i < adjMatrix.length; i++) {
            for (int j = 0; j < adjMatrix.length; j++) {
                System.out.printf("%-3d", adjMatrix[i][j]);
            }
            System.out.println();
        }
        Graph graph = new Graph(adjMatrix);
        graph.tsp();
    }

    public static int[][] getRandomGraph(int n) {
        Random random = new Random();
        int[][] adj = new int[n][n];
        for (int i = 1; i < n; i++) {
            for (int j = 0; j < i; j++) {
                while ((adj[i][j] = random.nextInt(25)) == 0) ;
                adj[j][i] = adj[i][j];
            }
        }
        return adj;
    }
}
