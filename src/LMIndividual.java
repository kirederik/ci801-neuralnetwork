import java.util.Random;

import com.davisan.ia.InicializacaoDistNormal;
import com.davisan.ia.MLPIndividual;
import com.davisan.ia.core.Individual;
import com.davisan.ia.core.MLP.MLPChromossome;
import com.davisan.ia.core.MLP.MultiLayerPerceptron;


public class LMIndividual extends MLPIndividual
{
    public static int mutationOperator = -1;
    public static int crossoverOperator = -1;
    
    public static InicializacaoDistNormal initDist = new InicializacaoDistNormal(0,5);
    
    @Override
    public String toString()
    {
        return "LMIndividual [cromo=" + cromo + "]";
    }

    public LMIndividual(int numEntrada, int numEscondida, int numSaida)
    {
        super(numEntrada, numEscondida, numSaida);
    }
    
    public LMIndividual()
    {
        super();
    }

    public Individual novo()
    {
        LMIndividual ind = new LMIndividual();
        ind.cromo = new MLPChromossome(new MultiLayerPerceptron(cromo.params[0], cromo.params[1], cromo.params[2], initDist));
        return ind;
    }
    
    public LMIndividual clone()
    {
        LMIndividual ind = new LMIndividual();
        ind.cromo = this.cromo.clone();
        return ind;
    }
    
    public void mutate()
    {
        try
        {
            int val = new Random().nextInt(6);
            
            if(mutationOperator != -1)
                val = mutationOperator;
            
            switch(val)
            {
            case 0:
                MontanaDavisOperators.mutationBiasedMutateWeights(this.cromo);
                break;
            case 1:
                MontanaDavisOperators.mutationUnbiasedMutateWeights(this.cromo);
                break;
            case 2:
                MontanaDavisOperators.mutationMutateNodes(this.cromo);
                break;
            case 3:
                MontanaDavisOperators.mutationMutateWeakestNodes(this.cromo);
                break;
            case 4:
                LiuWangLiuNiuOperators.mutationSinglePointRandom(this.cromo);
                break;
            case 5:
                LiuWangLiuNiuOperators.mutationNonUniform(this.cromo);
                break;
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
    public Individual[] crossOver(Individual other)
    {
        LMIndividual p1 = this.clone();
        LMIndividual p2 = ((LMIndividual) other).clone();
        
        int val = new Random().nextInt(4);
        
        if(crossoverOperator!= -1)
            val = crossoverOperator;
        
        switch(val)
        {
        case 0:
            return MontanaDavisOperators.crossOverWeights(p1.cromo, p2.cromo);
        case 1:
            return MontanaDavisOperators.crossOverNodes(p1.cromo, p2.cromo);
        case 2:
            return MontanaDavisOperators.crossOverFeatures(p1.cromo, p2.cromo);
        case 3:
            return LiuWangLiuNiuOperators.crossOver(p1.cromo, p2.cromo);
        }
        return MontanaDavisOperators.crossOverWeights(p1.cromo, p2.cromo);
    }
}
