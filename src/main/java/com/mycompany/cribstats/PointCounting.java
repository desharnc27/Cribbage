/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.cribstats;

import java.util.Arrays;
import java.util.Collections;

/**
 * This class contains methods for counting points of hand and crib.
 * 
 * @author desharnc27
 * 
 * 
 */
public class PointCounting {
    
    /** Counts how much points are created by 15's combinations of a set of cards
     * @param cards the cards 
     * @return the points are created by pair 15's combinations 
     */
    public static int fifteenPoints(Card[] cards) {
        int len = cards.length;
        boolean[] chosen = new boolean[len];
        int score = 0;
        do {
            int sum = 0;
            for (int i = 0; i < len; i++) {
                if (chosen[i]) {
                    sum += cards[i].numVal();
                }
            }
            if (sum == 15) {
                score += 2;
            }
        } while (CombMeths.boolIter(chosen));
        return score;
    }
    /** Counts how much points are created by pair combinations of a set of cards
     * @param cards the cards 
     * @return the points are created by pair combinations 
     */
    public static int pairPoints(Card[] cards) {
        int score = 0;
        for (int i = 0; i < cards.length; i++) {
            for (int j = i+1; j < cards.length; j++) {
                if (cards[i].getNum() == cards[j].getNum()) {
                    score += 2;
                }

            }
        }
        return score;
    }
    /**
     * Counts how much points are created by series of a set of cards
     * @param cards the cards 
     * @return the points are created by series
     */
    public static int seriePoints(Card[] arg) {
        Card[] cards = arg.clone();
        Arrays.sort(cards);
        int strikeLen = 0;
        int strikeIni = 0;
        int iniNum = 1;
        int actVal = 0;

        boolean zxcv = false;
        int r1 = 1;
        int r2 = 1;

        int idx = 0;
        while (idx < cards.length) {
            int tempNum = cards[idx].getNum();
            if (tempNum == actVal) {
                if (zxcv) {
                    r2++;
                } else {
                    r1++;
                }
                idx++;
                continue;
            }
            if (tempNum == actVal + 1) {
                if (r1 > 1) {
                    zxcv = true;
                }
                strikeLen++;
                actVal++;
                idx++;
                continue;
            }
            if (strikeLen < 3) {
                r1 = 1;
                r2 = 1;
                zxcv = false;
                strikeLen = 1;
                iniNum = tempNum;
                strikeIni = idx;
                actVal = tempNum;
                idx++;
                continue;
            }
            break;
        }
        if (strikeLen >= 3) {
            return r1 * r2 * strikeLen;
        }
        return 0;
    }
    /**
     * Returns the nob point of a combination of cards and flip, if applicable.
     * @param cards the cards
     * @param commo the flip
     * @return 1 if the hand contains a jack of the same suit of the flip
     */
    public static int nobPoints(Card commo, Card[] hand) {
        int commoSuit = commo.getSuit();
        for (int i = 0; i < hand.length; i++) {
            if (hand[i].getNum() == 11 && hand[i].getSuit() == commoSuit) {
                return 1;
            }
        }
        return 0;
    }
    /**
     * Returns flush points of a set of cards (bonus for cards of the same suit)
     * @param cards the cards
     * @param commo the flip
     * @return 5 if all cards (including the flip) are of the same suit,
     * 4 if it is a hand (not a crib) and all cards except the flip are of the same suit, 
     * 0 otherwise
     */
    public static int flushPoints(Card commo, Card[] hand,boolean isCrib) {
        int suit = hand[0].getSuit();
        for (int i = 1; i < hand.length; i++) {
            if (suit != hand[i].getSuit()) {
                return 0;
            }
        }
        if (commo.getSuit() == suit) {
            return hand.length + 1;
        }
        if (isCrib)
            return 0;
        return hand.length;

    }
    /**
     * Counts the total score of a combination
     * @param commo the flip card
     * @param hand the set of cards  in hand or crib
     * @param isCrib true if the set of cards is a hand, false if it is a crib
     * @return the total score of a combination
     */
    public static int countPoints(Card commo, Card[] hand,boolean isCrib) {
        Card[] mergeHand = merge(commo, hand);
        int score= fifteenPoints(mergeHand);
        score+= seriePoints(mergeHand);
        score+= pairPoints(mergeHand);
        score+= nobPoints(commo, hand);
        score+= flushPoints(commo, hand,isCrib);
        //System.out.println(commo+": "+score);
        return score;
    }
    /**
     * Put all the cards in one array
     * 
     * Note: does not modify the arguments.
     * 
     * @param c extra card
     * @param cards the initial array of cards
     * @return the array containing all these cards.
     */
    public static Card[] merge(Card c, Card[] cards) {
        Card[] res = new Card[cards.length + 1];
        System.arraycopy(cards, 0, res, 0, cards.length);
        res[cards.length] = c;
        return res;
    }
    
