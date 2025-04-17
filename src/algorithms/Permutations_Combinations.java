/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithms;

/**
 *
 * @author kabra
 */
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author y08677
 */
public class Permutations_Combinations {

    public List<String> perm_with_rep_result = new ArrayList<String>();
    public List<String> perm_without_rep_result = new ArrayList<String>();
    public List<String> comb_with_rep_result = new ArrayList<String>();
    public List<String> comb_without_rep_result = new ArrayList<String>();

    public static void main(String[] args) {
        Permutations_Combinations alg = new Permutations_Combinations();

        List<String> input = new ArrayList<String>();
        //Collections.addAll(input, "g", "g", "g", "g", "g");
        input.add("a");
        input.add("b");
        input.add("c");
        input.add("d");
        input.add("e");
        alg.perm_without_rep(input, 2);

        System.out.println(alg.perm_without_rep_result.size());
        for (int i = 0; i < alg.perm_without_rep_result.size(); i++) {
            System.out.println(alg.perm_without_rep_result.get(i));

        }

    }

    void perm_with_rep(List<String> input, int length) {
        List<String> middle = new ArrayList<String>();
        Collections.addAll(middle, "");

        for (int l = 0; l < length; l++) {
            for (int i = 0; i < middle.size(); i++) {
                for (int j = 0; j < input.size(); j++) {
                    perm_with_rep_result.add(middle.get(i) + input.get(j));
                }
            }

            if (l < length - 1) {
                middle = perm_with_rep_result;
                perm_with_rep_result = new ArrayList<String>();
            }

        }

    }

    void perm_without_rep(List<String> input, int length) {
        List<String> middle = new ArrayList<String>();
        Collections.addAll(middle, "");

        for (int l = 0; l < length; l++) {
            for (int i = 0; i < middle.size(); i++) {
                for (int j = 0; j < input.size(); j++) {

                    if (!middle.get(i).contains(input.get(j))) {
                        perm_without_rep_result.add(middle.get(i) + input.get(j));
                    }

                }
            }

            if (l < length - 1) {
                middle = perm_without_rep_result;
                perm_without_rep_result = new ArrayList<String>();
            }

        }

    }

    void comb_with_rep(List<String> input, int length) {
        List<String> middle = new ArrayList<String>();
        Collections.addAll(middle, "");

        for (int l = 0; l < length; l++) {

            for (int i = 0; i < middle.size(); i++) {

                String prefix = middle.get(i);
                int startIndex = input.indexOf(prefix.isEmpty() ? input.get(0) : prefix.substring(prefix.length() - 1));

                for (int j = startIndex; j < input.size(); j++) {
                    comb_with_rep_result.add(middle.get(i) + input.get(j));
                }
            }

            if (l < length - 1) {
                middle = comb_with_rep_result;
                comb_with_rep_result = new ArrayList<String>();
            }

        }

    }

    void comb_without_rep(List<String> input, int length) {
        List<String> middle = new ArrayList<String>();
        Collections.addAll(middle, "");

        for (int l = 0; l < length; l++) {

            for (int i = 0; i < middle.size(); i++) {

                String prefix = middle.get(i);
                int startIndex = prefix.isEmpty() ? 0 : input.indexOf(prefix.substring(prefix.length() - 1)) + 1;

                for (int j = startIndex; j < input.size(); j++) {
                    comb_without_rep_result.add(middle.get(i) + input.get(j));
                }
            }

            if (l < length - 1) {
                middle = comb_without_rep_result;
                comb_without_rep_result = new ArrayList<String>();
            }

        }
    }

}
