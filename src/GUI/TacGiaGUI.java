package GUI;

import java.awt.Color;
import java.awt.GridBagLayout;

import javax.swing.JPanel;

public class TacGiaGUI {
    Tool tool = new Tool();
    JPanel panel;
    int width = 1200;
    int width_sideMenu = 151;
    int height = (int)(width * 0.625);

    public TacGiaGUI() {
        panel = tool.createPanel(width - width_sideMenu, height, new GridBagLayout());
        // TODO: Design graphic
        panel.setBackground(new Color(243, 156, 18));
    }
    public JPanel getPanel() {
        return this.panel;
    } 
}
