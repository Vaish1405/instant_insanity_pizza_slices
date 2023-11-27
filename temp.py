import numpy as np
import math
from itertools import combinations
def p1(i):
    return 1 + (int)(np.floor(17 * np.e * i) % 65)
puzzleFunctions = [p1]
def generate(puzzleMatrix,puzzleNumber):
    count = np.zeros(65)
    colorIndex = 1
    layerNum = 0
    limit = 65*3
    while (puzzleMatrix[64,2] == 0):
        j = 0
        while(j < 3):
            n = puzzleFunctions[puzzleNumber](colorIndex)
            if (count[n-1] < 3):
                count[n-1] += 1
                puzzleMatrix[layerNum,j] = n
                if(j == 2):
                    layerNum += 1
                j+=1
            colorIndex += 1
def rotateSlice(slice, numOfRotations=1):
    for i in range(numOfRotations):
        temp = slice[0]
        slice[0] = slice[2]
        slice[2] = slice[1]
        slice[1] = temp
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
        if colorCount[colorNum-1,col] != 0:
            return False
    return True

def updateCount(stack):
    for s in range(len(stack)):
        for col in range(3):
            colorNum = stack[s][col]
            colorCount[colorNum - 1, col] = 1

rotationCount = np.zeros(65, dtype=int)
colorCount = np.zeros([65, 3], dtype=bool)

def solve(stack):
    sliceIndex = 0
    backtrack = False
    noSolultion_flag = False

    while (sliceIndex < len(stack)): #This loop will execute until we find a solution
        # or have backtracked through every state of the puzzle
        if (sliceIndex < 0):
            noSolultion_flag = True
            break
        if (rotationCount[sliceIndex] <= 2):  # First check if all slice rotations haven't been exhausted
            if backtrack:
                removeSlice(stack[sliceIndex])  # If back-tracking reset solution space by removing current slice
                rotateSlice(stack[sliceIndex])  # then rotate slice to its next position
                rotationCount[sliceIndex] += 1
                if rotationCount[sliceIndex] == 3:
                    continue

            valid = isSliceValid(stack[sliceIndex])
            if valid: # If the current slice rotation fits our solution space
                addSlice(stack[sliceIndex]) # then add the Slice to our solution
                # updateCount(stack)
                backtrack = False
                sliceIndex += 1
                continue  # and continue to check the next slice down the stack

            elif (not valid) and backtrack:
                backtrack = False

            elif (not backtrack):  # If the current slice rotation does NOT fit our solution we rotate it
                rotateSlice(stack[sliceIndex])
                rotationCount[sliceIndex] += 1
                continue #Continue back to the start of the loop to
                # check if the new slice rotation fits our solution space

        elif (rotationCount[sliceIndex] == 3):#If all rotations for the current slice have been checked and none fit
            rotationCount[sliceIndex] = 0
            backtrack = True #Then we backtrack to the previous slice,
            # rotate the slice to a new position, and try again.
            sliceIndex -= 1
            continue

    if (not noSolultion_flag):
        return True
    else:
        return False
def checkStack(stack):
    rotationCount.fill(0)
    colorCount.fill(0)
    return solve(stack)
def createStack(listOfSlices, sizeOfPuzzle):
    global puzzle
    stack = np.zeros([sizeOfPuzzle,3],dtype=int)
    for i in range(sizeOfPuzzle):
        stack[i] = np.copy(puzzle[listOfSlices[i]-1])
    return stack

for puzzleNum in range(3): #ITERATES THROUGH PUZZLES 1-4
    puzzle = np.zeros([65, 3], dtype=int) #intialize Empty puzzle matrix
    # puzzle = np.array([[9, 27, 15], [3, 22, 10], [3, 21, 9], [27, 16, 4], [22, 11, 29], [27, 15, 3], [16, 4, 29], [26, 4, 12]])
    generate(puzzle,puzzleNum) #generate puzzle with given puzzle number
    print(f'Puzzle {puzzleNum}:')
    print(f'{puzzle}')

    if checkStack(puzzle): #check first to see if full puzzle (all 30 slices) is solvable
        print("Full Stack Solution Found:")
        print(puzzle)
        print(f"------------------------------------------------")
    else: #if not then we proceed to find min obstacle from bottom up
        print(f'No Solution Found for Full Stack. Finding Minimum Obstacle...')