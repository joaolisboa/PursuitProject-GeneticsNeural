/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tasks;

import pursuitDomain.Environment;
import pursuitDomain.Predator;

/**
 *
 * @author J
 */
public class RandomController extends TaskMode {
    
    @Override
    public int[] run(Predator predator) {
        int[] output = new int[2];
        output[0] = Environment.random.nextInt(2);
        output[1] = Environment.random.nextInt(2);
        return output;
    }

    @Override
    public String toString(){
        return "Random Controller - Tarefa 1";
    } 

}
