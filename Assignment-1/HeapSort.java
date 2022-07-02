import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Implementation for Heap Sort.
 *
 * @author Akshay Dhabale, Mrinal Rai
 */
public class HeapSort {

    /** The Constant MEGABYTE. */
    private static final long MEGABYTE = 1024L * 1024L;

    /** The Constant SECOND. */
    private static final long SECOND = 1000000000;


    /** The Constant runtime. */
    static final Runtime runtime = Runtime.getRuntime();

    /** The Recursion count. */
    static int recurCount = 0;

    /**
     * Builds the max-heap and do the sort using heapify
     *
     * @param array Array containing numbers to sort
     */
    public static void sort(ArrayList<Integer> array) {

        // Number of elements
        int n=array.size();
        //long startTime=System.currentTimeMillis();
        // Build heap
        for(int i=n/2-1;i>=0;i--) {
            recurCount+=1;
            heapify(array,n,i);
        }

        // Extract element from heap
        for(int i=n-1;i>=0;i--) {

            // Move root node to end
            int temp=array.get(0);
            array.set(0, array.get(i));
            array.set(i,temp);

            //call heapify on reduced heap
            recurCount+=1;
            heapify(array,i,0);

        }
        //long endTime=System.currentTimeMillis();

        //return endTime-startTime;

    }

    /**
     * Builds max-heap
     *
     * @param array list of elements to sort
     * @param n number of elements to consider for heapify
     * @param i parent node
     */
    static void heapify(ArrayList<Integer> array,int n, int i) {

        int largest=i; //as root node
        //get left and right child
        int left=2*i+1;
        int right=2*i+2;

        //if left child is larger than root, move it to root

        if(left<n && array.get(left)>array.get(largest)) {
            largest=left;
        }
        //if right child is larger than root, move it to root
        if(right<n && array.get(right)>array.get(largest)) {
            largest=right;
        }

        // if largest is not root
        if(largest!=i) {
            int temp=array.get(i);
            array.set(i, array.get(largest));
            array.set(largest, temp);

            // call recursively heapify for the sub tree
            recurCount+=1;
            heapify(array,n,largest);
        }

    }

    /**
     *
     * @param file File contains elements to sort
     * @param loc number of elements to consider to sort
     * @return Array containing elements to sort
     * @throws FileNotFoundException
     */
    public static ArrayList<Integer> readFile(String file, int loc) throws FileNotFoundException{
        ArrayList<Integer> array= new ArrayList<Integer>();
        int currLoc = 0;
        int count = 0;

        Scanner scanner= new Scanner(new FileInputStream(file));
        while(scanner.hasNext() && currLoc < loc) {
            if(scanner.hasNextInt()) {
                array.add(scanner.nextInt());
                currLoc++;
                count++;
            }else {
                scanner.next();
            }
        }
        System.out.println("Number count: " + array.size());

        return array;
    }

    /**
     * Writes sorted list of elements to local disk
     *
     * @param array Array of sorted elements
     * @param file Output text file
     * @param <T>
     */
    public static <T> void writeData(ArrayList<T> array, String file) {
        try {
            FileWriter fileWriter=new FileWriter(file);
            for(int i=0; i<array.size();i++) {
                fileWriter.write(array.get(i)+"\n");
            }
            fileWriter.close();
            System.out.println("Outfile has created");
        }catch(IOException e) {
            System.out.println("Error to create output file");
            e.printStackTrace();
        }
    }

    /**
     * Writing stats to output file
     * @param array
     */
    public static void writeStat(ArrayList<String> array,String outfile) {

        PrintWriter out=null;

        try {
            out=new PrintWriter(new BufferedWriter(new FileWriter(outfile,true)));
            for(int i=0;i<array.size();i++) {
                out.println(array.get(i));
            }
        }catch(IOException e) {
            System.err.println(e);
        }finally {
            if(out!=null) {
                out.close();
            }
        }
    }

    public static void main(String[] args) throws FileNotFoundException {

        double startTimeFileRead = System.nanoTime();

        // Reading 1 million elements
        ArrayList<Integer> array=readFile("rand.txt", 1000000);


        double endTimeFileRead = System.nanoTime();

        // To measure time spent while reading the file
        double durationToReadFile = endTimeFileRead-startTimeFileRead;

        System.out.println("Reading file execution time " + durationToReadFile / SECOND + " seconds");

        double startTimeToSort=System.nanoTime();

        // Sort using heapsort
        sort(array);

        double endTimeToSort=System.nanoTime();

        double durationToSort=endTimeToSort-startTimeToSort;

        // To measure sort execution time
        System.out.println("Sorting execution time " + durationToSort/ SECOND + " seconds");

        double startTimeToWrite=System.nanoTime();
        String outputFile="output_heapsort_java.txt";
        writeData(array, outputFile);
        double endTimeToWrite=System.nanoTime();

        double durationToWrite=endTimeToWrite-startTimeToWrite;

        // To measure Write execution time
        System.out.println("Writing File execution time " + durationToWrite/ SECOND + " seconds");

        // run garbage collector before calculate memory usage
        runtime.gc();

        // Calculate memory usage
        long memory = runtime.totalMemory() - runtime.freeMemory();

        ArrayList<String> stat = new ArrayList<String>();
        stat.add("\n\nStatistics for Heap Sort with JAVA\n");
        stat.add("Number of elements: " + array.size());
        stat.add("Reading file excution time " + durationToReadFile / SECOND + " seconds");
        stat.add("Sorting excution time " + durationToSort / SECOND + " seconds");
        stat.add("Writing file excution time " + durationToWrite / SECOND + " seconds");
        stat.add("Used memory is megabytes: " + memory / MEGABYTE);
        stat.add("Number of recursions done: " + recurCount);

        System.out.println("Used memory is megabytes: " + memory / MEGABYTE);
        System.out.println("Number of recursions done: " + recurCount);

        // Writing stats to output file
        writeStat(stat,outputFile);


    }
}
