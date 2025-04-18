package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import BUS.ChiTietPhieuNhapBUS;
import BUS.NXBBUS;
import BUS.NhanVienBUS;
import BUS.PhieuNhapBUS;
import BUS.SachBUS;
import DTO.ChiTietPhieuNhapDTO;
import DTO.PhieuNhapDTO;
import DTO.SachDTO;
import Service.Lib;


public class NhapSachGUI {
    Tool tool = new Tool();
    JPanel panel;
    int width = 1200;
    int width_sideMenu = 151;
    int height = (int)(width * 0.625);
    JTextField[] txt_array_top = new JTextField[5];
    JTextField[] txt_array_down = new JTextField[2];
    JTextField txt_importId, txt_employeeId, txt_nxb, txt_date, txt_total, txt_name, txt_quantity;
    JButton[] buttons = new JButton[3];
    JTable table_down, table_top;
    private int selectedRow = -1;
    private int lastSelectedRow = -1;

    List<SachDTO> sachList;
    List<ChiTietPhieuNhapDTO> chiTietPhieuNhapList;
    List<PhieuNhapDTO> phieuNhapList;

    PhieuNhapBUS phieuNhapBUS = new PhieuNhapBUS();
    NXBBUS nhaXuatBanBUS = new NXBBUS();
    SachBUS sachBUS = new SachBUS();
    NhanVienBUS nhanVienBUS = new NhanVienBUS();
    ChiTietPhieuNhapBUS chiTietPhieuNhapBUS = new ChiTietPhieuNhapBUS();

    int count = 0;

    public String getID() {
        String str = String.valueOf(count);
        while (str.length() < 3)
            str = "0" + str;
        return "PN" + str;
    }

    public NhapSachGUI() {
        txt_importId = new JTextField();
        txt_employeeId = new JTextField();
        txt_nxb = new JTextField();
        txt_date = new JTextField();
        txt_total = new JTextField();
        txt_name = new JTextField();
        txt_quantity = new JTextField();

        txt_array_top = new JTextField[]{txt_importId, txt_employeeId, txt_nxb, txt_date, txt_total};
        txt_array_down = new JTextField[]{txt_name, txt_quantity};

        panel = tool.createPanel(width - width_sideMenu, height, new BorderLayout());
        panel.add(createSearchPanel(), BorderLayout.NORTH);
        panel.add(createTable_top(), BorderLayout.WEST);

        String txt_label_top[] = {"Mã phiếu nhập", "Mã NV", "NXB", "Ngày nhập", "Tổng tiền"};
        panel.add(createDetailPanel_top(400, 30, txt_array_top, txt_label_top, null), BorderLayout.CENTER);

        JPanel paymentPanel = tool.createPanel(width - width_sideMenu, (int)(height * 0.55), new BorderLayout());
        String[] txt_label = {"Mã Sách", "Số lượng"};
        paymentPanel.add(createDetailPanel_down(500, 10, txt_array_down, txt_label, new ImageIcon("images/Book/the_little_prince.jpg")), BorderLayout.WEST);
        paymentPanel.add(createButtonPanel(), BorderLayout.SOUTH);
        paymentPanel.add(createTable_down(), BorderLayout.EAST);
        panel.add(paymentPanel, BorderLayout.SOUTH);

        // Initialize maPN and date
        phieuNhapList = phieuNhapBUS.getAllPhieuNhap();
        if (!phieuNhapList.isEmpty()) {
            String maTG = phieuNhapList.get(phieuNhapList.size() - 1).getMaPN();
            String numericPart = maTG.substring(2);
            count = Integer.parseInt(numericPart) + 1;
        }

    }

    private JPanel createTable_top() {
        String column[] = {"Mã sách", "Tên sách", "Số lượng", "Đơn giá"};
        DefaultTableModel model = new DefaultTableModel(column, 0);
        try {
            sachList = sachBUS.getAllSach();
            for (SachDTO sach : sachList) {
                model.addRow(new Object[]{
                    sach.getMaSach(),
                    sach.getTenSach(),
                    sach.getSoLuong(),
                    sach.getDonGia()
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi khi tải dữ liệu sách: " + e.getMessage());
        }
        table_top = tool.createTable(model, column);
        table_top.setDefaultEditor(Object.class, null);
        JScrollPane scrollPane = new JScrollPane(table_top);
        scrollPane.setPreferredSize(new Dimension(650, 300));
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel panelTable = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelTable.add(scrollPane);

        table_top.getSelectionModel().addListSelectionListener(e -> {
            selectedRow = table_top.getSelectedRow();
            if (selectedRow >= 0) {
                txt_array_down[0].setText((String) table_top.getValueAt(selectedRow, 0));
                txt_array_down[0].setEditable(false);
                txt_array_down[1].setEditable(true);
            }
        });

        return panelTable;
    }

    private JPanel createTable_down() {
        String column[] = {"Mã sách", "Tên sách", "Số lượng", "Đơn giá"};
        DefaultTableModel model = new DefaultTableModel(column, 0);
        table_down = tool.createTable(model, column);
        table_down.setDefaultEditor(Object.class, null);
        JScrollPane scrollPane = new JScrollPane(table_down);
        scrollPane.setPreferredSize(new Dimension(500, 300));
        scrollPane.setBorder(BorderFactory.createEmptyBorder(20, 10, 10, 10));

        table_down.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                selectedRow = table_down.getSelectedRow();
                if (selectedRow == lastSelectedRow && selectedRow >= 0) {
                    table_down.clearSelection();
                    for (JTextField txt : txt_array_down) {
                        txt.setText("");
                        txt.setEditable(true);
                    }
                    lastSelectedRow = -1;
                } else if (selectedRow >= 0) {
                    txt_array_down[0].setText((String) table_down.getValueAt(selectedRow, 1));
                    txt_array_down[1].setText(String.valueOf(table_down.getValueAt(selectedRow, 2)));
                    for (JTextField txt : txt_array_down) {
                        txt.setEditable(false);
                    }
                    lastSelectedRow = selectedRow;
                }
            }
        });

        JPanel panelTable = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelTable.add(scrollPane);
        return panelTable;
    }

