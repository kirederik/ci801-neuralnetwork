import java.util.Arrays;

import com.davisan.ia.DataSetCancer;
import com.davisan.ia.GeneticAlgorithm;
import com.davisan.ia.MLPIndividual;
import com.davisan.ia.Torneio;
import com.davisan.ia.core.MLP.MultiLayerPerceptron;


public class Main
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
            
            MLPIndividual.dataset = new DataSetCancer("bases/cancer.data", 9, 2);
            MLPIndividual.dataset.faixaAmostra[0] = 0;
            MLPIndividual.dataset.faixaAmostra[1] = 350;
            GeneticAlgorithm.Evolve(new MLPIndividual(9,5,2), popsize, geracoes);
            System.out.println("fim! " + GeneticAlgorithm.best.toString());
            
            MultiLayerPerceptron bestMLP = ((MLPIndividual)GeneticAlgorithm.best).createtMLP();
            
            
            int difs = 0;
            for(int i=350; i < 525; ++i)
            {
                double[] out = new double[2];
                bestMLP.apresentarPadrao(MLPIndividual.dataset.input[i], out);
                if(!Arrays.equals(MLPIndividual.dataset.output[i], out))
                    difs++;
            }
            System.out.println("validação diferentes = " + difs);
            
            difs = 0;
            for(int i=525; i < 699; ++i)
            {
                double[] out = new double[2];
                bestMLP.apresentarPadrao(MLPIndividual.dataset.input[i], out);
                if(!Arrays.equals(MLPIndividual.dataset.output[i], out))
                    difs++;
            }
            System.out.println("teste diferentes = " + difs);
        }
        catch (Exception e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
