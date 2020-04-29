/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mainpackage;



/**
 * This class represents a choice of 2 cards (only identified by their numbers, without suit)
 * 
 * @author desharnc27
 */
public class CribChoiceHeuri {
    
    /**
     * Probability that the 2 cards of this choice are of the same suit.
     */
    private final float probSameSuit;
    /**
     * The two numbers of the choice
     */
    private final int numbers []=new int[2];
    public CribChoiceHeuri(int [] t,float probSameSuit ){
        this.probSameSuit=probSameSuit;
        System.arraycopy(t,0,numbers,0,2);
        
    }
    /**
     * Adds the contribution of this choice to the weight array values
     * @param values array that contains all probability weights of pair of cards to throw in the crib
     * @param unSuitedVal same that values but unsuited
     * @param multiCoeff a priori probability of the sixtuple the choice comes from 
     */
    public void addTo(float values [][], float [][] unSuitedVal ,float multiCoeff){
        if (multiCoeff==0f)
            return;
        if (numbers[0]==numbers[1]){
            for (int i = 0; i < 4; i++) 
                for (int j = i+1; j < 4; j++){
                int cardId0=Card.suitNumToId(i, numbers[0]);
                int cardId1=Card.suitNumToId(j, numbers[1]);
                values[cardId0][cardId1]+=multiCoeff/6.0f;
            }
            unSuitedVal[numbers[0]-1][numbers[0]-1]+=multiCoeff;
            return;
        }
        
        if (probSameSuit>0.95f){
            for (int i=0;i<4;i++){
                int cardId0=Card.suitNumToId(i, numbers[0]);
                int cardId1=Card.suitNumToId(i, numbers[1]);
                if (cardId0>cardId1){
                    int temp=cardId0;
                    cardId0=cardId1;
                    cardId1=temp;
                }
                values[cardId0][cardId1]+=multiCoeff/4.0f;
            }
            unSuitedVal[numbers[1]-1][numbers[0]-1]+=multiCoeff;
            
            return;
        }
        unSuitedVal[numbers[1]-1][numbers[0]-1]+=multiCoeff*probSameSuit;
        unSuitedVal[numbers[0]-1][numbers[1]-1]+=(1-probSameSuit)*multiCoeff;
        
        float eqValue=probSameSuit*multiCoeff/4.0f;
        float diffValue=(1-probSameSuit)*multiCoeff/12.0f;
        
        
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                int cardId0=Card.suitNumToId(i, numbers[0]);
                int cardId1=Card.suitNumToId(j, numbers[1]);
                if (cardId0>cardId1){
                    int temp=cardId0;
                    cardId0=cardId1;
                    cardId1=temp;
                }
                if (i==j)
                    values[cardId0][cardId1]+=eqValue;
                else
                    values[cardId0][cardId1]+=diffValue;
                    
            }
        }
    }
    /**
     * Returns a String representation of the choice.
     * @return a String representation of the choice.
     */
    @Override
    public String toString(){
        return numbers[0]+" "+numbers[1]+"   "+this.probSameSuit;
    }
    /**
     * Returns true if this and c have the same 2 unsuited cards, false otherwise.
     * @return true if this and c have the same 2 unsuited cards, false otherwise.
     */
    boolean hasSameNumbers(CribChoiceHeuri c) {
        if (numbers[0]!=c.numbers[0])
            return false;
        if (numbers[1]!=c.numbers[1])
            return false;
        return true;
    }
            
}
