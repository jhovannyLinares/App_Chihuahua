package mx.morena.presentacion.controlador;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.stereotype.Service;

@Service
public class CsvService {

	private static final String[] HEADERS = { "Nombre", "Apellido Materno" };
	private static final CSVFormat FORMAT = CSVFormat.DEFAULT.withHeader(HEADERS);

	// load data into csv
	public ByteArrayInputStream load(final List<Resident> residents) {
		return writeDataToCsv(residents);
	}

	// write data to csv
	private ByteArrayInputStream writeDataToCsv(final List<Resident> residents) {
		System.out.println("Writing data to the csv printer");

		try (final ByteArrayOutputStream stream = new ByteArrayOutputStream();
				final CSVPrinter printer = new CSVPrinter(new PrintWriter(stream), FORMAT)) {

			for (final Resident resident : residents) {

				final List<String> data = Arrays.asList(String.valueOf(resident.getNombre()), resident.getaMaterno());

				printer.printRecord(data);

			}

			printer.flush();
			return new ByteArrayInputStream(stream.toByteArray());
		} catch (final IOException e) {
			throw new RuntimeException("Csv writing error: " + e.getMessage());
		}
	}
}