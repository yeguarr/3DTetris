package main;

import javax.swing.*;
import java.awt.*;

public class Game {
    final int FPS = 60;
    int score = 0;
    JFrame frame;

    void display() {
        frame = new JFrame("3D Tetris");
        frame.setSize(500, 800);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        Viewer3D viewer3D = new Viewer3D();
        viewer3D.setFocusable(true);
        viewer3D.grabFocus();

        frame.add(viewer3D, BorderLayout.CENTER);

        frame.setVisible(true);
    }
}
