package pursuit;

import ga.GeneticAlgorithm;

/**
 * Created by J on 18/04/2016.
 */
public class Prey {

    //ambiente é que diz que espaços estão vazios
        //não sabe a posição dos predadores
        //escolhe entre os espaços livres à volta

    private double stopProbability = 0.1;

    public Prey(){

    }

    public void act(){
        if(GeneticAlgorithm.random.nextDouble() > stopProbability){
            //get surrounding empty cells from Environment
                //move to random empty cell
        }
    }


}
