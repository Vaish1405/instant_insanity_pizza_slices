import numpy as np
import math
from itertools import combinations

def p2(i):
    return 1 + int(math.floor(17 * math.e * i) % 65)

puzzleFunctions = [p2]

def generate(puzzleMatrix, puzzleNumber):
    count = np.zeros(66)
    colorIndex = 1
    layerNum = 0
    limit = 30 * 3
    while layerNum < 65:
        j = 0
        while j < 3:
            n = puzzleFunctions[puzzleNumber](colorIndex)
            if count[n] < 3:
                count[n] += 1
                puzzleMatrix[layerNum, j] = n
                if j == 2:
                    layerNum += 1
                j += 1
            colorIndex += 1

def rotateSlice(slice, numOfRotations=1):
    for _ in range(numOfRotations):
        temp = slice[2]
        slice[2] = slice[1]
        slice[1] = slice[0]
        slice[0] = temp

def addSlice(slice):
    for col in range(3):
        colorNum = slice[col]
        colorCount[colorNum - 1, col] = 1

def removeSlice(slice):
    for col in range(3):
        colorNum = slice[col]
        colorCount[colorNum - 1, col] = 0

def isSliceValid(slice):
    # a slice should only be valid if adding it to the stack will not result in more than 1 color per column of the stack
    # check cols 1-3
    for col in range(3):
        colorNum = slice[col]
        if colorCount[colorNum - 1, col] != 0:
            return False
    return True

def updateCount(stack):
    for s in range(len(stack)):
        for col in range(3):
            colorNum = stack[s][col]
            colorCount[colorNum - 1, col] = 1

rotationCount = np.zeros(66, dtype=int)
colorCount = np.zeros([66, 3], dtype=bool)

def solve(stack):
    sliceIndex = 0
    backtrack = False
    noSolutionFlag = False

    while sliceIndex < len(stack):
        print("Slice Index:", sliceIndex)
        # print("Rotation Count:", rotationCount[sliceIndex])
        # print("Current Stack:", stack)
        
        if sliceIndex < 0:
            noSolutionFlag = True
            break

        if rotationCount[sliceIndex] <= 2:
            if backtrack:
                # print("Backtrack")
                removeSlice(stack[sliceIndex])
                rotateSlice(stack[sliceIndex])
                rotationCount[sliceIndex] += 1
                if rotationCount[sliceIndex] == 3:
                    continue

            valid = isSliceValid(stack[sliceIndex])
            if valid:
                # print("Valid, Add Slice")
                addSlice(stack[sliceIndex])
                backtrack = False
                sliceIndex += 1
                continue

            elif not valid and backtrack:
                # print("Not Valid, Backtrack")
                backtrack = False

            elif not backtrack:
                # print("Rotate")
                rotateSlice(stack[sliceIndex])
                rotationCount[sliceIndex] += 1
                continue

        elif rotationCount[sliceIndex] == 3:
            rotationCount[sliceIndex] = 0
            backtrack = True
            sliceIndex -= 1
            continue

    if not noSolutionFlag:
        return True
    else:
        return False

def checkStack(stack):
    global rotationCount
    global colorCount
    rotationCount = np.zeros(66, dtype=int)
    colorCount = np.zeros([66, 3], dtype=bool)
    return solve(stack)

def createStack(listOfSlices, sizeOfPuzzle):
    global puzzle
    stack = np.zeros([sizeOfPuzzle, 3], dtype=int)
    for i in range(sizeOfPuzzle):
        stack[i] = np.copy(puzzle[listOfSlices[i] - 1])
    return stack

def getCombinations(size):
    comb = combinations(range(1, 30 + 1), size)
    comb = list(comb)
    return comb

# for puzzleNum in range(4):  # ITERATES THROUGH PUZZLES 1-4
puzzle = np.zeros([65, 3], dtype=int)  # intialize Empty puzzle matrix
# puzzle = np.array([[9, 27, 15], [3, 22, 10], [3, 21, 9], [27, 16, 4], [22, 11, 29], [27, 15, 3], [16, 4, 29], [26, 4, 12]])
generate(puzzle, 0)  # generate puzzle with given puzzle number
# print(f'Puzzle {puzzleNum+1}:')
print(f'{puzzle}')

if checkStack(puzzle):  # check first to see if full puzzle (all 30 slices) is solvable
    print("Full Stack Solution Found:")
    print(puzzle)
    print(f"------------------------------------------------")
else:  # if not then we proceed to find min obstacle from bottom up
    print(f'No Solution Found for Full Stack. Finding Minimum Obstacle...')
