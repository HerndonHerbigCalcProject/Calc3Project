public class MTest {
    private static Matrix m1;
    private static Matrix m2;
    private static Matrix m3;
    private static Matrix m4;
    public static void setup() {
        m1 = new Matrix(3, 3, new double[] {1, 2, 3, 4, 5, 6, 7, 8, 9});
        m2 = new Matrix(2, 3, new double[] {1, 2, 3, 4, 5, 6});
        m3 = new Matrix(3, 2, new double[] {1, 2, 3, 4, 5, 6});
        m4 = new Matrix(3, 3, new double[] {3, 1, 2, 4, 5, 8, 7, 6, 9});
    }

    public static boolean testAdd() {
        Matrix result = new Matrix(3, 3, new double[] {4,3,5,8,10,14,14,14,18});
        return result.equals(Matrix.sum(m1,m4));
    }

    public static boolean testDeterminant() {
        return m1.determinant() == 0 && m4.determinant() == -11;
    }

    public static boolean testMultiply(Matrix M1, Matrix M2, Matrix result) {
        return Matrix.product(M1, M2).equals(result);
    }

    public static void main(String[] args) {
        setup();
        System.out.println(testAdd());
        System.out.println(testDeterminant());
        System.out.println(m1.determinant());
        System.out.println(m4.determinant());
        Matrix tmp = new Matrix(2, 2, new double[] {22, 28, 49, 64});
        Matrix other = Matrix.product(m2, m3);
        System.out.println(other.get(0, 0));
        System.out.println(testMultiply(m2, m3, tmp));
        tmp = new Matrix(2, 3, new double[] {1,3,5,2,4,6});
        System.out.println(tmp.transpose().equals(m3));
    }
}
