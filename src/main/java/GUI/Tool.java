/**
 * Cung cấp những hàm cần thiết để thiết kế giao diện hệ thống dễ dàng hơn.
 * @author Dương Tùng Thiện
 */

package GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;

public class Tool {
	public Tool() {}
	public JFrame createFrame(String title, int width, LayoutManager layout) {
		int height = (int)(width * 0.625);
		JFrame frame = new JFrame(title);
		if (layout == null) {
			layout = new FlowLayout();
		}
		frame.setLayout(layout);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(width, height);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		return frame;
	}

	public JPanel createPanel(int width, int height, LayoutManager layout) {
		if (layout == null) {
			layout = new FlowLayout();
		}
		JPanel panel = new JPanel(layout);
		panel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		panel.setPreferredSize(new Dimension(width, height));
		return panel;
	}

	public JTable createTable(String tableContent[][], String nameField[]) {
		JTable table = new JTable(tableContent, nameField);

		table.setRowHeight(30);

		DefaultTableCellRenderer ob = new DefaultTableCellRenderer(); // Mục đích: Căn nội dung của bảng về giữa
		ob.setHorizontalAlignment(SwingConstants.CENTER);
		for (int i = 0; i < table.getColumnCount(); i++) {
			table.getColumnModel().getColumn(i).setCellRenderer(ob);
		}

		table.setShowGrid(true);
		table.setGridColor(Color.gray);
		table.setFont(new Font("Arial", Font.PLAIN, 14));

		JTableHeader header = table.getTableHeader();
		header.setFont(new Font("Arial", Font.BOLD, 18));
		return table;
	}

	// Hàm này bị thiếu việc xử lý sự kiện Event, đầy đủ của nó phải là public JPanel createButtonHorizontal(String texts[], Color bg, Color fg, ActionListener event)
	public JPanel createButtonHorizontal(String texts[], Color bg, Color fg) {
		// TODO: Thiếu việc gán event vào nút
		int btn_width = 130;
		int btn_height = 30;
		JPanel panel = new JPanel(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints(); // Để các nút có thể được thêm ngay phía dưới

		c.insets = new Insets(10, 10, 10, 10);
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 0.5;
        c.weighty = 0;
        c.fill = GridBagConstraints.HORIZONTAL;

		for (int i = 0; i < texts.length ; i++) {
			c.gridy += 1; 
			JButton button = new JButton(texts[i]);
			button.setFocusable(false);
			button.setPreferredSize(new Dimension(btn_width, btn_height));
			button.setBackground(bg);
			button.setForeground(fg);
	
			button.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseEntered(MouseEvent e) {
						if (bg != null) button.setBackground(bg.darker());
					}
	
					@Override
					public void mouseExited(MouseEvent e) {
						button.setBackground(bg);
					}
	
					@Override
					public void mouseReleased(MouseEvent e) {
						button.setBackground(bg);
					}
				});
			panel.add(button, c);		
		}

		return panel;
	}

	// Hàm này bị thiếu việc xử lý sự kiện Event, đầy đủ của nó phải là public JPanel createButtonVertical(String texts[], Color bg, Color fg, ActionListener event)
	public JPanel createButtonVertical(String texts[], Color bg, Color fg) {
		// TODO: Thiếu việc gán event vào nút
		int btn_width = 130;
		int btn_height = 30;
		JPanel panel = new JPanel(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

		c.insets = new Insets(10, 10, 10, 10);
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 0.5;
        c.weighty = 0;
        c.fill = GridBagConstraints.VERTICAL;

		for (int i = 0; i < texts.length ; i++) {
			c.gridx += 1; 
			JButton button = new JButton(texts[i]);
			button.setFocusable(false);
			button.setPreferredSize(new Dimension(btn_width, btn_height));
			button.setBackground(bg);
			button.setForeground(fg);
	
			button.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseEntered(MouseEvent e) {
						if (bg != null) button.setBackground(bg.darker());
					}
	
					@Override
					public void mouseExited(MouseEvent e) {
						button.setBackground(bg);
					}
	
					@Override
					public void mouseReleased(MouseEvent e) {
						button.setBackground(bg);
					}
				});
			panel.add(button, c);		
		}

		return panel;
	}


	// Chưa hoàn thiện, Tú đang làm
	public JTextField createSearchTextField(int x, int y) {
		JTextField textField = new JTextField(15);
		// textField.setPreferredSize(new Dimension(width, height));
		textField.setBounds(x, y, 30, 100);
		textField.setBackground(new Color(246,198,173));
		return textField;
	}

}
