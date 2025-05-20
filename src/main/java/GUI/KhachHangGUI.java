package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import static java.lang.String.valueOf;
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

import BUS.KhachHangBUS;
import DTO.KhachHangDTO;
import Utils.EventManager;
import Utils.TableRefreshListener;

public class KhachHangGUI implements TableRefreshListener {
    Tool tool = new Tool();
    JPanel panel, panelDetail;
    List<KhachHangDTO> khachHangList;
    JTextField txt_array[] = new JTextField[4];
    
    int width = 1200;
    int width_sideMenu = 151;
    int height = (int)(width * 0.625);
    JButton btn[] = new JButton[4];
    JTable table;
    KhachHangBUS khachHangBUS = new KhachHangBUS();

    private int selectedRow = -1;
    private int lastSelectedRow = -1;
    private boolean update = false;
    private JTextField[] txt_array_search = new JTextField[1];
    private JTextField txt_search;
    private JComboBox<String> comboBox;

    public KhachHangGUI() {
        EventManager.getInstance().registerListener(this);
        txt_search = new JTextField();
        txt_array_search = new JTextField[]{txt_search};
        panel = tool.createPanel(width - width_sideMenu, height, new BorderLayout());
        panel.setBackground(new Color(202, 220, 252));
        panel.add(createKhachHangTable(), BorderLayout.WEST);
        panel.add(createPanelButton(), BorderLayout.CENTER);
        String txt_label[] = {"Mã khách hàng", "Số điện thoại", "Tên", "Điểm tích lũy"};
        panel.add(createDetailPanel(txt_array, txt_label), BorderLayout.SOUTH);
        panel.add(createSearchPanel(), BorderLayout.NORTH);
        timkiem();
    }
    
