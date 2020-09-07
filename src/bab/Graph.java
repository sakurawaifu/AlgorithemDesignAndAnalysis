package bab;

import java.util.Arrays;
import java.util.PriorityQueue;

// tsp问题中，任意两个城市之间都有边(邻接)，即图是一个带权完全图。因此邻接矩阵只有主对角线无权，其余均有权。
// 因此代码中直接用i!=j替代adj[i][j]!=0
public class Graph {
    private class Vertex {// 图的顶点
        int ordinal;// 顶点序号
        int minOut = Integer.MAX_VALUE;// 顶点的最小出边权
        int minIn = Integer.MAX_VALUE;// 顶点的最小入边权

        Vertex(int ordinal) {
            this.ordinal = ordinal;
            for (int col = 0; col < n; col++)// 遍历邻接矩阵第ordinal行
                if (col != ordinal && adjMatrix[ordinal][col] < minOut)
                    minOut = adjMatrix[ordinal][col];
            for (int row = 0; row < n; row++)// 遍历邻接矩阵第ordinal列
                if (row != ordinal && adjMatrix[row][ordinal] < minIn)
                    minIn = adjMatrix[row][ordinal];
        }
    }

    private class Node implements Comparable<Node> {// 解空间树的结点
        int ordinal;// 该结点对应的顶点的序号
        int depth;// 结点在树中的深度
        // path保存顶点的一个全排列，因此path各元素互异
        int[] path;
        int cost;// 根到该结点的路径:p[0..d]的消费
        int lowerBound;// 结点代价下界，值为cost加上p[d+1..n-1]的估算

        public Node(int ordinal, int depth, int cost) {
            this.ordinal = ordinal;
            this.depth = depth;
            this.cost = cost;
        }

        public int getLowerBound() {
            int s = ordinal, e = path[0];// 以当前点为起点，回路起点为终点
            int minOut = Integer.MAX_VALUE, minIn = Integer.MAX_VALUE, min = 0;
            for (int i = depth + 1; i < n; i++) {// 遍历剩余的未访问顶点i
                if (adjMatrix[s][path[i]] < minOut)// 求s到剩余结点的最小出边
                    minOut = adjMatrix[s][path[i]];

                if (adjMatrix[path[i]][e] < minIn)// 求剩余结点到e的最小入边
                    minIn = adjMatrix[path[i]][e];

                min += vertices[path[i]].minIn + vertices[path[i]].minOut;// 求所有剩余顶点的最小出边和入边之和
            }
            return lowerBound = cost + (minOut + minIn + min) >>> 1;// 除以2得出可能回路的下界
        }

        public int compareTo(Node o) {
            return lowerBound - o.lowerBound;// 下界小的结点小
        }
    }

    private int[][] adjMatrix;// 邻接矩阵
    private int n;// 顶点个数
    private Vertex[] vertices;// 顶点数组
    public int leastCost = Integer.MAX_VALUE;// 最小花费
    public int[] optimalPath;

    public Graph(int[][] adjMatrix) {
        n = adjMatrix.length;
        this.adjMatrix = adjMatrix;
        vertices = new Vertex[n];
        for (int i = 0; i < n; i++)
            vertices[i] = new Vertex(i);
    }

    public void tsp() {
        computeUpperBound();// 贪心法计算上界和对应路径

        PriorityQueue<Node> queue = new PriorityQueue<>();
        Node head = new Node(0, 0, 0);// 创建解空间树的根，深度为0；以顶点0为起点，到其自身的花费为0
        head.path = new int[n];
        for (int i = 0; i < n; i++)
            head.path[i] = i;// 以自然排列做为初始路径

        queue.offer(head);// 结点入队
        while (!queue.isEmpty()) {
            head = queue.poll();// 队头结点出队，其下界最小，优先级最高
            // 取出的是叶结点，其下界是一条哈密顿回路的确切代价。其他结点不一定能最终构成哈密顿回路，即使可构成回路，花费也必定>=它
            if (head.depth == n - 1)
                break;

            // 叶结点的父结点，它只有一个孩子结点，即叶结点
            if (head.depth == n - 2) {
                int edge1 = adjMatrix[head.ordinal][head.path[n - 1]];// 该结点到叶结点的边权
                int edge2 = adjMatrix[head.path[n - 1]][head.path[0]];// 叶结点到起点的边权
                int totalCost = head.cost + edge1 + edge2;// 该条哈密顿回路的总代价
                if (totalCost < leastCost) {// 比当前最优值更优
                    leastCost = totalCost;// 更新上界
                    Node leaf = new Node(head.path[n - 1], n - 1, totalCost);// 创建叶结点
                    leaf.path = head.path;// head已经取出，不会再使用。其path就是叶结点path，直接赋引用，不用重新创建path数组
                    leaf.lowerBound = totalCost;// 下界不再是估计值，而是确切值
                    queue.offer(leaf);
                }
                continue;
            }

            // 非叶子的父结点，即深度<n-2
            for (int i = head.depth + 1; i < n; i++) {// 遍历剩余顶点p[d+1..n-1]，即当前结点的孩子结点
                Node child = new Node(head.path[i], head.depth + 1, head.cost + adjMatrix[head.ordinal][head.path[i]]);
                child.path = new int[n];
                System.arraycopy(head.path, 0, child.path, 0, n);
                child.path[head.depth + 1] = head.path[i];// 相当于将该孩子结点在path的值与父结点在path的后一个值交换
                child.path[i] = head.path[head.depth + 1];
                int lowerBound = child.getLowerBound();
                if (lowerBound < leastCost)// 可行结点
                    queue.offer(child);
                else
                    child = null;// 舍弃结点
            }
        }

        // 如果贪心求出的就是最优解，则每个叶结点都>=leastCost，最后一层全部剪掉。但队列为空，head深度为n-2，即叶结点的父结点
        if (head.depth == n - 1) {
            leastCost = head.cost;
            optimalPath = head.path;
        }
        System.out.println("最小花费" + leastCost);
        System.out.println("路径" + Arrays.toString(optimalPath));
    }

    public void computeUpperBound() {
        int[] path = new int[n];// 保存顶点序号，初始为自然排列
        for (int i = 0; i < n; i++)
            path[i] = i;

        int cursor = 0;// v的游标，path[cursor]为当前所在顶点的序号
        int cost = 0;// 回路总的贪心代价
        while (cursor < n - 2) {// n-2时，只剩最后一个顶点v[n-1]，不用再循环
            int minCost = Integer.MAX_VALUE;// 当前顶点到剩余顶点v[cur+1..n-1]的最小边权
            int minIndex = 0;// 最小边对应的终点在v中的下标
            for (int i = cursor + 1; i < n; i++) {
                if (minCost > adjMatrix[path[cursor]][path[i]]) {
                    minCost = adjMatrix[path[cursor]][path[i]];
                    minIndex = i;
                }
            }
            swap(path, cursor + 1, minIndex);// 将v[minIndex]移到v[cur]之后，表示以v[minIndex]做为下一顶点
            cursor++;// 当前顶点变为v[minIndex]
            cost += minCost;
        }
        optimalPath = path;// 保存贪心路径
        leastCost = cost + adjMatrix[path[cursor]][path[n - 1]] + adjMatrix[path[n - 1]][path[0]];
    }

    private void swap(int[] a, int i, int j) {
        int temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }
}