package main;

import java.awt.*;
import java.awt.geom.Line2D;

public class Line extends Shape {
    public Point4D first = new Point4D();
    public Point4D second = new Point4D();

    public Line() {
    }

    public Line(Point4D first, Point4D second) {
        this.first = first;
        this.second = second;
    }

    public Line(Point4D first, Point4D second, Color color) {
        this.first = first;
        this.second = second;
        this.color = color;
    }

    public void draw(Graphics2D g2d) {
        Line2D line2D = new Line2D.Double();
        line2D.setLine(this.first.getX(), this.first.getY(), this.second.getX(), this.second.getY());
        g2d.draw(line2D);
    }

    public double getZCentre() {
        return (this.first.getZ() + this.second.getZ()) / 2;
    }

    public Line getTransformed(Matrix transformation) {
        return new Line(transformation.multiplyByPoint4D(this.first), transformation.multiplyByPoint4D(this.second), this.color);
    }
}
