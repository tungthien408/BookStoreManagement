

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;

public class test extends JFrame {
    private static final String UPLOAD_DIR = "images/Book/"; // Folder to store images
    private JLabel imageLabel; // To display the selected image
    private File selectedImageFile; // To store the selected image file
    private JButton uploadButton, saveButton;
    
    public test() {
        setTitle("Simple Image Upload");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        initializeComponents();
        initializeUploadFolder();
        layoutComponents();
    }
    
    private void initializeComponents() {
        // Initialize image label
        imageLabel = new JLabel();
        imageLabel.setPreferredSize(new Dimension(300, 300));
        imageLabel.setHorizontalAlignment(JLabel.CENTER);
        imageLabel.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.GRAY));
        
        // Initialize buttons
        uploadButton = new JButton("Select Image");
        saveButton = new JButton("Save Image");
        
        // Add action listeners
        uploadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectImage();
            }
        });
        
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveImage();
            }
        });
    }
    
    private void initializeUploadFolder() {
        File uploadDir = new File(UPLOAD_DIR);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }
    }
    
    private void layoutComponents() {
        JPanel mainPanel = new JPanel(new BorderLayout());
        
        // Image display panel
        JPanel imagePanel = new JPanel();
        imagePanel.add(imageLabel);
        mainPanel.add(imagePanel, BorderLayout.CENTER);
        
        // Button panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(uploadButton);
        buttonPanel.add(saveButton);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        add(mainPanel);
    }
    
    private void selectImage() {
        JFileChooser fileChooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
            "Image Files", "jpg", "png", "jpeg", "gif");
        fileChooser.setFileFilter(filter);
        
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            selectedImageFile = fileChooser.getSelectedFile();
            
            // Display the selected image
            ImageIcon imageIcon = new ImageIcon(selectedImageFile.getAbsolutePath());
            Image scaledImage = imageIcon.getImage().getScaledInstance(
                300, 300, Image.SCALE_SMOOTH);
            imageLabel.setIcon(new ImageIcon(scaledImage));
        }
    }
    
    private void saveImage() {
        if (selectedImageFile == null) {
            JOptionPane.showMessageDialog(this, "Please select an image first!");
            return;
        }
        
        try {
            // Generate unique filename
            String fileExtension = selectedImageFile.getName().substring(
                selectedImageFile.getName().lastIndexOf("."));
            String newFileName = "image_" + System.currentTimeMillis() + fileExtension;
            Path destination = Paths.get(UPLOAD_DIR + newFileName);
            
            // Copy file to destination
            Files.copy(selectedImageFile.toPath(), destination);
            
            JOptionPane.showMessageDialog(this, 
                "Image saved successfully to: " + destination.toString());
            
            // Reset selection
            selectedImageFile = null;
            imageLabel.setIcon(null);
            
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, 
                "Error saving image: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new test().setVisible(true);
            }
        });
    }
}