    private JPanel createKhachHangTable() {
        String column[] = {"Mã khách hàng", "Số điện thoại", "Tên", "Điểm tích lũy"};
        DefaultTableModel model = new DefaultTableModel(column, 0);

        try {
            khachHangList = khachHangBUS.getAllKhachHang();
            for (KhachHangDTO kh : khachHangList) {
                model.addRow(new Object[] {
                    kh.getMaKH(), kh.getSdt(), kh.getHoTen(), kh.getDiem()
                });
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
                if (update) {
                    return;
                }
                tool.clearFields(txt_array);
                tool.clearButtons(btn);
                selectedRow = table.getSelectedRow();

                if (selectedRow == lastSelectedRow && selectedRow >= 0) {
                    table.clearSelection();
                    update = false;
                    for (JTextField txt : txt_array) {
                        txt.setText("");
                        txt.setEditable(true);
                    }
                    lastSelectedRow = -1;
                } else if (selectedRow >= 0) {
                    for (int i = 0; i < txt_array.length; i++) {
                        txt_array[i].setText(valueOf(table.getValueAt(selectedRow, i)));
                        txt_array[i].setEditable(false);
                    }
                    if (update) {
                        tool.Editable(txt_array, true);
                        txt_array[0].setEditable(false); // MaKH always non-editable
                        txt_array[3].setEditable(false); // Diem non-editable during update
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
        String[] btn_txt = {"Sửa", "Nhập Excel", "Xuất Excel", "Hủy"};
        JPanel panelBtn = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelBtn.add(tool.createButtonPanel(btn, btn_txt, new Color(0, 36, 107), Color.WHITE, "y"));

        btn[0].addActionListener(e -> updateKhachHang());
        btn[3].addActionListener(e -> cancel());
        
        return panelBtn;
    }

    private JPanel createDetailPanel(JTextField[] txt_array, String txt_label[]) {
        panelDetail = tool.createDetailPanel(txt_array, txt_label, null, 850, 90, 0.5, 2, false);
        JPanel wrappedPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        wrappedPanel.add(panelDetail);
        wrappedPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));
        return wrappedPanel;
    }

    private JPanel createSearchPanel() {
        String[] searchOptions = {"Mã khách hàng", "Tên khách hàng", "SDT"};
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
            for (KhachHangDTO kh : khachHangList) {
                boolean match = false;
                switch (searchType) {
                    case "Mã khách hàng":
                        match = kh.getMaKH().toLowerCase().contains(query.toLowerCase());
                        break;
                    case "Tên khách hàng":
                        match = kh.getHoTen().toLowerCase().contains(query.toLowerCase());
                        break;
                    case "SDT":
                        match = kh.getSdt().toLowerCase().contains(query.toLowerCase());
                        break;
                }
                if (match) {
                    model.addRow(new Object[]{
                        kh.getMaKH(), kh.getSdt(), kh.getHoTen(), kh.getDiem()
                    });
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi khi lọc dữ liệu: " + e.getMessage());
        }
    }
    
    @Override
    public void refreshTable() {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);
        try {
            khachHangList = khachHangBUS.getAllKhachHang();
            for (KhachHangDTO kh : khachHangList) {
                model.addRow(new Object[] {
                    kh.getMaKH(), kh.getSdt(), kh.getHoTen(), kh.getDiem()
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi khi tải dữ liệu từ cơ sở dữ liệu: " + e.getMessage());
        }
    }

    private void updateKhachHang() {
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(null, "Vui lòng chọn khách hàng để sửa!");
        } else if (!update) {
            tool.clearButtons(btn);
            tool.Editable(txt_array, true);
            txt_array[0].setEditable(false);
            txt_array[3].setEditable(false); 
            btn[0].setBackground(new Color(202, 220, 252));
            btn[0].setForeground(Color.BLACK);
            btn[3].setBackground(Color.RED);

            for (int i = 0; i < btn.length; i++) {
                if (i != 0 && i != 3) {
                    btn[i].setVisible(false);
                } else {
                    btn[i].setVisible(true);
                }
            }
            update = true;
        } else {
            try {
                KhachHangDTO khachHang = new KhachHangDTO();
                khachHang.setMaKH(txt_array[0].getText().trim());
                khachHang.setSdt(txt_array[1].getText().trim());
                khachHang.setHoTen(txt_array[2].getText().trim());
                khachHang.setDiem(Integer.parseInt(txt_array[3].getText().trim()));

                if (khachHang.getMaKH().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Mã khách hàng không được để trống!");
                    return;
                }

                if (!checkValidate(khachHang)) {
                    return;
                }

                if (khachHangBUS.updateKhachHang(khachHang)) {
                    JOptionPane.showMessageDialog(null, "Sửa khách hàng thành công!");
                    EventManager.getInstance().notifyListeners();
                    cancel();
                } else {
                    JOptionPane.showMessageDialog(null, "Sửa khách hàng thất bại!");
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Điểm tích lũy phải là số nguyên!");
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Lỗi khi sửa khách hàng: " + e.getMessage());
            }
        }
    }    

    private void cancel() {
        update = false;
        refreshTable();
        tool.clearButtons(btn);
        tool.clearFields(txt_array);
        tool.Editable(txt_array, false);
        selectedRow = -1;
        lastSelectedRow = -1;
    }

    private boolean checkValidate(KhachHangDTO khachHang) {
        if (khachHang.getSdt().isEmpty() || khachHang.getHoTen().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Vui lòng điền đầy đủ các trường thông tin");
            return false;
        }

        if (!isValidName(khachHang.getHoTen())) {
            JOptionPane.showMessageDialog(null, "Tên khách hàng chỉ được chứa chữ cái, khoảng trắng, dấu gạch ngang hoặc dấu nháy đơn, không chứa số hoặc ký tự đặc biệt");
            return false;
        }

        if (khachHang.getHoTen().length() > 255) {
            JOptionPane.showMessageDialog(null, "Tên khách hàng không được nhiều hơn 255 ký tự");
            return false;
        }

        if (!tool.checkPhoneNumber(khachHang.getSdt())) {
            return false;
        }

        for (KhachHangDTO kh : khachHangList) {
            if (!kh.getMaKH().equals(khachHang.getMaKH()) && kh.getSdt().equals(khachHang.getSdt())) {
                JOptionPane.showMessageDialog(null, "Số điện thoại đã được sử dụng");
                return false;
            }
        }

        if (khachHang.getDiem() < 0) {
            JOptionPane.showMessageDialog(null, "Điểm tích lũy không được âm");
            return false;
        }

        return true;
    }

    private boolean isValidName(String name) {
        return name.matches("^[\\p{L}\\s'-]+$");
    }

    public JPanel getPanel() {
        return this.panel;
    } 
}