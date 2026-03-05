package com.zipcodeconway;




public class ConwayGameOfLife {
    private int[][] currentWorld;
    private int dimension;
    private SimpleWindow window;

    public ConwayGameOfLife(Integer dimension) {
        this.dimension = dimension;
        this.currentWorld = createRandomStart(dimension);
        this.window = new SimpleWindow(dimension);
    }

    public ConwayGameOfLife(Integer dimension, int[][] startmatrix) {
        this.dimension = dimension;
        this.currentWorld = startmatrix;
        this.window = new SimpleWindow(dimension);
    }

    public static void main(String[] args) {
        ConwayGameOfLife sim = new ConwayGameOfLife(100);
        int[][] endingWorld = sim.simulate(500);

        
    }
    
    

    // Contains the logic for the starting scenario.
    // Which cells are alive or dead in generation 0.
    // allocates and returns the starting matrix of size 'dimension'
    private int[][] createRandomStart(Integer dimension) {
        if (dimension < 1) {
            return new int[1][1];
        }
        int[][] startMatrix = new int[dimension][dimension];
        for (int row = 0; row < dimension; row++) {
            for (int col = 0; col < dimension; col++) {
                startMatrix[row][col] = (int) (Math.random() * 2);
            }
        }
        return startMatrix;
    }

    public int[][] simulate(Integer maxGenerations) {
        int[][] nextWorld = new int[dimension][dimension];

        for (int gen = 0; gen < maxGenerations; gen++) {
            for (int row = 0; row < dimension; row++) {
                for (int col = 0; col < dimension; col++) {
                    nextWorld[row][col] = isAlive(row, col, currentWorld);
                }
            }
            window.display(currentWorld, gen);
            window.sleep(100);
            copyAndZeroOut(nextWorld, currentWorld);
        }
        return currentWorld;
    }

    // copy the values of 'next' matrix to 'current' matrix,
    // and then zero out the contents of 'next' matrix
    public void copyAndZeroOut(int [][] next, int[][] current) {
        for (int row = 0; row < dimension; row++) {
            for (int col = 0; col < dimension; col++) {
                current[row][col] = next[row][col];
                next[row][col] = 0;
            }
        }
    }

    // Calculate if an individual cell should be alive in the next generation.
    // Based on the game logic:
	/*
		Any live cell with fewer than two live neighbours dies, as if by needs caused by underpopulation.
		Any live cell with more than three live neighbours dies, as if by overcrowding.
		Any live cell with two or three live neighbours lives, unchanged, to the next generation.
		Any dead cell with exactly three live neighbours cells will come to life.
	*/
    private int isAlive(int row, int col, int[][] world) {
        int liveNeighbors = countLiveNeighbors(row, col, world);
        int currentCell = world[row][col];

        if (currentCell == 1) {
            if (liveNeighbors < 2 || liveNeighbors > 3) {
                return 0; // Cell dies
            } else {
                return 1; // Cell lives
            }
        } else {
            if (liveNeighbors == 3) {
                return 1; // Cell becomes alive
            } else {
                return 0; // Cell remains dead
            }
        }
    }

    private int countLiveNeighbors(int row, int col, int[][] world) {
        int liveCount = 0;
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (i == 0 && j == 0) continue; // Skip the cell itself
                int neighborRow = row + i;
                int neighborCol = col + j;
                if (neighborRow >= 0 && neighborRow < dimension && neighborCol >= 0 && neighborCol < dimension) {
                    liveCount += world[neighborRow][neighborCol];
                }
            }
        }
        return liveCount;
    }

    private void display(int[][] world, int generation) {
        System.out.println("Generation: " + generation);
        for (int row = 0; row < dimension; row++) {
            for (int col = 0; col < dimension; col++) {
                System.out.print(world[row][col] == 1 ? "O" : ".");
            }
            System.out.println();
        }
        System.out.println();
    }
}
