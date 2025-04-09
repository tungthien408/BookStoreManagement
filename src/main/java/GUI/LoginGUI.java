package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginGUI {
    private static JButton btnDangNhap = new JButton("Đăng nhập");
    static JFrame frame = new JFrame();

    public static void main(String[] args) {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 225);
        frame.getContentPane().setBackground(new Color(52, 152, 219));
        frame.setTitle("Thông tin tài khoản");
        frame.setLayout(null);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        // Khai báo JLabel header
        JLabel header = new JLabel("Quản lý cửa hàng bán sách");
        header.setFont(new Font("Arial", Font.BOLD, 22));
        header.setHorizontalAlignment(SwingConstants.CENTER);
        header.setBounds(-58, 10, 500, 50);
        // Tạo container chứa các contents đặt thành FlowLayout cho chuyên nghiệp
        JPanel container = new JPanel();
        container.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));
        container.setBounds(20, 60, 350, 200);
        container.setBackground(new Color(52, 152, 219));
        // Khai báo JLabel tài khoản
        JLabel taiKhoan = new JLabel("Nhập SĐT:            ");
        taiKhoan.setBounds(0, -10, 500, 50);
        JLabel matKhau = new JLabel("Nhập Password: ");
        taiKhoan.setFont(new Font("Arial", Font.BOLD, 13));
        matKhau.setFont(new Font("Arial", Font.BOLD, 13));
        // Khai báo JTextField tài khoản
        JTextField txtTaiKhoan = new JTextField(15);
        JPasswordField txtMatKhau = new JPasswordField(15);
        btnDangNhap.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnDangNhap.setFocusable(false);
        // Center the login button by adding a panel with FlowLayout.CENTER
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(new Color(52, 152, 219));
        buttonPanel.setPreferredSize(new Dimension(340, 35));
        buttonPanel.add(btnDangNhap);

        btnDangNhap.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String sdt = txtTaiKhoan.getText();
                String password = new String(txtMatKhau.getPassword());
                if (!sdt.startsWith("0") || sdt.length() != 10) {
                    JOptionPane.showMessageDialog(null, "SĐT không hợp lệ", "Lỗi", JOptionPane.ERROR_MESSAGE);
                } else if (password.length() == 0) {
                    JOptionPane.showMessageDialog(null, "Mật khẩu không hợp lệ", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            }

        });
        // Add components to container
        container.add(taiKhoan);
        container.add(txtTaiKhoan);
        container.add(matKhau);
        container.add(txtMatKhau);
        container.add(buttonPanel);

        frame.add(header);
        frame.add(container);
        frame.setVisible(true);
        frame.revalidate();
        frame.repaint();
    }
}
