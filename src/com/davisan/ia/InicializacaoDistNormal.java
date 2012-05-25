package com.davisan.ia;

import java.util.Random;

import com.davisan.ia.core.MLP.FuncPeso;


public class InicializacaoDistNormal implements FuncPeso
{
    public double media = 0;
    public double desvpad= 1;
    private static Random rand = new Random();
    
    InicializacaoDistNormal(double media, double desvpad)
    {
        this.media = media;
        this.desvpad = desvpad;
    }
    
    @Override
    public double peso(int camada, int ni, int nj)
    {
        return media + desvpad * (rand.nextGaussian());
    }
}
