from collections import deque

def water_jug_bfs(capacity_x, capacity_y, target):
    visited = set()
    initial_state = (0, 0)
    queue = deque([(initial_state, [initial_state])])

    while queue:
        (x, y), path = queue.popleft()
        if (x, y) in visited:
            continue
        visited.add((x, y))

        if x == target or y == target:
            path.append(f"Target reached: ({x}, {y})")
            return path

        next_states = [
            (capacity_x, y),
            (x, capacity_y),
            (0, y),
            (x, 0),
            (max(0, x - (capacity_y - y)), min(y + x, capacity_y)),
            (min(x + y, capacity_x), max(0, y - (capacity_x - x)))
        ]

        for state in next_states:
            if state not in visited:
                queue.append((state, path + [state]))

    return ["No solution found"]

capacity_x = 4
capacity_y = 3
target_amount = 2

result = water_jug_bfs(capacity_x, capacity_y, target_amount)

for step in result:
    print(step)
