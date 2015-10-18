/**
 * This is a class for matrices for the Calc 3 for CS project.
 * @author JT Herndon
 * @version 1.0
 */
public class Matrix {
    private double[][] matrix;
    private int rows;
    private int columns;
    public Matrix(int rows, int columns) {
        matrix = new double[rows][columns];
        this.rows = rows;
        this.columns = columns;
    }

    public Matrix(int rows, int columns, double[] nums) {
        if (nums.length != rows * columns) {
            throw new IllegalArgumentException("Tried to make a " + rows + " by "
                + columns + " matrix with " + nums.length + " elements.");
        }
        this.rows = rows;
        this.columns = columns;
        int z = 0;
        matrix = new double[rows][columns];
        for (int y = 0; y < columns; y++) {
            for (int x = 0; x < rows; x++) {
                matrix[y][x] = nums[z++];
            }
        }
    }

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }

    public double get(int row, int column) {
        if (row < 0 || row >= rows || column < 0 || column >= columns) {
            throw new IllegalArgumentException("Tried to get value at row " + row
                + " and column " + column + " of a " + rows + " by " +  columns
                + "matrix.");
        }
        return matrix[row][column];
    }
    public void set(int row, int column, double val) {
        if (row < 0 || row >= rows || column < 0 || column >= columns) {
            throw new IllegalArgumentException("Tried to set value at row " + row
                + " and column " + column + " of a " + rows + " by " +  columns
                + "matrix.");
        }
        matrix[row][column] = val;
    }
    public static Matrix sum(Matrix m1, Matrix m2) {
        if (m1 == null || m2 == null) {
            throw new IllegalArgumentException("Tried to add one or more null " +
                "matrices.");
        }
        if (m1.getRows() != m2.getRows() || m1.getColumns() != m2.getColumns()) {
            throw new IllegalArgumentException("Tried to add a " + m1.getRows()
                + " by " + m1.getColumns() + " matrix to a " + m2.getRows()
                + " by " + m2.getColumns() + " matrix.");
        }
        Matrix ret = new Matrix(m1.getRows(), m1.getColumns());
        for (int y = 0; y < ret.getRows(); y++) {
            for (int x = 0; x < ret.getColumns(); x++) {
                ret.set(y, x, m1.get(y, x) + m2.get(y, x));
            }
        }
        return ret;
    }
    public Matrix scalarMultiply(double scale) {
        Matrix ret = new Matrix(rows, columns);
        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < columns; x++) {
                ret.set(y, x, get(y, x) * scale);
            }
        }
        return ret;
    }
    public static Matrix product(Matrix m1, Matrix m2) {
        if (m1 == null || m2 == null) {
            throw new IllegalArgumentException("Tried to multiply one or more "
                + "null matrices.");
        }
        if (m1.getColumns() != m2.getRows()) {
            throw new IllegalArgumentException("Tried to multiply a "
                + m1.getRows()
                + " by " + m1.getColumns() + " matrix by a " + m2.getRows()
                + " by " + m2.getColumns() + " matrix.");

        }
        Matrix ret = new Matrix(m1.getRows(), m2.getColumns());
        for (int y = 0; y < m1.getRows(); y++) {
            for (int x = 0; x < m2.getColumns(); x++) {
                double val = 0;
                for (int z = 0; z < m1.getColumns(); z++) {
                    val += m1.get(y, z) + m2.get(z, x);
                }
                ret.set(y, x, val);
            }
        }
        return ret;
    }
    public Matrix transpose() {
        Matrix ret = new Matrix(columns, rows);
        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < columns; x++) {
                ret.set(x, y, matrix[y][x]);
            }
        }
        return ret;
    }
    public boolean equals(Object other) {
        if (other == null || !(other instanceof Matrix)) {
            return false;
        }
        if (this == other) {
            return true;
        }
        Matrix that = (Matrix) other;
        if (this.rows != that.rows || this.columns != that.columns) {
            return false;
        }
        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < columns; x++) {
                if (this.matrix[y][x] != that.matrix[y][x]) {
                    return false;
                }
            }
        }
        return true;
    }
}

