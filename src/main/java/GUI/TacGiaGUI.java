package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
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

import BUS.TacGiaBUS;
import DTO.TacGiaDTO;

public class TacGiaGUI {
    Tool tool = new Tool();
    JPanel panel, panelDetail;
    JTextField[] txt_array = new JTextField[4];
    // txt_authorId, txt_name, txt_address, txt_phone;
    int width = 1200;
    int width_sideMenu = 151;
    int height = (int) (width * 0.625);
    JButton[] buttons = new JButton[5];
    JTable table;
    TacGiaBUS tacGiaBUS = new TacGiaBUS(); // Khởi tạo TacGiaBUS

    private int lastSelectedRow = -1; // Lưu dòng được chọn trước đó
    private boolean update = false;
    private boolean add = false;
    int count = 0;
    public String getID() {
        count++;
        String str = String.valueOf(count);
        while (str.length() != 3)
            str = "0" + str;
        return "TG" + str;
    }

    public TacGiaGUI() {
        panel = tool.createPanel(width - width_sideMenu, height, new BorderLayout());
        panel.setBackground(new Color(202, 220, 252));
        panel.add(createTacGiaTable(), BorderLayout.WEST);

        // Panel chứa button
        panel.add(createPanelButton(), BorderLayout.CENTER);

        // Chi tiết sản phẩm
        txt_array[0] = new JTextField(20);
        txt_array[1] = new JTextField(20);
        txt_array[2] = new JTextField(20);
        txt_array[3] = new JTextField(20);
        // JTextField txt_array[] = {txt_authorId, txt_name, txt_address, txt_phone};
        String txt_label[] = {"Mã tác giả", "Tên tác giả", "Địa chỉ", "Số điện thoại"};
        panel.add(createPanelDetail(txt_array, txt_label), BorderLayout.SOUTH);

        // Tạo thanh tìm kiếm
        panel.add(createPanelSearch(), BorderLayout.NORTH);
    }

    private JPanel createTacGiaTable() {
        String column[] = {"Mã tác giả", "Tên tác giả", "Địa chỉ", "Số điện thoại"};
        DefaultTableModel model = new DefaultTableModel(column, 0);

        // Lấy dữ liệu từ cơ sở dữ liệu
        try {
            List<TacGiaDTO> tacGiaList = tacGiaBUS.getAllTacGia();
            for (TacGiaDTO tacGia : tacGiaList) {
                model.addRow(new Object[]{
                    tacGia.getMaTG(),
                    tacGia.getTenTG(),
                    tacGia.getDiaChi(),
                    tacGia.getSdt()
                    
                });
                String maTG = tacGiaList.get(tacGiaList.size() - 1).getMaTG();
                String numericPart = maTG.substring(2);
                count = Integer.parseInt(numericPart);
            }


        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi khi tải dữ liệu từ cơ sở dữ liệu: " + e.getMessage());
        }

        // Bảng
        table = new JTable(model);
        table.setDefaultEditor(Object.class, null); // Không cho chỉnh sửa trực tiếp trên bảng
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(850, 340));

        // Thêm MouseListener cho bảng
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int selectedRow = table.getSelectedRow();

