/**
 * Class to make sure everything works
 * @version 1.0
 * @author JT Herndon
 */
public class MTest {
    private static Matrix m1;
    private static Matrix m2;
    private static Matrix m3;
    private static Matrix m4;
    /**
     * Sets up test matrices
     */
    public static void setup() {
        m1 = new Matrix(3, 3, new double[] {1, 2, 3, 4, 5, 6, 7, 8, 9});
        m2 = new Matrix(2, 3, new double[] {1, 2, 3, 4, 5, 6});
        m3 = new Matrix(3, 2, new double[] {1, 2, 3, 4, 5, 6});
        m4 = new Matrix(3, 3, new double[] {3, 1, 2, 4, 5, 8, 12, 6, 9});
        System.out.println(m1);
        System.out.println(m2);
        System.out.println(m3);
        System.out.println(m4);
    }

    /**
     * tests the add function
     * @return true if Matrix.add() works properly
     */
    public static boolean testAdd() {
        Matrix result = new Matrix(3, 3, new double[] {4,3,5,8,10,14,14,14,18});
        return result.equals(Matrix.sum(m1,m4));
    }

    /**
     * tests the determinant function
     * @return true if determinant works properly
     */
    public static boolean testDeterminant() {
        return m1.determinant() == 0 && m4.determinant() == -11;
    }

    /**
     * tests the multiply function
     * @return true if Matrix.product() works as expected
     */
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
        tmp = new Matrix(2, 3, new double[] {2, 4, 6, 8, 10, 12});
        System.out.println(tmp.equals(m2.scalarMultiply(2)));
        tmp = new Matrix(3, 3, new double[] {1, 0, 0, 0, 1, 0, 0, 0, 1});
        System.out.println(tmp.equals(Matrix.identity(3)));
        System.out.println(m1);
        System.out.println(m2);
        System.out.println(m3);
        System.out.println(m4);
        System.out.println(tmp.scalarMultiply(20));
        tmp = new Matrix(3, 3, new double[] {1, 1, 1, 1, 2, 3, 1, 3, 6});
        System.out.println(tmp.equals(Part1.pascal(3)));
        System.out.println(m2.norm_inf());
        System.out.println(m3.norm_inf());
        System.out.println(new Vector(m4, 0));
        System.out.println((new Vector(m4, 0)).magnitude());
        Vector v1 = new Vector(m3, 1);
        Vector v2 = new Vector(m1, 1);
        System.out.println("" + v1 + v2);
        System.out.println(Vector.dotProduct(v1, v2));
        System.out.println(v1.subVector(0, 2));
        try {
            System.out.println(m2.subMatrix(1, 0, 3, 2));
        } catch (Exception e){ 
            System.out.println("Failed as expected");
        }
        System.out.println(m1.subMatrix(1, 0, 2, 2));
        //Matrix P = Part1.pascal(3);
        Matrix P = new Matrix(3, 3, new double[] {3, 4, 12, 4, 2, 3, 12, 4, 3});
        Object[] ans = Part1.qr_fact_househ(P);
        System.out.println(ans[0]);
        System.out.println(ans[1]);
        System.out.println(Matrix.product((Matrix) ans[0], (Matrix) ans[1]));
        System.out.println(ans[2]);
        Matrix H = (Matrix) ans[0];
        System.out.println(H.times(H.transpose()));
        ans = Part1.qr_fact_givens(P);
        System.out.println(ans[0]);
        System.out.println(ans[1]);
        System.out.println(Matrix.product((Matrix) ans[0], (Matrix) ans[1]));
        System.out.println(ans[2]);
        H = (Matrix) ans[0];
        System.out.println(H.times(H.transpose()));
        System.out.println(Part1.qr_fact_givens(m2)[1]);
        System.out.println(Part1.qr_fact_givens(m3)[1]);
        System.out.println(Part1.qr_fact_givens(m2)[2]);
        System.out.println(Part1.qr_fact_givens(m3)[2]);
        Matrix bad = new Matrix(3,3,new double[]{0,0,0,0,0,0,0,0,0});
        System.out.println(Part1.qr_fact_househ(bad)[0]);
        System.out.println(Part1.qr_fact_givens(bad)[0]);
        System.out.println(Part1.qr_fact_givens(bad)[1]);

        //Test Row operations
        Matrix r = new Matrix(3, 3, new double[] {1,2,3,5,5,5,1,2,3});
        r.rowOperation(2,0,-2);
        r.rowScale(1,.2);
        System.out.println(r);
    }
}
