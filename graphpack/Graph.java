package graphpack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class Graph {
    private int[][] adjMatrix;
    private boolean directed;
    private boolean weighted;

    private ArrayList<Integer[]> dfsScript;

    public Graph() {
        adjMatrix = null;
        dfsScript = null;
    }

    public Graph(int nodes, boolean directed, boolean weighted) {
        adjMatrix = new int[nodes][nodes];
        this.directed = directed;
        this.weighted = weighted;
        dfsScript = null;
    }

    public Graph(int[][] adjMatrix, boolean directed, boolean weighted) {
        this(adjMatrix.length, directed, weighted);
        copyAdjMatrix(adjMatrix);
        dfsScript = null;
    }

    public void copyAdjMatrix(int[][] adjMatrix) {
        this.adjMatrix = new int[adjMatrix.length][adjMatrix.length];
        for (int i = 0; i < adjMatrix.length; i++) {
            this.adjMatrix[i] = Arrays.copyOf(adjMatrix[i], adjMatrix[i].length);
        }
    }

    public int[][] getAdjMatrix() {
        return adjMatrix;
    }

    public boolean isDirected() {
        return directed;
    }

    public boolean isWeighted() {
        return weighted;
    }

    public void setDirected(boolean directed) {
        this.directed = directed;
    }

    public void setWeighted(boolean weighted) {
        this.weighted = weighted;
    }

    public ArrayList<Integer[]> breadthFirstSearch(int start) {
        int i = start, j;
        ArrayList<Integer[]> script = new ArrayList<Integer[]>();
        int edgesI = 0;
        Queue<Integer> queue = new LinkedList<>();
        boolean[] visited = new boolean[adjMatrix.length];

        System.out.println("Start: " + i);
        visited[i] = true;
        queue.add(i);

        while (!queue.isEmpty()) {
            i = queue.remove();
            for (j = 0; j < adjMatrix.length; j++) {
                if (adjMatrix[i][j] != 0) {
                    Integer[] edge = new Integer[3];
                    edge[0] = i;
                    edge[1] = j;
                    edge[2] = adjMatrix[i][j];
                    script.add(edge);
                    if (!visited[j]) {
                        visited[j] = true;
                        queue.add(j);
                    }
                }
            }
        }
        return script;
    }

    public ArrayList<Integer[]> depthFirstSearch(int start) {
        dfsScript = new ArrayList<Integer[]>();
        boolean[] visited = new boolean[adjMatrix.length];
        dfs(start, visited);

        return dfsScript;
    }

    private void dfs(int start, boolean[] visited) {
        int i = start, j;
        if (!visited[start]) {
            System.out.println("Start: " + start);
            visited[start] = true;

            for (j = 0; j < adjMatrix.length; j++) {
                if (adjMatrix[start][j] != 0) {
                    Integer[] edge = new Integer[3];
                    edge[0] = i;
                    edge[1] = j;
                    edge[2] = adjMatrix[start][j];
                    System.out.println("I: " + (i+1) + " J: " + (j+1));
                    dfsScript.add(edge);
                    if (!visited[j]) {
                        dfs(j, visited);
                    }
                }
            }
        }
    }

    public ArrayList<Integer[]> primsMST() {
        PrimsMST prim = new PrimsMST();
        prim.setV(adjMatrix.length);
        return prim.primMST(adjMatrix);
    }

    public ArrayList<Integer[]> kruskalMST() {
        int[][] kMat = new int[adjMatrix.length][adjMatrix.length];
        for (int i = 0; i < adjMatrix.length; i++) {
            for (int j = 0; j < adjMatrix.length; j++) {
                if (adjMatrix[i][j] == 0) {
                    kMat[i][j] = Integer.MAX_VALUE;
                    continue;
                }
                kMat[i][j] = adjMatrix[i][j];
            }
        }
        KruskalsMST kruskal = new KruskalsMST();
        kruskal.setV(adjMatrix.length);
        return kruskal.kruskalMST(kMat);
    }

}
