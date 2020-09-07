package backtrack;

import java.util.Random;

public class Test {
    public static void main(String[] args) {
        int m = 6;
        int[][] adjMatrix = {
                {0, 1, 1, 1, 0},
                {1, 0, 1, 1, 1},
                {1, 1, 0, 1, 0},
                {1, 1, 1, 0, 1},
                {0, 1, 0, 1, 0}
        };
        adjMatrix = getRandomGraph(6);
        for (int i = 0; i < adjMatrix.length; i++) {
            for (int j = 0; j < adjMatrix.length; j++) {
                System.out.printf("%-3d", adjMatrix[i][j]);
            }
            System.out.println();
        }
        UDGraph graph = new UDGraph(adjMatrix);
        graph.mColor(m);
    }

    public static int[][] getRandomGraph(int n) {
        Random random = new Random();
        int[][] adj = new int[n][n];
        for (int i = 1; i < n; i++) {
            for (int j = 0; j < i; j++) {
                adj[i][j] = random.nextInt(2);
                adj[j][i] = adj[i][j];
            }
            adj[i][i - 1] = 1;
        }
        return adj;
    }
}
