/**
 * Cung cấp những hàm cần thiết để thiết kế giao diện hệ thống dễ dàng hơn.
 * @author Dương Tùng Thiện, Dang Thai Tu
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
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
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
	public JPanel createButtonPanel(String texts[], Color bg, Color fg, String xy) {
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
			if(xy == "y")
				c.gridy += 1; 
			if(xy=="x")
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

	public JPanel createSearchTextField(int x, int y,String[] list) {
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        
        JTextField textField = new JTextField(20);
        textField.setPreferredSize(new Dimension(120, 30));
        textField.setBackground(new Color(202, 220, 252));
		// textField.setForeground(new Color(192, 79, 21));
        JComboBox<String> comboBox = new JComboBox<>(list);
		comboBox.setBackground(new Color(202, 220, 252));
        comboBox.setPreferredSize(new Dimension(120, 30));
        
        // Thêm sự kiện tìm kiếm
        // textField.addActionListener(new ActionListener() {
        //     @Override
        //     public void actionPerformed(ActionEvent e) {
        //         String searchText = textField.getText().trim();
        //         String searchCriteria = (String) comboBox.getSelectedItem();
        //         System.out.println("Tìm kiếm: " + searchText + " theo " + searchCriteria);
        //         // TODO: Thêm logic lọc dữ liệu trong JTable tại đây
        //     }
        // });
        
        searchPanel.add(textField);
        searchPanel.add(comboBox);
        return searchPanel;
    }
	
	public JPanel createDetailPanel(JTextField[] txt_array, String[] txt_label, ImageIcon img, int width,int height, double weightx, final int TEXTFIELD_CAPACITY, boolean newLine) {
		// final int TEXTFIELD_CAPACITY = 3;
		JPanel panelDetail = createPanel(width, height, new GridBagLayout());
        JPanel panel_detail = new JPanel(new GridBagLayout());

        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
		c.gridy = 0;
        c.weightx = weightx;
        c.weighty = 0;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(5, 0, 5, 10);

		// Image
		if (img != null) {
			JLabel label_img = new JLabel();
			label_img.setIcon(img);

			JPanel panelImg = new JPanel();
			panelImg.add(label_img);
			// add image inside panelImg
			panelDetail.add(panelImg, c);
		}
		
        
        for (int i = 0; i < txt_array.length; i++) {
             txt_array[i] = new JTextField();
			txt_array[i].setBackground(new Color(202, 220, 252));
			// txt_array[i].setForeground(new Color(192, 79, 21));
            txt_array[i].setPreferredSize(new Dimension(182, 30));//182
            // txt_array[i].setEditable(false);
        }

		int count = 0;

		while (count < txt_array.length) {
			int index = count;
			count = ((count + TEXTFIELD_CAPACITY) < txt_array.length) ? count + TEXTFIELD_CAPACITY : txt_array.length;
			for (int i = index; i < count; i++) {
				c.gridy += 1;
				JLabel label = new JLabel(txt_label[i]);
				panel_detail.add(label, c);
				if (newLine == true) {
					c.gridy += 1;
					panel_detail.add(txt_array[i], c);
				} else {
					c.gridx += 1;
					panel_detail.add(txt_array[i], c);
					c.gridx--;	
				}
			}	

			c.fill = GridBagConstraints.VERTICAL;
			c.gridx++;
			c.gridy = 0;
			panelDetail.add(panel_detail, c);
			c.fill = GridBagConstraints.HORIZONTAL;
	
			panel_detail = new JPanel(new GridBagLayout());
	
		}

        JPanel wrappedPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        wrappedPanel.add(panelDetail);
        wrappedPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 70, 0));

		return panelDetail;
	}
	
	public JPanel createJTextField(JTextField[] txt_array, String[] txt_label) {
		final int TEXTFIELD_CAPACITY = 5;
		JPanel panelDetail = createPanel(400, 250, new GridBagLayout());
		JPanel panel_detail = new JPanel(new GridBagLayout());
	
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;  // Cột bắt đầu
		c.gridy = 0;  // Dòng bắt đầu
		c.weightx = 0.5;
		c.weighty = 0;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(5, 5, 5, 5);  // Khoảng cách xung quanh
	
		// Khởi tạo các ô nhập liệu
		for (int i = 0; i < txt_array.length; i++) {
			txt_array[i] = new JTextField();
			txt_array[i].setBackground(new Color(202, 220, 252));
			txt_array[i].setPreferredSize(new Dimension(182, 30));
			txt_array[i].setEditable(false);  // Không cho chỉnh sửa
		}
	
		int count = 0;
	
		while (count < txt_array.length) {
			int index = count;
			count = ((count + TEXTFIELD_CAPACITY) < txt_array.length) ? count + TEXTFIELD_CAPACITY : txt_array.length;
			
			for (int i = index; i < count; i++) {
				// Đặt lại vị trí cột cho mỗi cặp nhãn-ô nhập liệu
				c.gridx = 0;
				// Thêm nhãn
				JLabel label = new JLabel(txt_label[i]);
				panel_detail.add(label, c);
				
				// Chuyển sang cột tiếp theo trên cùng dòng cho ô nhập liệu
				c.gridx = 1;
				panel_detail.add(txt_array[i], c);
				
				// Chuyển xuống dòng tiếp theo cho cặp tiếp theo
				c.gridy++;
			}   
	
			c.fill = GridBagConstraints.VERTICAL;
			c.gridx = 0;  // Đặt lại về cột đầu tiên
			c.gridy = 0;  // Đặt lại về dòng đầu tiên
			panelDetail.add(panel_detail, c);
			c.fill = GridBagConstraints.HORIZONTAL;
	
			panel_detail = new JPanel(new GridBagLayout());
		}
	
		JPanel wrappedPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		wrappedPanel.add(panelDetail);
		wrappedPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 70, 0));
	
		return panelDetail;
	}
	public JPanel createButtonPanel1(JButton[] button, Color bg, Color fg, String xy) {
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

		for (int i = 0; i < button.length ; i++) {
			if(xy == "y")
				c.gridy += 1; 
			if(xy=="x")
				c.gridx += 1; 
			// JButton button = new JButton(texts[i]);
			button[i].setFocusable(false);
			button[i].setPreferredSize(new Dimension(btn_width, btn_height));
			button[i].setBackground(bg);
			button[i].setForeground(fg);
	
			panel.add(button[i], c);		
		}

		return panel;
	}
}
