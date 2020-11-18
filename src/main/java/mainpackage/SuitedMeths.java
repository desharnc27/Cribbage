/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mainpackage;


import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.util.Arrays;

/**
 *
 * Contains useful methods for cribbage discard decision making.
 * 
 * For a user, printDiscardReport is the relevant function to call to get
 * a discard choice.
 * 
 * @author desharnc27
 */
public class SuitedMeths {
    
    private static boolean debugEqualizer=false;
    
    public static void main(String args[]) throws CribbageException {
        DataForStrings.proceed();
        printDiscardReport(args);
    }
    
    public static UserReport printDiscardReport(String[] stArr) throws CribbageException {
        return printDiscardReport(stArr,true);
    }

    /**
     * Prints discard report, which contains the expected score of every choice of
     * hand, plus or minus (depending on if player has crib or not) the expected
     * score of the resulting crib.TODOtemp: now prints both 1 with stats use 2 without stat use
     * 
     *
     * @param stArr String array. atArr[0] must contain "yes" ("no") if the
     * player has (does not have) the crib. Other entries of stArr are the
     * strings representing each card of the initial 6-hand. Format of a card:
     * suit-number. Example : "yes sp-3 sp-6 di-8 cl-K cl-A cl-T"
     * @throws mainpackage.CribbageException
     *
     */
    public static UserReport printDiscardReport(String[] stArr,boolean statUse) throws CribbageException {
        if (stArr.length == 0 || stArr[0].length()<0) {
            //the input is no defined
            //String message="no initiallization (no input)";
            String message=Langu.smallText("errorNoInput");
            throw new CribbageException(message,true);
        }
        if (stArr.length == 1) {
            stArr = stArr[0].split(",");
        }
        char cHasCrib = stArr[0].charAt(0);
        boolean myCrib = false;
        if (cHasCrib == 'y') {
            myCrib = true;
        }else if(cHasCrib != 'n'){
            String message=Langu.smallText("errorLeftParam");
            //String message="the leftmost paramter must be y or n, depending on if you have the crib";
            throw new CribbageException(message);
        }
        String[] stArrCards = new String[stArr.length - 1];
        System.arraycopy(stArr, 1, stArrCards, 0, stArrCards.length);
        int[] ids = DataForStrings.verboToIds(stArrCards);
        
        if (ids.length!=6){
            //String message="the number of cards must be exactly 6";
            String message=Langu.smallText("errorNbCards");
            throw new CribbageException(message);
        }
        for(int i=0;i<ids.length;i++){
            if (ids[i]==-1){
                String message=Langu.smallTextX("errorUnknownCard",new String[]{stArrCards[i]});
                throw new CribbageException(message);
            }
        }
        for (int i=0;i<6;i++)
            for (int j=1+i;j<6;j++){
                if (ids[i]==ids[j]){
                    String message=Langu.smallTextX("errorDuplicate",new int[]{i,j});;
                    throw new CribbageException(message);
                }
            }
        
        //debugEqualizer=false;
        //printDiscardReport(myCrib, ids,true);
        //System.out.println("-------------");
        //System.out.println("-------------");
        //debugEqualizer=true;
        if (statUse){
            System.out.println("Analysis using discarding behavior statistics:");
            return printDiscardReport(myCrib, ids,true);
        }else{
            System.out.println("Analysis using no previous statistics:");
            return printDiscardReport(myCrib, ids,false);
        }
        
        
        
    }
    /**
     * Prints discard report, which contains the expected score of every choice of
     * hand, plus or minus (depending on if player has crib or not) the expected
     * score of the resulting crib.
     *
     * @param myCrib true if the player has the crib, false otherwise
     * @param ids ids of the 6 initial cards
     * @param useStats false if the user wants the program to assume that the opponent puts
     * random cards (uniformly) in the crib, true if the program uses statistics 
     * on the opponent's discarding behavior.
     */
    public static UserReport printDiscardReport(boolean myCrib, int[] ids,boolean useStats) {
        if (!useStats){
            return printDiscardReport(myCrib, ids, null);
        }
        //DataForStatFiles.loadLatestStats();
        
        float [][] cribCoeffs=DataForStatFiles.getCopyOfSuitedCribData(!myCrib);
        return printDiscardReport(myCrib, ids, cribCoeffs);
    }

