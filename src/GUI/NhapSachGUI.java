package src.GUI;

import java.awt.*;
import javax.swing.*;

public class NhapSachGUI {
    Tool tool = new Tool();
    JPanel panel;
    public NhapSachGUI() {
        panel = tool.createPanel(1600, 0, new GridBagLayout());
        // TODO: Design graphic
        panel.setBackground(new Color(26, 188, 156));
    }
    public JPanel getPanel() {
        return this.panel;
    } 
}
