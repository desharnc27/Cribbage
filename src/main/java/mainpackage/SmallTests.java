/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mainpackage;

import java.util.logging.Level;
import java.util.logging.Logger;



/**
 *
 * Testing class for developer. Not for User.
 * @author desharnc27
 */
public class SmallTests {

    public static void main0() {
        int[] comgen = new int[7];
        DataForNumbers.createPascal();
        do {
            System.out.print(UnsuitedMeths.getIdxCode(comgen, 0) + ": ");
            println(comgen);

        } while (CombMeths.genCombIter(comgen, 13));
    }

    public static void main1() {
        int[] hand = new int[]{4, 4, 4, 4};
        int[] unavail = new int[]{1, 1};
        System.out.println("---------***-------");

        System.out.println(UnsuitedMeths.averageHandScore(hand, unavail));
        unavail[0]++;
        System.out.println(UnsuitedMeths.averageHandScore(hand, unavail));
        unavail[1]++;
        System.out.println(UnsuitedMeths.averageHandScore(hand, unavail));

    }

    public static void main2() {
        int[] cards = new int[]{5,7,8,9,12,13};
        System.out.println("---------***-------");
        
        System.out.println();
        UnsuitedMeths.getChoiceF6(cards, true);
        System.out.println("---------***-------");
        for (int i=0;i<1;i++)
            UnsuitedMeths.getChoiceF6(cards, false);

    }
    public static void main3() {
        int [] cr= new int[]{2,4};
        int [] ha= new int[]{2,4,3,3};
        System.out.println(UnsuitedMeths.tossProbSameSuit(cr, ha, true));

    }
    public static void main4() {

        UnsuitedMeths.analysisOfAll6Combos(true);
        
    }
    public static void main5() {
        //DataForStatFiles.readUnsuitedCribSelfFile();
        //DataForStatFiles.readUnsuitedCribOppFile();
        DataForStatFiles.loadLatestStats();
        
        String []args = new String[]{"y di2 cl-2 d3 c4 c8 c1"};
        try {
            SuitedMeths.printDiscardReport(args);
        } catch (CribbageException ex) {
        }
    }
    public static void main6() {
       DataForStatFiles.megaAnalysis(7);
    }
    public static void main7() {
       int [] cards=new int[]{1,1,4,4,10,12};
       CCHBunch c=UnsuitedMeths.getChoiceF6(cards, false);
       
    }
    public static void main8() {
       
       String currentDirectory = System.getProperty("user.dir");
       System.out.println(currentDirectory);
       
    }
    public static void main9() {
       
       DataForStatFiles.createValueFiles();
       
    }

    public static void main(String[] args) {
        DataForNumbers.proceed();
        DataForStrings.proceed();
        try {
            main6();
        } catch (Exception e) {
            System.out.println(e.getClass());
            System.out.println(e.getMessage());
            StackTraceElement[] ste = e.getStackTrace();
            for (int i = 0; i < ste.length; i++) {
                System.out.println(ste[i]);
            }
        }

    }

    public static void print(int[] tab) {
        for (int i = 0; i < tab.length; i++) {
            System.out.print(tab[i] + " ");
        }
    }

    public static void println(int[] tab) {
        print(tab);
        System.out.println();
    }
}