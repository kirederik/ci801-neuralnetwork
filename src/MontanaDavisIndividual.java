import com.davisan.ia.InicializacaoDistNormal;
import com.davisan.ia.MLPIndividual;
import com.davisan.ia.core.Individual;
import com.davisan.ia.core.MLP.MLPChromossome;
import com.davisan.ia.core.MLP.MultiLayerPerceptron;


public class MontanaDavisIndividual extends MLPIndividual
{
    @Override
    public String toString()
    {
        return "MontanaDavisIndividual [cromo=" + cromo + "]";
    }

    public MontanaDavisIndividual(int numEntrada, int numEscondida, int numSaida)
    {
        super(numEntrada, numEscondida, numSaida);
    }
    
    public MontanaDavisIndividual()
    {
        super();
    }

    public Individual novo()
    {
        MontanaDavisIndividual ind = new MontanaDavisIndividual();
        ind.cromo = new MLPChromossome(new MultiLayerPerceptron(cromo.params[0], cromo.params[1], cromo.params[2], new InicializacaoDistNormal(0,5)));
        return ind;
    }
    
    public MontanaDavisIndividual clone()
    {
        MontanaDavisIndividual ind = new MontanaDavisIndividual();
        ind.cromo = this.cromo.clone();
        return ind;
    }
    
    public void mutate()
    {
        //MontanaDavisOperators.mutationBiasedMutateWeights(this.cromo);
        //MontanaDavisOperators.mutationUnbiasedMutateWeights(this.cromo);
        //MontanaDavisOperators.mutationMutateNodes(this.cromo);
        try
        {
            MontanaDavisOperators.mutationMutateWeakestNodes(this.cromo);
        }
        catch (Exception e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    /**/
    public Individual[] crossOver(Individual other)
    {
        MontanaDavisIndividual p1 = this.clone();
        MontanaDavisIndividual p2 = ((MontanaDavisIndividual) other).clone();
        //return MontanaDavisOperators.crossOver(p1.cromo, p2.cromo);
        return MontanaDavisOperators.crossOverNodes(p1.cromo, p2.cromo);
    }
    /**/
}
