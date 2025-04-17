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
public class Test_diabetis {

    public static void main(String[] args) throws IOException {
        Diabetis_test_train traindata = new Diabetis_test_train("diabetes.csv");

        ArrayList<Integer> topology = new ArrayList<Integer>();
        traindata.getTopology(topology);

        Net my_net = new Net(topology);

        ArrayList<Double> inputVals = new ArrayList<Double>(),
                targetVals = new ArrayList<Double>(),
                resultVals = new ArrayList<Double>();

        int trainigpass = 0; // epoche
        int epoche_max = 700; // max number of epochs
        
        int test_data = 0;
        int number_of_test_datas = 9;
        
        while (trainigpass < epoche_max) {

            while (traindata.isEof()) {

                System.out.print("\nEpoche" + trainigpass);

                if (traindata.getDatas(inputVals, targetVals) != topology.get(0)) {
                    break;
                }

                showVectorVals(": Inputs:", inputVals);
                my_net.feedForward(inputVals);

                my_net.getResults(resultVals);
                showVectorVals("Outputs:", resultVals);

                showVectorVals("Targets:", targetVals);
                //assert(targetVals.size() == topology.back());

                my_net.backProp(targetVals);

                System.out.println("Net recent average error: " + my_net.getRecentAverageError() + "\r\n");
            }
            traindata = new Diabetis_test_train("diabetes.csv");
            traindata.getTopology(topology);
            trainigpass++;

            if (trainigpass == epoche_max) {
                System.out.println("");
                System.out.println("------------------------------------------------------------------------");
                System.out.println("Testing the Datas");

                while (traindata.isEof() && test_data <= number_of_test_datas) {

                    if (traindata.getDatas(inputVals, targetVals) != topology.get(0)) {
                        break;
                    }

                    showVectorVals(": Inputs:", inputVals);
                    my_net.feedForward(inputVals);

                    my_net.getResults(resultVals);
                    showVectorVals("Outputs:", resultVals);

                    showVectorVals("Targets:", targetVals);

                    test_data++;
                    //System.out.println("Net recent average error: " + my_net.getRecentAverageError() + "\r\n");
                }

            }
        }
        traindata.close_file();
        System.out.println("\r\nDone");

    }

    static void showVectorVals(String label, ArrayList<Double> v) {
        System.out.print(label + " ");

        for (int i = 0; i < v.size(); ++i) {
            System.out.print(v.get(i) + " ");

        }

        System.out.println("");
    }

}
