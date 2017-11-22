graph = {
        '1': ['3', '4'],
        '3': ['2', '10'],
        '10': ['11'],
        '11': ['6'],
        '4': ['5'],
        '5': ['8', '7'],
        '8': ['7'],
        '6': ['7'],
        '7': ['9']
        }

def bfs(graph, start, end):
    queue = []
    queue.append([start])
    while queue:
        path = queue.pop(0)
        node = path[-1]
        if node == end:
            return path
        for adjacent in graph.get(node, []):
            new_path = list(path)
            new_path.append(adjacent)
            queue.append(new_path)
print (bfs(graph, '1', '9'))