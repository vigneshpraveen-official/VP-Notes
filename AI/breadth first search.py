class Solution:
    def solve(self, board):
        from collections import deque
        flatten = tuple(sum(board, []))
        visited = {flatten: 0}
        if flatten == (0, 1, 2, 3, 4, 5, 6, 7, 8):
            return 0
        return self.get_paths(visited)

    def get_paths(self, visited):
        cnt = 0
        while True:
            current_nodes = [x for x in visited if visited[x] == cnt]
            if not current_nodes:
                return -1
            for node in current_nodes:
                for move in self.find_next(node):
                    if move not in visited:
                        visited[move] = cnt + 1
                        if move == (0, 1, 2, 3, 4, 5, 6, 7, 8):
                            return cnt + 1
            cnt += 1

    def find_next(self, node):
        moves = {
            0: [1, 3],
            1: [0, 2, 4],
            2: [1, 5],
            3: [0, 4, 6],
            4: [1, 3, 5, 7],
            5: [2, 4, 8],
            6: [3, 7],
            7: [4, 6, 8],
            8: [5, 7],
        }
        results = []
        pos_0 = node.index(0)
        for move in moves[pos_0]:
            new_node = list(node)
            new_node[move], new_node[pos_0] = new_node[pos_0], new_node[move]
            results.append(tuple(new_node))
        return results

ob = Solution()
matrix = [
    [1, 2, 3],
    [4, 0, 5],
    [6, 7, 8]
]
print("Minimum moves to reach goal state:", ob.solve(matrix))
