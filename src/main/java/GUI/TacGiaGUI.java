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
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;

import BUS.TacGiaBUS;
import DTO.TacGiaDTO;

public class TacGiaGUI {
    Tool tool = new Tool();
    JPanel panel, panelDetail;
    List<TacGiaDTO> tacGiaList;
    JTextField[] txt_array = new JTextField[4];
    // txt_authorId, txt_name, txt_address, txt_phone;
    int width = 1200;
    int width_sideMenu = 151;
    int height = (int) (width * 0.625);
    JButton[] buttons = new JButton[6];
    JTable table;
    TacGiaBUS tacGiaBUS = new TacGiaBUS(); // Khởi tạo TacGiaBUS

    private int selectedRow = -1;
    private int lastSelectedRow = -1; // Lưu dòng được chọn trước đó
    private boolean update = false;
    private boolean add = false;
    private boolean delete = false;
    private JTextField[] txt_array_search = new JTextField[1];
    private JTextField txt_search;
    private JComboBox<String> comboBox;

    int count = 0;
    public String getID() {
        String str = String.valueOf(count);
        while (str.length() != 3)
            str = "0" + str;
        return "TG" + str;
    }

    public TacGiaGUI() {
        txt_search = new JTextField();
        txt_array_search = new JTextField[]{txt_search};
        panel = tool.createPanel(width - width_sideMenu, height, new BorderLayout());
        panel.setBackground(new Color(202, 220, 252));
        panel.add(createTacGiaTable(), BorderLayout.WEST);

        // Panel chứa button
        panel.add(createPanelButton(), BorderLayout.CENTER);
        String txt_label[] = {"Mã tác giả", "Tên tác giả", "Địa chỉ", "Số điện thoại"};
        panel.add(createPanelDetail(txt_array, txt_label), BorderLayout.SOUTH);

        // Tạo thanh tìm kiếm
        panel.add(createSearchPanel(), BorderLayout.NORTH);
        timkiem();
    }

    private JPanel createTacGiaTable() {
        String column[] = {"Mã tác giả", "Tên tác giả", "Địa chỉ", "Số điện thoại"};
        DefaultTableModel model = new DefaultTableModel(column, 0);

        // Lấy dữ liệu từ cơ sở dữ liệu
        try {
            tacGiaList = tacGiaBUS.getAllTacGia();
            for (TacGiaDTO tacGia : tacGiaList) {
                model.addRow(new Object[]{
                    tacGia.getMaTG(),
                    tacGia.getTenTG(),
                    tacGia.getDiaChi(),
                    tacGia.getSdt()
                    
                });
                String maTG = tacGiaList.get(tacGiaList.size() - 1).getMaTG();
                String numericPart = maTG.substring(2);
                count = Integer.parseInt(numericPart)+1;
            }


        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi khi tải dữ liệu từ cơ sở dữ liệu: " + e.getMessage());
        }

        // Bảng
        table = tool.createTable(model, column);
        table.setDefaultEditor(Object.class, null); // Không cho chỉnh sửa trực tiếp trên bảng
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(850, 540));
        
        // Thêm MouseListener cho bảng
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (add || update) {
                    return;
                }

                tool.clearFields(txt_array);
                tool.clearButtons(buttons);
                add=false;
                
                selectedRow = table.getSelectedRow();

