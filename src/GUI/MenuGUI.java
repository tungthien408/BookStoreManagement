// Giao diện của menu

package GUI;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;

public class MenuGUI {
    String array_function[] = {"Bán sách", "Nhập sách", "Sách", "Nhà xuất bản", "Tác giả", "Hóa đơn nhập", "Hóa đơn bán", "Khuyến mãi", "Khách hàng", "Nhân viên", "Tài khoản", "Phân quyền", "Thống kê"};
    // ArrayList <JPanel> panel_content = new ArrayList<>();
    JPanel panel_content[];
    JPanel menuContent;
    // Hàm khởi tạo giao diện menu
    public MenuGUI() {
        Tool tool = new Tool();
        int length = array_function.length;

        JFrame frame = tool.createFrame("Book Shop Management", 1600, 1000, null);
        JPanel mainPanel = tool.createPanel(1600, 1000, new BorderLayout());
        panel_content = new JPanel[length];

        JPanel sideMenu = new JPanel(new GridBagLayout()); // GridBagLayout() -> layout linh động nhất nhưng phức tạp nhất trong java swing
        sideMenu.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0)); // xóa padding với margin mặc định của panel
        sideMenu.setBackground(new Color(21, 96, 130));
        
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(10, 10, 10, 10);
        c.gridx = 0;
        c.weightx = 0.5;
        c.fill = GridBagConstraints.HORIZONTAL;

        for (int i = 0; i < length; i++) {
            switch (i) {
                case 0:
                    panel_content[i] = new BanSachGUI().getPanel();
                    break;
                case 1:
                    panel_content[i] = new NhapSachGUI().getPanel();
                    break;
                case 2:
                    panel_content[i] = new SachGUI().getPanel();
                    break;
                case 3:
                    panel_content[i] = new NhaXuatBanGUI().getPanel();
                    break;
                case 4:
                    panel_content[i] = new TacGiaGUI().getPanel();
                    break;
                case 5:
                    panel_content[i] = new HoaDonNhapGUI().getPanel();
                    break;
                case 6:
                    panel_content[i] = new HoaDonBanGUI().getPanel();
                    break;
                case 7:
                    panel_content[i] = new KhuyenMaiGUI().getPanel();
                    break;
                case 8:
                    panel_content[i] = new KhachHangGUI().getPanel();
                    break;
                case 9:
                    panel_content[i] = new NhanVienGUI().getPanel();
                    break;
                case 10:
                    panel_content[i] = new TaiKhoanGUI().getPanel();
                    break;
                case 11:
                    panel_content[i] = new PhanQuyenGUI().getPanel();
                    break;
                case 12:
                    panel_content[i] = new ThongKeGUI().getPanel();
                    break;
                default:
                    panel_content[i] = new JPanel();
                    break;
            }
        }

        this.menuContent = (panel_content[0] != null) ? panel_content[0] : new JPanel();

        // Tạo từng module cho thanh menu bên trái
        for (int i = 0; i < length; i++) {
            c.gridy = i;
            final int func = i;

            JPanel panel = new JPanel(new BorderLayout());
            JLabel label = new JLabel(array_function[i], SwingConstants.CENTER);
            label.setForeground(Color.WHITE);
            label.setFont(new Font("Arial", Font.PLAIN, 20));
            panel.add(label, BorderLayout.CENTER);
            panel.setBackground(new Color(21, 96, 130));

            panel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    panel.setBackground(new Color(15, 76, 104));
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    panel.setBackground(new Color(21, 96, 130));
                }

                @Override
                public void mouseClicked(MouseEvent e) {
                    if (panel_content[func] != null) {
                        frame.setTitle("Book Shop Management - " + array_function[func]);
                        mainPanel.remove(menuContent);
                        menuContent = panel_content[func];
                        mainPanel.add(menuContent, BorderLayout.CENTER);
                        // refresh the UI
                        mainPanel.revalidate();
                        mainPanel.repaint();    
                    }
                }

                @Override
                public void mouseReleased(MouseEvent e) {
                    panel.setBackground(new Color(21, 96, 130));
                }
            });
            sideMenu.add(panel, c);
        }

        mainPanel.add(sideMenu, BorderLayout.WEST);
        mainPanel.add(menuContent, BorderLayout.AFTER_LAST_LINE);
        frame.add(mainPanel);


        frame.setVisible(true);
    }
}
