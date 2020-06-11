/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mainpackage;

/**
 *
 * @author desharnc27
 */

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * This class contains other basic methods used in this project.
 * 
 * @author desharnc27
 */
public class GeneralMeths {

    /**
     * Performs print if bool==true
     * @param bool a boolean
     * @param s the string to print
     */
    public static void printIf(boolean bool, String s){
        if (bool)
            System.out.print(s);
    }
    /**
     * Performs print if bool==true
     * @param bool a boolean
     * @param s the string to print
     */
    public static void printlnIf(boolean bool, String s){
        if (bool){
            System.out.print(s);
            System.out.println();
        }
    }
    

    /**
     * This method swaps two elements in an array of int
     *
     * @param tab An array of int
     * @param i index from which an element is swapped
     * @param j index from which an element is swapped
     */
    public static void swap(int[] tab, int i, int j) {
        int temp = tab[i];
        tab[i] = tab[j];
        tab[j] = temp;
    }
    /**
     * Swaps two elements in an array 
     *
     * 
     * @param <T> any class
     * @param tab an array 
     * @param i index from which an element is swapped
     * @param j index from which an element is swapped
     */
    public static <T> void swap(T [] tab, int i, int j) {
        T temp = tab[i];
        tab[i] = tab[j];
        tab[j] = temp;
    }

    /**
     * UnwantedJavadoc
     * @param arr
     * @param low
     * @param high
     * @return
     */
    private static int partition(int arr[], int low, int high) {
        // pivot (Element to be placed at right position)
        // The reason why we want the pivot to be chosen initially
        // in the middle is because if arr is close to be already
        // sorted, it is probably the best pivot
        swap(arr,high,(low+high)/2);
        int pivot = arr[high];

        int i = (low - 1); // Index of smaller element

        for (int j = low; j <= high - 1; j++) {
            // If current element is smaller than or
            // equal to pivot
            if (arr[j] <= pivot) {
                i++;    // increment index of smaller element
                swap(arr, i, j);
            }
        }
        swap(arr, i + 1, high);
        return (i + 1);
    }

    /**
     * UnwantedJavadoc
     * @param arr
     * @param low
     * @param high
     */
    private static void quickSort(int arr[], int low, int high) {
        if (low < high) {
            /* pi is partitioning index, arr[pi] is now
	           at right place */
            int pi = partition(arr, low, high);

            quickSort(arr, low, pi - 1);  // Before pi
            quickSort(arr, pi + 1, high); // After pi
        }
    }

    /**
     * Sorts an array of int by usual quickSort
     *
     * @param arr
     */
    public static void quickSort(int arr[]) {
        quickSort(arr, 0, arr.length - 1);
    }

    /**
     * UnwantedJavadoc
     * @param <T>
     * @param arr
     * @param low
     * @param high
     * @return
     */
    private static <T extends Comparable<T>> int partition(T arr[], int low, int high) {
        
        // pivot (Element to be placed at right position)
        // The reason why we want the pivot to be chosen initially
        // in the middle is because if arr is close to be already
        // sorted, it is probably the best pivot
        swap(arr,high,(low+high)/2);
        T pivot = arr[high];

        int i = (low - 1); // Index of smaller element

        for (int j = low; j <= high - 1; j++) {
            // If current element is smaller than or
            // equal to pivot
            if (arr[j].compareTo(pivot) < 0) {
                i++;    // increment index of smaller element
                swap(arr, i, j);
            }
        }
        swap(arr, i + 1, high);
        return (i + 1);
    }

    /**
     * UnwantedJavadoc
     * @param <T>
     * @param arr
     * @param low
     * @param high
     */
    private static <T extends Comparable<T>> void quickSort(T arr[], int low, int high) {
        if (low < high) {
            /* pi is partitioning index, arr[pi] is now
	           at right place */
            int pi = partition(arr, low, high);

            quickSort(arr, low, pi - 1);  // Before pi
            quickSort(arr, pi + 1, high); // After pi
        }
    }

