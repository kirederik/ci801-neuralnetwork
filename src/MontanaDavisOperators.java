import java.util.Random;

import com.davisan.ia.DataSetCancer;
import com.davisan.ia.DataSetGlass;
import com.davisan.ia.GeneticAlgorithm;
import com.davisan.ia.InicializacaoDistNormal;
import com.davisan.ia.MLPIndividual;
import com.davisan.ia.core.DataSet;
import com.davisan.ia.core.FitnessMLP;
import com.davisan.ia.core.Individual;
import com.davisan.ia.core.MLP.MLPChromossome;
import com.davisan.ia.core.MLP.MultiLayerPerceptron;


public class MontanaDavisOperators
{
    public static double desvp = 5.0;
    //public static DataSet dataset = null;

    
    /***    
     *  CROSSOVER-WEIGHTS: 
     *  This operator puts a value into  each position of the child's chromosome by randomly selecting one of the  two parents and using the value in the same  position on that parent's chromosome.
     *  
     *   CROSSOVER-NODES: 
     *   For each node in the network encoded by the chi ld chromosome, this operator selects one of  the two parent's networks and finds the corresponding node  in this network. It then puts the weight of each ingoing  l i nk to the parent's node into the corresponding link of the  chi ld's network. The  intui t ion here is that networks succeed  because of the synergism between their various weights, and  this synergism is greatest among weights from ingoing links  to the same node. Therefore, as genetic matenal gets passed  around, these logical subgroups should stay together. 
     *   
     *   CROSSOVER-FEATURES: Different nodes in a neural network perform different roles. For a  ful ly connected, layered network, the role which a given node can play depends  only on which layer it is in and not on its position in that  layer. In fact, we can exchange the role of  two nodes A and  B in the same layer of a network as  fol lows. Loop over all  nodes C connected (by either an ingoing or outgoing  l i n k)  to A (and thus also to B). Exchange the weight on the  l ink  between C and A wi th that on the link between C and B.  Ignor ing the internal structure, the new network is  ident ical to the  o ld network, i.e. given the same inputs they  w i ll  produce the same outputs.
     *   
     *    H I L L C L 1 M B: 
     *    This operator calculates the gradient for  each member of the training set and sums them together to  get a total gradient. It then normalizes this gradient by  d ividing by the magnitude. The child is obtained  f rom the  parent by taking a step in the direction determined by the  normalized gradient of size step-size, where step-size is a  parameter which adapts throughout the run in the  f o l l o w i ng  way. If the evaluation of the child is worse than the parent's,  step-size is multipled by the parameter step-size-decay=0.4;  if the chi ld is better than the parent, step-size is  m u l t i p l i ed  by step-size-expand=1.4. This operator differs from backpropagation in the  f o l l owi ng ways: 1) Weights are adjusted  only after calculating the gradient for all members of the  training set and 2) The gradient is normalized so that the  step size is not proportional to the size of the gradient.   
     */
    public static Individual[] crossOver(MLPChromossome p1, MLPChromossome p2)
    {
        MontanaDavisIndividual[] result = new MontanaDavisIndividual[2];
        
       
        result[0] = new MontanaDavisIndividual();
        result[1] = new MontanaDavisIndividual();
        result[0].cromo = p1;
        result[1].cromo = p2;
        
        return result;
    }
    
    public static void mutationUnbiasedMutateWeights(MLPChromossome ind)
    {
        for(int i=0; i < ind.genes.length; ++i)
        {
            if(GeneticAlgorithm.propMutacao >= new Random().nextDouble())
            {
                ind.genes[i] = desvp *(new Random().nextGaussian());
            }
        }
        
    }
    
    public static void mutationBiasedMutateWeights(MLPChromossome ind)
    {
        for(int i=0; i < ind.genes.length; ++i)
        {
            if(GeneticAlgorithm.propMutacao >= new Random().nextDouble())
            {
                ind.genes[i] += desvp *(new Random().nextGaussian());
            }
        }
        
    }
    
