/**
 * This is a class for matrices for the Calc 3 for CS project.
 * @author JT Herndon
 * @version 1.0
 */
public class Matrix {
    protected double[][] matrix;
    protected int rows;
    protected int columns;

    /**
     * Constructor that takes in m and n and makes an empty matrix
     * @param rows the number of rows
     * @param columns the number of columns
     */
    public Matrix(int rows, int columns) {
        matrix = new double[rows][columns];
        this.rows = rows;
        this.columns = columns;
    }

    /**
     * Constrctor that takes in m, n and a double array with the numbers.
     * If (2, 2, new double[]{1, 2, 3, 4}) are passed, the result will be:
     * 1  2
     * 3  4
     * @param rows the number of rows
     * @param columns the number of columns
     * @param nums a double array with the numbers
     * @throws IllegalArgumentException if length of nums is not equal to m x n
     */
    public Matrix(int rows, int columns, double[] nums) {
        if (nums.length != rows * columns) {
            throw new IllegalArgumentException("Tried to make a " + rows + " by "
                + columns + " matrix with " + nums.length + " elements.");
        }
        this.rows = rows;
        this.columns = columns;
        int z = 0;
        matrix = new double[rows][columns];
        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < columns; x++) {
                matrix[y][x] = nums[z++];
            }
        }
    }

    /**
     * returns a sub matrix
     * @param top the top of the matrix to include (to include all
     *      rows, top would be 0)
     * @param left the left of the matrix to include (to include all
            columns, left would be 0)
     * @param rows the number of rows to include
     * @param columns the number of columns to include
     */
    public Matrix subMatrix(int top, int left, int rows, int columns) {
        if (this.rows - top < rows || this.columns - left < columns) {
            throw new IllegalArgumentException("Tried to take submatrix of "
                + this.rows + "x" + this.columns + " matrix from rows "
                + top+ " to " + (top+rows) + " and columns " + left
                + " to " + (left+columns) + ".");
        }
        Matrix ret = new Matrix(rows, columns);
        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < columns; x++) {
                ret.set(y, x, this.get(top + y, left + x));
            }
        }
        return ret;
    }

    /**
     * Returns the number of rows in matrix, or m, or the height
     * @return total number of rows
     */
    public int getRows() {
        return rows;
    }
    /**
     * Returns the total number of columns in matrix, or n, or the width
     * @return total number of columns
     */
    public int getColumns() {
        return columns;
    }
    /**
     * Gets a value from the matrix. Arguments are zero-indexed, and Y-value
     * comes first
     * @param row the row where the desired data is
     * @param column the column where the desired data is
     * @return the value at this row and column
     * @throws IllegalArgumentException if args are not in proper range
     */
    public double get(int row, int column) {
        if (row < 0 || row >= rows || column < 0 || column >= columns) {
            throw new IllegalArgumentException("Tried to get value at row " + row
                + " and column " + column + " of a " + rows + " by " +  columns
                + "matrix.");
        }
        return matrix[row][column];
    }

    /**
     * Sets a value of some entry in the matrix. Note this method is also 
     * zero-indexed and the y-coordinate comes first
     * @param row the row where we want to set the value
     * @param column the column where we want to set the value
     * @param val a double with the new value we want to insert into the matrix
     * @throws IllegalArgumentException if parameters are not in proper range
     */
    public void set(int row, int column, double val) {
        if (row < 0 || row >= rows || column < 0 || column >= columns) {
            throw new IllegalArgumentException("Tried to set value at row " + row
                + " and column " + column + " of a " + rows + " by " +  columns
                + "matrix.");
        }
        matrix[row][column] = val;
    }
    /**
     * Returns the sum of two matrices
     * @param m1 a Matrix
     * @param m2 another Matrix
     * @return a matrix object that represents the sum of the two matrices given
     * @throws IllegalArgumentException if matrices have different dimensions
     */
    public static Matrix sum(Matrix m1, Matrix m2) {
        if (m1 == null || m2 == null) {
            throw new IllegalArgumentException("Tried to add one or more null " +
                "matrices.");
        }
        if (m1.rows != m2.rows || m1.columns != m2.columns) {
            throw new IllegalArgumentException("Tried to add a " + m1.rows
                + " by " + m1.columns + " matrix to a " + m2.rows
                + " by " + m2.columns + " matrix.");
        }
        Matrix ret = new Matrix(m1.rows, m1.columns);
        for (int y = 0; y < ret.rows; y++) {
            for (int x = 0; x < ret.columns; x++) {
                ret.set(y, x, m1.get(y, x) + m2.get(y, x));
            }
        }
        return ret;
    }

    /**
     * Adds this matrix to another
     * @param other another matrix to add
     * @return sum of this matrix and the argument
     */
    Matrix plus(Matrix other) {
        return sum(this, other);
    }

    /**
     * Multiplies this matrix by another, with this on the left and arg on right
     * @param other another matrix to multiply
     * @return product of this matrix and the argument
     */
    Matrix times(Matrix other) {
        return product(this, other);
    }

    Matrix times(double scale) {
        return scalarMultiply(scale);
    }

    /**
     * Returns this matrix multiplied by some scalar quantity
     * @param scale the scalar we are multiplying
     * @return this matrix multiplied by the scalar
     */
    public Matrix scalarMultiply(double scale) {
        Matrix ret = new Matrix(rows, columns);
        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < columns; x++) {
                ret.set(y, x, get(y, x) * scale);
            }
        }
        return ret;
    }

    /**
     * Returns the product of two matrices
     * @param m1 the first Matrix
     * @param m2 the second  Matrix
     * @return a matrix object that represents the first matrix times the second
     * @throws IllegalArgumentException if matrices cannot be multiplied
     */
    public static Matrix product(Matrix m1, Matrix m2) {
        if (m1 == null || m2 == null) {
            throw new IllegalArgumentException("Tried to multiply one or more "
                + "null matrices.");
        }
        if (m1.columns != m2.rows) {
            throw new IllegalArgumentException("Tried to multiply a " + m1.rows
                + " by " + m1.columns + " matrix by a " + m2.rows
                + " by " + m2.columns + " matrix.");
        }
        Matrix ret = new Matrix(m1.rows, m2.columns);
        for (int y = 0; y < m1.rows; y++) {
            for (int x = 0; x < m2.columns; x++) {
                double val = 0;
                for (int z = 0; z < m1.columns; z++) {
                    val += m1.get(y, z) * m2.get(z, x);
                }
                ret.set(y, x, val);
            }
        }
        return ret;
    }
    /**
     * Returns the transpose of this matrix
     * @return the transpose of this matrix
     */
    public Matrix transpose() {
        Matrix ret = new Matrix(columns, rows);
        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < columns; x++) {
                ret.set(x, y, matrix[y][x]);
            }
        }
        return ret;
    }
    /**
     * Returns the determinant of this matrix
     * @return the determinant of this matrix
     */
    public double determinant() {
        if (rows != columns) {
            throw new IllegalArgumentException("Tried to take determinant of a "
                + rows + " by " + columns + " matrix.");
        }
        if (rows == 1) {
            return matrix[0][0];
        }
        double total = 0;
        int scale = 1;
        Matrix tmp;
        for (int i = 0; i < rows; i++) {
            tmp = new Matrix(rows - 1, rows - 1);
            for (int y = 1; y < rows; y++) {
                for (int x = 0; x < columns; x++) {
                    if (x != i) {
                        tmp.set(y - 1, x > i ? x - 1 : x, matrix[y][x]);
                    }
                }
            }
            total += scale * matrix[0][i] * tmp.determinant();
            scale = -scale;
        }
        return total;
    }

    @Override
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

    /**
     * Creates the identity matrix
     * @param size the size of the identity matrix desired
     * @return the identity matrix
     */
    public static Matrix identity(int size) {
        Matrix ret = new Matrix(size, size);
        for (int i = 0; i < size; i++) {
            ret.set(i, i, 1);
        }
        return ret;
    }

    /**
     * Calculates the infinity-norm of a matrix, or the maximum absolute value
     * row sum
     * @return the infinity-norm
     */
    public double norm_inf() {
        double ret = 0;
        double cur;
        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < columns; x++) {
                cur = Math.abs(matrix[y][x]);
                ret = cur > ret ? cur : ret;
            }
        }
        return ret;
    }

    /**
     * Performs a row operation on one row of a matrix, one row plus another
     * row that has been scaled
     * @param targetRow The row that gets resized
     * @param scaledRow The row to resize by
     * @param scale The scale multiplied by the scaled row
     */
    public void rowOperation(int targetRow, int scaledRow, double scale) {
        if (targetRow == scaledRow) {
            throw new java.lang.IllegalArgumentException("Cannot apply row "
                    + "operation to the same rows");
        }
        double[] target = this.matrix[targetRow];
        double[] scaled = this.matrix[scaledRow];

        //scales each value of one row based on the entries in the other row
        for (int i = 0; i < target.length; i++) {
            target[i] = target[i] + scale * scaled[i];
        }

    }

    /**
     * Scales a row from its original numbers
     * @param row The row being affected
     * @param scale The number to multiply the affected row by
     */
    public void rowScale(int row, double scale) {
        double[] affected = this.matrix[row];
        for (int i = 0; i < affected.length; i++) {
            affected[i] = affected[i] * scale;
        }
    }

    /**
     * Swaps two rows of a matrix
     * @param row1 the first row to swap
     * @param row2 the second row to swap
     */
    public void rowSwap(int row1, int row2) {
        double[] temp = this.matrix[row1];
        this.matrix[row1] = this.matrix[row2];
        this.matrix[row2] = temp;
    }


    /** 
     * Gets the trace of the matrix, used in part 3
     * @return the trace
     */
    public double trace() {
        if (rows != columns) {
            throw new UnsupportedOperationException("Can't take trace of non"
                + " square matrix");
        }
        double ret = 0;
        for (int i = 0; i < rows; i++) {
            ret += matrix[i][i];
        }
        return ret;
    }

    /**
     * If the matrix has 1 and exactly 1 column, this method returns an
     * equivalent vector object
     * @return a vector equivalent to the matrix
     */
    public Vector toVector() {
        if (columns == 1) {
            return new Vector(this, 0);
        } else {
            throw new IllegalArgumentException("Tried to turn a matrix with "
                + columns + " columns into a vector.");
        }
    }

    @Override
    public String toString() {
        String ret = "\n";
        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < columns; x++) {
                ret = ret + String.format("%2.7f", matrix[y][x]) + ",";
            }
            ret = ret + "\n";
        }
        return ret + "\n";
    }
}

