package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import BUS.NXBBUS;
import BUS.SachBUS;
import BUS.TacGiaBUS;
import DTO.SachDTO;

public class SachGUI {
    private static final int WIDTH = 1200;
    private static final int SIDE_MENU_WIDTH = 151;
    private static final int HEIGHT = (int) (WIDTH * 0.625);
    private static final int TABLE_WIDTH = 850;
    private static final int TABLE_HEIGHT = 300;
    private static final int DETAIL_PANEL_WIDTH = 850;
    private static final int DETAIL_PANEL_HEIGHT = 150;
    private static final String DEFAULT_IMAGE_PATH = "/images/Book/the_little_prince.jpg";

    private Tool tool = new Tool();
    private JPanel panel, panelDetail;
    private JTextField[] txt_array = new JTextField[6]; // For maSach, tenSach, theLoai, soLuong, donGia, maTG, maNXB
    private JButton[] buttons = new JButton[6];
    private JTable table;
    private List<SachDTO> sachList;
    private SachBUS sachBUS = new SachBUS();
    private NXBBUS nxbBUS = new NXBBUS();
    private TacGiaBUS tacGiaBUS = new TacGiaBUS();

    private int selectedRow = -1;
    private int lastSelectedRow = -1;
    private boolean add = false;
    private boolean update = false;
    private boolean delete = false;
    private int count = 0;
    private JLabel imageLabel; // For displaying the book image
    private JPanel imagePanel; // Panel to hold the image

    public String getID() {
        String str = String.valueOf(count);
        while (str.length() != 3) {
            str = "0" + str;
        }
        return "S" + str;
    }

    public SachGUI() {
        panel = tool.createPanel(WIDTH - SIDE_MENU_WIDTH, HEIGHT, new BorderLayout());
        panel.setBackground(new Color(202, 220, 252));
        initializeTextFields();
        initializeData();
        panel.add(createSearchPanel(), BorderLayout.NORTH);
        panel.add(createBookTable(), BorderLayout.CENTER);
        panel.add(createPanelButton(), BorderLayout.EAST);
        String[] txt_label = {"Mã sách", "Tên sách", "Thể loại", "Số lượng", "Đơn giá", "Mã tác giả"};
        JPanel lowerPanel = new JPanel(new BorderLayout(10, 0));
        lowerPanel.add(createDetailPanel(txt_array, txt_label), BorderLayout.EAST);
        // panel.add(createDetailPanel(txt_array, txt_label), BorderLayout.SOUTH);

        // Initialize the image panel
        imagePanel = new JPanel(new BorderLayout());
        imageLabel = new JLabel();
        imageLabel.setHorizontalAlignment(JLabel.CENTER);
        imageLabel.setVerticalAlignment(JLabel.CENTER);
        imagePanel.setPreferredSize(new Dimension(200, 260)); // Adjust size as needed
        imagePanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        imagePanel.setBorder(BorderFactory.createEmptyBorder(0, 30, 0, 0));
        imagePanel.add(imageLabel, BorderLayout.CENTER);
        lowerPanel.add(imagePanel, BorderLayout.WEST); // Add the image panel to the right

        panel.add(lowerPanel, BorderLayout.SOUTH);
    }

    private void initializeTextFields() {
        for (int i = 0; i < txt_array.length; i++) {
            txt_array[i] = new JTextField();
        }
    }

