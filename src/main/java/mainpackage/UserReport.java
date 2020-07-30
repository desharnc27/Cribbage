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
public class UserReport {

    private final Card[][] hand = new Card[15][4];
    private final Card[][] crib = new Card[15][2];
    private final float[] cribScore = new float[15];
    private final float[] handScore = new float[15];
    private final float[] pegScore = new float[15];

    private final Float[] targetScore = new Float[15];
    private final int[] permArr = new int[15];

    public UserReport(Card[][] hand, Card[][] crib, float[] handScore, float[] cribScore,float[] pegScore) {
        for (int i=0;i<15;i++){
            System.arraycopy(hand[i],0,this.hand[i],0,4);
            System.arraycopy(crib[i],0,this.crib[i],0,2);
        }
        //System.arraycopy(this.hand,0,hand,0,15);
        //System.arraycopy(this.crib,0,crib,0,15);
        System.arraycopy(cribScore,0,this.cribScore,0,15);
        System.arraycopy(handScore,0,this.handScore,0,15);
        System.arraycopy(pegScore,0,this.pegScore,0,15);
        
    }

    public void sortTarget(boolean considerCrib, boolean considerPeg) {
        for (int i=0;i<15;i++)
                permArr[i]=i;
        for (int i = 0; i < 15; i++) {
            targetScore[i] = handScore[i];
            if (considerCrib) {
                targetScore[i] += cribScore[i];
            }
            if (considerPeg) {
                targetScore[i] += pegScore[i];
            }
            
            
        }
        GeneralMeths.quickSort(targetScore, permArr);

    }

    public String[] computeStringArr(boolean considerCrib, boolean considerPeg) {
        
        sortTarget(considerCrib, considerPeg);
        
        
        //TODO(titles???)
        String[] res = new String[15];
        
        for (int i = 0; i < 15; i++) {
            int j = permArr[i];
            res[15-i-1] = DataForStrings.verboArr(hand[j]);
            res[15-i-1] += " || ";
            res[15-i-1] += DataForStrings.verboArr(crib[j]);
            res[15-i-1] += " :: ";
            res[15-i-1] += DataForStrings.df(2, handScore[j]);
            if (considerCrib){
                res[15-i-1] += " , " + DataForStrings.df(2, cribScore[j]);
            }                
            if (considerPeg){
                res[15-i-1] += " , " + DataForStrings.df(2, pegScore[j]);
            }
            res[15-i-1] += " ::   ";                   
            res[15-i-1] += DataForStrings.df(2, targetScore[i]);

        }
        return res;
    }
}