    /**
     * This method sorts an array by usual quick sort
     *
     * @param <T>
     * @param arr
     */
    public static <T extends Comparable<T>> void quickSort(T[] arr) {
        quickSort(arr, 0, arr.length - 1);
    }

    /**
     * UnwantedJavadoc
     * @param arr
     * @param low
     * @param high
     * @param ranking
     * @return
     */
    private static int partition(int arr[], int low, int high, int ranking[]) {
        // pivot (Element to be placed at right position)
        int pivot = arr[high];

        int i = (low - 1); // Index of smaller element

        for (int j = low; j <= high - 1; j++) {
            // If current element is smaller than or
            // equal to pivot
            if (arr[j] <= pivot) {
                i++;    // increment index of smaller element
                swap(arr, i, j);
                swap(ranking, i, j);
            }
        }
        swap(arr, i + 1, high);
        swap(ranking, i + 1, high);
        return (i + 1);
    }

    /**
     * UnwantedJavadoc
     * @param arr
     * @param low
     * @param high
     * @param ranking
     */
    private static void quickSort(int arr[], int low, int high, int ranking[]) {
        if (low < high) {
            /* pi is partitioning index, arr[pi] is now
	           at right place */
            int pi = partition(arr, low, high, ranking);

            quickSort(arr, low, pi - 1, ranking);  // Before pi
            quickSort(arr, pi + 1, high, ranking); // After pi
        }
    }

    /**
     * This method sorts an array of int (usual quickSort) It also keeps track
     * of the movement of the elements. ranking[i] contains the initial index of
     * the element now at index i in arr
     *
     * @param arr Array to sort
     * @param ranking Array containing the initial positions of elements.
     */
    public static void quickSort(int arr[], int[] ranking) {
        quickSort(arr, 0, arr.length - 1, ranking);
    }
    
    private static <T extends Comparable<T>> int partition(T arr[], int low, int high,int ranking[]) {
        
        // pivot (Element to be placed at right position)
        // The reason why we want the pivot to be chosen initially
        // in the middle is because if arr is close to be already
        // sorted, it is probably the best pivot
        swap(arr,high,(low+high)/2);
        swap(ranking,high,(low+high)/2);
        T pivot = arr[high];

        int i = (low - 1); // Index of smaller element

        for (int j = low; j <= high - 1; j++) {
            // If current element is smaller than or
            // equal to pivot
            if (arr[j].compareTo(pivot) < 0) {
                i++;    // increment index of smaller element
                swap(arr, i, j);
                swap(ranking, i, j);
                
            }
        }
        swap(arr, i + 1, high);
        swap(ranking, i + 1, high);
        return (i + 1);
    }

    /**
     * UnwantedJavadoc
     * @param <T>
     * @param arr
     * @param low
     * @param high
     */
    private static <T extends Comparable<T>> void quickSort(T arr[], int low, int high, int [] ranking) {
        if (low < high) {
            /* pi is partitioning index, arr[pi] is now
	           at right place */
            int pi = partition(arr, low, high,ranking);

            quickSort(arr, low, pi - 1,ranking);  // Before pi
            quickSort(arr, pi + 1, high,ranking); // After pi
        }
    }

    /**
     * This method sorts an array by usual quick sort
     *
     * @param <T>
     * @param arr
     */
    public static <T extends Comparable<T>> void quickSort(T[] arr, int ranking []) {
        quickSort(arr, 0, arr.length - 1,ranking);
    }

    /**
     *
     * @param scoreTab scoreTab[i] contains score of element i
     * @return Returns an array containing elements in decreasing order of
     * scores given by scoreTab.
     *
     */
    public static int[] ranks(int[] scoreTab) {
        int n = scoreTab.length;
        int[] res = idPermArray(n);
        int[] scoreTab2 = getCopy(scoreTab);

        quickSort(scoreTab2, res);
        reverseOrder(res);
        return res;
    }

    /**
     * Reverses order of the elements in an array of int.
     *
     * @param tab Array to reverse
     */
    public static void reverseOrder(int[] tab) {
        //Reverses the order of elements in an array
        int n = tab.length;
        for (int i = 0; i < n / 2; i++) {
            swap(tab, i, n - i - 1);
        }
    }

