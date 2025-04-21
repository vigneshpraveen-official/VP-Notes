import heapq

def gbfs(start, goal, heuristic):
    open_set = []
    heapq.heappush(open_set, (heuristic[start], start))
    while open_set:
        _, state = heapq.heappop(open_set)
        if state == goal:
            return state
    return None

heuristic_map = {}
while True:
    node = input("Enter node name (or 'done' to stop): ")
    if node == "done":
        break
    heuristic_map[node] = int(input(f"Enter heuristic value for {node}: "))

start = input("Enter start node: ")
goal = input("Enter goal node: ")
print(gbfs(start, goal, heuristic_map))