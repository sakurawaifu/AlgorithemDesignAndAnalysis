package backtrack;

import java.util.LinkedList;

public class Vertex {
    public boolean visited;
    public int ordinal;// 顶点序号
    public int color;// 顶点颜色
    public LinkedList<Vertex> adjList = new LinkedList<>();// 邻接表

    public Vertex(int ordinal) {
        this.ordinal = ordinal;
    }

    public String toString() {
        return String.format("%d=%d", ordinal, color);
    }
}
