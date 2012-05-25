package com.davisan.ia.core;

import java.lang.reflect.Array;

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

}
