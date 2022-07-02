import java.awt.List;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Class for implementing lists and merge sort on them
 *
 * @author Mrinal Rai, Akshay Dhabale
 */
public class SingleThreadedMergeSort {

    /** The Constant runtime. */
    static final Runtime runtime = Runtime.getRuntime();

    /** The Constant MEGABYTE. */
    private static final long MEGABYTE = 1024L * 1024L;

    /** The Constant SECOND. */
    private static final long SECOND = 1000000000;

    static SingleThreadedMergeSort stm = new SingleThreadedMergeSort();

    /** List for storing even and prime numbers */
    static LinkedList list1;
    /** List for storing even and unprime numbers */
    static LinkedList list2;
    /** List for storing odd and prime numbers */
    static LinkedList list3;
    /** List for storing odd and unprime numbers */
    static LinkedList list4;

    /**
     * Read data from external file
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
     * Data structure representing node in a linked list
     */
    static class Node {
        long data;
        Node next;
        Node(long key)
        {
            this.data = key;
            next = null;
        }
    }

    static class LinkedList{
        Node head;
        int iteration;
        int size;

        public LinkedList(){
         this.head = null;
         this.iteration = 0;
         this.size = 0;
        }

        void add(long new_data){
            Node new_node = new Node(new_data);
            new_node.next = head;
            head = new_node;
            this.size+=1;
        }

        Node mergeSort(Node head)
        {
            this.iteration+=1;
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

        Node merge(Node head1, Node head2)
        {
            this.iteration+=1;
            Node merged = new Node(-1);
            Node temp = merged;
        
            // While head1 is not null and head2
            // is not null
            while (head1 != null && head2 != null) {
                if (head1.data < head2.data) {
                    temp.next = head1;
                    head1 = head1.next;
                }
                else {
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
 
        Node findMid(Node head)
        {
            Node slow = head, fast = head.next;
            while (fast != null && fast.next != null) {
                slow = slow.next;
                fast = fast.next.next;
            }
            return slow;
        }


    }

    /**
     * Write stats in exist out put file.
     *
     * @param arr the ArrayList of stats
     */
    public static void writeStat(ArrayList<String> arr) {
        FileWriter myWriter = null;
        try {
            myWriter = new FileWriter("stat_single_threaded_java.txt");
            for (int i = 0; i < arr.size(); i++) {
                System.out.println(arr.get(i));
                myWriter.write(arr.get(i)+ "\n");
            }
            myWriter.close();
        } catch (IOException e) {
            System.err.println(e);
        }

    }

    public static void writeSortedData(Node head,String filename){
        FileWriter myWriter = null;
        try{
            myWriter = new FileWriter(filename);
            Node curr = head;
            while(curr !=null){
                myWriter.write(curr.data+"\n");
                curr = curr.next;
            }
            myWriter.close();
        }catch(Exception e){
            System.err.println(e);
        }

    }

    public static void main(String[] args) throws InterruptedException {
        double startMainTime = System.nanoTime();

        list1 = new LinkedList();
        list2 = new LinkedList();
        list3 = new LinkedList();
        list4 = new LinkedList();

        double startTimeReading = System.nanoTime();
        //long memory1 = (instance.totalMemory() - instance.freeMemory()) / (1024 * 1024);
        readData("rand.txt");
        double endTimeReading = System.nanoTime();
        ArrayList<String> stat = new ArrayList<String>();
        stat.add("Time to read data from file and create 4 unsorted linked list : " + (endTimeReading - startTimeReading)/SECOND + " seconds\n");

        // Creating list1 with even and prime number. Only 2 qualifies for this list
        long temp = (long) 2;
        list1.add(temp);

        stat.add("Size of List1: " + list1.size);
        stat.add("Size of List2: " + list2.size);
        stat.add("Size of List3: " + list3.size);
        stat.add("Size of List4: " + list4.size);

        runtime.gc();
        long beforeUsedMem = runtime.totalMemory() - runtime.freeMemory();
        double startSortTimeList1=System.nanoTime();
        Node result1 = list1.mergeSort(list1.head);
        double endSortTimeList1=System.nanoTime();
        double timeTakentoSortList1=(endSortTimeList1-startSortTimeList1);
        int countList1 = list1.iteration;


        double startSortTimeList2=System.nanoTime();
        Node result2 = list2.mergeSort(list2.head);
        double endSortTimeList2=System.nanoTime();
        double timeTakentoSortList2=endSortTimeList2-startSortTimeList2;
        int countList2= list2.iteration;
       

        double startSortTimeList3=System.nanoTime();
        Node result3 = list3.mergeSort(list3.head);
        double endSortTimeList3=System.nanoTime();
        double timeTakentoSortList3=endSortTimeList3-startSortTimeList3;
        //System.out.println(timeTakentoSortList3/SECOND);
        int countList3= list3.iteration;
      

        double startSortTimeList4=System.nanoTime();
        Node result4 = list4.mergeSort(list4.head);
        double endSortTimeList4=System.nanoTime();
        double timeTakentoSortList4=endSortTimeList4-startSortTimeList4;
        int countList4= list4.iteration;

        long afterUsedMem = runtime.totalMemory() - runtime.freeMemory();

        long memory = afterUsedMem-beforeUsedMem;

        
        writeSortedData(result1,"output_singlethreaded_java1.txt");
        writeSortedData(result2,"output_singlethreaded_java2.txt");
        writeSortedData(result3,"output_singlethreaded_java3.txt");
        writeSortedData(result4,"output_singlethreaded_java4.txt");

        double endMainTime = System.nanoTime();

        stat.add("Time taken to sort list 1: "+timeTakentoSortList1 / SECOND + "seconds");
        stat.add("Time taken to sort list 2: "+timeTakentoSortList2 / SECOND + "seconds");
        stat.add("Time taken to sort list 3: "+timeTakentoSortList3 / SECOND + "seconds");
        stat.add("Time taken to sort list 4: "+timeTakentoSortList4 / SECOND + "seconds");
        stat.add("\nTotal Time taken to sort linked lists: "+ (timeTakentoSortList1+timeTakentoSortList2+timeTakentoSortList3+timeTakentoSortList4)/ SECOND + "seconds");


        stat.add("\nNumber of iteration to sort list 1 "+countList1);
        stat.add("\nNumber of iteration to sort list 2 "+countList2);
        stat.add("\nNumber of iteration to sort list 3 "+countList3);
        stat.add("\nNumber of iteration to sort list 4 "+countList4);
        stat.add("\nTotal Number of iterations done to sort: "+(countList1+countList2+countList3+countList4));
        stat.add("\nTotal Memory Usage to sort: " + (memory)/MEGABYTE + "MB");
        stat.add("\nTotal Speed of Execution: "+ (endMainTime - startMainTime) / SECOND + "seconds");

        writeStat(stat);

    }
}
