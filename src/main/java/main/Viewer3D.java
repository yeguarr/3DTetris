package main;

import javax.swing.*;
import java.awt.*;
import java.util.*;

public class Viewer3D extends JComponent {
    java.util.List<Shape> shapes = new LinkedList<>();
    java.util.List<Shape> drawShapes = new LinkedList<>();
    Game game;

    public Viewer3D(Game game) {
        this.game = game;
    }

    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2d.setPaint(Color.BLACK);
        g2d.drawString("Score: " + game.score, 30,30);

        drawShapes.clear();
        double scale = (1.3 * Math.min(this.getBounds().width , this.getBounds().height))/Math.max(Math.max(game.field.length - 1,game.field[0].length - 1),game.field[0][0].length - 1)/game.SCALE/2;
        Matrix transform = Matrix.move(this.getBounds().width / 2., this.getBounds().height / 2., 0).multiply(Matrix.scale(scale, scale, scale).multiply(Matrix.rotX(game.gameRot.getX()).multiply(Matrix.rotY(game.gameRot.getY()).multiply(Matrix.rotZ(game.gameRot.getZ()).multiply(Matrix.move(-game.SCALE/2, -game.SCALE/2, -game.SCALE/2))))));
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

    public void updateComponent() {
        shapes.clear();
        shapes.add(game.current.getTransformed(Matrix.move(game.tetPos.getX() + game.SCALE / 2, game.tetPos.getY() + game.SCALE / 2, game.tetPos.getZ() + game.SCALE / 2).multiply(Matrix.rotX(game.tetRot.getX()).multiply(Matrix.rotY(game.tetRot.getY()).multiply(Matrix.move(-game.SCALE / 2, -game.SCALE / 2, -game.SCALE / 2))))));
        for (int k = -game.field[0][0].length / 2; k < game.field[0][0].length / 2; k++)
            shapes.add(new Line(new Point4D((game.field[0].length / 2. + 1) * game.SCALE + 1, (game.field.length / 2. + 1) * game.SCALE + 1, (k + 1) * game.SCALE), new Point4D((game.field[0].length / 2. + 1) * game.SCALE + 1, (game.field.length / 2. + 1) * game.SCALE + 1, (k + 2) * game.SCALE), Color.BLUE));
        for (int j = -game.field.length / 2; j < game.field.length / 2; j++) {
            shapes.add(new Line(new Point4D((game.field[0].length / 2. + 1) * game.SCALE + 1, (j + 1) * game.SCALE, (game.field[0][0].length / 2. + 1) * game.SCALE + 1), new Point4D((game.field[0].length / 2. + 1) * game.SCALE + 1, (j + 2) * game.SCALE, (game.field[0][0].length / 2. + 1) * game.SCALE + 1), Color.GREEN));
            for (int k = -game.field[0][0].length / 2; k < game.field[0][0].length / 2; k++) {
                shapes.add(new Square(new Point4D(Math.copySign(1, Math.sin(Math.toRadians(game.gameRot.getY()))) * (game.field[0].length * game.SCALE / 2 + 1) + game.SCALE, (j + 1) * game.SCALE, (k + 1) * game.SCALE),
                        new Point4D(Math.copySign(1, Math.sin(Math.toRadians(game.gameRot.getY()))) * (game.field[0].length * game.SCALE / 2 + 1) + game.SCALE, (j + 2) * game.SCALE, (k + 1) * game.SCALE),
                        new Point4D(Math.copySign(1, Math.sin(Math.toRadians(game.gameRot.getY()))) * (game.field[0].length * game.SCALE / 2 + 1) + game.SCALE, (j + 2) * game.SCALE, (k + 2) * game.SCALE),
                        new Point4D(Math.copySign(1, Math.sin(Math.toRadians(game.gameRot.getY()))) * (game.field[0].length * game.SCALE / 2 + 1) + game.SCALE, (j + 1) * game.SCALE, (k + 2) * game.SCALE), new Color(200, 200, 200, 128)));
            }
        }
        for (int i = -game.field[0].length / 2; i < game.field[0].length / 2; i++) {
            shapes.add(new Line(new Point4D((i + 1) * game.SCALE, (game.field.length / 2. + 1) * game.SCALE + 1, (game.field[0][0].length / 2. + 1) * game.SCALE + 1), new Point4D((i + 2) * game.SCALE, (game.field.length / 2. + 1) * game.SCALE + 1, (game.field[0][0].length / 2. + 1) * game.SCALE + 1), Color.RED));
            for (int k = -game.field[0][0].length / 2; k < game.field[0][0].length / 2; k++) {
                shapes.add(new Square(new Point4D((i + 1) * game.SCALE, -Math.copySign(1, Math.sin(Math.toRadians(game.gameRot.getX()))) * (game.field.length * game.SCALE / 2 + 1) + game.SCALE, (k + 1) * game.SCALE),
                        new Point4D((i + 2) * game.SCALE, -Math.copySign(1, Math.sin(Math.toRadians(game.gameRot.getX()))) * (game.field.length * game.SCALE / 2 + 1) + game.SCALE, (k + 1) * game.SCALE),
                        new Point4D((i + 2) * game.SCALE, -Math.copySign(1, Math.sin(Math.toRadians(game.gameRot.getX()))) * (game.field.length * game.SCALE / 2 + 1) + game.SCALE, (k + 2) * game.SCALE),
                        new Point4D((i + 1) * game.SCALE, -Math.copySign(1, Math.sin(Math.toRadians(game.gameRot.getX()))) * (game.field.length * game.SCALE / 2 + 1) + game.SCALE, (k + 2) * game.SCALE), new Color(200, 200, 200, 128)));
            }
            for (int j = -game.field.length / 2; j < game.field.length / 2; j++) {
                shapes.add(new Square(new Point4D((i + 1) * game.SCALE, (j + 1) * game.SCALE, -Math.copySign(1, Math.cos(Math.toRadians(game.gameRot.getY()))) * (game.field[0][0].length * game.SCALE / 2 + 1) + game.SCALE),
                        new Point4D((i + 2) * game.SCALE, (j + 1) * game.SCALE, -Math.copySign(1, Math.cos(Math.toRadians(game.gameRot.getY()))) * (game.field[0][0].length * game.SCALE / 2 + 1) + game.SCALE),
                        new Point4D((i + 2) * game.SCALE, (j + 2) * game.SCALE, -Math.copySign(1, Math.cos(Math.toRadians(game.gameRot.getY()))) * (game.field[0][0].length * game.SCALE / 2 + 1) + game.SCALE),
                        new Point4D((i + 1) * game.SCALE, (j + 2) * game.SCALE, -Math.copySign(1, Math.cos(Math.toRadians(game.gameRot.getY()))) * (game.field[0][0].length * game.SCALE / 2 + 1) + game.SCALE), new Color(200, 200, 200, 128)));
                for (int k = -game.field[0][0].length / 2; k < game.field[0][0].length / 2; k++) {
                    if (game.field[j + game.field.length / 2][i + game.field[0].length / 2][k + game.field[0][0].length / 2] != Color.BLACK) {
                        shapes.add(new Cube(new Point4D((i + 1) * game.SCALE, (j + 1) * game.SCALE, (k + 1) * game.SCALE), new Point4D((i + 2) * game.SCALE, (j + 2) * game.SCALE, (k + 2) * game.SCALE), game.field[j + game.field.length / 2][i + game.field[0].length / 2][k + game.field[0][0].length / 2]));
                    }
                }
            }
        }
        repaint();
    }
}
