package main;

import java.awt.*;
import java.util.Arrays;
import java.util.List;

public class Cube extends Shape {
    Square[] faces = new Square[6];

    public Cube(Point4D first, Point4D second) {
        faces[0] = new Square(new Point4D(first.getX(), first.getY(), first.getZ()), new Point4D(first.getX(), second.getY(), first.getZ()), new Point4D(second.getX(), second.getY(), first.getZ()), new Point4D(second.getX(), first.getY(), first.getZ()), (Color.RED));
        faces[1] = new Square(new Point4D(first.getX(), first.getY(), first.getZ()), new Point4D(first.getX(), first.getY(), second.getZ()), new Point4D(second.getX(), first.getY(), second.getZ()), new Point4D(second.getX(), first.getY(), first.getZ()), (Color.GREEN));
        faces[2] = new Square(new Point4D(second.getX(), first.getY(), first.getZ()), new Point4D(second.getX(), second.getY(), first.getZ()), new Point4D(second.getX(), second.getY(), second.getZ()), new Point4D(second.getX(), first.getY(), second.getZ()), (Color.YELLOW));
        faces[3] = new Square(new Point4D(first.getX(), second.getY(), first.getZ()), new Point4D(first.getX(), second.getY(), second.getZ()), new Point4D(second.getX(), second.getY(), second.getZ()), new Point4D(second.getX(), second.getY(), first.getZ()), (Color.BLUE));
        faces[4] = new Square(new Point4D(first.getX(), first.getY(), second.getZ()), new Point4D(first.getX(), second.getY(), second.getZ()), new Point4D(second.getX(), second.getY(), second.getZ()), new Point4D(second.getX(), first.getY(), second.getZ()), (Color.MAGENTA));
        faces[5] = new Square(new Point4D(first.getX(), first.getY(), first.getZ()), new Point4D(first.getX(), first.getY(), second.getZ()), new Point4D(first.getX(), second.getY(), second.getZ()), new Point4D(first.getX(), second.getY(), first.getZ()), (Color.CYAN));
    }

    public Cube(Point4D first, Point4D second, Color color) {
        faces[0] = new Square(new Point4D(first.getX(), first.getY(), first.getZ()), new Point4D(first.getX(), second.getY(), first.getZ()), new Point4D(second.getX(), second.getY(), first.getZ()), new Point4D(second.getX(), first.getY(), first.getZ()), color);
        faces[1] = new Square(new Point4D(first.getX(), first.getY(), first.getZ()), new Point4D(first.getX(), first.getY(), second.getZ()), new Point4D(second.getX(), first.getY(), second.getZ()), new Point4D(second.getX(), first.getY(), first.getZ()), color);
        faces[2] = new Square(new Point4D(second.getX(), first.getY(), first.getZ()), new Point4D(second.getX(), second.getY(), first.getZ()), new Point4D(second.getX(), second.getY(), second.getZ()), new Point4D(second.getX(), first.getY(), second.getZ()), color);
        faces[3] = new Square(new Point4D(first.getX(), second.getY(), first.getZ()), new Point4D(first.getX(), second.getY(), second.getZ()), new Point4D(second.getX(), second.getY(), second.getZ()), new Point4D(second.getX(), second.getY(), first.getZ()), color);
        faces[4] = new Square(new Point4D(first.getX(), first.getY(), second.getZ()), new Point4D(first.getX(), second.getY(), second.getZ()), new Point4D(second.getX(), second.getY(), second.getZ()), new Point4D(second.getX(), first.getY(), second.getZ()), color);
        faces[5] = new Square(new Point4D(first.getX(), first.getY(), first.getZ()), new Point4D(first.getX(), first.getY(), second.getZ()), new Point4D(first.getX(), second.getY(), second.getZ()), new Point4D(first.getX(), second.getY(), first.getZ()), color);
        this.color = color;
    }

    private Cube() {
    }

    public Cube getTransformed(Matrix transformation) {
        Cube newCube = new Cube();
        newCube.faces[0] = this.faces[0].getTransformed(transformation);
        newCube.faces[1] = this.faces[1].getTransformed(transformation);
        newCube.faces[2] = this.faces[2].getTransformed(transformation);
        newCube.faces[3] = this.faces[3].getTransformed(transformation);
        newCube.faces[4] = this.faces[4].getTransformed(transformation);
        newCube.faces[5] = this.faces[5].getTransformed(transformation);
        return newCube;
    }

    public void draw(Graphics2D g2d) {
        Arrays.sort(faces);
        for (Square square : faces) {
            g2d.setPaint(square.color);
            square.draw(g2d);
        }
    }

    @Override
    public void addToList(List<Shape> shapes) {
        Arrays.sort(faces);
        //faces[0].addToList(shapes);
        //faces[1].addToList(shapes);
        //faces[2].addToList(shapes);
        faces[3].addToList(shapes);
        faces[4].addToList(shapes);
        faces[5].addToList(shapes);
    }

    public double getZCentre() {
        return (faces[0].first.getZ() + faces[2].third.getZ()) / 2;
    }

    public Point4D getPosition() {
        return new Point4D(faces[0].first.getX(),faces[0].first.getY(),faces[0].first.getZ());
    }
}
