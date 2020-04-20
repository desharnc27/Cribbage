/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.cribstats;

import java.text.DecimalFormat;

/**
 *
 * Contains useful methods for cribbage discard decision making.
 * 
 * For a user, printDiscardReport is the relevant function to call to get
 * a discard choice.
 * 
 * @author desharnc27
 */
public class DecisionMaker {

    public static void main(String args[]) {
        Labels.proceed();
        printDiscardReport(args);
    }

    /**
     * Prints discard report, which contains the expected score of every choice of
     * hand, plus or minus (depending on if player has crib or not) the expected
     * score of the resulting crib
     *
     * @param stArr String array. atArr[0] must contain "yes" ("no") if the
     * player has (does not have) the crib. Other entries of stArr are the
     * strings representing each card of the initial 6-hand. Format of a card:
     * suit-number. Example : "yes sp-3 sp-6 di-8 cl-K cl-A cl-T"
     *
     */
    public static void printDiscardReport(String[] stArr) {
        if (stArr.length == 0) {
            stArr = new String[]{"c6 s6 h6 d6 hK c9"};
        }
        if (stArr.length == 1) {
            stArr = stArr[0].split(" ");
        }
        char cHasCrib = stArr[0].charAt(0);
        boolean myCrib = false;
        if (cHasCrib == 'y') {
            myCrib = true;
        }
        String[] stArrCards = new String[stArr.length - 1];
        System.arraycopy(stArr, 1, stArrCards, 0, stArrCards.length);
        int[] ids = Labels.verboToIds(stArrCards);
        printDiscardReport(myCrib, ids);
    }

    /**
     * Prints discard report, which contains the expected score of every choice of
     * hand, plus or minus (depending on if player has crib or not) the expected
     * score of the resulting crib.
     *
     * @param myCrib true if the player has the crib, false otherwise
     * @param ids ids of the 6 initial cards
     */
    public static void printDiscardReport(boolean myCrib, int[] ids) {
        
        float [][] cribCoeffs;
        //if (Database.containsSuitedCribData()){
        cribCoeffs=Database.getCopyOfSuitedCribData(!myCrib);
        //}
        //if (cribCoeffs!=null)
        //    clearCribStatsFromUnavails(cribCoeffs,ids);
        Card chosenCards[] = new Card[4];
        Card ridCards[] = new Card[2];

        Card cards[] = new Card[6];
        for (int i = 0; i < 6; i++) {
            cards[i] = new Card(ids[i]);
        }

        String[] info = new String[15];
        int[] permArr = Aragula.idPermArray(15);
        Double[] score = new Double[15];

        int step = 0;
        for (int i = 0; i < 6; i++) {
            for (int j = i + 1; j < 6; j++) {

                int choIdx = 0;
                int rejIdx = 0;
                for (int k = 0; k < 6; k++) {
                    if (k == i || k == j) {
                        ridCards[rejIdx++] = cards[k];
                    } else {
                        chosenCards[choIdx++] = cards[k];
                    }
                }

                double handEval = computeHandAverage(chosenCards, ridCards);
                double cribEval;
                if (cribCoeffs==null){
                    //No prexisting stats will be used for calculations
                    cribEval = computeCribAverage(ridCards, chosenCards);
                } else {
                    //Prexisting crib stats in some files will be used for calculations
                    cribEval = computeCribAverage(ridCards, chosenCards,cribCoeffs);
                }
                if (!myCrib) {
                    cribEval *= -1;
                }
                score[step] = handEval + cribEval;
                info[step] = Labels.verboArr(chosenCards);
                info[step] += " " + Labels.df(2, handEval);
                info[step] += "   ///   ";
                info[step] += Labels.verboArr(ridCards);
                info[step] += " " + Labels.df(2, cribEval) + " :    ";
                info[step] += Labels.df(2, handEval + cribEval);
                step++;
            }
        }
        Aragula.quickSort(score, permArr);

        for (int i = 14; i >= 0; i--) {
            System.out.println(info[permArr[i]]);
        }

    }

    /**
     * Method that iterates on all possible 4-hands, and compute their expected
     * values.
     */
    public static void main0() {
        Labels.proceed();
        Card[] cards = new Card[52];
        for (int i = 0; i < cards.length; i++) {
            cards[i] = new Card(i);
            System.out.println(cards[i].verbos());
        }

        int[] iter = new int[]{0, 1, 2, 3};
        Card[] hand = new Card[4];
        int debugCount = 0;
        do {
            debugCount++;
            for (int i = 0; i < 4; i++) {
                hand[i] = cards[iter[i]];
            }
            int idxTaken = 0;
            int sumScore = 0;
            for (int i = 0; i < 52; i++) {
                if (idxTaken < 4 && iter[idxTaken] == i) {
                    idxTaken++;
                    continue;
                }
                Card commo = cards[i];
                int score = PointCounting.countPoints(commo, hand, false);
                sumScore += score;

            }
            if (iter[0] == 0
                    && iter[1] == 1
                    && iter[2] == 2
                    && iter[3] == 3) {
                Labels.print(hand);
                System.out.println("Total: " + sumScore / 48.0);
            }

        } while (CombMeths.combIter(iter, 52));
        System.out.println(debugCount);
    }

