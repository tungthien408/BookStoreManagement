package GUI;

import java.awt.Color;
import java.awt.GridBagLayout;

import javax.swing.JPanel;

public class KhuyenMaiGUI {
    Tool tool = new Tool();
    JPanel panel;
    int width = 1200;
    int width_sideMenu = 151;
    int height = (int)(width * 0.625);

    public KhuyenMaiGUI() {
        panel = tool.createPanel(width - width_sideMenu, height, new GridBagLayout());
        // TODO: Design graphic
        panel.setBackground(new Color(241, 196, 15));
    }
    public JPanel getPanel() {
        return this.panel;
    } 
}
