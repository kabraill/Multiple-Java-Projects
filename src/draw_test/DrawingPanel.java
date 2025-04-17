/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package draw_test;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import javax.swing.JPanel;

/**
 *
 * @author kabra
 */
class DrawingPanel extends JPanel {
    private Image drawing;
    private Graphics2D g2;

    public DrawingPanel() {
        setBackground(Color.WHITE);
        // create an empty image for drawing
        drawing = createImage(getWidth(), getHeight());
        g2 = (Graphics2D) drawing.getGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
            RenderingHints.VALUE_ANTIALIAS_ON);
        clear();
    }

    public void drawPen(int x, int y) {
        // draw a line when pen is used
        g2.drawLine(x, y, x, y);
        repaint();
    }

    public void drawLine(int x, int y) {
        // draw a line when line tool is used
        // code for drawing a line would go here
    }

    public void clear() {
        // clear the drawing
        g2.setPaint(getBackground());
        g2.fillRect(0, 0, getWidth(), getHeight());
        g2.setPaint(Color.BLACK);
        repaint();
    }

    public void saveImage() {
        // save the current drawing
        // code for saving the image would go here
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(drawing, 0, 0, null);
    }
}

