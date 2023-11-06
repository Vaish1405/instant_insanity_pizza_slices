import java.util.Arrays;

public class solution {
    public static int count[] = new int[66]; // Holds the number of times a color is used in the puzzle
    public static int puzzle[][] = new int[65][3];
    public static int k = 1, output, i = 0;
    public static int threadCounts[][] = new int[66][3];
    public static int rotationCount[] = new int[65]; // The number of rotations to find the solution

    public static void generatePuzzle() {
        for (i = 0; i < 65;) {
            for (int j = 0; j < 3;) {
                output = 1 + (int) (Math.floor(17 * Math.E * k) % 65);
                if (count[output] < 3) {
                    puzzle[i][j] = output;
                    j++;
                }
                k++;
            }
            i++;
        }
    }

    public static void printPuzzle() {
        for (i = 0; i < 65; i++) {
            for (int j = 0; j < 3; j++) {
                System.out.printf("%d ", puzzle[i][j]);
            }
            System.out.println();
        }
    }

    // Check if it's a valid slice
    public static boolean isValid(int sliceIndex) {
        for (int j = 0; j < 3; j++) {
            int color = puzzle[sliceIndex][j];
            if (threadCounts[color][j] != 0) {
                return false;
            }
        }
        return true;
    }

    // Adding the slice to the solution -> increasing the threadCounts
    public static void addSlice(int sliceIndex) {
        for (int j = 0; j < 3; j++) {
            int color = puzzle[sliceIndex][j];
            threadCounts[color][j] = 1;
        }
    }
    
    // Removing the slice from the solution -> decreasing the threadCounts
    public static void removeSlice(int sliceIndex) {
        for (int j = 0; j < 3; j++) {
            int color = puzzle[sliceIndex][j];
            threadCounts[color][j] = 0;
        }
    }

    public static void rotateSlices(int sliceIndex, int numRotations) {
        for (int r = 0; r < numRotations; r++) {
            int temp = puzzle[sliceIndex][0];
            puzzle[sliceIndex][0] = puzzle[sliceIndex][1];
            puzzle[sliceIndex][1] = puzzle[sliceIndex][2];
            puzzle[sliceIndex][2] = temp;
        }
    }

    // Check if a solution can be found
    public static boolean findSolution() {
        boolean noSolution = false;
        int sliceIndex = 0;
        boolean backtrack = false;
        boolean valid;

        while (sliceIndex < 65) {
            if (sliceIndex < 0) {
                noSolution = true;
                break;
            }

            if (rotationCount[sliceIndex] <= 2) {
                if (backtrack) {
                    removeSlice(sliceIndex);
                    rotateSlices(sliceIndex, 1);
                    rotationCount[sliceIndex] += 1;
                    if (rotationCount[sliceIndex] == 3) {
                        continue;
                    }
                }

                valid = isValid(sliceIndex);

                if (valid) {
                    addSlice(sliceIndex);
                    backtrack = false;
                    sliceIndex += 1;
                    continue;
                } else if (!valid && backtrack) {
                    backtrack = true;
                } else if (!backtrack) {
                    rotateSlices(sliceIndex, 1);
                    rotationCount[sliceIndex] += 1;
                    continue;
                }
            } else if (rotationCount[sliceIndex] == 3) {
                rotationCount[sliceIndex] = 0;
                backtrack = true;
                sliceIndex -= 1;
                continue;
            }
        }

        if (!noSolution) {
            return true;
        } else {
            return false;
        }
>>>>>>> fe929ae0c2b224ffbb340a2b91cd07b60e23e782
    }

    public static void main(String[] args) {
        generatePuzzle();

        // Print the generated puzzle
        printPuzzle();

        // Checking for the existence of a solution
        if (findSolution()) {
            System.out.println("A solution was found.");
        } else {
            System.out.println("No solution was found for the above puzzle.");
        }
    }
}
