package main;

import java.awt.*;

public class Triangle extends Shape {
    public Point4D first = new Point4D();
    public Point4D second = new Point4D();
    public Point4D third = new Point4D();

    public Triangle() {
    }

    public Triangle(Point4D first, Point4D second, Point4D third) {
        this.first = first;
        this.second = second;
        this.third = third;
    }

    public Triangle(Point4D first, Point4D second, Point4D third, Color color) {
        this.first = first;
        this.second = second;
        this.third = third;
        this.color = color;
    }

    public Shape getTransformed(Matrix transformation) {
        return new Triangle(transformation.multiplyByPoint4D(this.first), transformation.multiplyByPoint4D(this.second), transformation.multiplyByPoint4D(this.third), this.color);
    }

    public void draw(Graphics2D g2d) {
        g2d.fillPolygon(new int[]{(int) this.first.getX(), (int) this.second.getX(), (int) this.third.getX()},
                new int[]{(int) this.first.getY(), (int) this.second.getY(), (int) this.third.getY()}, 3);
    }

    public double getZCentre() {
        return (this.first.getZ() + this.second.getZ() + this.third.getZ()) / 3;
    }
}