    /**
     * Returns a copy of an array of int
     * @param tab
     * @return a copy of tab
     */
    public static int[] getCopy(int[] tab) {
        int[] res = new int[tab.length];
        System.arraycopy(tab, 0, res, 0, res.length);
        return res;
    }

    /**
     * Returns a copy of a 2D array of int
     * @param tab
     * @return a copy of tab
     */
    public static int[][] getCopy(int[][] tab) {
        int[][] res = new int[tab.length][];
        for (int i = 0; i < res.length; i++) {
            res[i] = getCopy(tab[i]);
        }
        return res;
    }

    /**
     * Randomly shuffle all values in tab
     *
     * @param tab
     */
    public static void randomSwaps(int[] tab) {
        //Randomly swaps all values in tab
        for (int i = 0; i < tab.length - 1; i++) {
            int j = (int) (Math.random() * (tab.length - i));
            j += i;
            int temp = tab[i];
            tab[i] = tab[j];
            tab[j] = temp;
        }
    }

    /**
     * Returns an array representing an identity permutation (that is an array
     * where the value at any index is the index itself)
     * @param n the length of the returned permutation.
     * @return an array representing an identity permutation (that is an array
     * where the value at any index is the index itself).
     */
    public static int[] idPermArray(int n) {
        int[] perm = new int[n];
        for (int i = 0; i < n; i++) {
            perm[i] = i;
        }
        return perm;
    }

    /**
     * Returns a random permutation array (that is an array of
     * int containing all numbers of interval [0,n-1] in a random order).
     * @param n length of the permutation array.
     * @return an array representing a random permutation (that is an array of
     * int containing all numbers of interval [0,n-1] in a random order).
     */
    public static int[] randomPermArray(int n) {
        int[] perm = idPermArray(n);
        randomSwaps(perm);
        return perm;

    }

    /**
     * Returns an array containing random permutation arrays
     * @param card the number of permutations
     * @param n the length of the permutations
     * @return an array containing random permutation arrays
     * @see randomPermArray
     */
    public static int[][] randomPermSetArray(int card, int n) {
        int[][] res = new int[card][];
        for (int i = 0; i < card; i++) {
            res[i] = randomPermArray(n);
        }
        return res;
    }

    /**
     * Sets all values of an array tab to val
     *
     * @param tab an array
     * @param val the value to set in all tab
     */
    public static void setTab(int[] tab, int val) {
        for (int i = 0; i < tab.length; i++) {
            tab[i] = val;
        }
    }

    /**
     * Sets all values of an array tab to val
     *
     * @param tab an array
     * @param val the value to set in all tab
     */
    public static void setTab(boolean[] tab, boolean val) {
        for (int i = 0; i < tab.length; i++) {
            tab[i] = val;
        }
    }

    /**
     * Returns the inverse of tab (if i is at index j in tab, then j is at index
     * i in tab)
     *
     * @param tab An array that must be representing a permutation (otherwise it
     * may throw an ArrayIndexOutOfBoundsException)
     * @return The inverse of tab
     */
    public static int[] inverse(int[] tab) {
        int[] res = new int[tab.length];
        for (int i = 0; i < tab.length; i++) {
            res[tab[i]] = i;
        }
        return res;
    }

    /**
     * Returns an array containing inverses of the arrays of tab
     * @param tab An array containing arrays that must be permutation arrays
     * @return An array containing inverses of the arrays of tab
     * @see inverse(int[] tab)
     */
    public static int[][] inverse(int[][] tab) {
        int res[][] = new int[tab.length][];
        for (int i = 0; i < tab.length; i++) {
            res[i] = inverse(tab[i]);
        }
        return res;
    }

    //TODO

    /**
     * Returns the number of inversions in a permutation array 
     * @param perm
     * @return the number of inversions in a permutation array (which is the number of pairs 
     *  i&lt j such that perm[i]&gt perm[j]
     */
    
