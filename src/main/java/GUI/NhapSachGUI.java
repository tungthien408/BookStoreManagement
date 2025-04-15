package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
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

public class NhapSachGUI {
    Tool tool = new Tool();
    JPanel panel;
    int width = 1200;
    int width_sideMenu = 151;
    int height = (int)(width * 0.625);
    JTextField txt_importId, txt_employeeId, txt_nxb, txt_date, txt_total, txt_name, txt_quantity;
    JButton[] buttons = new JButton[3];
    JTable table_down, table_top;
    private PhieuNhapBUS phieuNhapBUS;
    private NXBBUS nhaXuatBanBUS;
    private SachBUS sachBUS;
    private NhanVienBUS nhanVienBUS;
    private ChiTietPhieuNhapBUS chiTietPhieuNhapBUS;

    List<SachDTO> sachList;
    List<ChiTietPhieuNhapDTO> chiTietPhieuNhapList;

    public NhapSachGUI() {
        // Khởi tạo các BUS
        phieuNhapBUS = new PhieuNhapBUS();
        nhaXuatBanBUS = new NXBBUS();
        sachBUS = new SachBUS();
        nhanVienBUS = new NhanVienBUS();
        chiTietPhieuNhapBUS = new ChiTietPhieuNhapBUS();

        // Khởi tạo các text field
        txt_importId = new JTextField();
        txt_employeeId = new JTextField();
        txt_nxb = new JTextField();
        txt_date = new JTextField();
        txt_total = new JTextField();
        txt_name = new JTextField();
        txt_quantity = new JTextField();

        // Tạo giao diện
        panel = tool.createPanel(width - width_sideMenu, height, new BorderLayout());
        panel.add(createSearchPanel(), BorderLayout.NORTH); // search panel
        
        // table_top
        panel.add(createTable_top(), BorderLayout.WEST);

        // detail (top right)
        JTextField txt_array_top[] = {txt_importId, txt_employeeId, txt_nxb, txt_date, txt_total};
        String txt_label_top[] = {"Mã phiếu nhập", "Mã NV", "NXB", "Ngày nhập", "Tổng tiền"};
        panel.add(createDetailPanel(400, 30, txt_array_top, txt_label_top, null), BorderLayout.CENTER);
        
        JPanel paymentPanel = tool.createPanel(width - width_sideMenu, (int)(height * 0.55), new BorderLayout());
        JTextField txt_array[] = {txt_name, txt_quantity};
        String[] txt_label = {"Tên sách", "Số lượng"};
        // detail (bottom right)
        paymentPanel.add(createDetailPanel(500, 10, txt_array, txt_label, new ImageIcon("images/Book/the_little_prince.jpg")), BorderLayout.WEST);
        
        // buttons
        paymentPanel.add(createButtonPanel(), BorderLayout.SOUTH);
        
        paymentPanel.add(createTable_down(), BorderLayout.EAST);
        panel.add(paymentPanel, BorderLayout.SOUTH);
    }

    private JPanel createTable_top() {
        String column[] = {"Mã sách", "Tên sách", "Số lượng", "Đơn giá"};
        DefaultTableModel model = new DefaultTableModel(column, 0);
        // Lấy dữ liệu từ cơ sở dữ liệu
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
        // Bảng
        table_top = new JTable(model);
        table_top.setDefaultEditor(Object.class, null); // Không cho chỉnh sửa trực tiếp trên bảng
        JScrollPane scrollPane = new JScrollPane(table_top);
        scrollPane.setPreferredSize(new Dimension(650, 300));
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JPanel panelTable = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelTable.add(scrollPane);
        
        // Sự kiện chọn dòng trên bảng sách
        table_top.getSelectionModel().addListSelectionListener(e -> {
            int selectedRow = table_top.getSelectedRow();
            if (selectedRow >= 0) {
                txt_name.setText((String) table_top.getValueAt(selectedRow, 1));
            }
        });
        
        return panelTable;
    }

