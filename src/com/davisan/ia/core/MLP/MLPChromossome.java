package com.davisan.ia.core.MLP;

import java.util.Arrays;



public class MLPChromossome
{
    public int[] params;
    public double[] genes;
    FuncAtivacao func;
    
    @Override
    public String toString()
    {
        return "MLPChromossome [params=" + Arrays.toString(params) + ", genes="
                + Arrays.toString(genes) + ", func=" + func + "]";
    }
    
    public MLPChromossome clone()
    {
        MLPChromossome cromo = new MLPChromossome();
        cromo.params = this.params.clone();
        cromo.genes= this.genes.clone();
        cromo.func= this.func;
        return cromo;
    }
    
    private MLPChromossome()
    {
    }

    public MLPChromossome(MultiLayerPerceptron mlp)
    {
        params = new int[3];
        params[0] = mlp.getNumEntrada();
        params[1] = mlp.getNumEscondida();
        params[2] = mlp.getNumSaida();
        func = mlp.getFunc();
        
        genes = new double[(params[0]+1)*params[1] + (params[1]+1)*params[2]];
        buildGenes(mlp);
    }

    private void buildGenes(MultiLayerPerceptron mlp)
    {
        double[][][] p = mlp.getPesos();
        
        int index = 0;
        
        for(int j=0; j < params[1]; ++j) // for(int i=0; i < params[0]+1; ++i)
        {
            for(int i=0; i < params[0]+1; ++i) // for(int j=0; j < params[1]; ++j)
            {
                genes[index++] = p[0][i][j];
            }
        }

        for(int j=0; j < params[2]; ++j) // for(int i=0; i < params[1]+1; ++i)
        {
            for(int i=0; i < params[1]+1; ++i) // for(int j=0; j < params[2]; ++j)
            {
                genes[index++] = p[1][i][j];
            }
        }
    }
    
    public MultiLayerPerceptron createMLPFromGenes() throws Exception
    {
        int numCamadas = 3;
        double[][][] p = new double[numCamadas-1][][];
        p[0] = new double[params[0]+1][params[1]];
        p[1] = new double[params[1]+1][params[2]];
        
        int index = 0;
        for(int i=0; i < params[0]+1; ++i)
        {
            for(int j=0; j < params[1]; ++j)
            {
                p[0][i][j] = genes[index++];
            }
        }
        for(int i=0; i < params[1]+1; ++i)
        {
            for(int j=0; j < params[2]; ++j)
            {
                p[1][i][j] = genes[index++];
            }
        }
        
        MultiLayerPerceptron mlp = new MultiLayerPerceptron(params[0], params[1], params[2], p);
        mlp.setFunc(func);  
        return mlp;
    }
    
    
    public static void main(String[] args)
    {
        try
        {
            double[] test1 = { 1, 2 };
            double[] out1 = new double[2];
            double[] out2 = new double[2];
            double[] out3 = new double[2];
            MultiLayerPerceptron mlp = new MultiLayerPerceptron(2,3,2);
            
            MLPChromossome cromo = new MLPChromossome(mlp);        
            MultiLayerPerceptron mlp2 = cromo.createMLPFromGenes();
            
            cromo.genes[8] = 4;
            MultiLayerPerceptron mlp3 = cromo.createMLPFromGenes();
            
            mlp.apresentarPadrao(test1, out1);
            mlp2.apresentarPadrao(test1, out2);
            mlp3.apresentarPadrao(test1, out3);
            
            System.out.println(Arrays.toString(out1));
            System.out.println(Arrays.toString(out2));
            System.out.println(Arrays.toString(out3));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
