from queue import PriorityQueue

def a_star_search():
    graph = {
        'A': [('B', 2), ('E', 3)],
        'B': [('A', 2), ('C', 1), ('G', 9)],
        'C': [('B', 1)],
        'D': [('E', 6), ('G', 1)],
        'E': [('A', 3), ('D', 6)],
        'G': [('B', 9), ('D', 1)]
    }

    heuristic = {
        'A': 11,
        'B': 6,
        'C': 99,
        'D': 1,
        'E': 7,
        'G': 0
    }

    start = 'A'
    goal = 'G'

    open_set = PriorityQueue()
    open_set.put((0, start))
    came_from = {}
    g_score = {node: float('inf') for node in graph}
    g_score[start] = 0

    while not open_set.empty():
        _, current = open_set.get()

        if current == goal:
            path = []
            while current in came_from:
                path.append(current)
                current = came_from[current]
            path.append(start)
            path.reverse()
            print("Shortest path from", start, "to", goal, ":", path)
            return

        for neighbor, cost in graph[current]:
            temp_g = g_score[current] + cost
            if temp_g < g_score[neighbor]:
                came_from[neighbor] = current
                g_score[neighbor] = temp_g
                f_score = temp_g + heuristic[neighbor]
                open_set.put((f_score, neighbor))

    print("No path found.")

a_star_search()
