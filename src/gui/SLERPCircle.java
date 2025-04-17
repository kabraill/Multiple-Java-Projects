/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

/**
 *
 * @author kabra
 */
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class SLERPCircle extends JPanel {

    // Define the two colors for the gradient
    private static final Color colorA = new Color(255, 0, 0); // Red
    private static final Color colorB = new Color(0, 0, 255); // Blue

    // Circle parameters
    private int radius;
    private int numPoints;

    public SLERPCircle(int radius){
        this.radius = radius;
        this.numPoints = (int) (2 * Math.PI * radius) + 1;
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;
        int centerX = getWidth() / 2;
        int centerY = getHeight() / 2;

        // Create a buffered image to handle the gradient fill
        BufferedImage image = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D imgGraphics = image.createGraphics();

        // Draw the circle with the gradient
        for (int i = 0; i < numPoints; i++) {
            double theta = 2 * Math.PI * i / numPoints;
            double x = centerX + radius * Math.cos(theta);
            double y = centerY + radius * Math.sin(theta);

            // Calculate SLERP factor t based on theta
            double t = (theta ) / (2 * Math.PI); // Normalize theta to 0-1 range

            // Interpolate between colorA and colorB using SLERP
            Color currentColor = slerpColor(colorA, colorB, t);

            // Set the current color and draw the point
            imgGraphics.setColor(currentColor);
            imgGraphics.fillRect((int) x, (int) y, 1, 1);
        }

        // Draw the final image
        g2d.drawImage(image, 0, 0, null);
        imgGraphics.dispose();
    }

    // SLERP for color interpolation
    private Color slerpColor(Color colorA, Color colorB, double t) {
        int r = (int) (slerp(colorA.getRed(), colorB.getRed(), t));
        int g = (int) (slerp(colorA.getGreen(), colorB.getGreen(), t));
        int b = (int) (slerp(colorA.getBlue(), colorB.getBlue(), t));
        return new Color(r, g, b);
    }

    // SLERP for scalar values (R, G, B channels)
    private double slerp(double a, double b, double t) {
        return (1 - t) * a + t * b;
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("SLERP Circle Gradient");
        SLERPCircle panel = new SLERPCircle(130);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 500);
        frame.add(panel);
        frame.setVisible(true);
    }
}
