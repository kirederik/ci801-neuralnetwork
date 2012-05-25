package com.davisan.ia;

import java.util.Arrays;

import com.davisan.ia.core.DataSet;
import com.davisan.ia.core.MLP.MultiLayerPerceptron;

public class TestarSaidaMLP
{
    public static int Testar(MultiLayerPerceptron mlp, DataSet dataset, int ini, int fim) throws Exception
    {
        int difs = 0;
        for(int i=ini; i < fim; ++i)
        {
            double[] out = new double[dataset.numSaidas];
            mlp.apresentarPadrao(dataset.input[i], out);
            if(!Arrays.equals(dataset.output[i], out))
                difs++;
        }
        return difs;
    }
}
