import java.util.Arrays;

public class chatGpt {

    public static boolean solveStack(int[][] cubes, int[][] visited, int solutionNum) {
        int i = 0;
        int[] multiplicity = new int[30];
        boolean halfSolFlag = false;
        int halfSolCounter = 0;

        while (-1 < i && i < cubes.length) {
            boolean movingForward = true;
            boolean nextHalfSolution = false;

            for (int j = 0; j < 3; j++) {
                // This stops the dfs at the third thread
                if (i == 0 && solutionNum == 1 && j == 2) {
                    halfSolFlag = false;
                    movingForward = false;
                    break;
                }

                if (!halfSolFlag && i == cubes.length - 1 && visited[i][j] == solutionNum) {
                    if (j < 2) {
                        visited[i][j] = 0;
                        multiplicity[cubes[i][j] - 1]--;
                        multiplicity[cubes[i][j] - 1]--;
                        j++;
                    } else {
                        visited[i][j] = 0;
                        multiplicity[cubes[i][j] - 1]--;
                        multiplicity[cubes[i][j] - 1]--;
                        nextHalfSolution = true;
                    }
                }

                if (nextHalfSolution) {
                    break;
                }

                if (visited[i][j] == 0 && multiplicity[cubes[i][j][0] - 1] + 1 < 3 &&
                        multiplicity[cubes[i][j][1] - 1] + 1 < 3) {
                    visited[i][j] = solutionNum;
                    multiplicity[cubes[i][j][0] - 1]++;
                    multiplicity[cubes[i][j][1] - 1]++;
                    movingForward = false;
                    i++;
                    break;
                }
            }

            if (movingForward) {
                i--;
                boolean doneBacktracking = false;

                while (!doneBacktracking && i >= 0) {
                    for (int k = 0; k < 3; k++) {
                        if (visited[i][k] == solutionNum) {
                            visited[i][k] = 0;
                            multiplicity[cubes[i][k][0] - 1]--;
                            multiplicity[cubes[i][k][1] - 1]--;
                            k++;

                            while (k < 3) {
                                if (visited[i][k] == 0 &&
                                        (multiplicity[cubes[i][k][0] - 1] + 1) < 3 &&
                                        (multiplicity[cubes[i][k][1] - 1] + 1) < 3) {
                                    visited[i][k] = solutionNum;
                                    multiplicity[cubes[i][k][0] - 1]++;
                                    multiplicity[cubes[i][k][1] - 1]++;
                                    doneBacktracking = true;
                                    i++;
                                    break;
                                }
                                k++;
                            }
                            break;
                        }
                    }

                    if (!doneBacktracking) {
                        i--;
                    }
                }
            }

            if (i == cubes.length) {
                halfSolFlag = true;
            }

            if (i == cubes.length && solutionNum != 2) {
                halfSolCounter++;
                halfSolFlag = solveStack(cubes, visited, 2);

                if (!halfSolFlag) {
                    i--;
                }
            }
        }

        if (i == -1) {
            halfSolFlag = false;
        }

        return halfSolFlag;
    }

    public static int count[] = new int[66];
    public static int puzzle[][] = new int[65][3];
    public static int k=1; 

    public static void generatePuzzle() {
        int output;
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

    public static void main(String[] args) {
        // Example usage:
        int[][][] midtermPuzzle = {
                {{1, 5}, {1, 2}, {1, 1}},
                {{1, 2}, {3, 4}, {5, 5}},
                // ... (more cubes)
        };

        int numCubes = midtermPuzzle.length;
        int[][] visited = new int[numCubes][3];
        for (int[] row : visited) {
            Arrays.fill(row, 0);
        }

        boolean solution = solveStack(puzzle, visited, 1);

        if (solution) {
            System.out.println("Solution found!");
        } else {
            System.out.println("No solution found.");
        }
    }
}
