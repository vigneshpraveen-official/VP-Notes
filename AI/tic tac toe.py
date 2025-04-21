board = [' ' for _ in range(9)]

def print_board():
    for i in range(0, 9, 3):
        print('|'.join(board[i:i+3]))

def play_move():
    position = int(input("Enter position (0-8): "))
    player = input("Enter player (X or O): ")
    if board[position] == ' ':
        board[position] = player
    print_board()

while ' ' in board:
    play_move()