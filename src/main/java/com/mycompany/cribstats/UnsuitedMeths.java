/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.cribstats;

import java.util.Arrays;

/**
 *
 * @author desharnc27
 */
public class UnsuitedMeths {

    public static CCHBunch getChoiceF6(int[] cards, boolean myCrib) {
        return getChoiceF6(cards, myCrib, null);
    }

    /**
     * Returns the TODO
     *
     * @param cards
     * @param myCrib
     * @return
     */
    public static CCHBunch  getChoiceF6(int[] cards, boolean myCrib, float[][] cribstats) {
        int chosenCards[] = new int[4];
        int ridCards[] = new int[2];

        String[] info = new String[15];
        int[] permArr = Aragula.idPermArray(15);
        Float[] score = new Float[15];

        //Toseeusefulness!?
        CribChoiceHeuri[] recip = new CribChoiceHeuri[15];

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

                float handEval = averageHandScore(chosenCards, ridCards);

                float prob2CribSameSuit = twadocrinian(ridCards, chosenCards, myCrib);

                float cribEval;

                if (cribstats == null) {
                    cribEval = averageCribScore(ridCards, chosenCards, prob2CribSameSuit);
                } else {
                    cribEval = averageCribScore(ridCards, chosenCards, prob2CribSameSuit, cribstats);
                }
                //float cribEval=0;
                if (!myCrib) {
                    cribEval *= -1;
                }
                if (Float.isNaN(cribEval)) {
                    System.out.println("Found something wiird:");
                    SmallTests.println(chosenCards);
                    SmallTests.println(ridCards);
                }
                score[step] = handEval + cribEval;
                info[step] = Labels.verboArr(chosenCards);
                info[step] += " " + Labels.df(2, handEval);
                info[step] += "   ///   ";
                info[step] += Labels.verboArr(ridCards);
                info[step] += " " + Labels.df(2, cribEval) + " :    ";
                info[step] += Labels.df(2, handEval + cribEval);

                recip[step] = new CribChoiceHeuri(ridCards, prob2CribSameSuit);
                step++;
            }
        }
        Aragula.quickSort(score, permArr);

        for (int i = 14; i >= 0; i--) {
            //System.out.println(info[permArr[i]]);
        }
        
        float bestScore=score[score.length-1];
        int idxOut=score.length-1;
        while(idxOut>=0 && score[idxOut]>bestScore-1f)
            idxOut--;
        CribChoiceHeuri []cchb= new CribChoiceHeuri[score.length-1-idxOut];
        float []weights=new float[cchb.length];
        for (int i=0;i<cchb.length;i++){
            cchb[i]= recip[permArr[permArr.length - 1-i]];
            weights[i]= 1f-bestScore+score[permArr.length - 1-i];
        }
        //return (recip[permArr[permArr.length - 1]]);
        return new CCHBunch(cchb,weights);

    }

    /**
     * Returns an array containing all possibilities of hand-flip and their
     * exact value
     *
     * @return an array containing all possibilities of hand-flip and their
     * exact value
     */
    public static NumCombo[] fill5() {

        //C(4+13-1,13-1)
        int comb4Count = CombMeths.combine(4 + 13 - 1, 13 - 1);
        NumCombo res[] = new NumCombo[13 * comb4Count];
        int[] combId = new int[4];

        int[] numbers;
        int count = 0;
        int idx = 0;
        for (int commo = 1; commo < 14; commo++) {
            do {
                //TODO:suspicious assign numbers
                numbers = new int[4];
                for (int i = 0; i < numbers.length; i++) {
                    numbers[i] = combId[i] + 1;
                }
                res[idx] = new NumCombo(commo, numbers);
                count++;
                idx++;
            } while (CombMeths.genCombIter(combId, 13));
        }
        return res;
    }

    /*public static void getDumps() {
        int[] combId = new int[6];
        int[] numbers = new int[6];
        int[] ridCards = new int[2];
        int[] chosenCards = new int[4];

        int count = 0;
        do {
            count++;
            for (int i = 0; i < 6; i++) {
                numbers[i] = combId[i] + 1;
            }
            for (int i = 0; i < 6; i++) {
                for (int j = i + 1; j < 6; j++) {

                    int choIdx = 0;
                    int rejIdx = 0;
                    for (int k = 0; k < 6; k++) {
                        if (k == i || k == j) {
                            ridCards[rejIdx++] = numbers[k];
                        } else {
                            chosenCards[choIdx++] = numbers[k];
                        }
                    }
                    float medHandScore = averageHandScore(chosenCards, ridCards);
                    float medCribScore = averageCribScore(ridCards, chosenCards);
                    //TODO:add a peg potential evaluation heuristic?

                }
            }

        } while (CombMeths.genCombIter(combId, 13));
        System.out.println(count);
    }*/
    /**
     * Returns the expected value of a hand
     *
     * @param chosenCards the hand
     * @param ridCards cards tossed in crib (therefore cannot be in the flip)
     * @return the expected value of the hand
     */
    public static float averageHandScore(int[] chosenCards, int[] ridCards) {
        int[] remainAvail = new int[14];
        for (int k = 1; k < remainAvail.length; k++) {
            remainAvail[k] = 4;
        }
        for (int k = 0; k < chosenCards.length; k++) {
            remainAvail[chosenCards[k]]--;
        }
        for (int k = 0; k < ridCards.length; k++) {
            remainAvail[ridCards[k]]--;
        }
        int availCount = 0;
        int sumCoeff = 0;
        float sumScore = 0;
        for (int k = 1; k < remainAvail.length; k++) {
            int coeffProb = remainAvail[k];
            if (coeffProb <= 0) {
                continue;
            }
            int idx5 = getIdx5(k, chosenCards);
            sumScore += Database.getScore5(idx5) * coeffProb;
            sumCoeff += coeffProb;

        }
        return sumScore / sumCoeff;
    }

    /**
     * returns the mean value of the crib
     *
     * @param crib2Cards two cards of crib
     * @param unavail other four cards of hand (cannot be the flip or the two
     * other cards of the crib)
     * @param probOfCrib2SameSuit probability that the two cards of the crib
     * have the same suit
     * @return the mean value of the crib
     */
    private static float averageCribScore(int[] crib2Cards, int[] unavail, float probOfCrib2SameSuit) {
        int[] remainAvail = new int[14];
        for (int k = 1; k < remainAvail.length; k++) {
            remainAvail[k] = 4;
        }
        for (int k = 0; k < unavail.length; k++) {
            remainAvail[unavail[k]]--;
        }
        for (int k = 0; k < crib2Cards.length; k++) {
            remainAvail[crib2Cards[k]]--;
        }

        int coeff = 1;
        float sumScore = 0;
        int sumCoeff = 0;
        for (int i = 0; i < 14; i++) {
            if (remainAvail[i] <= 0) {
                continue;
            }
            coeff *= remainAvail[i]--;

            for (int j = 1; j < 14; j++) {
                if (remainAvail[j] <= 0) {
                    continue;
                }
                coeff *= remainAvail[j]--;
                for (int k = j; k < 14; k++) {
                    if (remainAvail[k] <= 0) {
                        continue;
                    }
                    coeff *= remainAvail[k]--;

                    //nbPoss++;
                    int[] crib = new int[4];
                    crib[0] = crib2Cards[0];
                    crib[1] = crib2Cards[1];
                    crib[2] = j;
                    crib[3] = k;
                    Arrays.sort(crib);
                    if (j == k) {
                        coeff /= 2;
                    }
                    int idx5 = getIdx5(i, crib);
                    sumScore += Database.getScore5WithoutFlush(idx5) * coeff;
                    sumScore += PointCounting.specialFlush(i, crib2Cards, j, k, probOfCrib2SameSuit);
                    sumCoeff += coeff;
                    if (j == k) {
                        coeff *= 2;
                    }
                    coeff /= ++remainAvail[k];
                }
                coeff /= ++remainAvail[j];
            }
            coeff /= ++remainAvail[i];

        }
        float res = sumScore / sumCoeff;
        if (Float.isNaN(res)) {
            System.out.println("Found something wiird:");
            System.out.println(sumScore);
            System.out.println(sumCoeff);
            SmallTests.println(crib2Cards);
            SmallTests.println(unavail);
        }

        return res;

    }

    private static float averageCribScore(int[] crib2Cards, int[] unavail, float probOfCrib2SameSuit, float[][] cribstats) {
        int[] remainAvail = new int[14];
        for (int k = 1; k < remainAvail.length; k++) {
            remainAvail[k] = 4;
        }
        for (int k = 0; k < unavail.length; k++) {
            remainAvail[unavail[k]]--;
        }
        for (int k = 0; k < crib2Cards.length; k++) {
            remainAvail[crib2Cards[k]]--;
        }

        float globalSumScore = 0;
        int globalSumCoeff = 0;

        for (int i = 0; i < 14; i++) {
            if (remainAvail[i] <= 0) {
                continue;
            }
            int firstCoeff = remainAvail[i]--;
            //secondCoeff *= remainAvail[i]--;
            float sumScore = 0;
            float sumCoeff = 0;
            int secondCoeff = 1;

            for (int j = 1; j < 14; j++) {
                if (remainAvail[j] <= 0) {
                    continue;
                }
                secondCoeff *= remainAvail[j]--;
                for (int k = j; k < 14; k++) {
                    if (remainAvail[k] <= 0) {
                        continue;
                    }
                    secondCoeff *= remainAvail[k]--;
                    if (k == j) {
                        secondCoeff /= 2;
                    }

                    //nbPoss++;
                    int[] crib = new int[4];
                    crib[0] = crib2Cards[0];
                    crib[1] = crib2Cards[1];
                    crib[2] = j;
                    crib[3] = k;
                    Arrays.sort(crib);
                    int idx5 = getIdx5(i, crib);

                    //sumScore += PointCounting.specialFlush(i,crib2Cards,j,k,probOfCrib2SameSuit);
                    //TODO:flushFunky calculation
                    if (k == j) {
                        sumScore += Database.getScore5WithoutFlush(idx5) * secondCoeff * cribstats[j - 1][j - 1];
                        sumCoeff += cribstats[j - 1][j - 1] * secondCoeff;
                    } else {

                        float v0 = cribstats[j - 1][k - 1];
                        float v1 = cribstats[k - 1][j - 1];
                        if (allDiff(crib, i)) {
                            //TODO:continue here
                            sumScore += 5 / 16.0f * probOfCrib2SameSuit * v1 * secondCoeff;
                        }
                        sumScore += Database.getScore5WithoutFlush(idx5) * secondCoeff * (v0 + v1);
                        sumCoeff += (v0 + v1) * secondCoeff;

                    }
                    if (k == j) {
                        secondCoeff *= 2;
                    }
                    secondCoeff /= ++remainAvail[k];
                }
                secondCoeff /= ++remainAvail[j];
            }
            //secondCoeff /= ++remainAvail[i];
            float midScore = sumScore / sumCoeff;
            globalSumScore += midScore * firstCoeff;
            globalSumCoeff += firstCoeff;

            ++remainAvail[i];
        }
        float res = globalSumScore / globalSumCoeff;
        if (Float.isNaN(res)) {
            System.out.println("Found something wiird:");
            System.out.println(globalSumScore);
            System.out.println(globalSumCoeff);
            SmallTests.println(crib2Cards);
            SmallTests.println(unavail);
        }

        return res;

    }

    /**
     * Returns the index of a flip-hand combination
     *
     * @param commo the flip
     * @param hand the hand
     * @return the index of the combination
     */
    public static int getIdx5(int commo, int[] hand) {
        int hwNums[] = new int[hand.length];
        for (int i = 0; i < hwNums.length; i++) {
            hwNums[i] = hand[i] - 1;
        }
        return getIdxCode(hwNums, 0) + (commo - 1) * Database.pascal(13 - 1 + 4, 13 - 1);
    }

    /**
     * Returns the index of a general combination if idx==0, otherwise TODO
     * explanations
     *
     * @param nums the elements
     * @param idx actual idx of the calculation TODO
     * @return the index of the general combination
     */
    public static int getIdxCode(int[] nums, int idx) {
        if (idx == nums.length) {
            return 0;
        }
        int ans = 0;
        int lastVal = 0;
        if (idx > 0) {
            lastVal = nums[idx - 1];
        }
        for (int i = lastVal; i < nums[idx]; i++) {
            //nums.length-idx-1 quantity of numbers to pick
            //13-i: quantity of available numbers
            ans += Database.pascal(nums.length - idx - 1 + 13 - i - 1, nums.length - idx - 1);
        }
        ans += getIdxCode(nums, idx + 1);
        return ans;

    }

    public static boolean hasCommonElem(int[] t0, int[] t1) {
        int idx0 = 0;
        int idx1 = 0;
        while (true) {
            if (idx0 == t0.length || idx1 == t1.length) {
                return false;
            }
            int comp = t0[idx0] - t1[idx1];
            if (comp < 0) {
                idx0++;
            } else if (comp > 0) {
                idx1++;
            } else {
                return true;
            }
        }
    }

    public static boolean sorted(int[] t) {
        for (int i = 1; i < t.length; i++) {
            if (t[i] < t[i - 1]) {
                return false;
            }
        }
        return true;
    }

    public static float twadocrinian(int[] ridCards, int[] chosenCards, boolean myCrib) {
        //Note: arrays in parameters must be already sorted

        //TODO:delete code, sould not be necessary
        if (!sorted(ridCards) || !sorted(chosenCards)) {
            System.out.println("Warning: unsorted array");
        }

        if (ridCards[0] == ridCards[1]) {
            return 0;
        }
        if (!myCrib) {
            if (hasCommonElem(ridCards, chosenCards)) {
                return 0.0f;
            }
            return 0.25f;
        }
        int[] repCounts = new int[]{0, 0};
        for (int i = 0; i < chosenCards.length; i++) {
            for (int j = 0; j < 2; j++) {
                if (chosenCards[i] == ridCards[j]) {
                    repCounts[j]++;
                }
            }
        }
        int sumRep = repCounts[0] + repCounts[1];
        if (sumRep > 2) {
            return 1.0f;
        }
        if (repCounts[0] == 1 && repCounts[1] == 1) {
            return (5 / 6.0f);
        }
        return (sumRep + 1) / 4.0f;
    }
    public static float[][] bigShitt( boolean myCrib) {
        return bigShitt(null, myCrib, 0);
    }
    public static float[][] bigShitt(float[][] readUnsuitedScorePairs, boolean myCrib, int level) {
        int[] combId = new int[6];

        //
        float[][] arrOfScorePairs = new float[52][52];
        float[][] unsuitedScorePairs = new float[13][13];
        //float[][] readUnsuitedScorePairs = null;
        //readUnsuitedScorePairs=Database.getCopyOfUnsuitedCribData(!myCrib);

        do {
            if (combId[0] == combId[4] || combId[1] == combId[5]) {
                continue;
            }
            int[] numbers = new int[6];
            for (int i = 0; i < combId.length; i++) {
                numbers[i] = combId[i] + 1;
            }

            //TODO:delete(debug)
            int[] debugNum = new int[]{5, 7, 8, 9, 12, 13};
            if (Arrays.equals(debugNum, numbers)) {
                int a = 1;
            }
            CCHBunch cchb = getChoiceF6(numbers, myCrib, readUnsuitedScorePairs);
            int multicoef = nbPossOfCardSet(numbers);
            cchb.addTo(arrOfScorePairs, unsuitedScorePairs, multicoef);
            //System.out.println("__-----___------___");
            //SmallTests.println(numbers);
            //System.out.println("multicoef: " + multicoef);
            //System.out.println(cchb);
            //System.out.println("__-----___------___");

        } while (CombMeths.genCombIter(combId, 13));

        Card[] cards = new Card[52];
        for (int i = 0; i < 52; i++) {
            cards[i] = new Card(i);
        }

        //Write 
        /*for (int i = 0; i < 52; i++) {
            for (int j = i + 1; j < 52; j++) {

                System.out.print(cards[i].verbos() + " " + cards[j].verbos() + ": ");
                System.out.print(arrOfScorePairs[i][j]);
                System.out.println();
            }
        }*/
        Database.writeCribFile(arrOfScorePairs, myCrib, level);
        Database.writeCribUnsuitedFile(unsuitedScorePairs, myCrib, level);
        System.out.println("Terminated for: " + myCrib + "," + level);
        return unsuitedScorePairs;

    }

    public static int nbPossOfCardSet(int[] numbers) {
        int coeff = 1;
        int miniCount = 1;
        int[] nbComb = new int[]{1, 4, 6, 4, 1};
        for (int i = 1; i < numbers.length; i++) {
            if (numbers[i] == numbers[i - 1]) {
                miniCount++;
            } else {
                coeff *= nbComb[miniCount];
                miniCount = 1;
            }

        }
        coeff *= nbComb[miniCount];
        return coeff;

    }

    /**
     * returns true if sorted array arr contain numbers that are all different
     * of k and all different between themselves
     *
     * @param arr
     * @param k
     * @return true if sorted array arr contain numbers that are all different
     * of k and all different between themselves false otherwise
     */
    public static boolean allDiff(int[] arr, int k) {
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] == arr[i - 1] || arr[i] == k) {
                return false;
            }
        }
        return arr[0] != k;
    }

}