                // Nếu click vào cùng một dòng đã chọn trước đó
                if (selectedRow == lastSelectedRow && selectedRow >= 0) {
                    // Hủy chọn dòng
                    table.clearSelection();
                    update=false;
                    delete=false;


                    // Reset dữ liệu trong các ô nhập
                    for (JTextField txt : txt_array) {
                        txt.setText("");
                        txt.setEditable(true);
                    }

                    lastSelectedRow = -1; // Reset chỉ số dòng
                } else if (selectedRow >= 0) {
                    // Click vào dòng mới
                    for (int i = 0; i < txt_array.length; i++) {
                        txt_array[i].setText((String) table.getValueAt(selectedRow, i));
                        txt_array[i].setEditable(false);
                    }
                    if(update==true){
                        tool.Editable(txt_array, true);
                        txt_array[0].setEditable(false);
                    }
                    if(delete==true){
                        tool.Editable(txt_array, false);
                    }

                    lastSelectedRow = selectedRow;
                }
                
            }
        });

        // Tạo khoảng cách xung quanh bảng
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 40, 30, 10));

        // Tạo panel FlowLayout để có thể tùy chỉnh kích cỡ bảng
        JPanel panelTable = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelTable.add(scrollPane);
        return panelTable;
    }

    private JPanel createPanelButton() {
        // Khai báo và khởi tạo mảng JButton[]
        String [] txt_btn = {"Thêm", "Sửa", "Xóa", "Nhập Excel", "Xuất Excel" ,"Hủy"};
        JPanel panelBtn = new JPanel(new FlowLayout(FlowLayout.CENTER));
        // panelBtn.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        panelBtn.add(tool.createButtonPanel(buttons, txt_btn, new Color(0, 36, 107), Color.WHITE, "y"));

        // Gắn sự kiện cho các nút
        buttons[0].addActionListener(e -> addTacGia());
        buttons[1].addActionListener(e -> updateTacGia());
        buttons[2].addActionListener(e -> deleteTacGia());
        buttons[5].addActionListener(e -> cancel());

        return panelBtn;
    }

    private JPanel createPanelDetail(JTextField[] txt_array, String[] txt_label) {
        // Sử dụng createDetailPanel từ Tool
        panelDetail = tool.createDetailPanel(txt_array, txt_label, null, 850,90, 0.5, 2, false);

        JPanel wrappedPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        wrappedPanel.add(panelDetail);
        wrappedPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));
        return wrappedPanel;    }

    private JPanel createSearchPanel() {
        String[] searchOptions = {"Mã tác giả", "Tên tác giả", "SDT"};
        comboBox = new JComboBox<>(searchOptions);
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchPanel.add(Box.createHorizontalStrut(33));
        searchPanel.add(tool.createSearchTextFieldTest(comboBox, txt_array_search));
        return searchPanel;
    }
    private void timkiem() {
        // Add real-time search with DocumentListener
        txt_array_search[0].getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                filterTable(txt_array_search[0].getText(), (String) comboBox.getSelectedItem());
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                filterTable(txt_array_search[0].getText(), (String) comboBox.getSelectedItem());
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                filterTable(txt_array_search[0].getText(), (String) comboBox.getSelectedItem());
            }
        });

        comboBox.addActionListener(
                e -> filterTable(txt_array_search[0].getText(), (String) comboBox.getSelectedItem()));
    }

        private void filterTable(String query, String searchType) {
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            model.setRowCount(0); // Xóa dữ liệu cũ
        try {
            for (TacGiaDTO tacGia : tacGiaList) {
                boolean match = false;
                switch (searchType) {
                    case "Mã tác giả":
                        match = tacGia.getMaTG().toLowerCase().contains(query.toLowerCase());
                        break;
                    case "Tên tác giả":
                        match = tacGia.getTenTG().toLowerCase().contains(query.toLowerCase());
                        break;
                    case "SDT":
                        match = tacGia.getSdt().toLowerCase().contains(query.toLowerCase());
                        break;
                }
                if (match) {
                    model.addRow(new Object[]{
                        tacGia.getMaTG(),
                        tacGia.getTenTG(),
                        tacGia.getDiaChi(),
                        tacGia.getSdt()
                    });
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi khi lọc dữ liệu: " + e.getMessage());
        }
    }


    // Phương thức làm mới bảng
    private void refreshTable() {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0); // Xóa dữ liệu cũ
        try {
            tacGiaList = tacGiaBUS.getAllTacGia();
            for (TacGiaDTO tacGia : tacGiaList) {
                model.addRow(new Object[]{
                    tacGia.getMaTG(),
                    tacGia.getTenTG(),
                    tacGia.getDiaChi(),
                    tacGia.getSdt()
                });
                String maTG = tacGiaList.get(tacGiaList.size() - 1).getMaTG();
                String numericPart = maTG.substring(2);
                count = Integer.parseInt(numericPart)+1;
                
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi khi làm mới bảng: " + e.getMessage());
        }
    }

    // Phương thức thêm tác giả
    private void addTacGia() {
        table.clearSelection();
        update=false;
        delete=false;
        if(add==false){
            add=true;
            tool.clearFields(txt_array);
            tool.clearButtons(buttons);
            tool.Editable(txt_array,true);
            txt_array[0].setText(getID());
            buttons[0].setBackground(new Color(202, 220, 252));
            buttons[0].setForeground(Color.BLACK);
            buttons[5].setBackground(Color.RED);

            for (int i = 0, length = buttons.length - 1; i < length; i++) {
                if (i != 0) {
                    buttons[i].setVisible(false);
                }
            }
            txt_array[0].setEditable(false);
        }
        else {
            try {
                TacGiaDTO tacGia = new TacGiaDTO();
                tacGia.setMaTG(txt_array[0].getText().trim());
                tacGia.setTenTG(txt_array[1].getText().trim());
                tacGia.setDiaChi(txt_array[2].getText().trim());
                tacGia.setSdt(txt_array[3].getText().trim());

                if (!checkValidate(tacGia)) {
                    return;
                }

                if (tacGiaBUS.addTacGia(tacGia)) {
                    JOptionPane.showMessageDialog(null, "Thêm tác giả thành công!");
                    cancel();
                } else {
                    JOptionPane.showMessageDialog(null, "Thêm tác giả thất bại!");
                }
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Lỗi khi thêm tác giả: " + e.getMessage());
            }
        }
    }

    // Phương thức sửa tác giả
    private void updateTacGia() {
        if(add==true){
            tool.clearFields(txt_array);
        }
        add=false;
        delete=false;
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(null, "Vui lòng chọn tác giả để sửa!");
        }
        else if (update==false){
            update=true;
            tool.clearButtons(buttons);
            tool.Editable(txt_array,true);
            tool.clearButtons(buttons);

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
                TacGiaDTO tacGia = new TacGiaDTO();
                tacGia.setMaTG(txt_array[0].getText().trim());
                tacGia.setTenTG(txt_array[1].getText().trim());
                tacGia.setDiaChi(txt_array[2].getText().trim());
                tacGia.setSdt(txt_array[3].getText().trim());

                if (tacGia.getMaTG().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Vui lòng chọn tác giả để sửa!");
                    return;
                }

                if (!checkValidate(tacGia)) {
                    return;
                }

                if (tacGiaBUS.updateTacGia(tacGia)) {
                    JOptionPane.showMessageDialog(null, "Sửa tác giả thành công!");
                    cancel();
                } else {
                    JOptionPane.showMessageDialog(null, "Sửa tác giả thất bại!");
                }
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Lỗi khi sửa tác giả: " + e.getMessage());
            }
        }
    }

    // Phương thức xóa tác giả
    private void deleteTacGia() {
        if(add==true){
            tool.clearFields(txt_array);
        }
        add=false;
        update=false;
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(null, "Vui lòng chọn tác giả để sửa!");
        }
        // else if (delete==false){
        //     tool.clearButtons(buttons);
        //     tool.Editable(txt_array,false);
        //     buttons[2].setBackground(new Color(202, 220, 252));
        //     buttons[2].setForeground(Color.BLACK);
        //     buttons[5].setBackground(Color.RED);

        //     // for (int i = 0, length = buttons.length - 1; i < length; i++) {
        //     //     if (i != 0) {
        //     //         buttons[i].setVisible(false);
        //     //     }
        //     // }

            // delete=true;
        // } 
        else {
            try {
                String maTG = txt_array[0].getText().trim();
                if (maTG.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Vui lòng chọn tác giả để xóa!");
                    return;
                }
                if (JOptionPane.showConfirmDialog(null, "Bạn có chắc muốn xóa tác giả này?", "Xóa thông tin tác giả", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                    if (tacGiaBUS.deleteTacGia(maTG)) {
                        JOptionPane.showMessageDialog(null, "Xóa tác giả thành công!");
                        cancel();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Lỗi khi xóa tác giả: " + e.getMessage());
            }
        }
    }

    private void cancel() {
        add = false;
        update = false;
        delete = false;
        refreshTable();
        tool.clearButtons(buttons);
        tool.clearFields(txt_array);
        tool.Editable(txt_array,false);
        selectedRow = -1;
        lastSelectedRow = -1;
    }

    private boolean checkValidate(TacGiaDTO tacGia) {
        // Kiểm tra dữ liệu đầu vào
        if (tacGia.getMaTG().isEmpty() || tacGia.getTenTG().isEmpty() || tacGia.getDiaChi().isEmpty() || tacGia.getSdt().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Vui lòng điền đầy đủ các trường thông tin");
            return false;
        }

        if (tacGia.getTenTG().length() > 100) {
            JOptionPane.showMessageDialog(null, "Tên tác giả không được nhiều hơn 100 ký tự");
            return false;
        }

        if (tacGia.getDiaChi().length() > 255) {
            JOptionPane.showMessageDialog(null, "Địa chỉ tác giả không được nhiều hơn 255 ký tự");
            return false;
        }

        if (!tool.checkPhoneNumber(tacGia.getSdt())) {
            return false;
        }

        for (TacGiaDTO tg : tacGiaList) {
            if (!tg.getMaTG().equals(tacGia.getMaTG()) && tg.getSdt().equals(tacGia.getSdt())) {
                JOptionPane.showMessageDialog(null, "Số điện thoại đã được sử dụng");
                return false;                            
            }
        }
        return true;
    }

    public JPanel getPanel() {
        return this.panel;
    }
} 
