package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import java.awt.Graphics2D;

import BUS.ChiTietHoaDonBUS;
import BUS.HoaDonBUS;
import BUS.KhachHangBUS;
import BUS.NhanVienBUS;
import BUS.SachBUS;
import DTO.ChiTietHoaDonDTO;
import DTO.HoaDonDTO;
import DTO.KhachHangDTO;
import DTO.NhanVienDTO;
import DTO.SachDTO;
import DTO.TaiKhoanNVDTO;

public class BanSachGUI {
    private static final int WIDTH = 1200;
    private static final int SIDE_MENU_WIDTH = 151;
    private static final int HEIGHT = (int) (WIDTH * 0.625);
    private static final String INVOICE_ID_PREFIX = "HD";
    private static final String DEFAULT_IMAGE_PATH = "/images/Book/the_little_prince.jpg";

    private Tool tool = new Tool();
    private JPanel panel, paymentPanel;
    private JTextField[] txt_array_top = new JTextField[5];
    private JTextField[] txt_array_down = new JTextField[2];
    private JTextField[] txt_array_search = new JTextField[1];
    private JTextField txt_invoiceId, txt_employeeId, txt_customerPhone, txt_date, txt_total;
    private JTextField txt_bookId, txt_quantity;
    private JTextField txt_search;
    private JButton[] buttons = new JButton[3];
    private JButton[] searchbutton = new JButton[1];
    private JTable table_down, table_top;
    private int selectedRow = -1;
    private int lastSelectedRow = -1;
    private int count = 0;

    private List<SachDTO> sachList;
    private List<HoaDonDTO> hoaDonList;
    private SachBUS sachBUS = new SachBUS();
    private NhanVienBUS nhanVienBUS = new NhanVienBUS();
    private HoaDonBUS hoaDonBUS = new HoaDonBUS();
    private ChiTietHoaDonBUS chiTietHoaDonBUS = new ChiTietHoaDonBUS();
    private KhachHangBUS khachHangBUS = new KhachHangBUS();
    private NhanVienDTO nv;

    public BanSachGUI(TaiKhoanNVDTO account) {
        nv = nhanVienBUS.getNhanVienByMaNV(account.getMaNV());
        initializeTextFields();
        initializeMainPanel();
        setupPanelLayout();
        initializeHoaDon();
    }

    private void initializeTextFields() {
        // txt_invoiceId = new JTextField();
        // txt_employeeId = new JTextField();
        // System.out.println(nv.getHoTen());
        // txt_customerPhone = new JTextField();
        // txt_date = new JTextField();
        // txt_total = new JTextField();
        // txt_bookId = new JTextField();
        // txt_quantity = new JTextField();
        txt_search = new JTextField();
        txt_array_top = new JTextField[] { txt_invoiceId, txt_employeeId, txt_customerPhone, txt_date, txt_total };
        txt_array_down = new JTextField[] { txt_bookId, txt_quantity };
        txt_array_search = new JTextField[] { txt_search };
    }

    private void initializeMainPanel() {
        panel = tool.createPanel(WIDTH - SIDE_MENU_WIDTH, HEIGHT, new BorderLayout());
        paymentPanel = tool.createPanel(WIDTH - SIDE_MENU_WIDTH, (int) (HEIGHT * 0.55), new BorderLayout());
    }

    private void setupPanelLayout() {
        panel.add(createSearchPanel(), BorderLayout.NORTH);
        panel.add(createTable_top(), BorderLayout.WEST);

        String[] txt_label_top = { "Mã hóa đơn", "Nhân viên", "SDT KH", "Ngày bán", "Tổng tiền" };
        panel.add(createDetailPanel_top(400, 30, txt_array_top, txt_label_top, null), BorderLayout.CENTER);

        String[] txt_label = { "Mã sách", "Số lượng" };
        ImageIcon img = null;
        try {
            img = new ImageIcon(getClass().getResource(DEFAULT_IMAGE_PATH));
        } catch (Exception e) {
            System.err.println("Image not found: " + e.getMessage());
        }
        paymentPanel.add(createDetailPanel_down(500, 10, txt_array_down, txt_label, img), BorderLayout.WEST);
        paymentPanel.add(createButtonPanel(), BorderLayout.SOUTH);
        paymentPanel.add(createTable_down(), BorderLayout.EAST);

        panel.add(paymentPanel, BorderLayout.SOUTH);
    }

