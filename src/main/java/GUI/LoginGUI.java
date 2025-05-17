package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import BUS.TaiKhoanNVBUS;

public class LoginGUI {
        LoginGUI() {
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
                panel2.setBackground(new Color(0, 36, 107));
                frame.add(panel1, BorderLayout.EAST);
                frame.add(panel2, BorderLayout.WEST);

                JLabel label1 = new JLabel("CỬA HÀNG SÁCH");
                JLabel label2 = new JLabel("ĐĂNG NHẬP VÀO HỆ THỐNG");
                // Use relative path for the image
                ImageIcon image = new ImageIcon(
                                "images/book-icon-transparent-image.png");
                label1.setFont(new Font("Arial", Font.PLAIN, 24));
                label1.setHorizontalAlignment(SwingConstants.CENTER);
                label1.setForeground(Color.WHITE);
                label2.setFont(new Font("Arial", Font.BOLD, 24));
                label2.setHorizontalAlignment(SwingConstants.CENTER);
                label2.setForeground(new Color(0, 36, 107));

                // Add padding to move label down
                JPanel labelPanel = new JPanel(new BorderLayout());
                labelPanel.setBackground(new Color(0, 36, 107));
                labelPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
                labelPanel.add(label1, BorderLayout.CENTER);
                panel2.add(labelPanel, BorderLayout.NORTH);
                panel1.add(label2, BorderLayout.NORTH);

                ImageIcon ScaledImage = new ImageIcon(image.getImage().getScaledInstance(260, 260, Image.SCALE_SMOOTH));

                JLabel imageLabel = new JLabel(ScaledImage);
                imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
                panel2.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 13));
                panel2.add(imageLabel, BorderLayout.CENTER);

                // Create login panel with GridBagLayout
                JPanel loginPanel = tool.createPanel(300, 400, new GridBagLayout());
                panel1.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

                GridBagConstraints gbc = new GridBagConstraints();
                gbc.fill = GridBagConstraints.HORIZONTAL;
                gbc.insets = new Insets(10, 10, 10, 10);
                gbc.gridx = 0;
                gbc.gridy = 0;
                gbc.gridwidth = 1;

                // Icon user
                ImageIcon imageUser = new ImageIcon(
                                "images/user.png");
                ImageIcon scaledImageUser = new ImageIcon(
                                imageUser.getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH));
                JLabel iconLabelUser = new JLabel(scaledImageUser);
                iconLabelUser.setHorizontalAlignment(SwingConstants.CENTER);

                // Icon password
                ImageIcon imagePassword = new ImageIcon(
                                "images/password.png");
                ImageIcon scaledImagePassword = new ImageIcon(
                                imagePassword.getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH));
                JLabel iconLabelPassword = new JLabel(scaledImagePassword);
                iconLabelPassword.setHorizontalAlignment(SwingConstants.CENTER);

                // Create and style components
                JLabel nhapMaNV = new JLabel("Nhập Mã Nhân Viên: ");
                nhapMaNV.setFont(new Font("Arial", Font.BOLD, 16));
                nhapMaNV.setForeground(new Color(0, 36, 107));

                // Create panel to hold text field and icon for username
                JPanel userPanel = new JPanel(new BorderLayout(5, 0));
                JTextField textFieldMaNV = new JTextField(20);
                textFieldMaNV.setText("NV01");
                textFieldMaNV.setFont(new Font("Arial", Font.PLAIN, 16));
                userPanel.add(textFieldMaNV, BorderLayout.CENTER);
                userPanel.add(iconLabelUser, BorderLayout.WEST);

                JLabel nhapMatKhau = new JLabel("Nhập Mật Khẩu: ");
                nhapMatKhau.setFont(new Font("Arial", Font.BOLD, 16));
                nhapMatKhau.setForeground(new Color(0, 36, 107));

                // Create panel to hold text field and icon for password
                JPanel passwordPanel = new JPanel(new BorderLayout(5, 0));
                JPasswordField textFieldMatKhau = new JPasswordField(20);
                textFieldMatKhau.setText("admin");
                textFieldMatKhau.setFont(new Font("Arial", Font.PLAIN, 16));
                passwordPanel.add(textFieldMatKhau, BorderLayout.CENTER);
                passwordPanel.add(iconLabelPassword, BorderLayout.WEST);

                JButton buttonDangNhap = new JButton("Đăng nhập");
                buttonDangNhap.setFont(new Font("Arial", Font.BOLD, 16));
                buttonDangNhap.setBackground(new Color(0, 36, 107));
                buttonDangNhap.setForeground(Color.WHITE);
                buttonDangNhap.setCursor(new Cursor(Cursor.HAND_CURSOR));
                buttonDangNhap.setFocusable(false);

                // Add components to login panel
                loginPanel.add(nhapMaNV, gbc);

                gbc.gridy = 1;
                loginPanel.add(userPanel, gbc);

                gbc.gridy = 2;
                loginPanel.add(nhapMatKhau, gbc);

                gbc.gridy = 3;
                loginPanel.add(passwordPanel, gbc);

                gbc.gridy = 4;
                gbc.insets = new Insets(20, 10, 10, 10); // More padding above button
                loginPanel.add(buttonDangNhap, gbc);

                buttonDangNhap.addActionListener(e -> {
                        String maNV = textFieldMaNV.getText().trim().toUpperCase();
                        String matKhau = new String(textFieldMatKhau.getPassword()).trim();
                        if (maNV.isEmpty() || matKhau.isEmpty()) {
                                JOptionPane.showMessageDialog(null, "Vui lòng nhập đầy đủ thông tin", "Lỗi",
                                                JOptionPane.ERROR_MESSAGE);
                                return;
                        }

                        TaiKhoanNVBUS taiKhoanNVBUS = new TaiKhoanNVBUS();
                        if (taiKhoanNVBUS.checkLogin(maNV, matKhau, 0) == true) {
                                JOptionPane.showMessageDialog(null, "Đăng nhập thành công", "Thông báo",
                                                JOptionPane.INFORMATION_MESSAGE);
                                frame.dispose();
                                new MenuGUI(taiKhoanNVBUS.getTaiKhoanById(maNV));
                        } else if (taiKhoanNVBUS.checkLogin(maNV, matKhau, 1) == true) {
                                JOptionPane.showMessageDialog(null, "Tài khoản đã bị khóa", "Thông báo",
                                                JOptionPane.ERROR_MESSAGE);
                        } else {
                                JOptionPane.showMessageDialog(null, "Tên đăng nhập hoặc mật khẩu chưa đúng!",
                                                "Thông báo", JOptionPane.ERROR_MESSAGE);
                        }

                });
                panel1.add(loginPanel, BorderLayout.CENTER);

                frame.setVisible(true);
        }
}