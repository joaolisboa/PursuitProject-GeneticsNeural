package pursuitDomain;

import ga.GeneticAlgorithm;
import java.awt.Color;
import java.util.LinkedList;
import java.util.List;

public class Predator extends Agent {

    final private int inputLayerSize;
    final private int hiddenLayerSize;
    final private int outputLayerSize;

    /**
     * Network inputs array.
     */
    final private int[] inputs;
    /**
     * Hiddden layer weights.
     */
    final private double[][] w1;
    /**
     * Output layer weights.
     */
    final private double[][] w2;
    /**
     * Hidden layer activation values.
     */
    final private double[] hiddenLayerOutput;
    /**
     * Output layer activation values.
     */
    final private int[] output;
    
    public int[] preyCor = new int[2];
    public int[] previousPreyCor = new int[2];
    public int[] relCor = new int[4];
    public boolean preyIsAlmostCaught = false;

    public Predator(
            Cell cell,
            int inputLayerSize,
            int hiddenLayerSize,
            int outputLayerSize) {
        super(cell, Color.BLUE);
        this.inputLayerSize = inputLayerSize;
        this.hiddenLayerSize = hiddenLayerSize;
        this.outputLayerSize = outputLayerSize;
        inputs = new int[inputLayerSize];
        inputs[inputs.length - 1] = -1; //bias entry
        w1 = new double[inputLayerSize][hiddenLayerSize]; // the bias entry for the hidden layer neurons is already counted in inputLayerSize variable
        w2 = new double[hiddenLayerSize + 1][outputLayerSize]; // + 1 due to the bias entry for the output neurons
        hiddenLayerOutput = new double[hiddenLayerSize + 1];
        hiddenLayerOutput[hiddenLayerSize] = -1; // the bias entry for the output neurons
        output = new int[outputLayerSize];
        
    }

    @Override
    public void act(Environment environment) {
        buildPerception(environment);
        execute(decide(), environment);
    }

    //Predators' coordinates relative to the Prey
    private void buildPerception(Environment environment) {
        preyCor[0] = environment.getPrey().cell.getLine();
        preyCor[1] = environment.getPrey().cell.getColumn();
        
        previousPreyCor[0] = environment.getPrey().getPreviousCell().getLine();
        previousPreyCor[1] = environment.getPrey().getPreviousCell().getColumn();
        
        preyIsAlmostCaught = environment.getFreeSurroundingCells(environment.getPrey().getPreviousCell()).size() == 1;
        
        int i = 0;
        for (Predator p : environment.getPredators()) {
            
            int width = p.cell.getColumn() - environment.getPrey().cell.getColumn();
            int height = p.cell.getLine() - environment.getPrey().cell.getLine();

            int heightT = environment.getSize() - Math.abs(height);
            if(heightT == environment.getSize()) heightT = 0;
            int widthT = environment.getSize() - Math.abs(width);
            if(widthT == environment.getSize()) widthT = 0;
            
            width = Math.abs(width);
            height = Math.abs(height);
            widthT = Math.abs(widthT);
            heightT = Math.abs(heightT);

            inputs[i] = height < heightT ? height : heightT;
            inputs[i+1] = width < widthT ? width : widthT;
            
            if(p == this){
                relCor[0] = inputs[i];
                relCor[1] =inputs[i+1];
            }
            
            i+=2;
        }
    }

    private Action decide() {
        forwardPropagation();

        if (output[0] == 0 && output[1] == 0) {
            return Action.NORTH;
        } else if (output[0] == 0 && output[1] == 1) {
            return Action.SOUTH;
        } else if (output[0] == 1 && output[1] == 0) {
            return Action.WEST;
        } else if (output[0] == 1 && output[1] == 1){
            return Action.EAST;
        }
        return null;
    }

    private void execute(Action action, Environment environment) {
        Cell nextCell;
        if (action == Action.NORTH) {
            nextCell = environment.getNorthCell(cell);
        } else if (action == Action.SOUTH) {
            nextCell = environment.getSouthCell(cell);
        } else if (action == Action.WEST) {
            nextCell = environment.getWestCell(cell);
        } else if (action == Action.EAST){
            nextCell = environment.getEastCell(cell);
        }else{
            nextCell = environment.getCell(getCell().getColumn(), getCell().getLine());
        }

        if (!nextCell.hasAgent()) {
            setCell(nextCell);
        }
    }

    /**
     * Initializes the network's weights
     *
     * @param weights vector of weights coming from the individual.
     */
    public void setWeights(double[] weights) {
        int i = 0;
        for (int x = 0; x < inputLayerSize; x++) {
            for (int y = 0; y < hiddenLayerSize; y++) {
                w1[x][y] = weights[i];
                i++;
            }
        }
        for (int x = 0; x < hiddenLayerSize; x++) {
            for (int y = 0; y < outputLayerSize; y++) {
                w2[x][y] = weights[i];
                i++;
            }
        }
        for(int x = 0; x < outputLayerSize; x++){
            w2[hiddenLayerSize][x] = -1;
        }
    }
    
    public double sigmoid(double x){
        return 1 / (1 + Math.exp(-x));
    }

    /**
     * Computes the output of the network for the inputs saved in the class
     * vector "inputs".
     *
     */
    private void forwardPropagation() {
        int[] out = GeneticAlgorithm.taskMode.run(this);
        output[0] = out[0];
        output[1] = out[1];
    }
    
    public int getInputLayerSize(){
        return inputLayerSize;
    }

    public int getHiddenLayerSize() {
        return hiddenLayerSize;
    }

    public int getOutputLayerSize() {
        return outputLayerSize;
    }

    public int[] getInputs() {
        return inputs;
    }

    public double[][] getW1() {
        return w1;
    }

    public double[][] getW2() {
        return w2;
    }

    public double[] getHiddenLayerOutput() {
        return hiddenLayerOutput;
    }

    public int[] getOutput() {
        return output;
    }

}