    public static void mutationMutateNodes(MLPChromossome ind)
    {
        int n = 2;
        int baseindex = (ind.params[0]+1)*ind.params[1];
        for(int i=0; i < n; ++i)
        {
            int base = baseindex + new Random().nextInt(ind.params[2])*(ind.params[1]+1);
            for(int j = base; j <= base+ind.params[1]; ++j)
            {
                ind.genes[j] += desvp *(new Random().nextGaussian());
            }
        }
    }
    
    public static void mutationMutateWeakestNodes(MLPChromossome ind) throws Exception
    {        
        MLPChromossome weakest = null;
        double piorFitness = Double.MIN_VALUE;
        int xbase = 0;
        
        int totalnodes = ind.params[1]+ind.params[2];
        for(int x = 0; x < totalnodes; ++x)
        {
            MLPChromossome novoCromo = ind.clone();
            int base;
            
            if(x < ind.params[1])
            {
                base = x*(ind.params[0]+1);
                
                for(int i=base; i <= base+ind.params[0]; ++i)
                {
                    novoCromo.genes[i] = 0;
                }
            }
            else
            {
                int y = x - ind.params[1];
                base = (y*(ind.params[1]+1) + (ind.params[0]+1)*ind.params[1]);
                for(int i=base; i <= base+ind.params[1]; ++i)
                {
                    novoCromo.genes[i] = 0;
                }
            }
            double fitness = FitnessMLP.fitness(novoCromo.createMLPFromGenes(), MLPIndividual.dataset);
            
            if(weakest == null || fitness > piorFitness)
            {
                piorFitness = fitness;
                weakest = novoCromo.clone();
                xbase = x;
            }
            
        }
        
        // aplica operador no pior nó
        if(xbase < ind.params[1])
        {
            int base = xbase*(ind.params[0]+1);
            
            // aplicar nas entradas
            for(int i=base; i <= base+ind.params[0]; ++i)
            {
                ind.genes[i] += desvp *(new Random().nextGaussian());
            }

            // aplicar nas saidas
            int offset = (ind.params[0]+1)*ind.params[1];
            int maxindex = ind.genes.length;
            for(int i = base+offset; i < maxindex; i += ind.params[1]+1)
            {
                ind.genes[i] += desvp *(new Random().nextGaussian());
            }

        }
        else
        {
            int y = xbase - ind.params[1];
            int base = (y*(ind.params[1]+1) + (ind.params[0]+1)*ind.params[1]);
            for(int i=base; i <= base+ind.params[1]; ++i)
            {
                ind.genes[i] += desvp *(new Random().nextGaussian());
            }
        }
    }
    

    public static void main(String[] args)
    {
        try
        {
            MLPChromossome ind = new MLPChromossome(new MultiLayerPerceptron(9, 10, 2, new InicializacaoDistNormal(0, 5)));
            
            MLPIndividual.dataset = new DataSetCancer("bases/cancer.data", 9, 2);
            MLPIndividual.dataset.faixaTreinamento[0] = 0;
            MLPIndividual.dataset.faixaTreinamento[1] = 350;
            MLPIndividual.dataset.faixaValidacao[0] = 350;
            MLPIndividual.dataset.faixaValidacao[1] = 525;
            MLPIndividual.dataset.faixaTeste[0] = 525;
            MLPIndividual.dataset.faixaTeste[1] = 699;
            
            /*
            dataset = new DataSetGlass("bases/glass.data", 9, 7);
            dataset.faixaTreinamento[0] = 0;
            dataset.faixaTreinamento[1] = 214;
            dataset.faixaValidacao[0] = 114;
            dataset.faixaValidacao[1] = 164;
            dataset.faixaTeste[0] = 164;
            dataset.faixaTeste[1] = 214;
            */
           
            mutationMutateWeakestNodes(ind);
        }
        catch (Exception e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
