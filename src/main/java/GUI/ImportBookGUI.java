// TODO: các event liên quan đến thanh toán + hóa đơn + button

package GUI;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.awt.FlowLayout;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import DTO.ChiTietPhieuNhapDTO;
import DTO.PhieuNhapDTO;
import DTO.SachDTO;
import DTO.TaiKhoanNVDTO;
import GUI.BaseGUI;
import Utils.EventManager;

public class ImportBookGUI extends BaseGUI {
    private static final String INVOICE_ID_PREFIX = "PN";
    private JPanel panel, paymentPanel;
    private final int TOP_TEXTFIELD_COUNT = 5;
    private final int DOWN_TEXTFIELD_COUNT = 2;
    private final int BTN_COUNT = 3;

    public ImportBookGUI(TaiKhoanNVDTO account) {
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
        initializePhieuNhap();
        timkiem(1);
        EventManager.getInstance().registerListener(this);
    }

    private void addButtonEvents() {
        buttons.get(0).addActionListener(e -> addChiTietHoaDon(1, 3, count, INVOICE_ID_PREFIX));
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
                        sach.getDonGia()
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi khi tải dữ liệu sách: " + e.getMessage());
        }

        panel.add(createTable_top(column, model, 650, 300), BorderLayout.WEST);

        // detail panel top
        String[] txt_label_top = { "Mã phiếu nhập", "Nhân viên", "NXB", "Ngày nhập", "Tổng tiền" };        
        panel.add(createDetailPanel(400, -10, txt_array_top, txt_label_top, null), BorderLayout.CENTER);
        // addEventDetailPanel_top(txt_array_top);
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

        paymentPanel.add(lowerPanel, BorderLayout.CENTER);
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
        // mã phiếu nhập, nhân viên, nxb, ngày nhập, tổng tiền
        txt_array.get(0).setEditable(false); // txt_invoiceId
        txt_array.get(1).setEditable(false); // txt_employeeName
        txt_array.get(1).setText(nv.getHoTen());
        txt_array.get(2).setEditable(true); // txt_nxb
        txt_array.get(3).setEditable(false); // txt_date
        txt_array.get(3).setText(LocalDate.now().toString());
        txt_array.get(4).setText("");
        txt_array.get(4).setEditable(false); // txt_total
    }

    @Override
    protected void addChiTietHoaDon(int status, int date_index, int count, String prefix) {
        super.addChiTietHoaDon(1, 3, count, INVOICE_ID_PREFIX);
        updateTotal();
    }

    @Override
    protected void deleteChiTietHoaDon() {
        super.deleteChiTietHoaDon();
        updateTotal();
    }

    private void thanhToan() {
        try {
            String maPN = txt_array_top.get(0).getText().trim();
            String maNV = nv.getMaNV(); // Use actual employee ID
            String maNXB = txt_array_top.get(2).getText().trim().toUpperCase();
            String ngayNhapStr = txt_array_top.get(3).getText().trim();
            String tongTienStr = txt_array_top.get(4).getText().trim();

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
            Inhoadon(1);
            model.setRowCount(0);

            for (JTextField txt : txt_array_top) {
                txt.setText("");
                txt.setEditable(false);
            }
            for (JTextField txt : txt_array_down) {
                txt.setText("");
                txt.setEditable(false);
            }

            count++;
            txt_array_top.get(0).setText(getID(INVOICE_ID_PREFIX, count));
            txt_array_top.get(3).setText(LocalDate.now().toString());
            initializePhieuNhap();
            imageLabel.setIcon(null);
            imagePanel.revalidate();
            imagePanel.repaint();
            EventManager.getInstance().notifyListeners();
            JOptionPane.showMessageDialog(null, "Thanh toán thành công!");
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi khi thanh toán: " + e.getMessage());
        }
    }

    private void initializePhieuNhap() {
        phieuNhapList = phieuNhapBUS.getAllPhieuNhap();
        if (!phieuNhapList.isEmpty()) {
            String maPN = phieuNhapList.get(phieuNhapList.size() - 1).getMaPN();
            String numericPart = maPN.substring(2);
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

        txt_array_top.get(4).setText(String.valueOf(tongTien));
    }
}