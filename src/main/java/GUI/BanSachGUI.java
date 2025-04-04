package GUI;

import java.awt.*;
import javax.swing.*;

public class BanSachGUI {
    Tool tool = new Tool();
    JPanel panel;
    public BanSachGUI() {
        panel = tool.createPanel(1600, 0, new GridBagLayout());
        panel.setBackground(new Color(52, 152, 219));
        // TODO: Design graphic
    }
    public JPanel getPanel() {
        return this.panel;
    } 
}
