package itmo.localpiper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class SecTest {
    
    private final static double EPSILON = 1e-9;
    private final Sec sec = new Sec();

    @ParameterizedTest
    @CsvSource({
        "0, 1.0", // 0
        "0.523598775598, 1.15470053838", // π/6
        "0.7853981633974483, 1.41421356237", // π/4
        "1.0471975512, 2", // π/3
        "1.57079632679, NaN",  // π/2
        "3.141592653589793, -1.0",  // π
        "4.71238898038, NaN", // 3π/2
        "6.283185307179586, 1.0",  // 2π
    })
    void testSecRegular(double x, double expected) {
        double result = sec.solve(x);
        if (Double.isNaN(expected)) {
            assertTrue(Double.isNaN(result), "Expected NaN at x = " + x);
        } else {
            assertEquals(expected, result, EPSILON, "Failed at x = " + x);
        }
    }

    @ParameterizedTest
    @CsvSource({
        "-0.523598775598", // π/6
        "-0.7853981633974483", // π/4
        "-1.0471975512", // π/3
        "-1.57079632679",  // π/2
        "-3.141592653589793",  // π
        "-4.71238898038", // 3π/2
        "-6.283185307179586", // 2π
    })
    void testSecParity(double x) {
        double y1 = sec.solve(x);
        double y2 = sec.solve(-x);
        assertEquals(y1, y2, EPSILON, "Failed at x = " + x);
    }

    @ParameterizedTest
    @CsvSource({
        "0.00001, 1.00000000005", // smallnum
        "-0.00001, 1.00000000005", // -smallnum
        "1.56079632679, 100.001666637",  // < π/2
        "1.58079632679, -100.001666735", // > π/2
        "3.13159265359, -1.00005000208",  // < π
        "3.15159265359, -1.00005000208",  // > π
        "7.85398163397, NaN",  // 5π/2
        "-7.85398163397, NaN", // -5π/2
        "5000, 6.46544452544",  // bignum
        "-5000, 6.46544452544", // -bignum
        "1e7, -1.10220725291", // verybignum
        "-1e7, -1.10220725291" // -verybignum
    })
    void testSecAny(double x, double expected) {
        double result = sec.solve(x);
        assertEquals(expected, result, EPSILON, "Failed at x = " + x);
    }

}