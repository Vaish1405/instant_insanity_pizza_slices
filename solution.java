/*
 * Description: This problem tries to solve a version of instant insanity situation for pizza slices. Program requirements and data is given in README.md file. 
 * Input: Manually generated with the given formula
 * Output: Should show the combination of pizza slices and their colors to ensure that it solves the problem. It should also show if there is any rotation of the slices involved. 
 * Main goal of the program: Try to minimize the backtracking in the problem while also ensuring it is both time and memory efficient. 
*/
import java.util.*; 
import java.io.*; 
import java.time.*; 

public class solution {
    public static int count[] = new int[66]; // Holds the number of times a color is used in the puzzle
    public static int puzzle[][] = new int[65][3]; // holds the actual color numbers for the puzzle 
    public static int k = 1, backtrackIndex = 0;
    public static int threadCounts[][] = new int[66][3]; // holds the number of times a color has appeared in a thread 
    public static int rotationCount[] = new int[66]; 

    public static int solution[][] = new int[65][3];
    public static int backtrack[][] = new int[10][3];
    public static int pizzaIndex = 0, solIndex = 0; 
    public static int valid[] = new int[65]; // storing the slices that were already added into the solution array

    // generates the initial puzzle. No color should show up more than 3 times in the puzzle.
    public static void generatePuzzle() {
        int output;
        for(int i = 0; i < 65;) {
            for(int j = 0; j < 3;) {
                output = 1 + (int)(Math.floor(17 * Math.E * k) % 65);
                if (count[output] < 3) {
                    puzzle[i][j] = output; 
                    count[output]++;
                    j++;
                }
                k++; 
            }
            i++; 
        }
    }

    // prints the solution puzzle 
    public static void printPuzzle() {
        for(int i = 0; i < 65; i++) {
            for (int j = 0; j < 3; j++) {
                System.out.printf("%3d ", puzzle[i][j]); 
            }
            System.out.println();
        }
    }

    // check if it's a valid slice 
    public static boolean isValid(int[] slice) {
        int color; 
        for (int i = 0; i < 3; i++) {
            color = slice[i];
            if(threadCounts[color][i] != 0) {
                return false; 
            }
        }
        return true; 
    }

    // adding the slice to the solution -> increasing the threadCounts 
    public static void addSlice(int puzzleIndex, int sliceIndex) {
        int color; 
        for (int i = 0; i < 3; i++) {
            color = puzzle[puzzleIndex][i];
            threadCounts[color][i] = 1; 
            solution[sliceIndex][i] = color; 
        }
    }

    // Removing the slice from the solution -> decreasing the threadCounts
    public static void removeSlice(int sliceIndex) {
        int color; 
        for(int i = 0; i < 3; i++) {
            color = puzzle[sliceIndex][i];
            threadCounts[color][i] = 0; 
        }
    }

    public static boolean rotateSlices(int sliceIndex) {
        // if (isValid(solution[sliceIndex])) {return true;}
        int temp[] = new int[3], x1 = 1, x2 = 2, x3 = 0; 
        for (int a = rotationCount[sliceIndex]; a < 2; a++) {
            temp[0] = puzzle[sliceIndex][x1];
            temp[1] = puzzle[sliceIndex][x2];
            temp[2] = puzzle[sliceIndex][x3];
            // System.out.printf("%3d %3d %3d\n", temp[0], temp[1], temp[2]);
            x1 = 2; x2 = 0; x3 = 1; 
            // temp = puzzle[sliceIndex][0];

            // threadCounts[puzzle[sliceIndex][0]][0] = 0;
            // puzzle[sliceIndex][0] = puzzle[sliceIndex][1];

            // threadCounts[puzzle[sliceIndex][1]][1] = 0;
            // puzzle[sliceIndex][1] = puzzle[sliceIndex][2];

            // threadCounts[puzzle[sliceIndex][2]][2] = 0;
            // puzzle[sliceIndex][2] = temp;
            // rotationCount[sliceIndex] = a+1; 
            if (isValid(temp)) {
                rotationCount[sliceIndex]++;
                threadCounts[puzzle[sliceIndex][0]][0] = 1; 
                threadCounts[puzzle[sliceIndex][1]][1] = 1; 
                threadCounts[puzzle[sliceIndex][2]][2] = 1;
                puzzle[sliceIndex][0] = temp[0];
                puzzle[sliceIndex][1] = temp[1];
                puzzle[sliceIndex][2] = temp[2];
                return true; 
            }          
        }
        return false; 
    }    

