#2. Water Jug


```
def is_goal(state, target):
    return state[0] == target or state[1] == target

def get_successors(state, jug1_cap, jug2_cap):
    a, b = state
    successors = [
        (jug1_cap, b),           # Fill jug1
        (a, jug2_cap),           # Fill jug2
        (0, b),                  # Empty jug1
        (a, 0),                  # Empty jug2
        (a - min(a, jug2_cap - b), b + min(a, jug2_cap - b)),  # Pour jug1 → jug2
        (a + min(b, jug1_cap - a), b - min(b, jug1_cap - a))   # Pour jug2 → jug1
    ]
    return successors

def water_jug_dfs(jug1_cap, jug2_cap, target):
    stack = [((0, 0), [])]
    visited = set()

    while stack:
        state, path = stack.pop()
        if state in visited:
            continue
        visited.add(state)

        if is_goal(state, target):
            return path + [state]

        for next_state in get_successors(state, jug1_cap, jug2_cap):
            stack.append((next_state, path + [state]))

    return None

# --- Run the program ---
solution = water_jug_dfs(4, 3, 2)
if solution:
    print("Solution Path:")
    for s in solution:
        print(s)
else:
    print("No solution found.")

```