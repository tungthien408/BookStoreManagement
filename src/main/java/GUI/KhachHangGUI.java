package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import BUS.KhachHangBUS;
import DTO.KhachHangDTO;

public class KhachHangGUI {
    Tool tool = new Tool();
    JPanel panel;
    JTextField tfSDT, tfTenKH;
    JButton btnThem, btnSua, btnXoa;
    JTable table;
    DefaultTableModel model;
    KhachHangBUS khBUS = new KhachHangBUS();

    public KhachHangGUI() {
        panel = tool.createPanel(1600, 0, new BorderLayout());
        panel.setBackground(new Color(155, 89, 182));

        JPanel inputPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        inputPanel.setBackground(new Color(155, 89, 182));
        tfSDT = new JTextField();
        tfTenKH = new JTextField();
        inputPanel.add(new JLabel("SĐT khách hàng:"));
        inputPanel.add(tfSDT);
        inputPanel.add(new JLabel("Tên khách hàng:"));
        inputPanel.add(tfTenKH);

        JPanel buttonPanel = new JPanel();
        btnThem = new JButton("Thêm");
        btnSua = new JButton("Sửa");
        btnXoa = new JButton("Xóa");
        buttonPanel.add(btnThem);
        buttonPanel.add(btnSua);
        buttonPanel.add(btnXoa);

        model = new DefaultTableModel(new String[]{"SĐT", "Tên khách hàng"}, 0);
        table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);

        panel.add(inputPanel, BorderLayout.NORTH);
        panel.add(buttonPanel, BorderLayout.CENTER);
        panel.add(scrollPane, BorderLayout.SOUTH);

        loadTable();

        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int row = table.getSelectedRow();
                tfSDT.setText(model.getValueAt(row, 0).toString());
                tfTenKH.setText(model.getValueAt(row, 1).toString());
            }
        });

        btnThem.addActionListener(e -> {
            String sdt = tfSDT.getText();
            String ten = tfTenKH.getText();
            if (!sdt.isEmpty() && !ten.isEmpty()) {
                KhachHangDTO kh = new KhachHangDTO(sdt, ten,0);
                if (khBUS.addKhachHang(kh)) {
                    JOptionPane.showMessageDialog(null, "Thêm thành công!");
                    loadTable();
                } else {
                    JOptionPane.showMessageDialog(null, "Thêm thất bại!");
                }
            }
        });

        btnSua.addActionListener(e -> {
            String sdt = tfSDT.getText();
            String ten = tfTenKH.getText();
            if (!sdt.isEmpty() && !ten.isEmpty()) {
                KhachHangDTO kh = new KhachHangDTO(sdt, ten,0);
                if (khBUS.updateKhachHang(kh)) {
                    JOptionPane.showMessageDialog(null, "Cập nhật thành công!");
                    loadTable();
                } else {
                    JOptionPane.showMessageDialog(null, "Cập nhật thất bại!");
                }
            }
        });

        btnXoa.addActionListener(e -> {
            String sdt = tfSDT.getText();
            if (!sdt.isEmpty()) {
                if (khBUS.deleteKhachHang(sdt)) {
                    JOptionPane.showMessageDialog(null, "Xóa thành công!");
                    loadTable();
                } else {
                    JOptionPane.showMessageDialog(null, "Xóa thất bại!");
                }
            }
        });
    }

    public void loadTable() {
        model.setRowCount(0);
        List<KhachHangDTO> list = khBUS.getAllKhachHang();
        if (list != null) {
            for (KhachHangDTO kh : list) {
                model.addRow(new Object[]{kh.getSdt(), kh.getHoTen(), kh.getDiem()});
            }
        }
    }

    public JPanel getPanel() {
        return this.panel;
    }
}
