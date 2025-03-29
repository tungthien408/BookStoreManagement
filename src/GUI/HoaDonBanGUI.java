package src.GUI;

import java.awt.*;
import javax.swing.*;

public class HoaDonBanGUI {
    Tool tool = new Tool();
    JPanel panel;
    public HoaDonBanGUI() {
        panel = tool.createPanel(1600, 0, new GridBagLayout());
        // TODO: Design graphic
        panel.setBackground(new Color(231, 76, 60));
    }
    public JPanel getPanel() {
        return this.panel;
    } 
}
