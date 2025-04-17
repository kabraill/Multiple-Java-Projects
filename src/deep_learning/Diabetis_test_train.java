/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package deep_learning;

import static deep_learning.Test_diabetis.showVectorVals;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author kabra
 */
public class Diabetis_test_train {

    File file;
    Scanner reader;

    public static void main(String[] args) throws IOException {
        Diabetis_test_train traindata = new Diabetis_test_train("diabetes.csv");
        ArrayList<Integer> topology = new ArrayList<Integer>();
        traindata.getTopology(topology);

        for (int i = 0; i < topology.size(); i++) {

            System.out.print(topology.get(i) + " ");

        }

        ArrayList<Double> inputVals = new ArrayList<Double>(),
                targetVals = new ArrayList<Double>();

        File fout = new File("out.txt");
        FileOutputStream fos = new FileOutputStream(fout);

        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));

        while (traindata.isEof()) {

            if (traindata.getDatas(inputVals, targetVals) != topology.get(0)) {
                break;
            }

            //myWriter.write("in: " + inputVals.get(0) + "\r\n");
            bw.write("in:");
            
            for (int i = 0; i < inputVals.size(); i++) {
                bw.write(" " + inputVals.get(i));
            }
            
            bw.newLine();
            
            bw.write("out: " + targetVals.get(0));
            bw.newLine();

            
        }
        bw.close();
    }

    public Diabetis_test_train(String filename) {
        try {
            file = new File(filename);
            reader = new Scanner(file);

        } catch (FileNotFoundException e) {
            System.out.println("File not ");
        }

    }

    boolean isEof() {
        return reader.hasNext();

    }

    void close_file() {
        reader.close();
    }

    void getTopology(ArrayList<Integer> topology) throws IOException {

        String data = reader.nextLine();
        String[] str_topology = data.split(" ");

        for (int i = 1; i < str_topology.length; i++) {
            topology.add(Integer.parseInt(str_topology[i]));
        }

    }

    int getDatas(ArrayList<Double> inputVals, ArrayList<Double> outputVals) {
        inputVals.clear();
        outputVals.clear();

        String data = reader.nextLine();
        String[] in_line = data.split(",");

        for (int i = 0; i < in_line.length - 1; i++) {
            inputVals.add(Double.parseDouble(in_line[i]));

        }

        outputVals.add(Double.parseDouble(in_line[in_line.length - 1]));

        return inputVals.size();

    }

}
