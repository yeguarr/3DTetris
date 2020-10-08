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
        frame = new JFrame("3D Tetris");
        frame.setSize(500, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        Game game = new Game();
        game.setFocusable(true);
        game.grabFocus();

        frame.add(game, BorderLayout.CENTER);

        frame.setVisible(true);
    }
}
