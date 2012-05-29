

import com.davisan.ia.DataSetCancer;
import com.davisan.ia.GeneticAlgorithm;
import com.davisan.ia.MLPIndividual;
import com.davisan.ia.TestarSaidaMLP;
import com.davisan.ia.Torneio;
import com.davisan.ia.core.MLP.MultiLayerPerceptron;


public class MainCancer
{
    
    
    public static void main(String[] args)
    {
        try
        {
            int popsize = 30;
            int geracoes = 100;
            Torneio.TamTorneio = 4;
            GeneticAlgorithm.propMutacao = 0.1;
            GeneticAlgorithm.propCrossover = 0.9;
            
            MLPIndividual.dataset = new DataSetCancer("bases/cancer.data", 9, 2);
            MLPIndividual.dataset.faixaTreinamento[0] = 0;
            MLPIndividual.dataset.faixaTreinamento[1] = 350;
            MLPIndividual.dataset.faixaValidacao[0] = 350;
            MLPIndividual.dataset.faixaValidacao[1] = 525;
            MLPIndividual.dataset.faixaTeste[0] = 525;
            MLPIndividual.dataset.faixaTeste[1] = 699;
            GeneticAlgorithm.Evolve(new MLPIndividual(9,5,2), popsize, geracoes);
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
