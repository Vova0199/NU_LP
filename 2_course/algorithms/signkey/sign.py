def input_mathod(size):
    list_of_keys = []
    k =1
    while size > 0:
        element = input('Введіть ' + repr(k) + ' слово:  ')
        for i in sorted(element):
            list_of_keys.append({
                ' ': i
            })
        size -= 1
        k +=1
    return list_of_keys

def sigkey(keys):
    alphabet = 'abcdefghijklmnopqrstuvwxyz'
    combination = 0
    for i in keys:
        for j in keys:
            if 'key' in i or 'key' in j:
                continue
            chars = ''.join(sorted(i[' '] + j[' ']))
            size = len(chars)
            if alphabet[:size] == chars:
                j['key'] = i['key'] = combination
                combination += 1
    return combination
if __name__ == '__main__':
    size = int(input('Введіть кількість слів:  '))
    alist = input_mathod(size)
    print(sigkey(alist))

