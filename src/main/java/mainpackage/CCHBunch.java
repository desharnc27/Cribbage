/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mainpackage;



/**
 * 
 * An instance of this class contains relevant discarding choices.
 * 
 * Each one is associated with a weight, which compares its relevancy to other relevant choices.
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
    /**
     * Divides all weights of the this CCHBunch by a same coefficient such that their sum becomes 1.0
     */
    public final void unitarize(){
        float sum=0f;
        for (float v:weights)
            sum+=v;
        for (int i=0;i<weights.length;i++)
            weights[i]/=sum;
    }
    /**
     * Returns the i'th discarding choice
     * @param i the index of the choice. Lower indexes are for averagely better choices.
     * @return the i'th discarding choice
     */
    public CribChoiceHeuri getChoice(int i){
        return choices[i];
    }
    /**
     * Returns the weight of the i'th discarding choice
     * @param i the index of the choice. Lower indexes are for averagely better choices.
     * @return the weight of the i'th discarding choice
     */
    public float getWeight(int i){
        return weights[i];
    }
    /**
     * Returns the number of choices in this CCHBunch
     * @return the number of choices in this CCHBunch
     */
    public int getSize(){
        return weights.length;
    }
    /**
     * Returns the number of choices in this CCHBunch
     * @param arrOfScorePairs array of suited statistics to fill
     * @param unsuitedScorePairs array of unsuited statistics to fill
     * @param multicoef a multiplicative factor, which usually derives from the likelihood of the
     * unsuited 6-combo from which this CCHBunch was found.
     */
    public void addTo(float[][] arrOfScorePairs, float[][] unsuitedScorePairs, int multicoef) {
        for (int i=0;i<getSize();i++){
            choices[i].addTo(arrOfScorePairs, unsuitedScorePairs, multicoef*weights[i]);
        }
    }
    /**
     * Returns a String representation of this CCHBunch
     * @return a String representation of this CCHBunch
     */
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
