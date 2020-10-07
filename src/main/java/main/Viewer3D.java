package main;

import javax.swing.Timer;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class Viewer3D extends JComponent implements ActionListener {
    final double SCALE = 100;
    final int FPS = 60;
    double setRX = -45;
    double setRY = 135;
    double rX = setRX;
    double rY = setRY;
    double rZ = 0;
    int score = 0;

    Color[][][] field = new Color[20][8][8];

    Point4D tetPos = new Point4D(0, -(field.length/2. - 1)*SCALE, 0);
    Point4D tetRot = new Point4D(0, 0, 0);
    Tetromino current = Tetromino.rand(SCALE);

    java.util.List<Shape> shapes = new LinkedList<>();
    java.util.List<Shape> drawShapes = new LinkedList<>();
    Timer t;
    Long delay = new Date().getTime();
    boolean testis = true;

    public Viewer3D() {
        for (int i = 0; i < field[0].length; i++) {
            for (int j = 0; j < field.length; j++) {
                for (int k = 0; k < field[0][0].length; k++) {
                    field[j][i][k] = Color.BLACK;
                }
            }
        }
        MyMouseListener listener = new MyMouseListener();
        addMouseListener(listener);
        addMouseMotionListener(listener);
        addKeyListener(new KeyAdapter() {
            private final Set<Integer> pressedKeys = new HashSet<>();

            @Override
            public void keyPressed(KeyEvent e) {
                pressedKeys.add(e.getKeyCode());
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
                                if (testFieldPos(new Point4D(Math.round(Math.sin(Math.toRadians(rY)))*SCALE, 0, -Math.round(Math.cos(Math.toRadians(rY)))*SCALE)))
                                    tetPos.add(Math.round(Math.sin(Math.toRadians(rY)))*SCALE, 0, -Math.round(Math.cos(Math.toRadians(rY)))*SCALE);
                                break;
                            case KeyEvent.VK_S:
                                if (testFieldPos(new Point4D(-Math.round(Math.sin(Math.toRadians(rY)))*SCALE, 0, Math.round(Math.cos(Math.toRadians(rY)))*SCALE)))
                                    tetPos.add(-Math.round(Math.sin(Math.toRadians(rY)))*SCALE, 0, Math.round(Math.cos(Math.toRadians(rY)))*SCALE);
                                break;
                            case KeyEvent.VK_A:
                                if (testFieldPos(new Point4D(-Math.round(Math.cos(Math.toRadians(rY)))*SCALE, 0, -Math.round(Math.sin(Math.toRadians(rY)))*SCALE)))
                                    tetPos.add(-Math.round(Math.cos(Math.toRadians(rY)))*SCALE, 0, -Math.round(Math.sin(Math.toRadians(rY)))*SCALE);
                                break;
                            case KeyEvent.VK_D:
                                if (testFieldPos(new Point4D(Math.round(Math.cos(Math.toRadians(rY)))*SCALE, 0, Math.round(Math.sin(Math.toRadians(rY)))*SCALE)))
                                    tetPos.add(Math.round(Math.cos(Math.toRadians(rY)))*SCALE, 0, Math.round(Math.sin(Math.toRadians(rY)))*SCALE);
                                break;
                            case KeyEvent.VK_Z:
                                if (testFieldPos(new Point4D(0, SCALE, 0)))
                                    tetPos.add(0, SCALE, 0);
                                break;
                            case KeyEvent.VK_V:
                                testis = !testis;
                                break;
                        }

                    }
                }
            }

            @Override
            public synchronized void keyReleased(KeyEvent e) {
                pressedKeys.remove(e.getKeyCode());
            }
        });
        actionPerformed(null);
        t = new Timer(1000/FPS, this);
        t.start();
    }

    boolean testFieldPos(Point4D pos) {
        Matrix transform = move(tetPos.getX(), tetPos.getY(), tetPos.getZ()).multiply(rotX(tetRot.getX()).multiply(rotY(tetRot.getY())));
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
        Matrix transform = move(tetPos.getX(), tetPos.getY(), tetPos.getZ()).multiply(rotX(tetRot.getX() + rot.getX()).multiply(rotY(tetRot.getY() + rot.getY()).multiply(rotZ(tetRot.getZ() + rot.getZ()))));
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

    void reset() {
        score+=100;
        Matrix transform = move(tetPos.getX(), tetPos.getY(), tetPos.getZ()).multiply(rotX(tetRot.getX()).multiply(rotY(tetRot.getY()).multiply(rotZ(tetRot.getZ()))));
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

    Matrix rotX(double angle) {
        return new Matrix(new double[][]{{1, 0, 0, 0},
                {0, Math.cos(Math.toRadians(angle)), -Math.sin(Math.toRadians(angle)), 0},
                {0, Math.sin(Math.toRadians(angle)), Math.cos(Math.toRadians(angle)), 0},
                {0, 0, 0, 1}});
    }

    Matrix rotY(double angle) {
        return new Matrix(new double[][]{{Math.cos(Math.toRadians(angle)), 0, Math.sin(Math.toRadians(angle)), 0},
                {0, 1, 0, 0},
                {-Math.sin(Math.toRadians(angle)), 0, Math.cos(Math.toRadians(angle)), 0},
                {0, 0, 0, 1}});
    }

    Matrix rotZ(double angle) {
        return new Matrix(new double[][]{{Math.cos(Math.toRadians(angle)), -Math.sin(Math.toRadians(angle)), 0, 0},
                {Math.sin(Math.toRadians(angle)), Math.cos(Math.toRadians(angle)), 0, 0},
                {0, 0, 1, 0},
                {0, 0, 0, 1}});
    }

    Matrix move(double x, double y, double z) {
        return new Matrix(new double[][]{{1, 0, 0, x},
                {0, 1, 0, y},
                {0, 0, 1, z},
                {0, 0, 0, 1}});
    }

    Matrix scale(double x, double y, double z) {
        return new Matrix(new double[][]{{x, 0, 0, 0},
                {0, y, 0, 0},
                {0, 0, z, 0},
                {0, 0, 0, 1}});
    }

    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2d.setPaint(Color.BLACK);
        g2d.drawString("Score: " + score, 30,30);

        drawShapes.clear();
        double scale = (1.3 * Math.min(this.getBounds().width , this.getBounds().height))/Math.max(Math.max(field.length - 1,field[0].length - 1),field[0][0].length - 1)/SCALE/2;
        Matrix transform = move(this.getBounds().width / 2., this.getBounds().height / 2., 0).multiply(scale(scale, scale, scale).multiply(rotX(rX).multiply(rotY(rY).multiply(rotZ(rZ).multiply(move(-SCALE/2, -SCALE/2, -SCALE/2))))));
        for (Shape shape : shapes) {
            shape.getTransformed(transform).addToList(drawShapes);
        }
        Collections.sort(drawShapes);
        g2d.setStroke(new BasicStroke(3));
        double max = drawShapes.stream().mapToDouble(Shape::getZCentre).max().orElse(1);
        double min = drawShapes.stream().mapToDouble(Shape::getZCentre).min().orElse(0);
        for (Shape shape : drawShapes) {
            if (!(shape instanceof Line))
                g2d.setPaint(new Color((int) (shape.color.getRed() * ((shape.getZCentre() - min) / (max - min) / 2 + 0.5)), (int) (shape.color.getGreen() * ((shape.getZCentre() - min) / (max - min) / 2 + 0.5)), (int) (shape.color.getBlue() * ((shape.getZCentre() - min) / (max - min) / 2 + 0.5)), shape.color.getAlpha()));
            else
                g2d.setPaint(shape.color);
            shape.draw(g2d);
        }
    }

    public void actionPerformed(ActionEvent e) {
        if (testis) {
            shapes.clear();
            if (new Date().getTime() - delay > 1000) {
                delay = new Date().getTime();
                if (testFieldPos(new Point4D(0, SCALE, 0)))
                    tetPos.add(0, SCALE, 0);
                else {
                    reset();
                }
            }
            shapes.add(current.getTransformed(move(tetPos.getX() + SCALE / 2, tetPos.getY() + SCALE / 2, tetPos.getZ() + SCALE / 2).multiply(rotX(tetRot.getX()).multiply(rotY(tetRot.getY()).multiply(move(-SCALE / 2, -SCALE / 2, -SCALE / 2))))));
            for (int k = -field[0][0].length / 2; k < field[0][0].length / 2; k++)
                shapes.add(new Line(new Point4D((field[0].length / 2. + 1) * SCALE + 1, (field.length / 2. + 1) * SCALE + 1, (k + 1) * SCALE), new Point4D((field[0].length / 2. + 1) * SCALE + 1, (field.length / 2. + 1) * SCALE + 1, (k + 2) * SCALE), Color.BLUE));
            for (int j = -field.length / 2; j < field.length / 2; j++) {
                shapes.add(new Line(new Point4D((field[0].length / 2. + 1) * SCALE + 1, (j + 1) * SCALE, (field[0][0].length / 2. + 1) * SCALE + 1), new Point4D((field[0].length / 2. + 1) * SCALE + 1, (j + 2) * SCALE, (field[0][0].length / 2. + 1) * SCALE + 1), Color.GREEN));
                for (int k = -field[0][0].length / 2; k < field[0][0].length / 2; k++) {
                    shapes.add(new Square(new Point4D(Math.copySign(1, Math.sin(Math.toRadians(rY))) * (field[0].length * SCALE / 2 + 1) + SCALE, (j + 1) * SCALE, (k + 1) * SCALE),
                            new Point4D(Math.copySign(1, Math.sin(Math.toRadians(rY))) * (field[0].length * SCALE / 2 + 1) + SCALE, (j + 2) * SCALE, (k + 1) * SCALE),
                            new Point4D(Math.copySign(1, Math.sin(Math.toRadians(rY))) * (field[0].length * SCALE / 2 + 1) + SCALE, (j + 2) * SCALE, (k + 2) * SCALE),
                            new Point4D(Math.copySign(1, Math.sin(Math.toRadians(rY))) * (field[0].length * SCALE / 2 + 1) + SCALE, (j + 1) * SCALE, (k + 2) * SCALE), new Color(200, 200, 200, 128)));
                }
            }
            for (int i = -field[0].length / 2; i < field[0].length / 2; i++) {
                shapes.add(new Line(new Point4D((i + 1) * SCALE, (field.length / 2. + 1) * SCALE + 1, (field[0][0].length / 2. + 1) * SCALE + 1), new Point4D((i + 2) * SCALE, (field.length / 2. + 1) * SCALE + 1, (field[0][0].length / 2. + 1) * SCALE + 1), Color.RED));
                for (int k = -field[0][0].length / 2; k < field[0][0].length / 2; k++) {
                    shapes.add(new Square(new Point4D((i + 1) * SCALE, -Math.copySign(1, Math.sin(Math.toRadians(rX))) * (field.length * SCALE / 2 + 1) + SCALE, (k + 1) * SCALE),
                            new Point4D((i + 2) * SCALE, -Math.copySign(1, Math.sin(Math.toRadians(rX))) * (field.length * SCALE / 2 + 1) + SCALE, (k + 1) * SCALE),
                            new Point4D((i + 2) * SCALE, -Math.copySign(1, Math.sin(Math.toRadians(rX))) * (field.length * SCALE / 2 + 1) + SCALE, (k + 2) * SCALE),
                            new Point4D((i + 1) * SCALE, -Math.copySign(1, Math.sin(Math.toRadians(rX))) * (field.length * SCALE / 2 + 1) + SCALE, (k + 2) * SCALE), new Color(200, 200, 200, 128)));
                }
                for (int j = -field.length / 2; j < field.length / 2; j++) {
                    shapes.add(new Square(new Point4D((i + 1) * SCALE, (j + 1) * SCALE, -Math.copySign(1, Math.cos(Math.toRadians(rY))) * (field[0][0].length * SCALE / 2 + 1) + SCALE),
                            new Point4D((i + 2) * SCALE, (j + 1) * SCALE, -Math.copySign(1, Math.cos(Math.toRadians(rY))) * (field[0][0].length * SCALE / 2 + 1) + SCALE),
                            new Point4D((i + 2) * SCALE, (j + 2) * SCALE, -Math.copySign(1, Math.cos(Math.toRadians(rY))) * (field[0][0].length * SCALE / 2 + 1) + SCALE),
                            new Point4D((i + 1) * SCALE, (j + 2) * SCALE, -Math.copySign(1, Math.cos(Math.toRadians(rY))) * (field[0][0].length * SCALE / 2 + 1) + SCALE), new Color(200, 200, 200, 128)));
                    for (int k = -field[0][0].length / 2; k < field[0][0].length / 2; k++) {
                        if (field[j + field.length / 2][i + field[0].length / 2][k + field[0][0].length / 2] != Color.BLACK) {
                            shapes.add(new Cube(new Point4D((i + 1) * SCALE, (j + 1) * SCALE, (k + 1) * SCALE), new Point4D((i + 2) * SCALE, (j + 2) * SCALE, (k + 2) * SCALE), field[j + field.length / 2][i + field[0].length / 2][k + field[0][0].length / 2]));
                        }
                    }
                }
            }
        }
        repaint();
    }

    class MyMouseListener extends MouseAdapter {
        int oldX = 0;
        int oldY = 0;

        public void mousePressed(MouseEvent e) {
            oldX = -e.getY();
            oldY = e.getX();
        }

        public void mouseDragged(MouseEvent e) {
            rX = Math.min(Math.max(setRX - e.getY() - oldX,-90),90);
            rY = setRY + e.getX() - oldY;
        }

        public void mouseReleased(MouseEvent e) {
            setRX = rX;
            setRY = rY;
        }
    }
}
