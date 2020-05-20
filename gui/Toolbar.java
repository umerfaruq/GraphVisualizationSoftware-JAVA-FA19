package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Toolbar extends JPanel implements ActionListener{
    private JButton newBtn;
    private ToolbarListener listener;

    public Toolbar() {
        setBorder(BorderFactory.createEtchedBorder());

        newBtn = new JButton("New");
        newBtn.addActionListener(this);

        setLayout(new FlowLayout(FlowLayout.LEFT));



        add(newBtn);
    }

    public void setListener(ToolbarListener listener) {
        this.listener = listener;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton clicked = (JButton) e.getSource();
        if (clicked == newBtn) {
            if (listener != null) {
                listener.toolbarAction(clicked.getText());
            }
        }
    }
}
