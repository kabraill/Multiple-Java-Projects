/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package draw_test;

/**
 *
 * @author kabra
 */
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class DrawingFrame extends JFrame {
    private JButton penButton, lineButton;
    private DrawingPanel drawingPanel;
    private boolean penDown = false;
    private boolean lineDown = false;

    public DrawingFrame() {
        setTitle("Drawing Program");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        penButton = new JButton("Pen");
        penButton.addActionListener(new PenButtonListener());
        lineButton = new JButton("Line");
        lineButton.addActionListener(new LineButtonListener());

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(penButton);
        buttonPanel.add(lineButton);
        add(buttonPanel, BorderLayout.NORTH);

        drawingPanel = new DrawingPanel();
        drawingPanel.addMouseMotionListener(new DrawingPanelListener());
        add(drawingPanel, BorderLayout.CENTER);
    }

    private class PenButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            penDown = true;
            lineDown = false;
        }
    }

    private class LineButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            penDown = false;
            lineDown = true;
        }
    }

    private class DrawingPanelListener implements MouseMotionListener {
        public void mouseDragged(MouseEvent e) {
            if (penDown) {
                drawingPanel.drawPen(e.getX(), e.getY());
            } else if (lineDown) {
                drawingPanel.drawLine(e.getX(), e.getY());
            }
        }

        public void mouseMoved(MouseEvent e) {
            // not used in this example
        }
    }

    public void save() {
        drawingPanel.saveImage();
    }

    public static void main(String[] args) {
        DrawingFrame frame = new DrawingFrame();
        frame.setVisible(true);
    }
}

