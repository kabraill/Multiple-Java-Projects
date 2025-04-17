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

import java.awt.*;
import java.io.File;

public class NativeFileChooserExample {
    public static void main(String[] args) {
        // Create a Frame (needed for the dialog)
        Frame frame = new Frame();
        frame.setVisible(true);

        // Create the FileDialog instance
        FileDialog fileDialog = new FileDialog(frame, "Select Files", FileDialog.LOAD);

        // Enable multiple file selection
        fileDialog.setMultipleMode(true);

        // Show the dialog
        fileDialog.setVisible(true);

        // Get the selected files
        File[] files = fileDialog.getFiles();

        // Process the selected files
        for (File file : files) {
            System.out.println("Selected file: " + file.getAbsolutePath());
        }

        // Dispose of the frame
        frame.dispose();
    }
}
