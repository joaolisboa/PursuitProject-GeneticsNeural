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
public class AdHocController extends TaskMode {

    @Override
    public int[] run(Predator p) {
        // give a 'role' to each predator depending on his relative position to the prey
        double[][] w1 = p.getW1();
        double[][] w2 = p.getW2();
        int[] inputs = p.getInputs();
        double[] hiddenLayerOutput = p.getHiddenLayerOutput();
        int[] output = p.getOutput();
        
        
        return null;
    }
    
    //give predator a role
    private int giveRole(int[] inputs){
        
        for(int i = 0; i < (inputs.length/2); i++){
            
        }
        
        return 0;
    }

    @Override
    public String toString() {
        return "AdHoc Controller - Tarefa 2";
    }

}
