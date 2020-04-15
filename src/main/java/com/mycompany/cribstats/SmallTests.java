/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.cribstats;

/**
 *
 * @author desharnc27
 */
public class SmallTests {

    public static void main0() {
        int[] comgen = new int[7];
        Database.createPascal();
        do {
            System.out.print(Ambitious.getIdxCode(comgen, 0) + ": ");
            println(comgen);

        } while (CombMeths.genCombIter(comgen, 13));
    }

    public static void main1() {
        int[] hand = new int[]{4, 4, 4, 4};
        int[] unavail = new int[]{1, 1};
        System.out.println("---------***-------");

        System.out.println(Ambitious.averageHandScore(hand, unavail));
        unavail[0]++;
        System.out.println(Ambitious.averageHandScore(hand, unavail));
        unavail[1]++;
        System.out.println(Ambitious.averageHandScore(hand, unavail));

    }

    public static void main2() {
        int[] cards = new int[]{5,7,8,9,12,13};
        System.out.println("---------***-------");
        
        System.out.println();
        Ambitious.getChoiceF6(cards, true);
        System.out.println("---------***-------");
        for (int i=0;i<1;i++)
            Ambitious.getChoiceF6(cards, false);

    }
    public static void main3() {
        int [] cr= new int[]{2,4};
        int [] ha= new int[]{2,4,3,3};
        System.out.println(Ambitious.twadocrinian(cr, ha, true));

    }
    public static void main4() {

        //Ambitious.bigShitt(true);
        //Database.vetoUseDataForReal=true;
        //Database.readUnsuitedCribOppFile();
        //Ambitious.bigShitt(true);
        
    }
    public static void main5() {
        //Database.readUnsuitedCribSelfFile();
        //Database.readUnsuitedCribOppFile();
        String []args = new String[]{"y di2 cl-2 d3 c4 c8 c1"};
        StatMaker.printDiscardReport(args);
    }
    public static void main6() {
       Database.megaAnalysis();
    }

    public static void main(String[] args) {
        Database.proceed();
        Labels.proceed();
        try {
            main6();
        } catch (Exception e) {
            System.out.println("here");
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
