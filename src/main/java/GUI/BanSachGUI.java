// TODO: các event liên quan đến thanh toán + hóa đơn + button

<<<<<<< HEAD
package GUI;
import java.util.ArrayList;
=======
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
>>>>>>> 703571d52f944198eca0e5d2b5dc02afb4e26331
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

<<<<<<< HEAD
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.lang.reflect.Array;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.awt.FlowLayout;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

=======
>>>>>>> 703571d52f944198eca0e5d2b5dc02afb4e26331
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
import GUI.BaseGUI;
import Utils.EventManager;

public class BanSachGUI extends BaseGUI {
    private static final String INVOICE_ID_PREFIX = "HD";
    private JPanel panel, paymentPanel;
    private final int TOP_TEXTFIELD_COUNT = 7;
    private final int DOWN_TEXTFIELD_COUNT = 2;
    private final int BTN_COUNT = 3;
    private int tienGiamGia = 0;

    public BanSachGUI(TaiKhoanNVDTO account) {
        nv = nhanVienBUS.getNhanVienByMaNV(account.getMaNV());
        initializeBaseFields();
        initializeCustomFields();
    }

    @Override
    protected void initializeCustomFields() {
        initializeTextFields(txt_array_top, TOP_TEXTFIELD_COUNT);
        initializeTextFields(txt_array_down, DOWN_TEXTFIELD_COUNT);
        initializeMainPanel();
        setupPanelLayout();
        initializeHoaDon();
        timkiem(0);
        EventManager.getInstance().registerListener(this);
    }

    private void addButtonEvents() {
        buttons.get(0).addActionListener(e -> addChiTietHoaDon(0, 5, count, INVOICE_ID_PREFIX));
        buttons.get(1).addActionListener(e -> deleteChiTietHoaDon());
        buttons.get(2).addActionListener(e -> thanhToan());
    }

    private void initializeMainPanel() {
        panel = tool.createPanel(WIDTH - SIDE_MENU_WIDTH, HEIGHT, new BorderLayout());
        paymentPanel = tool.createPanel(WIDTH - SIDE_MENU_WIDTH, (int) (HEIGHT * 0.55), new BorderLayout());
    }

