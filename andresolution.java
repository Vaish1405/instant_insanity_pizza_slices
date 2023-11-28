import java.util.*;

public class andresolution {

    private static int k = 0;
    private static int[] count = new int[66]; // 0 to 65

    public static void main(String[] args) {
        int numCubes = 65;
        int numSides = 3;
        int numColors = 65;

        // Generate a random puzzle
        long startTime = System.nanoTime();
        List<Cube> puzzle = generateRandomPuzzle(numCubes, numSides, numColors);

        // Print the generated puzzle
        // System.out.println("Generated Puzzle:");
        // for (Cube cube : puzzle) {
        //     System.out.println(cube);
        // }

        // Solve the puzzle using BFS
        List<Cube> solution = solveInstantInsanityBFS(puzzle);
        long endTime = System.nanoTime();
        long timer = endTime - startTime;

        // Print the solution if found
        if (solution != null) {
            System.out.println("\nSolution Found in:" + timer);
            for (Cube cube : solution) {
                System.out.println(cube);
            }
        } else {
            System.out.println("\nNo solution found.");
        }
    }

    static List<Cube> solveInstantInsanityBFS(List<Cube> puzzle) {
        Queue<List<Cube>> queue = new LinkedList<>();
        Set<List<Cube>> visited = new HashSet<>();

        queue.offer(puzzle);
        visited.add(puzzle);

        while (!queue.isEmpty()) {
            List<Cube> currentConfiguration = queue.poll();

            if (isSolution(currentConfiguration)) {
                return currentConfiguration;
            }

            for (List<Cube> neighbor : generateNeighbors(currentConfiguration)) {
                if (!visited.contains(neighbor)) {
                    queue.offer(neighbor);
                    visited.add(neighbor);
                }
            }
        }

        return null; // No solution found
    }

    static boolean isSolution(List<Cube> configuration) {
        for (int i = 0; i < configuration.size() - 1; i++) {
            if (!configuration.get(i).canStackWith(configuration.get(i + 1))) {
                return false;
            }
        }
        return true;
    }

    static List<List<Cube>> generateNeighbors(List<Cube> configuration) {
        List<List<Cube>> neighbors = new ArrayList<>();
    
        for (int i = 0; i < configuration.size(); i++) {
            List<Cube> neighbor = new ArrayList<>(configuration);
    
            // Rotate the colors of the cube
            int[] rotatedColors = rotateColors(neighbor.get(i).getColorConfiguration());
            neighbor.get(i).setColorConfiguration(rotatedColors);
    
            neighbors.add(neighbor);
        }
    
        return neighbors;
    }
    
    static int[] rotateColors(int[] colors) {
        int[] rotatedColors = new int[colors.length];
        rotatedColors[0] = colors[colors.length - 1];
    
        for (int i = 1; i < colors.length; i++) {
            rotatedColors[i] = colors[i - 1];
        }
    
        return rotatedColors;
    }
    
    
    
    
    

    static List<Cube> generateRandomPuzzle(int numCubes, int numSides, int numColors) {
        List<Cube> puzzle = new ArrayList<>();
        k = 1;
        Arrays.fill(count, 0);

        for (int i = 0; i < numCubes; i++) {
            int[] cubeColors = new int[numSides];
            for (int j = 0; j < numSides; ) {
                int output = 1 + (int) (Math.floor(17 * Math.E * k) % 65);
                if (count[output] < 3) {
                    cubeColors[j] = output;
                    count[output]++;
                    j++;
                }
                k++;
            }
            puzzle.add(new Cube(cubeColors));
        }

        return puzzle;
    }
}

class Cube {
    private int[] colorConfiguration;
    private int currentColor;

    public Cube(int[] colorConfiguration) {
        this.colorConfiguration = colorConfiguration;
        this.currentColor = colorConfiguration[0];
    }

    public boolean canStackWith(Cube other) {
        return this.currentColor != other.currentColor;
    }

    public void setCurrentColor(int color) {
        this.currentColor = color;
    }

    public int[] getColorConfiguration() {
        return colorConfiguration;
    }

    public void setColorConfiguration(int[] colorConfiguration) {
    this.colorConfiguration = colorConfiguration;
    this.currentColor = colorConfiguration[0];
    }


    public int getCurrentColor() {
        return currentColor;
    }

    @Override
    public String toString() {
        return Arrays.toString(colorConfiguration) + " (Current Color: " + currentColor + ")";
    }
}