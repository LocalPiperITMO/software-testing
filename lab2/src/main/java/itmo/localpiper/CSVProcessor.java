package itmo.localpiper;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvValidationException;

public class CSVProcessor {
    double[][] readCSV(String path) {
        double[][] data = new double[100][2];

        try (CSVReader reader = new CSVReader(new FileReader(path))) {
            String[] values;
            reader.readNext();
            for (int i = 0; i < 100; ++i) {
                values = reader.readNext();
                double x = Double.parseDouble(values[0].trim());
                double y = ("nan".equals(values[1].trim()))? Double.NaN : Double.parseDouble(values[1].trim());
                data[i][0] = x;
                data[i][1] = y;
            }
        } catch (IOException | CsvValidationException e) {
        }

        return data;
    }

    void writeCSV(double[][] data) {
        String dest = "out.csv";
        try (CSVWriter writer = new CSVWriter(new FileWriter(dest), CSVWriter.DEFAULT_SEPARATOR, CSVWriter.NO_QUOTE_CHARACTER, CSVWriter.DEFAULT_ESCAPE_CHARACTER, CSVWriter.DEFAULT_LINE_END)) {
            for (double[] row : data) {
                writer.writeNext(new String[]{String.valueOf(row[0]), String.valueOf(row[1])});
            }
        } catch (IOException e) {
        }
    }
}

