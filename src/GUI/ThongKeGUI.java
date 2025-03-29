package GUI;

import java.awt.GridBagLayout;
import javax.swing.*;

public class ThongKeGUI {
    Tool tool = new Tool();
    JPanel panel;
    public ThongKeGUI() {
        panel = tool.createPanel(1600, 0, new GridBagLayout());
        // TODO: Design graphic
    }
    public JPanel getPanel() {
        return this.panel;
    } 
}
