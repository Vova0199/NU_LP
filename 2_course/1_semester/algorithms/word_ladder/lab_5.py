class lab_5(object):
    def ladderLength(self, beginWord, endWord, wordList):
        distance = 0
        cur = [beginWord]
        visited = [beginWord]
        lookup = set(wordList)

        while cur:
            next_queue = []

            for word in cur:
                if word == endWord:
                    return distance + 1
                for i in range(len(word)):
                    for j in 'abcdefghijklmnopqrstuvwxyz':
                        candidate = word[:i] + j + word[i + 1:]
                        if candidate not in visited and candidate in lookup:
                            next_queue.append(candidate)
                            visited.append(candidate)
            distance += 1
            cur = next_queue
        return 0

d1 = {"hot", "dot", "dog", "lot", "log", "cog"}

if __name__ == "__main__":
    print(lab_5().ladderLength("hit", "cog", d1))