    /**
     * Compute the average value of a 4-card hand
     *
     * @param hand the cards of the hand
     * @param unavail cards that were already seen and cannot be flipped.
     * @return the average value of hand
     */
    public static double computeHandAverage(Card[] hand, Card[] unavail) {
        int sumScore = 0;
        int nbPoss = 0;
        boolean[] alreadyUsed = new boolean[52];
        for (Card c : hand) {
            alreadyUsed[c.getId()] = true;
        }
        for (Card c : unavail) {
            alreadyUsed[c.getId()] = true;
        }
        for (int i = 0; i < 52; i++) {
            if (alreadyUsed[i]) {
                continue;
            }
            nbPoss++;
            Card commo = new Card(i);
            int score = PointCounting.countPoints(commo, hand, false);
            sumScore += score;

        }
        return sumScore / (double) nbPoss;
    }

    /*public static boolean alreadyContains(int id, Card[] tab) {
        for (int i = 0; i < tab.length; i++) {
            if (tab[i].getId() == id) {
                return true;
            }
        }
        return false;
    }

    private static boolean alreadyContains(int id, Card[] tab, Card tab2[]) {
        for (int i = 0; i < tab.length; i++) {
            if (tab[i].getId() == id) {
                return true;
            }
        }
        return alreadyContains(id, tab2);
    }*/

    /**
     * Compute the average value of a crib containing two specific cards.
     *
     * @param myCrib the two cards of the crib
     * @param unavail cards that were already seen and can neither be the flip 
     * nor be the two other ones of the crib.
     * @return the average value of the crib. Note: the two other cards of the
     * crib and the flip are considered has uniformly random between remaining
     * available cards.
     */
    public static double computeCribAverage(Card[] myCrib, Card[] unavail) {
        int sumScore = 0;
        int nbPoss = 0;
        boolean[] alreadyUsed = new boolean[52];
        for (Card c : myCrib) {
            alreadyUsed[c.getId()] = true;
        }
        for (Card c : unavail) {
            alreadyUsed[c.getId()] = true;
        }

        for (int i = 0; i < 52; i++) {
            if (alreadyUsed[i]) {
                continue;
            }
            alreadyUsed[i] = true;

            for (int j = 0; j < 52; j++) {
                if (alreadyUsed[j]) {
                    continue;
                }
                for (int k = j + 1; k < 52; k++) {
                    if (alreadyUsed[k]) {
                        continue;
                    }
                    nbPoss++;
                    Card[] crib = new Card[4];
                    crib[0] = myCrib[0];
                    crib[1] = myCrib[1];
                    crib[2] = new Card(j);
                    crib[3] = new Card(k);
                    Card commo = new Card(i);
                    int score = PointCounting.countPoints(commo, crib, true);
                    sumScore += score;

                }
            }
            alreadyUsed[i] = false;

        }
        return sumScore / (double) nbPoss;
    }
    /**
     * Compute the average value of a crib containing two specific cards.
     *
     * @param myCrib the two cards of the crib
     * @param unavail cards that were already seen and can neither be the flip 
     * nor be the two other ones of the crib.
     * @param cribStats array containing statistics on what the other player my put in the crib
     * @return the average value of the crib. Note: the two other cards of the
     * crib and the flip are considered has uniformly random between remaining
     * available cards.
     */
    public static double computeCribAverage(Card[] myCrib, Card[] unavail, float[][] cribStats) {
        
        
        boolean[] alreadyUsed = new boolean[52];
        for (Card c : myCrib) {
            alreadyUsed[c.getId()] = true;
        }
        for (Card c : unavail) {
            alreadyUsed[c.getId()] = true;
        }
        
        int nbFlipPoss=0;
        float globalSumScore=0.0f;

        for (int i = 0; i < 52; i++) {
            if (alreadyUsed[i]) {
                continue;
            }
            nbFlipPoss++;
            alreadyUsed[i] = true;
            
            float sumScore = 0;
            float sumCoeff=0;

            for (int j = 0; j < 52; j++) {
                if (alreadyUsed[j]) {
                    continue;
                }
                for (int k = j + 1; k < 52; k++) {
                    if (alreadyUsed[k]) {
                        continue;
                    }
                    float coeff=cribStats[j][k];
                    
                    Card[] crib = new Card[4];
                    crib[0] = myCrib[0];
                    crib[1] = myCrib[1];
                    crib[2] = new Card(j);
                    crib[3] = new Card(k); 
                    Card commo = new Card(i);
                    float score = PointCounting.countPoints(commo, crib, true);
                    sumScore += score*coeff;
                    sumCoeff += coeff;

                }
            }
            globalSumScore+=sumScore / sumCoeff;
            alreadyUsed[i] = false;

        }
        return globalSumScore/nbFlipPoss;
    }

    

}
