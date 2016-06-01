/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tasks;

import java.util.Arrays;
import pursuitDomain.Predator;

/**
 *
 * @author J
 */
public class HomogeneousController extends TaskMode {

    @Override
    public int[] run(Predator p) {
        double sum;
        double[][] w1 = p.getW1();
        double[][] w2 = p.getW2();
        int[] inputs = p.getInputs();
        double[] hiddenLayerOutput = p.getHiddenLayerOutput();
        int[] output = p.getOutput();
        
        //System.out.println("w1: " + Arrays.toString(w1[1]));
        //System.out.println("w2: " + Arrays.toString(w2[1]));
        
        //First layer outputs computation
        for (int i = 0; i < p.getHiddenLayerSize(); i++) {
            sum = 0;
            for (int j = 0; j < p.getInputLayerSize(); j++) {
                sum += inputs[j] * w1[j][i];
            }
            hiddenLayerOutput[i] = p.sigmoid(sum);
        }

        //output layer outputs computation
        for (int i = 0; i < p.getOutputLayerSize(); i++) {
            sum = 0;
            for (int j = 0; j < p.getHiddenLayerSize() + 1; j++) {
                sum += hiddenLayerOutput[j] * w2[j][i];
            }
            if(p.sigmoid(sum) > 0.5){
                output[i] = 1;
            }else{
                output[i] = 0;
            }
        }
        return output;
    }

    @Override
    public String toString() {
        return "Homogeneous Controller - Tarefa 3";
    }

}
