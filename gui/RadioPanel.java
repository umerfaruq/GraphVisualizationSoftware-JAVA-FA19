package gui;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RadioPanel extends JPanel {
    private JRadioButton directedRadio;
    private JRadioButton undirectedRadio;
    private JRadioButton weightedRadio;
    private JRadioButton unweightedRadio;
    private ButtonGroup directionGroup;
    private ButtonGroup weightGroup;
    private JSlider nodesSlider;
    private JButton createMatrixBtn;

    private RadioFormListener rfl;


    public RadioPanel() {
        directedRadio = new JRadioButton("Directed");
        undirectedRadio = new JRadioButton("Undirected");
        directionGroup = new ButtonGroup();

        undirectedRadio.setSelected(true);

        directionGroup.add(directedRadio);
        directionGroup.add(undirectedRadio);

        weightedRadio = new JRadioButton("Weighted");
        unweightedRadio = new JRadioButton("Unweighted");
        weightGroup = new ButtonGroup();

        unweightedRadio.setSelected(true);

        weightGroup.add(weightedRadio);
        weightGroup.add(unweightedRadio);

        nodesSlider = new JSlider(1, 8, 3);
        createMatrixBtn = new JButton("Create Matrix");


        createMatrixBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean directed = false;
                if (directedRadio.isSelected()) directed = true;
                boolean weighted = false;
                if (weightedRadio.isSelected()) weighted = true;
                int nodes = nodesSlider.getValue();
                RadioFormEvent rfev = new RadioFormEvent(this, directed, weighted, nodes);

                if (rfl != null) {
                    rfl.radioFormEventOccurred(rfev);
                }

            }
        });

        nodesSlider.setPaintLabels(true);
        nodesSlider.setPaintTrack(true);
        nodesSlider.setPaintTicks(true);
        nodesSlider.setPaintLabels(true);

        // set spacing
        nodesSlider.setMajorTickSpacing(1);

        layoutComponents();
        Border innerBorder = BorderFactory.createTitledBorder("Type");
        Border outerBorder = BorderFactory.createEmptyBorder(5, 5, 5, 5);
        setBorder(BorderFactory.createCompoundBorder(outerBorder, innerBorder));
    }

    private void layoutComponents() {
        setLayout(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();

        gc.gridy = 0;

        gc.weightx = 1;
        gc.weighty = 0.1;

        gc.gridx = 0;
        gc.fill = GridBagConstraints.NONE;
        gc.anchor = GridBagConstraints.LINE_END;
        gc.insets = new Insets(0, 0, 0, 5);
        add(new JLabel("Direction: "), gc);

        gc.gridx = 1;
        gc.insets = new Insets(0, 0, 0, 0);
        gc.anchor = GridBagConstraints.LINE_START;
        add(directedRadio, gc);

        gc.gridx = 2;
        add(undirectedRadio, gc);


        gc.gridy++;
        gc.gridx = 0;
        gc.fill = GridBagConstraints.NONE;
        gc.anchor = GridBagConstraints.LINE_END;
        gc.insets = new Insets(0, 0, 0, 5);
        add(new JLabel("Weight: "), gc);

        gc.gridx = 1;
        gc.insets = new Insets(0, 0, 0, 0);
        gc.anchor = GridBagConstraints.LINE_START;
        add(weightedRadio, gc);

        gc.gridx = 2;
        add(unweightedRadio, gc);

        gc.gridy++;
        gc.gridx = 0;
        gc.fill = GridBagConstraints.NONE;
        gc.anchor = GridBagConstraints.LINE_END;
        add(new JLabel("Number of Nodes: "), gc);

        gc.gridx = 1;
        add(nodesSlider, gc);

        gc.gridy++;
        gc.gridx = 1;
        gc.anchor = GridBagConstraints.LINE_START;
        gc.insets = new Insets(10, 60, 0, 0);
        add(createMatrixBtn, gc);

    }

    public void setRadioFormListener(RadioFormListener rfl) {
        this.rfl = rfl;
    }
}
