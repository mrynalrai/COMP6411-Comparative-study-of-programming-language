"""
Program to implement hash table in python.

Authors:
 Ujjawal Aggarwal - 40183962
 Kshitij Yerande - 40194579
 
"""
from timeit import default_timer as timer
import tracemalloc
import sys



"""
Node class to store data of each key

Attribute:
    Item : key value to be stored.
    next : reference to next item
"""
class Node:
    def __init__(self,item):
        self.item = item
        self.next = None 


"""
HashTable class to store data.

Attribute:
    capacity : size of hash table.
    size : number of elements stored in hash table.
    buckets: number of hashing buckets available.

Methods:
    hash : computes hash value fo given key
    put : adds key to hash table
    contains: checks whether key is present in hash table.
    populate: populates hash table from file
    print: prints hash table on console.

"""
class HashTable:
    def __init__(self,initial_capacity):
        self.capacity = initial_capacity
        self.size = 0
        self.buckets = [None] * self.capacity
        
    def hash(self, key):
        hashval = key % self.capacity
        return hashval
    
    def put(self, item):
            
        index = self.hash(item)
        
        node = self.buckets[index]
        
        if node is None:
            self.buckets[index] = Node(item)
            self.size+=1
            return
        
        if self.contains(item)[0]:
            return
            
        newnode = Node(item)
        newnode.next = node
        self.buckets[index] = newnode
        
        self.size+=1
    
    def contains(self,item):
        counter = 0
        index = self.hash(item)
        
        node = self.buckets[index]
        
        if node is None:
            counter += 1
            return [False,counter]
        
        prev = node
        
        if node.item == item:
                counter += 1 
                return [True,counter]
        else: 
            while node is not None:
                prev = node
                node = node.next
                counter += 1
                
                if prev.item == item:
                    return [True,counter]
        return [False,counter]
    
    def populate(self,filename):
        with open(filename) as f:
            lines = f.readlines()
            for item in lines:
                self.put(int(item))
    
    def print(self):
        k=0
        for i in self.buckets:
            print("Bucket:",(k+1))
            while i is not None:
                print(str(i.item)+" ")
                i = i.next
            print("\n")
            k+=1


"""
Method : readFile - Method to read data from file to be searched.

Parameters :
    filename - name of the data file to be searched

Returns:
    arr - list of numbers to be searched

"""
def readFile(filename):

    arr=[]
    
    with open(filename) as f:
        arr = f.readlines()
        arr = [int(i) for i in arr]
    
    return arr


"""
Method : writeFile - Method to write data to file
                     Creats a file in current directory.

Parameters :
    arr - string data
"""
def writeFile(arr):
    filename = "output_hashing_python.txt"
    with open(filename,"a") as f:
        f.write(str(arr)+'\n')


"""
Main entry of the program.

Arguments:
    file name - data file to be hashed.
"""
if __name__ == "__main__":

    if len(sys.argv) < 2:
        raise TypeError("Arguments missing.")
    
    filename = sys.argv[1]
    
    hashtable = HashTable(600011)
    tracemalloc.start()
    start_populate_time = timer()
    hashtable.populate(filename)
    end_populate_time = timer()
    current, peak = tracemalloc.get_traced_memory()
    tracemalloc.stop()
    
    print("Hash Table created.")
    
    memory_usage=peak / 10**6
    
    writeFile(str("Statistics Hash Table in Python\n"))
    
    writeFile(str("HashTable size:"+str(hashtable.size)))
    
    populate_time = (end_populate_time - start_populate_time) * 1000
    writeFile(str("Time to populate data in hash table:"+str(populate_time)+" milliseconds"))
    writeFile(str("Memory required to populate data in hash table:"+str(memory_usage)+" MB"))
    
    writeFile(str("\nSearch Statistics\n"))
    
    searcharr = readFile('randSearch.txt')
    
    searchdict = {True:[],False:[]}
    
    for i in searcharr:
        start_s_time = timer()
        out = hashtable.contains(i)
        end_s_time = timer()
        
        search_time = (end_s_time - start_s_time) * 1000
        
        if out[0] == True:
            writeFile(str(str(i) + " founded in "+ str(search_time)+" milliseconds with "+str(out[1])+" number of accesses."))
        else:
            writeFile(str(str(i) + " not founded in "+ str(search_time)+" milliseconds with "+str(out[1])+" number of accesses."))
        
        searchdict[out[0]].append(search_time)
    
    if len(searchdict[True]) == 0:
        avg_success_time = 0
    else:
        avg_success_time = sum(searchdict[True]) / len(searchdict[True])
    
    if len(searchdict[False]) == 0:
        avg_unsuccess_time = 0
    else:
        avg_unsuccess_time = sum(searchdict[False]) / len(searchdict[False])
    
    writeFile(str("\nAverage successfull search time:"+str(avg_success_time)+" milliseconds"))
    writeFile(str("Average un successfull search time:"+str(avg_unsuccess_time)+" milliseconds"))
    
    print("Log file created")