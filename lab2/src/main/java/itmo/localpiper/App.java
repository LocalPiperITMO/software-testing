package itmo.localpiper;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void aboba() {
        Function f = new Function(new Log(), new Ln(), new Cos(), new Sec());
        double[] x = new double[100];

        try (BufferedReader br = new BufferedReader(new FileReader("in/x.txt"))) {
            int i = 0;
            for (double val : br.lines().mapToDouble(Double::parseDouble).toArray()) {
                x[i++] = val;
            }
        } catch (IOException e) {
        }

        double[][] res = new double[100][2];
        for (int i = 0; i < 100; ++i) {
            res[i][0] = x[i];
            res[i][1] = f.solve(x[i]);
        }
        CSVProcessor processor = new CSVProcessor();
        processor.writeCSV(res);
    }
    public static void main( String[] args )
    {
        // aboba();
        Function f = new Function(new Log(), new Ln(), new Cos(), new Sec());
        System.out.println(f.solve(1.111));
    }
}
