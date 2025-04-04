package GUI;

import java.awt.Color;
import java.awt.GridBagLayout;

import javax.swing.JPanel;

public class TaiKhoanGUI {
    Tool tool = new Tool();
    JPanel panel;
    int width = 1200;
    int width_sideMenu = 151;
    int height = (int)(width * 0.625);

    public TaiKhoanGUI() {
        panel = tool.createPanel(width - width_sideMenu, height, new GridBagLayout());
        // TODO: Design graphic
        panel.setBackground(new Color(44, 62, 80));
    }
    public JPanel getPanel() {
        return this.panel;
    } 
}