    private JPanel createDetailPanel_top(int width, int padding_top, JTextField txt_array[], String txt_label[], ImageIcon img) {
        JPanel panelDetail = tool.createDetailPanel(txt_array, txt_label, img, width, 300, 2, 5, false);
        JPanel wrappedPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        wrappedPanel.add(panelDetail);
        wrappedPanel.setBorder(BorderFactory.createEmptyBorder(padding_top, 0, 0, 0));

        txt_array[0].setEditable(false);
        txt_array[3].setEditable(false);
        txt_array[4].setEditable(false);
        return wrappedPanel;
    }

    private JPanel createDetailPanel_down(int width, int padding_top, JTextField txt_array[], String txt_label[], ImageIcon img) {
        JPanel panelDetail = tool.createDetailPanel(txt_array, txt_label, img, width, 300, 2, 5, false);
        JPanel wrappedPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        wrappedPanel.add(panelDetail);
        wrappedPanel.setBorder(BorderFactory.createEmptyBorder(padding_top, 0, 0, 0));
        return wrappedPanel;
    }

    private JPanel createButtonPanel() {
        String[] buttonTexts = {"Thêm", "Xóa", "Thanh toán"};
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(tool.createButtonPanel(buttons, buttonTexts, new Color(0, 36, 107), Color.WHITE, "x"));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 110, 25));

        buttons[0].addActionListener(e -> addChiTietPhieuNhap());
        buttons[1].addActionListener(e -> deleteChiTietPhieuNhap());
        buttons[2].addActionListener(e -> thanhToan());
        return buttonPanel;
    }

    private JPanel createSearchPanel() {
        String[] searchOptions = {"Mã nhân viên", "Mã khách hàng"};
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchPanel.add(Box.createHorizontalStrut(33));
        searchPanel.add(tool.createSearchTextField(300, 30, searchOptions));
        return searchPanel;
    }

    private void addChiTietPhieuNhap() {
        // Tạo mã phiếu nhập mới
        // String maPN = getID();
        txt_array_top[0].setEditable(false);
        txt_array_top[0].setText(getID());
        txt_array_top[3].setText(LocalDate.now().toString());
        for (JTextField txt : txt_array_top) {
            txt.setEditable(true);
        }
        selectedRow = table_top.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(null, "Vui lòng chọn một sách từ danh sách!");
            return;
        }

        try {
            String maSach = table_top.getValueAt(selectedRow, 0).toString();
            String tenSach = table_top.getValueAt(selectedRow, 1).toString();
            String soLuongStr = txt_array_down[1].getText().trim();
            int donGia = Integer.parseInt(table_top.getValueAt(selectedRow, 3).toString());

            if (soLuongStr.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Vui lòng nhập số lượng!");
                return;
            }

            int soLuong = Integer.parseInt(soLuongStr);
            if (soLuong <= 0) {
                JOptionPane.showMessageDialog(null, "Số lượng phải lớn hơn 0!");
                return;
            }

            // Check if book exists
            SachDTO sach = sachBUS.getSachByMaSach(maSach);
            if (sach == null) {
                JOptionPane.showMessageDialog(null, "Sách không tồn tại!");
                return;
            }

            DefaultTableModel model = (DefaultTableModel) table_down.getModel();
            model.addRow(new Object[]{maSach, tenSach, soLuong, donGia});

            updateTotal();
            txt_array_down[0].setText("");
            txt_array_down[1].setText("");

            JOptionPane.showMessageDialog(null, "Thêm chi tiết phiếu nhập thành công!");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Số lượng phải là số nguyên!");
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi khi thêm chi tiết phiếu nhập: " + e.getMessage());
        }
    }

    private void deleteChiTietPhieuNhap() {
        selectedRow = table_down.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(null, "Vui lòng chọn một dòng để xóa!");
            return;
        }

        DefaultTableModel model = (DefaultTableModel) table_down.getModel();
        model.removeRow(selectedRow);
        updateTotal();
        JOptionPane.showMessageDialog(null, "Xóa chi tiết phiếu nhập thành công!");
    }

