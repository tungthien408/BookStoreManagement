package src.GUI;

import java.awt.*;
import javax.swing.*;

public class TacGiaGUI {
    Tool tool = new Tool();
    JPanel panel;
    public TacGiaGUI() {
        panel = tool.createPanel(1600, 0, new GridBagLayout());
        // TODO: Design graphic
        panel.setBackground(new Color(243, 156, 18));
    }
    public JPanel getPanel() {
        return this.panel;
    } 
}