    private void initializeHoaDon() {
        hoaDonList = hoaDonBUS.getAllHoaDon();
        if (!hoaDonList.isEmpty()) {
            String maHD = hoaDonList.get(hoaDonList.size() - 1).getMaHD();
            String numericPart = maHD.substring(2);
            count = Integer.parseInt(numericPart) + 1;
        }
    }

    private String getID() {
        count++;
        return INVOICE_ID_PREFIX + String.format("%03d", count);
    }

    private JPanel createSearchPanel() {
        String[] searchOptions = { "Mã sách", "Tên sách" };
        JComboBox<String> comboBox = new JComboBox<>(searchOptions);
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchPanel.add(Box.createHorizontalStrut(33));
        searchPanel.add(tool.createSearchTextFieldTest(comboBox, searchbutton, txt_array_search));
        return searchPanel;
    }

    private JPanel createTable_top() {
        String[] column = { "Mã sách", "Tên sách", "Số lượng", "Đơn giá" };
        DefaultTableModel model = new DefaultTableModel(column, 0);

        try {
            sachList = sachBUS.getAllSach();
            for (SachDTO sach : sachList) {
                model.addRow(new Object[] {
                        sach.getMaSach(),
                        sach.getTenSach(),
                        sach.getSoLuong(),
                        sach.getDonGia() + 10000
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
        String[] column = { "Mã sách", "Tên sách", "Số lượng", "Đơn giá" };
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
        txt_array[1].setEditable(false);
        txt_array[1].setText(nv.getHoTen());
        txt_array[2].setEditable(false);
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
        String[] buttonTexts = { "Thêm", "Xóa", "Thanh toán" };
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(tool.createButtonPanel(buttons, buttonTexts, new Color(0, 36, 107), Color.WHITE, "x"));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 110, 25));

        buttons[0].addActionListener(e -> addChiTietHoaDon());
        buttons[1].addActionListener(e -> deleteChiTietHoaDon());
        buttons[2].addActionListener(e -> thanhToan());
        return buttonPanel;
    }

    private void addChiTietHoaDon() {
        txt_array_top[0].setEditable(false);
        txt_array_top[0].setText(getID());
        txt_array_top[1].setEditable(false); // Đặt mã nhân viên không đặt tên vì hàm kiểm tra tồn tại nhân viên
        txt_array_top[1].setText(nv.getMaNV()); // chỉ so mã nhân viên chứ không so tên nhân viên
        txt_array_top[2].setEditable(true);
        txt_array_top[3].setText(LocalDate.now().toString());

        selectedRow = table_top.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(null, "Vui lòng chọn một sách từ danh sách!");
            return;
        }
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

        if (soLuong > sach.getSoLuong()) {
            JOptionPane.showMessageDialog(null, "Số lượng sách trong kho không đủ!");
            return;
        }

        DefaultTableModel model = (DefaultTableModel) table_down.getModel();
        for (int i = 0; i < model.getRowCount(); i++) {
            String maSachCu = model.getValueAt(i, 0).toString().trim();
            if (maSach.equals(maSachCu)) {
                int soLuongCu = Integer.parseInt(model.getValueAt(i, 2).toString());
                soLuong += soLuongCu;
                model.removeRow(i);
                break;
            }
        }
        model.addRow(new Object[] { maSach, tenSach, soLuong, donGia });

        updateTotal();
        txt_array_down[0].setText("");
        txt_array_down[1].setText("");

    }

    private void deleteChiTietHoaDon() {
        selectedRow = table_down.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(null, "Vui lòng chọn một dòng để xóa!");
            return;
        }

        DefaultTableModel model = (DefaultTableModel) table_down.getModel();
        model.removeRow(selectedRow);
        updateTotal();
        JOptionPane.showMessageDialog(null, "Xóa chi tiết hóa đơn thành công!");
    }

