/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mainpackage;


import java.util.ArrayList;
import java.util.Arrays;

/**
 * This class contains functions for cribbage calculations that keep track only
 * of the numbers of the cards, no their suit.
 *
 * This leads to some inaccuracy in the results, but is necessary to speed up
 * calculations. Iterating on all C(52,6) combinations of 6 cards and find, for
 * each one, the best discard option would be way too long. Taking in account
 * only the numbers, there is no more than (19,6) combinations to iterate on,
 * which still take more than a minute.
 *
 * Because of its inaccuracies, this class is only used to produce statistics on
 * what the opponent could discard. Then class UserDiscardDecisioning uses them to make
 * an accurate choice.
 *
 * @author desharnc27
 */
public class UnsuitedMeths {

    private static boolean debugPrintChoices = false;

    /**
     * This function returns a CCHBunch containing relevant choices for a crib
     * discarding decision making.
     *
     * @param cards the sorted array of length 6 containing the cards of the
     * combination
     * @param myCrib true if the user has the crib, false otherwise
     * @return the CCHBunch containing relevant discarding choices.
     */
    public static CCHBunch getChoiceF6(int[] cards, boolean myCrib) {
        return getChoiceF6(cards, myCrib, null);
    }

    /**
     * This function returns a CCHBunch containing relevant choices for a crib
     * discarding decision making.
     *
     * @param cards the sorted array of length 6 containing the cards of the
     * combination
     * @param myCrib true if the user has the crib, false otherwise
     * @param cribstats preexisting stats (TODOsee) on what the other player may
     * put in the crib, null if none.
     * @return the CCHBunch containing relevant discarding choices.
     */
    public static CCHBunch getChoiceF6(int[] cards, boolean myCrib, float[][] cribstats) {
        int chosenCards[] = new int[4];
        int ridCards[] = new int[2];

        String[] info = new String[15];
        int[] permArr = Aragula.idPermArray(15);
        Float[] score = new Float[15];

        CribChoiceHeuri[] allChoices = new CribChoiceHeuri[15];

        //First step: calculating all average scores
        int noCombo = 0;
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

                float prob2CribSameSuit = tossProbSameSuit(ridCards, chosenCards, myCrib);

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
                score[noCombo] = handEval + cribEval;
                info[noCombo] = Labels.verboArr(chosenCards);
                info[noCombo] += " " + Labels.df(2, handEval);
                info[noCombo] += "   ///   ";
                info[noCombo] += Labels.verboArr(ridCards);
                info[noCombo] += " " + Labels.df(2, cribEval) + " :    ";
                info[noCombo] += Labels.df(2, handEval + cribEval);

                allChoices[noCombo] = new CribChoiceHeuri(ridCards, prob2CribSameSuit);
                noCombo++;
            }
        }

        //Second step: sorting by scores. 
        //permArr keeps track of the initial index of every number in scores
        Aragula.quickSort(score, permArr);

        for (int i = 14; i >= 0; i--) {
            //System.out.println(info[permArr[i]]);
        }

        //Third step: withdraw relevant possibilities (discarding choices with average no under bestScore-1
        float bestScore = score[score.length - 1];

        ArrayList<CribChoiceHeuri> list = new ArrayList<>();
        ArrayList<Float> listWeight = new ArrayList<>();

        int idx = score.length - 1;
        while (idx >= 0 && score[idx] > bestScore - 1f) {
            boolean clone = false;
            for (int i = idx + 1; i < score.length; i++) {
                if (allChoices[permArr[i]].hasSameNumbers(allChoices[permArr[idx]])) {
                    clone = true;
                    break;
                }
            }
            if (clone) {
                idx--;
                continue;
            }
            list.add(allChoices[permArr[idx]]);
            listWeight.add(1f - bestScore + score[idx]);
            idx--;

        }
        CribChoiceHeuri[] selectedChoices = toArrayList(list);
        float[] weights = toArrayListF(listWeight);
        CCHBunch res = new CCHBunch(selectedChoices, weights);
        return res;

    }

    private static CribChoiceHeuri[] toArrayList(ArrayList<CribChoiceHeuri> list) {
        CribChoiceHeuri[] arr = new CribChoiceHeuri[list.size()];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = list.get(i);
        }
        return arr;

    }

    private static float[] toArrayListF(ArrayList<Float> list) {
        float[] arr = new float[list.size()];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = list.get(i);
        }
        return arr;

    }

    /**
     * Returns an array containing all possibilities of hand-flip and their
     * exact value
     *
     * @return an array containing all possibilities of hand-flip and their
     * exact value
     */
    public static NumCombo[] fill5() {

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
            sumScore += DataNumbers.getScore5(idx5) * coeffProb;
            sumCoeff += coeffProb;

        }
        return sumScore / sumCoeff;
    }

    /**
     * returns and estimate mean value of the crib
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
                    sumScore += DataNumbers.getScore5WithoutFlush(idx5) * coeff;
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

    /**
     * Returns and estimate mean value of the crib
     *
     * @param crib2Cards two cards of crib
     * @param unavail other four cards of hand (cannot be the flip or the two
     * other cards of the crib)
     * @param probOfCrib2SameSuit probability that the two cards of the crib
     * have the same suit
     * @param cribstats preexisting stats about the two other cards (TODOsee)
     * that the opponent may put in the crib
     * @return the mean value of the crib
     */
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
                        sumScore += DataNumbers.getScore5WithoutFlush(idx5) * secondCoeff * cribstats[j - 1][j - 1];
                        sumCoeff += cribstats[j - 1][j - 1] * secondCoeff;
                    } else {

                        float v0 = cribstats[j - 1][k - 1];
                        float v1 = cribstats[k - 1][j - 1];
                        if (allDiff(crib, i)) {
                            //TODO:continue here
                            sumScore += 5 / 16.0f * probOfCrib2SameSuit * v1 * secondCoeff;
                        }
                        sumScore += DataNumbers.getScore5WithoutFlush(idx5) * secondCoeff * (v0 + v1);
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
        return getIdxCode(hwNums, 0) + (commo - 1) * DataNumbers.pascal(13 - 1 + 4, 13 - 1);
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
            ans += DataNumbers.pascal(nums.length - idx - 1 + 13 - i - 1, nums.length - idx - 1);
        }
        ans += getIdxCode(nums, idx + 1);
        return ans;

    }

    /**
     * Checks whether or not two sorted arrays of int have a common element
     *
     * @param t0 a sorted array of int
     * @param t1 a sorted array of int
     * @return true if and only if t0 and t1 have a common element
     */
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

    /**
     * Checks if array of int t is sorted in ascending order
     *
     * @param t an array of int
     * @return true if t is sorted
     */
    public static boolean sorted(int[] t) {
        for (int i = 1; i < t.length; i++) {
            if (t[i] < t[i - 1]) {
                return false;
            }
        }
        return true;
    }

    /**
     * Returns the probability that the two cards tossed in the crib by the
     * player are of the same suit.
     *
     * @param ridCards the two numbers tossed in the crib by the player
     * @param chosenCards the numbers kept in hand by the player
     * @param myCrib true if the player has the crib for this turn, false
     * otherwise
     * @return the probability that the two cards tossed in the crib by the
     * player are of the same suit.
     */
    public static float tossProbSameSuit(int[] ridCards, int[] chosenCards, boolean myCrib) {

        //TODO:delete code, sould not be necessary
        if (!sorted(ridCards) || !sorted(chosenCards)) {
            System.out.println("Warning: unsorted array");
        }

        if (ridCards[0] == ridCards[1]) {
            return 0f;
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

    /**
     * This method iterates on all 6-cards combinations, finds the resulting
     * choice of discard for all of them and sums it all to produce arrays
     * containing statistics about the likehood of every pair of cards to put in
     * the crib.
     *
     * It produces both suited and unsuited stats (write in files) and return
     * the unsuited array
     *
     * @param myCrib true if the player has the crib
     * @return the unsuited statistics
     */
    public static float[][] bigShitt(boolean myCrib) {
        return bigShitt(null, myCrib, 0);
    }

    /**
     * This method iterates on all 6-cards combinations, finds the resulting
     * choice of discard for all of them and sums it all to produce arrays
     * containing statistics about the likehood of every pair of cards to put in
     * the crib.It produces both suited and unsuited stats (write in files) and
     * return the unsuited array
     *
     * @param readUnsuitedScorePairs statistics on what two cards the opponent
     * is likely to discard
     * @param myCrib true if the player has the crib
     * @param level level of iteration (level 0 means that we assume the
     * opponent discards randomly, level i>0 uses stats of level i-1 for
     * opponent
     * @return the unsuited statistics
     */
    public static float[][] bigShitt(float[][] readUnsuitedScorePairs, boolean myCrib, int level) {
        int[] combId = new int[6];

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

            CCHBunch cchb = getChoiceF6(numbers, myCrib, readUnsuitedScorePairs);
            int multicoef = nbPossOfCardSet(numbers);
            cchb.addTo(arrOfScorePairs, unsuitedScorePairs, multicoef);

            if (debugPrintChoices) {
                System.out.println("__-----___------___");
                SmallTests.println(numbers);
                System.out.println("multicoef: " + multicoef);
                System.out.println(cchb);
                System.out.println("__-----___------___");
            }

        } while (CombMeths.genCombIter(combId, 13));

        /*Card[] cards = new Card[52];
        for (int i = 0; i < 52; i++) {
            cards[i] = new Card(i);
        }*/
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

    /**
     * Returns the number of combinations of suited cards that match numbers
     *
     * @param numbers the numbers of the cards (must be sorted before)
     * @return the number of combinations of cards that match numbers
     */
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
     * Returns true if sorted array arr contain numbers that are all different
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
