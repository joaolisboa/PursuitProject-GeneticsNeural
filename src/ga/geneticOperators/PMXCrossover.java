package ga.geneticOperators;

import ga.GeneticAlgorithm;
import ga.Individual;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PMXCrossover <I extends Individual> extends Recombination<I>{
    
    public PMXCrossover(double probability) {
        super(probability);
    }
    
    public double getProbablity(){
        return probability;
    }

    public void run(I ind1, I ind2) {
        //inc.
        int size = ind1.getNumGenes();
        int number1 = GeneticAlgorithm.random.nextInt(size - 1);
        int number2 = GeneticAlgorithm.random.nextInt(size);
        int start = (Math.min(number1, number2));
        int end = (Math.max(number1, number2));
        List<Double> child1 = Arrays.asList(new Double[size]);
        List<Double> child2 = Arrays.asList(new Double[size]);
        
        for(int i = 0; i < size; i++){
            child1.set(i, 0.0);
            child2.set(i, 0.0);
        }
      
        for(int i = start; i <= end; i++){
            child1.set(i, ind1.getGene(i));
            child2.set(i, ind2.getGene(i));
        }
      
        int currentIndex;
        double geneParent1;
        double geneParent2;
        for (int i = start; i <= end+2; i+=3){
            currentIndex = i;
            geneParent1 = ind1.getGene(currentIndex);
            geneParent2 = ind2.getGene(currentIndex);
            if (!checkValor(geneParent2, child1)){
                int indexP2j = indexOf(geneParent1, ind2);
                double geneP2j = getValueInChild(indexP2j, child1);
                
                if (geneP2j == 0){
                    child1.set(indexP2j, ind2.getGene(currentIndex));
                    child1.set(indexP2j+1, ind2.getGene(currentIndex+1));
                    child1.set(indexP2j+2, ind2.getGene(currentIndex+2));
                } else {
                    int indexP2k = indexOf(geneP2j, ind2);
                    if(getValueInChild(indexP2k, child1) == 0){
                        child1.set(indexP2k, ind2.getGene(currentIndex));
                        child1.set(indexP2k+1, ind2.getGene(currentIndex+1));
                        child1.set(indexP2k+2, ind2.getGene(currentIndex+2));
                    }
                    
                }
            }
            if (!checkValor(geneParent1, child2)){
                int indexP1j = indexOf(geneParent2, ind1);
                double geneP1j = getValueInChild(indexP1j, child2);
                if (geneP1j != 0){
                    child2.set(indexP1j, ind1.getGene(currentIndex));
                    child2.set(indexP1j+1, ind1.getGene(currentIndex+1));
                    child2.set(indexP1j+2, ind1.getGene(currentIndex+2));
                } else {
                    int indexP1k = indexOf(geneParent1, ind1);
                    if (getValueInChild(indexP1k, child2) == 0){
                        child2.set(indexP1k, ind1.getGene(currentIndex));
                        child2.set(indexP1k+1, ind1.getGene(currentIndex+1));
                        child2.set(indexP1k+2, ind1.getGene(currentIndex+2));
                    }
                }
            }
        }
        
        ArrayList<Integer> emptyChild1 = new ArrayList<Integer>();
        ArrayList<Integer> emptyChild2 = new ArrayList<Integer>();
        
        for (int i=0; i < (child1.size()/3);i++){
            //int tempGene = getPecaInChild(i, child1);
            if (!checkValor(i+1, child1)){
                emptyChild1.add(i+1);
            }
        }
        for (int i=0; i<(child2.size()/3);i++){
            if (!checkValor(i+1, child2)){
                emptyChild2.add(i+1);
            }
        }
        for (int i:emptyChild1){
            //posicao no ind2, onde i está
            int indPos = getIndPos(ind2, i);
            child1.set(indPos, ind2.getGene(indPos));
            child1.set(indPos+1, ind2.getGene(indPos+1));
            child1.set(indPos+2, ind2.getGene(indPos+2));
        }
        for (int i:emptyChild2){
            int indPos = getIndPos(ind1, i);
            child2.set(indPos, ind1.getGene(indPos));
            child2.set(indPos+1, ind1.getGene(indPos+1));
            child2.set(indPos+2, ind1.getGene(indPos+2));
        }     
        
        for(int num = 0; num < size * 3; num++){
            ind1.setGene(num, child1.get(num));
            ind2.setGene(num, child2.get(num));
        }
    }
    
    public double getValueInChild(int index, List<Double> child){
        for(int x = 0; x < child.size(); x++){
            if(index == x){
                return child.get(x);
            }
        }
        return 0;
    }
    
    public boolean checkValor(double id, List<Double> child){
        for(int x = 0; x < child.size(); x++){
            if(id == child.get(x)){
                return true;
            }
        }
        return false;
    }
    
    @Override
    public String toString(){
        return "PMXCrossover (" + probability + ")";
    }   

    private int indexOf(double geneParent, I ind) {
        for(int x = 0; x < ind.getNumGenes(); x++){
            if(geneParent == ind.getGene(x)){
                return x;
            }
        }
        return 0;
    }

    public int getIndPos(I ind, int i) {
        for(int x = 0; x < ind.getNumGenes(); x+=3){
            if(i == ind.getGene(x)){
                return x;
            }
        }
        return 0;
    }
}
