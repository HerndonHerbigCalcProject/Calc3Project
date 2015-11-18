/**
 * Class for Part 1: The Symmetric Pascal Matrix
 * @version 1.0
 * @author JT Herndon
 * @author Alex Herbig
 */
public class Part1 {
    /**
     * Make the pascal matrix
     * @param n the size
     * @return a nxn pascal matrix
     */
    public static Matrix pascal(int n) {
        Matrix ret = new Matrix(n, n);
        for (int y = 0; y < n; y++) {
            for (int x = 0; x < n; x++) {
                if (y == 0 || x == 0) {
                    ret.set(y, x, 1);
                } else {
                    ret.set(y, x, ret.get(y-1, x) + ret.get(y, x-1));
                }
            }
        }
        return ret;
    }

    /**
     * Implement the LU decomposition method for a n x n matrix A
     * @param A the matrix
     * @return L, U, and the error |LU-A|
     */
    public static Object[] lu_fact(Matrix A) {
        throw new UnsupportedOperationException();
    }

    /**
     * Implement QR-factorization of a n x n matrix A using Householder
     * reflections
     * @param A the matrix
     * @return Q, R, and the error |QR-A|
     */
    public static Object[] qr_fact_househ(Matrix A) {
        /*
        Household reflection:

        We are trying to convert the vector U
        {a, b, c} to {M, 0, 0} where M is magnitude

        We do this by taking the projection of U onto {1,0,0}
        and add it to U, and this gives us a vector x
        
        To reflect U across the perpendicular component of x,
        we would subtract 2 times projection of U onto x from U.

        in other words, our transformation would be
        I - 2*x*xt
        */

        if (A.getRows() == 1) {
            return new Object[] {new Matrix(1, 1, new double[] {1}), A, 0};
        }
        Vector X = new Vector(A, 0);
        double mag = X.magnitude();
        X.set(0, X.get(0) + mag);
        X = X.scalarMultiply(1 / X.magnitude()).toVector();
        Matrix H = Matrix.identity(A.getRows());
        H = Matrix.sum(H, Matrix.product(X, X.transpose()).scalarMultiply(-2));
        System.out.println("X: " + X);
        System.out.println("H: " + H);
        Matrix Anew = Matrix.product(H, A);
        //At this point, we've killed all the non-zero elements in first col
        System.out.println(Anew);
        Object[] next = qr_fact_househ(Anew.subMatrix(1, 1, A.getRows() - 1,
            A.getColumns() - 1));
        Matrix oldQ = Matrix.identity(A.getRows());
        Matrix oldR = Anew;
        for (int y = 1; y < A.getRows(); y++) {
            for (int x = 1; x < A.getRows(); x++) {
                oldQ.set(y, x, ((Matrix) next[0]).get(y - 1, x - 1));
                oldR.set(y, x, ((Matrix) next[1]).get(y - 1, x - 1));
            }
        }
        Matrix Q = Matrix.product(H, oldQ);
        return new Object[] {Q, oldR, new Double(0)};
    }

    /**
     * Implement QR-factorization of a n x n matrix A using Givens rotations
     * @param A the matrix
     * @return Q, R, and the error |QR-A|
     */
    public static Object[] qr_fact_givens(Matrix A) {
        throw new UnsupportedOperationException();
    }

    /**
     * Implement procedure to obtain the solution to a system Ax = b for a
     * nxn matrix A and a nx1 vector b using lu-factorization
     * IMPORTANT: the use of an inverse matrix function should
     * be avoided, your program should use backward or forward substitution;
     * using an inverse matrix defects the purpose of these methods
     * @param A the matrix
     * @param b the vector
     * @return x, the solution to the system
     */
    public static Matrix solve_lu_b(Matrix A, Vector b) {
        throw new UnsupportedOperationException();
    }

    /**
     * Implement procedure to obtain the solution to a system Ax = b for a
     * nxn matrix A and a nx1 vector b using qr-factorization
     * IMPORTANT: the use of an inverse matrix function should
     * be avoided, your program should use backward or forward substitution;
     * using an inverse matrix defects the purpose of these methods
     * @param A the matrix
     * @param b the vector
     * @return x, the solution to the system
     */
    public static Matrix solve_qr_b(Matrix A, Vector b) {
        throw new UnsupportedOperationException();
    }


}