    private void thanhToan() {

        try {
            String maHD = txt_array_top[0].getText().trim();
            String maNV = txt_array_top[1].getText().trim();
            String sdtKhach = txt_array_top[2].getText().trim();
            String ngayBanStr = txt_array_top[3].getText().trim();
            String tongTienStr = txt_array_top[4].getText().trim();

            if (maNV.isEmpty() || sdtKhach.isEmpty() || ngayBanStr.isEmpty() || tongTienStr.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Vui lòng nhập đầy đủ thông tin hóa đơn!");
                return;
            }

            if (!nhanVienBUS.isNhanVienExists(maNV)) {
                JOptionPane.showMessageDialog(null, "Mã nhân viên không tồn tại!");
                return;
            }

            KhachHangDTO maKH = khachHangBUS.getMaKhachHangBySdt(sdtKhach);
            if (maKH == null) {
                JOptionPane.showMessageDialog(null, "Số điện thoại khách hàng không tồn tại!");
                return;
            }

            DefaultTableModel model = (DefaultTableModel) table_down.getModel();
            if (model.getRowCount() == 0) {
                JOptionPane.showMessageDialog(null, "Vui lòng thêm ít nhất một sách vào hóa đơn!");
                return;
            }

            int tongTien = Integer.parseInt(tongTienStr);
            Date ngayBan;
            try {
                ngayBan = Date.valueOf(ngayBanStr);
            } catch (IllegalArgumentException e) {
                JOptionPane.showMessageDialog(null, "Định dạng ngày bán không hợp lệ (YYYY-MM-DD)!");
                return;
            }

            HoaDonDTO hoaDon = new HoaDonDTO(maHD, maNV, maKH.getMaKH(), ngayBan, tongTien);
            if (!hoaDonBUS.addHoaDon(hoaDon)) {
                throw new SQLException("Thêm hóa đơn thất bại!");
            }

            for (int i = 0; i < model.getRowCount(); i++) {
                String maSach = model.getValueAt(i, 0).toString().trim();
                int soLuong = Integer.parseInt(model.getValueAt(i, 2).toString());
                int donGia = Integer.parseInt(model.getValueAt(i, 3).toString());

                SachDTO sach = sachBUS.getSachByMaSach(maSach);
                if (sach == null) {
                    throw new SQLException("Mã sách " + maSach + " không tồn tại!");
                }

                if (soLuong > sach.getSoLuong()) {
                    throw new SQLException("Số lượng sách trong kho không đủ cho sách " + maSach);
                }

                ChiTietHoaDonDTO chiTiet = new ChiTietHoaDonDTO(maSach, maHD, soLuong, donGia);
                if (!chiTietHoaDonBUS.addChiTietHoaDon(chiTiet)) {
                    throw new SQLException("Thêm chi tiết hóa đơn thất bại cho sách " + maSach);
                }

                int soLuongHienTai = sach.getSoLuong();
                sachBUS.updateSoLuongTonSanPham(maSach, soLuongHienTai - soLuong);
            }

            refreshTable();
            model.setRowCount(0);

            for (JTextField txt : txt_array_top) {
                if (txt != txt_employeeId) {
                    txt.setText("");
                }
                txt.setEditable(txt != txt_invoiceId && txt != txt_date && txt != txt_total);
            }
            for (JTextField txt : txt_array_down) {
                txt.setText("");
            }
            txt_array_top[0].setText(getID());
            txt_array_top[3].setText(LocalDate.now().toString());

            JOptionPane.showMessageDialog(null, "Thanh toán thành công!");
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi khi thanh toán: " + e.getMessage());
        }
        Inhoadon();
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
                model.addRow(new Object[] {
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

    public void Inhoadon() {

        JOptionPane.showMessageDialog(null, "In hóa đơn thành công!");
    }

    public JPanel getPanel() {
        return this.panel;
    }
}
