package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import BUS.MaGiamGiaBUS;
import DTO.MaGiamGiaDTO;

public class MaGiamGiaGUI {
    private Tool tool = new Tool();
    private JPanel panel, panelDetail;
    private List<MaGiamGiaDTO> maGiamGiaList;
    private JTextField[] txt_array = new JTextField[6]; // maGiamGia, tenGiamGia, phanTramGiam, soTienGiam, ngayBatDau, ngayKetThuc
    private JComboBox<String> cbLoaiGiamGia; // For loaiGiamGia
    private int width = 1200;
    private int width_sideMenu = 151;
    private int height = (int) (width * 0.625);
    private JButton[] buttons = new JButton[6];
    private JTable table;
    private MaGiamGiaBUS bus = new MaGiamGiaBUS();
    private int selectedRow = -1;
    private int lastSelectedRow = -1;
    private boolean add = false;
    private boolean update = false;
    private boolean delete = false;
    private JTextField[] txt_array_search = new JTextField[1];
    private JTextField txt_search;
    private JComboBox<String> comboBox;
    private int count = 1;

    public String getID() {
        String str = String.valueOf(count);
        while (str.length() < 3) {
            str = "0" + str;
        }
        return "MGG" + str;
    }

    public MaGiamGiaGUI() {
        txt_search = new JTextField();
        txt_array_search = new JTextField[] { txt_search };
        for (int i = 0; i < txt_array.length; i++) {
            txt_array[i] = new JTextField();
        }
        cbLoaiGiamGia = new JComboBox<>(new String[] { "Phần Trăm", "Số Tiền" });

        panel = tool.createPanel(width - width_sideMenu, height, new BorderLayout());
        panel.setBackground(new Color(202, 220, 252));
        panel.add(createSearchPanel(), BorderLayout.NORTH);
        panel.add(createMaGiamGiaTable(), BorderLayout.WEST);
        panel.add(createPanelButton(), BorderLayout.CENTER);

        String[] txt_label = { "Mã Giảm Giá", "Tên Giảm Giá", "Phần Trăm Giảm", "Số Tiền Giảm", "Ngày Bắt Đầu", "Ngày Kết Thúc" };
        panel.add(createPanelDetail(txt_array, txt_label), BorderLayout.SOUTH);

        // Handle loaiGiamGia selection to enable/disable fields
        cbLoaiGiamGia.addActionListener(e -> updateFieldState());
        updateFieldState(); // Initial state
        timkiem();
    }

    private void updateFieldState() {
        boolean isPhanTram = cbLoaiGiamGia.getSelectedIndex() == 0;
        txt_array[2].setEnabled(isPhanTram); // phanTramGiam
        txt_array[3].setEnabled(!isPhanTram); // soTienGiam
        if (!isPhanTram) {
            txt_array[2].setText("0");
        } else {
            txt_array[3].setText("0");
        }
    }

