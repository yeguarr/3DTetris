package main;

public class Matrix {
    public double[][] matrix;
    // matrix.length - row
    // matrix[0].length - column


    public Matrix(double[][] matrix) {
        this.matrix = matrix;
    }

    public static Matrix multiply(Matrix first, Matrix second) {
        double[][] result = new double[first.matrix.length][second.matrix[0].length];
        if (first.matrix[0].length == second.matrix.length) {
            for (int row = 0; row < first.matrix.length; row++) {
                for (int col = 0; col < first.matrix[row].length; col++) {
                    result[row][col] = multiplyMatricesCell(first.matrix, second.matrix, row, col);
                }
            }
            return new Matrix(result);
        } else
            throw new RuntimeException("Bad, everything is bad in mult");
    }

    private static double multiplyMatricesCell(double[][] firstMatrix, double[][] secondMatrix, int row, int col) {
        double cell = 0;
        for (int i = 0; i < secondMatrix.length; i++) {
            cell += firstMatrix[row][i] * secondMatrix[i][col];
        }
        return cell;
    }

    public static Matrix add(Matrix first, Matrix second) {
        if (first.matrix.length == second.matrix.length && first.matrix[0].length == second.matrix[0].length) {
            for (int i = 0; i < first.matrix.length; i++) {
                for (int j = 0; j < first.matrix[0].length; j++)
                    first.matrix[i][j] += second.matrix[i][j];
            }

            return first;
        } else {
            throw new RuntimeException("Bad, everything is bad");
        }
    }

    public Point4D multiplyByPoint4D(Point4D point4D) {
        Point4D newPoint = new Point4D();
        if (this.matrix.length == 4 && this.matrix[0].length == 4) {
            double[][] vector = new double[][]{{point4D.x}, {point4D.y}, {point4D.z}, {point4D.w}};
            newPoint.x = multiplyMatricesCell(this.matrix, vector, 0, 0);
            newPoint.y = multiplyMatricesCell(this.matrix, vector, 1, 0);
            newPoint.z = multiplyMatricesCell(this.matrix, vector, 2, 0);
            newPoint.w = multiplyMatricesCell(this.matrix, vector, 3, 0);

            return newPoint;
        } else
            throw new RuntimeException("Bad, everything is bad in mult point");
    }

    public Matrix multiply(Matrix second) {
        double[][] result = new double[this.matrix.length][second.matrix[0].length];
        if (this.matrix[0].length == second.matrix.length) {
            for (int row = 0; row < this.matrix.length; row++) {
                for (int col = 0; col < this.matrix[row].length; col++) {
                    result[row][col] = multiplyMatricesCell(this.matrix, second.matrix, row, col);
                }
            }
            return new Matrix(result);
        } else
            throw new RuntimeException("Bad, everything is bad in mult");
    }

    public Matrix add(Matrix second) {
        if (this.matrix.length == second.matrix.length && this.matrix[0].length == second.matrix[0].length) {
            for (int i = 0; i < this.matrix.length; i++) {
                for (int j = 0; j < this.matrix[i].length; j++)
                    this.matrix[i][j] += second.matrix[i][j];
            }
            return this;
        } else {
            throw new RuntimeException("Bad, everything is bad");
        }
    }

}
