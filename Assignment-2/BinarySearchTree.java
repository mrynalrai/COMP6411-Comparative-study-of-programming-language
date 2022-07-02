
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * Binary Search Tree
 *
 * @author Mrinal Rai, Akshay Dhabale
 */
public class BinarySearchTree {
    /** The Constant MEGABYTE. */
    private static final long MEGABYTE = 1024L * 1024L;

    /** The Constant SECOND. */
    private static final long MILLISECOND = 1000000;

    /** The Constant runtime. */
    static final Runtime runtime = Runtime.getRuntime();

    /** Root of BST. */
    Node root;

    /** Constructor */
    BinarySearchTree() {
        root = null;
    }

    /**
     * Data structure containing left and right child of the current node with key value
     */
    public static class Node {
        int key;
        Node left, right;

        public Node(int item)
        {
            key = item;
            left = right = null;
        }
    }

    /**
     * Calls insertRec()
     * @param key Key for new node
     */
    void insert(int key) {
        root = insertRec(root, key);
    }

    /**
     * Inserts new node into the BST
     * @param root Current node
     * @param key Key for new node
     * @return parent Node of the new node
     */
    Node insertRec(Node root, int key) {

        /** If the tree is empty, return a new node */
        if (root == null) {
            root = new Node(key);
            return root;
        }

        /* Otherwise, recur down the tree */
        if (key < root.key)
            root.left = insertRec(root.left, key);
        else if (key > root.key)
            root.right = insertRec(root.right, key);

        /* return the (unchanged) node pointer */
        return root;
    }

    /**
     * Searchs a given key in BST
     * @param root Current node
     * @param key Key to be searched
     * @param accessCount Number of nodes visited so far
     * @return An array containing 0 -> unsuccessful/ 1 -> successful and number of nodes visited during traversal
     */
    public static int[] search(Node root, int key, int accessCount) {

//        if (root!=null)
//            System.out.println(root.key);
        accessCount++;
        int[] res;
        // Base Cases: root is null or key is present at root
        if (root==null) {
            res = new int[]{ 0, accessCount};
            return res;
        } else if (root.key==key) {
            res = new int[]{ 1, accessCount};
            return res;
        }

        // If key is greater than root's key, visit right child
        if (root.key < key)
            return search(root.right, key, accessCount);

        // If key is smaller than root's key, visit left child
        return search(root.left, key, accessCount);
    }

    /**
     * Reads file
     * @param file File contains elements to sort
     * @return Array containing elements to sort
     * @throws FileNotFoundException
     */
    public static ArrayList<Integer> readFile(String file) throws FileNotFoundException{
        ArrayList<Integer> array= new ArrayList<Integer>();

        Scanner scanner= new Scanner(new FileInputStream(file));
        while(scanner.hasNext()) {
            if(scanner.hasNextInt()) {
                array.add(scanner.nextInt());
            }else {
                scanner.next();
            }
        }

        return array;
    }

    /**
     * Searches for keys from an arraylist
     * @param bst Binary Search Tree to be traversed
     * @param searchInput Arraylist containing keys
     * @return Arraylist of strings containing results
     */
    public static ArrayList<String> searchBST(BinarySearchTree bst, ArrayList<Integer> searchInput) {
        double tTime = 0;
        int tCount = 0;
        double fTime = 0;
        int fCount = 0;
        ArrayList<String> res = new ArrayList<>();
        res.add("\nSearch in Binary Search Tree\n");
        for (int i = 0; i < searchInput.size(); i++) {
            double start = System.nanoTime();
            int[] searchRes = search(bst.root, searchInput.get(i), 0);
            double end = System.nanoTime();
            if (searchRes[0] == 1) {
                tTime += (end - start) / MILLISECOND;
                tCount++;
                res.add(searchInput.get(i) + "  founded in " + tTime + " milliseconds with " + searchRes[1]
                        + " number of accesses.");

            } else {
                fTime = end - start;
                fCount++;
                res.add(searchInput.get(i) + "  not founded in " + fTime + " milliseconds with " + searchRes[1]
                        + " number of accesses.");
            }

        }

        res.add("\nAverage time for successful search is " + (tTime / tCount) + " millisecond");
        res.add("Average time for un-successful search is " + (fTime / fCount)+ " millisecond");
        res.add("Average time for total search is " + ((tTime / tCount) + (fTime / fCount)) + " millisecond");

        return res;

    }

