package ga.geneticOperators;

import ga.GeneticAlgorithm;
import ga.RealVectorIndividual;

public class MutationReorder <I extends RealVectorIndividual> extends Mutation<I> {

    public MutationReorder(double probability) {
        super(probability);
    }

    @Override
    public void run(I ind) {
        int indSize = ind.getNumGenes();
        for (int i = 0; i < indSize; i++) {
            if (GeneticAlgorithm.random.nextDouble() < probability) {
                ind.setGene(i, ind.getGene(i));
            }
        }
    }
    
    @Override
    public String toString(){
        return "Binary mutation (" + probability + ")";
    }
}