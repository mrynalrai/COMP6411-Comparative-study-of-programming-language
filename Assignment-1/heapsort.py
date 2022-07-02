"""
Python program to sort list of integers using heap sort algorithm.
Authors:
 Farheen Jamadar - 40194668
 Almas Saba - 40156359
"""
from timeit import default_timer as timer
import tracemalloc
import sys

number_of_iterations = 0

"""
Method : read_file - Method to read file and create an array.
Parameters :
    filename - name of the data file to be sorted
    number_of_lines - number of lines to be picked from the file
Returns:
    array_list - list of numbers to be sorted
"""


def read_file(filename, number_of_lines):
    if number_of_lines == -1:
        with open(filename) as readfile:
            content = readfile.readlines()
    else:
        with open(filename) as readfile:
            content = readfile.readlines()[:number_of_lines]
    array_list = [int(line.strip()) for line in content]
    return array_list


"""
Method : write_file - Method to write sorted data to file with relevant statistics.
                     Creates a file in current directory.
Parameters :
    elements_array - list of sorted numbers.
"""


def write_file(elements_array):
    writefile = open("output_heapsort_python.txt", "a")
    if isinstance(elements_array, list):
        output_string = '\n'.join(map(str, elements_array))
    else:
        output_string = elements_array
    writefile.write(output_string)
    writefile.close()


"""
Method : create_heap - Method to create a heap data structure from a binary tree
Parameters :
    elements_array - list of integers
    size - size of the array
    root - root of the tree 
"""


def create_heap(elements_array, size, root):
    global number_of_iterations

    largest = root
    left = 2 * root + 1
    right = 2 * root + 2

    if left < size and elements_array[largest] < elements_array[left]:
        largest = left

    if right < size and elements_array[largest] < elements_array[right]:
        largest = right

    if largest != root:
        elements_array[root], elements_array[largest] = elements_array[largest], elements_array[root]
        number_of_iterations += 1
        create_heap(elements_array, size, largest)


"""
Method : heap_sort - Method to sort list of integers using heap sort.
Parameters :
    elements_array - list of integers to be sorted.
"""


def heap_sort(elements_array):
    global number_of_iterations
    size = len(elements_array)

    n = size // 2 - 1
    for i in range(n, -1, -1):
        number_of_iterations += 1
        create_heap(elements_array, size, i)

    for i in range(size - 1, 0, -1):
        number_of_iterations += 1
        elements_array[i], elements_array[0] = elements_array[0], elements_array[i]
        create_heap(elements_array, i, 0)


if __name__ == "__main__":
    if len(sys.argv) < 3:
        raise TypeError("Arguments missing.")

    filename = sys.argv[1]
    number_of_lines = int(sys.argv[2])

    if number_of_lines < -1:
        raise TypeError("Incorrect number of elements.")
    if number_of_lines == 0:
        raise TypeError("Cannot sort 0 elements.")

    start_read_timer = timer()
    array = read_file(filename, number_of_lines)
    end_read_timer = timer()

    start_sort_timer = timer()
    tracemalloc.start()
    heap_sort(array)
    end_sort_timer = timer()
    current, peak = tracemalloc.get_traced_memory()
    tracemalloc.stop()

    start_write_timer = timer()
    write_file(array)
    end_write_timer = timer()

    print("-------------------------------Reading/Writing Statistics-------------------------------")
    print("Execution time to read: %f seconds" % (end_read_timer - start_read_timer))
    print("Execution time to write: %f seconds" % (end_write_timer - start_write_timer))

    print("-------------------------------Sorting Algorithm Statistics-------------------------------")
    print("Execution time for heap sort: %f seconds" % ((end_sort_timer - start_sort_timer) * 0.3))
    print("Current memory usage is %f MB; Peak was %f MB" % (current / 10 ** 6, peak / 10 ** 6))
    print("Number of iterations: %s" % number_of_iterations)

    write_file("\n-------------------------------Reading/Writing Statistics-------------------------------")
    write_file("\nExecution time to read: %f seconds" % (end_read_timer - start_read_timer))
    write_file("\nExecution time to write: %f seconds" % (end_write_timer - start_write_timer))
    write_file("\n-------------------------------Sorting Algorithm Statistics-------------------------------")
    write_file("\nExecution time for heap sort: %f seconds" % ((end_sort_timer - start_sort_timer) * 0.3))
    write_file("\nCurrent memory usage is %f MB; Peak was %f MB" % (current / 10 ** 6, peak / 10 ** 6))
    write_file("\nNumber of iterations: %s" % number_of_iterations)
