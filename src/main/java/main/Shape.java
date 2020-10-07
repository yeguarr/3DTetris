package main;

import java.awt.*;
import java.util.List;

public abstract class Shape implements Comparable<Shape> {
    public Color color = Color.BLACK;

    public abstract Shape getTransformed(Matrix transformation);

    public abstract void draw(Graphics2D g2d);

    public abstract double getZCentre();

    public void addToList(List<Shape> shapes) {
        shapes.add(this);
    }

    public int compareTo(Shape o) {
        double comp = this.getZCentre() - o.getZCentre();
        if (comp == 0)
            return 0;
        return comp > 0 ? 1 : -1;
    }

}
