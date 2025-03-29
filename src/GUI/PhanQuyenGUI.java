package src.GUI;

import java.awt.*;
import javax.swing.*;

public class PhanQuyenGUI {
    Tool tool = new Tool();
    JPanel panel;
    public PhanQuyenGUI() {
        panel = tool.createPanel(1600, 0, new GridBagLayout());
        // TODO: Design graphic
        panel.setBackground(new Color(22, 160, 133));
    }
    public JPanel getPanel() {
        return this.panel;
    } 
}
