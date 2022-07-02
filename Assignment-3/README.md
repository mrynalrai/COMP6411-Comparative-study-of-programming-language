# COMP-6411---Comparing-Java-And-Python - Assignment 3

## Java and Python Version
Java8 or higher <br>
Python 3.8.x or higher


## Program Execution Steps

### Single Threaded Python Code

To run singlethreaded_merge.py run the command

Ensure that rand.txt is in the same directory of execution.

```
python singlethreaded_merge.py
```

### Multithreaded Python Code

To run multithreaded_merge.py run the command

Ensure that rand.txt is in the same directory of execution.

```
python multithreaded_merge.py
```

### Single Threaded Java Code

To run SingleThreadedMergeSort.java run the command

Ensure that rand.txt is in the same directory of execution.

```
javac SingleThreadedMergeSort.java
java SingleThreadedMergeSort
```

### Multithreaded Java Code

To run MultiThreadedMergeSort.java run the command

Ensure that rand.txt is in the same directory of execution.

```
javac MultiThreadedMergeSort.java
java MultiThreadedMergeSort
```

Note: All the output files will be created in the current directory.
Output Files:
stat file - containing relevant stats
output file - containing sorted output

Currently all sorted output files are stored in sorted_output folder.

## Additional Information
1. tracemalloc: 
   1. This module helps trace memory blocks allocated by Python
   2. For more information, refer - https://www.cs.unb.ca/~bremner/teaching/cs2613/books/python3-doc/library/tracemalloc.html