package ga.geneticOperators;

import ga.BitVectorIndividual;
import ga.GeneticAlgorithm;

public class MutationBinary <I extends BitVectorIndividual> extends Mutation<I> {

    public MutationBinary(double probability) {
        super(probability);
    }

    public void run(I ind) {
        int indSize = ind.getNumGenes();
        for (int i = 0; i < indSize; i++) {
            if (GeneticAlgorithm.random.nextDouble() < probability) {
                ind.setGene(i, ind.getGene(i) != I.ONE);
            }
        }
    }
    
    @Override
    public String toString(){
        return "Binary mutation (" + probability + ")";
    }
}