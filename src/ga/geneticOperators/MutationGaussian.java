package ga.geneticOperators;

import ga.GeneticAlgorithm;
import ga.RealVectorIndividual;

public class MutationGaussian <I extends RealVectorIndividual> extends Mutation<I> {

    private double sd;
    
    public MutationGaussian(double probability, double sd) {
        super(probability);
        this.sd = sd;
    }

    @Override
    public void run(I ind) {
        for (int i = 0; i < ind.getNumGenes(); i++) {
            if (GeneticAlgorithm.random.nextDouble() < probability) {
                double newValue = ind.getGene(i) + sd * GeneticAlgorithm.random.nextGaussian();
                ind.setGene(i, newValue);
            }
        }
    }
    
    @Override
    public String toString(){
        return "Gaussian mutation (prob = " + probability + "; sd = " + sd + ")";
    }
}