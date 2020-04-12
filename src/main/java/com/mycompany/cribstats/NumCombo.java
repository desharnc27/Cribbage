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
class NumCombo {

    int commo;
    int[] hand;
    float score=0.0f;
    float scoreWithoutFlush=0.0f;
    boolean isCrib;

    public NumCombo(int a, int[] b) {
        commo = a;
        hand = b;
        isCrib=false;
        fillScore();
    }

    public final void fillScore() {
        
        if (commo==hand[0]&&commo==hand[hand.length-1]){
            //Cannot have quintuples
            return;
        }
        int[] allCards = PointCounting.merge(commo, hand);
        float res = PointCounting.fifteenPoints(allCards);
        res += PointCounting.pairPoints(allCards);
        res += PointCounting.seriePoints(allCards);
        res += PointCounting.nobPoints(commo, hand);        
        scoreWithoutFlush=res;
        res += PointCounting.flushPoints(commo, hand, false);
        score=res;
        
    }
    public void print(){
        System.out.print(commo+", ");
        for (int i=0;i<hand.length;i++){
            System.out.print(hand[i]+" ");
        }
        System.out.print(": "+score);
        System.out.println();
        
    }
}
