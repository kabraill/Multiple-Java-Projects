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
public class Connection {

    double weight;
    double deltaWeight;

    public Connection() {

    }

    public double getWeight() {
        return weight;
    }

    public double getDeltaWeight() {
        return deltaWeight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public void setDeltaWeight(double deltaWeight) {
        this.deltaWeight = deltaWeight;
    }

}
