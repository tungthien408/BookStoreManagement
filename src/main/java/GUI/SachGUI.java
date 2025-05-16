package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import BUS.NXBBUS;
import BUS.SachBUS;
import BUS.TacGiaBUS;
import DTO.SachDTO;

public class SachGUI {
    private static final int WIDTH = 1200;
    private static final int SIDE_MENU_WIDTH = 151;
    private static final int HEIGHT = (int) (WIDTH * 0.625);
    private static final int TABLE_WIDTH = 850;
    private static final int TABLE_HEIGHT = 300;
    private static final int DETAIL_PANEL_WIDTH = 500;
    private static final int DETAIL_PANEL_HEIGHT = 200;

    private Tool tool = new Tool();
    private JPanel panel, panelDetail;
    private JTextField[] txt_array = new JTextField[6]; // For maSach, tenSach, theLoai, soLuong, donGia, maTG
    private JButton[] buttons = new JButton[6];
    private JTable table;
    private List<SachDTO> sachList;
    private SachBUS sachBUS = new SachBUS();
    private NXBBUS nxbBUS = new NXBBUS();
    private TacGiaBUS tacGiaBUS = new TacGiaBUS();

    private int selectedRow = -1;
    private int lastSelectedRow = -1;
    private boolean add = false;
    private boolean update = false;
    private boolean delete = false;
    private int count = 0;
    private JTextField[] txt_array_search = new JTextField[1];
    private JTextField txt_search;
    private JComboBox<String> comboBox;

    public String getID() {
        String str = String.valueOf(count);
        while (str.length() != 3) {
            str = "0" + str;
        }
        return "S" + str;
    }

    public SachGUI() {
        txt_search = new JTextField();
        txt_array_search = new JTextField[] { txt_search };
        panel = tool.createPanel(WIDTH - SIDE_MENU_WIDTH, HEIGHT, new BorderLayout());
        panel.setBackground(new Color(202, 220, 252));
        initializeTextFields();
        initializeData();
        setupPanelLayout();
        timkiem();
    }

    private void initializeTextFields() {
        for (int i = 0; i < txt_array.length; i++) {
            txt_array[i] = new JTextField();
        }
    }

