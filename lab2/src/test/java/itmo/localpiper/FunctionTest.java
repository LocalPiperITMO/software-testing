package itmo.localpiper;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class FunctionTest {

    private final static double EPSILON = 1e-8;

    private final static double[] xValues = new double[100];

        
    private static Cos cosMock;
    private static Sec secMock;
    private static Log logMock;
    private static Ln lnMock;
    private static Function functionMock;
    
    @BeforeAll
    @SuppressWarnings("unused")
    static void setupMocks() {
        cosMock = Mockito.mock(Cos.class);
        secMock = Mockito.mock(Sec.class);
        lnMock = Mockito.mock(Ln.class);
        logMock = Mockito.mock(Log.class);
        functionMock = new Function(logMock, lnMock, cosMock, secMock);
        try (BufferedReader br = new BufferedReader(new FileReader("in/x.txt"))) {
            int i = 0;
            for (double val : br.lines().mapToDouble(Double::parseDouble).toArray()) {
                xValues[i++] = val;
            }
        } catch (IOException e) {
        }

        CSVProcessor csvProcessor = new CSVProcessor();
        for (double[] row : csvProcessor.readCSV("in/sec_values.csv")) {
            Mockito.lenient().when(secMock.solve(row[0])).thenReturn(row[1]);
        }
        for (double[] row : csvProcessor.readCSV("in/cos_values.csv")) {
            Mockito.lenient().when(cosMock.solve(row[0])).thenReturn(row[1]);
        }
        for (double[] row : csvProcessor.readCSV("in/ln_values.csv")) {
            Mockito.lenient().when(lnMock.solve(row[0])).thenReturn(row[1]);
        }
        for (double[] row : csvProcessor.readCSV("in/log2_values.csv")) {
            Mockito.lenient().when(logMock.solve(row[0], 2)).thenReturn(row[1]);
        }
        for (double[] row : csvProcessor.readCSV("in/log3_values.csv")) {
            Mockito.lenient().when(logMock.solve(row[0], 3)).thenReturn(row[1]);
        }
        for (double[] row : csvProcessor.readCSV("in/log10_values.csv")) {
            Mockito.lenient().when(logMock.solve(row[0], 10)).thenReturn(row[1]);
        }
    }

    @Test
    void testSec() {
        Function f = new Function(logMock, lnMock, cosMock, new Sec());
        for (double x : xValues) {
            assertEquals(f.solve(x), functionMock.solve(x), EPSILON);
        }
    }

    @Test
    void testCos() {
        Function f = new Function(logMock, lnMock, new Cos(), secMock);
        for (double x : xValues) {
            assertEquals(f.solve(x), functionMock.solve(x), EPSILON);
        }
    }

    @Test
    void testLn() {
        Function f = new Function(logMock, new Ln(), cosMock, secMock);
        for (double x : xValues) {
            assertEquals(f.solve(x), functionMock.solve(x), EPSILON);
        }
    }

    @Test
    void testLog() {
        Function f = new Function(new Log(), lnMock, cosMock, secMock);
        for (double x : xValues) {
            assertEquals(f.solve(x), functionMock.solve(x), EPSILON);
        }
    }

    @Test
    void testSecAndCos() {
        Function f = new Function(logMock, lnMock, new Cos(), new Sec());
        for (double x : xValues) {
            assertEquals(f.solve(x), functionMock.solve(x), EPSILON);
        }
    }

    @Test
    void testSecAndLn() {
        Function f = new Function(logMock, new Ln(), cosMock, new Sec());
        for (double x : xValues) {
            assertEquals(f.solve(x), functionMock.solve(x), EPSILON);
        }
    }

    @Test
    void testSecAndLog() {
        Function f = new Function(new Log(), lnMock, cosMock, new Sec());
        for (double x : xValues) {
            assertEquals(f.solve(x), functionMock.solve(x), EPSILON);
        }
    }

    @Test
    void testCosAndLn() {
        Function f = new Function(logMock, new Ln(), new Cos(), secMock);
        for (double x : xValues) {
            assertEquals(f.solve(x), functionMock.solve(x), EPSILON);
        }
    }

    @Test
    void testCosAndLog() {
        Function f = new Function(new Log(), lnMock, new Cos(), secMock);
        for (double x : xValues) {
            assertEquals(f.solve(x), functionMock.solve(x), EPSILON);
        }
    }

    @Test
    void testLnAndLog() {
        Function f = new Function(new Log(), new Ln(), cosMock, secMock);
        for (double x : xValues) {
            assertEquals(f.solve(x), functionMock.solve(x), EPSILON);
        }
    }

    @Test
    void testCosSecAndLn() {
        Function f = new Function(logMock, new Ln(), new Cos(), new Sec());
        for (double x : xValues) {
            assertEquals(f.solve(x), functionMock.solve(x), EPSILON);
        }
    }

    @Test
    void testCosSecAndLog() {
        Function f = new Function(new Log(), lnMock, new Cos(), new Sec());
        for (double x : xValues) {
            assertEquals(f.solve(x), functionMock.solve(x), EPSILON);
        }
    }

    @Test
    void testSecLnAndLog() {
        Function f = new Function(new Log(), new Ln(), cosMock, new Sec());
        for (double x : xValues) {
            assertEquals(f.solve(x), functionMock.solve(x), EPSILON);
        }
    }

    @Test
    void testCosLnAndLog() {
        Function f = new Function(new Log(), new Ln(), new Cos(), secMock);
        for (double x : xValues) {
            assertEquals(f.solve(x), functionMock.solve(x), EPSILON);
        }
    }

    @Test
    void testAll() {
        Function f = new Function(new Log(), new Ln(), new Cos(), new Sec());
        for (double x : xValues) {
            assertEquals(f.solve(x), functionMock.solve(x), EPSILON);
        }
    }

    @ParameterizedTest
    @CsvSource({
        "-6.283185307179586, 1.0", // 2pi
        "-3.141592653589793, -1.0", // pi
        "0, 1.0", // trigonometric border
        "0.0001, -1347.3481010430450541", // 1st sector
        "0.95345, 0", // 2nd-start
        "1, 0", // logarithmic part
        "1.03053, 0", // 2nd-end
        "50, 18.6255203575057" // 3rd sector
    })
    void testPoints(double x, double expected) {
        Function f = new Function(new Log(), new Ln(), new Cos(), new Sec());
        assertEquals(expected, f.solve(x), EPSILON);
    }
}
