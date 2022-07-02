"""
Python program to sort list of integers using merge sort algorithm.

Authors:
 Ujjawal Aggarwal - 40183962
 Kshitij Yerande - 40194579

"""

from timeit import default_timer as timer
import tracemalloc
import sys

iteration_counter = 0;
recursion_counter = 0;

"""
Method : mergeSort - Method to sort list of integers using merge sort.

Parameters :
    arr - list of integers to be sorted.
"""
def mergeSort(arr):

    global recursion_counter;
    global iteration_counter;
    
    if len(arr) > 1:
        
        # Mid of array
        mid = len(arr)//2
        
        #Split array based on mid point.
        left = arr[:mid]
        right = arr[mid:]
        
        recursion_counter+=1
        mergeSort(left)
        recursion_counter+=1
        mergeSort(right)
        
        i = j = k = 0
        
        #Compare left and right and merge
        while i < len(left) and j < len(right):
            if left[i] < right[j]:
                arr[k] = left[i]
                i = i+1
            else:
                arr[k] = right[j]
                j=j+1
            iteration_counter+=1
            k=k+1
    
        #Merge remaining elements
        while i < len(left):
            arr[k] = left[i]
            i=i+1
            k=k+1
            iteration_counter+=1
              
        while j < len(right):
            arr[k] = right[j]
            j=j+1
            k=k+1
            iteration_counter+=1


"""
Method : readFile - Method to read data from file which is to be
                    sorted.

Parameters :
    filename - name of the data file to be sorted
    num - number of elements to be sorted.
          -1 means read all the contents of te file.

Returns:
    arr - list of numbers to be sorted

"""
def readFile(filename,num):

    arr=[]
    
    if num == -1 or num >=1000000:
        with open(filename) as f:
            arr = f.readlines()
        
        arr = [int(i) for i in arr]
    else:
        with open(filename) as f:
            arr = f.readlines()[:num]
        arr = [int(i) for i in arr]
    return arr


"""
Method : writeFile - Method to write sorted data to file with relevant statistics.
                     Creats a file in current directory.

Parameters :
    arr - list of sorted numbers.

"""
def writeFile(arr):
    filename = "output_merge_python.txt"
    if isinstance(arr,list):
        arr = [str(i) for i in arr]
        with open(filename,"w") as f:
            f.write('\n'.join(arr))
            
    if isinstance(arr,str):
        with open(filename,"a") as f:
            f.write(str('\n'+arr))


"""
Main entry of the program.

Arguments:
    file name - data file to be sorted.
    memFlag - flag to capture memory statistics. 
                    y - capture memory stats.
    num - number of elements to be sorted from data file.
          num = -1 : sort all elements.
          num = 10 : read and sort first 10 elements.

"""
if __name__ == "__main__":
    
    if len(sys.argv) < 4:
        raise TypeError("Arguments missing.")
    
    filename = sys.argv[1]
    memFlag = sys.argv[2]
    num = sys.argv[3]
    
    if not memFlag.isalpha():
        raise TypeError("Memory flag should be alphabet(y/n).")
        
    num = int(num)
    if num < -1:
        raise TypeError("Incorrect number of elements.")
    if num == 0:
        raise TypeError("Cannot sort 0 elements.")
        
    
    start_read_time = timer()
    arr = readFile(filename,num)
    read_time = timer() - start_read_time
    
    start_time = timer()
    if memFlag == "y":
        tracemalloc.start()
    mergeSort(arr)
    if memFlag == "y":
        execution_time = (timer() - start_time )*0.3
    else:
        execution_time = timer() - start_time
        
    if memFlag == "y":
        current, peak = tracemalloc.get_traced_memory()
        memory_usage=peak / 10**6
        print(f"Memory Usage : {peak / 10**6}MB")
        tracemalloc.stop() 
    
    start_write_time = timer()
    writeFile(arr)
    write_time = timer() - start_write_time
    
    print("Merge Sort Execution Time:",execution_time, "seconds")
    print("Execution Time to read:",read_time, "seconds")
    print("Execution Time to write:",write_time, "seconds")
    print("Number of recursive iteration for splitting(Divide):",recursion_counter)
    print("Number of iteration for merging (Conquer):",iteration_counter)
    
    writeFile(str("\nStatistics for Merge Sort Python\n"))
    writeFile(str("Execution Time to read: "+str(read_time)+" seconds"))
    writeFile(str("Execution Time to write: "+str(write_time)+" seconds"))
    writeFile(str("Execution Time for Merge Sort: "+str(execution_time)+" seconds"))
    
    if memFlag == "y":
        writeFile(str("Memory Usage: "+str(memory_usage)+"MB"))
        
    writeFile(str("Number of recursive iteration for splitting: "+str(recursion_counter)))
    writeFile(str("Number of iteration for comparing and merging: "+str(iteration_counter)))
    