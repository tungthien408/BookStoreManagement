package GUI;

import BUS.QuyenBUS;
import BUS.TaiKhoanNVBUS;
import DTO.QuyenDTO;
import DTO.TaiKhoanNVDTO;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

public class PhanQuyenGUI extends JPanel {
    private Tool tool = new Tool();
    private JPanel panel, panelDetail;
    private JButton[] btn = new JButton[4];
    private JTable table;
    private JTextField[] txt_array = new JTextField[2];
    private JTextField txt_search;
    private JComboBox<String> comboBox;
    private JTextField[] txt_array_search = new JTextField[1];
    private QuyenBUS quyenBUS;
    private TaiKhoanNVBUS taiKhoanBUS;
    private List<QuyenDTO> listQuyen;
    private int selectedRow = -1;
    private int lastSelectedRow = -1;
    private boolean add = false;
    private boolean update = false;
    private boolean delete = false;
    private int width = 1200;
    private int width_sideMenu = 151;
    private int height = (int)(width * 0.625);
    private String username;
    private int count = 0;

    public String getID() {
        String str = String.valueOf(count);
        while (str.length() != 3)
            str = "0" + str;
        return str;
    }

    public PhanQuyenGUI(TaiKhoanNVDTO taiKhoan) {
        this.quyenBUS = new QuyenBUS();
        this.taiKhoanBUS = new TaiKhoanNVBUS();
        this.username = taiKhoan.getMaNV();
        this.txt_search = new JTextField();
        this.txt_array_search = new JTextField[]{txt_search};

        panel = tool.createPanel(width - width_sideMenu, height, new BorderLayout());
        panel.setBackground(new Color(202, 220, 252));
        panel.add(createQuyenTable(), BorderLayout.WEST);
        panel.add(createPanelButton(), BorderLayout.CENTER);

        String[] txt_label = {"Mã Quyền", "Tên Quyền"};
        panel.add(createDetailPanel(txt_array, txt_label), BorderLayout.SOUTH);
        panel.add(createSearchPanel(), BorderLayout.NORTH);

        timkiem();
        applyPermissions(username, 9);
    }

