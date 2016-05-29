package pursuitDomain;

import ga.RealVectorIndividual;

public class PredatorIndividual extends RealVectorIndividual<PursuitDomainProblem, PredatorIndividual> {
    
    public PredatorIndividual(PursuitDomainProblem problem, int size) {
        super(problem, size);
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
            
            // adicionar uma formula de fitness diferente caso a presa seja apanhada?
            fitness += 1000 / (env.getNumIterations() * 0.5 + 
                        env.getTotalPredatorsDistance() * 0.25 +
                        env.getPredatorsPreyDistanceSum() * 0.25);
                        // alternative?
                        // 40% numIteracoes 
                        // 30% distancia total dos predadores à presa durante a simulação
                        // 30% distancia no final da simulação
        }
        return fitness;
    }

    public double[] getGenome(){
        return genome;
    }

    @Override
    public String toString() {
        Environment env = problem.getEnvironment();
        StringBuilder sb = new StringBuilder();
        sb.append("\nfitness: " + (double)Math.round(fitness * 1000) / 1000);
        sb.append("\nnumber of iterations: " + env.getNumIterations());
        sb.append("\nlast distance to prey: " + env.getPredatorsPreyDistanceSum());
        sb.append("\ntotal distance to prey: " + env.getTotalPredatorsDistance());
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
