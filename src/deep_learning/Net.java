/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package deep_learning;

/**
 *
 * @author kabra
 */
import java.util.ArrayList;

public class Net {
    
    private ArrayList<ArrayList<Neuron>> A_layers = new ArrayList<ArrayList<Neuron>>();
    private double A_error;
    private double A_recentAverageError;
    private static double A_recentAverageSmoothingFactor = 100.0;
    
    
    public Net(ArrayList<Integer> A_topology) {
        int numLayers = A_topology.size();
        
        for (int layerNum = 0; layerNum < numLayers; layerNum++) {
            A_layers.add(new ArrayList<Neuron>());
            int numOutputs = layerNum == A_topology.size() - 1 ? 0 : A_topology.get(layerNum + 1);
            
            for (int neuronNum = 0; neuronNum <= A_topology.get(layerNum); ++neuronNum) {
                A_layers.get(A_layers.size() - 1).add(new Neuron(numOutputs, neuronNum));
                
                System.out.println("Neuron gemacht");
                
            }
            
            //System.out.println(A_layers.get(A_layers.size() - 1).get(A_topology.get(layerNum) ).getOutputVal());
            A_layers.get(A_layers.size() - 1).get(A_layers.get(A_layers.size() - 1).size() - 1).setOutputVal(1.0);
        }
    }
    
    void feedForward(ArrayList<Double> A_inputVals) {
        int sizee = A_inputVals.size();
        
        for (int i = 0; i < sizee; ++i) {// parallel
            A_layers.get(0).get(i).setOutputVal(A_inputVals.get(i));
        }
        
        //System.out.println("bias " + A_layers.get(0).get(8).getOutputVal());
        
        for (int layerNum = 1; layerNum < A_layers.size(); ++layerNum) { //no parallel
            ArrayList<Neuron> prevLayer = A_layers.get(layerNum - 1);

            // parallel
            int sizee2 = A_layers.get(layerNum).size() - 1;
            
            for (int n = 0; n < sizee2; ++n) {
                A_layers.get(layerNum).get(n).feedForward(prevLayer);
            }
        }
        
    }
    
    void backProp(ArrayList<Double> A_targetVals) {
        ArrayList<Neuron> output_layer = A_layers.get(A_layers.size() - 1);
        A_error = 0.0;
        
        for (int n = 0; n < output_layer.size() - 1; ++n) {
            double delta = A_targetVals.get(n) - output_layer.get(n).getOutputVal();
            A_error += delta * delta;
        }
        
        A_error /= output_layer.size() - 1; // get average error squared
        A_error = Math.sqrt(A_error); // RMS

        A_recentAverageError
                = (A_recentAverageError * A_recentAverageSmoothingFactor + A_error)
                / (A_recentAverageSmoothingFactor + 1.0);

        // parallel
        int sizee = output_layer.size() - 1;
        
        for (int n = 0; n < sizee; ++n) {
            output_layer.get(n).calcOutputGradients(A_targetVals.get(n));//ja
        }
        
        for (int layerNum = A_layers.size() - 2; layerNum > 0; --layerNum) {
            ArrayList<Neuron> hiddenLayer = A_layers.get(layerNum);
            ArrayList<Neuron> nextLayer = A_layers.get(layerNum + 1);

            // parallel
            int sizee2 = hiddenLayer.size();
            
            for (int n = 0; n < sizee2; ++n) {
                hiddenLayer.get(n).calcHiddenGradients(nextLayer);//ja
            }
        }
        
        for (int layerNum = A_layers.size() - 1; layerNum > 0; --layerNum) {
            ArrayList<Neuron> layer = A_layers.get(layerNum);
            ArrayList<Neuron> prevLayer = A_layers.get(layerNum - 1);

            // parallel
            int sizee3 = layer.size() - 1;
            
            for (int n = 0; n < sizee3; ++n) {
                layer.get(n).updateInputWeights(prevLayer);// ja
            }
        }
        
    }
    
    void getResults(ArrayList<Double> A_resultVals) {
        A_resultVals.clear();
        
        for (int n = 0; n < A_layers.get(A_layers.size() - 1).size() - 1; ++n) {
            A_resultVals.add(A_layers.get(A_layers.size() - 1).get(n).getOutputVal());
        }
    }
    
    double getRecentAverageError() {
        return A_recentAverageError;
    }
    
}