                // Nếu click vào cùng một dòng đã chọn trước đó
                if (selectedRow == lastSelectedRow && selectedRow >= 0) {
                    // Hủy chọn dòng
                    table.clearSelection();

                    // Reset dữ liệu trong các ô nhập
                    for (JTextField txt : txt_array) {
                        txt.setText("");
                        txt.setEditable(true);
                    }

                    lastSelectedRow = -1; // Reset chỉ số dòng
                } else if (selectedRow >= 0) {
                    // Click vào dòng mới
                    txt_array[0].setText((String) table.getValueAt(selectedRow, 0));
                    txt_array[1].setText((String) table.getValueAt(selectedRow, 1));
                    txt_array[2].setText((String) table.getValueAt(selectedRow, 2));
                    txt_array[3].setText((String) table.getValueAt(selectedRow, 3));

                    for (JTextField txt : txt_array) {
                        txt.setEditable(false);
                    }

                    lastSelectedRow = selectedRow;
                }
                
            }
        });

        // Tạo khoảng cách xung quanh bảng
        scrollPane.setBorder(BorderFactory.createEmptyBorder(50, 40, 10, 10));

        // Tạo panel FlowLayout để có thể tùy chỉnh kích cỡ bảng
        JPanel panelTable = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelTable.add(scrollPane);
        return panelTable;
    }

    private JPanel createPanelButton() {
        // Khai báo và khởi tạo mảng JButton[]
        buttons[0] = new JButton("Thêm");
        buttons[1] = new JButton("Sửa");
        buttons[2] = new JButton("Xóa");
        buttons[3] = new JButton("Nhập Excel");
        buttons[4] = new JButton("Xuất Excel");

        // Gắn sự kiện cho các nút
        buttons[0].addActionListener(e -> addTacGia());
        buttons[1].addActionListener(e -> updateTacGia());
        buttons[2].addActionListener(e -> deleteTacGia());

        JPanel panelBtn = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelBtn.add(tool.createButtonPanel1(buttons, new Color(0, 36, 107), Color.WHITE, "y"));
        panelBtn.setBorder(BorderFactory.createEmptyBorder(50, 0, 0, 0));
        return panelBtn;
    }

    private JPanel createPanelDetail(JTextField[] txt_array, String[] txt_label) {
        // Sử dụng createDetailPanel từ Tool
        panelDetail = tool.createDetailPanel(txt_array, txt_label, null,850,300, 0.5, 4, false);

        JPanel wrappedPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        wrappedPanel.add(panelDetail);
        wrappedPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 70, 0));
        return wrappedPanel;
    }

    private JPanel createPanelSearch() {
        String[] searchOptions = {"Mã tác giả", "Tên tác giả", "Số điện thoại"};
        JPanel panelSearch = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelSearch.add(Box.createHorizontalStrut(25));
        panelSearch.add(tool.createSearchTextField(0, 0, searchOptions));
        return panelSearch;
    }

    // Phương thức làm mới bảng
    private void refreshTable() {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0); // Xóa dữ liệu cũ
        try {
            List<TacGiaDTO> tacGiaList = tacGiaBUS.getAllTacGia();
            for (TacGiaDTO tacGia : tacGiaList) {
                model.addRow(new Object[]{
                    tacGia.getMaTG(),
                    tacGia.getTenTG(),
                    tacGia.getDiaChi(),
                    tacGia.getSdt()
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi khi làm mới bảng: " + e.getMessage());
        }
    }

    // Phương thức thêm tác giả
    private void addTacGia() {
        if(add==false){
            txt_array[0].setText(getID());
            txt_array[1].setText("");
            txt_array[2].setText("");
            txt_array[3].setText("");

            add=true;
        }
        else{
            try {
                TacGiaDTO tacGia = new TacGiaDTO();
                txt_array[0].setEditable(false);
                txt_array[1].setEditable(true);
                txt_array[2].setEditable(true);
                txt_array[3].setEditable(true);
                tacGia.setMaTG(txt_array[0].getText().trim());
                tacGia.setTenTG(txt_array[1].getText().trim());
                tacGia.setDiaChi(txt_array[2].getText().trim());
                tacGia.setSdt(txt_array[3].getText().trim());
                // System.out.println(txt_phone+"..."+txt_name+"..."+txt_address+"..."+txt_phone);

                // Kiểm tra dữ liệu đầu vào
                if (tacGia.getMaTG().isEmpty() || tacGia.getTenTG().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Mã và tên tác giả không được để trống!");
                    return;
                }

                if (tacGiaBUS.addTacGia(tacGia)) {
                    JOptionPane.showMessageDialog(null, "Thêm tác giả thành công!");
                    refreshTable();
                    clearFields();
                } else {
                    JOptionPane.showMessageDialog(null, "Thêm tác giả thất bại!");
                }
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Lỗi khi thêm tác giả: " + e.getMessage());
            }
        }
        update = false;
    }

    // Phương thức sửa tác giả
    private void updateTacGia() {
        if(update==false){
            txt_array[0].setEditable(false);
            txt_array[1].setEditable(true);
            txt_array[2].setEditable(true);
            txt_array[3].setEditable(true);
            update=true;
        }
        else{
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

            if (tacGiaBUS.updateTacGia(tacGia)) {
                JOptionPane.showMessageDialog(null, "Sửa tác giả thành công!");
                refreshTable();
            } else {
                JOptionPane.showMessageDialog(null, "Sửa tác giả thất bại!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi khi sửa tác giả: " + e.getMessage());
        }
        update=false;
    }
    add = false;
    }

    // Phương thức xóa tác giả
    private void deleteTacGia() {
        try {
            String maTG = txt_array[0].getText().trim();
            if (maTG.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Vui lòng chọn tác giả để xóa!");
                return;
            }

            int confirm = JOptionPane.showConfirmDialog(null, "Bạn có chắc muốn xóa tác giả này?", "Xác nhận", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                if (tacGiaBUS.deleteTacGia(maTG)) {
                    JOptionPane.showMessageDialog(null, "Xóa tác giả thành công!");
                    refreshTable();
                    clearFields();
                } else {
                    JOptionPane.showMessageDialog(null, "Xóa tác giả thất bại!");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi khi xóa tác giả: " + e.getMessage());
        }
    }

    // Phương thức xóa các trường nhập liệu
    private void clearFields() {
        txt_array[0].setText("");
        txt_array[1].setText("");
        txt_array[2].setText("");
        txt_array[3].setText("");
    }

    public JPanel getPanel() {
        return this.panel;
    }
} 