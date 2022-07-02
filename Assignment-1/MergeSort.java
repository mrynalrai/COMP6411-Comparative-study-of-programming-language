import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;


/**
 * Implementation for Merge Sort.
 * Record execution time for each reading data file into list, sorting data, 
 * and writing sorted list in file.
 * Record memory usage and number of recursion and number of merge occurs during the sorting.
 * 
 * @author Seung Hyun Hong, Nastaran Naseri
 */
public class MergeSort {

	/** The Constant MEGABYTE. */
	private static final long MEGABYTE = 1024L * 1024L;
	
	/** The Constant SECOND. */
	private static final long SECOND = 1000000000;
	
	/** The recursion count. */
	private static long recCount = 0;
	
	/** The merge count. */
	private static long merCount = 0;
	
	/** The Constant runtime. */
	static final Runtime runtime = Runtime.getRuntime();

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		
		String outputFile = "output_merge_java";
		
		double startTimeReading = System.nanoTime();
		// Read data from file and get ArrayList of data
		ArrayList<Long> arr = readData("rand.txt");
		double endTimeReading = System.nanoTime();
		double durationReading = (endTimeReading - startTimeReading);
		System.out.println("Reading file excution time " + durationReading / SECOND + " seconds");

		double startTimeSorting = System.nanoTime();
		// Do mergeSort
		sort(arr, 0, arr.size() - 1);
		double endTimeSorting = System.nanoTime();
		double durationSorting = (endTimeSorting - startTimeSorting);
		System.out.println("Sorting excution time " + durationSorting / SECOND + " seconds");

		double startTimeWriting = System.nanoTime();
		// Write sorted data to file
		writeData(arr, outputFile);
		double endTimeWriting = System.nanoTime();
		double durationWriting = (endTimeWriting - startTimeWriting);
		System.out.println("Writing file excution time " + durationWriting / SECOND + " seconds");
		
		// run garbage collector before calculate memory usage
		runtime.gc();
		// get memory usages
		long memory = runtime.totalMemory() - runtime.freeMemory();

		ArrayList<String> stat = new ArrayList<String>();
		stat.add("\n\nStatistics for Merge Sort with JAVA\n");
		stat.add("Number of elements: " + arr.size());
		stat.add("Reading file excution time " + durationReading / SECOND + " seconds");
		stat.add("Sorting excution time " + durationSorting / SECOND + " seconds");
		stat.add("Writing file excution time " + durationWriting / SECOND + " seconds");
		stat.add("Used memory is megabytes: " + memory / MEGABYTE);
		stat.add("Number of Recursion: " + recCount);
		stat.add("Number of Merge: " + merCount);

		System.out.println("Used memory is megabytes: " + memory / MEGABYTE);
		System.out.println("Number of Recursion: " + recCount);
		System.out.println("Number of Merge: " + merCount);
		
		// write stats in to existing output file
		writeStat(stat);

//		ArrayList<Long> arr2 = readData("rand.txt");
//		getTimes(arr2);

	}

	/**
	 * Read raw data from text file.
	 *
	 * @param name the file name
	 * @return the array list
	 */
	public static ArrayList<Long> readData(String name) {

		ArrayList<Long> arr = new ArrayList<Long>();
		try {
			Scanner fis = new Scanner(new FileInputStream(name));
			// read each line
			while (fis.hasNextLine()) {
				arr.add(fis.nextLong());
			}
			fis.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return arr;

	}

	/**
	 * Write sorted data into text file.
	 *
	 * @param <T> the generic type
	 * @param arr the sorted data array list
	 * @param name the output file name
	 */
	public static <T> void writeData(ArrayList<T> arr, String name) {
		try {
			FileWriter myWriter = new FileWriter(name + ".txt");
			for (int i = 0; i < arr.size(); i++) {
				myWriter.write(arr.get(i) + "\n");
			}
			myWriter.close();
			System.out.println("Output file created ");
		} catch (IOException e) {
			System.out.println("error");
			e.printStackTrace();
		}
	}

	/**
	 * Helper function to get sorting execution time.
	 * Take ArrayList of data and run every 50000 elements 
	 * and get sorting execution time.
	 *
	 * @param arr the ArrayList of data
	 * @return the times in seconds
	 */
	public static void getTimes(ArrayList<Long> arr) {
		ArrayList<String> times = new ArrayList<String>();
		// index has problem-> last part of the array
		for (int i = 50000; i < arr.size(); i += 50000) {
			double startTime = System.nanoTime();
			// mergeSort
			sort(arr, 0, i);
			double endTime = System.nanoTime();
			double duration = (endTime - startTime);
			times.add(i + "\t" + duration / SECOND);
		}
		double startTime = System.nanoTime();
		// mergeSort for rest of data
		sort(arr, 0, arr.size() - 1);
		double endTime = System.nanoTime();
		double duration = (endTime - startTime);
		times.add(arr.size() + "\t" + duration / SECOND);
		System.out.println(arr.size() + "  " + duration / SECOND);
	}

	/**
	 * Write stats in exist out put file.
	 *
	 * @param arr the ArrayList of stats
	 */
	public static void writeStat(ArrayList<String> arr) {
		PrintWriter out = null;
		try {
			out = new PrintWriter(new BufferedWriter(new FileWriter("output_merge_java.txt", true)));
			for (int i = 0; i < arr.size(); i++) {
				out.println(arr.get(i));
			}
		} catch (IOException e) {
			System.err.println(e);
		} finally {
			if (out != null) {
				out.close();
			}
		}

	}

	/**
	 * recursively sort the array list
	 *
	 * @param arr the ArrayList of data
	 * @param left the left index
	 * @param right the right index
	 */
	private static void sort(ArrayList<Long> arr, long left, long right) {
		if (left < right) {
			// Find the middle point
			long middle = left + (right - left) / 2;
			// Sort first and second halves
			recCount++;
			sort(arr, left, middle);
			recCount++;
			sort(arr, middle + 1, right);
			// Merge the sorted halves
			merge(arr, left, middle, right);
		}

	}

	/**
	 * Merge the ArrayList
	 *
	 * @param arr the ArrayList of data
	 * @param left the left index
	 * @param middle the middle index
	 * @param right the right index
	 */
	private static void merge(ArrayList<Long> arr, long left, long middle, long right) {
		// Find sizes of two subarrays to be merged
		long n1 = middle - left + 1;
		long n2 = right - middle;

		/* Create temp arrays */
		long L[] = new long[(int) n1]; ///
		long R[] = new long[(int) n2]; /////

		/* Copy data to temp arrays */
		for (int i = 0; i < n1; ++i)
			L[i] = arr.get((int) (left + i));
		for (int j = 0; j < n2; ++j)
			R[j] = arr.get((int) (middle + 1 + j));

		/* Merge the temp arrays */

		// Initial indexes of first and second subarrays
		int i = 0, j = 0;

		// Initial index of merged subarray array
		long k = left;
		while (i < n1 && j < n2) {
			merCount++;
			if (L[i] <= R[j]) {
				arr.set((int) k, L[i]);
				i++;
			} else {
				arr.set((int) k, R[j]);
				j++;
			}
			k++;
		}

		/* Copy remaining elements of L[] if any */

		while (i < n1) {
			merCount++;
			arr.set((int) k, L[i]);
			i++;
			k++;
		}

		/* Copy remaining elements of R[] if any */

		while (j < n2) {
			merCount++;
			arr.set((int) k, R[j]);
			j++;
			k++;
		}
	}

}
