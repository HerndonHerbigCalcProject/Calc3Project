public class Vector extends Matrix{
    public Vector(int size) {
        super(size, 1);
    }
    public Vector(double[] data) {
        super (data.length, 1, data);
    }
    public Vector(Matrix m, int column) {
        super(m.getRows(), 1);
        if (column >= m.getColumns()) {
            throw new IllegalArgumentException("Tried to make vector of "
                + column + "th column of a matrix with " + m.getColumns()
                + " columns.");
        }
        for (int i = 0; i < getRows(); i++) {
            set(i, 0, m.get(i, column));
        }
    }

    public double get(int i) {
        return get(i, 0);
    }

    public void set(int i, double d) {
        set(i, 0, d);
    }

    public Vector subVector(int start, int size) {
        if (size + start > rows) {
            throw new IllegalArgumentException("Tried to make subvector"
                + " from size " + rows + " vector starting at "
                + start + " and of size " + size + ".");
        }
        Vector ret = new Vector(size);
        for (int i = 0; i < size; i++) {
            ret.set(i, this.get(i + start));
        }
        return ret;
    }
    public double magnitude() {
        double ret = 0;
        for (int i = 0; i < getRows(); i++) {
            ret += get(i, 0) * get(i, 0);
        }
        return Math.sqrt(ret);
    }
    public static double dotProduct(Vector v1, Vector v2) {
        return product(v1.transpose(), v2).get(0, 0);
    }
}
