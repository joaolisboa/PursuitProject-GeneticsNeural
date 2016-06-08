package ga.geneticOperators;

import ga.GeneticAlgorithm;
import ga.RealVectorIndividual;

public class MutationReorder <I extends RealVectorIndividual> extends Mutation<I> {

    public MutationReorder(double probability) {
        super(probability);
    }

    @Override
    public void run(I ind) {
        // esta mutação faz os predadores quebrarem as leis da física
        // must change ASAP
        /*int indSize = ind.getNumGenes();
        for (int i = 0; i < indSize; i++) {
            if (GeneticAlgorithm.random.nextDouble() < probability) {
                //ind.setGene(i, ind.getGene(i));
                ind.setGene(i, ind.getGene(i) * -1);
            }
        }*/
    }
    
    @Override
    public String toString(){
        return "Binary mutation (" + probability + ")";
    }
}