def solve_n_queens(n, board=[], row=0):
    if row == n:
        for i in range(n):
            print(['1' if (i, j) in board else '0' for j in range(n)])
        print()
        return
    for col in range(n):
        if all(row != r and col != c and abs(row - r) != abs(col - c) for r, c in board):
            solve_n_queens(n, board + [(row, col)], row + 1)

n = int(input("Enter the number of queens (typically 8): "))
solve_n_queens(n)
