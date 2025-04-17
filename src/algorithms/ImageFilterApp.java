/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithms;

/**
 *
 * @author kabra
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import java.io.IOException;

public class ImageFilterApp extends JFrame {

    private BufferedImage originalImage;
    private BufferedImage displayedImage;
    private ImagePanel imagePanel;

    public ImageFilterApp() {
        // Set up the JFrame
        setTitle("Image Filter Application");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Create a JPanel to display the image
        imagePanel = new ImagePanel();
        add(imagePanel, BorderLayout.CENTER);

        // Create a panel for buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1, 5));

        // Button for opening an image
        JButton openButton = new JButton("Open Image");
        openButton.addActionListener(e -> openImage());
        buttonPanel.add(openButton);

        // Button for grayscale filter
        JButton grayscaleButton = new JButton("Grayscale");
        grayscaleButton.addActionListener(e -> applyGrayscale());
        buttonPanel.add(grayscaleButton);

        // Button for blur filter
        JButton blurButton = new JButton("Blur");
        blurButton.addActionListener(e -> applyBlur());
        buttonPanel.add(blurButton);

        // Button for sharpen filter
        JButton sharpenButton = new JButton("Sharpen");
        sharpenButton.addActionListener(e -> applySharpen());
        buttonPanel.add(sharpenButton);

        // Button for threshold filter
        JButton thresholdButton = new JButton("Threshold");
        thresholdButton.addActionListener(e -> applyThreshold());
        buttonPanel.add(thresholdButton);

        // Add the button panel to the frame
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void openImage() {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            try {
                File file = fileChooser.getSelectedFile();
                originalImage = ImageIO.read(file);
                displayedImage = originalImage;
                imagePanel.repaint();
            } catch (IOException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error loading image", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void applyGrayscale() {
        if (originalImage != null) {
            displayedImage = convertToGrayscale(originalImage);
            imagePanel.repaint();
        }
    }

    private void applyBlur() {
        if (originalImage != null) {
            displayedImage = applyBlurFilter(originalImage);
            imagePanel.repaint();
        }
    }

    private void applySharpen() {
        if (originalImage != null) {
            displayedImage = applySharpenFilter(originalImage);
            imagePanel.repaint();
        }
    }

    private void applyThreshold() {
        if (originalImage != null) {
            int threshold = 128; // Simple static threshold
            displayedImage = applyThresholdFilter(originalImage, threshold);
            imagePanel.repaint();
        }
    }

    // Convert the image to grayscale
    private BufferedImage convertToGrayscale(BufferedImage image) {
        BufferedImage grayscaleImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_RGB);
        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                int rgb = image.getRGB(x, y);
                Color color = new Color(rgb);
                int gray = (int)(0.2989 * color.getRed() + 0.5870 * color.getGreen() + 0.1140 * color.getBlue());
                Color grayColor = new Color(gray, gray, gray);
                grayscaleImage.setRGB(x, y, grayColor.getRGB());
            }
        }
        return grayscaleImage;
    }

    // Apply a simple blur filter
    private BufferedImage applyBlurFilter(BufferedImage image) {
        BufferedImage blurredImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_RGB);
        for (int y = 1; y < image.getHeight() - 1; y++) {
            for (int x = 1; x < image.getWidth() - 1; x++) {
                int r = 0, g = 0, b = 0, count = 0;
                for (int dy = -1; dy <= 1; dy++) {
                    for (int dx = -1; dx <= 1; dx++) {
                        int rgb = image.getRGB(x + dx, y + dy);
                        Color color = new Color(rgb);
                        r += color.getRed();
                        g += color.getGreen();
                        b += color.getBlue();
                        count++;
                    }
                }
                r /= count;
                g /= count;
                b /= count;
                Color blurredColor = new Color(r, g, b);
                blurredImage.setRGB(x, y, blurredColor.getRGB());
            }
        }
        return blurredImage;
    }

    // Apply a sharpen filter
    private BufferedImage applySharpenFilter(BufferedImage image) {
        int[][] kernel = {{0, -1, 0}, {-1, 5, -1}, {0, -1, 0}};
        BufferedImage sharpenedImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_RGB);
        for (int y = 1; y < image.getHeight() - 1; y++) {
            for (int x = 1; x < image.getWidth() - 1; x++) {
                int r = 0, g = 0, b = 0;
                for (int ky = -1; ky <= 1; ky++) {
                    for (int kx = -1; kx <= 1; kx++) {
                        int rgb = image.getRGB(x + kx, y + ky);
                        Color color = new Color(rgb);
                        r += color.getRed() * kernel[ky + 1][kx + 1];
                        g += color.getGreen() * kernel[ky + 1][kx + 1];
                        b += color.getBlue() * kernel[ky + 1][kx + 1];
                    }
                }
                r = Math.min(Math.max(r, 0), 255);
                g = Math.min(Math.max(g, 0), 255);
                b = Math.min(Math.max(b, 0), 255);
                Color sharpenedColor = new Color(r, g, b);
                sharpenedImage.setRGB(x, y, sharpenedColor.getRGB());
            }
        }
        return sharpenedImage;
    }

    // Apply a threshold filter
    private BufferedImage applyThresholdFilter(BufferedImage image, int threshold) {
        BufferedImage thresholdedImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_RGB);
        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                int rgb = image.getRGB(x, y);
                Color color = new Color(rgb);
                int gray = (int)(0.2989 * color.getRed() + 0.5870 * color.getGreen() + 0.1140 * color.getBlue());
                if (gray > threshold) {
                    thresholdedImage.setRGB(x, y, Color.WHITE.getRGB());
                } else {
                    thresholdedImage.setRGB(x, y, Color.BLACK.getRGB());
                }
            }
        }
        return thresholdedImage;
    }

    // Custom JPanel to display the image
    class ImagePanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (displayedImage != null) {
                g.drawImage(displayedImage, 0, 0, this);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ImageFilterApp app = new ImageFilterApp();
            app.setVisible(true);
        });
    }
}
