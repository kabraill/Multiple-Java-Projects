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
import java.awt.geom.*;

public class ExtendedShapesExample extends JPanel {

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // Set anti-aliasing for smoother shapes
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Line
        g2d.setColor(Color.RED);
        g2d.drawLine(20, 20, 100, 100);

        // Rectangle
        g2d.setColor(Color.BLUE);
        g2d.drawRect(120, 20, 80, 50);
        g2d.setColor(Color.CYAN);
        g2d.fillRect(220, 20, 80, 50);

        // Round Rectangle
        g2d.setColor(Color.GREEN);
        g2d.draw(new RoundRectangle2D.Double(320, 20, 100, 50, 20, 20));
        g2d.setColor(Color.MAGENTA);
        g2d.fill(new RoundRectangle2D.Double(440, 20, 100, 50, 20, 20));

        // Ellipse
        g2d.setColor(Color.ORANGE);
        g2d.drawOval(20, 120, 80, 50);
        g2d.setColor(Color.PINK);
        g2d.fillOval(120, 120, 80, 50);

        // Arc
        g2d.setColor(Color.BLACK);
        g2d.draw(new Arc2D.Double(220, 120, 80, 50, 0, 180, Arc2D.OPEN));
        g2d.setColor(Color.GRAY);
        g2d.fill(new Arc2D.Double(320, 120, 80, 50, 0, 180, Arc2D.PIE));

        // Polygon
        int[] xPoints = {420, 480, 520};
        int[] yPoints = {120, 70, 120};
        g2d.setColor(Color.DARK_GRAY);
        g2d.drawPolygon(xPoints, yPoints, 3);
        g2d.setColor(Color.LIGHT_GRAY);
        g2d.fillPolygon(xPoints, yPoints, 3);

        // Quadratic Curve
        g2d.setColor(Color.RED);
        g2d.draw(new QuadCurve2D.Double(20, 200, 100, 250, 200, 200));

        // Cubic Curve
        g2d.setColor(Color.BLUE);
        g2d.draw(new CubicCurve2D.Double(220, 200, 300, 250, 380, 150, 480, 200));

        // General Path (Custom Shape)
        GeneralPath path = new GeneralPath();
        path.moveTo(20, 300);
        path.lineTo(100, 350);
        path.curveTo(150, 400, 200, 250, 250, 300);
        path.closePath();
        g2d.setColor(Color.GREEN);
        g2d.draw(path);
        g2d.setColor(Color.YELLOW);
        g2d.fill(path);

        // Line using drawLine
        g2d.setColor(Color.BLUE);
        g2d.drawLine(20, 350, 200, 350);

        // Oval using drawOval and fillOval
        g2d.setColor(Color.MAGENTA);
        g2d.drawOval(250, 350, 80, 50);
        g2d.setColor(Color.CYAN);
        g2d.fillOval(350, 350, 80, 50);

        // Rectangle using drawRect and fillRect
        g2d.setColor(Color.ORANGE);
        g2d.drawRect(450, 350, 80, 50);
        g2d.setColor(Color.PINK);
        g2d.fillRect(550, 350, 80, 50);

        // Text
        g2d.setColor(Color.BLACK);
        g2d.drawString("Text Example", 20, 450);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Extended Shapes Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(700, 500);
        frame.add(new ExtendedShapesExample());
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
