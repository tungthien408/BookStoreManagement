package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

import DTO.TaiKhoanNVDTO;

public class MenuGUI {
    String array_function[] = { "Bán sách", "Nhập sách", "Hóa đơn bán", "Hóa đơn nhập", "Sách", 
                              "Nhà xuất bản", "Tác giả", "Khách hàng", "Nhân viên", "Tài khoản", 
                              "Phân quyền", "Thống kê","Giảm Giá ", "Đăng xuất" };
    JPanel panel_content[];
    JPanel menuContent;
    private static final Color MENU_BACKGROUND = new Color(0, 36, 107);
    private static final Color MENU_HOVER = new Color(15, 76, 104);
    private static final String[] PERMISSION_LABELS = {
        "Bán sách", "Nhập sách", "Hóa đơn bán", "Hóa đơn nhập", "Sách",
        "Nhà xuất bản", "Tác giả", "Nhân viên", "Khách hàng", "Thống kê","Giảm Giá "
    }; // Permissions 0–9

    public MenuGUI(TaiKhoanNVDTO account) {
        Tool tool = new Tool();
        int length = array_function.length;
        int width = 1200;
        int height = (int) (width * 0.625);

        JFrame frame = tool.createFrame("Book Shop Management", 1200, null);
        JPanel mainPanel = tool.createPanel(width, height, new BorderLayout());
        panel_content = new JPanel[length];

        JPanel sideMenu = new JPanel(new GridBagLayout());
        sideMenu.setBorder(BorderFactory.createEmptyBorder(-45, 0, 0, 0));
        sideMenu.setBackground(MENU_BACKGROUND);
        sideMenu.setPreferredSize(null);

        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(8, 20, 10, 20);
        c.gridx = 0;
        c.gridy = 0;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.NORTH;

        JPanel panel_logo = new JPanel(new GridBagLayout());
        panel_logo.setBackground(new Color(0, 0, 0, 0));
        panel_logo.setPreferredSize(new Dimension(130, 130));
        ImageIcon Logo = new ImageIcon("images/book-icon-transparent-image.png");
        Image image = Logo.getImage();
        Image Logo_newImage = image.getScaledInstance(110, 110, Image.SCALE_SMOOTH);
        Logo = new ImageIcon(Logo_newImage);
        JLabel imageLabel = new JLabel(Logo);
        panel_logo.add(imageLabel);
        panel_logo.setOpaque(false);
        c.weighty = -5;
        sideMenu.add(panel_logo, c);

        // Initialize all panels (will filter based on permissions)
        for (int i = 0; i < length; i++) {
            switch (i) {
                case 0:
                    panel_content[i] = new BanSachGUI(account).getPanel();
                    break;
                case 1:
                    panel_content[i] = new NhapSachGUI(account).getPanel();
                    break;
                case 2:
                    panel_content[i] = new HoaDonBanGUI().getPanel();
                    break;
                case 3:
                    panel_content[i] = new HoaDonNhapGUI().getPanel();
                    break;
                case 4:
                    panel_content[i] = new SachGUI().getPanel();
                    break;
                case 5:
                    panel_content[i] = new NhaXuatBanGUI().getPanel();
                    break;
                case 6:
                    panel_content[i] = new TacGiaGUI().getPanel();
                    break;
                case 7:
                    panel_content[i] = new KhachHangGUI().getPanel();
                    break;
                case 8:
                    panel_content[i] = new NhanVienGUI().getPanel();
                    break;
                case 9:
                    panel_content[i] = new TaiKhoanGUI().getPanel();
                    break;
                case 10:
                    panel_content[i] = new PhanQuyenGUI().getPanel();
                    break;
                case 11:
                    panel_content[i] = new ThongKeGUI().getPanel();
                    break;
                case 12:
                    panel_content[i] = new MaGiamGiaGUI().getPanel();
                    break;
                case 13:
                    panel_content[i] = new DangXuatGUI().getPanel();
                    break;
                default:
                    panel_content[i] = new JPanel();
                    break;
            }
        }

        this.menuContent = (panel_content[0] != null) ? panel_content[0] : new JPanel();

        // Map menu items to permission indices (0–9)
        int[] permissionIndices = {
            0,  // Bán sách
            1,  // Nhập sách
            0,  // Hóa đơn bán
            1,  // Hóa đơn nhập
            2,  // Mã giảm giá 
            4,  // Sách
            5,  // Nhà xuất bản
            6,  // Tác giả
            8,  // Khách hàng
            3,  // Nhân viên
            7,  // Tài khoản 
            7,  // Phân quyền 
            9,  // Thống kê
            -1  // Đăng xuất (always visible)
        };

        String maQuyen = account.getMaQuyen(); // Get permissions from TaiKhoanNVDTO

        // Tạo menu items based on permissions
        for (int i = 0; i < length; i++) {
            c.gridy = i + 1;
            c.weighty = 0;
            final int func = i;

            JPanel panel = new JPanel(new BorderLayout());
            JLabel label = new JLabel(array_function[i], SwingConstants.CENTER);
            label.setForeground(Color.WHITE);
            label.setFont(new Font("Arial", Font.PLAIN, 18));
            panel.add(label, BorderLayout.CENTER);
            panel.setBackground(MENU_BACKGROUND);

            // Show menu items based on maQuyen
            if (i == 13 || (maQuyen != null && !maQuyen.isEmpty() && permissionIndices[i] != -1 &&
                    maQuyen.contains(String.valueOf(permissionIndices[i])))) {
                panel.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseEntered(MouseEvent e) {
                        panel.setBackground(MENU_HOVER);
                    }

                    @Override
                    public void mouseExited(MouseEvent e) {
                        panel.setBackground(MENU_BACKGROUND);
                    }

                    @Override
                    public void mouseClicked(MouseEvent e) {
                        if (panel_content[func] != null) {
                            frame.setTitle("Book Shop Management - " + array_function[func]);
                            mainPanel.remove(menuContent);
                            menuContent = panel_content[func];
                            mainPanel.add(menuContent, BorderLayout.CENTER);
                            mainPanel.revalidate();
                            mainPanel.repaint();
                        }
                        if (array_function[func].equals("Đăng xuất")) {
                            JOptionPane pane = new JOptionPane(
                                    "Bạn có muốn đăng xuất không?",
                                    JOptionPane.QUESTION_MESSAGE,
                                    JOptionPane.YES_NO_OPTION);
                            JDialog dialog = pane.createDialog("Thông báo");
                            dialog.setLocationRelativeTo(null);
                            dialog.setVisible(true);

                            Object value = pane.getValue();
                            if (value != null && value.equals(JOptionPane.YES_OPTION)) {
                                frame.dispose();
                                new LoginGUI();
                            }
                        }
                    }

                    @Override
                    public void mouseReleased(MouseEvent e) {
                        panel.setBackground(MENU_BACKGROUND);
                    }
                });
                panel.setCursor(new Cursor(Cursor.HAND_CURSOR));
                sideMenu.add(panel, c);
            }
        }

        sideMenu.setPreferredSize(new Dimension(160, Math.max(height, sideMenu.getPreferredSize().height)));
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setViewportView(sideMenu);
        scrollPane.setBorder(null);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        mainPanel.add(scrollPane, BorderLayout.WEST);
        mainPanel.add(menuContent, BorderLayout.CENTER);
        frame.add(mainPanel);

        frame.setVisible(true);
    }
}