    /**
     * Returns maximum depth of the BST
     * @param root Current Node
     * @return maximum depth of the BST
     */
    int maxDepth(Node root)
    {
        // Root being null means tree doesn't exist.
        if (root == null)
            return 0;

        // Get the depth of the left and right subtree
        // using recursion.
        int leftDepth = maxDepth(root.left);
        int rightDepth = maxDepth(root.right);

        // Choose the larger one and add the root to it.
        if (leftDepth > rightDepth)
            return (leftDepth + 1);
        else
            return (rightDepth + 1);
    }

    /**
     * Calls inorderRec()
     */
    void inorder() {
        inorderRec(root);
    }

    /**
     * Does inorder traversal of BST
     * @param root Current Node
     */
    void inorderRec(Node root)
    {
        if (root != null) {
            inorderRec(root.left);
            System.out.println(root.key);
            inorderRec(root.right);
        }
    }

    /**
     * Write sorted data into text file.
     *
     * @param <T>  the generic type
     * @param arr  the sorted data array list
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

    public static void main(String[] args) throws FileNotFoundException {
        String outputFile = "output_bst_java";
        ArrayList<Integer> rand_input = null;


        // run garbage collector before calculate memory usage
        runtime.gc();
        //memory used before creation of BST
        long beforeUsedMem = runtime.totalMemory() - runtime.freeMemory();
        double startTimeReading = System.nanoTime();
        rand_input = readFile("rand.txt");
        double endTimeReading = System.nanoTime();
        double durationReading = (endTimeReading - startTimeReading);
        System.out.println("Reading file execution time " + durationReading / MILLISECOND + " milliseconds");

        double startTimeInsertion = System.nanoTime();
        BinarySearchTree bst = new BinarySearchTree();
        for (int i = 0; i < rand_input.size(); i++) {
            bst.insert(rand_input.get(i));
        }

        double endTimeInsertion = System.nanoTime();
        double durationInsertion = (endTimeInsertion - startTimeInsertion);
        System.out.println("Binary Search Tree creation time " + durationInsertion / MILLISECOND + " milliseconds");


        //memory used after creation of BST
        long afterUsedMem = runtime.totalMemory() - runtime.freeMemory();
        //actual memory used while creating BST
        long memory = afterUsedMem-beforeUsedMem;

        System.out.println("Maximum depth of Binary Search Tree: " + bst.maxDepth(bst.root));

        double startReadingSearch = System.nanoTime();
        ArrayList<Integer> search_input = null;
        search_input = readFile("randSearch.txt");
        double endReadingSearch = System.nanoTime();
        double durationReadingSearch = (endReadingSearch - startReadingSearch);
        System.out.println("Writing file execution time " + durationReadingSearch / MILLISECOND + " milliseconds");

        ArrayList<String> searchTime = searchBST(bst, search_input);

        ArrayList<String> stat = new ArrayList<String>();
        stat.add("\n\nStatistics for Binary Search Tree with JAVA\n");
        stat.add("Number of elements: " + rand_input.size());
        stat.add("Maximum depth of the Binary Search Tree: " + bst.maxDepth(bst.root));
        stat.add("Reading file execution time " + durationReading / MILLISECOND + " milliseconds");
        stat.add("Binary Search Tree creation time " + durationInsertion / MILLISECOND + " milliseconds");
        stat.add("Used memory is megabytes: " + memory / MEGABYTE + "\n");

        for (int i = 0; i < searchTime.size(); i++) {
            stat.add(searchTime.get(i));
        }
        writeData(stat, outputFile);
    }
}
