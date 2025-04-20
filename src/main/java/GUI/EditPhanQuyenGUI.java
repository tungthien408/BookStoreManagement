package GUI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import com.google.protobuf.Value;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class EditPhanQuyenGUI extends JFrame {
    private JTextField txtMaQuyen;
    private JComboBox<String> cbNhomQuyen;
    private JTable tblPhanQuyen;
    private JButton btnLuu, btnHuy;
    private static final Color MENU_BACKGROUND = new Color(0, 36, 107);
    private static final Color MENU_HOVER = new Color(15, 76, 104);

    public EditPhanQuyenGUI(String ValueMaQuyen, String ValueNhomQuyen) {
        super("Book Shop Management - Phân Quyền - Sửa Quyền");
        initUI(ValueMaQuyen, ValueNhomQuyen);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
        pack();
        setSize(1100, 700);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void initUI(String ValueMaQuyen, String ValueNhomQuyen) {
        // --- Top panel: Mã quyền + Tên nhóm quyền ---
        JPanel pnlTop = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 20));
        JLabel maQuyen = new JLabel("Mã quyền:");
        pnlTop.add(maQuyen);
        maQuyen.setFont(new Font("Arial", Font.BOLD, 17));
        maQuyen.setPreferredSize(new Dimension(83, 30));
        txtMaQuyen = new JTextField(20); // Increased width
        txtMaQuyen.setPreferredSize(new Dimension(300, 30)); // Increased width
        txtMaQuyen.setFont(new Font("Arial", Font.PLAIN, 16));
        txtMaQuyen.setText(ValueMaQuyen);
        txtMaQuyen.setEditable(false);

        pnlTop.add(txtMaQuyen);
        JLabel tenNhomQuyen = new JLabel("Tên nhóm quyền:");
        tenNhomQuyen.setFont(new Font("Arial", Font.BOLD, 17));
        pnlTop.add(tenNhomQuyen);
        JTextField txtNhomQuyen = new JTextField(20); // Increased width
        txtNhomQuyen.setPreferredSize(new Dimension(300, 30)); // Increased width
        txtNhomQuyen.setFont(new Font("Arial", Font.PLAIN, 16));
        txtNhomQuyen.setEditable(false);
        txtNhomQuyen.setText(ValueNhomQuyen);
        pnlTop.add(tenNhomQuyen);
        pnlTop.add(txtNhomQuyen);

        add(pnlTop, BorderLayout.NORTH);

        // --- Center: table of chức năng + 4 check‑boxes each ---
        String[] colNames = { "CÁC CHỨC NĂNG", "THÊM", "XÓA", "SỬA", "XEM" };
        Object[][] data = {
                { "Quản lí Sản Phẩm", false, false, false, false },
                { "Quản lí Cấu Hình", false, false, false, false },
                { "Quản lí Khách Hàng", false, false, false, false },
                { "Quản lí Nhân Viên", false, false, false, false },
                { "Quản lí Tài Khoản", false, false, false, false },
                { "Quản lí Nhà Cung Cấp", false, false, false, false },
                { "Quản lí Nhập Hàng", false, false, false, false },
                { "Quản lí Xuất Hàng", false, false, false, false },
                { "Quản lí Phân Quyền", false, false, false, false },
                { "Quản lí Bảo Hành", false, false, false, false },
                { "Quản lí Thống Kê", false, false, false, false },
        };

        DefaultTableModel model = new DefaultTableModel(data, colNames) {
            @Override
            public Class<?> getColumnClass(int col) {
                return col == 0 ? String.class : Boolean.class;
            }

            @Override
            public boolean isCellEditable(int row, int col) {
                return col >= 1; // only check‑boxes editable
            }
        };

        tblPhanQuyen = new JTable(model);
        tblPhanQuyen.getTableHeader().setReorderingAllowed(false);
        tblPhanQuyen.getTableHeader().setFont(tblPhanQuyen.getFont().deriveFont(Font.BOLD));
        tblPhanQuyen.getColumnModel().getColumn(0).setPreferredWidth(250);
        tblPhanQuyen.setRowHeight(50);
        tblPhanQuyen.setFont(new Font("Arial", Font.PLAIN, 16));
        tblPhanQuyen.getTableHeader().setFont(new Font("Arial", Font.BOLD, 18));
        tblPhanQuyen.getTableHeader().setBackground(MENU_BACKGROUND);
        tblPhanQuyen.getTableHeader().setForeground(Color.WHITE);
        tblPhanQuyen.setBackground(getBackground());
        tblPhanQuyen.setShowGrid(false);
        JScrollPane scroll = new JScrollPane(tblPhanQuyen);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);

        scroll.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 20));
        scroll.setPreferredSize(new Dimension(1000, 500));
        add(scroll, BorderLayout.CENTER);

        // --- Bottom: LƯU / HỦY ---
        btnLuu = new JButton("LƯU");
        btnLuu.setBackground(new Color(0, 200, 0));
        btnLuu.setForeground(Color.BLACK);
        btnLuu.setFocusPainted(false);
        btnLuu.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnLuu.setPreferredSize(new Dimension(150, 40));
        btnLuu.setBorderPainted(false);
        btnHuy = new JButton("HỦY");
        btnHuy.setBackground(new Color(0, 120, 255));
        btnHuy.setForeground(Color.BLACK);
        btnHuy.setFocusPainted(false);
        btnHuy.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnHuy.setPreferredSize(new Dimension(150, 40));
        btnHuy.setBorderPainted(false);
        btnHuy.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int result = JOptionPane.showConfirmDialog(null, "Bạn có chắc chắn muốn hủy?", "Xác nhận",
                        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (result == JOptionPane.YES_OPTION) {
                    dispose(); // tắt cửa sổ
                }
            }
        });
        JPanel pnlButtons = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 20));
        pnlButtons.add(btnLuu);
        pnlButtons.add(btnHuy);
        add(pnlButtons, BorderLayout.SOUTH);
    }
}
