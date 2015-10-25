/**
 * A class for part 2: Convergence of the iterative methods
 * In this part, you will use both Jacobi and Gauss-Seidel method to obtain the
 * approximate solution to a system Ax = b, for 3×3 symmetric matrix
 * @version 1.0
 * @author JT Herndon
 * @author Alex Herbig
 */
public class Part2 {
    /*private static final Matrix A = (new Matrix(3, 3, new double[] {1.0, 1.0/2,
        1.0/3, 1.0/2, -1.0/3, -1.0/4, 1.0/3, -1.0/4, 1.0/5}))
        .scalarMultiply(60);
    private static final Vector b = (new Vector(new double[] {0.1, 0.1, 0.1}))
        .scalarMultiply(60).toVector();
        */
    private static final Matrix A = new Matrix(3, 3, new double[] {4, -1, -1, -2, 6, 1, -1, 1, 7});
    private static final Vector b = new Vector(new double[] {3, 9, -6});

    /**
     * Implement a procedure that uses the Jacobi iterative method to
     * approximate the solution to the system Ax = b
     * @param x0 a 3 x 1 vector x0 with floating-point real numbers as entries
     * @param e a tolerance parameter that determines when approximation is 
     *         close enough
     * @param M a positive integer M giving the maximum number of times to
     *         iterate the method before quitting
     */
    public static Vector jacobi_iter(Vector x0, double e, int M) {
        if (M == 0) {
            return null;
        }
        if ((Matrix.sum(Matrix.product(A, x0), b.scalarMultiply(-1)))
            .toVector().magnitude() < e) {
            return x0;
        }
        Vector x1;
        Matrix S = new Matrix(3, 3);
        for (int j = 0; j < 3; j++){
            S.set(j, j, 1.0 / A.get(j, j));
        }
        Matrix T  = new Matrix(3, 3);
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (j != i) {
                    T.set(i, j, A.get(i, j));
                }
            }
        }
        if (M == 1) {
            System.out.println(S);
            System.out.println(T);
        }
        x1 = (Matrix.product(S, Matrix.sum(b, Matrix.product(
            T, x0).scalarMultiply(-1)))).toVector();
        System.out.println(x1);
        return jacobi_iter(x1, e, M - 1);
    }

    /**
     * Implement a procedure that uses the Gauss-Seidel iterative method to
     * approximate the solution to the system Ax = b
     * @param x0 a 3 x 1 vector x0 with floating-point real numbers as entries
     * @param e a tolerance parameter that determines when approximation is 
     *         close enough
     * @param M a positive integer M giving the maximum number of times to
     *         iterate the method before quitting
     */
    public static Vector gs_iter(Vector A, double e, int M) {
        throw new UnsupportedOperationException();
    }

    public static void main(String[] args) {
        System.out.println(A);
        System.out.println(b);
        System.out.println(jacobi_iter(new Vector(new double[] {0, 0, 0}), 0.0005, 30));
    }
}
