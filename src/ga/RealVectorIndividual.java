package ga;

public abstract class RealVectorIndividual <P extends Problem, I extends RealVectorIndividual> extends Individual<P, I>{

    //TODO: GENOME DEFINITION;
    protected double[] genome;
    
    public RealVectorIndividual(P problem, int size) {
        super(problem);
        
        genome = new double[size];
        generateGenome();
        
    }

    public RealVectorIndividual(RealVectorIndividual<P, I> original) {
        super(original);
        this.genome = new double[original.getNumGenes()];
        System.arraycopy(original.genome, 0, genome, 0, genome.length);
    }
    
    private void generateGenome(){
        // generate random between -1 & 1
        for(int i = 0; i < genome.length; i++){
            genome[i] = GeneticAlgorithm.random.nextDouble() * 2 - 1;
        }
    }
    
    @Override
    public int getNumGenes() {
        return genome.length;
    }
    
    public double getGene(int index) {
        return genome[index];
    }
    
    public void setGene(int index, double newValue) {
        genome[index] = newValue;
    }

    @Override
    public void swapGenes(RealVectorIndividual other, int index) {
        double aux = genome[index];
        genome[index] = other.getGene(index);
        other.setGene(index, aux);
    }
}
