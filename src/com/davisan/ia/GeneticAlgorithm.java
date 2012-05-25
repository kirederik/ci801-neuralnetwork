package com.davisan.ia;

import java.util.ArrayList;
import com.davisan.ia.core.AlgoritmoSelecao;
import com.davisan.ia.core.Individual;

public class GeneticAlgorithm
{
    public static double propMutacao = 0.1;
    public static double propCrossover = 0.8;
    public static AlgoritmoSelecao algoselec = new Torneio();
    public static Individual best = null;
            
    public static void Evolve(Individual ind, int popsize, int maxGeracoes)
    {
        best = null;
        ArrayList<Individual> P = new ArrayList<Individual>();
        
        for(int i=0; i < popsize; ++i)
        {
            P.add(ind.novo());
        }
        best = null;
        
        for(int g=0; g < maxGeracoes; ++g)
        {        
            for(int i=0; i < P.size(); ++i)
            {
                if(best == null || P.get(i).fitness() < best.fitness()) // sinal trocado
                    best = P.get(i);
            }
            //System.out.println("Iteracao: " + g + "\nFitness do Melhor Individuo: " + best.fitness() + "\n");
            
            
            ArrayList<Individual> Q = new ArrayList<Individual>();
            for(int j=0; j < popsize/2; ++j)
            {
                Individual p1 = algoselec.Selection(P);
                Individual p2 = algoselec.Selection(P);
                
                Individual[] c = p1.crossOver(p2);
                Individual c1 = c[0];
                Individual c2 = c[1];
                c1.mutate(propMutacao);
                c2.mutate(propMutacao);
                Q.add(c1);
                Q.add(c2);
            }
            P = Q;
        }
    }

    public static void main(String[] args)
    {
        try
        {
            MLPIndividual.dataset = new DataSetCancer("bases/cancer.data", 9, 2);
            MLPIndividual.dataset.faixaAmostra[0] = 0;
            MLPIndividual.dataset.faixaAmostra[1] = 350;
            
            GeneticAlgorithm.Evolve(new MLPIndividual(9,5,2), 50, 1000);
            System.out.println("fim! " + GeneticAlgorithm.best.toString());
        }
        catch (Exception e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
