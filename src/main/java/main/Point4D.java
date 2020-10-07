package main;

public class Point4D {
    public double x = 0;
    public double y = 0;
    public double z = 0;
    public double w = 1;

    public Point4D() {
    }
    public Point4D(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    public Point4D(double x, double y, double z, double w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }

    public void add(double x, double y, double z) {
        this.x = this.w*(getX() + x);
        this.y = this.w*(getY() + y);
        this.z = this.w*(getZ() + z);
    }

    public double getX() {
        return x / w;
    }

    public double getY() {
        return y / w;
    }

    public double getZ() {
        return z / w;
    }

    public double multiply(Point4D second) {
        return this.getX() * second.getX() + this.getY() * second.getY() + this.getZ() * second.getZ();
    }
}
