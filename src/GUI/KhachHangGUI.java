package GUI;

import java.awt.*;
import javax.swing.*;

public class KhachHangGUI {
    Tool tool = new Tool();
    JPanel panel;
    public KhachHangGUI() {
        panel = tool.createPanel(1600, 0, new GridBagLayout());
        // TODO: Design graphic
        panel.setBackground(new Color(155, 89, 182));
    }
    public JPanel getPanel() {
        return this.panel;
    } 
}
