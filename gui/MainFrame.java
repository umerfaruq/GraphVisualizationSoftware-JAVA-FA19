package gui;

import graphpack.Graph;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;


public class MainFrame extends JFrame implements ActionListener{
    private Toolbar toolbar;
    private GraphPanel graphPanel;
    private GraphFormDialog graphFormDialog;
    private Graph graph;

    private ArrayList<Integer[]> script;
    private int indScript;

    public MainFrame() {
        super("Graph Visualization Software");

        setLayout(new BorderLayout());

        toolbar = new Toolbar();
        graphPanel = new GraphPanel();
        graphFormDialog = new GraphFormDialog(this);

        setJMenuBar(createMenuBar());


        toolbar.setListener(new ToolbarListener() {
            @Override
            public void toolbarAction(String button) {
                if (button == "New") {
                    graphPanel.clearPanel();
                    graphFormDialog.setVisible(true);
                }
            }
        });

        graphFormDialog.setListener(new MatrixEventListener() {
            @Override
            public void matrixEventOccurred(MatrixEvent e) {
                graph = new Graph();
                graphPanel.clearPanel();
                graphPanel.setDirectedAndWeighted(e.isDirected(), e.isWeighted());
                int[][] matrix = e.getMatrix();
                graph.copyAdjMatrix(matrix);
                graph.setDirected(e.isDirected());
                graph.setWeighted(e.isWeighted());
                System.out.println(matrix.length);
                for (int i = 0; i < matrix.length; i++) {
                    graphPanel.addNode();
                }

                for (int i = 0; i < matrix.length; i++) {
                    for (int j = 0; j < matrix[i].length; j++) {
                        if (matrix[i][j] != 0) {
                            graphPanel.connectNode(i, j, matrix[i][j]);
                        }
                    }
                }

            }
        });

        add(toolbar, BorderLayout.NORTH);
        add(graphPanel, BorderLayout.CENTER);

        setSize(600, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        JMenu fileMenu = new JMenu("File");
        JMenu viewMenu = new JMenu("View");

        JMenuItem newItem = new JMenuItem("New");
        //JMenuItem openItem = new JMenuItem("Open");
        //JMenuItem saveItem = new JMenuItem("Save");
        JMenuItem exitItem = new JMenuItem("Exit");

        newItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                graphPanel.clearPanel();
                graphFormDialog.setVisible(true);
                graph = new Graph();
            }
        });

        exitItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        fileMenu.add(newItem);
        //fileMenu.add(openItem);
        //fileMenu.add(saveItem);
        fileMenu.add(exitItem);

        JMenuItem clearItem = new JMenuItem("Clear Graph");
        JMenuItem drawItem = new JMenuItem("Draw Graph");

        clearItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                graphPanel.clearPanel();
            }
        });

        drawItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (graph == null) {
                    return;
                }
                int[][] matrix = graph.getAdjMatrix();
                for (int i = 0; i < matrix.length; i++) {
                    graphPanel.addNode();
                }
                for (int i = 0; i < matrix.length; i++) {
                    for (int j = 0; j < matrix[i].length; j++) {
                        if (matrix[i][j] != 0) {
                            graphPanel.connectNode(i, j, matrix[i][j]);
                        }
                    }
                }
            }
        });

        JMenu traversalMenu = new JMenu("Traversal");
        JMenuItem dfsItem = new JMenuItem("Depth First Search");
        JMenuItem bfsItem = new JMenuItem("Breadth First Search");

        bfsItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (graph == null) {
                    return;
                }
                graphPanel.clearEdges();
                script = graph.breadthFirstSearch(0);
                indScript = 0;
                Timer timer = new Timer(1000, MainFrame.this);
                timer.start();
            }
        });

        traversalMenu.add(dfsItem);
        traversalMenu.add(bfsItem);

        JMenu MSTMenu = new JMenu("Minimum Spanning Tree");

        JMenuItem primItem = new JMenuItem("Prim's Algorithm");
        JMenuItem kruskalItem = new JMenuItem("Kruskal's Algorithm");

        MSTMenu.add(primItem);
        MSTMenu.add(kruskalItem);

        primItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (graph == null) {
                    return;
                }
                graphPanel.clearEdges();
                script = graph.primsMST();
                indScript = 0;
                Timer timer = new Timer(1000, MainFrame.this);
                timer.start();
            }
        });

        kruskalItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (graph == null) {
                    return;
                }
                graphPanel.clearEdges();
                script = graph.kruskalMST();
                indScript = 0;
                Timer timer = new Timer(1000, MainFrame.this);
                timer.start();
            }
        });

        traversalMenu.add(MSTMenu);

        dfsItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (graph == null) {
                    return;
                }
                graphPanel.clearEdges();
                script = graph.depthFirstSearch(0);
                indScript = 0;
                int time = graph.isDirected() ? 1000 : 500;
                Timer timer = new Timer(1000, MainFrame.this);
                timer.start();
            }
        });

        viewMenu.add(clearItem);
        viewMenu.add(drawItem);
        viewMenu.add(traversalMenu);


        menuBar.add(fileMenu);
        menuBar.add(viewMenu);

        return menuBar;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String timer = e.getActionCommand();
        Timer timer1 = (Timer) e.getSource();
        Integer[] line = script.get(indScript);
        graphPanel.connectNode(line[0], line[1], line[2]);
        if (indScript == script.size() - 1) {
            timer1.stop();
            return;
        }
        indScript++;
    }
}
