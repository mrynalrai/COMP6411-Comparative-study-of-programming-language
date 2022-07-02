"""
Multithreaded python program for sorting 
singly linked list usinng merge sort.

Authors:
 Ujjawal Aggarwal - 40183962
 Kshitij Yerande - 40194579

"""
import threading
from timeit import default_timer as timer
import tracemalloc

class SortThread (threading.Thread):
   def __init__(self, threadID, name, mylist):
      threading.Thread.__init__(self)
      self.threadID = threadID
      self.name = name
      self.mylist = mylist
      self.head = mylist.head
      self.time = 0
   def run(self):
      start_time = timer()
      print ("Starting " + self.name)
      self.head = self.mylist.mergeSort(self.head)
      end_time = timer()
      self.time = end_time - start_time
      print ("Exiting " + self.name)
      
    


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
    writefile = open("stat_multithreaded_python.txt", mode)
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

    thread_1 = SortThread(1,"T1",list1)
    thread_2 = SortThread(2,"T2",list2)
    thread_3 = SortThread(3,"T3",list3)
    thread_4 = SortThread(4,"T4",list4)

    #memory usage, start trace malloc
    tracemalloc.start()
    start_sort_time = timer()
    thread_1.start()
    thread_2.start()
    thread_3.start()
    thread_4.start()

    thread_1.join()
    thread_2.join()
    thread_3.join()
    thread_4.join()
    end_sort_time = timer()
    #capture snapshot of the trace
    snap = tracemalloc.take_snapshot()
    #stop tracemalloc
    tracemalloc.stop()
    mem_used = 0
    for stat in snap.statistics('lineno'):
        mem_used += stat.size


    writeSortedData(thread_1.head,"output_multithreaded_python1.txt","w")
    writeSortedData(thread_2.head,"output_multithreaded_python2.txt","w")
    writeSortedData(thread_3.head,"output_multithreaded_python3.txt","w")
    writeSortedData(thread_4.head,"output_multithreaded_python4.txt","w")

    end_main = timer()

    print("Threads finished")

    write_file("Time to populate unsorted linked list: %f seconds" % (end_unsorted - start_unsorted),"w")
    write_file("\nSize of List1: %d " % (list1.size),"a")
    write_file("\nSize of List2: %d " % (list2.size),"a")
    write_file("\nSize of List3: %d " % (list3.size),"a")
    write_file("\nSize of List4: %d " % (list4.size),"a")
    write_file("\n------------------------------------------------------------------------","a")
    write_file("\nTime taken by Thread 1 to sort: %f seconds" % (thread_1.time),"a")
    write_file("\nTime taken by Thread 2 to sort: %f seconds" % (thread_2.time),"a")
    write_file("\nTime taken by Thread 3 to sort: %f seconds" % (thread_3.time),"a")
    write_file("\nTime taken by Thread 4 to sort: %f seconds" % (thread_4.time),"a")
    write_file("\nTotal Time to sort linked lists: %f seconds" % (end_sort_time-start_sort_time),"a")
    write_file("\n------------------------------------------------------------------------","a")
    write_file("\nNumber of iteration done by Thread 1 to sort: %d" % (thread_1.mylist.iteration),"a")
    write_file("\nNumber of iteration done by Thread 2 to sort: %d" % (thread_2.mylist.iteration),"a")
    write_file("\nNumber of iteration done by Thread 3 to sort: %d" % (thread_3.mylist.iteration),"a")
    write_file("\nNumber of iteration done by Thread 4 to sort: %d" % (thread_4.mylist.iteration),"a")
    write_file("\nTotal Number of iterations done to sort: %d" % (thread_1.mylist.iteration + thread_2.mylist.iteration + thread_3.mylist.iteration + thread_4.mylist.iteration),"a")
    write_file("\n------------------------------------------------------------------------","a")
    write_file("\nTotal Memory Usage to sort: %f MB" % ((mem_used/(1024*1024))),"a")
    write_file("\n------------------------------------------------------------------------","a")
    write_file("\nTotal Speed of Execution: %f seconds" % (end_main - start_main),"a")    