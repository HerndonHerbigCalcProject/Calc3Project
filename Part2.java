/**
 * A class for part 2: Convergence of the iterative methods
 * In this part, you will use both Jacobi and Gauss-Seidel method to obtain the
 * approximate solution to a system Ax = b, for 3×3 symmetric matrix
 * @version 1.0
 * @author JT Herndon
 * @author Alex Herbig
 */
public class Part2 {
    private static final Matrix A = new Matrix(3, 3, new double[] {1.0, 1.0/2,
        1.0/3, 1.0/2, 1.0, 1.0/4, 1.0/3, 1.0/4, 1.0});
    private static final Vector b = new Vector(new double[] {0.1, 0.1, 0.1});
    private static final Vector xExact = new Vector(new double[] {9.0/190,
        28.0/475, 33.0/475});

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
            return new Object[] {null, new Integer(0)};
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
        //if (M == 1) {
            //System.out.println(S);
            //System.out.println(T);
        //}
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
            return new Object[] {null, new Integer(total)};
        }
        return new Object[] {x0, new Integer(total)};
    }
    /**
     * Randomly generate at least 100 3×1 initial vectors x0. The entries of the
     * vectors should be floating-point real numbers uniformly distributed in
     * the interval [−1, 1]. For each x0 • Record x0. • Use both jacobi_iter and
     * gs_iter to find the approximation solution of Ax = b within a tolerance
     * of 5 decimal places (kxn − xn−1k∞ ≤ ε = 0.00005). Use a maximum of
     * M = 100 iterations before quitting in failure. • Record xN and number of
     * iterations N in both methods. (c) Average these 100 xN from (b) to get an
     * approximation solution xapprox. Compute the error of this approximation
     * to the exact solution xexact = (9/190 28/475 33/475 ), that is kxapprox
     * − xexactk∞, for both iterative methods. Average the ratio of number of
     * iteration steps N between Jacobi and Gauss-Seidel method, that is the
     * average of the ratio NJacobi/NGauss−Seidel.
     * 
     * Use of this program: in the command line type
     * ~$ javac Part2.java
     * to compile the program
     * 
     * Then type:
     * ~$ java Part2 > results.csv
     * This will make a new csv file of the data
     */
    public static void main(String[] args) {
        //System.out.println(A);
        //System.out.println(b);
        Object[] test1 = jacobi_iter(new Vector(new double[] {0, 0, 0}), 0.0005,
             30);
        //System.out.println("Vector result:\n"+test1[0]);
        //System.out.println("Iterations required: " + test1[1]);
        Object[] test2 = gs_iter(new Vector(new double[] {0,0,0}), 0.000005, 30);
        //System.out.println("Vector result: " + ((Matrix)test2[0]).transpose());
        //System.out.println("Iterations required: " + test2[1]);
        System.out.println("i,x01,x02,x03,|x0-xExact|,xN1j,xN2j,xN3j,Nj,xN1g,"
            + "xN2g,xN3g,Ng,");
        java.util.Random rand = new java.util.Random();
        Vector total_jacobi = new Vector(3);
        Vector total_gs = new Vector(3);
        int jacobi_iters = 0;
        int gs_iters = 0;
        for (int i = 0; i < 100; i++) {
            StringBuilder printme = new StringBuilder(i + ",");
            double[] x = new double[3];
            for (int j = 0; j < 3; j++) {
                x[j] = rand.nextDouble() * 2 - 1;
                printme.append(x[j] + ",");
            }
            Vector x0 = new Vector(x);
            printme.append(x0.plus(xExact.times(-1)).norm_inf() + ",");
            Object[] jacobi = jacobi_iter(x0,0.00005,100);
            if (jacobi[0] == null) {
                printme.append("null,null,null,infinity");
            } else {
                printme.append(jacobi[0].toString().replaceAll("\n", ""));
                printme.append(jacobi[1] + ",");
                total_jacobi = total_jacobi.plus((Matrix) jacobi[0]).toVector();
            }
            Object[] gs = gs_iter(x0, 0.00005, 100);
            if (gs[0] == null) {
                printme.append("null,null,null,infinity");
            } else {
                printme.append(gs[0].toString().replaceAll("\n", ""));
                printme.append(gs[1] + ",");
                total_gs = total_gs.plus((Matrix) gs[0]).toVector();
            }
            System.out.println(printme.toString());
            jacobi_iters += (int) jacobi[1];
            gs_iters += (int) gs[1];
        }
        total_jacobi = total_jacobi.times(1.0/100).toVector();
        total_gs = total_gs.times(0.01).toVector();
        double ratio = ((double) jacobi_iters) / gs_iters;
        System.out.println("Averages: ");
        System.out.println("Jacobi:" + total_jacobi.toString().trim());
        System.out.println("Error:," + total_jacobi.plus(xExact.times(-1)).norm_inf());
        System.out.println("Gauss-Seidal:\n" + total_gs.toString().trim());
        System.out.println("Error:," + total_gs.plus(xExact.times(-1)).norm_inf());
        System.out.println("Njacobi / Ngs," + ratio);
        System.out.println(xExact);
    }
}
