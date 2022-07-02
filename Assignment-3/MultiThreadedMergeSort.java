import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * The Class MultiTreadMergeSort.
 */
public class MultiThreadedMergeSort {

	/** The Constant runtime. */
	static final Runtime runtime = Runtime.getRuntime();

	/** The Constant MEGABYTE. */
	private static final long MEGABYTE = 1024L * 1024L;

	/** The Constant SECOND. */
	private static final long SECOND = 1000000000;

	/** The Class variable */
	static MultiThreadedMergeSort mtm = new MultiThreadedMergeSort();

	/** The list 1. Even and prime Number_ only number 2 */
	static LinkedList list1;

	/** The list 2. Even and Not Prime Number */
	static LinkedList list2;

	/** The list 3. Odd and Prime Number */
	static LinkedList list3;

	/** The list 4. Odd and Not Prime Number */
	static LinkedList list4;

	/**
	 * Read data from external file
	 * 
	 * @param name External file name
	 */
	public static void readData(String name) {
		try {
			Scanner fis = new Scanner(new FileInputStream(name));

			// read each line
			while (fis.hasNextLine()) {
				Long theNum = fis.nextLong();
				if (theNum % 2 == 0) {
					// it is even !
					if (checkPrime(theNum)) {
						// even + prime
						list1.add(theNum);
					} else {
						// even + !prime
						list2.add(theNum);
					}
				} else {
					// it is odd !
					if (checkPrime(theNum)) {
						// odd + prime
						list3.add(theNum);
					} else {
						// odd + !prime
						list4.add(theNum);
					}
				}
			}
			fis.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Checks if the number is prime or not
	 * 
	 * @param n
	 * @return
	 */
	public static boolean checkPrime(Long n) {
		// Check if number is less than equal to 1
		if (n <= 1)
			return false;
		// Check if number is 2
		else if (n == 2)
			return true;
		// Check if n is a multiple of 2
		else if (n % 2 == 0)
			return false;
		// If not, then just check the odds
		for (int i = 3; i <= Math.sqrt(n); i += 2) {
			if (n % i == 0)
				return false;
		}
		return true;
	}

	/**
	 * Write stats in file.
	 *
	 * @param arr the ArrayList of stats
	 */
	public static void writeStat(ArrayList<String> arr) {
		FileWriter myWriter = null;
		try {
			myWriter = new FileWriter("stat_multi_threaded_java.txt");
			for (int i = 0; i < arr.size(); i++) {
				System.out.println(arr.get(i));
				myWriter.write(arr.get(i) + "\n");
			}
			myWriter.close();
		} catch (IOException e) {
			System.err.println(e);
		}
	}

	/**
	 * Write data.
	 *
	 * @param lists the lists
	 * @param name  the name
	 */
	public static void writeData(Node head, String filename) {
		FileWriter myWriter = null;
		try {
			myWriter = new FileWriter(filename);
			Node curr = head;
			while (curr != null) {
				myWriter.write(curr.data + "\n");
				curr = curr.next;
			}
			myWriter.close();
		} catch (Exception e) {
			System.err.println(e);
		}

	}

	/**
	 * The Class SortThreads.
	 */
	class SortThreads extends Thread {

		/** The time. */
		double time;

		/** The memory. */
		long memory;

		/** The list. */
		LinkedList list;

		/** The num. */
		int num;

		/** The iterator. */
		int iterator;

		/**The node */
		Node head;

		/**
		 * Instantiates a new sort threads.
		 *
		 * @param list the list
		 * @param i    the number name of list
		 */
		SortThreads(LinkedList list, int i) {
			this.list = list;
			this.num = i;
			this.time = 0;
			this.memory = 0;
			this.iterator = 0;
		}

		/**
		 * Run.
		 */
		public void run() {
			double startTime = System.nanoTime();
			this.head = list.mergeSort(list.head);
			double endTime = System.nanoTime();
			this.time = endTime - startTime;
		}
	}

	/**
	 * Data structure representing node in a linked list
	 */
	static class Node {
		long data;
		Node next;

		Node(long key) {
			this.data = key;
			next = null;
		}
	}

	static class LinkedList {
		Node head;
		int iteration;
		int size;

		public LinkedList() {
			this.head = null;
			this.iteration = 0;
			this.size = 0;
		}

		public void add(long new_data) {
			Node new_node = new Node(new_data);
			if (head == null) {
				head = new_node;
			} else {
				new_node.next = head;
				head = new_node;
			}
			this.size += 1;
		}

		Node mergeSort(Node head) {
			this.iteration += 1;
			if (head.next == null)
				return head;

			Node mid = findMid(head);
			Node head2 = mid.next;
			mid.next = null;
			Node newHead1 = mergeSort(head);
			Node newHead2 = mergeSort(head2);
			Node finalHead = merge(newHead1, newHead2);

			return finalHead;
		}

		Node merge(Node head1, Node head2) {
			this.iteration += 1;
			Node merged = new Node(-1);
			Node temp = merged;

			// While head1 is not null and head2
			// is not null
			while (head1 != null && head2 != null) {
				if (head1.data < head2.data) {
					temp.next = head1;
					head1 = head1.next;
				} else {
					temp.next = head2;
					head2 = head2.next;
				}
				temp = temp.next;
			}

			// While head1 is not null
			while (head1 != null) {
				temp.next = head1;
				head1 = head1.next;
				temp = temp.next;
			}

			// While head2 is not null
			while (head2 != null) {
				temp.next = head2;
				head2 = head2.next;
				temp = temp.next;
			}
			return merged.next;
		}

		Node findMid(Node head) {
			Node slow = head, fast = head.next;
			while (fast != null && fast.next != null) {
				slow = slow.next;
				fast = fast.next.next;
			}
			return slow;
		}
	}

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 * @throws InterruptedException the interrupted exception
	 */
	public static void main(String[] args) throws InterruptedException {
		Runtime instance = Runtime.getRuntime();
		double startMainTime = System.nanoTime();
		double startTimeReading = System.nanoTime();

		list1 = new LinkedList();
		list2 = new LinkedList();
		list3 = new LinkedList();
		list4 = new LinkedList();

		readData("rand.txt");
		double endTimeReading = System.nanoTime();

		ArrayList<String> stat = new ArrayList<String>();
		stat.add("STATS for Mutithreads Merge Sort\n");
		stat.add("Time to read data from file and create 4 unsorted linked list : "
				+ (endTimeReading - startTimeReading) / SECOND + " seconds\n");
		long temp = (long) 2;
		list1.add(temp);

		stat.add("Size of List 1: " + list1.size);
		stat.add("Size of List 2: " + list2.size);
		stat.add("Size of List 3: " + list3.size);
		stat.add("Size of List 4: " + list4.size);


		SortThreads worker1 = mtm.new SortThreads(list1, 1);
		SortThreads worker2 = mtm.new SortThreads(list2, 2);
		SortThreads worker3 = mtm.new SortThreads(list3, 3);
		SortThreads worker4 = mtm.new SortThreads(list4, 4);

		runtime.gc();
		long memory1 = (instance.totalMemory() - instance.freeMemory()) / (1024 * 1024);

		double startSortTime = System.nanoTime();
		worker1.start();
		worker2.start();
		worker3.start();
		worker4.start();

		worker1.join();
		worker2.join();
		worker3.join();
		worker4.join();
		double endSortTime = System.nanoTime();
		long memory2 = (instance.totalMemory() - instance.freeMemory()) / MEGABYTE;

		writeData(worker1.head, "output_multi_threaded_merge_java1.txt");
		writeData(worker2.head, "output_multi_threaded_merge_java2.txt");
		writeData(worker3.head, "output_multi_threaded_merge_java3.txt");
		writeData(worker4.head, "output_multi_threaded_merge_java4.txt");

		double endMainTime = System.nanoTime();

		stat.add("\nTime taken by Thread 1 to sort: " + worker1.time / SECOND + "seconds");
		stat.add("Time taken by Thread 2 to sort: " + worker2.time / SECOND + "seconds");
		stat.add("Time taken by Thread 3 to sort: " + worker3.time / SECOND + "seconds");
		stat.add("Time taken by Thread 4 to sort: " + worker4.time / SECOND + "seconds");

		stat.add("\nTotal Time taken to sort linked lists: " + (endSortTime-startSortTime) / SECOND + "seconds");

		stat.add("\nNumber of iteration done by Thread 1 to sort: " + list1.iteration);
		stat.add("Number of iteration done by Thread 2 to sort: " + list2.iteration);
		stat.add("Number of iteration done by Thread 3 to sort: " + list3.iteration);
		stat.add("Number of iteration done by Thread 4 to sort: " + list4.iteration);
		stat.add("Total number of iteration done to sort: "
				+ (list1.iteration + list2.iteration + list3.iteration + list4.iteration));

		stat.add("\nTotal Memory Usage to sort: " + (memory2 - memory1) + "MB");

		stat.add("\nTotal Execution time: " + (endMainTime - startMainTime) / SECOND + "seconds");
		writeStat(stat);
	}
}
