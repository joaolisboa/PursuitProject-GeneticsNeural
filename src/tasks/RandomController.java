/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tasks;

import ga.GeneticAlgorithm;
import pursuitDomain.Predator;

/**
 *
 * @author J
 */
public class RandomController extends TaskMode {
    
    @Override
    public void run(Predator predator) {
        int[] output = new int[2];
        output[0] = GeneticAlgorithm.random.nextInt(2);
        output[1] = GeneticAlgorithm.random.nextInt(2);
        predator.setOutput(output);
    }

    @Override
    public String toString(){
        return "Random Controller - Tarefa 1";
    } 

}
