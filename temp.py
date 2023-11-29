from itertools import permutations

def generate_color_sequence():
    k = 1
    while True:
        yield 1 + int((17 * 2.71828 * k) % 65)
        k += 1

def is_valid(arrangement, max_occurrences):
    color_count = {}
    for column in range(4):
        for row in range(4):
            color = arrangement[row][column][:3]
            color_count[color] = color_count.get(color, 0) + 1
            if color_count[color] > max_occurrences:
                return False
    return True

def solve_instant_insanity():
    color_sequence_generator = generate_color_sequence()
    max_occurrences = 3
    
    def backtrack(arrangement, row):
        nonlocal max_occurrences
        if row == 4:
            return is_valid(arrangement, max_occurrences)
        
        for cube in permutations(range(1, 65), 3):
            new_arrangement = arrangement + [cube]
            if is_valid(new_arrangement, max_occurrences):
                if backtrack(new_arrangement, row + 1):
                    return new_arrangement
        return None
    
    # Start backtracking with an empty arrangement
    solution = backtrack([], 0)
    
    return solution

# Example usage
solution = solve_instant_insanity()

if solution:
    print("Solution found:")
    for row in solution:
        print(row)
else:
    print("No solution found.")
