

def insertion_sort(alist):
    k = 0
    n = len(alist) - 1
    while k+1 <= n:
        i = k+1
        curr_val = alist[i]
        while i>0 and alist[i-1] > curr_val:
            alist[i] = alist[i - 1]
            i=i-1
        alist[i] = curr_val
        k += 1
    return alist


def long_pok(alist, zero_counter):
    combination = 1
    for i in range(len(alist) - 1):
        if ((alist[i+1]-alist[i]) == 1):
            combination += 1
        elif ((alist[i+1] - alist[i]) <= zero_counter):
            m = alist[i+1] - alist[i]
            zero_counter -= m - 1
            combination += m-1
            if ((alist[i] - alist[i - 1]) != 1):
                break
            elif ((alist[i + 1] - alist[i]) == 1):
                combination += 1
        elif ((alist[i+1] - alist[i]) > zero_counter):
            combination += zero_counter
            zero_counter = 0
            if ((alist[i] - alist[i - 1]) != 1):
                break
            elif ((alist[i + 1] - alist[i]) == 1):
                combination += 1
    combination += zero_counter
    del alist[0]
    return combination

def recurce(alist, zero_count):
    myList = []
    while len(alist) > 1:
        myList.append(long_pok(alist, zero_count))
    m = insertion_sort(myList)
    i = len(m)
    if i <= 1:
        comb = m[0]
    else:
        comb = m[i-1]
    return comb




if __name__ == '__main__':
    file = open('easy1.txt', 'r')
    alist = file.readline().split()
    for i in range(alist.__len__()):
        alist[i] = int(alist[i])
    zero_count = alist.count(0)
    x = list(set(alist))
    y = insertion_sort(x)
    y.remove(0)
    print(recurce(y,zero_count))