    public static int backtrack(int index) {
        int backtrack = 0; 
 // checking conditions for the previous slices 
        while (index > 0) {
            index--;
            // if (backtrack) {
                System.out.println(rotationCount[index]);
                if (rotationCount[index] < 2) {
                    if(rotateSlices(solIndex)) {
                        // rotationCount[index] += 1;
                        return backtrack;
                    }
                }
                if (rotationCount[index] == 2) {
                    removeSlice(solIndex);  
                    rotationCount[index] = 0;
                    backtrack--;
                }
                backtrack++;
                solIndex--;

            // }

        } 
        return backtrack;
    }    

    public static void addSliceBacktrack(int backtrackIndex, int index) {
        for (int i = 0; i < 3; i++) {
            backtrack[backtrackIndex][i] = puzzle[index][i];
        }
    }
    // this method is used to minimize the backtracking 
    // It puts all the slices that are right without any rotation or anything first in the solution array 
    // Once everything that doesn't need to be rotated is done, we can look at the other slices and rotate and backtrack those 
    public static void findSolution() {
        while(pizzaIndex < 65) {
            if((threadCounts[puzzle[pizzaIndex][0]][0] == 0) && (threadCounts[puzzle[pizzaIndex][1]][1] == 0) && threadCounts[puzzle[pizzaIndex][2]][2] == 0) {
                addSlice(pizzaIndex, solIndex);
                System.out.printf("%3d %3d %3d, rotation: %d\n", puzzle[pizzaIndex][0], puzzle[pizzaIndex][1], puzzle[pizzaIndex][2], rotationCount[pizzaIndex]);
                solIndex++;
                valid[pizzaIndex] = 1; 
            }
            pizzaIndex++;
        }
        pizzaIndex = solIndex; // writing till where the back tracking or rotating is not done
        for (int index = 0; index < 65; index++) {
            if(valid[index] == 0) {
                boolean a = rotateSlices(index);
                if(a) {
                    addSlice(index, solIndex);
                    System.out.printf("%3d %3d %3d, rotation: %d\n", puzzle[index][0], puzzle[index][1], puzzle[index][2], rotationCount[index]);
                    solIndex++;
                }
                else {
                    // while (!check) {
                    //     backtrack(index, solIndex);
                    //     check = true; 
                    // }
                    // System.out.println("backtracking");
                    // addSliceBacktrack(backtrackIndex, index);
                    // backtrackIndex++;
                    
                    int temp = backtrack(index);
                    index = index - temp - 1;
                    // System.out.printf("%3d %3d %3d backtracked\n", puzzle[index][0], puzzle[index][1], puzzle[index][2]);
                }
            }
        }

        while (solIndex < 65) {
            System.out.println("Slice index: " + solIndex);
            backtrack(solIndex);
        }
    }

    public static void printBacktrack() {
        int i = 0, j = 0;
        while (backtrack[i][j] != 0) {
            System.out.printf("%3d ", backtrack[i][j]);
            j++;
            if (j % 3 == 0) {
                i++;
                j = 0;
                System.out.println(); 
            } 
        }
    }

    public static void main(String[] args) {

        long startTime = System.nanoTime();

        generatePuzzle();
        
        // print the generated puzzle
        findSolution(); 

        long endTime = System.nanoTime();

        // Calculate the elapsed time in milliseconds
        long elapsedTime = endTime - startTime;

        System.out.printf("%d nanoseconds\n", elapsedTime);
        // printPuzzle();

        // System.out.println("backtrack array");
        // printBacktrack(); 
    }
}