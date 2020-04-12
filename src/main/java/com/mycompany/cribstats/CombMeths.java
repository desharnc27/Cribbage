/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.cribstats;

/**
 * This method
 *
 * @author desharnc27
 */
public class CombMeths {

    /**
     * After calling C(roof,vals.length) times, iterates on all combinations of
     * vals.length integers among those in {0...roof-1}
     *
     * @param vals chosen integers of one combination
     * @param roof maximum value (excluded) a chosen integer can have
     * @return false if next iteration puts vals back to {0,1,2...}, true
     * otherwise
     */
    public static boolean combIter(int[] vals, int roof) {
        //Iterates on all combinations of vals.length numbers among those in {0...n-1}
        //Numbers in vals are in ascending order and represent the chosen numbers

        int index = vals.length - 1;
        int loose = roof - vals.length;
        while (index >= 0 && vals[index] == index + loose) {
            index--;
        }
        if (index == -1) {
            for (int i = 0; i < vals.length; i++) {
                vals[i] = i;
            }
            return false;
        }
        vals[index]++;
        int decalage = vals[index] - index;
        for (int i = index + 1; i < vals.length; i++) {
            vals[i] = decalage + i;
        }
        return true;
    }

    public static boolean genCombIter(int[] vals, int roof) {
        //Iterates on all combinations of vals.length numbers among those in {0...n-1}
        //Numbers in vals are in ascending order and represent the chosen numbers

        int index = vals.length - 1;
        //int loose = roof - vals.length;
        while (index >= 0 && vals[index] == roof - 1) {
            index--;
        }
        if (index == -1) {
            for (int i = 0; i < vals.length; i++) {
                vals[i] = 0;
            }
            return false;
        }
        int newVal = ++vals[index];
        for (int i = index + 1; i < vals.length; i++) {
            vals[i] = newVal;
        }
        return true;
    }

    /**
     * After calling tab.length! times, iterates on all permutations of
     * tab.length elements
     *
     * @param tab array representing the permutation
     * @return false if next iteration puts tab back to identity, true otherwise
     */
    public static boolean iter(int[] tab) {
        //Iterates on all permutations of {0..n-1}
        boolean found = false;
        int n = tab.length;
        int i = n - 1;
        while (!found && i > 0) {
            if (tab[i] < tab[i - 1]) {
                i--;
            } else {
                found = true;
            }
        }
        if (i == 0) {
            for (int j = 0; j < n; j++) {
                tab[j] = j;
            }
            return false;
        }
        int pos = i - 1;
        int temp = tab[pos];
        i = n - 1;
        while (tab[pos] > tab[i]) {
            i--;
        }
        tab[pos] = tab[i];
        tab[i] = temp;
        i = pos + 1;
        while (i <= (pos + n) / 2) {
            temp = tab[i];
            tab[i] = tab[n - i + pos];
            tab[n - i + pos] = temp;
            i++;
        }
        return true;

    }

    /**
     * After calling 2^tab.length times, iterates on all 2^tab.length possible
     * boolean array of length tab.length
     *
     * @param tab
     * @return false if next iteration puts tab back to false at all indexes,
     * true otherwise
     */
    public static boolean boolIter(boolean[] tab) {
        //

        int i = tab.length - 1;
        //System.out.println("i"+i);
        while (i >= 0 && tab[i]) {
            i--;
        }
        if (i < 0) {
            //overlap: return false and reset all to false
            for (i = 0; i < tab.length; i++) {
                tab[i] = false;
            }
            return false;
        }
        tab[i] = true;
        i++;
        while (i < tab.length) {
            tab[i] = false;
            i++;
        }
        return true;

    }

    /**
     *
     * @param n
     * @param k
     * @return The number of ways to choose k distinct elements from a set of n
     * elements. The formula is C(n,k)=n!/((n-k)!*k!)
     *
     *
     */
    public static int combine(int n, int k) {
        if (k < 0 || n < k) {
            System.out.println("Invalid input for combine");
        }
        if (2 * k > n) {
            k = n - k;
        }
        long res = 1;
        int num = 1 + n - k;
        int denom = 1;
        while (num <= n) {
            res = res * num / denom;
            num++;
            denom++;
        }
        return (int) res;
    }

}
