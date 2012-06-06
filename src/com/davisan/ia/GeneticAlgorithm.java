package com.davisan.ia;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;

import com.davisan.ia.core.AlgoritmoSelecao;
import com.davisan.ia.core.Individual;

class FitnessComparator implements Comparator<Integer>
{
    double[] list;
    
    @Override
    public int compare(Integer x, Integer y)
    {
        if(list == null) return 0;
        if(list[x.intValue()] < list[y.intValue()])
            return -1;
        if(list[x.intValue()] > list[y.intValue()])
            return 1;
        return 0;
    }
}

public class GeneticAlgorithm
{
    public static double propMutacao = 0.1;
    public static double propCrossover = 0.8;
    public static AlgoritmoSelecao algoselec = new Torneio();
    public static Individual best = null;
    public static int gmax;
    public static int currentg;
            
    public static void Evolve(Individual ind, int popsize, int maxGeracoes, int numElites)
    {
        gmax = maxGeracoes;
        
        best = null;
        ArrayList<Individual> P = new ArrayList<Individual>();
        
        for(int i=0; i < popsize; ++i) 
        {
            P.add(ind.novo());
        }
        best = null;
        
        for(int g=0; g < maxGeracoes; ++g)
        {
            currentg = g;
            
            // avalia fitness de todos
            double[] fitnessPop = new double[P.size()];
            for(int i=0; i < P.size(); ++i)
            {
                fitnessPop[i] = P.get(i).fitness();
            }
            double bestFitness = (best == null)? Double.MAX_VALUE : best.fitness() ;
            
            // encontra o best
            for(int i=0; i < P.size(); ++i)
            {
                if(best == null || fitnessPop[i] < bestFitness) // sinal trocado
                {
                    best = P.get(i);
                    bestFitness = fitnessPop[i];
                }
            }
           
            //System.out.println("Iteracao: " + g + "\nFitness do Melhor Individuo: " + best.fitness() + "\n");
            System.out.print("" + best.fitness() + ";");
            
            ArrayList<Individual> Q = new ArrayList<Individual>();
            
            // elitismo
            FitnessComparator mycomp = new FitnessComparator();
            mycomp.list = fitnessPop;
            PriorityQueue<Integer> myQueue = new PriorityQueue<Integer>(P.size(), mycomp);
            for(int i=0; i < P.size(); ++i)
                myQueue.add(new Integer(i));
            
            for(int i=0; i < numElites; ++i)
            {
                Q.add(P.get(myQueue.poll()));
            }
            
            
            int numCrossovers = (popsize - numElites)/2;
            for(int j=0; j < numCrossovers; ++j)
            {
                Individual p1 = algoselec.Selection(P);
                Individual p2 = algoselec.Selection(P);
                
                Individual[] c = p1.crossOver(p2);
                Individual c1 = c[0];
                Individual c2 = c[1];
                c1.mutate();
                c2.mutate();
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
            MLPIndividual.dataset.faixaTreinamento[0] = 0;
            MLPIndividual.dataset.faixaTreinamento[1] = 350;
            
            /*
            GeneticAlgorithm.Evolve(new MLPIndividual(9,5,2), 50, 1000, 10);
            System.out.println("fim! " + GeneticAlgorithm.best.toString());
            */
            
            Individual ind =  new MLPIndividual(9,5,2);
            ArrayList<Individual> P = new ArrayList<Individual>();
            for(int i=0; i < 20; ++i) 
            {
                P.add(ind.novo());
            }
            
            double[] fitnessPop = new double[P.size()];
            for(int i=0; i < P.size(); ++i)
            {
                fitnessPop[i] = P.get(i).fitness();
                System.out.println(fitnessPop[i]);
            }
            FitnessComparator mycomp = new FitnessComparator();
            mycomp.list = fitnessPop;
            PriorityQueue<Integer> myQueue = new PriorityQueue<Integer>(P.size(), mycomp);
            for(int i=0; i < P.size(); ++i)
                myQueue.add(new Integer(i));
            
            System.out.println("\ntotal:");
            for(int i=0; i < 5; ++i)
            {
                System.out.println(P.get(myQueue.poll()).fitness());
            }
        }
        catch (Exception e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