    private void setupPanelLayout() { 
        panel.add(createSearchPanel(), BorderLayout.NORTH); // search panel
        String[] column = new String[] { "Mã sách", "Tên sách", "Số lượng", "Đơn giá" };
        // top table
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

        panel.add(createTable_top(column, model, 650, 300), BorderLayout.WEST);

        // detail panel top
        String[] txt_label_top = { "Mã hóa đơn", "Nhân viên", "SDT KH", "Tên KH", "Điểm tích lũy", "Ngày bán",
                "Tổng tiền" };
        panel.add(createDetailPanel(450, -10, txt_array_top, txt_label_top, null), BorderLayout.CENTER);
        addEventDetailPanel_top(txt_array_top);
        JPanel lowerPanel = new JPanel(new BorderLayout(10, 0));

        // detail panel down
        String[] txt_label_down = { "Mã sách", "Số lượng" };
        JPanel detailPanelDown = createDetailPanel(300, 10, txt_array_down, txt_label_down, null);
        lowerPanel.add(detailPanelDown, BorderLayout.CENTER);

        imagePanel = new JPanel(new BorderLayout());
        imageLabel = new JLabel();
        imageLabel.setHorizontalAlignment(JLabel.CENTER);
        imageLabel.setVerticalAlignment(JLabel.CENTER);
        imagePanel.setPreferredSize(new Dimension(200, 260));
        imagePanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 0, 0));
        imagePanel.add(imageLabel, BorderLayout.CENTER);
        lowerPanel.add(imagePanel, BorderLayout.WEST);

        paymentPanel.add(lowerPanel, BorderLayout.WEST);
        // paymentPanel.add(createButtonPanel(), BorderLayout.SOUTH);

        // bottom table
        DefaultTableModel model_down = new DefaultTableModel(column, 0);
        paymentPanel.add(createTable_down(column, model_down, 500, 240), BorderLayout.EAST);
        addButtonEvents();
        panel.add(paymentPanel, BorderLayout.SOUTH);
    }

    @Override
    protected void addEventTable(JTable table, int type) {
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                selectedRow = table.getSelectedRow();
                if (selectedRow == lastSelectedRow && selectedRow >= 0) {
                    table.clearSelection();
                    lastSelectedRow = -1;
                    txt_array_down.get(0).setText("");
                    txt_array_down.get(1).setText("");
                    for (JTextField txt : txt_array_down) {
                        txt.setEditable(false);
                    }
                    imageLabel.setIcon(null);
                } else if (selectedRow >= 0) {
                    String bookId = (String) table.getValueAt(selectedRow, 0);
                    txt_array_down.get(0).setText(bookId);
                    if (type == 1) {
                        txt_array_down.get(1).setText("");
                        txt_array_down.get(1).setEditable(true);
                    } else {
                        txt_array_down.get(1).setText(String.valueOf(table.getValueAt(selectedRow, 2)));
                        for (JTextField txt : txt_array_down) {
                            txt.setEditable(false);
                        }
                    }
                    lastSelectedRow = selectedRow;

                    SachDTO sach = sachBUS.getSachByMaSach(bookId);
                    if (sach != null) {
                        renderBookImage(bookId, sach);
                    } else {
                        System.err.println("SachDTO not found for ID: " + bookId);
                        imageLabel.setIcon(null);
                    }
                }
                imagePanel.revalidate();
                imagePanel.repaint();
            }
        });
    }

    private void addEventDetailPanel_top(ArrayList<JTextField> txt_array) {
        txt_array.get(0).setEditable(false); // txt_invoiceId
        txt_array.get(1).setEditable(false); // txt_employeeName
        txt_array.get(1).setText(nv.getHoTen());
        txt_array.get(2).setEditable(true); // txt_customerPhone
        txt_array.get(3).setText("Anonymous"); // txt_customerName
        txt_array.get(5).setEditable(false); // txt_date
        txt_array.get(5).setText(LocalDate.now().toString());
        txt_array.get(6).setText("");
        txt_array.get(6).setEditable(false); // txt_total

        txt_array.get(2).addKeyListener(new java.awt.event.KeyAdapter() {
            private String previousPhoneNumber = "";

            @Override
            public void keyReleased(java.awt.event.KeyEvent e) {
                String sdt = txt_array.get(2).getText().trim();
                if (sdt.equals(previousPhoneNumber)) {
                    return; // Skip if the phone number hasn't changed
                }
                previousPhoneNumber = sdt;
                if (sdt.matches("(02|03|05|07|08|09)\\d{8}")) {
                    KhachHangDTO khachHang = khachHangBUS.getMaKhachHangBySdt(sdt);
                    if (khachHang != null) {
                        txt_array.get(3).setText(khachHang.getHoTen());
                        txt_array.get(3).setEditable(false);
                        txt_array.get(4).setText(khachHang.getDiem() + "");
                        txt_array.get(4).setEditable(true);
                        System.out.println("Tong tien: " + txt_array.get(6).getText());

                        int tien = Integer.parseInt(txt_array.get(6).getText());
                        if (khachHang.getDiem() > tien) {
                            tienGiamGia = 0;
                        } else
                            tienGiamGia = Integer.parseInt(txt_array.get(4).getText()) * 1000;
                    } else {
                        txt_array.get(3).setEditable(true);
                        txt_array.get(3).setText("");
                        txt_array.get(4).setText("0");
                        txt_array.get(4).setEditable(false);
                        tienGiamGia = 0;
                    }
                } else if (!sdt.isBlank()) {
                    tienGiamGia = 0;
                    txt_array.get(3).setText("Số điện thoại không hợp lệ!");
                } else {
                    tienGiamGia = 0;
                    txt_array.get(4).setText("");
                    txt_array.get(4).setEditable(false);
                    txt_array.get(3).setText("Anonymous");
                }
                updateTotal();
            }
        });

        txt_array.get(4).addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyReleased(java.awt.event.KeyEvent e) {
                String diemStr = txt_array.get(4).getText();
                if (diemStr.isBlank() || !diemStr.matches("\\d+")) {
                    tienGiamGia = 0;
                } else {
                    int diem = Integer.parseInt(diemStr);
                    int tien = Integer.parseInt(txt_array.get(6).getText());
                    if (diem * 1000 > tien) {
                        tienGiamGia = 0;
                    } else {
                        tienGiamGia = diem * 1000;
                    }
                }
                updateTotal();
            }
        });
    }

    @Override
    protected void addChiTietHoaDon(int status, int date_index, int count, String prefix) {
        super.addChiTietHoaDon(0, 5, count, INVOICE_ID_PREFIX);
        updateTotal();
        String diemStr = txt_array_top.get(4).getText().trim();
        int diem = (!diemStr.isEmpty()) ? Integer.parseInt(diemStr) : 0;
        int tien = Integer.parseInt(txt_array_top.get(6).getText());
        if (diem * 1000 > tien) {
            tienGiamGia = 0;
        } else {
            tienGiamGia = diem * 1000;
        }
        updateTotal();
    }

    @Override
    protected void deleteChiTietHoaDon() {
        super.deleteChiTietHoaDon();
        updateTotal();
    }

    private void thanhToan() {
        try {
            // txt_invoiceId, txt_employeeName, txt_customerPhone, txt_customerName,
            // txt_point, txt_date, txt_total
            String maHD = txt_array_top.get(0).getText().trim();
            String maNV = nv.getMaNV();
            String sdtKhach = txt_array_top.get(2).getText().trim();
            String tenKhach = txt_array_top.get(3).getText().trim();
            String diemStr = txt_array_top.get(4).getText().trim();
            String ngayBanStr = txt_array_top.get(5).getText().trim();
            String tongTienStr = txt_array_top.get(6).getText().trim();

            KhachHangDTO maKH;
            int diem;

            if (maNV.isEmpty() || tenKhach.isEmpty() || ngayBanStr.isEmpty() || tongTienStr.isEmpty()
                    || (!sdtKhach.isBlank() && diemStr.isEmpty())) {
                JOptionPane.showMessageDialog(null, "Vui lòng nhập đầy đủ thông tin hóa đơn!");
                return;
            }

            if (!sdtKhach.isBlank() && !sdtKhach.matches("(02|03|05|07|08|09)\\d{8}")) {
                JOptionPane.showMessageDialog(null, "Số điện thoại không hợp lệ!");
                return;
            }

            if (!nhanVienBUS.isNhanVienExists(maNV)) {
                JOptionPane.showMessageDialog(null, "Mã nhân viên không tồn tại!");
                return;
            }

            if (!sdtKhach.isBlank() && !diemStr.matches("\\d+")) {
                JOptionPane.showMessageDialog(null, "Điểm tích lũy được áp dụng phải chứa ký tự số");
                return;
            }

            DefaultTableModel model = (DefaultTableModel) table_down.getModel();
            if (model.getRowCount() == 0) {
                JOptionPane.showMessageDialog(null, "Vui lòng thêm ít nhất một sách vào hóa đơn!");
                return;
            }

            if (!sdtKhach.isEmpty()) {
                maKH = khachHangBUS.getMaKhachHangBySdt(sdtKhach);
                if (maKH == null) {
                    maKH = new KhachHangDTO();
                    maKH.setMaKH(getNextMaKH());
                    maKH.setHoTen(tenKhach);
                    maKH.setSdt(sdtKhach);
                    maKH.setTrangThaiXoa(0);
                    maKH.setDiem(0);
                    boolean b = khachHangBUS.addKhachHang(maKH);
                }
            } else {
                maKH = khachHangBUS.getKhachHangByMaKH("KH000");
                diemStr = "null";
            }

            if (!diemStr.equals("null")) {
                diem = Integer.parseInt(diemStr);

                if (diem < 0) {
                    JOptionPane.showMessageDialog(null,
                            "Điểm tích lũy được áp dụng phải lớn hơn 0 và phải chứa ký tự số");
                    return;
                }

                if (maKH.getDiem() < diem) {
                    JOptionPane.showMessageDialog(null, "Điểm tích lũy của khách hàng không đủ để được áp dụng");
                    return;
                }
            } else {
                diem = 0;
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
            Inhoadon(0);
            model.setRowCount(0);

            for (JTextField txt : txt_array_top) {
                if (txt != txt_array_top.get(1) ) { // employee
                    txt.setText("");
                }
                txt.setEditable(txt != txt_array_top.get(0) && txt != txt_array_top.get(1) && txt != txt_array_top.get(5) && txt != txt_array_top.get(6)); // txt_invoiceId, txt_employeeName, txt_date, txt_total respectively
            }
            for (JTextField txt : txt_array_down) {
                txt.setText("");
            }

            count++;
            txt_array_top.get(0).setText(getID(INVOICE_ID_PREFIX, count));
            txt_array_top.get(0).setEditable(false);
            txt_array_top.get(1).setText(nv.getHoTen());
            txt_array_top.get(1).setEditable(false);
            txt_array_top.get(3).setText("Anonymous");
            txt_array_top.get(3).setEditable(false);
            txt_array_top.get(4).setEditable(false);
            txt_array_top.get(5).setText(LocalDate.now().toString());
            txt_array_top.get(5).setEditable(false);
            txt_array_top.get(6).setEditable(false);
            initializeHoaDon();
            if (diem != 0)
                maKH.setDiem(maKH.getDiem() - diem);
            else
                maKH.setDiem(maKH.getDiem() + (int) (tongTien / 1000));
            khachHangBUS.updateKhachHang(maKH);
            EventManager.getInstance().notifyListeners();
            JOptionPane.showMessageDialog(null, "Thanh toán thành công!");
            finalIcon = null;
            imageLabel.setIcon(finalIcon);
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi khi thanh toán: " + e.getMessage());
        }
    }

    private void initializeHoaDon() {
        hoaDonList = hoaDonBUS.getAllHoaDon();
        if (!hoaDonList.isEmpty()) {
            String maHD = hoaDonList.get(hoaDonList.size() - 1).getMaHD();
            String numericPart = maHD.substring(2);
            count = Integer.parseInt(numericPart) + 1;
        }
    }

    public JPanel getPanel() {
        return this.panel;
    }

    private void updateTotal() {
        int tongTien = 0;
        DefaultTableModel model = (DefaultTableModel) table_down.getModel();
        for (int i = 0; i < model.getRowCount(); i++) {
            int soLuong = Integer.parseInt(model.getValueAt(i, 2).toString());
            int donGia = Integer.parseInt(model.getValueAt(i, 3).toString());
            tongTien += soLuong * donGia;
        }

        if (tienGiamGia > tongTien) {
            txt_array_top.get(4).setText(String.valueOf(tongTien));
        }
        tongTien -= tienGiamGia;
        txt_array_top.get(6).setText(String.valueOf(tongTien));
private void updateTotal() {
    int tongTien = 0;
    DefaultTableModel model = (DefaultTableModel) table_down.getModel();
    
    // Tính tổng tiền hóa đơn
    for (int i = 0; i < model.getRowCount(); i++) {
        int soLuong = Integer.parseInt(model.getValueAt(i, 2).toString());
        int donGia = Integer.parseInt(model.getValueAt(i, 3).toString());
        tongTien += soLuong * donGia;
    }

    // Xác thực và áp dụng giảm giá dựa trên điểm tích lũy
    String diemStr = txt_array_top[4].getText().trim();
    int diem = 0;
    try {
        if (!diemStr.isEmpty() && diemStr.matches("\\d+")) {
            diem = Integer.parseInt(diemStr);
            // Kiểm tra điểm không vượt quá tổng tiền hoặc điểm của khách hàng
            String sdt = txt_array_top[2].getText().trim();
            if (!sdt.isEmpty()) {
                KhachHangDTO khachHang = khachHangBUS.getMaKhachHangBySdt(sdt);
                if (khachHang != null && diem > khachHang.getDiem()) {
                    JOptionPane.showMessageDialog(null, "Điểm nhập vượt quá điểm tích lũy của khách hàng!");
                    txt_array_top[4].setText(String.valueOf(khachHang.getDiem()));
                    diem = khachHang.getDiem();
                }
            }
            // Tính giảm giá (1 điểm = 1000 VNĐ)
            tienGiamGia = Math.min(diem * 1000, tongTien); // Đảm bảo giảm giá không vượt quá tổng tiền
        } else {
            tienGiamGia = 0; // Không có điểm hợp lệ
        }
    } catch (NumberFormatException e) {
        tienGiamGia = 0; // Reset giảm giá nếu lỗi phân tích
        txt_array_top[4].setText("0"); // Reset trường điểm
        JOptionPane.showMessageDialog(null, "Điểm tích lũy phải là số nguyên dương!");
    }

    // Cập nhật tổng tiền sau giảm giá
    tongTien -= tienGiamGia;
    txt_array_top[6].setText(String.valueOf(tongTien));
}

    @Override
    public void refreshTable() {
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

    private String getNextMaKH() {
        String maKH = "KH";
        int count = khachHangBUS.getCountKhachHang() + 1;
        if (count < 10) {
            maKH += "00" + count;
        } else if (count < 100) {
            maKH += "0" + count;
        } else {
            maKH += count;
        }
        return maKH;
    }
}