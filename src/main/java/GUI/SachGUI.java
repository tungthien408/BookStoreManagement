package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
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
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

import BUS.NXBBUS;
import BUS.SachBUS;
import BUS.TacGiaBUS;
import DTO.SachDTO;
import Utils.EventManager;
import Utils.TableRefreshListener;

public class SachGUI implements TableRefreshListener {
    private static final int WIDTH = 1200;
    private static final int SIDE_MENU_WIDTH = 151;
    private static final int HEIGHT = (int) (WIDTH * 0.625);
    private static final int TABLE_WIDTH = 850;
    private static final int TABLE_HEIGHT = 400;
    private static final int DETAIL_PANEL_WIDTH = 600;
    private static final int DETAIL_PANEL_HEIGHT = 200;

    private Tool tool = new Tool();
    private JPanel panel, panelDetail;
    private JTextField[] txt_array = new JTextField[6]; // For maSach, tenSach, theLoai, soLuong, donGia, maTG
    private JButton[] buttons = new JButton[6];
    private JTable table;
    private JLabel imageLabel;
    private JPanel imagePanel;
    private List<SachDTO> sachList;
    private SachBUS sachBUS = new SachBUS();
    private NXBBUS nxbBUS = new NXBBUS();
    private TacGiaBUS tacGiaBUS = new TacGiaBUS();

    private int selectedRow = -1;
    // Lưu tên file ảnh vừa chọn
    private ImageIcon finalIcon;
    private String currentChosenImage = null;

    private int lastSelectedRow = -1;
    private boolean add = false;
    private boolean update = false;
    private boolean delete = false;
    private int count = 0;
    private JTextField[] txt_array_search = new JTextField[1];
    private JTextField txt_search;
    private JComboBox<String> comboBox;

    public String getID() {
        String str = String.valueOf(count);
        while (str.length() != 3) {
            str = "0" + str;
        }
        return "S" + str;
    }

