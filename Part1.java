import java.util.Scanner;
import java.util.ArrayList;
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
        //System.out.println(A.getRows() + " by " + A.getColumns());
        if (A.getRows() == 1) {
            return new Object[] {new Matrix(1, 1, new double[] {1}), A, 0};
        }
        //System.out.println(A.getColumns() == 0);
        if (A.getColumns() == 0) {
            return new Object[] {new Matrix(0, 0), A, new Double(0)};
        }
        Vector X = new Vector(A, 0);
        double mag = X.magnitude();
        X.set(0, X.get(0) + mag);
        if (mag > 0) {
            X = X.scalarMultiply(1 / X.magnitude()).toVector();
        }
        Matrix H = Matrix.identity(A.getRows());
        H = Matrix.sum(H, Matrix.product(X, X.transpose()).scalarMultiply(-2));
        //System.out.println("X: " + X);
        //System.out.println("H: " + H);
        Matrix Anew = Matrix.product(H, A);
        //At this point, we've killed all the non-zero elements in first col
        //System.out.println(Anew);
        Object[] next = qr_fact_househ(Anew.subMatrix(1, 1, A.getRows() - 1,
            A.getColumns() - 1));
        Matrix oldQ = Matrix.identity(A.getRows());
        Matrix oldR = Anew;
        for (int y = 1; y < A.getRows(); y++) {
            for (int x = 1; x < A.getColumns(); x++) {
                oldR.set(y, x, ((Matrix) next[1]).get(y - 1, x - 1));
            }
        }
        for (int y = 1; y < A.getRows(); y++) {
            for (int x = 1; x < A.getRows(); x++) {
                oldQ.set(y, x, ((Matrix) next[0]).get(y - 1, x - 1));
            }
        }
        Matrix Q = Matrix.product(H, oldQ);
        return new Object[] {Q, oldR, new Double(Q.times(oldR).plus(A.times(-1)).norm_inf())};
    }

    /**
     * Implement QR-factorization of a n x n matrix A using Givens rotations
     * @param A the matrix
     * @return Q, R, and the error |QR-A|
     */
    public static Object[] qr_fact_givens(Matrix A) {
        if (A.getRows() == 1) {
            return new Object[] {Matrix.identity(1), A, 0};
        }
        if (A.getColumns() == 0) {
            return new Object[] {new Matrix(0, 0), new Matrix(0, 0), new Double(0)};
        }
        Matrix Anew = A.subMatrix(0, 0, A.getRows(), A.getColumns());
        Matrix G = Matrix.identity(A.getRows());
        //Copyig A
        for (int i = 1; i < A.getRows(); i++) {
            double x = Anew.get(0, 0);
            double y = Anew.get(i, 0);
            double r = Math.sqrt(x * x + y * y);
            double c, s;
            if (r > 0) {
                c = x / r;
                s = -y / r;
            } else {
                c = 1;
                s = 0;
            }
            Matrix g = Matrix.identity(A.getRows());
            g.set(0, 0, c);
            g.set(i, 0, s);
            g.set(0, i, -s);
            g.set(i, i, c);
            Anew = g.times(Anew);
            G = G.times(g.transpose());
            //System.out.println("A: "+Anew);
            //System.out.println("G: "+G);
        }
        Object[] next = qr_fact_givens(Anew.subMatrix(1, 1, A.getRows() - 1,
            A.getColumns() - 1));
        Matrix oldQ = Matrix.identity(A.getRows());
        Matrix oldR = Anew;
        for (int y = 1; y < A.getRows(); y++) {
            for (int x = 1; x < A.getColumns(); x++) {
                oldR.set(y, x, ((Matrix) next[1]).get(y - 1, x - 1));
            }
        }
        for (int y = 1; y < A.getRows(); y++) {
            for (int x = 1; x < A.getRows(); x++) {
                oldQ.set(y, x, ((Matrix) next[0]).get(y - 1, x - 1));
            }
        }
        Matrix Q = Matrix.product(G, oldQ);
        return new Object[] {Q, oldR, new Double(Q.times(oldR).plus(A.times(-1)).norm_inf())};
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

    public static void main(String[] args) {
        Scanner scan = null;
        if (args.length == 0) {
            System.out.println("Gimme a file");
            System.exit(0);
        }
        try {
            scan = new Scanner(new java.io.File(args[0]));
        } catch (java.io.FileNotFoundException e) {
            System.out.println("Not a valid file");
        }
        Scanner row = null;
        int rows = 0;
        int columns = 0;
        ArrayList<Double> data = new ArrayList<>();
        while (scan.hasNextLine()) {
            int c = 0;
            row = new Scanner(scan.nextLine().replace(',',' '));
            while (row.hasNextDouble()) {

                data.add(row.nextDouble());
                c++; //wait no this is java
            }
            if (columns == 0) {
                columns = c;
            } else if (columns != c) {
                System.out.println("Invalid matrix");
                System.exit(1);
            }
            rows++;
        }
        double[] dataArray = new double[data.size()];
        for (int i = 0; i < data.size(); i++) {
            dataArray[i] = data.get(i);
        }
        Matrix A = new Matrix(rows, columns, dataArray);
        if (rows == columns) {
            //Display lu and qr and stuff
            //Object[] lu = lu_fact(A);
            Object[] househ = qr_fact_househ(A);
            Object[] givens = qr_fact_givens(A);
            /*
            //Display lu here when herbig finishes
            */
            System.out.println("Household QR");
            System.out.println("Q:");
            System.out.println(househ[0]);
            System.out.println("R:");
            System.out.println(househ[1]);
            System.out.println("|QR-A|: " + househ[2]); 
            System.out.println("\n\n\n");
            System.out.println("Givens QR");
            System.out.println("Q:");
            System.out.println(givens[0]);
            System.out.println("R:");
            System.out.println(givens[1]);
            System.out.println("|QR-A|: " + givens[2]); 
        } else if (rows + 1 == columns) {
            Vector b = A.subMatrix(0, A.getColumns() - 1, A.getRows(), 1).toVector();
            A = A.subMatrix(0, 0, A.getRows(), A.getColumns() - 1);
            //Solve augmented matrix and stuff
        } else {
            System.out.println("You gave me a " + A.getRows() + " by "
                + A.getColumns() + " matrix. I dunno what to do");
        }
    }

}
