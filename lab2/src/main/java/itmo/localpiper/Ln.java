package itmo.localpiper;

public class Ln implements Solvable {
    
    private static final double LN2 = 0.6931471805599453;

    @Override
    public double solve(double x) {
        if (x <= 0) return Double.NaN;

        int exp = 0;
        while (x > 2) {
            x /= 2;
            exp++;
        }
        while (x < 1) {
            x *= 2;
            exp--;
        }

        double y = (x - 1) / (x + 1);
        double y2 = y * y;
        double sum = 0;
        double term = y;
        int n = 1;

        for (int i = 0; i < 30; ++i) {
            sum += term / n;
            term *= y2;
            n += 2;
        }
        return 2 * sum + exp * LN2;
    }
}

