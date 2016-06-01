package pursuitDomain;

import ga.RealVectorIndividual;
import java.util.Arrays;

public class PredatorIndividual extends RealVectorIndividual<PursuitDomainProblem, PredatorIndividual> {

    public PredatorIndividual(PursuitDomainProblem problem, int size) {
        super(problem, size, problem.getNumEvironmentSimulations());
    }

    public PredatorIndividual(PredatorIndividual original) {
        super(original);
    }

    @Override
    public double computeFitness() {
        Environment env = problem.getEnvironment();
        fitness = 0;
        numCatches = 0;
        numIterations = new int[problem.getNumEvironmentSimulations()];
        lastDistancesToPrey = new int[problem.getNumEvironmentSimulations()];
        totalDistanceToPreyInSim = new int[problem.getNumEvironmentSimulations()];
        
        for (int i = 0; i < problem.getNumEvironmentSimulations(); i++) {
            env.setPredatorsWeights(genome);
            env.initializeAgentsPositions(i);
            if (env.simulate()) {
                numCatches++;
            }
            numIterations[i] = env.getNumIterations();
            totalDistanceToPreyInSim[i] = env.getTotalPredatorsDistance();
            lastDistancesToPrey[i] = env.getPredatorsPreyDistanceSum();
            
            //fitness += (numIterations[i]*0.4 + lastDistancesToPrey[i]*0.6 + (totalDistanceToPreyInSim[i]/20)*0.5);
            //fitness += lastDistancesToPrey[i];
        }
        fitness = 100 / ((sumAll(numIterations)/2)*0.3 
                + (sumAll(lastDistancesToPrey)/2)*0.4 
                + (sumAll(totalDistanceToPreyInSim)/20)*0.3);
        fitness += numCatches * 0.5;
        //fitness = getAverage(lastDistancesToPrey) + getAverage(totalDistanceToPreyInSim);
        
        return fitness;
    }
    
    private int min(int[] array){
        int num = array[0];
        for(int i = 1; i < array.length; i++){
            num = num > array[i] ? array[i] : num;
        }
        return num;
    }

    private int sumAll(int[] array) {
        int sum = 0;
        for (int i = 0; i < array.length; i++) {
            sum += array[i];
        }
        return sum;
    }

    private double getAverage(int[] array) {
        double average;
        int sum = 0;
        for (int i = 0; i < array.length; i++) {
            sum += array[i];
        }
        average = sum / array.length;
        return average;
    }

    public double[] getGenome() {
        return genome;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\nfitness: ").append((double)Math.round(fitness*1000)/1000);
        sb.append("\nnumber of iterations: \n").append(Arrays.toString(numIterations));
        sb.append("\nlast distance to prey: \n").append(Arrays.toString(lastDistancesToPrey));
        sb.append("\ntotal distance to prey: \n").append(Arrays.toString(totalDistanceToPreyInSim));
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
        //System.out.println("my fitness: " + fitness + " compared to " + i.getFitness());
        if (this.fitness > i.getFitness()) {
            return 1;
        } else if (this.fitness < i.getFitness()) {
            return -1;
        }
        return 0;
    }

    @Override
    public PredatorIndividual clone() {
        return new PredatorIndividual(this);
    }

    public int[] getNumIterations() {
        return numIterations;
    }

    public int[] getLastDistancesToPrey() {
        return lastDistancesToPrey;
    }

    public int[] getTotalDistanceToPreyInSim() {
        return totalDistanceToPreyInSim;
    }

    public int getNumCatches() {
        return numCatches;
    }
    
}
