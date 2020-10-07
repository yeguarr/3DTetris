package main;

import java.awt.*;
import java.util.Arrays;
import java.util.List;

public class Tetromino extends Shape {
    Cube[] cubes = new Cube[4];
    private Tetromino() {}

    public static Tetromino shapeI(double scale, Color color) {
        Tetromino t = new Tetromino();
        for (int i = 0; i < 4; i++)
            t.cubes[i] = new Cube(new Point4D(i*scale,0,0), new Point4D((i+1)*scale,scale,scale), color);
        t.color = color;
        return t;
    }

    public static Tetromino shapeO(double scale, Color color) {
        Tetromino t = new Tetromino();
        t.cubes[0] = new Cube(new Point4D(0,0,0), new Point4D(scale,scale,scale), color);
        t.cubes[1] = new Cube(new Point4D(scale,0,0), new Point4D(2*scale,scale,scale), color);
        t.cubes[2] = new Cube(new Point4D(0,0,scale), new Point4D(scale,scale,2*scale), color);
        t.cubes[3] = new Cube(new Point4D(scale,0,scale), new Point4D(2*scale,scale,2*scale), color);
        t.color = color;
        return t;
    }

    public static Tetromino shapeT(double scale, Color color) {
        Tetromino t = new Tetromino();
        t.cubes[0] = new Cube(new Point4D(0,0,0), new Point4D(scale,scale,scale), color);
        t.cubes[1] = new Cube(new Point4D(scale,0,0), new Point4D(2*scale,scale,scale), color);
        t.cubes[2] = new Cube(new Point4D(2*scale,0,0), new Point4D(3*scale,scale,scale), color);
        t.cubes[3] = new Cube(new Point4D(scale,0,scale), new Point4D(2*scale,scale,2*scale), color);
        t.color = color;
        return t;
    }

    public static Tetromino shapeL(double scale, Color color) {
        Tetromino t = new Tetromino();
        t.cubes[0] = new Cube(new Point4D(0,0,0), new Point4D(scale,scale,scale), color);
        t.cubes[1] = new Cube(new Point4D(scale,0,0), new Point4D(2*scale,scale,scale), color);
        t.cubes[2] = new Cube(new Point4D(2*scale,0,0), new Point4D(3*scale,scale,scale), color);
        t.cubes[3] = new Cube(new Point4D(0,0,scale), new Point4D(scale,scale,2*scale), color);
        t.color = color;
        return t;
    }

    public static Tetromino shapeJ(double scale, Color color) {
        Tetromino t = new Tetromino();
        t.cubes[0] = new Cube(new Point4D(0,0,0), new Point4D(scale,scale,scale), color);
        t.cubes[1] = new Cube(new Point4D(scale,0,0), new Point4D(2*scale,scale,scale), color);
        t.cubes[2] = new Cube(new Point4D(2*scale,0,0), new Point4D(3*scale,scale,scale), color);
        t.cubes[3] = new Cube(new Point4D(2*scale,0,scale), new Point4D(3*scale,scale,2*scale), color);
        t.color = color;
        return t;
    }

    public static Tetromino shapeS(double scale, Color color) {
        Tetromino t = new Tetromino();
        t.cubes[0] = new Cube(new Point4D(0,0,0), new Point4D(scale,scale,scale), color);
        t.cubes[1] = new Cube(new Point4D(scale,0,0), new Point4D(2*scale,scale,scale), color);
        t.cubes[2] = new Cube(new Point4D(2*scale,0,scale), new Point4D(3*scale,scale,2*scale), color);
        t.cubes[3] = new Cube(new Point4D(scale,0,scale), new Point4D(2*scale,scale,2*scale), color);
        t.color = color;
        return t;
    }

