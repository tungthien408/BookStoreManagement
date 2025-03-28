package DTO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegisterForm {
    private JFrame frame;
    private JTextField txtHoTen;
    private JTextField txtSDT;
    private JPasswordField txtMatKhau;
    private JButton btnDangKy;
    private JButton btnTroVe;

    public RegisterForm() {
        frame = new JFrame();
        frame.setSize(500, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Đăng Ký Thẻ Thành Viên");
        frame.setLayout(null);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        
        JLabel header = new JLabel("Thẻ Thành Viên");
        header.setFont(new Font("Arial", Font.BOLD, 20));
        header.setHorizontalAlignment(SwingConstants.CENTER);
        header.setBounds(100, 0, 300, 50);
        frame.add(header);
        
        JLabel lbHoTen = new JLabel("Họ và tên:");
        lbHoTen.setBounds(50, 70, 100, 20);
        txtHoTen = new JTextField();
        txtHoTen.setBounds(150, 70, 300, 20);
        frame.add(lbHoTen);
        frame.add(txtHoTen);
        
        JLabel lbSDT = new JLabel("SĐT:");
        lbSDT.setBounds(50, 100, 100, 20);
        txtSDT = new JTextField();
        txtSDT.setBounds(150, 100, 300, 20);
        frame.add(lbSDT);
        frame.add(txtSDT);
        
        JLabel lbMatKhau = new JLabel("Mật khẩu:");
        lbMatKhau.setBounds(50, 130, 100, 20);
        txtMatKhau = new JPasswordField();
        txtMatKhau.setBounds(150, 130, 300, 20);
        frame.add(lbMatKhau);
        frame.add(txtMatKhau);
        
        btnDangKy = new JButton("Đăng ký");
        btnDangKy.setBounds(190, 170, 100, 30);
        btnDangKy.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnDangKy.setFocusable(false);
        frame.add(btnDangKy);
        
        btnTroVe = new JButton("<- Trở về");
        btnTroVe.setContentAreaFilled(false);
        btnTroVe.setBorderPainted(false);
        btnTroVe.setBounds(10, 10, 100, 30);
        btnTroVe.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnTroVe.setFocusable(false);

        btnTroVe.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                new LoginForm();
            }
        });
        frame.add(btnTroVe);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        new RegisterForm();
    }
}