    public static int nbOfInv(int[] perm) {

        int res = 0;
        for (int i = 0; i < perm.length; i++) {
            for (int j = 0; j < i; j++) {
                if (perm[i] < perm[j]) {
                    res++;
                }
            }
        }
        return res;
    }


    
    /*public static int nbOfInv(int[] perm) {
        //counts the number of inversion, i.e. dkt(perm,idPerm)
        return cpNumber(inverse(perm));
    }*/

    /**
     * Returns the parity of the number of inversions in the permutation
     * represented by perm
     *
     * @param perm
     * @return The parity of the number of inversions in the permutation
     * represented by perm
     */
    public static int detSg(int[] perm) {
        //returns the sign of a permutation
        return (nbOfInv(perm) % 2);
    }

    /**
     * Returns the array representing function composition q o p if lengths
     * match, 0 otherwise
     *
     * @param p a permutation array which values must also be valid indexes.
     * @param q a permutation array
     * @return The array representing function composition q o p if lengths
     * match, 0 otherwise
     * @throws ArrayIndexOutOfBoundsException if a value in p is not an index of
     * q
     */
    public static int[] compo(int[] p, int[] q) {
        //Returns the array representing function composition q o p
        int n = p.length;
        if (n != q.length) {
            return null;
        }
        int[] res = new int[n];
        for (int i = 0; i < n; i++) {
            res[i] = p[q[i]];
        }
        return res;
    }

    /**
     * Returns the exp'th power of the function represented by perm
     *
     * @param fct an array representing a function that maps i to perm[i] for
     * all i in [0,perm.length-1] (all values of perm must also be in that
     * interval to avoid aioobe exceptions)
     * @param exp the exponent
     * @return The exp'th power of the function represented by perm
     *
     *
     */
    public static int[] pow(int[] fct, int exp) {
        if (exp < 2) {
            if (exp<0)
                return pow(inverse(fct), -exp);
            else if (exp==1)
                return fct;
            else{
                int [] res =new int[fct.length];
                Arrays.fill(res,1);
                return res;
            }
                
        }
        /*int[] res = idPermArray(fct.length);
        for (int j = 0; j < exp; j++) {
            for (int i = 0; i < fct.length; i++) {
                res[i] = fct[res[i]];
            }
        }*/
        int [] sqrt= pow(fct,exp/2);
        if (exp%2!=0)
            return compo(fct,compo(sqrt,sqrt));
        else
            return compo(sqrt,sqrt);
    }

    /**
     * Returns an array res such that res[i]=pow(set[i],exp) for all i in
     * [0,set.length-1]
     *
     * @param set
     * @param exp
     * @return An array res such that res[i]=pow(set[i],exp) for all i in
     * @see pow(int[] perm, int exp)
     */
    public static int[][] pow(int[][] set, int exp) {
        int card = set.length;
        int res[][] = new int[card][];

        for (int i = 0; i < card; i++) {
            res[i] = pow(set[i], exp);
        }
        return res;
    }

    /**
     * Returns true if values in cycle appears in its periodic order in tab, false otherwise.
     * More precisely, let us define pInv=inverse(tab). Then, the method returns true if and only if only one of these inequalities is false: 
     * pInv[cycle[0]]&lt pInv[cycle[1]]&lt pInv[cycle[2]]&lt ... pInv[cycle[cycle.length-1]]&lt pInv[cycle[0]].
     * @param tab a permutation array
     * @param cycle an array that must contain valid indexes of tab
     * @return true if values in cycle appears in its periodic order in tab, false otherwise.
     */
    public static boolean respectCycle(int[] tab, int[] cycle) {
        int it = 0;
        int n = tab.length;
        while (it < n && tab[it] != cycle[0]) {
            it++;
        }
        if (it == n) {
            return false;
        }
        if (cycle.length < 2) {
            return true;
        }
        int end = it;
        int it2 = 1;
        it = (it + 1) % n;
        while (it != end && it2 < cycle.length) {
            if (tab[it] == cycle[it2]) {
                it2++;
            }
            it = (it + 1) % n;
        }
        return it2 == cycle.length;
    }

