package pursuitDomain;

import ga.GeneticAlgorithm;
import java.awt.Color;
import java.util.List;

public class Prey extends Agent{

    final private double restProbability;
    
    public Prey(Cell cell, double restProbability){
        super(cell, Color.RED);
        this.restProbability = restProbability;
    }
    
    @Override
    public void act(Environment environment) {
        double prob = Environment.random.nextDouble();
        
        if(prob > restProbability){
            List<Cell> freeCells = environment.getFreeSurroundingCells(cell);
            //setCell(freeCells.get(Environment.random.nextInt(freeCells.size())));
        }// else: won't move
    }    
}
