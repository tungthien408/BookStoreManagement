package GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.LayoutManager;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;

public class Tool {
	public Tool() {}
	public JFrame createFrame(String title, int width, int height, LayoutManager layout) {
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

		DefaultTableCellRenderer ob = new DefaultTableCellRenderer();
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

	public JButton createButton(int width, int height, String text, ImageIcon icon, Color bg, Color fg, ActionListener event) {
		// TODO
		JButton button = (icon != null) ? new JButton(text, icon) : new JButton(text);
		button.setPreferredSize(new Dimension(width, height));
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

		return button;
	}

	// find things
}
