package com.connectgroup;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class DataFilterer {

	private static double average;

	/**
	 * @param avg
	 */
	public static void setAverage(double avg) {
		average = avg;
	}

	/**
	 * @return double
	 */
	public static double getAverage() {
		return average;
	}

	/**
	 * @param source
	 * @param country
	 * @return
	 */
	public static Collection<?> filterByCountry(Reader source, String country) {
		BufferedReader br = new BufferedReader(source);
		String line = null;
		Collection<String> additionalList = new ArrayList<String>();
		int iteration = 0;
		try {
			while ((line = br.readLine()) != null) {
				// Removing header from the input data.
				if (iteration == 0) {
					iteration++;
					continue;
				}
				String[] myArray = line.split(",");
				List<String> myList = new ArrayList<String>(Arrays.asList(myArray));
				if (myList.contains(country)) {
					additionalList.addAll(myList);
				}
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return additionalList;
	}

	/**
	 * @param source
	 * @param country
	 * @param limit
	 * @return
	 */
	public static Collection<?> filterByCountryWithResponseTimeAboveLimit(Reader source, String country, long limit) {

		BufferedReader br = new BufferedReader(source);
		String line = null;
		Collection<String> additionalList = new ArrayList<String>();
		int iteration = 0;
		long count = 0;
		long responseTime = 0;
		try {
			while ((line = br.readLine()) != null) {
				// Removing header from the input data
				if (iteration == 0) {
					iteration++;
					continue;
				}
				String[] myArray = line.split(",");

				List<String> myList = new ArrayList<String>(Arrays.asList(myArray));
				for (String eachval : myArray) {
					// Finding the RESPONSE TIME from the input line
					boolean isNumeric = eachval.chars().allMatch(x -> Character.isDigit(x));
					if (isNumeric) {
						count = eachval.chars().count();
						// Identifying between RESPONSETIME and
						// REQUEST_TIMESTAMP.Unix Timestamp will be always 10
						// digits or 13 digits.And RESPONSE TIME will be less
						// than 10 digits.
						if (count < 10) {
							responseTime = Integer.parseInt(eachval);
							if (myList.contains(country)) {
								if (responseTime > limit) {
									additionalList.addAll(myList);
								}
							}

						}
					}
				}
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return additionalList;

	}

	/**
	 * @param source
	 * @return
	 */
	public static Collection<?> filterByResponseTimeAboveAverage(Reader source) {
		BufferedReader br = new BufferedReader(source);
		String line = null;
		double average = 0.0;
		Collection<String> additionalList = new ArrayList<String>();
		average = getAverage();

		long responseTime = 0;
		int iteration = 0;
		long count = 0;
		String[] myArray = null;
		try {
			while ((line = br.readLine()) != null) {
				// Removing header from the input data.
				if (iteration == 0) {
					iteration++;
					continue;
				}

				myArray = line.split(",");
				List<String> myList = new ArrayList<String>(Arrays.asList(myArray));
				for (String eachval : myArray) {
					// Finding the RESPONSE TIME from the input line
					boolean isNumeric = eachval.chars().allMatch(x -> Character.isDigit(x));
					if (isNumeric) {
						count = eachval.chars().count();
						// Identifying between RESPONSETIME and
						// REQUEST_TIMESTAMP.Unix Timestamp will be always 10
						// digits or 13 digits.And Response Time will be less
						// than 10 digits.
						if (count < 10) {
							responseTime = Integer.parseInt(eachval);
							if (responseTime > average) {
								additionalList.addAll(myList);
							}
						}
					}
				}

			}

		} catch (IOException e) {
			e.printStackTrace();
		}

		return additionalList;

	}
}