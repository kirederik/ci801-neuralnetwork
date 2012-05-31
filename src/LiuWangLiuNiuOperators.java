import java.util.Random;

import com.davisan.ia.GeneticAlgorithm;
import com.davisan.ia.core.Individual;
import com.davisan.ia.core.utils;
import com.davisan.ia.core.MLP.MLPChromossome;


public class LiuWangLiuNiuOperators
{
    static double lambda = 0.28;
    static double mutLimInf = -50;
    static double mutLimSup = 50;
    static double b = 4;

    public static Individual[] crossOver(MLPChromossome p1, MLPChromossome p2)
    {
        LiuWangIndividual[] result = new LiuWangIndividual[2];
        
        for(int i = 0; i < p1.genes.length; ++i)
        {
            if(GeneticAlgorithm.propCrossover >= new Random().nextDouble())
            {       
                lambda = 5*(new Random().nextGaussian());
                double t = lambda* p1.genes[i] + (1-lambda) * p2.genes[i];
                double s = lambda* p2.genes[i] + (1-lambda) * p1.genes[i];
                p1.genes[i] = t;
                p2.genes[i] = s;
            }
        }
        result[0] = new LiuWangIndividual();
        result[1] = new LiuWangIndividual();
        result[0].cromo = p1;
        result[1].cromo = p2;
        
        return result;
    }
    
    public static void mutationSinglePointRandom(MLPChromossome ind)
    {
        for(int i=0; i < ind.genes.length; ++i)
        {
            if(GeneticAlgorithm.propMutacao >= new Random().nextDouble())
            {
                ind.genes[i] = utils.randomRange(mutLimInf, mutLimSup);
            }
        }
    }
    
    
    private static double deltaFunc(double t, double y, double gmax)
    {
        double expo = Math.pow(1 - (t/gmax), b);
        return y * (1.0 - Math.pow(Math.random(), expo));
    }
    
    public static void mutationNonUniform(MLPChromossome ind)
    {
        for(int i=0; i < ind.genes.length; ++i)
        {
            if(GeneticAlgorithm.propMutacao >= new Random().nextDouble())
            {
                int tau = (new Random().nextInt() % 2);
                if(tau == 0)
                {
                    ind.genes[i] += deltaFunc(GeneticAlgorithm.currentg, mutLimSup -  ind.genes[i], GeneticAlgorithm.gmax); 
                }
                else
                {
                    ind.genes[i] -= deltaFunc(GeneticAlgorithm.currentg, ind.genes[i] - mutLimInf, GeneticAlgorithm.gmax );    
                }
            }
        }
    }
}
