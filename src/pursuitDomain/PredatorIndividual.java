package pursuitDomain;

import ga.RealVectorIndividual;
import tasks.TaskMode;

public class PredatorIndividual extends RealVectorIndividual<PursuitDomainProblem, PredatorIndividual> {
    
    public PredatorIndividual(PursuitDomainProblem problem, int size, TaskMode taskMode /*COMPLETE?*/) {
        super(problem, size, taskMode);
    }

    public PredatorIndividual(PredatorIndividual original) {
        super(original);
    }

    @Override
    public double computeFitness() {
        Environment env = problem.getEnvironment();
        env.setPredatorsWeights(genome);
        fitness = 0;
        
        for(int i = 0; i < problem.getNumEvironmentSimulations(); i++){
            env.initializeAgentsPositions(i);
            env.simulate();
            fitness += 100 / (env.getNumIterations() * 0.4 + 
                            env.getTotalPredatorsDistance() * 0.3 +
                            env.computePredatorsPreyDistanceSum() * 0.3);
                // 40% numIteracoes 
                // 30% distancia total dos predadores à presa durante a simulação
                // 30% distancia no final da simulação 
        }
        
        return 0;
    }

    public double[] getGenome(){
        return genome;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\nfitness: ");
        sb.append(fitness);
        //COMPLETE
        return sb.toString();
    }

    /**
     *
     * @param i
     * @return 1 if this object is BETTER than i, -1 if it is WORST than I and
     * 0, otherwise.
     */
    @Override
    public int compareTo(PredatorIndividual i) {
        if(this.fitness > i.getFitness()){
            return 1;
        }else if(this.fitness < i.getFitness()){
            return -1;
        }
        return 0;
    }

    @Override
    public PredatorIndividual clone() {
        return new PredatorIndividual(this);
    }
}
