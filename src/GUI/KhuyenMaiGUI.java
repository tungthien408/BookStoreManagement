package src.GUI;

import java.awt.*;
import javax.swing.*;

public class KhuyenMaiGUI {
    Tool tool = new Tool();
    JPanel panel;
    public KhuyenMaiGUI() {
        panel = tool.createPanel(1600, 0, new GridBagLayout());
        // TODO: Design graphic
        panel.setBackground(new Color(241, 196, 15));
    }
    public JPanel getPanel() {
        return this.panel;
    } 
}
