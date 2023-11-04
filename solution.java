import java.util.*; 
import java.io.*; 

public class solution {
    public static int count[] = new int[66];
    public static int puzzle[][] = new int[65][3];
    public static int k = 1, output, i=0; 
    public static int threadACount[] = new int[66];
    public static int threadBCount[] = new int[66];
    public static int threadCCount[] = new int[66];

    public static void generatePuzzle() {
        for(i = 0; i < 65; i++) {
            for (int j = 0; j < 3; j++) {
                output = 1 + (int) (Math.floor(17 * Math.E * k) % 65);
                if (count[output] < 3) {
                    puzzle[i][j] = output; 
                    count[output]++;
                }
                k++; 
            }
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
        
        return true; 
    }

    // checks if there is a number in the thread already
    public static void findSolution() {

    }

    public static void rotateSlices() {

    }

    public static void main(String[] args) {
        generatePuzzle();
        
        // print the generated puzzle
        printPuzzle();

        // checking for the existence of the solution 
        // if (checkStack(puzzle)) {
        //     System.out.println("A solution was found.");
        // }
        // else {
        //     System.out.println("No solution was found for the above puzzle.");
        // }
    }

}