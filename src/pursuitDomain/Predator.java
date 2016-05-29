package pursuitDomain;

import ga.GeneticAlgorithm;
import java.awt.Color;

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
        int i = 0;
        for (Predator p : environment.getPredators()) {
            int width = p.cell.getColumn() - environment.getPrey().cell.getColumn();
            int height = p.cell.getLine() - environment.getPrey().cell.getLine();

            int heightT = environment.getSize() - height;
            int widthT = environment.getSize() - width;

            if (Math.abs(widthT) < Math.abs(width)) {
                inputs[i] = (width > 0) ? -widthT : widthT;
            }
            
            if (Math.abs(heightT) < Math.abs(height)) {
                inputs[i + 1] = (height > 0) ? -heightT : height;
            }
            i+=2;
        }

    }

    private Action decide() {
        forwardPropagation();

        //Here we are assuming that the output has two elements, only
        if (output[0] == 0 && output[1] == 0) {
            return Action.NORTH;
        } else if (output[0] == 0 && output[1] == 1) {
            return Action.SOUTH;
        } else if (output[0] == 1 && output[1] == 0) {
            return Action.WEST;
        }
        return Action.EAST;
    }

    private void execute(Action action, Environment environment) {
        Cell nextCell;
        if (action == Action.NORTH) {
            nextCell = environment.getNorthCell(cell);
        } else if (action == Action.SOUTH) {
            nextCell = environment.getSouthCell(cell);
        } else if (action == Action.WEST) {
            nextCell = environment.getWestCell(cell);
        } else {
            nextCell = environment.getEastCell(cell);
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
        i = inputLayerSize;
        for (int x = 0; x < hiddenLayerSize + 1; x++) {
            for (int y = 0; y < outputLayerSize; y++) {
                w2[x][y] = weights[i];
                i++;
            }
        }
    }
    
    private double sigmoid(double x){
        return 1 / (1 + Math.exp(-x));
    }
    
    public void setOutput(int[] output){
        System.arraycopy(output, 0, this.output, 0, this.outputLayerSize);
    }

    /**
     * Computes the output of the network for the inputs saved in the class
     * vector "inputs".
     *
     */
    private void forwardPropagation() {
        /*double sum;
        //First layer outputs computation
        for (int i = 0; i < hiddenLayerSize; i++) {
            sum = 0;
            for (int j = 0; j < inputLayerSize; j++) {
                sum += inputs[j] * w1[j][i];
            }

            hiddenLayerOutput[i] = sigmoid(sum);
        }
        //the bias entry is already set to -1 in the constructor
        
        //output layer outputs computation
        for (int i = 0; i < outputLayerSize; i++) {
            sum = 0;
            for (int j = 0; j < hiddenLayerSize + 1; j++) {
                sum += hiddenLayerOutput[j] * w2[j][i];
            }

            output[i] = (int) sigmoid(sum);
        }*/
        GeneticAlgorithm.taskMode.run(this);
    }
}
