package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Arc2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;



public class GraphPanel extends JPanel {
    private ArrayList<Ellipse2D> nodes;
    private ArrayList<Line2D> edges;
    private ArrayList<Integer> edgeWeights;
    private ArrayList<Arc2D> selfEdges;
    private ArrayList<Ellipse2D> arrows;
    private Graphics2D g2d;
    private int[][] nodePos;
    private boolean directed;
    private boolean weighted;

    private static final int CIRCLE_SIZE = 70;

    public GraphPanel() {
        super();
        setBackground(Color.darkGray);
        nodes = new ArrayList<Ellipse2D>();
        edges = new ArrayList<Line2D>();
        edgeWeights = new ArrayList<Integer>();
        selfEdges = new ArrayList<Arc2D>();
        arrows = new ArrayList<Ellipse2D>();
        int x = CIRCLE_SIZE + 10, y = CIRCLE_SIZE - (-30);
        nodePos = new int[][]{
                {100 + x, 50 + y},
                {250 + x, 50 + y},
                {100 + x, 200 + y},
                {250 + x, 200 + y},
                {175 + x, 340 + y},
                {385 + x, 125 + y},
                {-35 + x, 125 + y},
                {175 + x, -90 + y}
        };

    }

    public void clearPanel() {
        clearEdges();
        clearNodes();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g2d = (Graphics2D) g;
        g2d.setStroke(new BasicStroke(2));
        for (Line2D edge : edges) {
            g2d.setPaint(Color.red);
            g2d.draw(edge);
        }
        for (Arc2D selfEdge : selfEdges) {
            g2d.setPaint(Color.red);
            g2d.draw(selfEdge);
        }
        g2d.setFont(new Font("serif", Font.BOLD, 30));
        for (Ellipse2D node : nodes) {
            g2d.setPaint(Color.gray);
            g2d.fill(node);
            g2d.setPaint(Color.WHITE);
            g2d.drawString(Character.toString('a' + nodes.indexOf(node)), (int) node.getCenterX(), (int) node.getCenterY());
            g2d.setPaint(Color.gray);
            g2d.draw(node);
        }
        for (Ellipse2D arrow : arrows) {
            if (directed) {
                g2d.setPaint(Color.red);
                g2d.fill(arrow);
                g2d.draw(arrow);
            }
            if (weighted) {
                g2d.setPaint(Color.white);
                g2d.setFont(new Font("Arial", Font.BOLD, (int) arrow.getHeight() + 10));
                FontMetrics metrics = g2d.getFontMetrics();
                Rectangle2D rect = new Rectangle2D.Double(arrow.getMinX(), arrow.getMinY(), arrow.getWidth(), arrow.getHeight());
                String weight = "" + edgeWeights.get(arrows.indexOf(arrow)).toString();
                int x = (int) (rect.getX() + (rect.getWidth() - metrics.stringWidth(weight)) / 2);
                int y = (int) (rect.getY() + ((rect.getHeight() - metrics.getHeight()) / 2) + metrics.getAscent());
                g2d.drawString(weight, x, y);
            }
        }
    }

    public void addNode() {
        Ellipse2D circle = new Ellipse2D.Double(nodePos[nodes.size()][0], nodePos[nodes.size()][1], CIRCLE_SIZE, CIRCLE_SIZE);

        nodes.add(circle);
        repaint();
    }

    public void connectNode(int i, int j, int weight) {
        if (i == j) {
            selfConnectNode(i, weight);
            return;
        }
        double bx = nodes.get(i).getCenterX();
        double ax = nodes.get(j).getCenterX();
        double by = nodes.get(i).getCenterY();
        double ay = nodes.get(j).getCenterY();
        double bx_ax2 = Math.pow(bx - ax, 2);
        double by_ay2 = Math.pow(by - ay, 2);
        double r_bx_ax2pby_ay2 = Math.sqrt(bx_ax2 + by_ay2);
        double cx = ax + ((nodes.get(i).getWidth() / 2) * ((bx - ax) / r_bx_ax2pby_ay2));
        double cy = ay + ((nodes.get(i).getWidth() / 2)  * ((by - ay) / r_bx_ax2pby_ay2));
        Line2D edge = new Line2D.Double(nodes.get(i).getCenterX(), nodes.get(i).getCenterY(), cx, cy);
        Ellipse2D arrowHead = new Ellipse2D.Double(edge.getX2() - 8 / 2.0, edge.getY2() - 8 / 2.0, 12, 12);
        edges.add(edge);
        edgeWeights.add(weight);
        arrows.add(arrowHead);
        repaint();
    }

    private void selfConnectNode(int i, int weight) {
        Arc2D selfEdge = new Arc2D.Double(nodes.get(i).getCenterX() - 50, nodes.get(i).getCenterY() - 30, CIRCLE_SIZE - 20, CIRCLE_SIZE - 20, 30, 270, Arc2D.PIE);
        Ellipse2D arrowHead = new Ellipse2D.Double((nodes.get(i).getCenterX() - CIRCLE_SIZE / 2) + 10, nodes.get(i).getCenterY() - CIRCLE_SIZE / 2, 12, 12);
        selfEdges.add(selfEdge);
        edgeWeights.add(weight);
        arrows.add(arrowHead);
        repaint();
    }

    public void setDirectedAndWeighted(boolean directed, boolean weighted) {
        this.directed = directed;
        this.weighted = weighted;
    }

    public void clearEdges() {
        edges.clear();
        edgeWeights.clear();
        selfEdges.clear();
        arrows.clear();
        repaint();
    }

    public void clearNodes() {
        nodes.clear();
        repaint();
    }

    public int getNumberOfEdges() {
        return edges.size() + selfEdges.size();
    }


}
