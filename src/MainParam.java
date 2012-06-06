import java.util.Arrays;

import com.davisan.ia.DataSetCancer;
import com.davisan.ia.DataSetDiabetes;
import com.davisan.ia.DataSetGlass;
import com.davisan.ia.DataSetHeart;
import com.davisan.ia.DataSetIris;
import com.davisan.ia.GeneticAlgorithm;
import com.davisan.ia.InicializacaoDistNormal;
import com.davisan.ia.MLPIndividual;
import com.davisan.ia.TestarSaidaMLP;
import com.davisan.ia.Torneio;
import com.davisan.ia.core.MLP.MultiLayerPerceptron;


public class MainParam
{

    /**
     * @param args
     */
    public static void main(String[] args)
    {
        if(args.length != 9)
        {
            System.out.println("Rede Neural - Tópicos em IA - 2012.1");
            System.out.println("Uso: java MainParam tipo_base operador_mutacao operador_crossover populacao num_elites tam_torneio geracoes prob_mutacao prob_crossover");
            System.out.println("tipo_base - [0 = Cancer] [1 = Diabetes ][2 = Glass] [3 = Heart] [4 = Iris]");
            System.out.println("operador_mutacao - [-1 = random] [0 = BiasedMutateWeights] [1 = UnbiasedMutateWeights] [2 = MutateNodes] [3 = MutateWeakestNodes] [4 = mutationSinglePointRandom] [5 = mutationNonUniform]");
            System.out.println("operador_crossover - [-1 = random] [0 = crossOverWeights] [1 = crossOverNodes] [2 = crossOverFeatures] [3 = LineRecombination]");
            System.out.println("populacao - Tamanho da população (int)");
            System.out.println("num_elites - Número de indivíduos elites (int)");
            System.out.println("tam_torneio - Tamanho do torneio (int)");
            System.out.println("geracoes - número de gerações (int)");
            System.out.println("prob_mutacao - probabilidade de mutação (double)");
            System.out.println("prob_crossover - Probabilidade de crossover (double)");
            return;
        }
        int tipoBase = Integer.parseInt(args[0]);
        int tipoOpMut = Integer.parseInt(args[1]);
        int tipoOpCros = Integer.parseInt(args[2]);
        int popsize = Integer.parseInt(args[3]);
        int numElites = Integer.parseInt(args[4]);
        int tamTorneio = Integer.parseInt(args[5]);
        int geracoes = Integer.parseInt(args[6]);
        double pMut = Double.parseDouble(args[7]);
        double pCross = Double.parseDouble(args[8]);
        
        String[] basesStr = { "Cancer", "Diabetes", "Glass", "Heart", "Iris"};       
        try
        {
            switch(tipoBase)
            {
            case 0:
                MLPIndividual.dataset = new DataSetCancer("bases/cancer.data", 9, 2);    
                MLPIndividual.dataset.faixaTreinamento[0] = 0;
                MLPIndividual.dataset.faixaTreinamento[1] = 350;
                MLPIndividual.dataset.faixaValidacao[0] = 350;
                MLPIndividual.dataset.faixaValidacao[1] = 525;
                MLPIndividual.dataset.faixaTeste[0] = 525;
                MLPIndividual.dataset.faixaTeste[1] = 699;
                break;
            case 1:
                MLPIndividual.dataset = new DataSetDiabetes("bases/pima-indians-diabetes.data", 8, 2);
                MLPIndividual.dataset.faixaTreinamento[0] = 0;
                MLPIndividual.dataset.faixaTreinamento[1] = 252;
                MLPIndividual.dataset.faixaValidacao[0] = 252;
                MLPIndividual.dataset.faixaValidacao[1] = 510;
                MLPIndividual.dataset.faixaTeste[0] = 510;
                MLPIndividual.dataset.faixaTeste[1] = 768;
                break;
            case 2:
                MLPIndividual.dataset = new DataSetGlass("bases/glass.data", 9, 7);
                MLPIndividual.dataset.faixaTreinamento[0] = 0;
                MLPIndividual.dataset.faixaTreinamento[1] = 114;
                MLPIndividual.dataset.faixaValidacao[0] = 114;
                MLPIndividual.dataset.faixaValidacao[1] = 164;
                MLPIndividual.dataset.faixaTeste[0] = 164;
                MLPIndividual.dataset.faixaTeste[1] = 214;
                break;
            case 3:
                MLPIndividual.dataset = new DataSetHeart("bases/heart.dat", 13, 2);
                MLPIndividual.dataset.faixaTreinamento[0] = 0;
                MLPIndividual.dataset.faixaTreinamento[1] = 130;
                MLPIndividual.dataset.faixaValidacao[0] = 130;
                MLPIndividual.dataset.faixaValidacao[1] = 200;
                MLPIndividual.dataset.faixaTeste[0] = 200;
                MLPIndividual.dataset.faixaTeste[1] = 270;
                break;
            case 4:
                MLPIndividual.dataset = new DataSetIris("bases/iris.data", 4, 3);
                MLPIndividual.dataset.faixaTreinamento[0] = 0;
                MLPIndividual.dataset.faixaTreinamento[1] = 70;
                MLPIndividual.dataset.faixaValidacao[0] = 70;
                MLPIndividual.dataset.faixaValidacao[1] = 110;
                MLPIndividual.dataset.faixaTeste[0] = 110;
                MLPIndividual.dataset.faixaTeste[1] = 150;
                break;
            default:
                System.out.println("Base Inválida");
                return;
            }
            
            Torneio.TamTorneio = tamTorneio;
            GeneticAlgorithm.propMutacao = pMut;
            GeneticAlgorithm.propCrossover = pCross;
            
            LMIndividual.mutationOperator = tipoOpMut;
            LMIndividual.crossoverOperator = tipoOpCros;
            LMIndividual.initDist = new InicializacaoDistNormal(0, 5);
            
            MontanaDavisOperators.desvp = 5;
            LiuWangLiuNiuOperators.lambda = 0.28;
            LiuWangLiuNiuOperators.mutLimInf = -50;
            LiuWangLiuNiuOperators.mutLimSup = 50;
            LiuWangLiuNiuOperators.b = 4;
            
            System.out.println("Base = " + basesStr[tipoBase]);
            
            switch(tipoBase)
            {
            case 0:
                GeneticAlgorithm.Evolve(new LMIndividual(9,5,2), popsize, geracoes, numElites);
                break;
            case 1:
                GeneticAlgorithm.Evolve(new LMIndividual(8,10,2), popsize, geracoes, numElites);
                break;
            case 2:
                GeneticAlgorithm.Evolve(new LMIndividual(9,10,7), popsize, geracoes, numElites);
                break;
            case 3:
                GeneticAlgorithm.Evolve(new LMIndividual(13,5,2), popsize, geracoes, numElites);
                break;
            case 4:
                GeneticAlgorithm.Evolve(new LMIndividual(4,10,3), popsize, geracoes, numElites);
                break;
            }

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