    public static Tetromino shapeZ(double scale, Color color) {
        Tetromino t = new Tetromino();
        t.cubes[0] = new Cube(new Point4D(0,0,scale), new Point4D(scale,scale,2*scale), color);
        t.cubes[1] = new Cube(new Point4D(scale,0,0), new Point4D(2*scale,scale,scale), color);
        t.cubes[2] = new Cube(new Point4D(2*scale,0,0), new Point4D(3*scale,scale,scale), color);
        t.cubes[3] = new Cube(new Point4D(scale,0,scale), new Point4D(2*scale,scale,2*scale), color);
        t.color = color;
        return t;
    }

    public static Tetromino shapeB(double scale, Color color) {
        Tetromino t = new Tetromino();
        t.cubes[0] = new Cube(new Point4D(0,0,0), new Point4D(scale,scale,scale), color);
        t.cubes[1] = new Cube(new Point4D(scale,0,0), new Point4D(2*scale,scale,scale), color);
        t.cubes[2] = new Cube(new Point4D(0,scale,0), new Point4D(scale,2*scale,scale), color);
        t.cubes[3] = new Cube(new Point4D(0,0,scale), new Point4D(scale,scale,2*scale), color);
        t.color = color;
        return t;
    }

    public static Tetromino shapeD(double scale, Color color) {
        Tetromino t = new Tetromino();
        t.cubes[0] = new Cube(new Point4D(0,0,0), new Point4D(scale,scale,scale), color);
        t.cubes[1] = new Cube(new Point4D(scale,0,0), new Point4D(2*scale,scale,scale), color);
        t.cubes[2] = new Cube(new Point4D(0,scale,scale), new Point4D(scale,2*scale,2*scale), color);
        t.cubes[3] = new Cube(new Point4D(0,0,scale), new Point4D(scale,scale,2*scale), color);
        t.color = color;
        return t;
    }
    public static Tetromino shapeF(double scale, Color color) {
        Tetromino t = new Tetromino();
        t.cubes[0] = new Cube(new Point4D(0,0,0), new Point4D(scale,scale,scale), color);
        t.cubes[1] = new Cube(new Point4D(scale,0,0), new Point4D(2*scale,scale,scale), color);
        t.cubes[2] = new Cube(new Point4D(scale,scale,0), new Point4D(2*scale,2*scale,scale), color);
        t.cubes[3] = new Cube(new Point4D(0,0,scale), new Point4D(scale,scale,2*scale), color);
        t.color = color;
        return t;
    }

    @Override
    public Shape getTransformed(Matrix transformation) {
        Tetromino t = new Tetromino();
        t.cubes[0] = this.cubes[0].getTransformed(transformation);
        t.cubes[1] = this.cubes[1].getTransformed(transformation);
        t.cubes[2] = this.cubes[2].getTransformed(transformation);
        t.cubes[3] = this.cubes[3].getTransformed(transformation);
        return t;
    }

    public static Tetromino rand(double scale) {
        Color color = Color.getHSBColor((float)Math.random(), 1,1);
        double rand = Math.random();
        if (rand <= 0.1)
            return shapeI(scale, color);
        else if (rand <= 0.2)
            return shapeO(scale, color);
        else if (rand <= 0.3)
            return shapeT(scale, color);
        else if (rand <= 0.4)
            return shapeL(scale, color);
        else if (rand <= 0.5)
            return shapeJ(scale, color);
        else if (rand <= 0.6)
            return shapeS(scale, color);
        else if (rand <= 0.7)
            return shapeZ(scale, color);
        else if (rand <= 0.8)
            return shapeB(scale, color);
        else if (rand <= 0.9)
            return shapeD(scale, color);
        else
            return shapeF(scale, color);
    }

    @Override
    public void draw(Graphics2D g2d) {
        Arrays.sort(cubes);
        for(Cube cube : cubes) {
            g2d.setPaint(cube.color);
            cube.draw(g2d);
        }
    }

    @Override
    public void addToList(List<Shape> shapes) {
        for(Cube cube : cubes) {
            cube.addToList(shapes);
        }
    }

    @Override
    public double getZCentre() {
        double sum  = 0;
        for(Cube cube : cubes) {
            sum += cube.getZCentre();
        }
        return sum/this.cubes.length;
    }
}
