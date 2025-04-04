package GUI;

import java.awt.GridBagLayout;

import javax.swing.JPanel;

public class ThongKeGUI {
    Tool tool = new Tool();
    JPanel panel;
    int width = 1200;
    int width_sideMenu = 151;
    int height = (int)(width * 0.625);

    public ThongKeGUI() {
        panel = tool.createPanel(width - width_sideMenu, height, new GridBagLayout());
        // TODO: Design graphic
    }
    public JPanel getPanel() {
        return this.panel;
    } 
}
