// TODO: các event liên quan đến thanh toán + hóa đơn + button

package GUI;
import java.util.ArrayList;
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

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.lang.reflect.Array;
import java.time.LocalDate;
import java.awt.FlowLayout;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import BUS.ChiTietHoaDonBUS;
import BUS.HoaDonBUS;
import BUS.KhachHangBUS;
import BUS.NhanVienBUS;
import BUS.SachBUS;
import DTO.HoaDonDTO;
import DTO.KhachHangDTO;
import DTO.NhanVienDTO;
import DTO.SachDTO;
import GUI.BaseGUI;

public class SellBookGUI extends BaseGUI {
    private static final String INVOICE_ID_PREFIX = "HD";
    private JPanel panel, paymentPanel;
    private final int TOP_TEXTFIELD_COUNT = 7;
    private final int DOWN_TEXTFIELD_COUNT = 2;
    private final int BTN_COUNT = 3;
    private int tienGiamGia = 0;

    private List<SachDTO> sachList;
    private List<HoaDonDTO> hoaDonList;
    private SachBUS sachBUS = new SachBUS();
    private NhanVienBUS nhanVienBUS = new NhanVienBUS();
    private HoaDonBUS hoaDonBUS = new HoaDonBUS();
    private ChiTietHoaDonBUS chiTietHoaDonBUS = new ChiTietHoaDonBUS();
    private KhachHangBUS khachHangBUS = new KhachHangBUS();
    private NhanVienDTO nv;

    @Override
    protected void initializeCustomFields() {
        initializeTextFields(txt_array_top, TOP_TEXTFIELD_COUNT);
        initializeTextFields(txt_array_down, DOWN_TEXTFIELD_COUNT);
        setupPanelLayout();
        initializeMainPanel();
    }

    private void addButtonEvents() {
        buttons.get(0).addActionListener(e -> addChiTietHoaDon(0, 5, count, "HD"));
        buttons.get(1).addActionListener(e -> deleteChiTietHoaDon());
        // buttons.get(2).addActionListener(e -> thanhToan());
    }

    private JPanel createSearchPanel() {
        String[] searchOptions = { "Mã sách", "Tên sách" };
        comboBox = new JComboBox<>(searchOptions);
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchPanel.add(Box.createHorizontalStrut(33));
        searchPanel.add(tool.createSearchTextFieldTest(comboBox, txt_search));
        return searchPanel;
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
        buttons = new ArrayList<>(BTN_COUNT);
        DefaultTableModel model_down = new DefaultTableModel(column, 0);
        paymentPanel.add(createTable_down(column, model_down, 500, 240), BorderLayout.EAST);
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
        super.addChiTietHoaDon(0, 5, count, "HD");
        String diemStr = txt_array_top.get(4).getText().trim();
        int diem = (!diemStr.isEmpty()) ? Integer.parseInt(diemStr) : 0;
        int tien = Integer.parseInt(txt_array_top.get(6).getText());
        if (diem * 1000 > tien) {
            tienGiamGia = 0;
        } else {
            tienGiamGia = diem * 1000;
        }
    }

    public SellBookGUI() {
        initializeBaseFields();
        initializeCustomFields();
        // Additional setup for SellBookGUI can be done here
    }
}