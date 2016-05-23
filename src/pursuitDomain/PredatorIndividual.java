package pursuitDomain;

import ga.RealVectorIndividual;
import tasks.TaskMode;

public class PredatorIndividual extends RealVectorIndividual<PursuitDomainProblem, PredatorIndividual> {

    public PredatorIndividual(PursuitDomainProblem problem, int size, TaskMode taskMode /*COMPLETE?*/) {
        super(problem, size, taskMode);
        //COMPLETE?
    }

    public PredatorIndividual(PredatorIndividual original) {
        super(original);
        //COMPLETE
    }

    @Override
    public double computeFitness() {
        //TODO
        
        return 0;
    }

    public double[] getGenome(){
        //TODO
        return null;
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
        //condição vai depender do computeFitness()
        //se o fitness mais baixo for melhor tem de ser ao contrário
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