    private JPanel createQuyenTable() {
        String[] column = {"Mã Quyền", "Tên Quyền"};
        DefaultTableModel model = new DefaultTableModel(column, 0);

        try {
            listQuyen = quyenBUS.getAllQuyen();
            for (QuyenDTO quyen : listQuyen) {
                model.addRow(new Object[]{
                    quyen.getMaQuyen(),
                    quyen.getTenQuyen()
                });
                int maQuyen = listQuyen.get(listQuyen.size() - 1).getMaQuyen();
                // String numericPart = maQuyen.substring(5);
                count = maQuyen + 1;
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi khi tải dữ liệu từ cơ sở dữ liệu: " + e.getMessage());
        }

        table = tool.createTable(model, column);
        table.setDefaultEditor(Object.class, null);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(850, 540));

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (add || update) return;

                tool.clearFields(txt_array);
                tool.clearButtons(btn);
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
                        txt_array[i].setText((String)table.getValueAt(selectedRow, i));
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

        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 40, 30, 10));
        JPanel panelTable = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelTable.add(scrollPane);
        return panelTable;
    }

    private JPanel createPanelButton() {
        String[] btn_txt = {"Thêm", "Sửa", "Xóa", "Hủy"};
        JPanel panelBtn = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelBtn.add(tool.createButtonPanel(btn, btn_txt, new Color(0, 36, 107), Color.WHITE, "y"));

        btn[0].addActionListener(e -> add());
        btn[1].addActionListener(e -> update());
        btn[2].addActionListener(e -> delete());
        btn[3].addActionListener(e -> cancel());

        return panelBtn;
    }

    private JPanel createDetailPanel(JTextField[] txt_array, String[] txt_label) {
        panelDetail = tool.createDetailPanel(txt_array, txt_label, null, 850, 90, 0.5, 2, false);
        JPanel wrappedPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        wrappedPanel.add(panelDetail);
        wrappedPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));
        return wrappedPanel;
    }

    private JPanel createSearchPanel() {
        String[] searchOptions = {"Mã", "Tên"};
        comboBox = new JComboBox<>(searchOptions);
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchPanel.add(Box.createHorizontalStrut(33));
        searchPanel.add(tool.createSearchTextFieldTest(comboBox, txt_array_search));
        return searchPanel;
    }

    private void applyPermissions(String username, int maCN) {
        btn[0].setVisible(taiKhoanBUS.hasPermission(username, maCN, "add"));
        btn[1].setVisible(taiKhoanBUS.hasPermission(username, maCN, "edit"));
        btn[2].setVisible(taiKhoanBUS.hasPermission(username, maCN, "delete"));
    }

    private void refreshTable() {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);
        try {
            listQuyen = quyenBUS.getAllQuyen();
            for (QuyenDTO quyen : listQuyen) {
                model.addRow(new Object[]{
                    quyen.getMaQuyen(),
                    quyen.getTenQuyen()
                });
                String maQuyen = listQuyen.get(listQuyen.size() - 1).getMaQuyen();
                String numericPart = maQuyen.substring(5);
                count = Integer.parseInt(numericPart) + 1;
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi khi tải dữ liệu từ cơ sở dữ liệu: " + e.getMessage());
        }
    }

    private void add() {
        table.clearSelection();
        update = false;
        delete = false;

        if (!add) {
            add = true;
            tool.clearFields(txt_array);
            tool.clearButtons(btn);
            tool.Editable(txt_array, true);
            txt_array[0].setText(getID());
            txt_array[0].setEditable(false);
            btn[0].setBackground(new Color(202, 220, 252));
            btn[0].setForeground(Color.BLACK);
            btn[3].setBackground(Color.RED);

            for (int i = 1; i < btn.length - 1; i++) {
                btn[i].setVisible(false);
            }
        } else {
            try {
                QuyenDTO quyen = new QuyenDTO();
                quyen.setMaQuyen(txt_array[0].getText().trim());
                quyen.setTenQuyen(txt_array[1].getText().trim());

                if (!checkValidate(quyen)) {
                    return;
                }

                if (quyenBUS.getQuyenById(quyen.getMaQuyen()) != null) {
                    JOptionPane.showMessageDialog(null, "Mã quyền đã tồn tại!");
                    return;
                }

                if (quyenBUS.addQuyen(quyen)) {
                    JOptionPane.showMessageDialog(null, "Thêm quyền thành công!");
                    cancel();
                } else {
                    JOptionPane.showMessageDialog(null, "Thêm quyền thất bại!");
                }
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Lỗi khi thêm quyền: " + e.getMessage());
            }
        }
    }

    private void update() {
        if (add) {
            cancel();
        }
        add = false;
        delete = false;

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(null, "Vui lòng chọn quyền để sửa!");
            return;
        }

        if (!update) {
            update = true;
            tool.clearButtons(btn);
            tool.Editable(txt_array, true);
            txt_array[0].setEditable(false);
            btn[1].setBackground(new Color(202, 220, 252));
            btn[1].setForeground(Color.BLACK);
            btn[3].setBackground(Color.RED);

            for (int i = 0; i < btn.length - 1; i++) {
                if (i != 1) {
                    btn[i].setVisible(false);
                }
            }
        } else {
            try {
                QuyenDTO quyen = new QuyenDTO();
                quyen.setMaQuyen(txt_array[0].getText().trim());
                quyen.setTenQuyen(txt_array[1].getText().trim());

                if (!checkValidate(quyen)) {
                    return;
                }

                if (quyenBUS.updateQuyen(quyen)) {
                    JOptionPane.showMessageDialog(null, "Sửa quyền thành công!");
                    cancel();
                } else {
                    JOptionPane.showMessageDialog(null, "Sửa quyền thất bại!");
                }
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Lỗi khi sửa quyền: " + e.getMessage());
            }
        }
    }

    private void delete() {
        if (add) {
            cancel();
        }
        add = false;
        update = false;

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(null, "Vui lòng chọn quyền để xóa!");
            return;
        }

        if (!delete) {
            delete = true;
            tool.clearButtons(btn);
            tool.Editable(txt_array, false);
            btn[2].setBackground(new Color(202, 220, 252));
            btn[2].setForeground(Color.BLACK);
            btn[3].setBackground(Color.RED);

            for (int i = 0; i < btn.length - 1; i++) {
                if (i != 2) {
                    btn[i].setVisible(false);
                }
            }
        } else {
            try {
                String maQuyen = txt_array[0].getText().trim();
                if (maQuyen.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Vui lòng chọn quyền để xóa!");
                    return;
                }
                if (JOptionPane.showConfirmDialog(null, "Bạn có chắc muốn xóa quyền này?", 
                        "Xóa thông tin quyền", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                    if (quyenBUS.deleteQuyen(maQuyen)) {
                        JOptionPane.showMessageDialog(null, "Xóa quyền thành công!");
                        cancel();
                    } else {
                        JOptionPane.showMessageDialog(null, "Xóa quyền thất bại!");
                    }
                } else {
                    cancel();
                }
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Lỗi khi xóa quyền: " + e.getMessage());
            }
        }
    }

    private void cancel() {
        add = false;
        update = false;
        delete = false;
        tool.clearFields(txt_array);
        tool.Editable(txt_array, false);
        tool.clearButtons(btn);

        for (JButton button : btn) {
            button.setVisible(taiKhoanBUS.hasPermission(username, 9, button.getText().toLowerCase()));
            button.setBackground(new Color(0, 36, 107));
            button.setForeground(Color.WHITE);
        }

        selectedRow = -1;
        lastSelectedRow = -1;
        table.clearSelection();
        refreshTable();
    }

    private boolean checkValidate(QuyenDTO quyen) {
        if (quyen.getMaQuyen().isEmpty() || quyen.getTenQuyen().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Vui lòng điền đầy đủ các trường thông tin!");
            return false;
        }

        if (!quyen.getMaQuyen().matches("QUYEN\\d{3}")) {
            JOptionPane.showMessageDialog(null, "Mã quyền phải có định dạng QUYENxxx (x là số)!");
            return false;
        }

        if (quyen.getTenQuyen().length() > 100) {
            JOptionPane.showMessageDialog(null, "Tên quyền không được vượt quá 100 ký tự!");
            return false;
        }

        for (QuyenDTO q : listQuyen) {
            if (!quyen.getMaQuyen().equals(q.getMaQuyen()) && q.getTenQuyen().equals(quyen.getTenQuyen())) {
                JOptionPane.showMessageDialog(null, "Tên quyền đã tồn tại!");
                return false;
            }
        }

        return true;
    }

    private void timkiem() {
        comboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedOption = (String) comboBox.getSelectedItem();
                filterTable(txt_array_search[0].getText().trim(), selectedOption);
            }
        });
    }

    private void filterTable(String query, String searchType) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);
        try {
            for (QuyenDTO quyen : listQuyen) {
                boolean match = false;
                switch (searchType) {
                    case "Mã":
                        match = quyen.getMaQuyen().toLowerCase().contains(query.toLowerCase());
                        break;
                    case "Tên":
                        match = quyen.getTenQuyen().toLowerCase().contains(query.toLowerCase());
                        break;
                }
                if (match) {
                    model.addRow(new Object[]{
                        quyen.getMaQuyen(),
                        quyen.getTenQuyen()
                    });
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi khi lọc dữ liệu: " + e.getMessage());
        }
    }

    public JPanel getPanel() {
        return this.panel;
    }
}