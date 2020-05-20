package gui;

import java.util.EventObject;

public class MatrixEvent extends EventObject {
    private boolean directed;
    private boolean weighted;
    private int[][] matrix;

    public MatrixEvent(Object source, boolean directed, boolean weighted, int[][] matrix) {
        super(source);

        this.directed = directed;
        this.weighted = weighted;
        this.matrix = matrix;
    }

    public boolean isDirected() {
        return directed;
    }

    public void setDirected(boolean directed) {
        this.directed = directed;
    }

    public boolean isWeighted() {
        return weighted;
    }

    public void setWeighted(boolean weighted) {
        this.weighted = weighted;
    }

    public int[][] getMatrix() {
        return matrix;
    }

    public void setMatrix(int[][] matrix) {
        this.matrix = matrix;
    }
}
