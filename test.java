public static void main(String[] args) {
    DefaultTableModel model = (DefaultTableModel) table_down.getModel();
    int rowCount = model.getRowCount();
    int imgWidth = 900;
    int imgHeight = 100 + rowCount * 25 + 100;
    BufferedImage img = new BufferedImage(imgWidth, imgHeight, BufferedImage.TYPE_INT_RGB);
    Graphics2D g2d = img.createGraphics();
    g2d.setColor(Color.WHITE);
    g2d.fillRect(0, 0, imgWidth, imgHeight);
    g2d.setColor(Color.BLACK);
    g2d.setFont(new Font("New Times Roman", Font.BOLD, 18));
    g2d.drawString("HÓA ĐƠN BÁN HÀNG", 500, 30);
    g2d.drawString("CỬA HÀNG BÁN SÁCH", 75, 30);
    g2d.setFont(new Font("New Times Roman", Font.BOLD, 16));
    g2d.drawString("Địa chỉ:............................. ", 51, 54);
    g2d.drawString("ĐT:....................................", 51, 78);
    g2d.drawString("Mặt hàng bán (Hoặc nghành nghề kinh doanh)", 423, 66);
    g2d.drawString("Tên Khách hàng:.....................................", 51, 128);
    g2d.drawString("Địa chỉ:.....................................", 51, 152);
    g2d.drawLine(51, 160, imgWidth - 51, 170);
    g2d.drawString("STT", 60, 182);
    g2d.drawString("TÊN HÀNG", 150, 182);
    g2d.drawString("SỐ LƯỢNG", 300, 182);
    g2d.drawString("ĐƠN GIÁ", 450, 182);
    g2d.drawString("THÀNH TIỀN", 600, 182);
    try {
        ImageIO.write(img, "png", new File("HoaDonBan\\Hoa_Don_Ban.png"));
        JFrame frame = new JFrame("Hóa đơn bán hàng");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(900, 800);
        frame.setLocationRelativeTo(null);

        JLabel label = new JLabel(new ImageIcon(img));
        label.setBackground(Color.black);
        frame.add(label);

        frame.setVisible(true);
    } catch (Exception e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(null, "Lỗi khi in hóa đơn: " + e.getMessage());
    }
}
