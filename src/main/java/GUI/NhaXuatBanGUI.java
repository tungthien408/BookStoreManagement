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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import BUS.NXBBUS;
import DTO.NXBDTO;

public class NhaXuatBanGUI {
    Tool tool = new Tool();
    JPanel panel, panelDetail;
    List <NXBDTO> listNXB;
    JTextField txt_array[] = new JTextField[4];
    int width = 1200;
    int width_sideMenu = 151;
    int height = (int)(width * 0.625);
    JButton btn[] = new JButton[6];
    JTable table;
    NXBBUS nxbBUS = new NXBBUS();

    private int selectedRow = -1;
    private int lastSelectedRow = -1; // Lưu dòng được chọn trước đó
    private boolean update = false;
    private boolean add = false;
    private boolean delete = false;
    int count = 0;
    public String getID() {
        String str = String.valueOf(count);
        while (str.length() != 3)
            str = "0" + str;
        return "NXB" + str;
    }


    public NhaXuatBanGUI() {
        panel = tool.createPanel(width - width_sideMenu, height, new BorderLayout());
        panel.setBackground(new Color(202, 220, 252));
        panel.add(createNXBTable(), BorderLayout.WEST);
        panel.add(createPanelButton(), BorderLayout.CENTER);

        // Chi tiết sản phẩm
        String txt_label[] = {"Mã NXB", "Tên NXB", "Địa chỉ", "Số điện thoại"};
        panel.add(createDetailPanel(txt_array, txt_label), BorderLayout.SOUTH);
      
        // Tạo thanh tìm kiếm 
        panel.add(createSearchPanel(), BorderLayout.NORTH);
    }

