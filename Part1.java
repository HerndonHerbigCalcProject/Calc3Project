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
        if (A == null) {
            throw new java.lang.IllegalArgumentException("Cannot take null"
                    + " arguments");
        }
        //If it's not a square matrix, break code because that's absurd
        if (A.getRows() != A.getColumns()) {
            throw new java.lang.IllegalArgumentException("Method does not "
                    + "work on a non-square matrix");
        }

        //cycle through each column on A
        int y = 0;
        int x = 0;
        int rows = A.getRows();
        int cols = A.getColumns();
        ArrayList<Matrix> operations = new ArrayList<>();
        Matrix identity;
        Matrix U = A.subMatrix(0, 0, A.getRows(), A.getColumns());

        for (int i = 0; i < U.getColumns(); i++) {
            //get a pivot in the top left corner
            int nextRow = y + 1;
            while (U.get(y, x) == 0 && nextRow < rows) {

                //If there is a row operation involved in getting the pivot,
                // record that row operation in the operations list
                if (U.get(nextRow, x) != 0) {
                    U.rowSwap(y, nextRow);
                    identity = Matrix.identity(rows);
                    identity.rowSwap(y,nextRow);
                    operations.add(identity);
                }
                nextRow++;
            }

            //The pivot value is in the top left of the submatrix
            if (U.get(y, x) != 0) {

                //Makes all the values below the pivot column 0
                for (int currRow = y + 1; currRow < rows; currRow++) {
                    double scale = -U.get(currRow, x) / U.get(y, x);
                    U.rowOperation(currRow, y, scale);
                    identity = Matrix.identity(rows);
                    identity.rowOperation(currRow, y, scale);
                    operations.add(identity);
                }
            }
            //Increment x and y, focus on the next pivot space
            x += 1;
            y += 1;
        }
        // With matrix U created, create matrix L.
        Matrix L = Matrix.identity(rows);

        //L = (I3*I2*I1...)^-1
        //L = I1^-1 * I2^-1 * I3^-1...

        //Get the inverse of all the row operation matrices
        for (Matrix I : operations) {
            for (int x1 = 0; x1 < cols; x1++) {
                for (int y1 = 0; y1 < rows; y1++) {
                    if (y1 != x1) {
                        I.set(y1, x1, -I.get(y1, x1));
                    }
                }
            }
        }

        //Multiply the inverse identity matrices together to get L.
        for (int i = 0; i < operations.size(); i++) {
            L = L.times(operations.get(i));
        }

        //Now calculate the error
        Matrix tempLU = L.times(U);
        Matrix tempA = A.times(-1);
        Matrix tempError = tempLU.plus(tempA);
        double error = tempError.norm_inf();

        //Add the data to the output array
        Object[] output = new Object[3];
        output[0] = L;
        output[1] = U;
        output[2] = error;

        return output;
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
        //Copying A
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
        if (A == null || b == null) {
            throw new java.lang.IllegalArgumentException("Cannot use "
                    + "null parameters");
        }
        Object[] LU = lu_fact(A);
        Matrix L = (Matrix) LU[0];
        Matrix U = (Matrix) LU[1];
        Vector y = new Vector(L.getRows());
        Vector x = new Vector(U.getRows());
        double aNum = 0;
        double answer = 0;

        //cycle through all the rows in L, solving for y, using b
        for (int i = 0; i < L.getRows(); i++) {

            //find the values that have already been solved for and add them
            // to the other side of the augmented matrix
            aNum = 0;
            for (int j = 0; j < i; j++) {
                aNum += L.get(i, j) * y.get(j);
            }

            aNum = b.get(i) - aNum;
            answer = aNum / L.get(i, i);

            y.set(i, answer);
        }

        //cycle through all the rows in U, solving for x, using y
        for (int i = U.getRows() - 1; i >= 0; i--) {

            //find the values that have already been solved for and add them
            // to the other side of the augmented matrix
            aNum = 0;
            for (int j = U.getColumns() - 1; j > i; j--) {
                aNum += U.get(i, j) * x.get(j);
            }

            aNum = y.get(i) - aNum;
            answer = aNum / U.get(i, i);

            x.set(i, answer);
        }
        return x;
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
    public static Vector solve_qr_b(Matrix A, Vector b) {
        Object[] qr = qr_fact_househ(A);
        Matrix Q = (Matrix) qr[0];
        Matrix R = (Matrix) qr[1];
        Vector B = Q.transpose().times(b).toVector();
        for (int i = A.getRows() - 1; i >=0; i--) {
            double tmp = 1.0 / R.get(i, i);
            R.rowScale(i, tmp);
            B.rowScale(i, tmp);
            for (int j = i - 1; j >=0; j--) {
                tmp = -R.get(j, i);
                R.rowOperation(j, i, tmp);
                B.rowOperation(j, i, tmp);
            }
        }
        //System.out.println(R);
        return B;
    }
    /**
     * See README.md for use
     * @param args command line arguments
     */
    public static void main(String[] args) {
        Scanner scan = null;
        java.io.PrintWriter writer = null;
        if (args.length == 0) {
            try {
            writer = new java.io.PrintWriter("PascalResults.csv", "UTF-8");
            } catch (Exception e) {
                System.out.println(e.getMessage());
                System.exit(1);
            }
            writer.println("Size,LU error, QR error H, QR error G, Solution error");
            for (int n = 2; n <= 12; n++) {
                Matrix P = pascal(n);
                double bData[] = new double[n];
                for (int i = 1; i <=n; i++) {
                    bData[i - 1] = 1.0 / i;
                }
                Vector b = new Vector(bData);
                Matrix lu = solve_lu_b(P, b);
                Matrix qr = solve_qr_b(P, b);
                Object[] lu_err = lu_fact(P);
                Object[] qr_err_g = qr_fact_givens(P);
                Object[] qr_err_h = qr_fact_househ(P);
                double sol_error = P.times(lu).plus(b.times(-1))
                    .norm_inf();
                writer.println(n + "," + lu_err[2] + "," + qr_err_h[2]
                    + "," + qr_err_g[2] + "," + sol_error);
                System.out.println("Solution: " + lu);
                System.out.println("|LU-P|: " + lu_err[2]);
                System.out.println("Household |QR-P|: " + qr_err_h[2]);
                System.out.println("Givens |QR-P|: " + qr_err_g[2]);
                System.out.println("|Pxsol-b|: " + sol_error);
                System.out.println("\n\n");
            }
            writer.close();
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
            Object[] lu = lu_fact(A);
            Object[] househ = qr_fact_househ(A);
            Object[] givens = qr_fact_givens(A);
            System.out.println("LU Factorization");
            System.out.println("L:");
            System.out.println(lu[0]);
            System.out.println("U:");
            System.out.println(lu[1]);
            System.out.println("|LU-A|: " + lu[2]);
            System.out.println("\n\n\n");
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
//        Matrix a = new Matrix(4, 4, new double[] {1,1,1,1,1,2,3,4,1,3,6,10,1,4,10,20});
//        Vector b = new Vector(new double[] {1, .5, (double) 1/3, .25});
//        System.out.println("b = " + b);
//        System.out.println(solve_lu_b(a, b));

    }

}
