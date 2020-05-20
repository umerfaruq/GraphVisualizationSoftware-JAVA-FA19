package graphpack;

import java.util.ArrayList;

public class KruskalsMST {
    public int V = 5;
    public int[] parent = new int[V];
    public int INF = Integer.MAX_VALUE;

    public ArrayList<Integer[]> script;

    // Find set of vertex i
    public int find(int i)
    {
        while (parent[i] != i)
            i = parent[i];
        return i;
    }

    // Does union of i and j. It returns
// false if i and j are already in same
// set.
    public void union1(int i, int j)
    {
        int a = find(i);
        int b = find(j);
        parent[a] = b;
    }

    // Finds MST using Kruskal's algorithm
    public ArrayList<Integer[]> kruskalMST(int cost[][])
    {
        int mincost = 0; // Cost of min MST.

        // Initialize sets of disjoint sets.
        for (int i = 0; i < V; i++)
            parent[i] = i;

        // Include minimum weight edges one by one
        int edge_count = 0;

        script = new ArrayList<Integer[]>();
        while (edge_count < V - 1)
        {
            int min = INF, a = -1, b = -1;
            for (int i = 0; i < V; i++)
            {
                for (int j = 0; j < V; j++)
                {
                    if (find(i) != find(j) && cost[i][j] < min)
                    {
                        min = cost[i][j];
                        a = i;
                        b = j;
                    }
                }
            }

            union1(a, b);
            Integer[] edge = new Integer[3];
            edge_count++;
            edge[0] = a;
            edge[1] = b;
            edge[2] = min;
            script.add(edge);

            mincost += min;
        }
        return script;
    }

    public void setV(int nodes) {
        V = nodes;
    }
}
