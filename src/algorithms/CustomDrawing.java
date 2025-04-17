package algorithms;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public class CustomDrawing extends JPanel {

    private BufferedImage canvas;
    private int strokeWidth;
    private int startX, startY, endX, endY;
    private boolean isDrawing;
    private String draw_tool;

    public CustomDrawing(int width, int height, int strokeWidth, String draw_tool) {
        canvas = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = canvas.createGraphics();
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, width, height);
        g2d.dispose();

        this.strokeWidth = strokeWidth;
        this.draw_tool = draw_tool;
        isDrawing = false;

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                startX = e.getX();
                startY = e.getY();
                isDrawing = true;
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (isDrawing) {
                    endX = e.getX();
                    endY = e.getY();
                    if (CustomDrawing.this.draw_tool.equals("custom_line")) {
                        plotLine(canvas, startX, startY, endX, endY, Color.BLACK);
                    } else if (CustomDrawing.this.draw_tool.equals("custom_oval")) {
                        drawOval(startX, startY, endX, endY, Color.BLACK);
                    }
                    isDrawing = false;
                    repaint();
                }
            }
        });

        addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (isDrawing) {
                    endX = e.getX();
                    endY = e.getY();
                    BufferedImage tempCanvas = new BufferedImage(canvas.getWidth(), canvas.getHeight(), canvas.getType());
                    Graphics2D g2d = tempCanvas.createGraphics();
                    g2d.drawImage(canvas, 0, 0, null);
                    if (CustomDrawing.this.draw_tool.equals("custom_line")) {
                        plotLine(tempCanvas, startX, startY, endX, endY, Color.BLACK);
                    } else if (CustomDrawing.this.draw_tool.equals("custom_oval")) {
                        drawOval(tempCanvas, startX, startY, endX, endY, Color.BLACK);
                    }
                    g2d.dispose();
                    repaint(tempCanvas);
                }
            }
        });
    }

    public void setDrawTool(String draw_tool) {
        this.draw_tool = draw_tool;
    }

    private void plotLine(BufferedImage canvas, int x0, int y0, int x1, int y1, Color color) {
        if (Math.abs(y1 - y0) < Math.abs(x1 - x0)) {
            if (x0 > x1) {
                plotLineLow(canvas, x1, y1, x0, y0, color);
            } else {
                plotLineLow(canvas, x0, y0, x1, y1, color);
            }
        } else {
            if (y0 > y1) {
                plotLineHigh(canvas, x1, y1, x0, y0, color);
            } else {
                plotLineHigh(canvas, x0, y0, x1, y1, color);
            }
        }
    }

    private void plotLineLow(BufferedImage canvas, int x0, int y0, int x1, int y1, Color color) {
        int dx = x1 - x0;
        int dy = y1 - y0;
        double angle = Math.atan2(dy, dx);
        int yi = 1;
        if (dy < 0) {
            yi = -1;
            dy = -dy;
        }
        int D = (2 * dy) - dx;
        int y = y0;
        for (int x = x0; x <= x1; x++) {
            setStrokePixels(canvas, x, y, angle, color);
            if (D > 0) {
                y += yi;
                D += 2 * (dy - dx);
            } else {
                D += 2 * dy;
            }
        }
    }

    private void plotLineHigh(BufferedImage canvas, int x0, int y0, int x1, int y1, Color color) {
        int dx = x1 - x0;
        int dy = y1 - y0;
        double angle = Math.atan2(dy, dx);
        int xi = 1;
        if (dx < 0) {
            xi = -1;
            dx = -dx;
        }
        int D = (2 * dx) - dy;
        int x = x0;
        for (int y = y0; y <= y1; y++) {
            setStrokePixels(canvas, x, y, angle, color);
            if (D > 0) {
                x += xi;
                D += 2 * (dx - dy);
            } else {
                D += 2 * dx;
            }
        }
    }

    private void setStrokePixels(BufferedImage canvas, int x, int y, double angle, Color color) {
        for (int i = -strokeWidth / 2; i <= strokeWidth / 2; i++) {
            for (int j = -strokeWidth / 2; j <= strokeWidth / 2; j++) {
                double[] rotatedPoint = rotatePoint(x + i, y + j, x, y, angle);
                int rx = (int) rotatedPoint[0];
                int ry = (int) rotatedPoint[1];
                if (rx >= 0 && rx < canvas.getWidth() && ry >= 0 && ry < canvas.getHeight()) {
                    canvas.setRGB(rx, ry, color.getRGB());
                }
            }
        }
    }

    public double[] rotatePoint(double px, double py, double ox, double oy, double theta) {
        double sinTheta = Math.sin(theta);
        double cosTheta = Math.cos(theta);
        double translatedX = px - ox;
        double translatedY = py - oy;
        double rotatedX = translatedX * cosTheta - translatedY * sinTheta;
        double rotatedY = translatedX * sinTheta + translatedY * cosTheta;
        return new double[]{rotatedX + ox, rotatedY + oy};
    }

    private void drawOval(int x0, int y0, int x1, int y1, Color color) {
        drawOval(canvas, x0, y0, x1, y1, color);
    }

    private void drawOval(BufferedImage canvas, int x0, int y0, int x1, int y1, Color color) {
        int radiusX = Math.abs(x1 - x0) / 2;
        int radiusY = Math.abs(y1 - y0) / 2;
        int centerX = (x0 + x1) / 2;
        int centerY = (y0 + y1) / 2;

        for (int i = -strokeWidth / 2; i <= strokeWidth / 2; i++) {
            for (int j = -strokeWidth / 2; j <= strokeWidth / 2; j++) {
                midPointEllipseDraw(canvas, centerX + i, centerY + j, radiusX, radiusY, color);
            }
        }
    }

    private void midPointEllipseDraw(BufferedImage canvas, int xCenter, int yCenter, int Rx, int Ry, Color color) {
        int Rx2 = Rx * Rx;
        int Ry2 = Ry * Ry;
        int twoRx2 = 2 * Rx2;
        int twoRy2 = 2 * Ry2;
        int p;
        int x = 0;
        int y = Ry;
        int px = 0;
        int py = twoRx2 * y;

        drawEllipsePoints(canvas, xCenter, yCenter, x, y, color);

        p = (int) Math.round(Ry2 - (Rx2 * Ry) + (0.25 * Rx2));
        while (px < py) {
            x++;
            px += twoRy2;
            if (p < 0) {
                p += Ry2 + px;
            } else {
                y--;
                py -= twoRx2;
                p += Ry2 + px - py;
            }
            drawEllipsePoints(canvas, xCenter, yCenter, x, y, color);
        }

        p = (int) Math.round(Ry2 * (x + 0.5) * (x + 0.5) + Rx2 * (y - 1) * (y - 1) - Rx2 * Ry2);
        while (y > 0) {
            y--;
            py -= twoRx2;
            if (p > 0) {
                p += Rx2 - py;
            } else {
                x++;
                px += twoRy2;
                p += Rx2 - py + px;
            }
            drawEllipsePoints(canvas, xCenter, yCenter, x, y, color);
        }
    }

    private void drawEllipsePoints(BufferedImage canvas, int xCenter, int yCenter, int x, int y, Color color) {
        int[][] points = {
            {x + xCenter, y + yCenter}, {-x + xCenter, y + yCenter},
            {x + xCenter, -y + yCenter}, {-x + xCenter, -y + yCenter}
        };
        for (int[] point : points) {
            int px = point[0];
            int py = point[1];
            if (px >= 0 && px < canvas.getWidth() && py >= 0 && py < canvas.getHeight()) {
                canvas.setRGB(px, py, color.getRGB());
            }
        }
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(canvas, 0, 0, this);
    }

    private void repaint(BufferedImage tempCanvas) {
        Graphics g = this.getGraphics();
        if (g != null) {
            g.drawImage(tempCanvas, 0, 0, this);
            g.dispose();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Custom Drawing with Stroke");
            CustomDrawing panel = new CustomDrawing(1300, 1300, 1, "custom_line");

            JButton lineButton = new JButton("Draw Line");
            JButton ovalButton = new JButton("Draw Oval");

            lineButton.addActionListener(e -> panel.setDrawTool("custom_line"));
            ovalButton.addActionListener(e -> panel.setDrawTool("custom_oval"));

            JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            buttonPanel.add(lineButton);
            buttonPanel.add(ovalButton);

            frame.setLayout(new BorderLayout());
            frame.add(buttonPanel, BorderLayout.NORTH);
            frame.add(panel, BorderLayout.CENTER);
            frame.setSize(800, 600);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
        });
    }
}
