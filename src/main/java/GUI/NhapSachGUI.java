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
import DTO.NhanVienDTO;
import DTO.PhieuNhapDTO;
import DTO.SachDTO;
import DTO.TaiKhoanNVDTO;

public class NhapSachGUI {
    private Tool tool = new Tool();
    private JPanel panel, paymentPanel;
    private static final int WIDTH = 1200;
    private static final int SIDE_MENU_WIDTH = 151;
    private static final int HEIGHT = (int) (WIDTH * 0.625);
    
    private JTextField[] txt_array_top = new JTextField[5];
    private JTextField[] txt_array_down = new JTextField[2];
    private JTextField txt_importId, txt_employeeId, txt_nxb, txt_date, txt_total;
    private JTextField txt_name, txt_quantity;
    private JButton[] buttons = new JButton[3];
    private JTable table_down, table_top;
    
    private int selectedRow = -1;
    private int lastSelectedRow = -1;
    private int count = 0;
    
    private List<SachDTO> sachList;
    private List<ChiTietPhieuNhapDTO> chiTietPhieuNhapList;
    private List<PhieuNhapDTO> phieuNhapList;
    
    private PhieuNhapBUS phieuNhapBUS = new PhieuNhapBUS();
    private NXBBUS nhaXuatBanBUS = new NXBBUS();
    private SachBUS sachBUS = new SachBUS();
    private NhanVienBUS nhanVienBUS = new NhanVienBUS();
    private ChiTietPhieuNhapBUS chiTietPhieuNhapBUS = new ChiTietPhieuNhapBUS();
    private NhanVienDTO nv;

    public NhapSachGUI(TaiKhoanNVDTO account) {
        nv = nhanVienBUS.getNhanVienByMaNV(account.getMaNV());
        initializeTextFields();
        initializeMainPanel();
        setupPanelLayout();
        initializePhieuNhap();
    }
    
    private void initializeTextFields() {
        txt_array_top = new JTextField[]{txt_importId, txt_employeeId, txt_nxb, txt_date, txt_total};
        txt_array_down = new JTextField[]{txt_name, txt_quantity};
    }
    
    private void initializeMainPanel() {
        panel = tool.createPanel(WIDTH - SIDE_MENU_WIDTH, HEIGHT, new BorderLayout());
        paymentPanel = tool.createPanel(WIDTH - SIDE_MENU_WIDTH, (int)(HEIGHT * 0.55), new BorderLayout());
    }
    
    private void setupPanelLayout() {
        panel.add(createSearchPanel(), BorderLayout.NORTH);
        panel.add(createTable_top(), BorderLayout.WEST);
        
        String[] txt_label_top = {"Mã phiếu nhập", "Nhân viên", "NXB", "Ngày nhập", "Tổng tiền"};
        panel.add(createDetailPanel_top(400, 30, txt_array_top, txt_label_top, null), BorderLayout.CENTER);
        
        String[] txt_label = {"Mã Sách", "Số lượng"};
        paymentPanel.add(createDetailPanel_down(500, 10, txt_array_down, txt_label, 
            new ImageIcon("images/Book/the_little_prince.jpg")), BorderLayout.WEST);
        paymentPanel.add(createButtonPanel(), BorderLayout.SOUTH);
        paymentPanel.add(createTable_down(), BorderLayout.EAST);
        
        panel.add(paymentPanel, BorderLayout.SOUTH);
    }
    
    private void initializePhieuNhap() {
        phieuNhapList = phieuNhapBUS.getAllPhieuNhap();
        if (!phieuNhapList.isEmpty()) {
            String maTG = phieuNhapList.get(phieuNhapList.size() - 1).getMaPN();
            String numericPart = maTG.substring(2);
            count = Integer.parseInt(numericPart) + 1;
        }
    }
    
    private String getID() {
        String str = String.format("%03d", count);
        return "PN" + str;
    }
    
    private JPanel createSearchPanel() {
        String[] searchOptions = {"Mã nhân viên", "Mã khách hàng"};
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchPanel.add(Box.createHorizontalStrut(33));
        searchPanel.add(tool.createSearchTextField(300, 30, searchOptions));
        return searchPanel;
    }
    
