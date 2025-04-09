package GUI; 
import javax.swing.*;
import java.awt.*;

public class LoginGUIImproved {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Login");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(700, 400);
        frame.getContentPane().setBackground(new Color(52, 152, 219));
        frame.setTitle("Thông tin tài khoản");
        frame.setLayout(new BorderLayout());
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);

        Tool tool = new Tool();
        JPanel panel1 = tool.createPanel(400, 400, new BorderLayout());
        JPanel panel2 = tool.createPanel(300, 400, new BorderLayout());
        panel1.setBackground(new Color(52, 10, 219));
        panel2.setBackground(new Color(52, 152, 219));
        frame.add(panel1, BorderLayout.EAST);
        frame.add(panel2, BorderLayout.WEST);

        JLabel label1 = new JLabel("NHÀ SÁCH ONLINE");

        // Use relative path for the image
        ImageIcon image = new ImageIcon("images/DogLogo.jpg");
        label1.setFont(new Font("Arial", Font.BOLD, 24));
        label1.setHorizontalAlignment(SwingConstants.CENTER);
        label1.setForeground(Color.WHITE);

        // Add padding to move label down
        JPanel labelPanel = new JPanel(new BorderLayout());
        labelPanel.setBackground(new Color(52, 152, 219));
        labelPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
        labelPanel.add(label1, BorderLayout.CENTER);
        panel2.add(labelPanel, BorderLayout.NORTH);

        ImageIcon ScaledImage = new ImageIcon(image.getImage().getScaledInstance(250, 250, Image.SCALE_SMOOTH));

        JLabel imageLabel = new JLabel(ScaledImage);
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panel2.add(imageLabel, BorderLayout.CENTER);

        JPanel loginPanel = tool.createPanel(100,100,new FlowLayout());        
        loginPanel.setBackground(Color.RED);
        panel1.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel1.add(loginPanel, BorderLayout.CENTER);
        
        frame.setVisible(true);
    }
}
