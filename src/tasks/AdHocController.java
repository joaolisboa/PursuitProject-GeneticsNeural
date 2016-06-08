/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tasks;

import java.util.LinkedList;
import java.util.List;
import pursuitDomain.Predator;

/**
 *
 * @author J
 */
public class AdHocController extends TaskMode {

    @Override
    public int[] run(Predator p) {
        
        // give a 'role' to each predator depending on his relative position to the prey
        int[] outputDef = new int[2];
        outputDef[0] = 0;
        outputDef[1] = 0;
        
        //role 0->Norte 1->Sul 2->Este 3->Oeste
        if(p.getRole()==-1){
            giveRole(p);
        }
        
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

    //give predator a role
    private void giveRole(Predator p) {

        LinkedList<Predator> pred = new LinkedList<Predator>(); // = lista predadores
        
        for (Predator predator : pred) {
            if(predator.getRole()>=0){
                if(predator.getRole()>=1){
                    if(predator.getRole()>=2){
                        p.setRole(3);
                    }else{
                        p.setRole(2);
                    }
                }else{
                    p.setRole(1);
                }
            }else{
                p.setRole(0);
            }
        }
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
                
        } else if(p.getCell().getLine() > p.preyCor[1]){
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
        
        return null;
    }

    private int[] roleSul(Predator p) {
        int[] inputs = p.getInputs();
        int[] output = p.getOutput();
        
        if (p.getCell().getLine() > p.preyCor[1]) {
            if(Math.abs(p.getCell().getLine()-p.preyCor[1])!=1){
                output[0] = 0;
                output[1] = 0;  
                return output;
            }
                
        } else if(p.getCell().getLine() > p.preyCor[1]){
            output[0] = 0;
            output[1] = 1;
            return output;
        
        } else if (Math.abs(p.getCell().getColumn() - p.preyCor[0]) != 0) {
            if (p.getCell().getColumn()> p.preyCor[0]) {
                output[0] = 1;
                output[1] = 0;
                return output;
                
            } else {
                output[0] = 1;
                output[1] = 1;
                return output;
                }
        }
        
        return null;
    }

    private int[] roleEste(Predator p) {
        int[] inputs = p.getInputs();
        int[] output = p.getOutput();
        
        if (p.getCell().getColumn() < p.preyCor[0]) {
            if(Math.abs(p.getCell().getColumn()-p.preyCor[0])!=1){
                output[0] = 1;
                output[1] = 0;
                return output;
            }
                
        } else if(p.getCell().getColumn()> p.preyCor[0]){
            output[0] = 1;
            output[1] = 1;
            return output;
        
        } else if (Math.abs(p.getCell().getLine()- p.preyCor[1]) != 0) {
            if (p.getCell().getLine()> p.preyCor[1]) {
                output[0] = 0;
                output[1] = 0;
                return output;
            } else {
                output[0] = 0;
                output[1] = 1;
                return output;
                }
        }
        
        return null;
    }

    private int[] roleOeste(Predator p) {
        int[] inputs = p.getInputs();
        int[] output = p.getOutput();
        
        if (p.getCell().getColumn() > p.preyCor[0]) {
            if(Math.abs(p.getCell().getColumn()-p.preyCor[0])!=1){
                output[0] = 1;
                output[1] = 1;
                return output;
            }
                
        } else if(p.getCell().getColumn() < p.preyCor[0]){
            output[0] = 1;
            output[1] = 0;
            return output;
        
        } else if (Math.abs(p.getCell().getLine()- p.preyCor[1]) != 0) {
            if (p.getCell().getLine()> p.preyCor[1]) {
                output[0] = 0;
                output[1] = 0;
                return output;
            } else {
                output[0] = 0;
                output[1] = 1;
                return output;
                }
        }
        
        return null;
    }

}
