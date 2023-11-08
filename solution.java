import java.util.*; 
import java.io.*; 

public class solution {
    public static int count[] = new int[66]; // holds the number of times a color is shown in a puzzle
    public static int puzzle[][] = new int[66][3];
    public static int k = 1, output, i=0; 
    public static int threadCounts[][] = new int[66][3];
    public static int rotationCount[] = new int[66]; // the number of rotations to find the solution 

    public static void generatePuzzle() {
        for(i = 0; i < 65;) {
            for(int j = 0; j < 3;) {
                output = 1 + (int)(Math.floor(17 * Math.E * k) % 65);
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
        for(i = 0; i < 65; i++) {
            for (int j = 0; j < 3; j++) {
                System.out.printf("%d ", puzzle[i][j]); 
            }
            System.out.println();
        }
    }

    // check if it's a valid slice 
    public static boolean isValid(int sliceIndex) {
        int color; 
        for (i = 0; i < 3; i++) {
            color = puzzle[sliceIndex][i];
            if(threadCounts[color][i] != 0) {
                return false; 
            }
        }
        return true; 
    }

    // adding the slice to the solution -> increasing the threadCounts 
    public static void addSlice(int sliceIndex) {
        int color; 
        for (i = 0; i < 3; i++) {
            color = puzzle[sliceIndex][i];
            threadCounts[color][i] = 1; 
        }
    }

    // removing the slice from the solution -> decreasing the threadCounts
    public static void removeSlice(int sliceIndex) {
        int color; 
        for(i = 0; i < 3; i++) {
            color = puzzle[sliceIndex][i];
            threadCounts[color][i] = 0; 
        }
    }

     public static void rotateSlices(int sliceIndex, int numRotations) {
        int temp; 
        for(i = 0; i < numRotations; i++) {
            temp = puzzle[sliceIndex][0];
            puzzle[sliceIndex][0] = puzzle[sliceIndex][1];         
            puzzle[sliceIndex][1] = puzzle[sliceIndex][2];
            puzzle[sliceIndex][2] = temp; 
        }
    }

    // checks if there is a number in the thread already
    public static boolean findSolution() {
        boolean noSolution = false; 
        int sliceIndex = 0; 
        boolean backtrack = false, valid; 
        while(sliceIndex < 65) {
            if (sliceIndex < 0) {
                noSolution = true; 
                break; 
            }
            if (rotationCount[sliceIndex] <= 2){
                if (backtrack){
                    removeSlice(puzzle[sliceIndex][0]);
                    rotateSlices(puzzle[sliceIndex][0], 2);
                    rotationCount[sliceIndex] += 1; 
                    if (rotationCount[sliceIndex] == 3){
                        continue; 
                    }
                }
                valid = isValid(sliceIndex);
                if (valid) {
                    addSlice(puzzle[sliceIndex][0]);
                    backtrack = false; 
                    sliceIndex += 1; 
                    continue; 
                }
                else if (!valid && backtrack) {
                    backtrack = false; 
                }
                else if (!backtrack) {
                    rotateSlices(puzzle[sliceIndex][0], 1);
                    rotationCount[puzzle[sliceIndex][0]] += 1; 
                    continue; 
                }
            }
            else if(rotationCount[sliceIndex] == 3) {
                rotationCount[sliceIndex] = 0; 
                backtrack = true; 
                sliceIndex -= 1; 
                continue; 
            }
        }    
        if (!noSolution) {
            return true; 
        }
        else return false;
    }   

    public static void main(String[] args) {
        generatePuzzle();
        
        // print the generated puzzle
        printPuzzle();
        findSolution(); 
        // checking for the existence of the solution 
        // if (checkStack(puzzle)) {
        //     System.out.println("A solution was found.");
        // }
        // else {
        //     System.out.println("No solution was found for the above puzzle.");
        // }
    }

}