package com.davisan.ia.core.MLP;


public class Sigmoid implements FuncAtivacao
{
    public double ativacao(double sum, int camada)
    {
        return 1.0 / (1.0f + Math.exp(-sum));
    }
}
