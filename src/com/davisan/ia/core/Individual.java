package com.davisan.ia.core;

public interface Individual
{
    public Individual novo();
    public double fitness();
    public void mutate(double p);
    public Individual[] crossOver(Individual other);
}
