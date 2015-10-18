public class Vector extends Matrix{
    public Vector(int size) {
        super(size, 1);
    }
    public Vector(double[] data) {
        super (data.length, 1, data);
    }
}
