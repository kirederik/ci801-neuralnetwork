package com.davisan.ia.core;


import com.davisan.ia.DataSetCancer;
import com.davisan.ia.core.MLP.MultiLayerPerceptron;

public class FitnessMLP
{
    public static double fitness(MultiLayerPerceptron mlp, DataSet teste) throws Exception
    {
        double res = 0;
        for(int i=teste.faixaAmostra[0]; i < teste.faixaAmostra[1]; ++i) // para cada teste
        {
            double[] out = new double[teste.numSaidas];

            mlp.apresentarPadrao(teste.input[i], out);
            
            double erro = 0;
            for(int j=0; j < teste.numSaidas; ++j)
            {
                double x = teste.output[i][j] - out[j]; 
                erro += x*x;
            }
            res += erro;
        }
        
        return res / (teste.numSaidas*teste.input.length);
    }
    
    public static void main(String[] args)
    {
        try
        {
            DataSet data = new DataSetCancer("bases/cancer.data", 9, 2);
            MultiLayerPerceptron mlp = new MultiLayerPerceptron(data.numEntradas, 5, data.numSaidas);
            
            System.out.println(FitnessMLP.fitness(mlp, data));
        }
        catch (Exception e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }        
    }
}
