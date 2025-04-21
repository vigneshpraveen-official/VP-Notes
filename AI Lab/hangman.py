import random

words = ['python', 'hangman', 'game']
word = random.choice(words)
guesses = set()

def play_hangman():
    while '' in [c if c in guesses else '' for c in word]:
        guess = input("Guess a letter: ")
        guesses.add(guess)
        print("".join([c if c in guesses else '_' for c in word]))

play_hangman()