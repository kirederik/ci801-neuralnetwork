package com.davisan.ia.core.MLP;

import java.util.Random;

import com.davisan.ia.core.utils;



public class MultiLayerPerceptron
{
    private int numEntrada;
    private int numEscondida;
    private int numSaida;
    
    // colocar +1 para o bias
    // camada, nuronio i, neuronio j 
    double[][][] pesos;
    
    // funcao de ativacao
    private FuncAtivacao func = new Sigmoid();
    
    public void criarRede(FuncPeso fp)
    {
        int numCamadas = 3;
        
        pesos = new double[numCamadas-1][][];
        pesos[0] = new double[numEntrada+1][numEscondida];
        pesos[1] = new double[numEscondida+1][numSaida];
        
        Random rand = new Random();
        
        for(int i=0; i < numEntrada+1; ++i)
        {
            for(int j=0; j < numEscondida; ++j)
            {
                pesos[0][i][j] = (fp != null) ? fp.peso(0, i, j) : rand.nextDouble();
                pesos[0][numEntrada][j] = 1;
            }
        }
        for(int i=0; i < numEscondida+1; ++i)
        {
            for(int j=0; j < numSaida; ++j)
            {
                pesos[1][i][j] = (fp != null) ? fp.peso(1, i, j) : rand.nextDouble();
                pesos[1][numEscondida][j] = 1;
            }
        }
    }

    public FuncAtivacao getFunc()
    {
        return func;
    }

    public void setFunc(FuncAtivacao func)
    {
        this.func = func;
    }

    public void debugRede()
    {
        System.out.println("Camamada 0");
        for(int i=0; i < numEntrada+1; ++i)
        {
            for(int j=0; j < numEscondida; ++j)
            {
                System.out.print(pesos[0][i][j] + ", ");
            }
            System.out.println("");
        }
        
        System.out.println("\nCamamada 1");
        for(int i=0; i < numEscondida+1; ++i)
        {
            for(int j=0; j < numSaida; ++j)
            {
                System.out.print(pesos[1][i][j] + "   ");
            }
            System.out.println("");
        }
    }
    

    public int getNumEntrada()
    {
        return numEntrada;
    }

    public int getNumEscondida()
    {
        return numEscondida;
    }

    public int getNumSaida()
    {
        return numSaida;
    }
    
    public void setPeso(int camada, int ni, int nj, double peso)
    {
        pesos[camada][ni][nj] = peso;
    }

    public void setPesos(double[][][] pesos) throws Exception
    {
        boolean ok = true;
        if(this.pesos.length == pesos.length)
        {
            for(int i=0; i < this.pesos.length; ++i)
            {
                if(this.pesos[i].length == pesos[i].length)
                {
                    for(int j=0; j < this.pesos[i].length; ++j)
                    {
                        if(this.pesos[i][j].length != pesos[i][j].length)
                        {
                            ok = false;
                        }
                    }
                }
                else ok = false;
            }
        }
        else ok = false;
        
        if(ok)
            this.pesos = (double[][][]) utils.copyNd(pesos);
        else
            throw new Exception("dimensoes diferentes");
    }
    
    public double[][][] getPesos()
    {
        return (double[][][]) utils.copyNd(pesos);
    }
    
    public void apresentarPadrao(double[] entrada, double[] saida) throws Exception
    {
        if(saida.length != numSaida || entrada.length != numEntrada)
            throw new Exception("dimensoes erradas");
        
        double[] intermed = new double[numEscondida];
        
        double sum = 0;
        for(int j=0; j < numEscondida; ++j)
        {
            sum = 0;
            for(int i=0; i < numEntrada; ++i)
            {
                sum += pesos[0][i][j]*entrada[i];
            }
            sum += -pesos[0][numEntrada][j]; // bias
            intermed[j] = func.ativacao(sum, 0);
        }
        
        for(int j=0; j < numSaida; ++j)
        {
            sum = 0;
            for(int i=0; i < numEscondida; ++i)
            {
                sum += pesos[1][i][j]*intermed[i];
            }
            sum += -pesos[1][numEscondida][j]; // bias
            saida[j] = func.ativacao(sum, 1);
            
            // tolerância
            saida[j] = (saida[j] > 0.9)? 1:0;
        }
    }

    public MultiLayerPerceptron(int numEntrada, int numEscondida, int numSaida)
    {
        this.numEntrada = numEntrada;
        this.numEscondida = numEscondida;
        this.numSaida = numSaida;
        criarRede(null);
    }
    
    public MultiLayerPerceptron(int numEntrada, int numEscondida, int numSaida, FuncPeso fp)
    {
        this.numEntrada = numEntrada;
        this.numEscondida = numEscondida;
        this.numSaida = numSaida;
        criarRede(fp);
    }
    
    public MultiLayerPerceptron(int numEntrada, int numEscondida, int numSaida, double[][][] pesos) throws Exception
    {
        this.numEntrada = numEntrada;
        this.numEscondida = numEscondida;
        this.numSaida = numSaida;
        criarRede(null);
        setPesos(pesos);
    }
    
    /**
     * @param args
     */
    public static void main(String[] args)
    {
        try
        {
            double[] test1 = { 1, 2 };
            double[] out = new double[2];
            
            MultiLayerPerceptron mlp = new MultiLayerPerceptron(2,8,2);
            mlp.apresentarPadrao(test1, out);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
