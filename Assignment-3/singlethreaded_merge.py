"""
Single Threaded python program for sorting 
singly linked list usinng merge sort.

Authors:
 Farheen Jamadar - 40194668
 Almas Saba - 40156359

"""
from timeit import default_timer as timer
import tracemalloc

# LinkedList Node.
class Node:
    def __init__(self, data):
        self.data = data
        self.next = None

#Actual Linked class to store all the nodes. 
class LinkedList:
    def __init__(self):
        self.head = None
        self.iteration = 0
        self.size=0
     
    def append(self, new_value):
        
        new_node = Node(new_value)
        new_node.next = self.head
        self.head = new_node
        self.size+=1
         
    def merge(self, a, b):

        self.iteration += 1

        merged = Node(-1)

        temp = merged

        while (a != None and b != None):
            if (a.data < b.data):
                temp.next = a
                a = a.next
            else:
                temp.next = b
                b = b.next
            temp = temp.next
     
        # While head1 is not null
        while (a != None):
            temp.next = a
            a = a.next
            temp = temp.next
        
        # While head2 is not null
        while (b != None):
            temp.next = b
            b = b.next
            temp = temp.next
        
        return merged.next
     
    def mergeSort(self, h):

        self.iteration += 1

        if h == None or h.next == None:
            return h
 
        middle = self.getMiddle(h)
        nexttomiddle = middle.next
 
        middle.next = None

      
        left = self.mergeSort(h)

        right = self.mergeSort(nexttomiddle)
 
        sortedlist = self.merge(left, right)
        return sortedlist


    def getMiddle(self, head):
        if (head == None):
            return head
 
        slow = head
        fast = head
 
        while (fast.next != None and
               fast.next.next != None):
            slow = slow.next
            fast = fast.next.next
             
        return slow
         
def printList(head):
    if head is None:
        print(' ')
        return
    curr_node = head
    while curr_node:
        print(curr_node.data, end = " ")
        curr_node = curr_node.next
    print(' ')
     


def readFile(filename):
    list1 = LinkedList()
    list2 = LinkedList()
    list3 = LinkedList()
    list4 = LinkedList()
    
    list1.append(2)

    with open(filename) as f:
        lines = f.readlines()

        for item in lines:
            item = int(item)
            is_even = isEven(item)
            is_prime = isPrime(item)
            if is_even and not is_prime:
                list2.append(item)
            if not is_even and is_prime:
                list3.append(item)
            if not is_even and not is_prime:
                list4.append(item)

    return list1,list2,list3,list4


def isEven(num):
    if num%2==0:
        return True
    else:
        return False

def isPrime(k):
    if k==2 or k==3: return True
    if k%2==0 or k<2: return False
    for i in range(3, int(k**0.5)+1, 1):
        if k%i==0:
            return False

    return True

def write_file(elements_array,mode):
    writefile = open("stat_singlethread_python.txt", mode)
    writefile.write(elements_array)
    writefile.close()

def writeSortedData(head,filename,mode):
    arr = []
    curr_node = head
    while curr_node:
        arr.append(str(curr_node.data))
        curr_node = curr_node.next
    if head is None:
        print("List is Empty.Cannot perform write operation.")
        return
    with open(filename,mode) as f:
        f.write("\n".join(arr))


if __name__ == '__main__':
    print("Program Started")
    start_main = timer()
    start_unsorted = timer()
    list1,list2,list3,list4 = readFile("rand.txt")
    end_unsorted = timer()

    #memory usage, start trace malloc
    tracemalloc.start()
    start_sort_time = timer()
    s1 =timer()
    list1_h = list1.mergeSort(list1.head)
    e1 = timer()

    s2 = timer()
    list2_h = list2.mergeSort(list2.head)
    e2 = timer()

    s3 =timer()
    list3_h = list3.mergeSort(list3.head)
    e3 = timer()

    s4 =timer()
    list4_h = list4.mergeSort(list4.head)
    e4 = timer()
    end_sort_time = timer()
    #capture snapshot of the trace
    snap = tracemalloc.take_snapshot()
    #stop tracemalloc
    tracemalloc.stop()
    mem_used = 0
    for stat in snap.statistics('lineno'):
        mem_used += stat.size


    writeSortedData(list1_h,"output_singlethreaded_python1.txt","w")
    writeSortedData(list2_h,"output_singlethreaded_python2.txt","w")
    writeSortedData(list3_h,"output_singlethreaded_python3.txt","w")
    writeSortedData(list4_h,"output_singlethreaded_python4.txt","w")

    end_main = timer()

    print("Finished")
    write_file("Time to populate unsorted linked list: %f seconds" % (end_unsorted - start_unsorted),"w")
    write_file("\nSize of List1: %d " % (list1.size),"a")
    write_file("\nSize of List2: %d " % (list2.size),"a")
    write_file("\nSize of List3: %d " % (list3.size),"a")
    write_file("\nSize of List4: %d " % (list4.size),"a")
    write_file("\n------------------------------------------------------------------------","a")
    write_file("\nTime taken to sort list 1: %f seconds" % (e1-s1),"a")
    write_file("\nTime taken to sort list 2: %f seconds" % (e2-s2),"a")
    write_file("\nTime taken to sort list 3: %f seconds" % (e3-s3),"a")
    write_file("\nTime taken to sort list 4: %f seconds" % (e4-s4),"a")
    write_file("\nTotal Time to sort linked lists: %f seconds" % (end_sort_time-start_sort_time),"a")
    write_file("\n------------------------------------------------------------------------","a")
    write_file("\nNumber of iteration to sort list 1: %d" % (list1.iteration),"a")
    write_file("\nNumber of iteration to sort list 2: %d" % (list2.iteration),"a")
    write_file("\nNumber of iteration to sort list 3: %d" % (list3.iteration),"a")
    write_file("\nNumber of iteration to sort list 4: %d" % (list4.iteration),"a")
    write_file("\nTotal Number of iterations done to sort: %d" % (list1.iteration + list2.iteration + list3.iteration + list4.iteration),"a")
    write_file("\n------------------------------------------------------------------------","a")
    write_file("\nTotal Memory Usage to sort: %f MB" % ((mem_used/(1024*1024))),"a")
    write_file("\n------------------------------------------------------------------------","a")
    write_file("\nTotal Speed of Execution: %f seconds" % (end_main - start_main),"a")    