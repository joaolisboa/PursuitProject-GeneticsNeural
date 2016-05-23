package pursuitDomain;

import java.awt.Color;

public class Prey extends Agent{

    final private double restProbability;
    
    public Prey(Cell cell, double restProbability){
        super(cell, Color.RED);
        this.restProbability = restProbability;
    }
    
    @Override
    public void act(Environment environment) {
        double prob = environment.random.nextDouble();
        
        if(prob > restProbability){
            switch(environment.random.nextInt(4)){
                case 0: //move up
                case 1: //move down
                case 2: //move left
                case 3: //move right
            }
        }// else: won't move
    }    
}
