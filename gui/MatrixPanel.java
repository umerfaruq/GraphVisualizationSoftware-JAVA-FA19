package gui;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;



public class MatrixPanel extends JPanel implements ActionListener{
    private NodeButton[][] matrix;
    private boolean directed;
    private boolean weighted;

    public MatrixPanel() {
        setView();
    }

    private void setView() {
        setLayout(new GridBagLayout());
        Border innerBorder = BorderFactory.createTitledBorder("Adjacency Matrix");
        Border outerBorder = BorderFactory.createEmptyBorder(5, 5, 5, 5);
        setBorder(BorderFactory.createCompoundBorder(outerBorder, innerBorder));
    }
    public void setDirected(boolean directed) {
        this.directed = directed;
    }

    public void setWeighted(boolean weighted) {
        this.weighted = weighted;
    }

    public void createMatrix(int nodes) {
        removeAll();
        setView();
        matrix = new NodeButton[nodes][nodes];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                matrix[i][j] = new NodeButton();
                matrix[i][j].i = i;
                matrix[i][j].j = j;
                matrix[i][j].setText("0");
                matrix[i][j].addActionListener(this);
            }
        }
        layoutComponents();
    }

    private void layoutComponents() {
        GridBagConstraints gc = new GridBagConstraints();
        gc.gridy = 0;
        gc.gridx = 1;
        gc.weightx = 0.1;
        gc.weighty = 0.1;
        gc.insets = new Insets(0, 0, 0, 0);

        for (char c = 97; c < 97 + matrix.length; c++) {
            add(new JLabel(Character.toString(c)), gc);
            gc.gridx++;
        }

        for (int i = 0; i < matrix.length; i++) {
            gc.gridy++;
            gc.gridx = 0;
            add(new JLabel(Character.toString((char) 97 + i)), gc);
            for (int j = 0; j < matrix[i].length; j++) {
                gc.gridx++;
                add(matrix[i][j], gc);
            }
        }
    }

    public int[][] getMatrix() {
        int[][] matrixInt = new int[matrix.length][matrix.length];

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                matrixInt[i][j] = Integer.parseInt(matrix[i][j].getText());
            }
        }
        return matrixInt;
    }

    public boolean isDirected() {
        return directed;
    }

    public boolean isWeighted() {
        return weighted;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        NodeButton button= (NodeButton) e.getSource();
        if (!weighted) {
            if (button.getText().equals("0")) {
                button.setText("1");
            } else {
                button.setText("0");
            }
        } else {
            button.setText(Integer.toString(Integer.parseInt(button.getText()) + 1));
        }

        if (!directed) {
            matrix[button.j][button.i].setText(button.getText());
        }
    }
}
