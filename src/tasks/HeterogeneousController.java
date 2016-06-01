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
public class HeterogeneousController extends TaskMode{
    
    @Override
    public int[] run(Predator predator) {
        return null;
    }
    
    @Override
    public int getGenomeSize(double numPredators, int predatorsNumInputs, int predatorsNumHiddenUnits){
        return (int)((numPredators + 1) * 2) + ((predatorsNumInputs * 4) + 1) * predatorsNumHiddenUnits;
    }
    
    @Override
    public String toString(){
        return "Heterogeneous Controller - Tarefa 4";
    } 
    
}
