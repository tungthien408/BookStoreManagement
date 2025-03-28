package GUI;

import java.awt.*;
import javax.swing.*;

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
		panel.setPreferredSize(new Dimension(width, height));
		return panel;
	}

	// table
	
	// button field
}
