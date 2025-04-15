package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.sql.SQLException;
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
import DTO.SachDTO;

public class NhapSachGUI {
    Tool tool = new Tool();
    JPanel panel;
    int width = 1200;
    int width_sideMenu = 151;
    int height = (int)(width * 0.625);
    JTextField txt_importId, txt_employeeId, txt_nxb, txt_date, txt_total, txt_name, txt_quantity;
    JButton[] buttons = new JButton[3];
    JTable table_down,table_top;
    private PhieuNhapBUS phieuNhapBUS;
    private NXBBUS nhaXuatBanBUS;
    private SachBUS sachBUS;
    private NhanVienBUS nhanVienBUS;
    private ChiTietPhieuNhapBUS chiTietPhieuNhapBUS;

    List<SachDTO> sachList;
    List<ChiTietPhieuNhapDTO> chiTietPhieuNhapList;


    public NhapSachGUI() {
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
            JOptionPane.showMessageDialog(null, "Lỗi khi tải dữ liệu từ cơ sở dữ liệu: " + e.getMessage());
        }
        // Bảng
        table_top = new JTable(model);
        table_top.setDefaultEditor(Object.class, null); // Không cho chỉnh sửa trực tiếp trên bảng
        JScrollPane scrollPane = new JScrollPane(table_top);
        scrollPane.setPreferredSize(new Dimension(650, 300));
        // Tạo khoảng cách xung quanh bảng
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Top, Left, Bottom, Right
        
        // Tạo panel FlowLayout để có thể tùy chỉnh kích cỡ bảng
        JPanel panelTable = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelTable.add(scrollPane);
        return panelTable;
    }

    private JPanel createTable_down() {
        String column[] = {"Mã PN","Mã sách", "Số lượng", "Đơn giá"};
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
            JOptionPane.showMessageDialog(null, "Lỗi khi tải dữ liệu từ cơ sở dữ liệu: " + e.getMessage());
        }
        // Bảng
        table_down = new JTable(model);
        table_down.setDefaultEditor(Object.class, null); // Không cho chỉnh sửa trực tiếp trên bảng
        JScrollPane scrollPane = new JScrollPane(table_down);
        scrollPane.setPreferredSize(new Dimension(500, 300));
        // Tạo khoảng cách xung quanh bảng
        scrollPane.setBorder(BorderFactory.createEmptyBorder(20, 10, 10, 10)); // Top, Left, Bottom, Right
        
        // Tạo panel FlowLayout để có thể tùy chỉnh kích cỡ bảng
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

        // khởi tạo event cho các buttons 
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

    public JPanel getPanel() {
        return this.panel;
    } 
}
