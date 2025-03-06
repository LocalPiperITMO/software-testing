package itmo.localpiper;

public class Log implements Solvable {

    private final Ln ln = new Ln();

    @Override
    public double solve(double x) {
        return ln.solve(x);
    }

    public double solve(double x, int base) {
        return solve(x) / solve(base);
    }
}
