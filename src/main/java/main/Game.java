package main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class Game extends JPanel implements ActionListener  {
    final int FPS = 60;
    int score = 0;
    Timer updater;
    Long delay1 = new Date().getTime();
    boolean keyPressed = false;

    final double SCALE = 100;
    private final Set<Integer> pressedKeys = new HashSet<>();
    Color[][][] field = new Color[20][8][8];
    Point4D gameSetRot = new Point4D(-45, 135, 0);
    Point4D gameRot = new Point4D(gameSetRot);
    Point4D tetPos = new Point4D(0, -(field.length/2. - 1)*SCALE, 0);
    Point4D tetRot = new Point4D(0, 0, 0);
    Tetromino current = Tetromino.rand(SCALE);

    Viewer3D viewer3D;

    public Game() {
        super(new BorderLayout());
        MyListener listener = new MyListener();
        addMouseListener(listener);
        addMouseMotionListener(listener);
        addKeyListener(listener);
        for (int i = 0; i < field[0].length; i++) {
            for (int j = 0; j < field.length; j++) {
                for (int k = 0; k < field[0][0].length; k++) {
                    field[j][i][k] = Color.BLACK;
                }
            }
        }
        viewer3D = new Viewer3D(this);
        this.add(viewer3D, BorderLayout.CENTER);
        updater = new Timer(1000/FPS, this);
        updater.start();
    }

    boolean testFieldPos(Point4D pos) {
        Matrix transform = Matrix.move(tetPos.getX(), tetPos.getY(), tetPos.getZ()).multiply(Matrix.rotX(tetRot.getX()).multiply(Matrix.rotY(tetRot.getY())));
        for (Cube cube : current.cubes) {
            Point4D newRot = transform.multiplyByPoint4D(cube.getPosition());
            int x = (int) (Math.round(pos.getX() + newRot.getX()) / SCALE + field[0].length/2 - 1);
            int y = (int) (Math.round(pos.getY() + newRot.getY()) / SCALE + field.length/2 - 1);
            int z = (int) (Math.round(pos.getZ() + newRot.getZ()) / SCALE + field[0][0].length/2 - 1);
            if (x < 0 || x > field[0].length - 1 || y < 0 || y > field.length - 1 || z < 0 || z > field[0][0].length - 1 || field[y][x][z] != Color.BLACK)
                return false;
        }
        return true;
    }

    boolean testFieldRot(Point4D rot) {
        Matrix transform = Matrix.move(tetPos.getX(), tetPos.getY(), tetPos.getZ()).multiply(Matrix.rotX(tetRot.getX() + rot.getX()).multiply(Matrix.rotY(tetRot.getY() + rot.getY()).multiply(Matrix.rotZ(tetRot.getZ() + rot.getZ()))));
        for (Cube cube : current.cubes) {
            Point4D newRot = transform.multiplyByPoint4D(cube.getPosition());
            int x = (int) (Math.round(newRot.getX()) / SCALE + field[0].length/2 - 1);
            int y = (int) (Math.round(newRot.getY()) / SCALE + field.length/2 - 1);
            int z = (int) (Math.round(newRot.getZ()) / SCALE + field[0][0].length/2 - 1);
            if (x < 0 || x > field[0].length - 1 || y < 0 || y > field.length - 1 || z < 0 || z > field[0][0].length - 1 || field[y][x][z] != Color.BLACK)
                return false;
        }
        return true;
    }

    private void updateKeys() {
        if (!pressedKeys.isEmpty()) {
            for (Integer pressedKey : pressedKeys) {
                switch (pressedKey) {
                    case KeyEvent.VK_UP:
                        if (testFieldRot(new Point4D(90, 0, 0)))
                            tetRot.add(90, 0, 0);
                        break;
                    case KeyEvent.VK_DOWN:
                        if (testFieldRot(new Point4D(-90, 0, 0)))
                            tetRot.add(-90, 0, 0);
                        break;
                    case KeyEvent.VK_LEFT:
                        if (testFieldRot(new Point4D(0, 90, 0)))
                            tetRot.add(0, 90, 0);
                        break;
                    case KeyEvent.VK_RIGHT:
                        if (testFieldRot(new Point4D(0, -90, 0)))
                            tetRot.add(0, -90, 0);
                        break;
                    case KeyEvent.VK_W:
                        if (testFieldPos(new Point4D(Math.round(Math.sin(Math.toRadians(gameRot.getY())))*SCALE, 0, -Math.round(Math.cos(Math.toRadians(gameRot.getY())))*SCALE)))
                            tetPos.add(Math.round(Math.sin(Math.toRadians(gameRot.getY())))*SCALE, 0, -Math.round(Math.cos(Math.toRadians(gameRot.getY())))*SCALE);
                        break;
                    case KeyEvent.VK_S:
                        if (testFieldPos(new Point4D(-Math.round(Math.sin(Math.toRadians(gameRot.getY())))*SCALE, 0, Math.round(Math.cos(Math.toRadians(gameRot.getY())))*SCALE)))
                            tetPos.add(-Math.round(Math.sin(Math.toRadians(gameRot.getY())))*SCALE, 0, Math.round(Math.cos(Math.toRadians(gameRot.getY())))*SCALE);
                        break;
                    case KeyEvent.VK_A:
                        if (testFieldPos(new Point4D(-Math.round(Math.cos(Math.toRadians(gameRot.getY())))*SCALE, 0, -Math.round(Math.sin(Math.toRadians(gameRot.getY())))*SCALE)))
                            tetPos.add(-Math.round(Math.cos(Math.toRadians(gameRot.getY())))*SCALE, 0, -Math.round(Math.sin(Math.toRadians(gameRot.getY())))*SCALE);
                        break;
                    case KeyEvent.VK_D:
                        if (testFieldPos(new Point4D(Math.round(Math.cos(Math.toRadians(gameRot.getY())))*SCALE, 0, Math.round(Math.sin(Math.toRadians(gameRot.getY())))*SCALE)))
                            tetPos.add(Math.round(Math.cos(Math.toRadians(gameRot.getY())))*SCALE, 0, Math.round(Math.sin(Math.toRadians(gameRot.getY())))*SCALE);
                        break;
                    case KeyEvent.VK_Z:
                        if (testFieldPos(new Point4D(0, SCALE, 0)))
                            tetPos.add(0, SCALE, 0);
                        break;
                }
            }
        }
    }

    void reset() {
        score+=100;
        Matrix transform = Matrix.move(tetPos.getX(), tetPos.getY(), tetPos.getZ()).multiply(Matrix.rotX(tetRot.getX()).multiply(Matrix.rotY(tetRot.getY()).multiply(Matrix.rotZ(tetRot.getZ()))));
        for (Cube cube : current.cubes) {
            Point4D newRot = transform.multiplyByPoint4D(cube.getPosition());
            field[(int) (Math.round(newRot.getY()) / SCALE + field.length/2 - 1)][(int) (Math.round(newRot.getX()) / SCALE + field[0].length/2 - 1)][(int) (Math.round(newRot.getZ()) / SCALE + field[0][0].length/2 - 1)] = current.color;
        }
        for (int i = 0; i < field.length; i++) {
            int sum = 0;
            over:
            for (int j = 0; j < field[0].length; j++) {
                for (int k = 0; k < field[0][0].length; k++) {
                    if (field[i][j][k] == Color.BLACK)
                        break over;
                    sum++;
                }
            }
            if (sum == field[0].length * field[0][0].length) {
                score+=1000;
                System.arraycopy(field, 0, field, 1, i);
                for (int j = 0; j < field[0].length; j++) {
                    for (int k = 0; k < field[0][0].length; k++) {
                        field[0][j][k] = Color.BLACK;
                    }
                }
            }
        }
        tetPos = new Point4D(0, -(field.length/2. - 1)*SCALE, 0);
        tetRot = new Point4D(0, 0, 0);
        current = Tetromino.rand(SCALE);
        if (!testFieldPos(new Point4D(0, 0, 0))) {
            score = 0;
            for (int i = 0; i < field[0].length; i++) {
                for (int j = 0; j < field.length; j++) {
                    for (int k = 0; k < field[0][0].length; k++) {
                        field[j][i][k] = Color.BLACK;
                    }
                }
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (keyPressed) {
            keyPressed = false;
            updateKeys();
        }
        if (new Date().getTime() - delay1 > 1000) {
            delay1 = new Date().getTime();
            updateField();
        }
        viewer3D.updateComponent();
    }

    private void updateField() {
        if (testFieldPos(new Point4D(0, SCALE, 0)))
            tetPos.add(0, SCALE, 0);
        else
            reset();
    }

    class MyListener extends MouseAdapter implements KeyListener {
        int oldX = 0;
        int oldY = 0;

        public void mousePressed(MouseEvent e) {
            oldX = -e.getY();
            oldY = e.getX();
        }

        public void mouseDragged(MouseEvent e) {
            gameRot.x = Math.min(Math.max(gameSetRot.getX() - e.getY() - oldX,-90),90);
            gameRot.y = gameSetRot.getY() + e.getX() - oldY;
        }

        public void mouseReleased(MouseEvent e) {
            gameSetRot.x = gameRot.getX();
            gameSetRot.y = gameRot.getY();
        }

        @Override
        public void keyTyped(KeyEvent e) { }

        @Override
        public void keyPressed(KeyEvent e) {
            pressedKeys.add(e.getKeyCode());
            keyPressed = true;
        }

        @Override
        public void keyReleased(KeyEvent e) {
            pressedKeys.remove(e.getKeyCode());
        }
    }
}
