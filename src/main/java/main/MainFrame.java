package main;

import javax.swing.*;
import java.awt.*;

public class MainFrame {
    JFrame frame;


    public static void main(String[] args) {
        MainFrame mainFrame = new MainFrame();
        mainFrame.display();
    }

    void display() {
        frame = new JFrame("3D test");
        frame.setSize(500, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        Viewer3D viewer3D = new Viewer3D();
        viewer3D.setFocusable(true);
        viewer3D.grabFocus();

        frame.add(viewer3D, BorderLayout.CENTER);

        frame.setVisible(true);
    }
}
