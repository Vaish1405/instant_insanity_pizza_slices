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

    }

}