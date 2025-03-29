package GUI;

import java.awt.*;
import javax.swing.*;

public class SachGUI {
    Tool tool = new Tool();
    JPanel panel;
    public SachGUI() {
        panel = tool.createPanel(1600, 0, new GridBagLayout());
        // TODO: Design graphic
        panel.setBackground(new Color(142, 68, 173));
    }
    public JPanel getPanel() {
        return this.panel;
    } 
}
