package GUI;

import java.awt.Color;
import java.awt.GridBagLayout;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class DangXuatGUI {
    Tool tool = new Tool();
    JPanel panel;
    int width = 1200;
    int width_sideMenu = 151;
    int height = (int) (width * 0.625);

    public DangXuatGUI() {
        panel = tool.createPanel(width - width_sideMenu, height, new BorderLayout());

    }

    public JPanel getPanel() {
        return this.panel;
    }
}
