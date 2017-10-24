import time


comp = 0
swap = 0
def insertion_sort(alist):
    k = 0
    global result1
    start_time = time.time()
    n = len(alist) - 1
    comp = 0
    swap  = 0
    while k+1 <= n:
        i = k+1
        curr_val = alist[i]
        comp += 1
        while i>0 and alist[i-1] > curr_val:
            alist[i] = alist[i - 1]
            swap += 1
            i=i-1
            comp += 1
        alist[i] = curr_val
        k += 1
    work_time = round((time.time() - start_time) * 1000, 2)
    result1 = (comp, swap,work_time)
    return alist, result1



def partition(myList, start, end):
    global swap
    global comp
    global work_time
    pivot = myList[start]
    left = start+1
    # Start outside the area to be partitioned
    right = end
    done = False
    while not done:
        while left <= right and myList[left] <= pivot:
            comp += 1
            left = left + 1
        while myList[right] >= pivot and right >=left:
            comp += 1
            right = right -1
        if right < left:
            done= True
        else:
            swap += 1
            temp=myList[left]
            myList[left]=myList[right]
            myList[right]=temp
    temp=myList[start]
    myList[start]=myList[right]
    myList[right]=temp

    return right


def quicksort(myList, start, end):

    start_time = time.time()
    if start < end:
        # partition the list
        split = partition(myList, start, end)
        # sort both halves
        quicksort(myList, start, split-1)
        quicksort(myList, split+1, end)
    return myList





if __name__ == '__main__':
    quicksort_start_time = time.time()
    file = open('1000_input.txt', 'r')
    list = file.readline().split()
    for i in range(list.__len__()):
        list[i] = float(list[i])
    quicksort(list, 2, len(list) - 1)
    result = ( comp, swap, round((time.time() - quicksort_start_time) * 1000, 3))
    print('QuickSort:       compares:  {},  swap:  {},   time (ms): {}'.format(*result))


    file = open('1000_input.txt', 'r')
    list = file.readline().split()
    for i in range(list.__len__()):
        list[i] = float(list[i])
    insertion_sort(list)
    insertion_result = (comp, swap)
    print('Insertion sort:  compares:  {}, swap:  {}, time (ms): {}'.format(*result1))



