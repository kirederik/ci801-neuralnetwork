package com.davisan.ia;

import java.util.ArrayList;
import java.util.Random;

import com.davisan.ia.core.AlgoritmoSelecao;
import com.davisan.ia.core.Individual;

public class Torneio implements AlgoritmoSelecao
{
    public static int TamTorneio = 4;
    public Individual Selection(ArrayList<Individual> P)
    {
        Individual best = P.get(new Random().nextInt(P.size()));
        for(int i=1; i < 4; ++i)
        {
            Individual other;
            while ( (other = P.get(new Random().nextInt(P.size()))) == best);
            if(other.fitness() < best.fitness()) // sinal trocado
                best = other;
        }
        return best;
    }
}