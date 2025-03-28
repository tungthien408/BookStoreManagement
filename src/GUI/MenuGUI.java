package GUI;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class MenuGUI {
    public MenuGUI() {
        Tool tool = new Tool();
        JFrame frame = tool.createFrame("Book Shop Management", 1600, 1000);
        JLabel label = new JLabel("Hello, World");
        frame.add(label);
        frame.setVisible(true);
    }
}
