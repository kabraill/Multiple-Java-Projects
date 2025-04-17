/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mn;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import javax.swing.JPanel;

/**
 *
 * @author kabra
 */
public class RulerComponent extends JPanel {

    public static final int HORIZONTAL = 0;
    public static final int VERTICAL = 1;
    private static final int SIZE = 30;
    private final int orientation;

    public RulerComponent(int orientation) {
        this.orientation = orientation;
        System.err.println("drrd");
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.BLACK);

        if (orientation == HORIZONTAL) {
            for (int i = 0; i < getWidth(); i += 10) {
                if (i % 100 == 0) {//size       size
                    g2d.drawLine(i + SIZE, 0, i + SIZE, SIZE);//size
                    g2d.drawString(String.valueOf(i ), i + 2 + SIZE, (SIZE / 2) + 10);
                } else if (i % 50 == 0) {
                    //size       size
                    g2d.drawLine(i + SIZE, 0, i + SIZE, (int) (SIZE / 2.5));
                } else {           //size       size
                    g2d.drawLine(i + SIZE, 0, i + SIZE, SIZE / 4);
                }
            }

        } else {
            for (int i = 0; i < getHeight(); i += 10) {
                if (i % 100 == 0) {

                    
                    // Calculating number of digits
                    int y =  i;
                    int n = 0;
                    if (i == 0) {
                        n = 1;
                    }
                    while (y != 0) {
                        y /= 10;
                        n++;
                    }
                    
                    g2d.drawLine(0, i, SIZE, i);
                    // Save the original transform
                    AffineTransform original = g2d.getTransform();
                    // Rotate 90 degrees around the point (SIZE / 2, i + 15)
                    g2d.rotate(-Math.PI / 2, SIZE / 2 + 12, i + Math.ceil(7.5 * n));
                    // Draw the string
                    g2d.drawString(String.valueOf(i), SIZE / 2 + 12, (int) (i + Math.ceil(7.5 * n)));
                    // Restore the original transform
                    g2d.setTransform(original);
                } else if (i % 50 == 0) {
                    g2d.drawLine(0, i, SIZE / 2, i);
                } else {
                    g2d.drawLine(0, i, SIZE / 4, i);
                }
            }
        }
    }

//    for (int i = 0; i < getHeight(); i += 10) {
//                if (i % 100 == 0) {
//                    
//                    int q = i * 1000;
//                    // Calculating number of digits
// int y = q;
// int n = 0;
// while (y != 0) {
// y /= 10;
// ++n;
// }
//                    
//                    g2d.drawLine(0, i, SIZE, i);
//                    // Save the original transform
//                    AffineTransform original = g2d.getTransform();
//                    // Rotate 90 degrees around the point (SIZE / 2, i + 15)
//                    g2d.rotate(-Math.PI / 2, SIZE / 2, q + Math.ceil(7.5 * n));
//                    // Draw the string
//                    g2d.drawString(String.valueOf(q), SIZE / 2, (int) (q + Math.ceil(7.5 * n)));
//                    // Restore the original transform
//                    g2d.setTransform(original);
//                } else if (i % 50 == 0) {
//                    g2d.drawLine(0, i, SIZE / 2, i);
//                } else {
//                    g2d.drawLine(0, i, SIZE / 4, i);
//                }
//            }
    @Override
    public Dimension getPreferredSize() {
        return orientation == HORIZONTAL ? new Dimension(0, SIZE) : new Dimension(SIZE, 0);
    }
}
