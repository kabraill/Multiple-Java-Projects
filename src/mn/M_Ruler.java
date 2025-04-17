/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mn;

/**
 *
 * @author kabra
 */
import javax.swing.*;
import java.awt.*;

public class M_Ruler {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Main Application");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        JTabbedPane tabbedPane = new JTabbedPane();
        frame.add(tabbedPane);

        // Adding new tab with scroll pane and ruler
        addNewImageTab(tabbedPane);

        frame.setVisible(true);
    }

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("Main Application");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        JTabbedPane tabbedPane = new JTabbedPane();
        frame.add(tabbedPane);

        // Adding new tab with scroll pane and ruler
        addNewImageTab(tabbedPane);

        frame.setVisible(true);
    }

    private static void addNewImageTab(JTabbedPane tabbedPane) {
        // Create innerPanel and outerPanel
        JPanel innerPanel = new JPanel();
        innerPanel.setPreferredSize(new Dimension(1000, 1000));
        innerPanel.setBackground(Color.WHITE);

        // Create JScrollPane containing innerPanel
        JScrollPane scrollPane = new JScrollPane(innerPanel);

        // Create the horizontal and vertical rulers
        RulerComponent horizontalRuler = new RulerComponent(RulerComponent.HORIZONTAL);
        RulerComponent verticalRuler = new RulerComponent(RulerComponent.VERTICAL);

        // Create a corner component to fill the top-left corner
        JPanel cornerPanel = new JPanel();
        cornerPanel.setPreferredSize(new Dimension(30, 30));
        cornerPanel.setBackground(Color.LIGHT_GRAY);

        // Create a panel to hold the rulers and the scroll pane
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.add(horizontalRuler, BorderLayout.NORTH);
        contentPanel.add(verticalRuler, BorderLayout.WEST);
        contentPanel.add(scrollPane, BorderLayout.CENTER);
        

        // Add the contentPanel to the tabbedPane
        tabbedPane.addTab("New Image", contentPanel);
    }
}




