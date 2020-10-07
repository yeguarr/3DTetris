package main;

import java.awt.*;
import java.util.List;

public class Square extends Shape {
    public Point4D first = new Point4D();
    public Point4D second = new Point4D();
    public Point4D third = new Point4D();
    public Point4D fourth = new Point4D();

    public Square() {
    }

    public Square(Point4D first, Point4D second, Point4D third, Point4D fourth) {
        this.first = first;
        this.second = second;
        this.third = third;
        this.fourth = fourth;
    }

    public Square(Point4D first, Point4D second, Point4D third, Point4D fourth, Color color) {
        this.first = first;
        this.second = second;
        this.third = third;
        this.fourth = fourth;
        this.color = color;
    }

    public Square getTransformed(Matrix transformation) {
        return new Square(transformation.multiplyByPoint4D(this.first), transformation.multiplyByPoint4D(this.second), transformation.multiplyByPoint4D(this.third), transformation.multiplyByPoint4D(this.fourth), this.color);

    }

    public void draw(Graphics2D g2d) {
        g2d.fillPolygon(new int[]{(int) this.first.getX(), (int) this.second.getX(), (int) this.third.getX(), (int) this.fourth.getX()},
                new int[]{(int) this.first.getY(), (int) this.second.getY(), (int) this.third.getY(), (int) this.fourth.getY()}, 4);

    }

    public double getZCentre() {
        return (this.first.getZ() + this.second.getZ() + this.third.getZ() + this.fourth.getZ()) / 4;
    }

    public void addToList(List<Shape> shapes) {
        //if (this.first.getZ() > 0 && this.second.getZ() > 0 && this.third.getZ() > 0 && this.fourth.getZ() > 0 )
        shapes.add(this);
    }
}
