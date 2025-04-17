/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package deep_learning;

import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author kabra
 */
public class Test_xor {

    public static void main(String[] args) throws IOException {
        Trainingsdata_xor traindata = new Trainingsdata_xor("a.txt");

        ArrayList<Integer> topology = new ArrayList<Integer>();
        traindata.getTopology(topology);

        Net my_net = new Net(topology);

        ArrayList<Double> inputVals = new ArrayList<Double>(),
                targetVals = new ArrayList<Double>(),
                resultVals = new ArrayList<Double>();

        int trainigpass = 0; // epoche
        while (trainigpass < 4000) {

            while (traindata.isEof()) {

                System.out.print("\npass" + trainigpass);

                if (traindata.getNextInput(inputVals) != topology.get(0)) {
                    break;
                }

                showVectorVals(": Inputs:", inputVals);
                my_net.feedForward(inputVals);

                my_net.getResults(resultVals);
                showVectorVals("Outputs:", resultVals);

                traindata.getTargetOutputs(targetVals);
                showVectorVals("Targets:", targetVals);
                //assert(targetVals.size() == topology.back());

                my_net.backProp(targetVals);

                System.out.println("Net recent average error: " + my_net.getRecentAverageError() + "\r\n");
            }
            traindata = new Trainingsdata_xor("a.txt");
            traindata.getTopology(topology);
            trainigpass++;
        }
        traindata.close_file();
        System.out.println("\r\nDone");

        inputVals.clear();
        inputVals.add(1.0);
        inputVals.add(1.0);

        System.out.println(inputVals.get(0) + " ||| " + inputVals.get(1));

        my_net.feedForward(inputVals);
        my_net.getResults(resultVals);
        showVectorVals("Outputs:", resultVals);
        if (resultVals.get(0) > resultVals.get(1)) {
            System.out.println("result : " + 0);
        } else {
            System.out.println("result : " + 1);
        }

        ///-----------------------
        inputVals.set(0, 0.0);
        inputVals.set(1, 1.0);
        System.out.println(inputVals.get(0) + " ||| " + inputVals.get(1));

        my_net.feedForward(inputVals);
        my_net.getResults(resultVals);
        showVectorVals("Outputs:", resultVals);
        if (resultVals.get(0) > resultVals.get(1)) {
            System.out.println("result : " + 0);
        } else {
            System.out.println("result : " + 1);
        }

        ///-----------------------
        inputVals.set(0, 0.0);
        inputVals.set(1, 0.0);

        System.out.println(inputVals.get(0) + " ||| " + inputVals.get(1));

        my_net.feedForward(inputVals);
        my_net.getResults(resultVals);
        showVectorVals("Outputs:", resultVals);
        if (resultVals.get(0) > resultVals.get(1)) {
            System.out.println("result : " + 0);
        } else {
            System.out.println("result : " + 1);
        }

        ///-----------------------
        inputVals.set(0, 1.0);
        inputVals.set(1, 0.0);

        System.out.println(inputVals.get(0) + " ||| " + inputVals.get(1));

        my_net.feedForward(inputVals);
        my_net.getResults(resultVals);
        showVectorVals("Outputs:", resultVals);
        if (resultVals.get(0) > resultVals.get(1)) {
            System.out.println("result : " + 0);
        } else {
            System.out.println("result : " + 1);
        }

    }

    static void showVectorVals(String label, ArrayList<Double> v) {
        System.out.print(label + " ");

        for (int i = 0; i < v.size(); ++i) {
            System.out.print(v.get(i) + " ");

        }

        System.out.println("");
    }

}
