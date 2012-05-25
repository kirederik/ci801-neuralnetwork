package com.davisan.ia;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;
import java.util.Scanner;

import com.davisan.ia.core.DataSet;
import com.davisan.ia.core.utils;

public class DataSetDiabetes extends DataSet
{
    public DataSetDiabetes(String arquivo, int numEntradas, int numSaidas) throws Exception
    {
        this.numEntradas = numEntradas;
        this.numSaidas = numSaidas;
        
        BufferedReader in = new BufferedReader(new FileReader(arquivo));
        
        ArrayList<double[]> dadosin = new ArrayList<double[]>();
        ArrayList<double[]> dadosout = new ArrayList<double[]>();
        
        String str;
        while( (str = in.readLine()) != null)
        {
            //str = str.replace('?', '0'); // substitui o erro
    
            Scanner scan = new Scanner(str);
            scan.useDelimiter(",");
            scan.useLocale(Locale.US);
            
            double[] tmp = new double[numEntradas];
            for(int i = 0; i<numEntradas; ++i)
                tmp[i] = scan.nextDouble();
            
            double[] tmp2 = new double[numSaidas];
            double out = scan.nextDouble();
            
            if(out == 0)
            {
                tmp2[0] = 1;
                tmp2[1] = 0;
            }
            else if(out == 1)
            {
                tmp2[0] = 0;
                tmp2[1] = 1;
            }
            else
            {
                throw new Exception("valor inválido na saída");
            }
            
            dadosin.add(tmp);
            dadosout.add(tmp2);
        }
         
        input = new double[dadosin.size()][];
        input = dadosin.toArray(input);
        
        output = new double[dadosout.size()][];
        output = dadosout.toArray(output);
        
        utils.shuffle(input, output);
    }

    
    public static void main(String[] args)
    {
        try
        {
            DataSet data = new DataSetDiabetes("bases/pima-indians-diabetes.data", 8, 2);
        }
        catch (Exception e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }    
    }
}
