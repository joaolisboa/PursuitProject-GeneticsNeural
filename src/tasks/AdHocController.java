/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tasks;

import java.util.Arrays;
import pursuitDomain.Environment;
import pursuitDomain.Predator;

/**
 *
 * @author J
 */
public class AdHocController extends TaskMode {

    @Override
    public int[] run(Predator p) {
        // give a 'role' to each predator depending on his relative position to the prey
        int[] inputs = p.getInputs();
        int[] output = p.getOutput();
        
        if (p.relCor[0] > p.relCor[1]) {
            if (p.getCell().getColumn() > p.preyCor[1]) {
                output[0] = 1;
                output[1] = 0;
            } else if (Math.abs(p.getCell().getColumn() - p.preyCor[1]) == 1) {
                if (p.getCell().getLine() > p.preyCor[0]) {
                    output[0] = 0;
                    output[1] = 0;
                } else {
                    output[0] = 0;
                    output[1] = 1;
                }
            } else {
                output[0] = 1;
                output[1] = 1;
            }
        } else if(p.relCor[0] < p.relCor[1]){
            if (p.getCell().getLine() > p.preyCor[0]) {
                output[0] = 0;
                output[1] = 0;
            }else if (Math.abs(p.getCell().getLine() - p.preyCor[0]) == 1){
                if (p.getCell().getColumn() > p.preyCor[1]) {
                    output[0] = 1;
                    output[1] = 0;
                } else {
                    output[0] = 1;
                    output[1] = 1;
                }
            }else{
                output[0] = 0;
                output[1] = 1;
            }
        }else{
            output[0] = Environment.random.nextInt(2);
            output[1] = Environment.random.nextInt(2);
        }

        return output;
    }

    //give predator a role
    private int giveRole(int[] inputs) {

        for (int i = 0; i < (inputs.length / 2); i++) {

        }

        return 0;
    }

    @Override
    public String toString() {
        return "AdHoc Controller - Tarefa 2";
    }

}
