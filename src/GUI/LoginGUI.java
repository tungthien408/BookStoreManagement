package GUI;

import java.awt.GridLayout;
import javax.swing.*;

public class LoginGUI {
    // create login window
    // if login sucessfully, go to menuGUI
    JButton exit, login;
    JLabel label1, label2;
    JTextField username;
    JPasswordField pass;

    Tool tool = new Tool();

    public LoginGUI() {
        JFrame frame = tool.createFrame("Đăng nhập", 500, 312, new GridLayout(3, 1, 10, 10));
        // create textfields to enter username and password
        label1 = new JLabel("Tên đăng nhập: ");
        username = new JTextField();
        label2 = new JLabel("Mật khẩu: ");
        pass = new JPasswordField();

        // create panel to store buttons
        exit = new JButton("Hủy");
        login = new JButton("Đăng nhập");

        JPanel panel_username = tool.createPanel(1, 1, new GridLayout(1, 3));
        JPanel panel_pass = tool.createPanel(1, 1, new GridLayout(1, 3));
        JPanel panelbtn = tool.createPanel(1, 1, new GridLayout(1, 3));

        panel_username.add(label1);
        panel_username.add(username);
        panel_pass.add(label2);
        panel_pass.add(pass);

        panelbtn.add(exit);
        panelbtn.add(login);

        frame.add(panel_username);
        frame.add(panel_pass);
        frame.add(panelbtn);

        frame.setVisible(true);
    }
}
