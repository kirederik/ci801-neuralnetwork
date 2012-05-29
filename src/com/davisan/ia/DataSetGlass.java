package com.davisan.ia;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;
import java.util.Scanner;

import com.davisan.ia.core.DataSet;
import com.davisan.ia.core.utils;

public class DataSetGlass extends DataSet
{
    public DataSetGlass(String arquivo, int numEntradas, int numSaidas) throws Exception
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
            scan.nextDouble(); // 
            
            double[] tmp = new double[numEntradas];
            for(int i = 0; i<numEntradas; ++i)
                tmp[i] = scan.nextDouble();
            
            double[] tmp2 = new double[numSaidas];
            int out = scan.nextInt();
            
            if(out == 1)
            {
                tmp2[0] = 1;
                tmp2[1] = 0;
                tmp2[2] = 0;
                tmp2[3] = 0;
                tmp2[4] = 0;
                tmp2[5] = 0;
                tmp2[6] = 0;
            }
            else if(out == 2)
            {
                tmp2[0] = 0;
                tmp2[1] = 1;
                tmp2[2] = 0;
                tmp2[3] = 0;
                tmp2[4] = 0;
                tmp2[5] = 0;
                tmp2[6] = 0;
            }
            else if(out == 3)
            {
                tmp2[0] = 0;
                tmp2[1] = 0;
                tmp2[2] = 1;
                tmp2[3] = 0;
                tmp2[4] = 0;
                tmp2[5] = 0;
                tmp2[6] = 0;
            }
            else if(out == 4)
            {
                tmp2[0] = 0;
                tmp2[1] = 0;
                tmp2[2] = 0;
                tmp2[3] = 1;
                tmp2[4] = 0;
                tmp2[5] = 0;
                tmp2[6] = 0;
            }
            else if(out == 5)
            {
                tmp2[0] = 0;
                tmp2[1] = 0;
                tmp2[2] = 0;
                tmp2[3] = 0;
                tmp2[4] = 1;
                tmp2[5] = 0;
                tmp2[6] = 0;
            }
            else if(out == 6)
            {
                tmp2[0] = 0;
                tmp2[1] = 0;
                tmp2[2] = 0;
                tmp2[3] = 0;
                tmp2[4] = 0;
                tmp2[5] = 1;
                tmp2[6] = 0;
            }
            else if(out == 7)
            {
                tmp2[0] = 0;
                tmp2[1] = 0;
                tmp2[2] = 0;
                tmp2[3] = 0;
                tmp2[4] = 0;
                tmp2[5] = 0;
                tmp2[6] = 1;
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
            DataSet data = new DataSetGlass("bases/glass.data", 9, 7);
            System.out.println(Arrays.toString(data.input[164]));
            System.out.println(Arrays.toString(data.output[164]));
            //for(int i=0; i < data.output.length; ++i)
            //    System.out.println(Arrays.toString(data.output[i]));
        }
        catch (Exception e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }    
    }
}