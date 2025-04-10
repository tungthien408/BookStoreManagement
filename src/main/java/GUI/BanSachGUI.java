package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

public class BanSachGUI {
    private Tool tool = new Tool();
    private JPanel mainPanel;
    private JTextField txtName, txtPublisher, txtAuthor, txtCategory, txtQuantity;
    private static final int WIDTH = 1200;
    private static final int SIDE_MENU_WIDTH = 151;
    private static final int HEIGHT = (int) (WIDTH * 0.625);
    private static final String[] COLUMN_HEADERS = {"Mã sách", "Tên sách", "Số lượng", "Đơn giá"};

    public BanSachGUI() {
        initializeMainPanel();
        addComponents();
    }

    private void initializeMainPanel() {
        mainPanel = tool.createPanel(WIDTH - SIDE_MENU_WIDTH, HEIGHT, new BorderLayout());
    }

    private void addComponents() {
        mainPanel.add(createSearchPanel(), BorderLayout.NORTH);
        mainPanel.add(createMainContentPanel(), BorderLayout.CENTER);
        mainPanel.add(createButtonPanel(), BorderLayout.SOUTH);
    }

    private JPanel createSearchPanel() {
        String[] searchOptions = {"Mã nhân viên", "Mã khách hàng"};
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchPanel.add(Box.createHorizontalStrut(33));
        searchPanel.add(tool.createSearchTextField(300, 30, searchOptions));
        return searchPanel;
    }

    private JPanel createMainContentPanel() {
        JPanel contentPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 2));
        
        // Bảng 1 (trên bên trái)
        JScrollPane tableScrollPane = createTableScrollPane(getSampleData(), 600, 250);
        contentPanel.add(tableScrollPane);

        // Panel chi tiết 1 (trên bên phải)
        contentPanel.add(createDetailPanel1());

        // Panel chi tiết 2 (dưới panel 1)
        contentPanel.add(createDetailPanel2());

        // Bảng 2 (dưới cùng)
        JScrollPane tableScrollPane2 = createTableScrollPane(getSampleData(), 600, 250);
        JPanel tablePanel2 = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        tablePanel2.add(tableScrollPane2);
        contentPanel.add(tablePanel2);

        return contentPanel;
    }

    private JScrollPane createTableScrollPane(String[][] data, int width, int height) {
        JTable table = tool.createTable(data, COLUMN_HEADERS);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(width, height));
        return scrollPane;
    }

    private JPanel createDetailPanel1() {
        JTextField[] textFields = {txtName, txtPublisher, txtAuthor, txtCategory, txtQuantity};
        String[] labels = {"Tên", "Nhà xuất bản", "Tác giả", "Thể loại", "Số lượng"};
        JPanel detailPanel = tool.createJTextField(textFields, labels);
        
        JPanel wrapper = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        wrapper.setPreferredSize(new Dimension(400, 250));
        wrapper.add(detailPanel);
        return wrapper;
    }

    private JPanel createDetailPanel2() {
        JTextField[] textFields = {txtName, txtQuantity};
        String[] labels = {"Tên", "Số lượng"};
        JPanel detailPanel = tool.createDetailPanel(textFields, labels, null, 380, 300, 0, 2);
        
        JPanel wrapper = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        wrapper.add(detailPanel);
        return wrapper;
    }

    private JPanel createButtonPanel() {
        String[] buttonTexts = {"Thêm", "Xóa", "Thanh toán"};
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(tool.createButtonHorizontal(buttonTexts, new Color(0, 36, 107), Color.WHITE, "x"));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 110, 95));
        return buttonPanel;
    }

    private String[][] getSampleData() {
        return new String[][] {
            {"HD001", "NV001", "0123456789", "2023-01-01", "500000"},
            {"HD002", "NV002", "0987654321", "2023-01-02", "450000"},
            {"S021", "Book U", "Fiction", "110", "1100", "A021", "NXB021"},
            {"S022", "Book V", "Non-Fiction", "115", "1150", "A022", "NXB022"},
            {"S023", "Book W", "Science", "120", "1200", "A023", "NXB023"},
            {"S024", "Book X", "History", "125", "1250", "A024", "NXB024"},
            {"S025", "Book Y", "Biography", "130", "1300", "A025", "NXB025"},
            {"S026", "Book Z", "Fantasy", "135", "1350", "A026", "NXB026"},
            {"S027", "Book AA", "Mystery", "140", "1400", "A027", "NXB027"},
            {"S028", "Book AB", "Romance", "145", "1450", "A028", "NXB028"},
            {"S029", "Book AC", "Thriller", "150", "1500", "A029", "NXB029"},
            {"S030", "Book AD", "Adventure", "155", "1550", "A030", "NXB030"},
            {"S031", "Book AE", "Fiction", "160", "1600", "A031", "NXB031"},
            {"S032", "Book AF", "Non-Fiction", "165", "1650", "A032", "NXB032"},
            {"S033", "Book AG", "Science", "170", "1700", "A033", "NXB033"},
            {"S034", "Book AH", "History", "175", "1750", "A034", "NXB034"},
            {"S035", "Book AI", "Biography", "180", "1800", "A035", "NXB035"},
            {"S036", "Book AJ", "Fantasy", "185", "1850", "A036", "NXB036"},
            {"S037", "Book AK", "Mystery", "190", "1900", "A037", "NXB037"},
            {"S038", "Book AL", "Romance", "195", "1950", "A038", "NXB038"},
            {"S039", "Book AM", "Thriller", "200", "2000", "A039", "NXB039"},
            {"S040", "Book AN", "Adventure", "205", "2050", "A040", "NXB040"}
        };
    }

    public JPanel getPanel() {
        return mainPanel;
    }
}