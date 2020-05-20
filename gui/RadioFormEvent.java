package gui;

import java.util.EventObject;

public class RadioFormEvent extends EventObject {
    private boolean directed;
    private boolean weighted;
    private int nodes;

    public RadioFormEvent(Object source, boolean directed, boolean weighted, int nodes) {
        super(source);

        this.directed = directed;
        this.weighted = weighted;
        this.nodes = nodes;
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

    public int getNodes() {
        return nodes;
    }

    public void setNodes(int nodes) {
        this.nodes = nodes;
    }
}
