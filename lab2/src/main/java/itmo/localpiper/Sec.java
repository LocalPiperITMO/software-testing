package itmo.localpiper;

import static java.lang.Math.PI;
import static java.lang.Math.abs;
import static java.lang.Math.cos;

public class Sec implements Solvable {

    @Override
    public double solve(double x) {
        x = x % (2 * Math.PI);
        if (x > PI) x -= 2 * PI;
        if (x < -PI) x += 2 * PI;

        if (abs(cos(x)) < 1e-10) {
            return Double.NaN;
        }

        double cosX = 0;
        double term = 1;
        for (int i = 0; i < 20; ++i) {
            cosX += term;
            term *= - (x * x) / ((2 * i + 1) * (2 * i + 2));
        }
        return 1 / cosX;
    }
    
}