    /**
     * Returns true if respectCycle(tab, cycle) return true for all cycle in cycles
     * @param tab
     * @param cycles
     * @return true if respectCycle(tab, arr) return true for all arr in cycles
     * @see respectCycles(int[] tab, int[] cycles)
     */
    public static boolean respectCycles(int[] tab, int[][] cycles) {
        for (int[] cycle : cycles) {
            if (!respectCycle(tab, cycle)) {
                return false;
            }
        }
        return true;
    }

    /*public static boolean respectCycle( int []tab,int [] cycle){
		int it=0;
		int n=tab.length;
		while (it<n &&tab[it]!=cycle[0]) it++;
		if (it==n) return false;
		if (cycle.length<2) return true;
		int end=it;
		int it2=1;
		it=(it+1)%n;
		while (it!=end && it2<cycle.length){
			if (tab[it]==cycle[it2]) it2++;
			it=(it+1)%n;
		}
		if (it2==cycle.length) return true;
		return false;
	}
	public static boolean respectOrder( int []tab,int [][] order){
		for (int i=0;i<order.length;i++) 
			if (!respectOrder(tab, order[i]))
				return false;
		return true;
	}*/
    /**
     * Returns a copy of an array of int
     *
     * @param tab
     * @return a copy of an array of int
     */
    public static int[] copy(int[] tab) {
        int[] res = new int[tab.length];
        System.arraycopy(tab, 0, res, 0, tab.length);
        return res;
    }

    /**
     * Put all elements of src in dest if lengths match, does nothing otherwise
     *
     * @param src the array from which values are copied
     * @param dest the array in which values are copied
     */
    public static void copyIn(int[] src, int[] dest) {
        int s1 = src.length;
        int s2 = dest.length;
        if (s1 != s2) {
            System.out.println("Lengths do not match");
            return;
        }
        System.arraycopy(src, 0, dest, 0, s1);
    }

    /**
     * Returns 1 if i if positive, 0 if i==0, -1 otherwise
     *
     * @param i
     * @return 1 if i if positive, 0 if i==0, -1 otherwise
     */
    public static int sign(int i) {
        if (i > 0) {
            return 1;
        }
        if (i == 0) {
            return 0;
        }
        return -1;
    }

    /**
     * Returns a copy of list
     *
     * @param <T>
     * @param list
     * @return A copy of list
     */
    public static <T> ArrayList<T> copy(ArrayList<T> list) {
        ArrayList<T> res = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            res.add(list.get(i));
        }
        return res;

    }

    /**
     * Converts an array of int to an arrayList of Integer
     *
     * @param tab
     * @return an ArrayList which contains tab's values in the same order
     */
    public static ArrayList<Integer> toArrayList(int[] tab) {
        ArrayList<Integer> list = new ArrayList<>();
        for (int i = 0; i < tab.length; i++) {
            list.add(tab[i]);
        }
        return list;

    }
    /**
     * Converts an array into an arrayList
     *
     * @param tab
     * @return the array tab as an ArrayList
     */
    public static <T> ArrayList<T> toArrayList(T[] tab) {
        ArrayList<T> list = new ArrayList<>();
        for (int i = 0; i < tab.length; i++) {
            list.add(tab[i]);
        }
        return list;

    }
    /*
     * Converts an arrayList into an array
     *
     * @param list
     * @return the arrayList list as an array
     
    public static <T>  T[] toArrayList(ArrayList<T> list) {
        T[] arr = new T[list.size()];
        for (int i = 0; i < arr.length; i++) {
            arr[i]=list.get(i);
        }
        return arr;

    }*/
    
    public static String fileToString(String filename){

        File file;
        BufferedReader br;
        file = new File(filename);
        String line;
        String res="";
        try {
            br = new BufferedReader(new FileReader(file));
            while ((line = br.readLine()) != null) {
                res+=line+"\n";

            }

        } catch (FileNotFoundException ex) {
            return ("Error: the file"+file+" is missing");
        } catch (IOException ex) {
            return ("Error: the file"+file+" has an unknown IO problem");
       
        }
        return res;
    }


}
