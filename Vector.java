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
