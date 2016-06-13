package pursuitDomain;

import ga.GeneticAlgorithm;
import java.awt.Color;
import java.util.List;

public class Prey extends Agent{

    final private double restProbability;
    private Cell previousCell;
    
    public Prey(Cell cell, double restProbability){
        super(cell, Color.RED);
        this.restProbability = restProbability;
    }
    
    @Override
    public void act(Environment environment) {
        double prob = Environment.random.nextDouble();
        
        if(prob > restProbability){
            previousCell = cell;
            List<Cell> freeCells = environment.getFreeSurroundingCells(cell);
            Cell newCell = freeCells.get(Environment.random.nextInt(freeCells.size()));
            setCell(newCell);
        }// else: won't move
    }   
    
    public Cell getPreviousCell(){
        return previousCell;
    }
}
