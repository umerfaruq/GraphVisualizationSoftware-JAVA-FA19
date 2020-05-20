package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GraphFormDialog extends JDialog {
    RadioPanel radioPanel;
    MatrixPanel matrixPanel;
    private JButton applyBtn;

    private MatrixEventListener mel;

    public GraphFormDialog(JFrame parentFrame) {
        super(parentFrame, "New Graph", false);

        setSize(700, 200);
        setLayout(new BorderLayout());

        radioPanel = new RadioPanel();
        matrixPanel = new MatrixPanel();

        applyBtn = new JButton("Apply");

        add(radioPanel, BorderLayout.NORTH);


        radioPanel.setRadioFormListener(new RadioFormListener() {
            public void radioFormEventOccurred(RadioFormEvent e) {
                setSize(700, 200);
                boolean directed = e.isDirected();
                boolean weighted = e.isWeighted();
                int nodes = e.getNodes();
                matrixPanel.createMatrix(nodes);
                matrixPanel.setDirected(directed);
                matrixPanel.setWeighted(weighted);
                add(matrixPanel, BorderLayout.CENTER);
                add(applyBtn, BorderLayout.SOUTH);
                setSize(700, 500);
                System.out.println("Directed: " + directed + "\nWeighted: " + weighted + "\nNodes: " + nodes);
            }
        });

        applyBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean directed = matrixPanel.isDirected();
                boolean weighted = matrixPanel.isWeighted();
                int[][] matrix = matrixPanel.getMatrix();

                MatrixEvent me = new MatrixEvent(this, directed, weighted, matrix);
                if (mel != null) {
                    mel.matrixEventOccurred(me);
                }
            }
        });
    }

    public void setListener(MatrixEventListener mel) {
        this.mel = mel;
    }
}