    private JPanel createMaGiamGiaTable() {
        String[] column = { "Mã Giảm Giá", "Tên Giảm Giá", "Phần Trăm Giảm", "Số Tiền Giảm", "Loại", "Ngày Bắt Đầu", "Ngày Kết Thúc" };
        DefaultTableModel model = new DefaultTableModel(column, 0);

        try {
            maGiamGiaList = bus.getAllMaGiamGia();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            for (MaGiamGiaDTO dto : maGiamGiaList) {
                model.addRow(new Object[] {
                        dto.getMaGiamGia() != null ? dto.getMaGiamGia() : "",
                        dto.getTenGiamGia() != null ? dto.getTenGiamGia() : "",
                        dto.getPhanTramGiam(),
                        dto.getSoTienGiam(),
                        dto.getLoaiGiamGia() == 1 ? "Phần Trăm" : "Số Tiền",
                        dto.getNgayBatDau() != null ? sdf.format(dto.getNgayBatDau()) : "",
                        dto.getNgayKetThuc() != null ? sdf.format(dto.getNgayKetThuc()) : ""
                });
            }
            if (!maGiamGiaList.isEmpty()) {
                String maGiamGia = maGiamGiaList.get(maGiamGiaList.size() - 1).getMaGiamGia();
                String numericPart = maGiamGia.replaceAll("[^0-9]", "");
                count = Integer.parseInt(numericPart) + 1;
            } else {
                count = 1;
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi khi tải dữ liệu từ cơ sở dữ liệu: " + e.getMessage());
        }

        table = tool.createTable(model, column);
        table.setDefaultEditor(Object.class, null);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(850, 440));

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (add || update) {
                    return;
                }

                tool.clearFields(txt_array);
                cbLoaiGiamGia.setSelectedIndex(0);
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
                    cbLoaiGiamGia.setEnabled(true);
                    updateFieldState();

                    lastSelectedRow = -1;
                } else if (selectedRow >= 0) {
                    for (int i = 0; i < txt_array.length; i++) {
                        Object value = table.getValueAt(selectedRow, i);
                        txt_array[i].setText(value != null ? value.toString() : "");
                        txt_array[i].setEditable(false);
                    }
                    String loaiGiamGia = table.getValueAt(selectedRow, 4).toString();
                    cbLoaiGiamGia.setSelectedItem(loaiGiamGia);
                    cbLoaiGiamGia.setEnabled(false);
                    updateFieldState();

                    if (update) {
                        tool.Editable(txt_array, true);
                        txt_array[0].setEditable(false);
                        cbLoaiGiamGia.setEnabled(true);
                        updateFieldState();
                    }
                    if (delete) {
                        tool.Editable(txt_array, false);
                        cbLoaiGiamGia.setEnabled(false);
                    }

                    lastSelectedRow = selectedRow;
                }
            }
        });

        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 40, 30, 10));
        JPanel panelTable = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelTable.add(scrollPane);
        return panelTable;
    }

    private JPanel createPanelButton() {
        String[] btn_txt = { "Thêm", "Sửa", "Xóa", "Nhập Excel", "Xuất Excel", "Hủy" };
        JPanel panelBtn = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelBtn.add(tool.createButtonPanel(buttons, btn_txt, new Color(0, 36, 107), Color.WHITE, "y"));

        buttons[0].addActionListener(e -> themMaGiamGia());
        buttons[1].addActionListener(e -> suaMaGiamGia());
        buttons[2].addActionListener(e -> xoaMaGiamGia());
        buttons[3].addActionListener(e -> importExcel());
        buttons[4].addActionListener(e -> exportExcel());
        buttons[5].addActionListener(e -> cancel());

        return panelBtn;
    }

    private JPanel createPanelDetail(JTextField[] txt_array, String[] txt_label) {
        JPanel panelDetail = new JPanel();
        panelDetail.setLayout(new FlowLayout(FlowLayout.CENTER));
        panelDetail.setPreferredSize(new Dimension(850, 180));

        for (int i = 0; i < txt_array.length; i++) {
            JPanel fieldPanel = new JPanel();
            fieldPanel.setLayout(new BoxLayout(fieldPanel, BoxLayout.Y_AXIS));
            fieldPanel.add(tool.createLabel(txt_label[i]));
            txt_array[i].setPreferredSize(new Dimension(150, 30));
            fieldPanel.add(txt_array[i]);
            panelDetail.add(fieldPanel);
            panelDetail.add(Box.createHorizontalStrut(10));
        }

        JPanel comboPanel = new JPanel();
        comboPanel.setLayout(new BoxLayout(comboPanel, BoxLayout.Y_AXIS));
        comboPanel.add(tool.createLabel("Loại"));
        cbLoaiGiamGia.setPreferredSize(new Dimension(150, 30));
        comboPanel.add(cbLoaiGiamGia);
        panelDetail.add(comboPanel);

        JPanel wrappedPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        wrappedPanel.add(panelDetail);
        wrappedPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));
        return wrappedPanel;
    }

    private JPanel createSearchPanel() {
        String[] searchOptions = { "Mã Giảm Giá", "Tên Giảm Giá" };
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
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            for (MaGiamGiaDTO dto : maGiamGiaList) {
                boolean match = false;
                switch (searchType) {
                    case "Mã Giảm Giá":
                        match = dto.getMaGiamGia() != null && dto.getMaGiamGia().toLowerCase().contains(query.toLowerCase());
                        break;
                    case "Tên Giảm Giá":
                        match = dto.getTenGiamGia() != null && dto.getTenGiamGia().toLowerCase().contains(query.toLowerCase());
                        break;
                }
                if (match) {
                    model.addRow(new Object[] {
                            dto.getMaGiamGia() != null ? dto.getMaGiamGia() : "",
                            dto.getTenGiamGia() != null ? dto.getTenGiamGia() : "",
                            dto.getPhanTramGiam(),
                            dto.getSoTienGiam(),
                            dto.getLoaiGiamGia() == 1 ? "Phần Trăm" : "Số Tiền",
                            dto.getNgayBatDau() != null ? sdf.format(dto.getNgayBatDau()) : "",
                            dto.getNgayKetThuc() != null ? sdf.format(dto.getNgayKetThuc()) : ""
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
            maGiamGiaList = bus.getAllMaGiamGia();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            for (MaGiamGiaDTO dto : maGiamGiaList) {
                model.addRow(new Object[] {
                        dto.getMaGiamGia() != null ? dto.getMaGiamGia() : "",
                        dto.getTenGiamGia() != null ? dto.getTenGiamGia() : "",
                        dto.getPhanTramGiam(),
                        dto.getSoTienGiam(),
                        dto.getLoaiGiamGia() == 1 ? "Phần Trăm" : "Số Tiền",
                        dto.getNgayBatDau() != null ? sdf.format(dto.getNgayBatDau()) : "",
                        dto.getNgayKetThuc() != null ? sdf.format(dto.getNgayKetThuc()) : ""
                });
            }
            if (!maGiamGiaList.isEmpty()) {
                String maGiamGia = maGiamGiaList.get(maGiamGiaList.size() - 1).getMaGiamGia();
                String numericPart = maGiamGia.replaceAll("[^0-9]", "");
                count = Integer.parseInt(numericPart) + 1;
            } else {
                count = 1;
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi khi làm mới bảng: " + e.getMessage());
        }
    }

    private void themMaGiamGia() {
        table.clearSelection();
        update = false;
        delete = false;
        if (!add) {
            add = true;
            tool.clearFields(txt_array);
            cbLoaiGiamGia.setSelectedIndex(0);
            tool.clearButtons(buttons);
            tool.Editable(txt_array, true);
            txt_array[0].setText(getID());
            txt_array[0].setEditable(false);
            cbLoaiGiamGia.setEnabled(true);
            updateFieldState();
            buttons[0].setBackground(new Color(202, 220, 252));
            buttons[0].setForeground(Color.BLACK);
            buttons[5].setBackground(Color.RED);

            for (int i = 0; i < buttons.length; i++) {
                buttons[i].setVisible(i == 0 || i == 5);
            }
        } else {
            try {
                MaGiamGiaDTO dto = new MaGiamGiaDTO();
                dto.setMaGiamGia(txt_array[0].getText().trim());
                dto.setTenGiamGia(txt_array[1].getText().trim());
                String phanTramGiamStr = txt_array[2].getText().trim();
                String soTienGiamStr = txt_array[3].getText().trim();
                int loaiGiamGia = cbLoaiGiamGia.getSelectedIndex() == 0 ? 1 : 0;
                dto.setLoaiGiamGia(loaiGiamGia);
                dto.setPhanTramGiam(phanTramGiamStr.isEmpty() ? 0 : Integer.parseInt(phanTramGiamStr));
                dto.setSoTienGiam(soTienGiamStr.isEmpty() ? 0 : Integer.parseInt(soTienGiamStr));
                dto.setNgayBatDau(Date.valueOf(txt_array[4].getText().trim()));
                dto.setNgayKetThuc(Date.valueOf(txt_array[5].getText().trim()));
                dto.setTrangThaiXoa(0);

                if (!checkValidate(dto)) {
                    return;
                }

                if (bus.getMaGiamGiaById(dto.getMaGiamGia()) != null) {
                    JOptionPane.showMessageDialog(null, "Mã giảm giá đã tồn tại!");
                    return;
                }

                if (bus.addMaGiamGia(dto)) {
                    JOptionPane.showMessageDialog(null, "Thêm mã giảm giá thành công!");
                    count++;
                    cancel();
                } else {
                    JOptionPane.showMessageDialog(null, "Thêm mã giảm giá thất bại!");
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Phần trăm giảm hoặc số tiền giảm phải là số nguyên!");
            } catch (IllegalArgumentException e) {
                JOptionPane.showMessageDialog(null, "Ngày phải có định dạng yyyy-MM-dd!");
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Lỗi khi thêm mã giảm giá: " + e.getMessage());
            }
        }
    }

    private void suaMaGiamGia() {
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(null, "Vui lòng chọn mã giảm giá để sửa!");
        } else if (!update) {
            add = false;
            delete = false;
            update = true;
            tool.clearButtons(buttons);
            tool.Editable(txt_array, true);
            txt_array[0].setEditable(false);
            cbLoaiGiamGia.setEnabled(true);
            updateFieldState();
            buttons[1].setBackground(new Color(202, 220, 252));
            buttons[1].setForeground(Color.BLACK);
            buttons[5].setBackground(Color.RED);

            for (int i = 0; i < buttons.length; i++) {
                buttons[i].setVisible(i == 1 || i == 5);
            }
        } else {
            try {
                MaGiamGiaDTO dto = new MaGiamGiaDTO();
                dto.setMaGiamGia(txt_array[0].getText().trim());
                dto.setTenGiamGia(txt_array[1].getText().trim());
                String phanTramGiamStr = txt_array[2].getText().trim();
                String soTienGiamStr = txt_array[3].getText().trim();
                int loaiGiamGia = cbLoaiGiamGia.getSelectedIndex() == 0 ? 1 : 0;
                dto.setLoaiGiamGia(loaiGiamGia);
                dto.setPhanTramGiam(phanTramGiamStr.isEmpty() ? 0 : Integer.parseInt(phanTramGiamStr));
                dto.setSoTienGiam(soTienGiamStr.isEmpty() ? 0 : Integer.parseInt(soTienGiamStr));
                dto.setNgayBatDau(Date.valueOf(txt_array[4].getText().trim()));
                dto.setNgayKetThuc(Date.valueOf(txt_array[5].getText().trim()));
                dto.setTrangThaiXoa(0);

                if (dto.getMaGiamGia().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Mã giảm giá không được để trống!");
                    return;
                }

                if (!checkValidate(dto)) {
                    return;
                }

                if (bus.updateMaGiamGia(dto)) {
                    JOptionPane.showMessageDialog(null, "Sửa mã giảm giá thành công!");
                    cancel();
                } else {
                    JOptionPane.showMessageDialog(null, "Sửa mã giảm giá thất bại!");
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Phần trăm giảm hoặc số tiền giảm phải là số nguyên!");
            } catch (IllegalArgumentException e) {
                JOptionPane.showMessageDialog(null, "Ngày phải có định dạng yyyy-MM-dd!");
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Lỗi khi sửa mã giảm giá: " + e.getMessage());
            }
        }
    }

    private void xoaMaGiamGia() {
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(null, "Vui lòng chọn mã giảm giá để xóa!");
        } else if (!delete) {
            add = false;
            update = false;
            delete = true;
            tool.clearButtons(buttons);
            tool.Editable(txt_array, false);
            cbLoaiGiamGia.setEnabled(false);
            buttons[2].setBackground(new Color(202, 220, 252));
            buttons[2].setForeground(Color.BLACK);
            buttons[5].setBackground(Color.RED);

            for (int i = 0; i < buttons.length; i++) {
                buttons[i].setVisible(i == 2 || i == 5);
            }
        } else {
            try {
                String maGiamGia = txt_array[0].getText().trim();
                if (maGiamGia.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Mã giảm giá không được để trống!");
                    return;
                }
                if (JOptionPane.showConfirmDialog(null, "Bạn có chắc muốn xóa mã giảm giá này?",
                        "Xóa mã giảm giá", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                    if (bus.deleteMaGiamGia(maGiamGia)) {
                        JOptionPane.showMessageDialog(null, "Xóa mã giảm giá thành công!");
                        cancel();
                    } else {
                        JOptionPane.showMessageDialog(null, "Xóa mã giảm giá thất bại!");
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Lỗi khi xóa mã giảm giá: " + e.getMessage());
            }
        }
    }

    private void importExcel() {
        JOptionPane.showMessageDialog(null, "Chức năng Nhập Excel chưa được triển khai!");
    }

    private void exportExcel() {
        JOptionPane.showMessageDialog(null, "Chức năng Xuất Excel chưa được triển khai!");
    }

    private void cancel() {
        add = false;
        update = false;
        delete = false;
        refreshTable();
        tool.clearButtons(buttons);
        tool.clearFields(txt_array);
        cbLoaiGiamGia.setSelectedIndex(0);
        tool.Editable(txt_array, false);
        cbLoaiGiamGia.setEnabled(false);
        updateFieldState();
        selectedRow = -1;
        lastSelectedRow = -1;
    }

    private boolean checkValidate(MaGiamGiaDTO dto) {
        if (dto.getMaGiamGia().isEmpty() || dto.getTenGiamGia().isEmpty() ||
                txt_array[4].getText().trim().isEmpty() || txt_array[5].getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Vui lòng điền đầy đủ các trường bắt buộc (Mã, Tên, Ngày)");
            return false;
        }

        if (!isValidName(dto.getTenGiamGia())) {
            JOptionPane.showMessageDialog(null, "Tên giảm giá chỉ được chứa chữ cái, số, khoảng trắng, dấu gạch ngang hoặc dấu nháy đơn");
            return false;
        }

        if (dto.getTenGiamGia().length() > 50) {
            JOptionPane.showMessageDialog(null, "Tên giảm giá không được vượt quá 50 ký tự");
            return false;
        }

        if (dto.getLoaiGiamGia() == 1) {
            if (txt_array[2].getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Phần trăm giảm không được để trống cho loại phần trăm");
                return false;
            }
            if (dto.getPhanTramGiam() < 1 || dto.getPhanTramGiam() > 100) {
                JOptionPane.showMessageDialog(null, "Phần trăm giảm phải từ 1 đến 100");
                return false;
            }
            if (dto.getSoTienGiam() != 0) {
                JOptionPane.showMessageDialog(null, "Số tiền giảm phải là 0 cho loại phần trăm");
                return false;
            }
        } else {
            if (txt_array[3].getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Số tiền giảm không được để trống cho loại số tiền");
                return false;
            }
            if (dto.getSoTienGiam() <= 0) {
                JOptionPane.showMessageDialog(null, "Số tiền giảm phải lớn hơn 0");
                return false;
            }
            if (dto.getPhanTramGiam() != 0) {
                JOptionPane.showMessageDialog(null, "Phần trăm giảm phải là 0 cho loại số tiền");
                return false;
            }
        }

        if (dto.getNgayBatDau().after(dto.getNgayKetThuc())) {
            JOptionPane.showMessageDialog(null, "Ngày bắt đầu phải trước hoặc bằng ngày kết thúc");
            return false;
        }

        for (MaGiamGiaDTO existing : maGiamGiaList) {
            if (!existing.getMaGiamGia().equals(dto.getMaGiamGia()) &&
                    existing.getTenGiamGia().toLowerCase().equals(dto.getTenGiamGia().toLowerCase())) {
                JOptionPane.showMessageDialog(null, "Tên giảm giá đã được sử dụng");
                return false;
            }
        }

        return true;
    }

    private boolean isValidName(String name) {
        return name.matches("^[\\p{L}\\p{N}\\s'-]+$");
    }

    public JPanel getPanel() {
        return this.panel;
    }
}