    private JPanel createTable_down() {
        String column[] = {"Mã PN", "Mã sách", "Số lượng", "Đơn giá"};
        DefaultTableModel model = new DefaultTableModel(column, 0);
        // Lấy dữ liệu từ cơ sở dữ liệu
        try {
            chiTietPhieuNhapList = chiTietPhieuNhapBUS.getAllChiTietPhieuNhap();
            for (ChiTietPhieuNhapDTO chiTietPhieuNhap : chiTietPhieuNhapList) {
                model.addRow(new Object[]{
                    chiTietPhieuNhap.getMaPN(),
                    chiTietPhieuNhap.getMaSach(),
                    chiTietPhieuNhap.getSoLuong(),
                    chiTietPhieuNhap.getGiaNhap()
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi khi tải dữ liệu chi tiết phiếu nhập: " + e.getMessage());
        }
        // Bảng
        table_down = new JTable(model);
        table_down.setDefaultEditor(Object.class, null); // Không cho chỉnh sửa trực tiếp trên bảng
        JScrollPane scrollPane = new JScrollPane(table_down);
        scrollPane.setPreferredSize(new Dimension(500, 300));
        scrollPane.setBorder(BorderFactory.createEmptyBorder(20, 10, 10, 10));
        
        JPanel panelTable = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelTable.add(scrollPane);
        return panelTable;
    }

    private JPanel createDetailPanel(int width, int padding_top, JTextField txt_array[], String txt_label[], ImageIcon img) {
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

        // Khởi tạo event cho các buttons 
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
        try {
            String maPN = txt_importId.getText().trim();
            String tenSach = txt_name.getText().trim();
            String soLuongStr = txt_quantity.getText().trim();

            if (maPN.isEmpty() || tenSach.isEmpty() || soLuongStr.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Vui lòng nhập đầy đủ thông tin!");
                return;
            }

            int soLuong = Integer.parseInt(soLuongStr);
            if (soLuong <= 0) {
                JOptionPane.showMessageDialog(null, "Số lượng phải lớn hơn 0!");
                return;
            }

            // Tìm mã sách từ tên sách
            String maSach = null;
            for (SachDTO sach : sachList) {
                if (sach.getTenSach().equals(tenSach)) {
                    maSach = sach.getMaSach();
                    break;
                }
            }

            if (maSach == null) {
                JOptionPane.showMessageDialog(null, "Sách không tồn tại!");
                return;
            }

            // Kiểm tra phiếu nhập
            PhieuNhapDTO phieuNhap = phieuNhapBUS.getPhieuNhapByMaPN(maPN);
            if (phieuNhap == null) {
                JOptionPane.showMessageDialog(null, "Phiếu nhập không tồn tại!");
                return;
            }

            // Thêm chi tiết phiếu nhập
            ChiTietPhieuNhapDTO chiTiet = new ChiTietPhieuNhapDTO(maPN, maSach, soLuong, sachBUS.getSachByMaSach(maSach).getDonGia());
            chiTietPhieuNhapBUS.addChiTietPhieuNhap(chiTiet);

            // Cập nhật bảng chi tiết phiếu nhập
            DefaultTableModel model = (DefaultTableModel) table_down.getModel();
            model.addRow(new Object[]{maPN, maSach, soLuong, chiTiet.getGiaNhap()});

            // Cập nhật tổng tiền
            updateTotal();

            JOptionPane.showMessageDialog(null, "Thêm chi tiết phiếu nhập thành công!");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Số lượng phải là số nguyên!");
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi khi thêm chi tiết phiếu nhập: " + e.getMessage());
        }
    }

    private void deleteChiTietPhieuNhap() {
        int selectedRow = table_down.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(null, "Vui lòng chọn một dòng để xóa!");
            return;
        }

        try {
            String maPN = (String) table_down.getValueAt(selectedRow, 0);
            String maSach = (String) table_down.getValueAt(selectedRow, 1);

            // Xóa chi tiết phiếu nhập
            chiTietPhieuNhapBUS.deleteChiTietPhieuNhap(maPN, maSach);

            // Cập nhật bảng
            DefaultTableModel model = (DefaultTableModel) table_down.getModel();
            model.removeRow(selectedRow);

            // Cập nhật tổng tiền
            updateTotal();

            JOptionPane.showMessageDialog(null, "Xóa chi tiết phiếu nhập thành công!");
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi khi xóa chi tiết phiếu nhập: " + e.getMessage());
        }
    }

    private void thanhToan() {
        try {
            String maPN = txt_importId.getText().trim();
            if (maPN.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Vui lòng nhập mã phiếu nhập!");
                return;
            }

            PhieuNhapDTO phieuNhap = phieuNhapBUS.getPhieuNhapByMaPN(maPN);
            if (phieuNhap == null) {
                JOptionPane.showMessageDialog(null, "Phiếu nhập không tồn tại!");
                return;
            }


            // Cập nhật số lượng sách trong kho
            for (ChiTietPhieuNhapDTO chiTiet : chiTietPhieuNhapBUS.getChiTietPhieuNhapByMaPN(maPN)) {
                SachDTO sach = sachBUS.getSachByMaSach(chiTiet.getMaSach());
                sach.setSoLuong(sach.getSoLuong() + chiTiet.getSoLuong());
                sachBUS.updateSach(sach);
            }

            // Cập nhật bảng sách
            DefaultTableModel sachModel = (DefaultTableModel) table_top.getModel();
            sachModel.setRowCount(0);
            sachList = sachBUS.getAllSach();
            for (SachDTO sach : sachList) {
                sachModel.addRow(new Object[]{
                    sach.getMaSach(),
                    sach.getTenSach(),
                    sach.getSoLuong(),
                    sach.getDonGia()
                });
            }

            // Xóa bảng chi tiết phiếu nhập
            DefaultTableModel chiTietModel = (DefaultTableModel) table_down.getModel();
            chiTietModel.setRowCount(0);

            // Reset các trường nhập liệu
            txt_importId.setText("");
            txt_employeeId.setText("");
            txt_nxb.setText("");
            txt_date.setText("");
            txt_total.setText("");
            txt_name.setText("");
            txt_quantity.setText("");

            JOptionPane.showMessageDialog(null, "Thanh toán thành công!");
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi khi thanh toán: " + e.getMessage());
        }
    }

    private void updateTotal() {
        try {
            String maPN = txt_importId.getText().trim();
            if (!maPN.isEmpty()) {
                double total = 0;
                for (ChiTietPhieuNhapDTO chiTiet : chiTietPhieuNhapBUS.getChiTietPhieuNhapByMaPN(maPN)) {
                    total += chiTiet.getSoLuong() * chiTiet.getGiaNhap();
                }
                txt_total.setText(String.format("%.2f", total));
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi khi cập nhật tổng tiền: " + e.getMessage());
        }
    }

    public JPanel getPanel() {
        return this.panel;
    }
}