package src.GUI;

import java.awt.*;
import javax.swing.*;

public class NhaXuatBanGUI {
    Tool tool = new Tool();
    JPanel panel;
    public NhaXuatBanGUI() {
        panel = tool.createPanel(1600, 0, new GridBagLayout());
        // TODO: Design graphic
        panel.setBackground(new Color(192, 57, 43));
    }
    public JPanel getPanel() {
        return this.panel;
    } 
}