    private void initializeData() {
        try {
            sachList = sachBUS.getAllSach();
            if (!sachList.isEmpty()) {
                String maSach = sachList.get(sachList.size() - 1).getMaSach();
                String numericPart = maSach.substring(1);
                count = Integer.parseInt(numericPart) + 1;
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi khi tải dữ liệu sách: " + e.getMessage());
        }
    }

    private JPanel createBookTable() {
        String[] columns = {"Mã sách", "Tên sách", "Thể loại", "Số lượng", "Đơn giá", "Mã tác giả"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);

        try {
            for (SachDTO sach : sachList) {
                model.addRow(new Object[]{
                        sach.getMaSach(),
                        sach.getTenSach(),
                        sach.getTheLoai(),
                        sach.getSoLuong(),
                        sach.getDonGia(),
                        sach.getMaTG()
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi khi tải dữ liệu sách: " + e.getMessage());
        }

        table = tool.createTable(model, columns);
        table.setDefaultEditor(Object.class, null);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(TABLE_WIDTH, TABLE_HEIGHT));

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (add || update) {
                    return;
                }

                tool.clearFields(txt_array);
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

                    lastSelectedRow = -1;
                    imageLabel.setIcon(null); // Clear the image
                    imagePanel.revalidate();
                    imagePanel.repaint();
                } else if (selectedRow >= 0) {
                    for (int i = 0; i < txt_array.length; i++) {
                        txt_array[i].setText(String.valueOf(table.getValueAt(selectedRow, i)));
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

                    // --- Load and Display the Image ---
                    String bookId = table.getValueAt(selectedRow, 0).toString();
                    SachDTO sach = sachBUS.getSachByMaSach(bookId);

                    if (sach != null) {
                        ImageIcon finalIcon = null;
                        BufferedImage originalImage = null;

                        try {
                            String imgName = sach.getImg();
                            if (imgName != null && !imgName.trim().isEmpty()) {
                                String absoluteImagePath = "/home/thien408/Documents/programming/java/Java/DoAn/BookStoreManagement/images/Book/" + imgName;
                                File imageFile = new File(absoluteImagePath);

                                if (imageFile.exists() && imageFile.isFile()) {
                                    originalImage = ImageIO.read(imageFile);

                                    if (originalImage != null) {
                                        int targetWidth = imagePanel.getPreferredSize().width;
                                        int targetHeight = imagePanel.getPreferredSize().height;
                                        if (targetWidth <= 0) targetWidth = 200;
                                        if (targetHeight <= 0) targetHeight = 250;

                                        Image scaledImage = originalImage.getScaledInstance(targetWidth, targetHeight, Image.SCALE_SMOOTH);
                                        finalIcon = new ImageIcon(scaledImage);
                                    } else {
                                        System.err.println("ImageIO.read returned null for file: " + absoluteImagePath);
                                    }
                                } else {
                                    System.err.println("Image file not found or is not a file: " + absoluteImagePath);
                                }
                            } else {
                                System.err.println("Image name is null or empty for book: " + bookId);
                            }
                        } catch (IOException ioEx) {
                            System.err.println("IOException reading image file: " + sach.getImg() + " - " + ioEx.getMessage());
                            ioEx.printStackTrace();
                        }

                        // Load default image if necessary
                        if (finalIcon == null) {
                            try {
                                BufferedImage defaultOriginal = null;
                                String defaultImagePath = "/home/thien408/Documents/programming/java/Java/DoAn/BookStoreManagement/images/Book/default.jpg";
                                File defaultImageFile = new File(defaultImagePath);
                                if (defaultImageFile.exists()) {
                                    defaultOriginal = ImageIO.read(defaultImageFile);
                                }

                                if (defaultOriginal != null) {
                                    int targetWidth = imagePanel.getPreferredSize().width;
                                    int targetHeight = imagePanel.getPreferredSize().height;
                                    if (targetWidth <= 0) targetWidth = 200;
                                    if (targetHeight <= 0) targetHeight = 250;

                                    Image scaledDefault = defaultOriginal.getScaledInstance(targetWidth, targetHeight, Image.SCALE_SMOOTH);
                                    finalIcon = new ImageIcon(scaledDefault);
                                }
                            } catch (IOException ioEx) {
                                System.err.println("IOException reading default image: " + ioEx.getMessage());
                            }
                        }

                        // Update the image label
                        imageLabel.setIcon(finalIcon);
                        imagePanel.revalidate();
                        imagePanel.repaint();
                    }
                }
            }
        });

        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 40, 30, 10));
        // JPanel panelTable = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel panelTable = new JPanel();
        panelTable.add(scrollPane);
        panelTable.setBackground(Color.GREEN);
        panelTable.setPreferredSize(new Dimension(TABLE_WIDTH, TABLE_HEIGHT - 100));
        panelTable.setMinimumSize(new Dimension(TABLE_WIDTH, TABLE_HEIGHT - 100));
        panelTable.setMaximumSize(new Dimension(TABLE_WIDTH, TABLE_HEIGHT - 100));
        return panelTable;
    }

    private JPanel createPanelButton() {
        String[] txt_btn = {"Thêm", "Sửa", "Xóa", "Nhập Excel", "Xuất Excel", "Hủy"};
        JPanel panelBtn = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelBtn.add(tool.createButtonPanel(buttons, txt_btn, new Color(0, 36, 107), Color.WHITE, "y"));

        buttons[0].addActionListener(e -> addSach());
        buttons[1].addActionListener(e -> updateSach());
        buttons[2].addActionListener(e -> deleteSach());
        buttons[3].addActionListener(e -> importFromExcel());
        buttons[4].addActionListener(e -> exportToExcel());
        buttons[5].addActionListener(e -> cancel());

        return panelBtn;
    }

    private JPanel createDetailPanel(JTextField[] txt_array, String[] txt_label) {
        panelDetail = tool.createDetailPanel(txt_array, txt_label, null, DETAIL_PANEL_WIDTH, DETAIL_PANEL_HEIGHT, 0.5, 3, false);

        JPanel wrappedPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        wrappedPanel.add(panelDetail);
        wrappedPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));
        return wrappedPanel;
    }

    private JPanel createSearchPanel() {
        String[] searchOptions = {"Mã sách", "Tên sách", "Mã tác giả"};
        JPanel panelSearch = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelSearch.add(Box.createHorizontalStrut(25));
        panelSearch.add(tool.createSearchTextField(0, 0, searchOptions));
        return panelSearch;
    }

    private void refreshTable() {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);
        try {
            sachList = sachBUS.getAllSach();
            for (SachDTO sach : sachList) {
                model.addRow(new Object[]{
                        sach.getMaSach(),
                        sach.getTenSach(),
                        sach.getTheLoai(),
                        sach.getSoLuong(),
                        sach.getDonGia(),
                        sach.getMaTG()
                });
            }
            if (!sachList.isEmpty()) {
                String maSach = sachList.get(sachList.size() - 1).getMaSach();
                String numericPart = maSach.substring(1);
                count = Integer.parseInt(numericPart) + 1;
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi khi làm mới bảng: " + e.getMessage());
        }
    }

    private void addSach() {

        table.clearSelection();
        update = false;
        delete = false;
        if (!add) {
            add = true;
            tool.clearFields(txt_array);
            tool.clearButtons(buttons);
            tool.Editable(txt_array, true);
            txt_array[0].setText(getID());
            buttons[0].setBackground(new Color(202, 220, 252));
            buttons[0].setForeground(Color.BLACK);
            buttons[5].setBackground(Color.RED);

            for (int i = 0, length = buttons.length - 1; i < length; i++) {
                if (i != 0) {
                    buttons[i].setVisible(false);
                }
            }
            txt_array[3].setText("0"); 
            txt_array[0].setEditable(false);
            txt_array[3].setEditable(false);
        } else {
            try {
                SachDTO sach = new SachDTO();
                sach.setMaSach(txt_array[0].getText().trim());
                sach.setTenSach(txt_array[1].getText().trim());
                sach.setTheLoai(txt_array[2].getText().trim());
                sach.setDonGia(Integer.parseInt(txt_array[4].getText().trim()));
                sach.setMaTG(txt_array[5].getText().trim());
                sach.setSoLuong(0);
                sach.setMaNXB(null);

                if (!checkValidate(sach)) {
                    return;
                }

                if (sachBUS.addSach(sach)) {
                    JOptionPane.showMessageDialog(null, "Thêm sách thành công!");
                    cancel();
                } else {
                    JOptionPane.showMessageDialog(null, "Thêm sách thất bại!");
                }
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Lỗi khi thêm sách: " + e.getMessage());
            }
        }
    }

    private void updateSach() {
        if (add) {
            tool.clearFields(txt_array);
        }
        add = false;
        delete = false;
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(null, "Vui lòng chọn sách để sửa!");
        } else if (!update) {
            update = true;
            tool.clearButtons(buttons);
            tool.Editable(txt_array, true);

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
                SachDTO sach = new SachDTO();
                sach.setMaSach(txt_array[0].getText().trim());
                sach.setTenSach(txt_array[1].getText().trim());
                sach.setTheLoai(txt_array[2].getText().trim());
                sach.setSoLuong(Integer.parseInt(txt_array[3].getText().trim()));
                sach.setDonGia(Integer.parseInt(txt_array[4].getText().trim()));
                sach.setMaTG(txt_array[5].getText().trim());

                if (sach.getMaSach().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Vui lòng chọn sách để sửa!");
                    return;
                }

                if (!checkValidate(sach)) {
                    return;
                }

                if (sachBUS.updateSach(sach)) {
                    JOptionPane.showMessageDialog(null, "Sửa sách thành công!");
                    cancel();
                } else {
                    JOptionPane.showMessageDialog(null, "Sửa sách thất bại!");
                }
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Lỗi khi sửa sách: " + e.getMessage());
            }
        }
    }

    private void deleteSach() {
        if (add) {
            tool.clearFields(txt_array);
        }
        add = false;
        update = false;
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(null, "Vui lòng chọn sách để xóa!");
        } else {
            try {
                String maSach = txt_array[0].getText().trim();
                if (maSach.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Vui lòng chọn sách để xóa!");
                    return;
                }
                if (JOptionPane.showConfirmDialog(null, "Bạn có chắc muốn xóa sách này?", "Xóa thông tin sách", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                    if (sachBUS.deleteSach(maSach)) {
                        JOptionPane.showMessageDialog(null, "Xóa sách thành công!");
                        cancel();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Lỗi khi xóa sách: " + e.getMessage());
            }
        }
    }

    private void importFromExcel() {
        JOptionPane.showMessageDialog(null, "Chức năng Nhập Excel đang được phát triển!");
    }

    private void exportToExcel() {
        JOptionPane.showMessageDialog(null, "Chức năng Xuất Excel đang được phát triển!");
    }

    private void cancel() {
        add = false;
        update = false;
        delete = false;
        refreshTable();
        tool.clearButtons(buttons);
        tool.clearFields(txt_array);
        tool.Editable(txt_array, false);
        selectedRow = -1;
        lastSelectedRow = -1;
    }

    private boolean checkValidate(SachDTO sach) {
        if (sach.getMaSach().isEmpty() || sach.getTenSach().isEmpty() || sach.getTheLoai().isEmpty() ||
                sach.getMaTG().isEmpty() ) {
            JOptionPane.showMessageDialog(null, "Vui lòng điền đầy đủ các trường thông tin!");
            return false;
        }

        if (sach.getTenSach().length() > 100) {
            JOptionPane.showMessageDialog(null, "Tên sách không được nhiều hơn 100 ký tự!");
            return false;
        }

        if (sach.getTheLoai().length() > 50) {
            JOptionPane.showMessageDialog(null, "Thể loại không được nhiều hơn 50 ký tự!");
            return false;
        }



        if (!tacGiaBUS.isTacGiaExists(sach.getMaTG())) {
            JOptionPane.showMessageDialog(null, "Mã tác giả không tồn tại!");
            return false;
        }



        return true;
    }

    public JPanel getPanel() {
        return this.panel;
    }
}