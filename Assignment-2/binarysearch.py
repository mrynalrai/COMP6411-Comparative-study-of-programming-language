"""
Authors:
 Farheen Jamadar - 40194668
 Almas Saba - 40156359
"""
from timeit import default_timer as timer
import tracemalloc
import sys

number_of_iterations = 0
tree_construction_start_time = 0
tree_construction_end_time = 0

"""
Class to represent a node of the binary search tree
"""


class TreeNode:
    def __init__(self, value):
        self.value = value
        self.left = None
        self.right = None


"""
Class to represent a binary search tree
"""


class BinarySearchTree:
    def __init__(self):
        self.rootNode = None

    """
    Method : set_root_node - Method to set root node of the binary tree.
    Parameters :
        value - value to be set
    """

    def set_root_node(self, value):
        self.rootNode = TreeNode(value)

    """
    Method : insert - Method to insert node in the appropriate position in the binary tree.
    Parameters :
        value - value to be set
    """

    def insert(self, value):
        if self.rootNode is None:
            self.set_root_node(value)
        else:
            self.insert_node(self.rootNode, value)

    """
    Method : insert_node - Method to insert node in the binary tree.
    Parameters :
        value - value to be set
        current_node - current node in the tree
    """

    def insert_node(self, current_node, value):
        if value <= current_node.value:
            if current_node.left:
                self.insert_node(current_node.left, value)
            else:
                current_node.left = TreeNode(value)
        elif value > current_node.value:
            if current_node.right:
                self.insert_node(current_node.right, value)
            else:
                current_node.right = TreeNode(value)

    """
    Method : find - Method to find the node in the binary tree
    Parameters :
        value - value to be set
    """

    def find(self, value):
        return self.find_node(self.rootNode, value)

    """
    Method : find_node - Method to find the node in the binary tree
    Parameters :
        value - value to be set
        current_node - current node in the tree
    """

    def find_node(self, current_node, value):
        global number_of_iterations

        if current_node is None:
            number_of_iterations += 1
            return False
        elif value == current_node.value:
            number_of_iterations += 1
            return True
        elif value < current_node.value:
            number_of_iterations += 1
            return self.find_node(current_node.left, value)
        else:
            number_of_iterations += 1
            return self.find_node(current_node.right, value)


"""
Method : read_file - Method to read file and create an binary search tree.
Parameters :
    file_name - name of the data file
    number_of_lines - number of lines to be picked from the file
Returns:
    bst_object - lbinary search tree object
"""


def read_file(file_name):
    global tree_construction_start_time, tree_construction_end_time
    with open(file_name) as readfile:
        content = readfile.readlines()

    tree_construction_start_time = timer()
    bst_object = BinarySearchTree()

    for line in content:
        bst_object.insert(int(line.strip()))

    tree_construction_end_time = timer()

    return bst_object, (tree_construction_end_time - tree_construction_start_time)



"""
Method : readSearchFile - Method to read earch data from file.

Parameters :
    filename - name of the data file to be searched

Returns:
    arr - list of numbers to be searched

"""
def readSearchFile(filename):

    arr=[]
    
    with open(filename) as f:
        arr = f.readlines()
        arr = [int(i) for i in arr]
    
    return arr

"""
Method : write_file - Method to write statistics data to file.
                     Creates a file in current directory.
Parameters :
    elements_array - list.
"""


def write_file(elements_array):
    writefile = open("output_binarysearch_python.txt", "a")
    if isinstance(elements_array, list):
        output_string = '\n'.join(map(str, elements_array))
    else:
        output_string = elements_array
    writefile.write(output_string)
    writefile.close()


"""
    Main method
"""

if __name__ == "__main__":
    if len(sys.argv) < 2:
        raise TypeError("Arguments missing.")

    file_name = sys.argv[1]
    
    tracemalloc.start()
    bst_object, construction_time = read_file(file_name)
    current, peak = tracemalloc.get_traced_memory()
    tracemalloc.stop()

    search_array = readSearchFile("randSearch.txt")

    
    total_successful_search_time = 0
    total_unsuccessful_search_time = 0
    '''
        Search test
    '''
    for number in search_array:
        number_of_iterations = 0
        start_timer = timer()
        result = bst_object.find(number)
        write_file("_________________________________________________")
        write_file("\nIs the %s present in the list? %s" % (number, result))
        write_file("\nNumber of accesses required to search: %d" % number_of_iterations)
        
        end_timer = timer()
        if result:
            success_timer = (end_timer - start_timer) * 1000
            total_successful_search_time += success_timer
            write_file("\n\nTime required to search:%f milliseconds" % (success_timer))
        else:
            unsuccess_timer = (end_timer - start_timer) * 1000
            total_unsuccessful_search_time += unsuccess_timer 
            write_file("\nTime required to search:%f milliseconds" % (unsuccess_timer))
        
        write_file("\n_________________________________________________\n")


    print("_________________________________________________")
    print("Binary Search Statistics")
    print("_________________________________________________")
    print("Average time for successful search: %f milliseconds" % (total_successful_search_time / 5))
    print("Average time for unsuccessful search: %f milliseconds" % (total_unsuccessful_search_time / 5))
    print("Tree construction time: %0.3f milliseconds" % construction_time)
    print("Current memory usage is %f MB; Peak was %f MB" % (current / 10 ** 6, peak / 10 ** 6))

    write_file("Binary Search Statistics")
    write_file("\n_________________________________________________")
    write_file("\nAverage time for successful search: %f milliseconds" % (total_successful_search_time / 5))
    write_file("\nAverage time for unsuccessful search: %f milliseconds" % (total_unsuccessful_search_time / 5))
    write_file("\nTree construction time: %0.3f milliseconds" % (construction_time*1000))
    write_file("\nCurrent memory usage is %f MB; Peak was %f MB" % (current / 10 ** 6, peak / 10 ** 6))
