import java.util.ArrayList;

/**
 * In this part you'll investigate the convergence of the power method for
 * calculating eigenvalues and eigenvectors of randomly generated 2×2 matrices.
 * @version 1.0
 * @author JT Herndon 
 * @author Alex Herbig
 */
public class Part3 {
    /**
     * Implement a procedure named power_method that uses the power method to 
     * approximate an eigenvalue and associated eigenvector of an n x n matrix.
     * @param A an n × n matrix A with floating-point real numbers as entries 
     *          (the algorithm should work for n ≥ 2)
     * @param v a vector of n floating-point real numbers that serves as the
     *          initial guess for an eigenvector of A
     * @param e a tolerance parameter ε (a positive floating-point real number) 
     *          that determines when the approximation is close enough
     * @param N  a positive integer N giving the maximum number of times to 
     *          iterate the power method before quitting.
     * @return an eigenvalue and associated eigenvector and number of iterations,
     *          or an Object array with null values if it failed
     */
    public static Object[] power_method(Matrix A, Vector v, double e, int N) {
        if (A.getColumns() != v.getRows()) {
            throw new java.lang.IllegalArgumentException("the input matrix and "
                    + "vector cannot be used together (mismatch dimensions)");
        }
        if (A == null || v == null) {
            throw new java.lang.IllegalArgumentException("Null parameters "
                    + "for matrix or vector");
        }
        if (A.getColumns() != A.getRows()) {
            throw new java.lang.IllegalArgumentException("Not a square matrix");
        }

        int rows = A.getRows();
        int cols = A.getColumns();
        double error = 100;
        int counter = 0;
        ArrayList<Matrix> eigenvectors = new ArrayList<>();
        ArrayList<Double> eigenvalues = new ArrayList<>();
        Object[] output = new Object[3];
        double prevEigen = Double.MAX_VALUE;
        double eigenvalue = 0;

        //Initialize the first x vector to be used in process.
        double[] xData = new double[cols];
        for (int i = 0; i < xData.length; i++) {
            xData[i] = 1;
        }
        Vector x = new Vector(xData);


        while (error > e && counter < N) {
            //Uses the Rayleigh quotient for finding eigenvalues
            Matrix temp = A.times(x);
            Vector xTemp = new Vector(temp, 0);
            double numerator = Vector.dotProduct(xTemp, x);
            double denominator = Vector.dotProduct(x, x);
            eigenvalue = numerator / denominator;
            eigenvalues.add(eigenvalue);
            eigenvectors.add(x);
            x = xTemp;
            counter++;
            error = Math.abs(eigenvalue - prevEigen);
            prevEigen = eigenvalue;
        }

//        for (int i = 1; i < eigenvalues.size(); i++) {
//            System.out.println("Eigen Value " + (i) + ": " + eigenvalues.get(i));
//            System.out.print("Eigen Vector " + (i) + ": " + eigenvectors.get(i));
//            System.out.println("-----------------");
//        }

        //Algorithm did not converge in time
        if (!(counter == N && error > e)) {
            output[0] = eigenvalue;
            output[1] = x.unit();
            output[2] = counter;
        }

        return output;
    }

    public static void main(String[] args) {

        System.out.println("Test Number,Max Eigenvalue,"
                + "Iterations for Max,Min Eigenvalue,Iterations for Min,"
                + "Trace,Determinant");
        java.util.Random rand = new java.util.Random();
        for (int i = 0; i < 1000; i++) {

            StringBuilder printme = new StringBuilder((i + 1) + ",");
            double[] x = new double[4];
            boolean done = false;

            while (!done) {
                for (int j = 0; j < 4; j++) {
                    x[j] = rand.nextDouble() * 4 - 2;
                }
                if ((x[0] * x[3] - x[1] * x[2]) == 0) {
                    done = false;
                } else {
                    done = true;
                }
            }

            Matrix x0 = new Matrix(2, 2, x);

            //creates an inverse matrix to find min eigenvalue
            Matrix xI = x0.times(1.0 / (x[0] * x[3] - x[1] * x[2]));
            double temp = xI.get(0, 0);
            xI.set(0, 0, xI.get(1, 1));
            xI.set(1, 1, temp);
            xI.set(0, 1, -xI.get(0, 1));
            xI.set(1, 0, -xI.get(1, 0));

            Vector guess = new Vector(new double[] {1, 1});
            Object[] answers = power_method(x0, guess, .00005, 100);
            Object[] answersInverse = power_method(xI, guess, .00005, 100);

            //Check to see valid output
            if (answers[0] == null) {
                printme.append("null,infinity,");
            } else {
                printme.append(answers[0] + ",");
                printme.append(answers[2] + ",");
            }

            //Check to see valid output again
            if (answersInverse[0] == null) {
                printme.append("null,infinity,");
            } else {
                double tempdub = 1.0 / (double) answersInverse[0];
                printme.append(tempdub + ",");
                printme.append(answersInverse[2] + ",");
            }

            printme.append(x0.trace() + ",");
            printme.append(x0.determinant() + ",");

            System.out.println(printme.toString());

        }

//        Matrix A = new Matrix(3, 3, new double[] {1,2,0,-2,1,2,1,3,1});
//        Vector v = new Vector(new double[] {1,1,1});
//
//        Object[] ans = power_method(A, v, .00005, 100);
//        System.out.println("Power Method: " + ans[0] + ans[1] + ans[2]);
    }
}
