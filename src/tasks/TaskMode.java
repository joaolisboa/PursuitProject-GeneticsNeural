/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tasks;

import pursuitDomain.Predator;

/**
 *
 * @author J
 */
public abstract class TaskMode {
    
    public abstract void run(Predator predator);
    
    public int getGenomeSize(double numPredators, int predatorsNumInputs, int predatorsNumHiddenUnits){
        return (int)((numPredators + 1) * 2) + (predatorsNumInputs + 1) * predatorsNumHiddenUnits;
    }
    
}
