package GUI;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import DTO.SachDTO;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Dimension;

public abstract class BaseGUI extends JPanel {
    protected static final int WIDTH = 1200;
    protected static final int SIDE_MENU_WIDTH = 151;
    protected static final int HEIGHT = (int) (WIDTH * 0.625);

    protected ToolTmp tool = new ToolTmp();
    protected JTextField txt_search;
    protected ArrayList<JButton> buttons;
    protected ArrayList<JTextField> txt_array_top;
    protected ArrayList<JTextField> txt_array_down;
    protected JComboBox<String> comboBox;

    protected JTable table_down, table_top;
    protected JLabel imageLabel;
    protected JPanel imagePanel;
    protected ImageIcon finalIcon;

    protected int selectedRow = -1;
    protected int lastSelectedRow = -1;
    protected int count = 0;

    protected void initializeTextFields(ArrayList<JTextField> textFieldList, int count) {
        for (int i = 0; i < count; i++) {
            JTextField textField = new JTextField();
            textFieldList.add(textField);
        }
    }

    protected JPanel createButtonPanel(String [] buttonTexts, String xy) {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(tool.createButtonPanel(buttons, buttonTexts, new Color(0, 36, 107), Color.WHITE, xy));
        return buttonPanel;
    }

    protected JPanel createTable_top(String[] column, DefaultTableModel model, int width, int height) {
        table_top = tool.createTable(model, column);
        table_top.setDefaultEditor(Object.class, null);
        JScrollPane scrollPane = new JScrollPane(table_top);
        scrollPane.setPreferredSize(new Dimension(width, height));
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel panelTable = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelTable.add(scrollPane);
        addEventTable(table_top, 1);
        return panelTable;
    }

    protected JPanel createTable_down(String []column, DefaultTableModel model, int width, int height) {
        table_down = tool.createTable(model, column);
        table_down.setDefaultEditor(Object.class, null);
        JScrollPane scrollPane = new JScrollPane(table_down);
        scrollPane.setPreferredSize(new Dimension(width, height));

        addEventTable(table_down, 2);
        JPanel panelTable = new JPanel();
        panelTable.setLayout(new BoxLayout(panelTable, BoxLayout.Y_AXIS)); // Stack components vertically
        scrollPane.setBorder(BorderFactory.createEmptyBorder(60, 0, 0, 0));
        scrollPane.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        panelTable.add(scrollPane);
        panelTable.add(createButtonPanel(new String[] { "Thêm", "Xóa", "Thanh toán" }, "x"));
        panelTable.add(Box.createVerticalStrut(50)); // Add 20px spacing
        panelTable.setBorder(BorderFactory.createEmptyBorder(90, 10, 0, 10));
        // panelTable.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        return panelTable;

    }

    protected JPanel createDetailPanel(int width, int padding_top, ArrayList<JTextField> txt_array,
        String[] txt_label, ImageIcon img) {
        JPanel panelDetail = tool.createDetailPanel(txt_array, txt_label, img, width, 320, 2, txt_label.length, false);
        JPanel wrappedPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        wrappedPanel.add(panelDetail);
        wrappedPanel.setBorder(BorderFactory.createEmptyBorder(padding_top, 0, 0, 0));
        return wrappedPanel;
    }

    protected abstract void addEventTable(JTable table, int type);

    protected void initializeBaseFields() {
        txt_search = new JTextField();
        txt_array_top = new ArrayList<>();
        txt_array_down = new ArrayList<>();
    }

    protected abstract void initializeCustomFields();
}