    /** Counts how much points are created by 15's combinations of a set of cards
     * @param cards the cards 
     * @return the points are created by pair 15's combinations 
     */
    public static int fifteenPoints(int[] cards) {
        int len = cards.length;
        boolean[] chosen = new boolean[len];
        int score = 0;
        do {
            int sum = 0;
            for (int i = 0; i < len; i++) {
                if (chosen[i]) {
                    int add=10;
                    if (cards[i]<add)
                        add=cards[i];    
                    sum += add;
                }
            }
            if (sum == 15) {
                score += 2;
            }
        } while (CombMeths.boolIter(chosen));
        return score;
    }
    /** Counts how much points are created by pair combinations of a set of cards
     * @param cards the cards 
     * @return the points are created by pair combinations 
     */
    public static int pairPoints(int[] cards) {
        int score = 0;
        for (int i = 0; i < cards.length; i++) {
            for (int j = i+1; j < cards.length; j++) {
                if (cards[i] == cards[j]) {
                    score += 2;
                }

            }
        }
        return score;
    }
    /**
     * Counts how much points are created by series of a set of cards
     * @param cards the cards 
     * @return the points are created by series
     */
    public static int seriePoints(int[] cards) {
        //Card[] cards = arg.clone();
        //Arrays.sort(cards);
        int strikeLen = 0;
        int strikeIni = 0;
        int iniNum = 1;
        int actVal = 0;

        boolean zxcv = false;
        int r1 = 1;
        int r2 = 1;

        int idx = 0;
        while (idx < cards.length) {
            int tempNum = cards[idx];
            if (tempNum == actVal) {
                if (zxcv) {
                    r2++;
                } else {
                    r1++;
                }
                idx++;
                continue;
            }
            if (tempNum == actVal + 1) {
                if (r1 > 1) {
                    zxcv = true;
                }
                strikeLen++;
                actVal++;
                idx++;
                continue;
            }
            if (strikeLen < 3) {
                r1 = 1;
                r2 = 1;
                zxcv = false;
                strikeLen = 1;
                iniNum = tempNum;
                strikeIni = idx;
                actVal = tempNum;
                idx++;
                continue;
            }
            break;
        }
        if (strikeLen >= 3) {
            return r1 * r2 * strikeLen;
        }
        return 0;
    }
    /**
     * Returns the nob point of a combination of cards and flip, if applicable.
     * @param cards the cards
     * @param commo the flip
     * @return 1 if the hand contains a jack of the same suit of the flip
     */
    public static float nobPoints(int commo, int []hand) {
        if (commo==11)
            return 0;
        float res=0.0f;
        for (int i = 0; i < hand.length; i++) {
            if (hand[i]== 11) {
                res+=0.25f;
            }
        }
        return res;
    }
    /**
     * Returns flush points of a set of cards (bonus for cards of the same suit)
     * @param cards the cards
     * @param commo the flip
     * @return 5 if all cards (including the flip) are of the same suit,
     * 4 if it is a hand (not a crib) and all cards except the flip are of the same suit, 
     * 0 otherwise
     */
    public static float flushPoints(int commo, int[] hand,boolean isCrib) {
        for (int i=1;i<hand.length;i++){
            if (hand[i]==hand[i-1])
                return 0;
        }
        boolean commoHope=true;
        for (int i=1;i<hand.length;i++){
            if (hand[i]==commo)
                commoHope= false;
        }
        //float probaHandSameSuit=12*11*10/((float)(51*50*49));
        float probaHandSameSuit=1/64.0f;
        if (!commoHope){
            if (isCrib)
                return 0;
            return 4*probaHandSameSuit;
            
        }
        //float probaAllSameSuit=probaHandSameSuit*9/48.0f;
        float probaAllSameSuit=probaHandSameSuit/4.0f;
        if (isCrib)
            return (5*probaAllSameSuit);
        return 4*probaHandSameSuit+probaAllSameSuit;


    }
    
    /**
     * Put all the cards in one array
     * 
     * Note: does not modify the arguments.
     * 
     * @param c extra card
     * @param cards the initial array of cards
     * @return the array containing all these cards.
     */
    public static int[] merge(int c, int[] cards) {
        int[] res = new int[cards.length + 1];
        System.arraycopy(cards, 0, res, 0, cards.length);
        res[cards.length] = c;
        return res;
    }

    public static float specialFlush(int commo, int[] crib2Cards, int j, int k, float probOfCrib2SameSuit) {
        
        //Impossible flush if existing pairs
        if (probOfCrib2SameSuit==0.0f)
            return 0.0f;
        int occurCounts[]=new int[14];
        occurCounts[crib2Cards[0]]++;
        occurCounts[crib2Cards[1]]++;       
        occurCounts[j]++;
         if (occurCounts[j]==2)
            return 0.0f;
        occurCounts[k]++;
        if (occurCounts[k]==2)
            return 0.0f;
        occurCounts[commo]++;
        if (occurCounts[commo]==2)
            return 0.0f;
        
        return 5*probOfCrib2SameSuit/64.0f;
    }
    

}
