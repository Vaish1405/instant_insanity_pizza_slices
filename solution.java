import java.util.*; 
import java.io.*; 

public class Project482 {
    // public static void flip(int index) {
    //     System.out.print("flipped\n");
    // }

    public static void main(String[] args) {
        int threads[] = new int[65*3];
        int threadACount[] = new int[66];
        int threadBCount[] = new int[66];
        int threadCCount[] = new int[66];
        int count[] = new int[66];
        int k = 1, output, i=1; 
        while (i <= 65*3) {
            output = 1 + (int)(Math.floor(17 * Math.E * k) % 65);
            if (count[output] < 3) {
                System.out.printf("%3d ", output);
                if (i % 3 == 0) {
                    System.out.println();
                }
                i++;
                count[output]++;
                // if(count[output]==2) {System.out.printf("%d ", output);}
            }
            k++; 
        }
        // for(int j = 1; j <= threads.length; j++) {
        //     System.out.printf("%d ", threads[j-1]);
        //     if (j % 3 == 0) {
        //         System.out.println();
        //     }
        // }


        // for (i = 0; i <= 50; i+=3) {
        //     if(threadACount[threads[i]] == 0) {
        //         threadACount[threads[i]]++;
        //         System.out.printf("%3d ", threads[i]);
        //     }
        //     else{
        //         System.out.printf("%3d ", threads[i]);
        //         // flip(i);
        //     }
        //     if(threadBCount[threads[i+1]] == 0) {
        //         threadBCount[threads[i+1]]++;
        //         System.out.printf("%3d ", threads[i+1]);
        //     }
        //     else{
        //         System.out.printf("%3d ", threads[i+1]);
        //         // flip(i);
        //     }
        //     if(threadCCount[threads[i+2]] == 0) {
        //         threadCCount[threads[i+2]]++;
        //         System.out.printf("%3d ", threads[i+2]);
        //     }
        //     else{
        //         System.out.printf("%3d ", threads[i+2]);
        //         // flip(i);
        //     }
        //     if(i%3==0) {System.out.println();}
        // }
    }

}