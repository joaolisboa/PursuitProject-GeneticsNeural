package gui;

import pursuitDomain.Environment;
import pursuitDomain.EnvironmentListener;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingWorker;
import pursuitDomain.PredatorIndividual;

public class PanelSimulation extends JPanel implements EnvironmentListener {

    public static final int PANEL_SIZE = 250;
    public static final int CELL_SIZE = 20;
    public static final int GRID_TO_PANEL_GAP = 20;
    MainFrame mainFrame;
    private Environment environment;
    private Image image;
    JPanel environmentPanel = new JPanel();
    final JButton buttonSimulate = new JButton("Simulate");
    JTextArea statistics = new JTextArea();

    public PanelSimulation(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        environmentPanel.setPreferredSize(new Dimension(PANEL_SIZE, PANEL_SIZE));
        setLayout(new BorderLayout());

        add(environmentPanel, java.awt.BorderLayout.NORTH);
        add(statistics, java.awt.BorderLayout.CENTER);
        add(buttonSimulate, java.awt.BorderLayout.SOUTH);
        
        buttonSimulate.addActionListener(new SimulationPanel_jButtonSimulate_actionAdapter(this));

        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder(""),
                BorderFactory.createEmptyBorder(1, 1, 1, 1)));
    }

    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    public void setJButtonSimulateEnabled(boolean enabled) {
        buttonSimulate.setEnabled(enabled);
    }

    public void jButtonSimulate_actionPerformed(ActionEvent e) {

        environment = mainFrame.getProblem().getEnvironment();
        environment.addEnvironmentListener(this);

        buildImage(environment);

        final PanelSimulation simulationPanel = this;

        SwingWorker worker = new SwingWorker<Void, Void>() {
            @Override
            public Void doInBackground() {
                PredatorIndividual ind = mainFrame.getBestInRun();
                double[] weights = ind.getGenome();
                int environmentSimulations = mainFrame.getProblem().getNumEvironmentSimulations();
                System.out.println("\n####################################");
                System.out.println("simulation: " + Arrays.toString(weights));
                System.out.println("fitness: " + ind.getFitness());
                
                for (int i = 0; i < environmentSimulations; i++) {
                    environment.setPredatorsWeights(weights);
                    environment.initializeAgentsPositions(i);
                    //environmentUpdated();
                    printStats(ind, i);
                    environment.simulate();
                }
                return null;
            }

            @Override
            public void done() {
                environment.removeEnvironmentListener(simulationPanel);
                try {
                    Thread.sleep(1200);
                } catch (InterruptedException ignore) {
                }

            }
        };
        worker.execute();
    }
    
    private void printStats(PredatorIndividual ind, int i){
        StringBuilder sb = new StringBuilder();
        sb.append("Simulation ").append(i+1).append(":");
        sb.append("\n# iterations: ").append(ind.getNumIterations()[i]);
        sb.append("\nlast distance to prey: ").append(ind.getLastDistancesToPrey()[i]);
        sb.append("\ntotal distance to prey: ").append(ind.getTotalDistanceToPreyInSim()[i]);
        statistics.setText(sb.toString());
    }

    public void buildImage(Environment environment) {
        image = new BufferedImage(
                environment.getSize() * CELL_SIZE + 1,
                environment.getSize() * CELL_SIZE + 1,
                BufferedImage.TYPE_INT_RGB);
    }

    @Override
    public void environmentUpdated() {
        int n = environment.getSize();
        Graphics g = image.getGraphics();

        //Fill the cells color
        for (int y = 0; y < n; y++) {
            for (int x = 0; x < n; x++) {
                g.setColor(environment.getCellColor(y, x));
                g.fillRect(x * CELL_SIZE, y * CELL_SIZE, CELL_SIZE, CELL_SIZE);
            }
        }

        //Draw the grid lines
        g.setColor(Color.BLACK);
        for (int i = 0; i <= n; i++) {
            g.drawLine(0, i * CELL_SIZE, n * CELL_SIZE, i * CELL_SIZE);
            g.drawLine(i * CELL_SIZE, 0, i * CELL_SIZE, n * CELL_SIZE);
        }

        g = environmentPanel.getGraphics();
        g.drawImage(image, GRID_TO_PANEL_GAP, GRID_TO_PANEL_GAP, null);

        try {
            Thread.sleep(250);
        } catch (InterruptedException ignore) {
        }
    }
}

//--------------------
class SimulationPanel_jButtonSimulate_actionAdapter implements ActionListener {

    final private PanelSimulation adaptee;

    SimulationPanel_jButtonSimulate_actionAdapter(PanelSimulation adaptee) {
        this.adaptee = adaptee;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        adaptee.jButtonSimulate_actionPerformed(e);
    }
}
