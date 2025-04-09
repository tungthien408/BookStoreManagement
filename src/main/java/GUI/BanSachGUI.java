package GUI;

import java.awt.Color;
import java.awt.GridBagLayout;

import javax.swing.JPanel;

public class BanSachGUI {
    Tool tool = new Tool();
    JPanel panel;
    int width = 1200;
    int width_sideMenu = 151;
    int height = (int)(width * 0.625);

    public BanSachGUI() {
        panel = tool.createPanel(width - width_sideMenu, height, new GridBagLayout());
        panel.setBackground(new Color(202, 220, 252));
        // TODO: Design graphic
    }
    public JPanel getPanel() {
        return this.panel;
    } 
}
