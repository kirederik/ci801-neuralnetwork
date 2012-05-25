package com.davisan.ia;

import java.util.Random;


import com.davisan.ia.core.DataSet;
import com.davisan.ia.core.FitnessMLP;
import com.davisan.ia.core.Individual;
import com.davisan.ia.core.MLP.MLPChromossome;
import com.davisan.ia.core.MLP.MultiLayerPerceptron;

public class MLPIndividual implements Individual
{
    private MLPChromossome cromo = null;
    public static DataSet dataset = null;
    
    public MultiLayerPerceptron createtMLP() throws Exception
    {
        return cromo.createMLPFromGenes();
    }
    
    @Override
    public String toString()
    {
        return "MLPIndividual [cromo=" + cromo + "]";
    }

    public MLPIndividual(int numEntrada, int numEscondida, int numSaida)
    {
        cromo = new MLPChromossome(new MultiLayerPerceptron(numEntrada, numEscondida, numSaida, new InicializacaoDistNormal(0,1)));
    }
    
    private MLPIndividual()
    {
    }

    
    public Individual novo()
    {
        MLPIndividual ind = new MLPIndividual();
        ind.cromo = new MLPChromossome(new MultiLayerPerceptron(cromo.params[0], cromo.params[1], cromo.params[2], new InicializacaoDistNormal(0,1)));
        return ind;
    }
    
    public MLPIndividual clone()
    {
        MLPIndividual ind = new MLPIndividual();
        ind.cromo = this.cromo.clone();
        return ind;
    }
    
    public double fitness()
    {
        try
        {
            return FitnessMLP.fitness(cromo.createMLPFromGenes(), dataset);
        }
        catch (Exception e)
        {
            System.out.println("erro");
            // TODO Auto-generated catch block
            e.printStackTrace();
            System.exit(1);
            return 0;
        }
    }
    public void mutate(double p)
    {
        double desvp = 1;
        double min = -50;
        double max = 50;
        
        for(int i=0; i < cromo.genes.length; ++i)
        {
            if(p >= new Random().nextDouble())
            {
                double n = 0;
                //do
                {
                    n = desvp * (new Random().nextGaussian());
                    //System.out.println("" + min + " >= " + (cromo.genes[i] + n) + " >= " + max);
                } //while (min >= (cromo.genes[i] + n) || (cromo.genes[i] +n) >= max );
                
                cromo.genes[i] = cromo.genes[i] + n;
            }
        }
    }
    public Individual[] crossOver(Individual other)
    {
        MLPIndividual[] ind = new MLPIndividual[2]; 
        MLPIndividual p1 = this.clone();
        MLPIndividual p2 = ((MLPIndividual) other).clone();
     
        /**/
        double p = 0.01;
        double alpha = (-p) + (int)(Math.random() * (((1+p) - (-p)) + 1));
        double beta = (-p) + (int)(Math.random() * (((1+p) - (-p)) + 1));
        
        /**/
        for(int i = 0; i < p1.cromo.genes.length; ++i)
        {
            double t = alpha * p1.cromo.genes[i] + (1-alpha) * p2.cromo.genes[i];
            double s =  beta * p2.cromo.genes[i] + (1-beta ) * p1.cromo.genes[i];
           
            p1.cromo.genes[i] = t;
            p2.cromo.genes[i] = s;
        }
        
        ind[0] = p1;
        ind[1] = p2;
        return ind;
    }
}