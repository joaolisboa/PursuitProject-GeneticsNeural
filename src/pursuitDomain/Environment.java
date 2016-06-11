package pursuitDomain;

import ga.GeneticAlgorithm;
import java.awt.Color;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Environment {

    public static Random random;
    private static Cell[][] grid;
    private final List<Predator> predators;
    private final Prey prey;
    private final int maxIterations;
    private int numIterations; // num of iterations in the simulation
    private int totalPredatorsDistance;

    public Environment(
            int size,
            int maxIterations,
            double probPreyRests,
            int numPredators,
            int predatorsNumInputs,
            int predatorsNumHiddenLayers,
            int predatorsNumOutputs) {

        this.maxIterations = maxIterations;
        numIterations = 0;
        totalPredatorsDistance = 0;

        grid = new Cell[size][size];
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid.length; j++) {
                grid[i][j] = new Cell(i, j);
            }
        }

        //THE PREY AND THE PREDATORS ARE CREATED BUT THEY ARE NOT PLACED IN ANY CELL.
        //THEY ARE PLACED IN AN INITIAL CELL IN THE BEGINNING OF EACH SIMULATION
        //(SEE METHOD initializeAgentsPositions).
        prey = new Prey(null, probPreyRests);

        predators = new LinkedList<>();
        for (int i = 0; i < numPredators; i++) {
            predators.add(new Predator(null, predatorsNumInputs, predatorsNumHiddenLayers, predatorsNumOutputs));
        }

        this.random = new Random();
    }

    //THIS METHOD SHOULD BE CALLED IN THE METHOD computeFitness BEFORE
    //ALL THE SIMULATIONS START.
    public void setPredatorsWeights(double[] weights) {
        GeneticAlgorithm.taskMode.setWeights(predators, weights);
        /*for (Predator predator : predators) {
            predator.setWeights(weights);
        }*/
    }

    //THIS METHOD SHOULD BE CALLED RIGHT BEFORE EACH CALL TO METHOD simulate (SEE BELOW).
    //THAT IS, IT MUST BE CALLED RIGHT BEFORE EACH SIMULATION.
    public void initializeAgentsPositions(int seed) {
        //THE NEXT LINE MEANS THAT ALL INDIVIDUALS WILL BE EVALUATED WITH THE SAME
        //ENVIRONEMNT STARTING POSITIONS.
        random.setSeed(seed);
        //reset cells
        prey.setCell(null);
        for (Predator predator : predators) {
            predator.setCell(null);
        }

        prey.setCell(getCell(random.nextInt(grid.length), random.nextInt(grid.length)));
        for (Predator predator : predators) {
            do {
                Cell cell = getCell(random.nextInt(grid.length), random.nextInt(grid.length));
                if (!cell.hasAgent()) {
                    predator.setCell(cell);
                }
            } while (predator.getCell() == null);
            predator.giveRole(predators, seed);
        }
        
    }

    //MAKES A SIMULATION OF THE ENVIRONMENT. THE AGENTS START IN THE POSITIONS
    //WHERE THEY WHERE PLACED IN METHOD initializeAgentsPositions.
    public boolean simulate() {
        totalPredatorsDistance = 0;
        for (numIterations = 0; numIterations < maxIterations; numIterations++) {
            prey.act(this);
            for (Predator predator : predators) {
                predator.act(this);
            }
            computePredatorsPreyDistanceSum();
            fireUpdatedEnvironment();
            if (isPreyTrapped()) {
                return true;
            }
        }
        return false;
    }
    
    public boolean isPreyTrapped() {
        return getFreeSurroundingCells(prey.cell).isEmpty();
    }

    public int computePredatorsPreyDistanceSum() {
        int distance = getPredatorsPreyDistanceSum();
        totalPredatorsDistance += distance;
        return distance;
    }

    //COMPUTES THE SUM OF THE (SMALLEST) DISTANCES OF ALL THE PREDATORS TO THE PREY.
    //IT TAKES INTO ACCOUNT THAT THE ENVIRONMENT IS TOROIDAL.
    public int getPredatorsPreyDistanceSum() {
        int distance = 0;
        for (Predator pre : predators) {
            int width = pre.cell.getColumn() - prey.cell.getColumn();
            int height = pre.cell.getLine() - prey.cell.getLine();

            int heightT = grid[0].length - Math.abs(height);
            if (heightT == grid[0].length) {
                heightT = 0;
            }
            int widthT = grid.length - Math.abs(width);
            if (widthT == grid.length) {
                widthT = 0;
            }
            
            width = Math.abs(width);
            height = Math.abs(height);
            widthT = Math.abs(widthT);
            heightT = Math.abs(heightT);

            distance += height < heightT ? height : heightT;
            distance += width < widthT ? width : widthT;
        }
        return distance;
    }

    public int getSize() {
        return grid.length;
    }

    public Prey getPrey() {
        return prey;
    }

    public List<Predator> getPredators() {
        return predators;
    }

    public final Cell getCell(int line, int column) {
        return grid[line][column];
    }
    
    public static boolean cellHasPredator(int[] newCell){
        return grid[newCell[0]][newCell[1]].hasPredator();
    }

    public static boolean cellHasPrey(int[] newCell){
        return grid[newCell[0]][newCell[1]].hasPredator();
    }
    
    //THIS METHOD *MAY* BE USED BY THE PREY IF YOU WANT TO SELECT THE RANDOM
    //PREY MOVEMENT JUST BETWEEN FREE SURROUNDING CELLS.
    public List<Cell> getFreeSurroundingCells(Cell cell) {
        List<Cell> freeCells = new LinkedList<>();
        if (!getNorthCell(cell).hasAgent()) {
            freeCells.add(getNorthCell(cell));
        }
        if (!getSouthCell(cell).hasAgent()) {
            freeCells.add(getSouthCell(cell));
        }
        if (!getEastCell(cell).hasAgent()) {
            freeCells.add(getEastCell(cell));
        }
        if (!getWestCell(cell).hasAgent()) {
            freeCells.add(getWestCell(cell));
        }
        return freeCells;
    }

    public int getNumIterations() {
        return numIterations;
    }

    public void setNumIterations(int i) {
        this.numIterations = 0;
    }

    public int getTotalPredatorsDistance() {
        return totalPredatorsDistance;
    }

    public int getMaxIterations() {
        return maxIterations;
    }

    public Color getCellColor(int line, int column) {
        return grid[line][column].getColor();
    }

    public Cell getNorthCell(Cell cell) {
        return cell.getLine() > 0 ? grid[cell.getLine() - 1][cell.getColumn()] : grid[grid.length - 1][cell.getColumn()];
    }

    public Cell getSouthCell(Cell cell) {
        return cell.getLine() < grid.length - 1 ? grid[cell.getLine() + 1][cell.getColumn()] : grid[0][cell.getColumn()];
    }

    public Cell getWestCell(Cell cell) {
        return cell.getColumn() > 0 ? grid[cell.getLine()][cell.getColumn() - 1] : grid[cell.getLine()][grid.length - 1];
    }

    public Cell getEastCell(Cell cell) {
        return cell.getColumn() < grid.length - 1 ? grid[cell.getLine()][cell.getColumn() + 1] : grid[cell.getLine()][0];
    }

    //listeners
    private final ArrayList<EnvironmentListener> listeners = new ArrayList<>();

    public synchronized void addEnvironmentListener(EnvironmentListener l) {
        if (!listeners.contains(l)) {
            listeners.add(l);
        }
    }

    public synchronized void removeEnvironmentListener(EnvironmentListener l) {
        listeners.remove(l);
    }

    public void fireUpdatedEnvironment() {
        for (EnvironmentListener listener : listeners) {
            listener.environmentUpdated();
        }
    }
}
