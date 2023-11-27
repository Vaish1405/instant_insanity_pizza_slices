/*
 * Description: This problem tries to solve a version of instant insanity situation for pizza slices. Program requirements and data is given in README.md file. 
 * Input: Manually generated with the given formula
 * Output: Should show the combination of pizza slices and their colors to ensure that it solves the problem. It should also show if there is any rotation of the slices involved. 
 * Main goal of the program: Try to minimize the backtracking in the problem while also ensuring it is both time and memory efficient. 
*/
import java.util.*; 
import java.io.*; 

public class solution {
    public static int count[] = new int[66]; // Holds the number of times a color is used in the puzzle
    public static int puzzle[][] = new int[65][3]; // holds the actual color numbers for the puzzle 
    public static int k = 1;
    public static int threadCounts[][] = new int[66][3]; // holds the number of times a color has appeared in a thread 
    public static int rotationCount[] = new int[66]; 

    public static int solution[][] = new int[65][3];
    public static int pizzaIndex = 0, solIndex = 0; 
    public static int valid[] = new int[65];

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
        int temp; 
        for (int a = rotationCount[sliceIndex]; a <= 2; a++) {
            temp = puzzle[sliceIndex][0];

            threadCounts[puzzle[sliceIndex][0]][0] = 0;
            puzzle[sliceIndex][0] = puzzle[sliceIndex][1];

            threadCounts[puzzle[sliceIndex][1]][1] = 0;
            puzzle[sliceIndex][1] = puzzle[sliceIndex][2];

            threadCounts[puzzle[sliceIndex][2]][2] = 0;
            puzzle[sliceIndex][2] = temp;
            rotationCount[sliceIndex] = a+1; 
            if (isValid(puzzle[sliceIndex])) {
                threadCounts[puzzle[sliceIndex][0]][0] = 1; 
                threadCounts[puzzle[sliceIndex][1]][1] = 1; 
                threadCounts[puzzle[sliceIndex][2]][2] = 1; 
                return true; 
            }          
        }
        return false; 
    }    

    public static boolean rotateSlices(int[] slice) {
        int temp = slice[0];
        threadCounts[slice[0]][0] = 0; 
        slice[0] = slice[1];
        threadCounts[slice[1]][1] = 0; 
        slice[1] = slice[2];
        threadCounts[slice[2]][2] = 0; 
        slice[2] = temp; 
        if (isValid(slice)) {
            threadCounts[slice[0]][0] = 1; 
            threadCounts[slice[1]][1] = 1; 
            threadCounts[slice[2]][2] = 1; 
            return true; 
        }   
        return true; 
    }

    // backtracking to the last index where the color was seen to rotate and see if it's valid 
    public static void backtrack(int sliceIndex, int solIndex) {
        List<Integer> curSlice; 
        for (int i = solIndex - 1; i >= 0; i--) {
            // if (solution[sliceIndex] == solution[i]) {
            //     System.out.print("Equal");
            // }
            // if (rotationCount[i] < 2) {

            // }
            // System.out.printf("Find: %3d %3d %3d\n", puzzle[sliceIndex][0], puzzle[sliceIndex][1], puzzle[sliceIndex][2]); -> testing the given index
            curSlice = Arrays.asList(solution[i][0], solution[i][1], solution[i][2]);
            // int[] slice = {solution[i][0], solution[i][1], solution[i][2]};
            if (isValid(new int[]{puzzle[sliceIndex][0], puzzle[sliceIndex][1], puzzle[sliceIndex][2]})) {
                return;
            }
            // System.out.printf("%3d %3d %3d\n", solution[i][0], solution[i][1], solution[i][2]); -> testing the given index 
            if (curSlice.contains(puzzle[sliceIndex][0]) || curSlice.contains(puzzle[sliceIndex][1]) || curSlice.contains(puzzle[sliceIndex][2])) {
                if (rotateSlices(solution[i])) { // rotation of the previous slices doesn't cause problem to the solution
                    // System.out.print("this was rotated \n");
                    rotationCount[sliceIndex]++;
                }
                // if rotating is not possible.. i.e. rotating the slice disturbs other slices
                else {
                    System.out.println("Need to work");
                }
            }
        }
    }

    // this method is used to minimize the backtracking 
    // It puts all the slices that are right without any rotation or anything first in the solution array 
    // Once everything that doesn't need to be rotated is done, we can look at the other slices and rotate and backtrack those 
    public static void findSolution() {
        boolean check = false; 
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
            check = false; 
            if(valid[index] == 0) {
                boolean a = rotateSlices(index);
                if(a) {
                    addSlice(index, solIndex);
                    System.out.printf("%3d %3d %3d, rotation: %d\n", puzzle[index][0], puzzle[index][1], puzzle[index][2], rotationCount[index]);
                    solIndex++;
                }
                else {
                    while (!check) {
                        backtrack(index, solIndex);
                        System.out.printf("%3d\n", puzzle[index][0]);
                        if(isValid(puzzle[index])) {
                            check = true; 
                        }
                        index--;
                    }
                    
                    // System.out.printf("%3d %3d %3d backtracked\n", puzzle[index][0], puzzle[index][1], puzzle[index][2]);
                }
            }
        }
    }
    public static void main(String[] args) {
        generatePuzzle();
        // printPuzzle();
        
        // print the generated puzzle
        findSolution(); 

        // printPuzzle();
        // for(int i = 0; i < 66; i++) {
        //     System.out.printf("%d ", rotationCount[i]);
        // }
    }
}