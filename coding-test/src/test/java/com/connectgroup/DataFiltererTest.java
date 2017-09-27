package com.connectgroup;

import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class DataFiltererTest {

	@Test
	public void shouldReturnEmptyCollection_WhenLogFileIsEmpty() throws FileNotFoundException {
		assertTrue(DataFilterer.filterByCountry(openFile("src/test/resources/empty"), "GB").isEmpty());
		assertTrue(DataFilterer
				.filterByCountryWithResponseTimeAboveLimit(openFile("src/test/resources/empty"), "GB", 10).isEmpty());
		assertTrue(DataFilterer.filterByResponseTimeAboveAverage(openFile("src/test/resources/empty")).isEmpty());
	}

	@Test
	public void shouldReturnCollection_WhenLogFileIsNotEmpty() throws FileNotFoundException {
		String[] myArray = { "1433190845", "US", "539", "1433666287", "US", "789", "1432484176", "US", "850" };
		Collection<?> expected = new ArrayList<String>(Arrays.asList(myArray));
		assertEquals(expected, DataFilterer.filterByCountry(openFile("src/test/resources/multi-lines"), "US"));
		assertEquals(expected, DataFilterer
				.filterByCountryWithResponseTimeAboveLimit(openFile("src/test/resources/multi-lines"), "US", 500));
		double average = getAverageForFilter("src/test/resources/multi-lines");
		DataFilterer.setAverage(average);
		assertEquals(expected,
				DataFilterer.filterByResponseTimeAboveAverage(openFile("src/test/resources/multi-lines")));
	}

	/**
	 * @param filename
	 * @return average
	 * @throws FileNotFoundException
	 */
	private double getAverageForFilter(String filename) throws FileNotFoundException {
		return new Average().findAverage(openFile(filename));
	}

	/**
	 * @param filename
	 * @return FileReader
	 * @throws FileNotFoundException
	 */
	private FileReader openFile(String filename) throws FileNotFoundException {
		return new FileReader(new File(filename));
	}
}
