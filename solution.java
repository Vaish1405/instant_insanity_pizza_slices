import java.util.*; 
import java.io.*; 

public class solution {
    public static int count[] = new int[66];
    public static int puzzle[] = new int[65*3];
    public static int k = 1, output, i=1; 
    public static int threadACount[] = new int[66];
    public static int threadBCount[] = new int[66];
    public static int threadCCount[] = new int[66];

    public static void generatePuzzle() {
        while (i <= 65*3) {
            output = 1 + (int)(Math.floor(17 * Math.E * k) % 65);
            if (count[output] < 3) {
              puzzle[i-1] = output; 
                i++;
                count[output]++;
            }
            k++; 
        }
    }

    public static void printPuzzle() {
        for(int j = 1; j <= puzzle.length; j++) {
            System.out.printf("%d ", puzzle[j-1]);
            if (j % 3 == 0) {
                System.out.println();
            }
        }
    }

    // check if it's a valid slice 
    public static boolean isValid(int sliceIndex) {
        if (threadACount[sliceIndex] != 0 || threadBCount[sliceIndex + 1] != 0 || threadCCount[sliceIndex + 2] != 0) {
            return false; 
        }
        return true; 
    }

    public static void findSolution() {
        
    }

    // rotate the slices till a solution is found 
    public static void rotateSlices(int sliceIndex) {
        int tempCount = threadACount[sliceIndex];
        int tempNumber = threads[sliceIndex];

        threads[sliceIndex] = threads[sliceIndex + 1];
        threadsACount[sliceIndex] = threadBCount[sliceIndex + 1];
        threads[sliceIndex+1] = threads[sliceIndex+2];
        threadsBCount[sliceIndex+1] = threadBCount[sliceIndex+2];
    }

    public static void main(String[] args) {
        generatePuzzle();
        printPuzzle();
    }

}