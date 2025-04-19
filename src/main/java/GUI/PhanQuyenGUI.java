package GUI;

import java.awt.*;
import java.util.concurrent.Flow;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

public class PhanQuyenGUI {
    Tool tool = new Tool();
    JPanel panel;
    int width = 1200;
    int width_sideMenu = 151;
    int height = (int) (width * 0.625);
    JPanel panel_buttons, panel_table;
    JButton btn_add, btn_edit, btn_delete;
    private static final Color MENU_BACKGROUND = new Color(0, 36, 107);
    private static final Color MENU_HOVER = new Color(15, 76, 104);

    public PhanQuyenGUI() {
        // Tạo panel chia bố cục
        panel = tool.createPanel(width - width_sideMenu, height, new BorderLayout());
        panel_buttons = tool.createPanel(width - width_sideMenu, 50, new FlowLayout(FlowLayout.LEFT, 10, 0));
        panel_table = tool.createPanel(width - width_sideMenu, height - 50, new BorderLayout());
        // Thêm các nội dung vào các panel
        panel_buttons.setBorder(BorderFactory.createEmptyBorder(7, 3, 10, 10));
        panel_table.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
        btn_add = new JButton("THÊM");
        btn_edit = new JButton("SỬA");
        btn_delete = new JButton("XÓA");
        btn_add.setFont(new Font("Arial", Font.BOLD, 14));
        btn_edit.setFont(new Font("Arial", Font.BOLD, 14));
        btn_delete.setFont(new Font("Arial", Font.BOLD, 14));
        btn_add.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn_edit.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn_delete.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn_add.setFocusable(false);
        btn_edit.setFocusable(false);
        btn_delete.setFocusable(false);
        btn_add.setBackground(new Color(0, 36, 107));
        btn_edit.setBackground(new Color(0, 36, 107));
        btn_delete.setBackground(new Color(0, 36, 107));
        btn_add.setForeground(Color.white);
        btn_edit.setForeground(Color.white);
        btn_delete.setForeground(Color.white);
        Dimension buttonSize = new Dimension(150, 35);
        btn_add.setPreferredSize(buttonSize);
        btn_edit.setPreferredSize(buttonSize);
        btn_delete.setPreferredSize(buttonSize);
        // Hiệu ứng nút bấm
        btn_add.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btn_add.setBackground(MENU_HOVER);
            }

            public void mouseExited(MouseEvent e) {
                btn_add.setBackground(MENU_BACKGROUND);
            }
        });
        btn_edit.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btn_add.setBackground(MENU_HOVER);
            }

            public void mouseExited(MouseEvent e) {
                btn_add.setBackground(MENU_BACKGROUND);
            }
        });
        btn_delete.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btn_add.setBackground(MENU_HOVER);
            }

            public void mouseExited(MouseEvent e) {
                btn_add.setBackground(MENU_BACKGROUND);
            }
        });
        panel_buttons.add(btn_add); // Thêm nút thêm
        panel_buttons.add(btn_edit); // Thêm nút sửa
        panel_buttons.add(btn_delete); // Thêm nút xóa
        // Tạo các nút chức năng
        String[] columnNames = { "MÃ QUYỀN", "TÊN QUYỀN" };
        String[][] data = {
                { "1", "Quản trị viên (admin)" },
                { "2", "Quản lý" },
                { "3", "Bán hàng" },
                { "4", "Kế toán" },
        };
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        for (String[] row : data) {
            model.addRow(row);
        }
        JTable table = new JTable(model);
        table.setRowHeight(25);
        table.getTableHeader().setResizingAllowed(false);
        table.getTableHeader().setReorderingAllowed(false);
        table.setDefaultEditor(Object.class, null);
        table.setShowGrid(false);
        table.setGridColor(Color.BLACK);
        table.setRowSelectionAllowed(true);
        table.setSelectionBackground(new Color(240, 240, 240));
        table.setSelectionForeground(Color.BLACK);
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 18));
        table.getTableHeader().setBackground(MENU_BACKGROUND);
        table.getTableHeader().setForeground(Color.white);
        table.setCursor(new Cursor(Cursor.HAND_CURSOR));
        table.setDragEnabled(false);
        table.setFocusable(false);
        table.setBackground(MENU_BACKGROUND);
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        table.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        table.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
        if (table.getRowCount() > 0) {
            table.setRowSelectionInterval(0, 0);
        }
        JScrollPane scrollPane = new JScrollPane(table);

        panel_table.add(scrollPane, BorderLayout.CENTER);
        panel.add(panel_table, BorderLayout.SOUTH);
        panel.add(panel_buttons, BorderLayout.NORTH);

    }

    public JPanel getPanel() {
        return this.panel;
    }
}
