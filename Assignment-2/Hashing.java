
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.lang.management.ManagementFactory;
import com.sun.management.OperatingSystemMXBean;

/**
 * Hashing.
 *
 * @author Seung Hyun Hong, Nastaran Naseri
 */
public class Hashing {
    static final Runtime runtime = Runtime.getRuntime();

    /** The Constant MEGABYTE. */
    private static final long MEGABYTE = 1024L * 1024L;

    /** The Constant SECOND. */
    private static final long MILLISECOND = 1000000;


    /**
     * The main method.
     *
     * @param args the arguments
     */
    public static void main(String[] args) {
        // get raw data file name
        String filename = args[0];
        // create Hashing object
        Hashing h = new Hashing();
        // variable for output file
        String outputFile = "output_hashing_java";

        // run garbage collector before calculate memory usage
        runtime.gc();
        //memory used before creation of BST
        long beforeUsedMem = runtime.totalMemory() - runtime.freeMemory();
        double startTimeReading = System.nanoTime();
        // read raw data from the file and put it in array list
        ArrayList<Long> arr = readData(filename);

        double endTimeReading = System.nanoTime();
        double durationReading = (endTimeReading - startTimeReading);

        double startTimeHashing = System.nanoTime();

        HashingTable ht = h.new HashingTable();
        // Put raw data into HashTable
        for (int i = 0; i < arr.size(); i++) {
            ht.put(ht,arr.get(i));
        }
        double endTimeHashing = System.nanoTime();
        double durationHashing = (endTimeHashing - startTimeHashing);

        //memory used after creation of BST
        long afterUsedMem = runtime.totalMemory() - runtime.freeMemory();
        //actual memory used while creating BST
        long memory = afterUsedMem-beforeUsedMem;

        // get search variables from the file into arraylist
        ArrayList<Long> search = readData(args[1]);

        // get search result
        ArrayList<String> searchTime = searchHasing(ht, search);

        // create out put file for statistics
        ArrayList<String> stat = new ArrayList<String>();
        stat.add("Statistics for Hashing with JAVA\n");
        stat.add("Number of elements: " + arr.size());
        stat.add("Reading file execution time: " + durationReading / MILLISECOND + " milliseconds");
        stat.add("Hashing execution time " + durationHashing / MILLISECOND + " milliseconds");
        stat.add("Memory Used for create hash table: " + memory / MEGABYTE + " megabytes\n");

        for (int i = 0; i < searchTime.size(); i++) {
            stat.add(searchTime.get(i));
        }
        writeData(stat, outputFile);
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
     * Search hasing.
     *
     * @param ht the HashTable
     * @param search the search data
     * @return the array list of string that contain result of searches
     */
    public static ArrayList<String> searchHasing(HashingTable ht, ArrayList<Long> search) {
        double tTime = 0;
        int tCount = 0;
        double fTime = 0;
        int fCount = 0;
        ArrayList<String> res = new ArrayList<>();
        res.add("\nSearch in Hash Table\n");
        for (int i = 0; i < search.size(); i++) {
            double start = System.nanoTime();
            Map<Integer, Integer> chk = ht.search(search.get(i));
            double end = System.nanoTime();
            if (chk.containsKey(1)) {
                tTime += (end - start) / MILLISECOND;
                tCount++;
                res.add(search.get(i) + "  founded in " + (end - start) / MILLISECOND + " milliseconds with " + chk.get(1)
                        + " number of accesses.");

            } else {
                fTime += (end - start) / MILLISECOND;
                fCount++;
                res.add(search.get(i) + "  not founded in " + (end - start) / MILLISECOND + " milliseconds with " + chk.get(0)
                        + " number of accesses.");
            }
        }
        res.add("\nAverage time for successful search is " + (tTime / tCount) + " milliseconds");
        res.add("Average time for un-successful search is " + (fTime / fCount)+ " milliseconds");
        res.add("Average time for total search is " + ((tTime / tCount) + (fTime / fCount)) + " milliseconds");

        return res;
    }

    /**
     * The Class HashingTable.
     */
    class HashingTable {

        /** The table size. */
        private int tableSize = 600011;

        /** The hash table. */
        private Node[] hashTable = new Node[tableSize];

        /**
         * The Class Node.
         */
        class Node {

            /** The key. */
            long key;

            /** The next. */
            Node next;
        }

        /**
         * Put key in HashTable
         *
         * @param ht the HeahTable
         * @param key the key
         */
        public void put(HashingTable ht, long key) {
            int location = (int) (Math.abs(key) % tableSize);
            Node newNode = new Node();
            newNode.key = key;
            Map<Integer, Integer> chk = ht.search(key);

            if(chk.containsKey(0)) {
                if (hashTable[location] == null) {
                    hashTable[location] = newNode;
                } else {
                    Node runner = hashTable[location];
                    hashTable[location] = newNode;
                    newNode.next = runner;
                }
            }
        }

        /**
         * Search the key
         *
         * @param key the key
         * @return the map for successful/ un-successful and number of accesses
         */
        public Map<Integer, Integer> search(long key) {
            Map<Integer, Integer> res = new HashMap<>();
            Integer location = (int) (Math.abs(key) % tableSize);
            int cnt = 0;
            Node runner = hashTable[location];

            while (runner != null) {
                cnt++;
                if (runner.key == key) {
                    res.put(1, cnt);
                    return res;
                }
                runner = runner.next;
            }
            res.put(0, cnt);
            return res;
        }
    }
}
