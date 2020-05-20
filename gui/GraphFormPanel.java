package gui;

import javax.swing.*;
import java.awt.*;

public class GraphFormPanel extends JPanel {
    GraphFormPanel() {
        Dimension dim = getPreferredSize();
        dim.width = 250;
        setPreferredSize(dim);
    }
}
