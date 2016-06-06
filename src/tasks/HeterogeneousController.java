/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tasks;

import java.util.List;
import pursuitDomain.Predator;

/**
 *
 * @author J
 */
public class HeterogeneousController extends TaskMode{
    
    @Override
    public int[] run(Predator p) {
        double sum;
        double[][] w1 = p.getW1();
        double[][] w2 = p.getW2();
        int[] inputs = p.getInputs();
        double[] hiddenLayerOutput = p.getHiddenLayerOutput();
        int[] output = p.getOutput();
        
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
    public int getGenomeSize(double numPredators, int predatorsNumInputs, int predatorsNumHiddenUnits){
        return ((int)((numPredators + 1) * 2) + ((predatorsNumInputs) + 1) * predatorsNumHiddenUnits) * 4;
    }
    
    @Override
    public String toString(){
        return "Heterogeneous Controller - Tarefa 4";
    } 
    
    @Override
    public void setWeights(List<Predator> predators, double[] weights) {
        int i = 0;
        int size = weights.length / predators.size();
        for(Predator pre: predators){
            double[] newW = new double[size];
            for(int x = 0; x < size; x++){
                newW[x] = weights[i];
                i++;
            }
            pre.setWeights(newW);
        }
    }
}