    private JPanel createTable_top() {
        String[] column = {"Mã sách", "Tên sách", "Số lượng", "Đơn giá"};
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
        String[] column = {"Mã sách", "Tên sách", "Số lượng", "Đơn giá"};
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
    
    private JPanel createDetailPanel_top(int width, int padding_top, JTextField[] txt_array, 
            String[] txt_label, ImageIcon img) {
        JPanel panelDetail = tool.createDetailPanel(txt_array, txt_label, img, width, 300, 2, 5, false);
        JPanel wrappedPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        wrappedPanel.add(panelDetail);
        wrappedPanel.setBorder(BorderFactory.createEmptyBorder(padding_top, 0, 0, 0));
        
        txt_array[0].setEditable(false);
        txt_array[1].setText(nv.getHoTen());
        txt_array[1].setEditable(false);
        txt_array[3].setEditable(false);
        txt_array[4].setEditable(false);
        return wrappedPanel;
    }
    
    private JPanel createDetailPanel_down(int width, int padding_top, JTextField[] txt_array, 
            String[] txt_label, ImageIcon img) {
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
    
    private void addChiTietPhieuNhap() {
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
            
            SachDTO sach = sachBUS.getSachByMaSach(maSach);
            if (sach == null) {
                JOptionPane.showMessageDialog(null, "Sách không tồn tại!");
                return;
            }
            
            
            DefaultTableModel model = (DefaultTableModel) table_down.getModel();
            for (int i = 0; i < model.getRowCount(); i++) {
                String maSachCu = model.getValueAt(i, 0).toString().trim();

                if(maSach == maSachCu){
                    int soLuongCu = Integer.parseInt(model.getValueAt(i, 2).toString());
                    soLuong = soLuong + soLuongCu;
                    model.removeRow(i);
                }
                
            }
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
            String maPN = txt_array_top[0].getText().trim();
            String maNV = txt_array_top[1].getText().trim();
            String maNXB = txt_array_top[2].getText().trim();
            String ngayNhapStr = txt_array_top[3].getText().trim();
            String tongTienStr = txt_array_top[4].getText().trim();
            
            if (maNV.isEmpty() || maNXB.isEmpty() || ngayNhapStr.isEmpty() || tongTienStr.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Vui lòng nhập đầy đủ thông tin phiếu nhập!");
                return;
            }
            
            if (!nhanVienBUS.isNhanVienExists(maNV)) {
                JOptionPane.showMessageDialog(null, "Mã nhân viên không tồn tại!");
                return;
            }
            
            if (!nhaXuatBanBUS.isNhaXuatBanExists(maNXB)) {
                JOptionPane.showMessageDialog(null, "Nhà xuất bản không tồn tại!");
                return;
            }
            
            DefaultTableModel model = (DefaultTableModel) table_down.getModel();
            if (model.getRowCount() == 0) {
                JOptionPane.showMessageDialog(null, "Vui lòng thêm ít nhất một sách vào phiếu nhập!");
                return;
            }
            
            int tongTien = Integer.parseInt(tongTienStr);
            Date ngayNhap;
            try {
                ngayNhap = Date.valueOf(ngayNhapStr);
            } catch (IllegalArgumentException e) {
                JOptionPane.showMessageDialog(null, "Định dạng ngày nhập không hợp lệ (YYYY-MM-DD)!");
                return;
            }
            
            PhieuNhapDTO phieuNhap = new PhieuNhapDTO(maPN, maNV, ngayNhap, tongTien, maNXB);
            if (!phieuNhapBUS.addPhieuNhap(phieuNhap)) {
                throw new SQLException("Thêm phiếu nhập thất bại!");
            }
            
            for (int i = 0; i < model.getRowCount(); i++) {
                String maSach = model.getValueAt(i, 0).toString().trim();
                int soLuong = Integer.parseInt(model.getValueAt(i, 2).toString());
                int giaNhap = Integer.parseInt(model.getValueAt(i, 3).toString());
                
                SachDTO sach = sachBUS.getSachByMaSach(maSach);
                if (sach == null) {
                    throw new SQLException("Mã sách " + maSach + " không tồn tại!");
                }
                
                ChiTietPhieuNhapDTO chiTiet = new ChiTietPhieuNhapDTO(maSach, maPN, soLuong, giaNhap);
                if (!chiTietPhieuNhapBUS.addChiTietPhieuNhap(chiTiet)) {
                    throw new SQLException("Thêm chi tiết phiếu nhập thất bại cho sách " + maSach);
                }
                
                int soLuongHienTai = sachBUS.getSoLuongTonSanPham(maSach);
                sachBUS.updateSoLuongTonSanPham(maSach, soLuongHienTai + soLuong);
            }
            
            refreshTable();
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
        model.setRowCount(0);
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