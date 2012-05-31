
import com.davisan.ia.InicializacaoDistNormal;
import com.davisan.ia.MLPIndividual;
import com.davisan.ia.core.Individual;
import com.davisan.ia.core.MLP.MLPChromossome;
import com.davisan.ia.core.MLP.MultiLayerPerceptron;

public class LiuWangIndividual extends MLPIndividual
{
       
    @Override
    public String toString()
    {
        return "LiuWangIndividual [cromo=" + cromo + "]";
    }

    public LiuWangIndividual(int numEntrada, int numEscondida, int numSaida)
    {
        super(numEntrada, numEscondida, numSaida);
    }
    
    public LiuWangIndividual()
    {
        super();
    }

    public Individual novo()
    {
        LiuWangIndividual ind = new LiuWangIndividual();
        ind.cromo = new MLPChromossome(new MultiLayerPerceptron(cromo.params[0], cromo.params[1], cromo.params[2], new InicializacaoDistNormal(0,5)));
        return ind;
    }
    
    public LiuWangIndividual clone()
    {
        LiuWangIndividual ind = new LiuWangIndividual();
        ind.cromo = this.cromo.clone();
        return ind;
    }

    /**/
    public void mutate()
    {
        LiuWangLiuNiuOperators.mutationSinglePointRandom(this.cromo);
        LiuWangLiuNiuOperators.mutationNonUniform(this.cromo);
    }
    /**/
    public Individual[] crossOver(Individual other)
    {
        LiuWangIndividual p1 = this.clone();
        LiuWangIndividual p2 = ((LiuWangIndividual) other).clone();
        return LiuWangLiuNiuOperators.crossOver(p1.cromo, p2.cromo);
    }/**/
}
