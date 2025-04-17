/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package deep_learning;

import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author kabra
 */
public class Neuron {

    private static double eta = 0.15; // 0.15
    private static double alpha = 0.5;
    private double A_outputVal;
    private int A_myIndex;
    private double A_gradient;
    private ArrayList<Connection> A_outputWeights = new ArrayList<Connection>();

    Neuron(int numOutputs, int myIndex) {
        for (int c = 0; c < numOutputs; ++c) {
            A_outputWeights.add(new Connection());
            A_outputWeights.get(A_outputWeights.size() - 1).weight = randomWeight();
        }

        A_myIndex = myIndex;
    }

    private static double transferFunction(double x) {
        return Math.tanh(x);
        //return 1 / (1 + Math.exp(-x));
    }

    private static double transferFunctionDerivative(double x) {
        return 1.0 - transferFunction(x) * transferFunction(x);
        //return transferFunction(x) * (1 - transferFunction(x));
        
    }

    private static double randomWeight() {
        Random r = new Random();
        //return 0 + (0.1 - 0) * r.nextDouble();
        return r.nextDouble();
        //return Math.random()*2 - 1;
    }

    private double sumDOW(ArrayList<Neuron> nextLayer) {
        double sum = 0.0;

        for (int n = 0; n < nextLayer.size() - 1; ++n) {
            sum += A_outputWeights.get(n).weight * nextLayer.get(n).A_gradient;
        }

        return sum;

    }

    public void setOutputVal(double val) {
        A_outputVal = val;
    }

    public double getOutputVal() {
        return A_outputVal;
    }

    public void feedForward(ArrayList<Neuron> prevLayer) {
        double sum = 0.0;

        for (int n = 0; n < prevLayer.size() - 1; ++n) {
            sum += prevLayer.get(n).getOutputVal()
                    * prevLayer.get(n).A_outputWeights.get(A_myIndex).weight; // bias also has wight?
        }
        
        sum = sum - 1;
        
        A_outputVal = transferFunction(sum);
    }

    public void calcOutputGradients(double targetVal) {
        double delta = targetVal - A_outputVal;
        A_gradient = delta * Neuron.transferFunctionDerivative(A_outputVal);
    }

    public void calcHiddenGradients(ArrayList<Neuron> nextLayer) {
        double dow = sumDOW(nextLayer);
        A_gradient = dow * Neuron.transferFunctionDerivative(A_outputVal);
    }

    public void updateInputWeights(ArrayList<Neuron> prevLayer) {
        for (int n = 0; n < prevLayer.size(); ++n) {
            Neuron neuron = prevLayer.get(n);
            double oldDeltaWeight = neuron.A_outputWeights.get(A_myIndex).deltaWeight;

            double newDeltaWeight = eta * neuron.getOutputVal() * A_gradient + alpha * oldDeltaWeight;

            neuron.A_outputWeights.get(A_myIndex).deltaWeight = newDeltaWeight;
            neuron.A_outputWeights.get(A_myIndex).weight += newDeltaWeight;
        }
    }

}
