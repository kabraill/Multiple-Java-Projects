/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package deep_learning;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author kabra
 */
public class Trainingsdata_xor {

    File file;
    Scanner reader;

    public static void main(String[] args) throws IOException {
        Trainingsdata_xor t = new Trainingsdata_xor("a.txt");
        ArrayList<Integer> topology = new ArrayList<Integer>();
        t.getTopology(topology);

        for (int i = 0; i < topology.size(); i++) {

            System.out.print(topology.get(i) + " ");

        }

    }

    public Trainingsdata_xor(String filename) {
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

    int getNextInput(ArrayList<Double> inputVals) {
        inputVals.clear();

        String data = reader.nextLine();
        String[] in_line = data.split(" ");

        //if (in_line[0].equals("in:")) {
            for (int i = 1; i < in_line.length; i++) {
                inputVals.add(Double.parseDouble(in_line[i]));
            }
        //}

        return inputVals.size();

    }

    int getTargetOutputs(ArrayList<Double> targetOutputVals) {

          targetOutputVals.clear();

        String data = reader.nextLine();
        String[] out_line = data.split(" ");

        //if (out_line[0].equals("out:")) {
            for (int i = 1; i < out_line.length; i++) {
                targetOutputVals.add(Double.parseDouble(out_line[i]));
            }
        //}

        return targetOutputVals.size();

    }
}
