package DTO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginForm implements ActionListener {
    private static JButton btnDangNhap = new JButton("Đăng nhập");
    static JButton btnDangKy = new JButton("Đăng ký");
    static JFrame frame = new JFrame();

    public LoginForm() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 225);
        frame.getContentPane().setBackground(new Color(216, 215, 215));
        frame.setTitle("Thông tin tài khoản");
        frame.setLayout(null);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        // Khai báo JLabel header
        JLabel header = new JLabel("Quản lý cửa hàng bán sách");
        header.setFont(new Font("Arial", Font.BOLD, 20));
        header.setHorizontalAlignment(SwingConstants.CENTER);
        header.setBounds(-58, 0, 500, 50);
        // Tạo container chứa các contents đặt thành FlowLayout cho chuyên nghiệp
        JPanel container = new JPanel();
        container.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));
        container.setBounds(20, 60, 350, 200);
        container.setBackground(new Color(216, 215, 215));
        // Khai báo JLabel tài khoản
        JLabel taiKhoan = new JLabel("Nhập SĐT:           ");
        taiKhoan.setBounds(0, -10, 500, 50);
        JLabel matKhau = new JLabel("Nhập Password: ");
        // Khai báo JTextField tài khoản
        JTextField txtTaiKhoan = new JTextField(20);
        JPasswordField txtMatKhau = new JPasswordField(20);
        btnDangKy.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnDangNhap.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnDangKy.addActionListener(this);
        btnDangNhap.addActionListener(this);
        btnDangNhap.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnDangKy.setCursor(new Cursor(Cursor.HAND_CURSOR));
        container.add(taiKhoan);
        container.add(txtTaiKhoan);
        container.add(matKhau);
        container.add(txtMatKhau);
        container.add(btnDangNhap);
        container.add(btnDangKy);
        frame.add(header);
        frame.add(container);
        frame.setVisible(true);
        frame.revalidate();
        frame.repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnDangKy) {
            frame.dispose();

            new RegisterForm();
        }
    }

    public static void main(String[] args) {
        new LoginForm();
    }
}

