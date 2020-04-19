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
public class CCHBunch {
    private final float [] weights;
    private final CribChoiceHeuri [] choices;
    public CCHBunch(CribChoiceHeuri [] a, float [] b){
        if (a.length!=b.length)
            System.out.println("Something wiird: unmatching lengths for some CCHBunch instance");
        weights=b;
        choices=a;
        unitarize();
    }
    public final void unitarize(){
        float sum=0f;
        for (float v:weights)
            sum+=v;
        for (int i=0;i<weights.length;i++)
            weights[i]/=sum;
    }
    public CribChoiceHeuri getChoice(int i){
        return choices[i];
    }
    public float getWeight(int i){
        return weights[i];
    }
    public int getSize(){
        return weights.length;
    }

    public void addTo(float[][] arrOfScorePairs, float[][] unsuitedScorePairs, int multicoef) {
        for (int i=0;i<getSize();i++){
            choices[i].addTo(arrOfScorePairs, unsuitedScorePairs, multicoef*weights[i]);
        }
    }
    @Override
    public String toString(){
        String s="";
        
        for (int i=0;i<getSize();i++){
            if (i>0)
                s+="\n";
            s+=choices[i].toString()+", weight:"+weights[i];
            
        }
        
        return s;
    }
    
}
