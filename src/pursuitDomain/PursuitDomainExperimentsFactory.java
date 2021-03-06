package pursuitDomain;

import experiments.*;
import ga.GAListener;
import ga.GeneticAlgorithm;
import ga.geneticOperators.*;
import ga.selectionMethods.*;
import gui.MainFrame;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import statistics.StatisticBestAverage;
import statistics.StatisticBestInRun;
import tasks.AdHocController;
import tasks.HeterogeneousController;
import tasks.HomogeneousController;
import tasks.RandomController;
import tasks.TaskMode;

public class PursuitDomainExperimentsFactory extends ExperimentsFactory {

    private int populationSize;
    private int maxGenerations;
    private TaskMode taskMode;
    private SelectionMethod<PredatorIndividual, PursuitDomainProblem> selection;
    private Recombination<PredatorIndividual> recombination;
    private Mutation<PredatorIndividual> mutation;
    private PursuitDomainProblem problem;
    private Experiment<PursuitDomainExperimentsFactory, PursuitDomainProblem> experiment;

    public PursuitDomainExperimentsFactory(File configFile) throws IOException {
        super(configFile);
    }

    @Override
    public Experiment buildExperiment() throws IOException {
        numRuns = Integer.parseInt(getParameterValue("Runs"));
        populationSize = Integer.parseInt(getParameterValue("Population size"));
        maxGenerations = Integer.parseInt(getParameterValue("Max generations"));

        //SELECTION
        if (getParameterValue("Selection").equals("tournament")) {
            int tournamentSize = Integer.parseInt(getParameterValue("Tournament size"));
            selection = new Tournament<>(populationSize, tournamentSize);
        }

        //RECOMBINATION
        double recombinationProbability = Double.parseDouble(getParameterValue("Recombination probability"));
        switch(getParameterValue("Recombination")){
            case "one_cut":
                recombination = new RecombinationOneCut<>(recombinationProbability);
                break;
            case "two_cuts":
                recombination = new RecombinationTwoCuts<>(recombinationProbability);
                break;
            case "uniform":
                recombination = new RecombinationUniform<>(recombinationProbability);
        }
        
        switch(getParameterValue("Task")){
            case "random":
                taskMode = new RandomController();
                break;
            case "adhoc":
                taskMode = new AdHocController();
                break;
            case "task_3":
                taskMode = new HomogeneousController();
                break;
            case "task_4":
                taskMode = new HeterogeneousController();
        }

        //COMPLETE
        //MUTATION
        double mutationProbability = Double.parseDouble(getParameterValue("Mutation probability"));
        double sd = Double.parseDouble(getParameterValue("Standard deviation"));
        if (getParameterValue("Mutation").equals("gaussian_distribution")) {
            //OTHER PARAMETERS TO YOUR MUTATION OPERATOR, IF THEY EXIST ARE FETCHED HERE
            mutation = new MutationGaussian<>(mutationProbability, sd);
        }

        //PROBLEM 
        problem = PursuitDomainProblem.buildProblemFromFile(new File(getParameterValue("Problem file")));

        String textualRepresentation = buildTextualExperiment();

        experiment = new Experiment<>(this, numRuns, problem, textualRepresentation);

        statistics = new ArrayList<>();
        for (String statisticName : statisticsNames) {
            ExperimentListener statistic = buildStatistic(statisticName);
            statistics.add(statistic);
            experiment.addExperimentListener(statistic);
        }

        return experiment;
    }

    @Override
    public GeneticAlgorithm generateGAInstance(int seed) {
        GeneticAlgorithm<PredatorIndividual, PursuitDomainProblem> ga = 
                new GeneticAlgorithm<>(
                    populationSize,
                    maxGenerations,
                    selection,
                    recombination,
                    mutation,
                    new Random(seed),
                    taskMode);

        for (ExperimentListener statistic : statistics) {
            ga.addGAListener((GAListener) statistic);
        }

        return ga;
    }

    private ExperimentListener buildStatistic(String statisticName) {
        switch(statisticName){
            case "BestIndividual":
                return new StatisticBestInRun();
            case "BestAverage":
                return new StatisticBestAverage(numRuns);
        }        
        return null;
    }

    private String buildTextualExperiment() {
        StringBuilder sb = new StringBuilder();
        sb.append("Population size:" + populationSize + "\t");
        sb.append("Max generations:" + maxGenerations + "\t");
        sb.append("Selection:" + selection + "\t");
        sb.append("Recombination:" + recombination + "\t");
        sb.append("Mutation:" + mutation + "\t");
        sb.append("Task: " + taskMode + "\t");
        return sb.toString();
    }
}
