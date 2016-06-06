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
public abstract class TaskMode {
    
    public abstract int[] run(Predator predator);
    
    public int getGenomeSize(double numPredators, int predatorsNumInputs, int predatorsNumHiddenUnits){
        return (int)((numPredators + 1) * 2) + (predatorsNumInputs + 1) * predatorsNumHiddenUnits;
    }

    public void setWeights(List<Predator> predators, double[] weights) {
        for(Predator pre: predators){
            pre.setWeights(weights);
        }
    }
}
