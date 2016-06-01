package ga.geneticOperators;

import ga.GeneticAlgorithm;
import ga.Individual;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class OXCrossover <I extends Individual> extends Recombination<I>{
    
    public OXCrossover(double probability) {
        super(probability);
    }
    
    public double getProbablity(){
        return probability;
    }

    public void run(I ind1, I ind2) {
        int size = (ind1.getNumGenes()) / 3;
        
        int number1 = GeneticAlgorithm.random.nextInt(size - 1);
        int number2 = GeneticAlgorithm.random.nextInt(size);
        
        int start = (Math.min(number1, number2)) * 3;
        int end = (Math.max(number1, number2)) * 3;
      
        List<Double> child1 = new ArrayList<Double>();
        List<Double> child2 = new ArrayList<Double>();
      
        for(int i = start; i <= end + 2; i++){
            child1.add(ind1.getGene(i));
            child2.add(ind2.getGene(i));
        }
      
        int currentIndex;
        int count = 0;
        for (int i = 0; i < size * 3; i+=3) {
            currentIndex = ((end / 3) + count) % size*3;
            count++;
            
            if(!checkValor(currentIndex, ind2, child1)){
            	child1.add(ind2.getGene(currentIndex));
                child1.add(ind2.getGene(currentIndex+1));
                child1.add(ind2.getGene(currentIndex+2));
            }
          
            if(!checkValor(currentIndex, ind1, child2)){
            	child2.add(ind1.getGene(currentIndex));
                child2.add(ind1.getGene(currentIndex+1));
                child2.add(ind1.getGene(currentIndex+2));
            }
        }
        
        Collections.rotate(child1, start);
        Collections.rotate(child2, start);
        
        for(int num = 0; num < size * 3; num++){
            ind1.setGene(num, child1.get(num));
            ind2.setGene(num, child2.get(num));
        }
    }
    
    public boolean checkValor(int id, I ind, List<Double> child){
        for(int x = 0; x < child.size(); x+=3){
            if(ind.getGene(id) == child.get(x)){
                return true;
            }
        }
        return false;
    }
    
    @Override
    public String toString(){
        return "OXCrossover (" + probability + ")";
    }   
}
