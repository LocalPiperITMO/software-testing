package itmo.localpiper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class LnTest {

    private final static double EPSILON = 1e-9;
    private final Ln ln = new Ln();

    @ParameterizedTest
    @CsvSource({
        "1, 0.0",   // ln(1) = 0
        "2.718281828459, 1.0",  // ln(e) = 1
        "7.38905609893, 2.0",  // ln(e^2) = 2
        "0.367879441171, -1.0", // ln(1/e) = -1
        "0.135335283237, -2.0", // ln(1/e^2) = -2
        "10, 2.30258509299",  // ln(10)
        "100, 4.60517018599",  // ln(100)
        "0.1, -2.30258509299",  // ln(0.1)
        "0.01, -4.60517018599"  // ln(0.01)
    })
    void testLnRegular(double x, double expected) {
        double result = ln.solve(x);
        assertEquals(expected, result, EPSILON, "Failed at x = " + x);
    }

    @ParameterizedTest
    @CsvSource({
        "0.5", // ln(0.5) = -ln(2)
        "2",   // ln(2) = -ln(0.5)
        "0.1", // ln(0.1) = -ln(10)
        "10",  // ln(10) = -ln(0.1)
        "0.01", // ln(0.01) = -ln(100)
        "100"   // ln(100) = -ln(0.01)
    })
    void testLnInverse(double x) {
        double y1 = ln.solve(x);
        double y2 = ln.solve(1 / x);
        assertEquals(-y1, y2, EPSILON, "Failed at x = " + x);
    }

    @ParameterizedTest
    @CsvSource({
        "1.00001, 0.00000999995", // ln(1 + small num)
        "0.99999, -0.00001000005", // ln(1 - small num)
        "1e-9, -20.723265837", // very small number
        "1e9, 20.723265837",  // very large number
        "5000, 8.51719319141", // large number
        "0.0001, -9.210340372", // small number
        "0, NaN", // ln(0) is undefined
        "-1, NaN", // ln(-1) is undefined
        "-10, NaN" // ln(-10) is undefined
    })
    void testLnAny(double x, double expected) {
        double result = ln.solve(x);
        if (Double.isNaN(expected)) {
            assertTrue(Double.isNaN(result), "Expected NaN at x = " + x);
        } else {
            assertEquals(expected, result, EPSILON, "Failed at x = " + x);
        }
    }
}
