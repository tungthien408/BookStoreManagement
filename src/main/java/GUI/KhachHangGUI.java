package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.util.List;
// import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import java.awt.event.MouseEvent;
import static java.lang.String.valueOf;

import BUS.KhachHangBUS;
import DTO.KhachHangDTO;

public class KhachHangGUI {
    Tool tool = new Tool();
    JPanel panel, panelDetail;
    List<KhachHangDTO> khachHangList;
    JTextField txt_array[] = new JTextField[4];
    
    int width = 1200;
    int width_sideMenu = 151;
    int height = (int)(width * 0.625);
    JButton btn [] = new JButton[4];
    JTable table;
    KhachHangBUS khachHangBUS = new KhachHangBUS();

    private int selectedRow = -1;
    private int lastSelectedRow = -1; // Lưu dòng được chọn trước đó
    private boolean update = false;


    public KhachHangGUI() {
        panel = tool.createPanel(width - width_sideMenu, height, new BorderLayout());
        panel.setBackground(new Color(202, 220, 252));
        panel.add(createKhachHangTable(), BorderLayout.WEST);

        // Panel chứa button
        panel.add(createPanelButton(), BorderLayout.CENTER);

        // Chi tiết sản phẩm
        String txt_label[] = {"Mã khách hàng", "Số điện thoại", "Tên", "Điểm tích lũy"};
        panel.add(createDetailPanel(txt_array, txt_label), BorderLayout.SOUTH);
      
        // Tạo thanh tìm kiếm 
        panel.add(createSearchPanel(), BorderLayout.NORTH);
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
        // Bảng
        table = tool.createTable(model, column);        table.setDefaultEditor(Object.class, null); // Không cho chỉnh sửa trực tiếp trên bảng
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(850, 540));

        // Thêm MouseListener cho bảng
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (update) {
                    return;
                }
                tool.clearFields(txt_array);
                tool.clearButtons(btn);
                selectedRow = table.getSelectedRow();

                // Nếu click vào cùng một dòng đã chọn trước đó
                if (selectedRow == lastSelectedRow && selectedRow >= 0) {
                    // Hủy chọn dòng
                    table.clearSelection();
                    update=false;

                    // Reset dữ liệu trong các ô nhập
                    for (JTextField txt : txt_array) {
                        txt.setText("");
                        txt.setEditable(true);
                    }

                    lastSelectedRow = -1; // Reset chỉ số dòng
                } else if (selectedRow >= 0) {
                    // Click vào dòng mới
                    for (int i = 0; i < txt_array.length; i++) {
                        txt_array[i].setText(valueOf(table.getValueAt(selectedRow, i)));
                        txt_array[i].setEditable(false);
                    }
                    if(update==true){
                        tool.Editable(txt_array, true);
                        txt_array[0].setEditable(false);
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
        String [] btn_txt = {"Sửa", "Nhập Excel", "Xuất Excel", "Hủy"};
        JPanel panelBtn = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelBtn.add(tool.createButtonPanel(btn, btn_txt, new Color(0, 36, 107), Color.WHITE,"y"));
        // panelBtn.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

        // Gắn sự kiện cho các nút
        btn[0].addActionListener(e -> updateKhachHang());
        btn[3].addActionListener(e -> cancel());
        
        return panelBtn;
    }

    private JPanel createDetailPanel(JTextField[] txt_array, String txt_label[]) {
        panelDetail = tool.createDetailPanel(txt_array, txt_label, null, 850,90, 0.5, 2, false);

        JPanel wrappedPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        wrappedPanel.add(panelDetail);
        wrappedPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));
        return wrappedPanel;
    }

    private JPanel createSearchPanel() {
        String [] searchOptions = {"Mã khách hàng", "SĐT", "Tên khách hàng"};
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

    // Phương thức sửa khách hàng
    private void updateKhachHang() {
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(null, "Vui lòng chọn khách hàng để sửa!");
            }
            else if (update==false){
                tool.clearButtons(btn);
                tool.Editable(txt_array,true);
                tool.clearButtons(btn);
    
                btn[0].setBackground(new Color(202, 220, 252));
                btn[0].setForeground(Color.BLACK);
                btn[3].setBackground(Color.RED);
    
                for (int i = 0, length = btn.length - 1; i < length; i++) {
                    if (i != 0) {
                        btn[i].setVisible(false);
                    }
                }
                update=true;
            } else {
                try {
                    KhachHangDTO khachHang = new KhachHangDTO();
                    khachHang.setSdt(txt_array[0].getText().trim());
                    khachHang.setHoTen(txt_array[1].getText().trim());
                    khachHang.setDiem(Integer.parseInt(txt_array[2].getText().trim()));
    
                    if (khachHang.getSdt().isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Vui lòng chọn khách hàng để sửa!");
                        return;
                    }
    
                    if (!checkValidate(khachHang)) {
                        return;
                    }
    
                    if (khachHangBUS.updateKhachHang(khachHang)) {
                        JOptionPane.showMessageDialog(null, "Sửa khách hàng thành công!");
                        cancel();
                    } else {
                        JOptionPane.showMessageDialog(null, "Sửa khách hàng thất bại!");
                    }
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
        tool.Editable(txt_array,false);
        selectedRow = -1;
        lastSelectedRow = -1;
    }

    private boolean checkValidate(KhachHangDTO khachHang) {
        // Kiểm tra dữ liệu đầu vào
        if (khachHang.getSdt().isEmpty() || khachHang.getHoTen().isEmpty() || valueOf(khachHang.getDiem()).isEmpty()) {
            JOptionPane.showMessageDialog(null, "Vui lòng điền đầy đủ các trường thông tin");
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
            if (kh.getSdt().equals(khachHang.getSdt()) && khachHangList.indexOf(kh) != selectedRow) {
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
