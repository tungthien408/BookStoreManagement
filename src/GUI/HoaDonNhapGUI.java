package src.GUI;

import java.awt.*;
import javax.swing.*;

public class HoaDonNhapGUI {
    Tool tool = new Tool();
    JPanel panel;
    public HoaDonNhapGUI() {
        panel = tool.createPanel(1600, 0, new GridBagLayout());
        // TODO: Design graphic
        panel.setBackground(new Color(46, 204, 113));
    }
    public JPanel getPanel() {
        return this.panel;
    } 
}
