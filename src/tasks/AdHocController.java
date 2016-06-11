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
public class AdHocController extends TaskMode {
    
    private static List<Predator> predators;
    int i =1;
    
    public AdHocController(List<Predator> predators){
        this.predators = predators;
    }
    
    public AdHocController(){
        
    }

    @Override
    public int[] run(Predator p) {
        
        // give a 'role' to each predator depending on his relative position to the prey
        
        int[] outputDef = new int[2];
        
        switch(p.getRole()){
            case 0:
                return roleNorth(p);
                
            case 1:
                return roleSul(p);
             
            case 2:
                return roleEste(p);
                
            case 3:
                return roleOeste(p);
        }
        return outputDef;
    }

    @Override
    public String toString() {
        return "AdHoc Controller - Tarefa 2";
    }

    private int[] roleNorth(Predator p) {
        int[] inputs = p.getInputs();
        int[] output = p.getOutput();
        
        if (p.getCell().getLine() < p.preyCor[1]) {
            if(Math.abs(p.getCell().getLine()-p.preyCor[1])!=1){
                output[0] = 0;
                output[1] = 1;   
            }
                
        } else if(p.getCell().getLine() >= p.preyCor[1]){
            output[0] = 0;
            output[1] = 0;
        
        } else if (Math.abs(p.getCell().getColumn() - p.preyCor[0]) != 0) {
            if (p.getCell().getColumn()> p.preyCor[0]) {
                output[0] = 1;
                output[1] = 0;
            } else {
                output[0] = 1;
                output[1] = 1;
                }
        }        
        return output;
    }

    private int[] roleSul(Predator p) {
        int[] inputs = p.getInputs();
        int[] output = p.getOutput();
        
        if (p.getCell().getLine() > p.preyCor[1]) {
            if(Math.abs(p.getCell().getLine()-p.preyCor[1])!=1){
                output[0] = 0;
                output[1] = 0;  
            }
                
        } else if(p.getCell().getLine() <= p.preyCor[1]){
            output[0] = 0;
            output[1] = 1;
        
        } else if (Math.abs(p.getCell().getColumn() - p.preyCor[0]) != 0) {
            if (p.getCell().getColumn()> p.preyCor[0]) {
                output[0] = 1;
                output[1] = 0;
                
            } else {
                output[0] = 1;
                output[1] = 1;
                }
        }
        
        return output;
    }

    private int[] roleEste(Predator p) {
        int[] inputs = p.getInputs();
        int[] output = p.getOutput();
        
        if (p.getCell().getColumn() < p.preyCor[0]) {
            if(Math.abs(p.getCell().getColumn()-p.preyCor[0])!=1){
                output[0] = 1;
                output[1] = 0;
            }
                
        } else if(p.getCell().getColumn()>= p.preyCor[0]){
            output[0] = 1;
            output[1] = 1;
        
        } else if (Math.abs(p.getCell().getLine()- p.preyCor[1]) != 0) {
            if (p.getCell().getLine()> p.preyCor[1]) {
                output[0] = 0;
                output[1] = 0;
            } else {
                output[0] = 0;
                output[1] = 1;
                }
        }
        
        return output;
    }

    private int[] roleOeste(Predator p) {
        int[] inputs = p.getInputs();
        int[] output = p.getOutput();
        
        if (p.getCell().getColumn() > p.preyCor[0]) {
            if(Math.abs(p.getCell().getColumn()-p.preyCor[0])!=1){
                output[0] = 1;
                output[1] = 1;
            }
                
        } else if(p.getCell().getColumn() <= p.preyCor[0]){
            output[0] = 1;
            output[1] = 0;
        
        } else if (Math.abs(p.getCell().getLine()- p.preyCor[1]) != 0) {
            if (p.getCell().getLine()> p.preyCor[1]) {
                output[0] = 0;
                output[1] = 0;
            } else {
                output[0] = 0;
                output[1] = 1;
                }
        }
        
        return output;
    }

}