private void thanhToan() {
    try {
        

        // Lấy thông tin từ giao diện
        String maPN = txt_array_top[0].getText().trim();
        String maNV = txt_array_top[1].getText().trim();
        String maNXB = txt_array_top[2].getText().trim();
        String ngayNhapStr = txt_array_top[3].getText().trim();
        String tongTienStr = txt_array_top[4].getText().trim();

        // Kiểm tra đầu vào
        if (maNV.isEmpty() || maNXB.isEmpty() || ngayNhapStr.isEmpty() || tongTienStr.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Vui lòng nhập đầy đủ thông tin phiếu nhập!");
            return;
        }

        // Kiểm tra nhân viên
        if (!nhanVienBUS.isNhanVienExists(maNV)) {
            JOptionPane.showMessageDialog(null, "Mã nhân viên không tồn tại!");
            return;
        }

        // Kiểm tra nhà xuất bản
        if (!nhaXuatBanBUS.isNhaXuatBanExists(maNXB)) {
            JOptionPane.showMessageDialog(null, "Nhà xuất bản không tồn tại!");
            return;
        }

        // Kiểm tra bảng chi tiết phiếu nhập
        DefaultTableModel model = (DefaultTableModel) table_down.getModel();
        if (model.getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "Vui lòng thêm ít nhất một sách vào phiếu nhập!");
            return;
        }

        // Parse ngày nhập và tổng tiền
        int tongTien = Integer.parseInt(tongTienStr);
        Date ngayNhap;
        try {
            ngayNhap = Date.valueOf(ngayNhapStr);
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(null, "Định dạng ngày nhập không hợp lệ (YYYY-MM-DD)!");
            return;
        }



        // Thêm phiếu nhập
        PhieuNhapDTO phieuNhap = new PhieuNhapDTO(maPN, maNV, ngayNhap, tongTien, maNXB);
        if (!phieuNhapBUS.addPhieuNhap(phieuNhap)) {
            throw new SQLException("Thêm phiếu nhập thất bại!");
        }

        // Thêm chi tiết phiếu nhập
        for (int i = 0; i < model.getRowCount(); i++) {
            String maSach = model.getValueAt(i, 0).toString().trim();
            int soLuong = Integer.parseInt(model.getValueAt(i, 2).toString());
            int giaNhap = Integer.parseInt(model.getValueAt(i, 3).toString());

            // Kiểm tra mã sách
            SachDTO sach = sachBUS.getSachByMaSach(maSach);
            if (sach == null) {
                throw new SQLException("Mã sách " + maSach + " không tồn tại!");
            }

            // Thêm chi tiết phiếu nhập
            ChiTietPhieuNhapDTO chiTiet = new ChiTietPhieuNhapDTO(maSach, maPN, soLuong, giaNhap);
            if (!chiTietPhieuNhapBUS.addChiTietPhieuNhap(chiTiet)) {
                throw new SQLException("Thêm chi tiết phiếu nhập thất bại cho sách " + maSach);
            }

            // Cập nhật số lượng tồn
            int soLuongHienTai = sachBUS.getSoLuongTonSanPham(maSach);
            sachBUS.updateSoLuongTonSanPham(maSach, soLuongHienTai + soLuong);
        }

        // Cập nhật lại bảng top 
        refreshTable();


        // Xóa bảng và reset giao diện
        model.setRowCount(0);
        for (JTextField txt : txt_array_top) {
            txt.setText("");
        }
        for (JTextField txt : txt_array_down) {
            txt.setText("");
        }
        count++;
        txt_array_top[0].setText(getID());
        txt_array_top[3].setText(LocalDate.now().toString());

        JOptionPane.showMessageDialog(null, "Thanh toán thành công!");

    } catch (SQLException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(null, "Lỗi khi thanh toán: " + e.getMessage());
    }
}

    private void updateTotal() {
        int tongTien = 0;
        DefaultTableModel model = (DefaultTableModel) table_down.getModel();
        for (int i = 0; i < model.getRowCount(); i++) {
            int soLuong = Integer.parseInt(model.getValueAt(i, 2).toString());
            int donGia = Integer.parseInt(model.getValueAt(i, 3).toString());
            tongTien += soLuong * donGia;
        }
        txt_array_top[4].setText(String.valueOf(tongTien));
    }

        private void refreshTable() {
        DefaultTableModel model = (DefaultTableModel) table_top.getModel();
        model.setRowCount(0); // Xóa dữ liệu cũ
        try {
            sachList = sachBUS.getAllSach();
            for (SachDTO sach : sachList) {
                model.addRow(new Object[]{
                    sach.getMaSach(),
                    sach.getTenSach(),
                    sach.getSoLuong(),
                    sach.getDonGia()
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi khi làm mới bảng: " + e.getMessage());
        }
    }

    public JPanel getPanel() {
        return this.panel;
    }
}