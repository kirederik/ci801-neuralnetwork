import java.util.Arrays;

import com.davisan.ia.DataSetCancer;
import com.davisan.ia.DataSetDiabetes;
import com.davisan.ia.GeneticAlgorithm;
import com.davisan.ia.MLPIndividual;
import com.davisan.ia.TestarSaidaMLP;
import com.davisan.ia.Torneio;
import com.davisan.ia.core.MLP.MultiLayerPerceptron;


public class MainDiabetes
{
    public static void main(String[] args)
    {
        try
        {
            int popsize = 25;
            int geracoes = 100;
            Torneio.TamTorneio = 4;
            GeneticAlgorithm.propMutacao = 0.5;
            GeneticAlgorithm.propCrossover = 0.8;
            MLPIndividual.dataset = new DataSetDiabetes("bases/pima-indians-diabetes.data", 8, 2);
            MLPIndividual.dataset.faixaTreinamento[0] = 0;
            MLPIndividual.dataset.faixaTreinamento[1] = 252;
            MLPIndividual.dataset.faixaValidacao[0] = 252;
            MLPIndividual.dataset.faixaValidacao[1] = 510;
            MLPIndividual.dataset.faixaTeste[0] = 510;
            MLPIndividual.dataset.faixaTeste[1] = 768;
            
            GeneticAlgorithm.Evolve(new MLPIndividual(8,10,2), popsize, geracoes);
            System.out.println("fim! " + GeneticAlgorithm.best.toString());
            MultiLayerPerceptron bestMLP = ((MLPIndividual)GeneticAlgorithm.best).createtMLP();
                        
            int difs = TestarSaidaMLP.Testar(bestMLP, MLPIndividual.dataset, MLPIndividual.dataset.faixaValidacao[0], MLPIndividual.dataset.faixaValidacao[1]);
            System.out.println("validacao diferentes = " + difs + " Acerto = " + (1 - 1.0*difs/(MLPIndividual.dataset.faixaValidacao[1]-MLPIndividual.dataset.faixaValidacao[0])));
            
            difs = TestarSaidaMLP.Testar(bestMLP, MLPIndividual.dataset, MLPIndividual.dataset.faixaTeste[0], MLPIndividual.dataset.faixaTeste[1]);
            System.out.println("teste diferentes = " + difs + " Acerto = " + (1 - 1.0*difs/(MLPIndividual.dataset.faixaTeste[1]-MLPIndividual.dataset.faixaTeste[0])));
        }
        catch (Exception e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
