package test;

import core.Card;
import core.CentralSettings;
import core.PointCounting;

/**
 *
 * @author desha
 */
public class PointsCategoryTests {

    //Add comments (also to TRAs)
    private static int compteurNH = 0;
    //private static int compteurVal = 0;

    private static int fifScore = 0;
    private static int serieScore = 0;
    private static int pairScore = 0;
    private static int nobScore = 0;
    private static int flushScore = 0;

    public static void initialize() {
        compteurNH = 0;
        //compteurVal = 0;
        fifScore = 0;
        serieScore = 0;
        pairScore = 0;
        nobScore = 0;
        flushScore = 0;
    }

    public static void computeAVG(Card[] cFixed) {
        computeAVG(cFixed, new Card[]{});
    }

    public static void computeAVG(Card[] cFixed, Card[] unavail) {
        initialize();
        System.out.print("FixedCards: ");
        for (Card c : cFixed) {
            System.out.print(c + " ");

        }

        int[] fixed = new int[cFixed.length];
        for (int i = 0; i < fixed.length; i++) {
            fixed[i] = cFixed[i].getId();
        }
        int[] hadd = new int[4 - fixed.length];
        for (int i = 0; i < hadd.length; i++) {
            hadd[i] = -1;
        }
        boolean[] taken = new boolean[52];
        for (int i = 0; i < fixed.length; i++) {
            taken[fixed[i]] = true;
        }
        for (Card unavail1 : unavail) {
            taken[unavail1.getId()] = true;
        }
        computeAVG(taken, fixed, hadd);

        System.out.print("FixedCards: ");
        for (Card c : cFixed) {
            System.out.print(c + " ");

        }
        System.out.println();
        //System.out.println("sum: "+compteurVal);
        System.out.println("nb5: " + compteurNH);
        //System.out.println("average: "+compteurVal/(double)compteurNH);

        int total = fifScore + serieScore + pairScore + nobScore + flushScore;
        System.out.println("avfif: " + fifScore / (double) compteurNH);
        System.out.println("avpair: " + pairScore / (double) compteurNH);
        System.out.println("avser: " + serieScore / (double) compteurNH);
        System.out.println("avnob: " + nobScore / (double) compteurNH);
        System.out.println("avflu: " + flushScore / (double) compteurNH);
        System.out.println("avTotal: " + total / (double) compteurNH);
        System.out.println("-----------");
    }

    public static void computeAVG(boolean[] taken, int[] fixed, int[] hadd) {
        int idxa = 0;
        while (idxa < hadd.length && hadd[idxa] >= 0) {
            idxa++;
        }
        if (idxa == hadd.length) {
            Card[] hand = new Card[4];
            for (int i = 0; i < fixed.length; i++) {
                hand[i] = new Card(fixed[i]);
            }
            for (int i = 0; i < hadd.length; i++) {
                hand[i + fixed.length] = new Card(hadd[i]);
            }
            computeHandAverage(hand, taken);
            return;
        }
        int low = 0;
        if (idxa > 0) {
            low = hadd[idxa - 1] + 1;
        }
        for (int i = low; i < 52; i++) {
            if (taken[i]) {
                continue;
            }
            taken[i] = true;
            hadd[idxa] = i;
            computeAVG(taken, fixed, hadd);
            hadd[idxa] = -1;
            taken[i] = false;

        }
    }

    public static void computeHandAverage(Card[] hand, boolean[] taken) {
        //int sumScore = 0;
        //int nbPoss = 0;
        boolean[] alreadyUsed = taken;//new boolean[52];
        //for (Card c : hand) {
        //    alreadyUsed[c.getId()] = true;
        //}
        for (int i = 0; i < 52; i++) {
            if (alreadyUsed[i]) {
                continue;
            }
            //nbPoss++;
            Card commo = new Card(i);
            countPoints(commo, hand, true);
            //sumScore += score;

        }
        //compteurNH+=nbPoss;
        //compteurVal+=sumS
    }

    public static void countPoints(Card commo, Card[] hand, boolean isCrib) {
        //if (debugPoints)
        //    return 42;
        Card[] mergeHand = PointCounting.merge(commo, hand);
        fifScore += PointCounting.fifteenPoints(mergeHand);
        serieScore += PointCounting.seriePoints(mergeHand);
        pairScore += PointCounting.pairPoints(mergeHand);
        nobScore += PointCounting.nobPoints(commo, hand);
        flushScore += PointCounting.flushPoints(commo, hand, isCrib);
        compteurNH++;
        //int total = fifScore + serieScore + pairScore + nobScore + flushScore;
        //return total;
    }

    public static void main0() {

        Card[] start = new Card[]{
            new Card(0, 2),
            new Card(1, 3),};
        computeAVG(start);

        start = new Card[]{
            new Card(0, 6),
            new Card(1, 9),};
        computeAVG(start);
        start = new Card[]{
            new Card(0, 4),
            new Card(1, 4),};
        Card[] unavail = new Card[]{
            new Card(0, 8),
            new Card(1, 5),
            new Card(2, 5),
            new Card(3, 11),};
        computeAVG(start);
        computeAVG(start, unavail);
    }

    public static void main1() {
        Card[] start = new Card[]{
            new Card(0, 2), //new Card(0,2),
        //new Card(0,2),
        };
        for (int i = 1; i < 14; i++) {
            start[0] = new Card(0, i);
            //start[1]=new Card(1,i+1);
            //start[1]=new Card(2,i);
            computeAVG(start);
        }
    }

    public static void main2() {
    }

    public static void main(String[] args) {
        CentralSettings.doPreOperations();
        main1();
    }
}