    private JPanel createNXBTable() {
        String column[] = {"Mã NXB", "Tên NXB", "Địa chỉ", "Số điện thoại"};
        DefaultTableModel model = new DefaultTableModel(column, 0);

        try {
            listNXB = nxbBUS.getAllNhaXuatBan();
            for (NXBDTO nxb : listNXB) {
                model.addRow(new Object[] {
                    nxb.getMaNXB(),
                    nxb.getTenNXB(),
                    nxb.getDiaChi(),
                    nxb.getSdt()
                });
                String maNXB = listNXB.get(listNXB.size() - 1).getMaNXB();
                String numericPart = maNXB.substring(2);
                count = Integer.parseInt(numericPart) + 1;
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi khi tải dữ liệu từ cơ sở dữ liệu: " + e.getMessage());
        }

        // Bảng
        JTable table = tool.createTable(model, column);
        table.setDefaultEditor(Object.class, null);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(850, 540));

        // Add MouseListener for the table
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
                }
                else if (selectedRow >= 0) {

                    for (int i = 0; i < txt_array.length; i++) {
                        txt_array[i].setText((String)table.getValueAt(selectedRow, i));
                        txt_array[i].setEditable(false);
                    }

                    if (update) {
                        tool.Editable(txt_array, true);
                        txt_array[0].setEditable(false); //fuck tui làm xong cái nhập rồi đó 
                    }
                    if (delete) {
                        tool.Editable(txt_array, false);
                    }

                    lastSelectedRow = selectedRow;
                }
            }
        });

        // Tạo khoảng cách xung quanh bảng
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 40, 30, 10)); // Top, Left, Bottom, Right
        
        // Tạo panel FlowLayout để có thể tùy chỉnh kích cỡ bảng
        JPanel panelTable = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelTable.add(scrollPane);
        return panelTable;
    }
    
    private JPanel createPanelButton() {
        String [] btn_txt = {"Thêm", "Sửa", "Xóa", "Nhập Excel", "Xuất Excel", "Hủy"};
        JPanel panelBtn = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelBtn.add(tool.createButtonPanel(btn, btn_txt, new Color(0, 36, 107), Color.WHITE,"y"));
        // panelBtn.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

        // TODO: assign event for btn
        btn[0].addActionListener(e -> add());
        btn[1].addActionListener(e -> update());
        btn[2].addActionListener(e -> delete());
        btn[5].addActionListener(e -> cancel());
        
        return panelBtn;
    }

    private JPanel createDetailPanel(JTextField txt_array[], String txt_label[]) {
        panelDetail = tool.createDetailPanel(txt_array, txt_label, null,850,90, 0.5, 2, false);

        JPanel wrappedPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        wrappedPanel.add(panelDetail);
        wrappedPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));
        return wrappedPanel;

    }

    private JPanel createSearchPanel() {
        String [] searchOptions = {"Mã NXB", "Tên NXB"};
        JPanel panelSearch = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelSearch.add(Box.createHorizontalStrut(25));
        panelSearch.add(tool.createSearchTextField(0, 0,searchOptions));
        return panelSearch;
    }

    // Phương thức làm mới bảng
    private void refreshTable() {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0); // Xóa dữ liệu cũ
        try {
            listNXB = nxbBUS.getAllNhaXuatBan();
            for (NXBDTO nxb : listNXB) {
                model.addRow(new Object[] {
                    nxb.getMaNXB(),
                    nxb.getTenNXB(),
                    nxb.getDiaChi(),
                    nxb.getSdt()
                });
                String maNXB = listNXB.get(listNXB.size() - 1).getMaNXB();
                String numericPart = maNXB.substring(2);
                count = Integer.parseInt(numericPart) + 1;
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi khi tải dữ liệu từ cơ sở dữ liệu: " + e.getMessage());
        }
    }

    // Phương thức thêm nhà xuất bản
    private void add() {
        table.clearSelection();
        update=false;
        delete=false;
        if(add==false){
            add=true;
            tool.clearFields(txt_array);
            tool.clearButtons(btn);
            tool.Editable(txt_array,true);
            txt_array[0].setText(getID());
            btn[0].setBackground(new Color(202, 220, 252));
            btn[0].setForeground(Color.BLACK);
            btn[5].setBackground(Color.RED);

            for (int i = 0, length = btn.length - 1; i < length; i++) {
                if (i != 0) {
                    btn[i].setVisible(false);
                }
            }
            txt_array[0].setEditable(false);
        }
        else {
            try {
                NXBDTO nxb = new NXBDTO();
                nxb.setMaNXB(txt_array[0].getText().trim());
                nxb.setTenNXB(txt_array[1].getText().trim());
                nxb.setDiaChi(txt_array[2].getText().trim());
                nxb.setSdt(txt_array[3].getText().trim());
                nxb.setTrangThaiXoa(0);

                if (!checkValidate(nxb)) {
                    return;
                }

                if (nxbBUS.addNhaXuatBan(nxb)) {
                    JOptionPane.showMessageDialog(null, "Thêm nhà xuất bản thành công!");
                    cancel();
                } else {
                    JOptionPane.showMessageDialog(null, "Thêm nhà xuất bản thất bại!");
                }
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Lỗi khi thêm nhà xuất bản: " + e.getMessage());
            }
        }
    }

    // Phương thức sửa nhà xuất bản
    private void update() {
        if(add==true){
            tool.clearFields(txt_array);
        }
        add=false;
        delete=false;
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(null, "Vui lòng chọn nhà xuất bản để sửa!");
        }
        else if (update==false){
            update=true;
            tool.clearButtons(btn);
            tool.Editable(txt_array,true);
            tool.clearButtons(btn);

            btn[1].setBackground(new Color(202, 220, 252));
            btn[1].setForeground(Color.BLACK);
            btn[5].setBackground(Color.RED);

            for (int i = 0, length = btn.length - 1; i < length; i++) {
                if (i != 1) {
                    btn[i].setVisible(false);
                }
            }

            txt_array[0].setEditable(false);
        } else {
            try {
                NXBDTO nxb = new NXBDTO();
                nxb.setMaNXB(txt_array[0].getText().trim());
                nxb.setTenNXB(txt_array[1].getText().trim());
                nxb.setDiaChi(txt_array[2].getText().trim());
                nxb.setSdt(txt_array[3].getText().trim());

                if (nxb.getMaNXB().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Vui lòng chọn nhà xuất bản để sửa!");
                    return;
                }

                if (!checkValidate(nxb)) {
                    return;
                }

                if (nxbBUS.updateNhaXuatBan(nxb)) {
                    JOptionPane.showMessageDialog(null, "Sửa nhà xuất bản thành công!");
                    cancel();
                } else {
                    JOptionPane.showMessageDialog(null, "Sửa nhà xuất bản thất bại!");
                }
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Lỗi khi sửa nhà xuất bản: " + e.getMessage());
            }
        }
    }

    // Phương thức xóa nhà xuất bản
    private void delete() {
        if(add==true){
            tool.clearFields(txt_array);
        }
        add=false;
        update=false;
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(null, "Vui lòng chọn nhà xuất bản để sửa!");
        }
        // else if (delete==false){
        //     tool.clearButtons(btn);
        //     tool.Editable(txt_array,false);
        //     btn[2].setBackground(new Color(202, 220, 252));
        //     btn[2].setForeground(Color.BLACK);
        //     btn[5].setBackground(Color.RED);

        //     // for (int i = 0, length = btn.length - 1; i < length; i++) {
        //     //     if (i != 0) {
        //     //         btn[i].setVisible(false);
        //     //     }
        //     // }

            // delete=true;
        // } 
        else {
            try {
                String maTG = txt_array[0].getText().trim();
                if (maTG.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Vui lòng chọn nhà xuất bản để xóa!");
                    return;
                }
                if (JOptionPane.showConfirmDialog(null, "Bạn có chắc muốn xóa nhà xuất bản này?", "Xóa thông tin nhà xuất bản", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                    if (nxbBUS.deleteNhaXuatBan(maTG)) {
                        JOptionPane.showMessageDialog(null, "Xóa nhà xuất bản thành công!");
                        cancel();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Lỗi khi xóa nhà xuất bản: " + e.getMessage());
            }
        }
    }

    private void cancel() {
        add = false;
        update = false;
        delete = false;
        refreshTable();
        tool.clearButtons(btn);
        tool.clearFields(txt_array);
        tool.Editable(txt_array,false);
        selectedRow = -1;
        lastSelectedRow = -1;
    }

    private boolean checkValidate(NXBDTO nxb) {
        // Kiểm tra dữ liệu đầu vào
        if (nxb.getMaNXB().isEmpty() || nxb.getTenNXB().isEmpty() || nxb.getDiaChi().isEmpty() || nxb.getSdt().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Vui lòng điền đầy đủ các trường thông tin");
            return false;
        }

        if (nxb.getTenNXB().length() > 100) {
            JOptionPane.showMessageDialog(null, "Tên nhà xuất bản không được nhiều hơn 100 ký tự");
            return false;
        }

        if (nxb.getDiaChi().length() > 255) {
            JOptionPane.showMessageDialog(null, "Địa chỉ nhà xuất bản không được nhiều hơn 255 ký tự");
            return false;
        }

        if (!tool.checkPhoneNumber(nxb.getSdt())) {
            return false;
        }

        for (NXBDTO nxban : listNXB) {
            if (!nxb.getMaNXB().equals(nxban.getMaNXB()) && nxban.getSdt().equals(nxb.getSdt())) {
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
