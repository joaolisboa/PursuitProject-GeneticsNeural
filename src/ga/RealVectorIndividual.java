package ga;

import tasks.TaskMode;

public abstract class RealVectorIndividual <P extends Problem, I extends RealVectorIndividual> extends Individual<P, I>{

    //TODO: GENOME DEFINITION;
    private double[] genome;
    private TaskMode taskMode;
    
    
    public RealVectorIndividual(P problem, int size, TaskMode taskMode) {
        super(problem);
        //size = genomeSize
        genome = new double[size];
        this.taskMode = taskMode;
        generateGenome();
    }

    public RealVectorIndividual(RealVectorIndividual<P, I> original) {
        super(original);
        this.genome = new double[original.getNumGenes()];
        System.arraycopy(original.genome, 0, genome, 0, genome.length);
        this.taskMode = original.taskMode;
    }
    
    private void generateGenome(){
        taskMode.generateGenome();
    }
    
    public double[] getGenome(){
        return this.genome;
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