    public SachGUI() {
        txt_search = new JTextField();
        txt_array_search = new JTextField[] { txt_search };
        panel = tool.createPanel(WIDTH - SIDE_MENU_WIDTH, HEIGHT, new BorderLayout());
        panel.setBackground(new Color(202, 220, 252));
        initializeTextFields();
        initializeData();
        setupPanelLayout();
        timkiem();
        EventManager.getInstance().registerListener(this);
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

    private void setupPanelLayout() {
        // Add search panel at the top
        panel.add(createSearchPanel(), BorderLayout.NORTH);
        // Create a center panel to hold table and detail panel
        JPanel centerPanel = new JPanel(new BorderLayout());
        // Add table to the center
        centerPanel.add(createBookTable(), BorderLayout.NORTH);

        // Create lower panel for detail
        String[] txt_label = { "Mã sách", "Tên sách", "Thể loại", "Số lượng", "Đơn giá", "Mã tác giả" };
        centerPanel.add(createDetailPanel(txt_array, txt_label), BorderLayout.CENTER);

        imagePanel = new JPanel(new BorderLayout());
        imageLabel = new JLabel();
        imageLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        imageLabel.setHorizontalAlignment(JLabel.CENTER);
        imageLabel.setVerticalAlignment(JLabel.CENTER);
        imageLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (!update && !add)
                    return;

                JFileChooser chooser = new JFileChooser();
                chooser.setFileFilter(new FileNameExtensionFilter("Image files", ImageIO.getReaderFileSuffixes()));
                if (chooser.showOpenDialog(panel) == JFileChooser.APPROVE_OPTION) {
                    File file = chooser.getSelectedFile();
                    try {
                        BufferedImage img = ImageIO.read(file);
                        if (img == null) {
                            JOptionPane.showMessageDialog(panel, "Không thể đọc file ảnh, vui lòng chọn lại!");
                            return;
                        }
                        Image scaled = img.getScaledInstance(
                                imagePanel.getWidth(),
                                imagePanel.getHeight(),
                                Image.SCALE_SMOOTH);
                        imageLabel.setIcon(new ImageIcon(scaled));
                        currentChosenImage = file.getName();
                        // copy file...
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(panel, "Lỗi I/O: " + ex.getMessage());
                    }
                }
            }
        });

        imagePanel.setPreferredSize(new Dimension(200, 260));
        imagePanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 0, 0));
        imagePanel.add(imageLabel, BorderLayout.CENTER);
        // imagePanel.setBackground(Color.BLUE);
        centerPanel.add(imagePanel, BorderLayout.WEST);

        // Add center panel to main panel
        panel.add(centerPanel, BorderLayout.CENTER);

        // Add button panel to the right
        panel.add(createPanelButton(), BorderLayout.EAST);
    }

    private JPanel createBookTable() {
        String[] columns = { "Mã sách", "Tên sách", "Thể loại", "Số lượng", "Đơn giá", "Mã tác giả" };
        DefaultTableModel model = new DefaultTableModel(columns, 0);

        try {
            for (SachDTO sach : sachList) {
                model.addRow(new Object[] {
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
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 40, 10, 10));

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (add || update) {
                    return;
                }

                tool.clearFields(txt_array);
                tool.clearButtons(buttons);
                add = false; // ????????????

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
                        txt_array[i].setText(String.valueOf(table.getValueAt(selectedRow, i)));
                        txt_array[i].setEditable(false);
                    }
                    String bookId = table.getValueAt(selectedRow, 0).toString();
                    SachDTO sach = sachBUS.getSachByMaSach(bookId);

                    if (sach != null) {
                        renderBookImage(bookId, sach);
                    } else {
                        System.err.println("SachDTO not found for ID: " + bookId);
                        imageLabel.setIcon(null);
                        imagePanel.revalidate();
                        imagePanel.repaint();
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

        JPanel panelTable = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelTable.add(scrollPane);
        return panelTable;
    }

    private JPanel createPanelButton() {
        String[] txt_btn = { "Thêm", "Sửa", "Xóa", "Nhập Excel", "Xuất Excel", "Hủy" };
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
        panelDetail = tool.createDetailPanel(txt_array, txt_label, null, DETAIL_PANEL_WIDTH, DETAIL_PANEL_HEIGHT, 0.5,
                3,
                false);

        JPanel wrappedPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        wrappedPanel.add(panelDetail);
        wrappedPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));
        return wrappedPanel;
    }

    private JPanel createSearchPanel() {
        String[] searchOptions = { "Mã sách", "Tên sách", "Thể loại" };
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

        // Add real-time search
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
            for (SachDTO sach : sachList) {
                boolean match = false;
                switch (searchType) {
                    case "Mã sách":
                        match = sach.getMaSach().toLowerCase().contains(query.toLowerCase());
                        break;
                    case "Tên sách":
                        match = sach.getTenSach().toLowerCase().contains(query.toLowerCase());
                        break;
                    case "Thể loại":
                        match = sach.getTheLoai().toLowerCase().contains(query.toLowerCase());
                        break;
                }
                if (match) {
                    model.addRow(new Object[] {
                            sach.getMaSach(),
                            sach.getTenSach(),
                            sach.getTheLoai(),
                            sach.getSoLuong(),
                            sach.getDonGia(),
                            sach.getMaTG()
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
            sachList = sachBUS.getAllSach();
            for (SachDTO sach : sachList) {
                model.addRow(new Object[] {
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
            renderBookImage("haha", null);

            for (int i = 0, length = buttons.length - 1; i < length; i++) {
                if (i != 0) {
                    buttons[i].setVisible(false);
                }
            }
            txt_array[3].setText("0");
            txt_array[0].setEditable(false);
            txt_array[3].setEditable(false);
        } else {
            if (txt_array[4].getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Vui lòng nhập đơn giá!");
                return;
            }

            try {
                SachDTO sach = new SachDTO();
                sach.setMaSach(txt_array[0].getText().trim());
                sach.setTenSach(txt_array[1].getText().trim());
                sach.setTheLoai(txt_array[2].getText().trim());
                sach.setDonGia(Integer.parseInt(txt_array[4].getText().trim()));
                sach.setMaTG(txt_array[5].getText().trim().toUpperCase());
                sach.setSoLuong(0);
                sach.setMaNXB(null);
                sach.setImg(currentChosenImage);

                if (!checkValidate(sach)) {
                    return;
                }

                if (sachBUS.addSach(sach)) {
                    EventManager.getInstance().notifyListeners();
                    JOptionPane.showMessageDialog(null, "Thêm sách thành công!");
                    cancel();
                } else {
                    JOptionPane.showMessageDialog(null, "Thêm sách thất bại!");
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Đơn giá phải là số!");
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Lỗi khi thêm sách: " + e.getMessage());
            } 
        }
    }

    private void updateSach() {
        if (add)
            tool.clearFields(txt_array);
        add = false;
        delete = false;
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(null, "Vui lòng chọn sách để sửa!");
            return;
        }
        if (!update) {
            update = true;
            tool.clearButtons(buttons);
            tool.Editable(txt_array, true);
            buttons[1].setBackground(new Color(202, 220, 252));
            buttons[1].setForeground(Color.BLACK);
            buttons[5].setBackground(Color.RED);
            for (int i = 0; i < buttons.length - 1; i++)
                if (i != 1)
                    buttons[i].setVisible(false);
            txt_array[0].setEditable(false);
            return;
        }
        try {
            SachDTO sach = new SachDTO();
            sach.setMaSach(txt_array[0].getText().trim());
            sach.setTenSach(txt_array[1].getText().trim());
            sach.setTheLoai(txt_array[2].getText().trim());
            sach.setSoLuong(Integer.parseInt(txt_array[3].getText().trim()));
            sach.setDonGia(Integer.parseInt(txt_array[4].getText().trim()));
            sach.setMaTG(txt_array[5].getText().trim().toUpperCase());
            if (currentChosenImage != null) {
                sach.setImg(currentChosenImage);
            }
            if (sachBUS.updateSach(sach)) {
                EventManager.getInstance().notifyListeners();
                JOptionPane.showMessageDialog(null, "Sửa sách thành công!");
                currentChosenImage = null;
                cancel();
            } else {
                JOptionPane.showMessageDialog(null, "Sửa sách thất bại!");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Lỗi khi sửa sách: " + e.getMessage());
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
                if (JOptionPane.showConfirmDialog(null, "Bạn có chắc muốn xóa sách này?", "Xóa thông tin sách",
                        JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                    if (sachBUS.deleteSach(maSach)) {
                        EventManager.getInstance().notifyListeners();
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
        finalIcon = null;
        imageLabel.setIcon(finalIcon);
        refreshTable();
        tool.clearButtons(buttons);
        tool.clearFields(txt_array);
        tool.Editable(txt_array, false);
        selectedRow = -1;
        lastSelectedRow = -1;
    }

    private boolean checkValidate(SachDTO sach) {
        if (sach.getMaSach().isEmpty() || sach.getTenSach().isEmpty() || sach.getTheLoai().isEmpty() ||
                sach.getMaTG().isEmpty()) {
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

        if (sach.getDonGia() <= 0) {
            JOptionPane.showMessageDialog(null, "Đơn giá phải lớn hơn 0!");
            return false;
        }

        if (sach.getImg() != null && sach.getImg().length() > 50) {
            JOptionPane.showMessageDialog(null, "Tên file ảnh không được nhiều hơn 50 ký tự!");
            return false;
        }

        return true;
    }

    public JPanel getPanel() {
        return this.panel;
    }

    public void renderBookImage(String bookId, SachDTO sach) {
        finalIcon = null;
        BufferedImage originalImage = null;
        if (sach != null) {
            try {
                String imgName = sach.getImg();
                System.out.println("Image name from database: " + imgName);
                if (imgName != null && !imgName.trim().isEmpty()) {
                    String absoluteImagePath = System.getProperty("user.dir") + "/images/Book/" + imgName;
                    System.out.println("Constructed image path: " + absoluteImagePath);
                    java.nio.file.Path imagePath = java.nio.file.Paths.get(absoluteImagePath);
                    File imageFile = imagePath.toFile();
                    if (imageFile.exists() && imageFile.isFile()) {
                        originalImage = ImageIO.read(imageFile);
                        if (originalImage != null) {
                            System.out.println("Successfully read image file: " + absoluteImagePath);
                            int targetWidth = imagePanel.getPreferredSize().width;
                            int targetHeight = imagePanel.getPreferredSize().height;
                            if (targetWidth <= 0)
                                targetWidth = 200;
                            if (targetHeight <= 0)
                                targetHeight = 250;
                            Image scaledImage = originalImage.getScaledInstance(targetWidth, targetHeight,
                                    Image.SCALE_SMOOTH);
                            finalIcon = new ImageIcon(scaledImage);
                            System.out.println("Scaled image to: " + targetWidth + "x" + targetHeight);
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
                System.err.println(
                        "IOException reading image file: " + sach.getImg() + " - " + ioEx.getMessage());
                ioEx.printStackTrace();
            } catch (Exception ex) {
                System.err.println(
                        "General error processing image " + sach.getImg() + ": " + ex.getMessage());
                ex.printStackTrace();
            }    
        }

        if (finalIcon == null) {
            System.err.println("Attempting to load and scale default image...");
            try {
                BufferedImage defaultOriginal = null;
                String defaultImagePath = System.getProperty("user.dir") + "/images/Book/default.jpg";
                File defaultImageFile = new File(defaultImagePath);
                if (defaultImageFile.exists()) {
                    defaultOriginal = ImageIO.read(defaultImageFile);
                } else {
                    System.err.println("Default image file not found at: " + defaultImagePath);
                }
                if (defaultOriginal != null) {
                    int targetWidth = imagePanel.getPreferredSize().width;
                    int targetHeight = imagePanel.getPreferredSize().height;
                    if (targetWidth <= 0)
                        targetWidth = 200;
                    if (targetHeight <= 0)
                        targetHeight = 250;
                    Image scaledDefault = defaultOriginal.getScaledInstance(targetWidth, targetHeight,
                            Image.SCALE_SMOOTH);
                    finalIcon = new ImageIcon(scaledDefault);
                    System.out.println("Loaded and scaled default image.");
                }
            } catch (IOException ioEx) {
                System.err.println("IOException reading default image: " + ioEx.getMessage());
            } catch (Exception ex) {
                System.err.println("General error processing default image: " + ex.getMessage());
            }
        }

        imageLabel.setIcon(finalIcon);
        imagePanel.revalidate();
        imagePanel.repaint();
    }
}