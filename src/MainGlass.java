
import com.davisan.ia.DataSetGlass;
import com.davisan.ia.GeneticAlgorithm;
import com.davisan.ia.MLPIndividual;
import com.davisan.ia.TestarSaidaMLP;
import com.davisan.ia.Torneio;
import com.davisan.ia.core.MLP.MultiLayerPerceptron;


public class MainGlass
{
    public static void main(String[] args)
    {
        try
        {
            int popsize = 50;
            int geracoes = 200;
            Torneio.TamTorneio = 8;
            GeneticAlgorithm.propMutacao = 0.9;
            GeneticAlgorithm.propCrossover = 0.9;
            MLPIndividual.dataset = new DataSetGlass("bases/glass.data", 9, 7);
            MLPIndividual.dataset.faixaTreinamento[0] = 0;
            MLPIndividual.dataset.faixaTreinamento[1] = 114;
            MLPIndividual.dataset.faixaValidacao[0] = 114;
            MLPIndividual.dataset.faixaValidacao[1] = 164;
            MLPIndividual.dataset.faixaTeste[0] = 164;
            MLPIndividual.dataset.faixaTeste[1] = 214;
            
            GeneticAlgorithm.Evolve(new LMIndividual(9,10,7), popsize, geracoes, 10);
            
            System.out.println("fim! " + GeneticAlgorithm.best.toString());
            MultiLayerPerceptron bestMLP = ((MLPIndividual)GeneticAlgorithm.best).createtMLP();
                        
            int difs = TestarSaidaMLP.Testar(bestMLP, MLPIndividual.dataset, MLPIndividual.dataset.faixaValidacao[0], MLPIndividual.dataset.faixaValidacao[1]);
            System.out.println("validacao diferentes = " + difs + " Acerto = " + (1 - 1.0*difs/(MLPIndividual.dataset.faixaValidacao[1]-MLPIndividual.dataset.faixaValidacao[0])));
            
            difs = TestarSaidaMLP.Testar(bestMLP, MLPIndividual.dataset, MLPIndividual.dataset.faixaTeste[0], MLPIndividual.dataset.faixaTeste[1]);
            System.out.println("teste diferentes = " + difs + " Acerto = " + (1 - 1.0*difs/(MLPIndividual.dataset.faixaTeste[1]- MLPIndividual.dataset.faixaTeste[0])));
        }
        catch (Exception e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
