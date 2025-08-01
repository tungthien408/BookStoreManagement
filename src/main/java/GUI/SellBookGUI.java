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
        // buttons.get(0).addActionListener(e -> addChiTietHoaDon());
        // buttons.get(1).addActionListener(e -> deleteChiTietHoaDon());
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
        // top table 
        panel.add(createSearchPanel(), BorderLayout.NORTH);
        String[] column = new String[] { "Mã sách", "Tên sách", "Số lượng", "Đơn giá" };
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

        String[] txt_label_top = { "Mã hóa đơn", "Nhân viên", "SDT KH", "Tên KH", "Điểm tích lũy", "Ngày bán",
                "Tổng tiền" };
        panel.add(createDetailPanel_top(450, -10, txt_array_top, txt_label_top, null), BorderLayout.CENTER);

        JPanel lowerPanel = new JPanel(new BorderLayout(10, 0));
        String[] txt_label_down = { "Mã sách", "Số lượng" };
        JPanel detailPanelDown = createDetailPanel_down(300, 10, txt_array_down, txt_label_down);
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

    public SellBookGUI() {
        initializeBaseFields();
        initializeCustomFields();
        // Additional setup for SellBookGUI can be done here
    }
}