    /**
     * Prints discard report, which contains the expected score of every choice of
     * hand, plus or minus (depending on if player has crib or not) the expected
     * score of the resulting crib.
     *
     * @param myCrib true if the player has the crib, false otherwise
     * @param ids ids of the 6 initial cards
     * @param cribCoeffs statistics on opponent's discarding behavior, null if unused
     */
    public static UserReport printDiscardReport(boolean myCrib, int[] ids, float [][] cribCoeffs) {
        
        //if (DataForStatFiles.containsSuitedCribData()){
        
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
        //int[] permArr = GeneralMeths.idPermArray(15);
        //Double[] score = new Double[15];
        
        Card[][] handMem = new Card[15][4];
        Card[][] cribMem = new Card[15][2];
        float[] cribEval = new float[15];
        float[] handEval = new float[15];
        float[] pegEval = new float[15];

        int step = 0;
        for (int i = 0; i < 6; i++) {
            for (int j = i + 1; j < 6; j++) {

                int choIdx = 0;
                int rejIdx = 0;
                for (int k = 0; k < 6; k++) {
                    if (k == i || k == j) {
                        cribMem[step][rejIdx++]=cards[k];
                        //ridCards[rejIdx++] = cards[k];
                    } else {
                        handMem[step][choIdx++]= cards[k];
                        //chosenCards[choIdx++] = cards[k];
                    }
                }
                ridCards=cribMem[step];
                chosenCards=handMem[step];

                handEval[step] = (float)computeHandAverage(chosenCards, ridCards);
                if (cribCoeffs==null){
                    //No prexisting stats will be used for calculations
                    cribEval[step] = (float)computeCribAverage(ridCards, chosenCards);
                } else {
                    //Prexisting crib stats in some files will be used for calculations
                    cribEval[step] = (float)computeCribAverage(ridCards, chosenCards,cribCoeffs);
                }
                if (!myCrib) {
                    cribEval[step] *= -1;
                }
                pegEval[step]=UnsuitedMeths.computePegHeuri(chosenCards);
                //score[step] = handEval + cribEval;
                //info[step] = DataForStrings.verboArr(chosenCards);
                //info[step] += " " + DataForStrings.df(2, handEval);
                //info[step] += "   ///   ";
                //info[step] += DataForStrings.verboArr(ridCards);
                //info[step] += " " + DataForStrings.df(2, cribEval) + " :    ";
                //info[step] += DataForStrings.df(2, handEval + cribEval);
                step++;
            }
        }
        for (int i=0;i<15;i++){
            if (cribEval[i]<0.000001)
                System.out.println("Problem 0  in SuitedMeths");
        }
        System.out.println("Before creating report: "+cribMem[0][0]);
        return new UserReport(handMem,cribMem,handEval,cribEval,pegEval);
        /*GeneralMeths.quickSort(score, permArr);

        for (int i = 14; i >= 0; i--) {
            System.out.println(info[permArr[i]]);
        }
        String resArr[]=new String[15];
        for (int i = 14; i >= 0; i--) {
            resArr[15-i-1]=info[permArr[i]];
        }
        return String.join("\n", resArr);*/

    }

    /*
     * Method that iterates on all possible 4-hands, and compute their expected
     * values.
     *
    public static void main0() {
        
        String filename4=DataForStatFiles.
        PrintWriter writer;
        Card[] cards = new Card[52];
        for (int i = 0; i < 52; i++) {
            cards[i] = new Card(i);
        }

        //Write 
        try {
            writer = new PrintWriter(filename, "UTF-8");
            for (int i = 0; i < 52; i++) {
                for (int j = i + 1; j < 52; j++) {
                    writer.print(cards[i].verbos() + "," + cards[j].verbos() + ": ");
                    writer.println(weights[i][j]);
                }
            }
            writer.close();
        } catch (FileNotFoundException | UnsupportedEncodingException ex) {
            System.out.println("Error somewhere");
        }
        
        
        
        DataForStrings.proceed();
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
                DataForStrings.print(hand);
                System.out.println("Total: " + sumScore / 48.0);
            }

        } while (CombMeths.combIter(iter, 52));
        System.out.println(debugCount);
    }
    */
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
                    if (debugEqualizer)
                        coeff=1;
                    
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
