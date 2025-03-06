package itmo.localpiper;

public class Cos implements Solvable {

    private final Sec sec = new Sec();

    @Override
    public double solve(double x) {
        return 1 / sec.solve(x);
    }
    
}
