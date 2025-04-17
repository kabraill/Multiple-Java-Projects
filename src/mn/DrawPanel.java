package mn;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

public class DrawPanel extends JPanel implements MouseListener, MouseMotionListener, ComponentListener {

    private int startX = 0;
    private int startY = 0;
    private int currentX = 0;
    private int currentY = 0;
    private boolean leftButtonPressed = false;
    private boolean no_move_draw = false;
    public BufferedImage image = null;
    private String[] draw_tool = {"none"};
    private Color customColor = new Color(0, 0, 0);
    private double scale = 1.0;
    private boolean showGrid = false; // Flag to toggle gridlines

    public DrawPanel() {
        addMouseListener(this);
        addMouseMotionListener(this);
        addComponentListener(this);
        addMouseWheelListener(new MouseWheelListener() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                if (e.getWheelRotation() < 0) {
                    scale += 0.1;
                } else {
                    scale -= 0.1;
                    if (scale < 0.1) {
                        scale = 0.1;
                    }
                }
                revalidate();
                repaint();
            }
        });
    }

    public DrawPanel(String[] draw_tool) {
        this();
        this.draw_tool = draw_tool;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
        repaint();
    }

    public BufferedImage getImage() {
        return this.image;
    }

    public void setDrawTool(String[] draw_tool) {
        this.draw_tool = draw_tool;
    }

    public void setShowGrid(boolean showGrid) {
        this.showGrid = showGrid;
        repaint();
    }

    private void drawImagee(Graphics2D g2d, BufferedImage img, int x, int y, int width, int height) {
        int imgWidth = img.getWidth();
        int imgHeight = img.getHeight();
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                int srcX = (int) ((double) i / width * imgWidth);
                int srcY = (int) ((double) j / height * imgHeight);
                int rgb = img.getRGB(srcX, srcY);
                g2d.setColor(new Color(rgb, true));
                g2d.fillRect(x + i, y + j, 1, 1);
            }
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);

        // Draw the scaled image
        if (image != null) {
            int w = (int) (image.getWidth() * scale);
            int h = (int) (image.getHeight() * scale);
            g2d.drawImage(image, 0, 0, w, h, this);
        }

        // Draw user interactions
        if (leftButtonPressed) {
            g2d.setColor(customColor);
            g2d.setStroke(new BasicStroke((float) (1 * scale)));

            int scaledStartX = (int) (startX * scale);
            int scaledStartY = (int) (startY * scale);
            int scaledCurrentX = (int) (currentX * scale);
            int scaledCurrentY = (int) (currentY * scale);
            if (draw_tool[0].equals("line")) {
                g2d.drawLine(scaledStartX, scaledStartY, scaledCurrentX, scaledCurrentY);
            } else if (draw_tool[0].equals("pen")) {
                Graphics2D pen_redraw = image.createGraphics();
                pen_redraw.setColor(customColor);
                pen_redraw.setStroke(new BasicStroke(10, BasicStroke.CAP_ROUND, BasicStroke.CAP_ROUND));
                pen_redraw.drawLine(startX, startY, currentX, currentY);
                pen_redraw.dispose();
                startX = currentX;
                startY = currentY;
            } else if (draw_tool[0].equals("rectangle")) {
                g2d.drawRect(Math.min(scaledStartX, scaledCurrentX), Math.min(scaledStartY, scaledCurrentY),
                        Math.abs(scaledStartX - scaledCurrentX), Math.abs(scaledStartY - scaledCurrentY));
            } else if (draw_tool[0].equals("round_rectangle")) {
                g2d.drawRoundRect(Math.min(scaledStartX, scaledCurrentX), Math.min(scaledStartY, scaledCurrentY),
                        Math.abs(scaledStartX - scaledCurrentX), Math.abs(scaledStartY - scaledCurrentY),
                        Math.abs(scaledStartX - scaledCurrentX) / 2, Math.abs(scaledStartY - scaledCurrentY) / 2);
            } else if (draw_tool[0].equals("circle")) {
                g2d.drawOval(Math.min(scaledStartX, scaledCurrentX), Math.min(scaledStartY, scaledCurrentY),
                        Math.abs(scaledStartX - scaledCurrentX), Math.abs(scaledStartY - scaledCurrentY));
            } else if (draw_tool[0].equals("point")) {
                int color = customColor.getRGB();
                image.setRGB(startX, startY, color);
                startX = currentX;
                startY = currentY;
            }
        }

        // Draw the gridlines
        if (showGrid) {
            drawGrid(g2d);
        }
        
        
    }

    private void drawGrid(Graphics2D g2d) {
        g2d.setColor(Color.LIGHT_GRAY);
        int gridSize = 20;
        int width = getWidth();
        int height = getHeight();

        Stroke originalStroke = g2d.getStroke();
        g2d.setStroke(new BasicStroke(1));

        for (int i = 0; i < width; i += gridSize) {
            int x = (int) (i);
            g2d.drawLine(x, 0, x, height);
        }

        for (int i = 0; i < height; i += gridSize) {
            int y = (int) (i);
            g2d.drawLine(0, y, width, y);
        }

        g2d.setStroke(originalStroke);
    }

    @Override
    public Dimension getPreferredSize() {
        if (image != null) {
            int w = (int) (image.getWidth() * scale);
            int h = (int) (image.getHeight() * scale);
            return new Dimension(w, h);
        } else {
            return super.getPreferredSize();
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (image == null) {
            image = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
        }
        if (e.getButton() == MouseEvent.BUTTON1) {
            startX = (int) (e.getX() / scale);
            startY = (int) (e.getY() / scale);
            leftButtonPressed = true;
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (leftButtonPressed) {
            currentX = (int) (e.getX() / scale);
            currentY = (int) (e.getY() / scale);
            no_move_draw = true;
            repaint();
            
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            leftButtonPressed = false;
            if (no_move_draw) {
                Graphics2D g2d = image.createGraphics();
                g2d.setColor(customColor);
                g2d.setStroke(new BasicStroke(10));
                if (draw_tool[0].equals("line")) {
                    g2d.drawLine(startX, startY, currentX, currentY);
                } else if (draw_tool[0].equals("rectangle")) {
                    g2d.drawRect(Math.min(startX, currentX), Math.min(startY, currentY),
                            Math.abs(startX - currentX), Math.abs(startY - currentY));
                } else if (draw_tool[0].equals("round_rectangle")) {
                    g2d.drawRoundRect(Math.min(startX, currentX), Math.min(startY, currentY),
                            Math.abs(startX - currentX), Math.abs(startY - currentY),
                            Math.abs(startX - currentX) / 2, Math.abs(startY - currentY) / 2);
                } else if (draw_tool[0].equals("circle")) {
                    g2d.drawOval(Math.min(startX, currentX), Math.min(startY, currentY),
                            Math.abs(startX - currentX), Math.abs(startY - currentY));
                }
                g2d.dispose();
                no_move_draw = false;
                //repaint();
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void mouseMoved(MouseEvent e) {
    }

    @Override
    public void componentResized(ComponentEvent e) {
    }

    @Override
    public void componentMoved(ComponentEvent e) {
    }

    @Override
    public void componentShown(ComponentEvent e) {
    }

    @Override
    public void componentHidden(ComponentEvent e) {
    }
}


