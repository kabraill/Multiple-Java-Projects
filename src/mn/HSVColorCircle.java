package mn;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class HSVColorCircle extends JPanel {
    private BufferedImage canvas;

    public HSVColorCircle() {
        // Create a BufferedImage to draw on
        canvas = new BufferedImage(1000, 1000, BufferedImage.TYPE_INT_RGB);
        drawCircleWithHSV(500, 500, 500); // Draw a circle at (200, 200) with radius 150
    }

    // Method to draw a circle with distributed HSV colors using SLERP
    private void drawCircleWithHSV(int centerX, int centerY, int radius) {
        int segments = 360*4; // Number of segments to interpolate
        Color[] colors = new Color[segments];

        // Calculate colors for each segment
        for (int i = 0; i < segments; i++) {
            float hue = i / (float) segments; // Hue ranges from 0 to 1
            colors[i] = Color.getHSBColor(hue, 1.0f, 1.0f); // Full saturation and brightness
        }

        // Draw the circle with interpolated colors
        for (int i = 0; i < segments; i++) {
            // Interpolate between the current color and the next color
            Color color = slerp(colors[i], colors[(i + 1) % segments], 0.5f);
            drawSegment(centerX, centerY, radius, i * (2 * Math.PI / segments), color);
        }
    }

    // Draw a segment of the circle
    private void drawSegment(int centerX, int centerY, int radius, double angle, Color color) {
        int xStart = (int) (centerX + radius * Math.cos(angle));
        int yStart = (int) (centerY + radius * Math.sin(angle));
        
        int xEnd = (int) (centerX + radius * Math.cos(angle + (2 * Math.PI / 360))); // Next angle
        int yEnd = (int) (centerY + radius * Math.sin(angle + (2 * Math.PI / 360)));

        // Draw a line from start to end points
        drawLine(centerX, centerY, xStart, yStart, color);
        drawLine(centerX, centerY, xEnd, yEnd, color);
    }

    // Draw a line from (x1, y1) to (x2, y2) with Bresenham's algorithm
    private void drawLine(int x1, int y1, int x2, int y2, Color color) {
        int dx = Math.abs(x2 - x1);
        int dy = Math.abs(y2 - y1);
        int sx = (x1 < x2) ? 1 : -1;
        int sy = (y1 < y2) ? 1 : -1;
        int err = dx - dy;

        while (true) {
            setPixel(x1, y1, color);
            if (x1 == x2 && y1 == y2) break;
            int err2 = err * 2;
            if (err2 > -dy) {
                err -= dy;
                x1 += sx;
            }
            if (err2 < dx) {
                err += dx;
                y1 += sy;
            }
        }
    }

    // Spherical Linear Interpolation (SLERP) between two colors
    private Color slerp(Color color1, Color color2, float t) {
        float[] rgb1 = color1.getRGBColorComponents(null);
        float[] rgb2 = color2.getRGBColorComponents(null);

        // Convert RGB to a normalized 3D vector
        float[] v1 = normalize(rgb1);
        float[] v2 = normalize(rgb2);

        // Compute the dot product
        float dot = dotProduct(v1, v2);
        dot = Math.max(-1.0f, Math.min(1.0f, dot)); // Clamp to [-1, 1]

        // Calculate the angle between the two vectors
        float theta = (float) Math.acos(dot) * t;
        float sinTheta = (float) Math.sin(theta);

        // Compute the interpolated color
        float[] result = new float[3];
        for (int i = 0; i < 3; i++) {
            result[i] = (float) (Math.sin((1 - t) * theta) * v1[i] + Math.sin(t * theta) * v2[i]) / sinTheta;
        }

        // Convert the result back to Color
        return new Color(clamp(result[0]), clamp(result[1]), clamp(result[2]));
    }

    // Normalize a color vector
    private float[] normalize(float[] rgb) {
        float length = (float) Math.sqrt(rgb[0] * rgb[0] + rgb[1] * rgb[1] + rgb[2] * rgb[2]);
        return new float[]{rgb[0] / length, rgb[1] / length, rgb[2] / length};
    }

    // Calculate the dot product of two vectors
    private float dotProduct(float[] v1, float[] v2) {
        return v1[0] * v2[0] + v1[1] * v2[1] + v1[2] * v2[2];
    }

    // Clamp the value to the range [0, 1]
    private int clamp(float value) {
        return (int) Math.max(0, Math.min(255, value * 255));
    }

    // Helper method to set a pixel color in the BufferedImage
    private void setPixel(int x, int y, Color color) {
        if (x >= 0 && x < canvas.getWidth() && y >= 0 && y < canvas.getHeight()) {
            canvas.setRGB(x, y, color.getRGB());
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(canvas, 0, 0, null); // Draw the BufferedImage onto the panel
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("HSV Color Circle with SLERP");
        HSVColorCircle hsvColorCircle = new HSVColorCircle();
        frame.add(hsvColorCircle);
        frame.setSize(400, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
