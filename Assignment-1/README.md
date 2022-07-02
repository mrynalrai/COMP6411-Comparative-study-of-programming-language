# COMP-6411---Comparing-Java-And-Python - Assignment 1

## Java and Python Version
Java8 or higher <br>
Python 3.8.x or higher


## Program Execution Steps

### Merge Sort Python Code

The mergesort.py has three arguments:
1. File name 
2. Trace memory flag : y for enabling memory trace to calculate memory usage
3. Number of elements to sort : 1 to 998676 or -1 for all elements

To run mergesort on complete file:

```
python mergesort.py rand.txt y -1
```

To run mergesort on complete filewithout trace:

```
python mergesort.py rand.txt n -1
```

To run mergesort on first 10 records with memory trace:
```
python mergesort.py rand.txt y 10
```

Output file will be created in same directory with relevant statistics.

### Merge Sort Java Code

Ensure that JDK is included in path variable.

Compile java file
```
javac MergeSort.java
```
Run java File
```
java MergeSort
```

Run Executable Jar
```
java -jar MergeSort.jar
```

### Heap Sort Java Code

Ensure that JDK is included in path variable.

Compile java file
```
javac HeapSort.java
```
Run java File
```
java HeapSort
```

Run Executable Jar
```
java -jar HeapSort.jar
```

### Heap Sort Python Code

The heapsort.py has two arguments:
1. File name 
2. Number of elements to sort : 1 to 998676 or -1 for all elements

To run heapsort on complete file:

```
python heapsort.py rand.txt -1
```

To run heapsort on first 10 records with memory trace:
```
python heapsort.py rand.txt 10
```

Output file will be created in same directory with relevant statistics.

**Note: Ensure that the input file is in directory of the soruce code.**

## Additional Information
1. tracemalloc: 
   1. This module helps trace memory blocks allocated by Python
   2. For more information, refer - https://www.cs.unb.ca/~bremner/teaching/cs2613/books/python3-doc/library/tracemalloc.html
   