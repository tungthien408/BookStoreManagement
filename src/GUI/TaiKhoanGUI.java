package src.GUI;

import java.awt.*;
import javax.swing.*;

public class TaiKhoanGUI {
    Tool tool = new Tool();
    JPanel panel;
    public TaiKhoanGUI() {
        panel = tool.createPanel(1600, 0, new GridBagLayout());
        // TODO: Design graphic
        panel.setBackground(new Color(44, 62, 80));
    }
    public JPanel getPanel() {
        return this.panel;
    } 
}
