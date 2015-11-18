/**
 * A class for part 2: Convergence of the iterative methods
 * In this part, you will use both Jacobi and Gauss-Seidel method to obtain the
 * approximate solution to a system Ax = b, for 3×3 symmetric matrix
 * @version 1.0
 * @author JT Herndon
 * @author Alex Herbig
 */
public class Part2 {
    private static final Matrix A = (new Matrix(3, 3, new double[] {1.0, 1.0/2,
        1.0/3, 1.0/2, 1.0, 1.0/4, 1.0/3, 1.0/4, 1.0}));
    private static final Vector b = (new Vector(new double[] {0.1, 0.1, 0.1}));

    /**
     * Implement a procedure that uses the Jacobi iterative method to
     * approximate the solution to the system Ax = b
     * @param x0 a 3 x 1 vector x0 with floating-point real numbers as entries
     * @param e a tolerance parameter that determines when approximation is 
     *         close enough
     * @param M a positive integer M giving the maximum number of times to
     *         iterate the method before quitting
     * @return The outputs should be the approximate solution xN obtained by the
     *          iterative method as well as the number of iterations N needed to
     *          obtainthat approximation. If the procedure iterates M and has
     *           not attained an answer with sufficient accuracy, the
     *           output should instead be a value representing a failure 
     */
    public static Object[] jacobi_iter(Vector x0, double e, int M) {
        if (M == 0) {
            return null;
        }
        if ((Matrix.sum(Matrix.product(A, x0), b.scalarMultiply(-1)))
            .toVector().magnitude() < e) {
            return new Object[] {x0, new Integer(0)};
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
            //System.out.println(S);
            //System.out.println(T);
        }
        x1 = (Matrix.product(S, Matrix.sum(b, Matrix.product(
            T, x0).scalarMultiply(-1)))).toVector();
        //System.out.println(x1);
        Object[] ret =  jacobi_iter(x1, e, M - 1);
        ret[1] = new Integer((Integer) ret[1] + 1);
        return ret;
    }

    /**
     * Implement a procedure that uses the Gauss-Seidel iterative method to
     * approximate the solution to the system Ax = b
     * @param x0 a 3 x 1 vector x0 with floating-point real numbers as entries
     * @param error a tolerance parameter that determines when approximation is 
     *         close enough
     * @param M a positive integer M giving the maximum number of times to
     *         iterate the method before quitting
     * @return the vector object representing the approximation, and an integer
     *         object representing the number of iterations required
     */
    public static Object[]  gs_iter(Vector x0, double error, int M) {
        Vector b0 = b;
        Matrix S = new Matrix(3,3);
        Matrix T = new Matrix(3,3);
        for (int y = 0; y < 3; y++) {
            for (int x = y + 1; x < 3; x++) {
                T.set(y, x, A.get(y, x));
            }
        }
        double a = A.get(0,0);
        double b = A.get(1,0);
        double c = A.get(1,1);
        double d = A.get(2,0);
        double e = A.get(2,1);
        double f = A.get(2,2);
        S.set(0,0,1/a);
        S.set(1,0,-b/a/c);
        S.set(1,1,1/c);
        S.set(2,0,(-c*d+b*e)/a/c/f);
        S.set(2,1,-e/c/f);
        S.set(2,2,1/f);
        int total = 0;
        //System.out.println(S);
        //System.out.println(T);
        while ((Matrix.sum(Matrix.product(A, x0), b0.scalarMultiply(-1)))
            .toVector().magnitude() > error && total < M) {
            x0 = (Matrix.product(S, Matrix.sum(b0, Matrix.product(
                T, x0).scalarMultiply(-1)))).toVector();
            //System.out.println("YO");
            total++;
        }
        if (total == M) {
            return null;
        }
        return new Object[] {x0, new Integer(total)};
    }

    public static void main(String[] args) {
        System.out.println(A);
        System.out.println(b);
        Object[] test1 = jacobi_iter(new Vector(new double[] {0, 0, 0}), 0.0005,
             30);
        System.out.println("Vector result:\n"+test1[0]);
        System.out.println("Iterations required: " + test1[1]);
        Object[] test2 = gs_iter(new Vector(new double[] {0,0,0}), 0.000005, 30);
        System.out.println("Vector result: " + ((Matrix)test2[0]).transpose());
        System.out.println("Iterations required: " + test2[1]);
    }
}
