package backtrack;

import java.util.Arrays;

public class UDGraph {
    public int numOfVertices;
    public int numOfColors;
    public int numOfSolutions;
    public Vertex[] vertices;

    public UDGraph(int[][] adjMatrix) {
        numOfVertices = adjMatrix.length;
        vertices = new Vertex[numOfVertices];
        for (int i = 0; i < numOfVertices; i++) {
            vertices[i] = new Vertex(i);
        }
        // 遍历邻接矩阵 n^2
        for (int i = 0; i < numOfVertices; i++) {
            for (int j = 0; j < numOfVertices; j++) {
                if (adjMatrix[i][j] != 0)
                    vertices[i].adjList.add(vertices[j]);
            }
        }
    }

    public void mColor(int m) {
        numOfColors = m;
        dfs(0);// 从第一个顶点v[0]开始顺序地试探，直到v[n-1]。对图本身不是深搜，是对解空间树深搜(先根遍历+剪枝)
        System.out.println("方案数：" + numOfSolutions);
    }

    private void dfs(int i) {
        if (i == numOfVertices) {// 最后一个结点也成功涂色
            // System.out.println(Arrays.toString(vertices));
            numOfSolutions++;
            return;
        }
        for (int color = 1; color <= numOfColors; color++) {
            if (isFeasible(color, i)) {
                vertices[i].color = color;
                dfs(i + 1);
            }
        }
        vertices[i].color = 0;// 所有m种颜色均已试过，m棵子树均已遍历或剪枝，将颜色擦除，返回第i-1个顶点继续深搜
    }

    // 判断颜色color能否涂顶点i
    private boolean isFeasible(int color, int i) {
        for (Vertex adjVertex : vertices[i].adjList)
            if (adjVertex.color == color)// 顶点i存在邻接点已涂颜色color
                return false;
        return true;// 所有邻接点均未涂color
    }
}
