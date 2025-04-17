/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package draw_test;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class DrawRectangle extends JPanel implements MouseMotionListener {
    private int x1, y1, x2, y2;

    public DrawRectangle() {
        addMouseMotionListener(this);
    }

    public void mouseDragged(MouseEvent e) {
        x2 = e.getX();
        y2 = e.getY();
        repaint();
    }

    public void mouseMoved(MouseEvent e) {
        x1 = e.getX();
        y1 = e.getY();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawRect(Math.min(x1, x2), Math.min(y1, y2), Math.abs(x1 - x2), Math.abs(y1 - y2));
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setContentPane(new DrawRectangle());
        frame.setSize(300, 300);
        frame.setVisible(true);
    }
}