    private void initializeData() {
        try {
            sachList = sachBUS.getAllSach();
            if (!sachList.isEmpty()) {
                String maSach = sachList.get(sachList.size() - 1).getMaSach();
                String numericPart = maSach.substring(1);
                count = Integer.parseInt(numericPart) + 1;
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi khi tải dữ liệu sách: " + e.getMessage());
        }
    }

    private void setupPanelLayout() {
        // Add search panel at the top
        panel.add(createSearchPanel(), BorderLayout.NORTH);

        // Create a center panel to hold table and detail panel
        JPanel centerPanel = new JPanel(new BorderLayout());

        // Add table to the center
        centerPanel.add(createBookTable(), BorderLayout.NORTH);

        // Create lower panel for detail
        String[] txt_label = { "Mã sách", "Tên sách", "Thể loại", "Số lượng", "Đơn giá", "Mã tác giả" };
        centerPanel.add(createDetailPanel(txt_array, txt_label), BorderLayout.CENTER);

        // Add center panel to main panel
        panel.add(centerPanel, BorderLayout.CENTER);

        // Add button panel to the right
        panel.add(createPanelButton(), BorderLayout.EAST);
    }

    private JPanel createBookTable() {
        String[] columns = { "Mã sách", "Tên sách", "Thể loại", "Số lượng", "Đơn giá", "Mã tác giả" };
        DefaultTableModel model = new DefaultTableModel(columns, 0);

        try {
            for (SachDTO sach : sachList) {
                model.addRow(new Object[] {
                        sach.getMaSach(),
                        sach.getTenSach(),
                        sach.getTheLoai(),
                        sach.getSoLuong(),
                        sach.getDonGia(),
                        sach.getMaTG()
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi khi tải dữ liệu sách: " + e.getMessage());
        }

        table = tool.createTable(model, columns);
        table.setDefaultEditor(Object.class, null);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(TABLE_WIDTH, TABLE_HEIGHT));
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 40, 10, 10));

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (add || update) {
                    return;
                }

                tool.clearFields(txt_array);
                tool.clearButtons(buttons);
                add = false;

                selectedRow = table.getSelectedRow();

                if (selectedRow == lastSelectedRow && selectedRow >= 0) {
                    table.clearSelection();
                    update = false;
                    delete = false;

                    for (JTextField txt : txt_array) {
                        txt.setText("");
                        txt.setEditable(true);
                    }

                    lastSelectedRow = -1;
                } else if (selectedRow >= 0) {
                    for (int i = 0; i < txt_array.length; i++) {
                        txt_array[i].setText(String.valueOf(table.getValueAt(selectedRow, i)));
                        txt_array[i].setEditable(false);
                    }
                    if (update) {
                        tool.Editable(txt_array, true);
                        txt_array[0].setEditable(false);
                    }
                    if (delete) {
                        tool.Editable(txt_array, false);
                    }

                    lastSelectedRow = selectedRow;
                }
            }
        });

        JPanel panelTable = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelTable.add(scrollPane);
        return panelTable;
    }

    private JPanel createPanelButton() {
        String[] txt_btn = { "Thêm", "Sửa", "Xóa", "Nhập Excel", "Xuất Excel", "Hủy" };
        JPanel panelBtn = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelBtn.add(tool.createButtonPanel(buttons, txt_btn, new Color(0, 36, 107), Color.WHITE, "y"));

        buttons[0].addActionListener(e -> addSach());
        buttons[1].addActionListener(e -> updateSach());
        buttons[2].addActionListener(e -> deleteSach());
        buttons[3].addActionListener(e -> importFromExcel());
        buttons[4].addActionListener(e -> exportToExcel());
        buttons[5].addActionListener(e -> cancel());

        return panelBtn;
    }

    private JPanel createDetailPanel(JTextField[] txt_array, String[] txt_label) {
        panelDetail = tool.createDetailPanel(txt_array, txt_label, null, DETAIL_PANEL_WIDTH, DETAIL_PANEL_HEIGHT, 0.5, 3,
                false);

        JPanel wrappedPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        wrappedPanel.add(panelDetail);
        wrappedPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));
        return wrappedPanel;
    }

    private JPanel createSearchPanel() {
        String[] searchOptions = { "Mã sách", "Tên sách", "Thể loại" };
        comboBox = new JComboBox<>(searchOptions);
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchPanel.add(Box.createHorizontalStrut(33));
        searchPanel.add(tool.createSearchTextFieldTest(comboBox, txt_array_search));
        return searchPanel;
    }

    private void timkiem() {
        comboBox.addActionListener(e -> {
            String selectedOption = (String) comboBox.getSelectedItem();
            filterTable(txt_array_search[0].getText(), selectedOption);
        });

        // Add real-time search
        txt_array_search[0].addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyReleased(java.awt.event.KeyEvent e) {
                String selectedOption = (String) comboBox.getSelectedItem();
                filterTable(txt_array_search[0].getText(), selectedOption);
            }
        });
    }

    private void filterTable(String query, String searchType) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);
        try {
            for (SachDTO sach : sachList) {
                boolean match = false;
                switch (searchType) {
                    case "Mã sách":
                        match = sach.getMaSach().toLowerCase().contains(query.toLowerCase());
                        break;
                    case "Tên sách":
                        match = sach.getTenSach().toLowerCase().contains(query.toLowerCase());
                        break;
                    case "Thể loại":
                        match = sach.getTheLoai().toLowerCase().contains(query.toLowerCase());
                        break;
                }
                if (match) {
                    model.addRow(new Object[] {
                            sach.getMaSach(),
                            sach.getTenSach(),
                            sach.getTheLoai(),
                            sach.getSoLuong(),
                            sach.getDonGia(),
                            sach.getMaTG()
                    });
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi khi lọc dữ liệu: " + e.getMessage());
        }
    }

    private void refreshTable() {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);
        try {
            sachList = sachBUS.getAllSach();
            for (SachDTO sach : sachList) {
                model.addRow(new Object[] {
                        sach.getMaSach(),
                        sach.getTenSach(),
                        sach.getTheLoai(),
                        sach.getSoLuong(),
                        sach.getDonGia(),
                        sach.getMaTG()
                });
            }
            if (!sachList.isEmpty()) {
                String maSach = sachList.get(sachList.size() - 1).getMaSach();
                String numericPart = maSach.substring(1);
                count = Integer.parseInt(numericPart) + 1;
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi khi làm mới bảng: " + e.getMessage());
        }
    }

    private void addSach() {
        table.clearSelection();
        update = false;
        delete = false;
        if (!add) {
            add = true;
            tool.clearFields(txt_array);
            tool.clearButtons(buttons);
            tool.Editable(txt_array, true);
            txt_array[0].setText(getID());
            buttons[0].setBackground(new Color(202, 220, 252));
            buttons[0].setForeground(Color.BLACK);
            buttons[5].setBackground(Color.RED);

            for (int i = 0, length = buttons.length - 1; i < length; i++) {
                if (i != 0) {
                    buttons[i].setVisible(false);
                }
            }
            txt_array[3].setText("0");
            txt_array[0].setEditable(false);
            txt_array[3].setEditable(false);
        } else {
            try {
                SachDTO sach = new SachDTO();
                sach.setMaSach(txt_array[0].getText().trim());
                sach.setTenSach(txt_array[1].getText().trim());
                sach.setTheLoai(txt_array[2].getText().trim());
                sach.setDonGia(Integer.parseInt(txt_array[4].getText().trim()));
                sach.setMaTG(txt_array[5].getText().trim());
                sach.setSoLuong(0);
                sach.setMaNXB(null);

                if (!checkValidate(sach)) {
                    return;
                }

                if (sachBUS.addSach(sach)) {
                    JOptionPane.showMessageDialog(null, "Thêm sách thành công!");
                    cancel();
                } else {
                    JOptionPane.showMessageDialog(null, "Thêm sách thất bại!");
                }
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Lỗi khi thêm sách: " + e.getMessage());
            }
        }
    }

    private void updateSach() {
        if (add) {
            tool.clearFields(txt_array);
        }
        add = false;
        delete = false;
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(null, "Vui lòng chọn sách để sửa!");
        } else if (!update) {
            update = true;
            tool.clearButtons(buttons);
            tool.Editable(txt_array, true);

            buttons[1].setBackground(new Color(202, 220, 252));
            buttons[1].setForeground(Color.BLACK);
            buttons[5].setBackground(Color.RED);

            for (int i = 0, length = buttons.length - 1; i < length; i++) {
                if (i != 1) {
                    buttons[i].setVisible(false);
                }
            }

            txt_array[0].setEditable(false);
        } else {
            try {
                SachDTO sach = new SachDTO();
                sach.setMaSach(txt_array[0].getText().trim());
                sach.setTenSach(txt_array[1].getText().trim());
                sach.setTheLoai(txt_array[2].getText().trim());
                sach.setSoLuong(Integer.parseInt(txt_array[3].getText().trim()));
                sach.setDonGia(Integer.parseInt(txt_array[4].getText().trim()));
                sach.setMaTG(txt_array[5].getText().trim());

                if (sach.getMaSach().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Vui lòng chọn sách để sửa!");
                    return;
                }

                if (!checkValidate(sach)) {
                    return;
                }

                if (sachBUS.updateSach(sach)) {
                    JOptionPane.showMessageDialog(null, "Sửa sách thành công!");
                    cancel();
                } else {
                    JOptionPane.showMessageDialog(null, "Sửa sách thất bại!");
                }
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Lỗi khi sửa sách: " + e.getMessage());
            }
        }
    }

    private void deleteSach() {
        if (add) {
            tool.clearFields(txt_array);
        }
        add = false;
        update = false;
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(null, "Vui lòng chọn sách để xóa!");
        } else {
            try {
                String maSach = txt_array[0].getText().trim();
                if (maSach.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Vui lòng chọn sách để xóa!");
                    return;
                }
                if (JOptionPane.showConfirmDialog(null, "Bạn có chắc muốn xóa sách này?", "Xóa thông tin sách",
                        JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                    if (sachBUS.deleteSach(maSach)) {
                        JOptionPane.showMessageDialog(null, "Xóa sách thành công!");
                        cancel();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Lỗi khi xóa sách: " + e.getMessage());
            }
        }
    }

    private void importFromExcel() {
        JOptionPane.showMessageDialog(null, "Chức năng Nhập Excel đang được phát triển!");
    }

    private void exportToExcel() {
        JOptionPane.showMessageDialog(null, "Chức năng Xuất Excel đang được phát triển!");
    }

    private void cancel() {
        add = false;
        update = false;
        delete = false;
        refreshTable();
        tool.clearButtons(buttons);
        tool.clearFields(txt_array);
        tool.Editable(txt_array, false);
        selectedRow = -1;
        lastSelectedRow = -1;
    }

    private boolean checkValidate(SachDTO sach) {
        if (sach.getMaSach().isEmpty() || sach.getTenSach().isEmpty() || sach.getTheLoai().isEmpty() ||
                sach.getMaTG().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Vui lòng điền đầy đủ các trường thông tin!");
            return false;
        }

        if (sach.getTenSach().length() > 100) {
            JOptionPane.showMessageDialog(null, "Tên sách không được nhiều hơn 100 ký tự!");
            return false;
        }

        if (sach.getTheLoai().length() > 50) {
            JOptionPane.showMessageDialog(null, "Thể loại không được nhiều hơn 50 ký tự!");
            return false;
        }

        if (!tacGiaBUS.isTacGiaExists(sach.getMaTG())) {
            JOptionPane.showMessageDialog(null, "Mã tác giả không tồn tại!");
            return false;
        }

        return true;
    }

    public JPanel getPanel() {
        return this.panel;
    }
}