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
    }

    /**
     * Implement the LU decomposition method for a n x n matrix A
     * @param A the matrix
     * @return L, U, and the error |LU-A|
     */
    public static Object[] lu_fact(Matrix A) {
    }

    /**
     * Implement QR-factorization of a n x n matrix A using Householder
     * reflections
     * @param A the matrix
     * @return Q, R, and the error |QR-A|
     */
    public static Object[] qr_fact_househ(Matrix A) {
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
    }


}
