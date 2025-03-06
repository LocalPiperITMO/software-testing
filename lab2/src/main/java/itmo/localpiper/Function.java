package itmo.localpiper;

import static java.lang.Math.pow;

public class Function {

    private final Log log;
    private final Ln ln;
    private final Cos cos;
    private final Sec sec;

    public Function(Log log, Ln ln, Cos cos, Sec sec) {
        this.log = log;
        this.ln = ln;
        this.cos = cos;
        this.sec = sec;
    }
    
    
    public double solve(double x) {
        if (x > 0) {
            return (pow(pow((log.solve(x, 10) + log.solve(x, 3)) - ln.solve(x), 2), 2)) * log.solve(x, 2);
        }
        return (sec.solve(x) / cos.solve(x)) / sec.solve(x);
    }
}
