package com.davisan.ia.core;

public class DataSet
{
    public int numEntradas;
    public int numSaidas;
    public double[][] input; // vetor de entrada de cada instancia
    public double[][] output; // vetor de resultado de cada instância
    public int[] faixaTreinamento = {0,100}; // intervalo [0, 100), fechado e aberto
    public int[] faixaValidacao = {100,200};
    public int[] faixaTeste = {200,300};
}
