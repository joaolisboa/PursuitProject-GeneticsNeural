package ga;

/**
 * Created by J on 18/04/2016.
 */
public abstract class RealVectorIndividual<P extends Problem, I extends RealVectorIndividual> extends Individual<P, I> {

    public static final boolean ONE = true;
    public static final boolean ZERO = false;

    protected double[] genome;

    public RealVectorIndividual(P problem, int size, double prob1s) {
        super(problem);
        genome = new double[size];
        for (int g = 0; g < genome.length; g++) {
            //genome[g] = (GeneticAlgorithm.random.nextDouble() < prob1s) ? ONE : ZERO;
        }
    }

    public RealVectorIndividual(RealVectorIndividual<P, I> original) {
        super(original);
        this.genome = new double[original.genome.length];
        System.arraycopy(original.genome, 0, genome, 0, genome.length);
    }

    public int getNumGenes() {
        return genome.length;
    }

    public double getGene(int g) {
        return genome[g];
    }

    public void setGene(int g, double alel) {
        genome[g] = alel;
    }

    public void swapGenes(RealVectorIndividual other, int g) {
        double aux = genome[g];
        genome[g] = other.genome[g];
        other.genome[g] = aux;
    }
}
