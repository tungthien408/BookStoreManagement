package GUI;

import java.awt.*;
import javax.swing.*;

public class NhanVienGUI {
    Tool tool = new Tool();
    JPanel panel;
    public NhanVienGUI() {
        panel = tool.createPanel(1600, 0, new GridBagLayout());
        // TODO: Design graphic
        panel.setBackground(new Color(230, 126, 34));
    }
    public JPanel getPanel() {
        return this.panel;
    } 
}
