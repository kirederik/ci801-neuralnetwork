package com.davisan.ia.core;

import java.lang.reflect.Array;
import java.util.Random;

public class utils
{
    @SuppressWarnings("rawtypes")
    public static Object copyNd(Object arr) 
    {
        if (arr.getClass().isArray()) 
        {
            int innerArrayLength = Array.getLength(arr);
            Class component = arr.getClass().getComponentType();
            Object newInnerArray = Array.newInstance(component, innerArrayLength);
            //copy each elem of the array
            for (int i = 0; i < innerArrayLength; i++) {
                Object elem = copyNd(Array.get(arr, i));
                Array.set(newInnerArray, i, elem);
            }
            return newInnerArray;
        } else {
            return arr;//cant deep copy an opac object??
        }
    }

    public static void shuffle(double[][] v, double[][] o)
    {
        for(int i = v.length-1; i >= 1; --i)
        {
                int j = new Random().nextInt(i+1);
                
                double[] tmp = v[i];
                v[i] = v[j];
                v[j] = tmp;
                
                tmp = o[i];
                o[i] = o[j];
                o[j] = tmp;
        }
    }
}
