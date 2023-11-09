import java.util.*; 
import java.io.*; 

public class solution {
    public static int count[] = new int[66]; // Holds the number of times a color is used in the puzzle
    public static int puzzle[][] = new int[65][3]; // holds the actual color numbers for the puzzle 
    public static int solution[][] = new int[65][3];
    public static int k = 1, output;
    public static int threadCounts[][] = new int[66][3]; // holds the number of times a color has appeared in a thread 
    public static int rotationCount[] = new int[65]; // The number of rotations to find the solution

    public static void generatePuzzle() {
        for(int i = 0; i < 65;) {
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
        for(int i = 0; i < 65; i++) {
            for (int j = 0; j < 3; j++) {
                System.out.printf("%3d ", solution[i][j]); 
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
    public static void addSlice(int sliceIndex) {
        int color; 
        for (int i = 0; i < 3; i++) {
            color = puzzle[sliceIndex][i];
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
        for (int a = 0; a < 2; a++) {
            temp = puzzle[sliceIndex][0];

            threadCounts[puzzle[sliceIndex][0]][0] = 0;
            puzzle[sliceIndex][0] = puzzle[sliceIndex][1];

            threadCounts[puzzle[sliceIndex][1]][1] = 0;
            puzzle[sliceIndex][1] = puzzle[sliceIndex][2];

            threadCounts[puzzle[sliceIndex][2]][2] = 0;
            puzzle[sliceIndex][2] = temp;

            if (isValid(sliceIndex)) {
                threadCounts[puzzle[sliceIndex][0]][0] = 1; 
                threadCounts[puzzle[sliceIndex][1]][1] = 1; 
                threadCounts[puzzle[sliceIndex][2]][2] = 1; 
                return true; 
            }          
        }
        return false; 
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
        // doesn't seem to be working - bogus code
            // if (rotationCount[sliceIndex] <= 2) {
            //     if (backtrack) {
            //         removeSlice(sliceIndex);
            //         rotateSlices(sliceIndex, 1);
            //         rotationCount[sliceIndex] += 1;
            //         if (rotationCount[sliceIndex] == 3) {
            //             continue;
            //         }
            //     }

            //     valid = isValid(sliceIndex);

            //     if (valid) {
            //         addSlice(sliceIndex);
            //         backtrack = false;
            //         sliceIndex += 1;
            //         continue;
            //     } else if (!valid && backtrack) {
            //         backtrack = true;
            //     } else if (!backtrack) {
            //         rotateSlices(sliceIndex, 1);
            //         rotationCount[sliceIndex] += 1;
            //         continue;
            //     }
            // } else if (rotationCount[sliceIndex] == 3) {
            //     rotationCount[sliceIndex] = 0;
            //     backtrack = true;
            //     sliceIndex -= 1;
            //     continue;
            // }
            if (isValid(puzzle[sliceIndex])) {
                addSlice(sliceIndex);
                System.out.printf("%d %d %d\n", solution[sliceIndex][0], solution[sliceIndex][1], solution[sliceIndex][2]);
                sliceIndex++;
            }
            else{
                if (rotateSlices(sliceIndex)) {
                    System.out.printf("%d %d %d, this was rotated succesfully\n", puzzle[sliceIndex][0], puzzle[sliceIndex][1], puzzle[sliceIndex][2]);
                }
                else{
                    System.out.printf("%d %d %d, would have to backtrack\n", puzzle[sliceIndex][0], puzzle[sliceIndex][1], puzzle[sliceIndex][2]);
                }
                sliceIndex++;
            }
            /* bogus code */
            // else if(rotationCount[sliceIndex] == 3) {
            //     rotationCount[sliceIndex] = 0; 
            //     backtrack = true; 
            //     sliceIndex -= 1; 
            //     continue; 
            // }
        }    
        if (!noSolution) {
            return true; 
        }
        else return false;
    }   

    public static void main(String[] args) {
        generatePuzzle();
        
        // print the generated puzzle
        findSolution(); 


        // // checking the threads 
        // for (int a = 0; a < 66; a++) {
        //     System.out.printf("%d ", threadCounts[a][0]);
        // }
        // System.out.println(); 
        // for (int a = 0; a < 66; a++) {
        //     System.out.printf("%d ", threadCounts[a][1]);
        // }
        // System.out.println(); 
        // for (int a = 0; a < 66; a++) {
        //     System.out.printf("%d ", threadCounts[a][2]);
        // }
        // System.out.println(); 

    }

}