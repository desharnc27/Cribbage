package test;

import core.CCHBunch;
import core.CentralSettings;
import tools.CombMeths;
import core.CribbageException;
import core.DataForNumbers;
import core.DataForStatFiles;
import core.SuitedMeths;
import core.UnsuitedMeths;
import tools.GeneralMeths;

/**
 *
 * Testing class for developer. Not for User.
 *
 * @author desharnc27
 */
public class SmallTests {

    public static void main0() {
        int[] comgen = new int[7];
        DataForNumbers.createPascal();
        do {
            System.out.print(UnsuitedMeths.getIdxCode(comgen, 0) + ": ");
            println(comgen);

        } while (CombMeths.genCombIter(comgen, 13));
    }

    public static void main1() {
        int[] hand = new int[]{4, 4, 4, 4};
        int[] unavail = new int[]{1, 1};
        System.out.println("---------***-------");

        System.out.println(UnsuitedMeths.averageHandScore(hand, unavail));
        unavail[0]++;
        System.out.println(UnsuitedMeths.averageHandScore(hand, unavail));
        unavail[1]++;
        System.out.println(UnsuitedMeths.averageHandScore(hand, unavail));

    }

    public static void main2() {
        int[] cards = new int[]{5, 7, 8, 9, 12, 13};
        System.out.println("---------***-------");

        System.out.println();
        UnsuitedMeths.getChoiceF6(cards, true);
        System.out.println("---------***-------");
        for (int i = 0; i < 1; i++) {
            UnsuitedMeths.getChoiceF6(cards, false);
        }

    }

    public static void main3() {
        int[] cr = new int[]{2, 4};
        int[] ha = new int[]{2, 4, 3, 3};
        System.out.println(UnsuitedMeths.tossProbSameSuit(cr, ha, true));

    }

    public static void main4() {

        UnsuitedMeths.analysisOfAll6Combos(true);

    }

    public static void main5() {
        //DataForStatFiles.readUnsuitedCribSelfFile();
        //DataForStatFiles.readUnsuitedCribOppFile();
        DataForStatFiles.loadLatestStats();

        String[] args = new String[]{"y di2 cl-2 d3 c4 c8 c1"};
        try {
            SuitedMeths.getDiscardReport(args, (byte) 1);
        } catch (CribbageException ex) {
        }
    }

    public static void main6() {
        DataForStatFiles.megaAnalysis(7);
    }

    public static void main7() {
        int[] cards = new int[]{1, 1, 4, 4, 10, 12};
        CCHBunch c = UnsuitedMeths.getChoiceF6(cards, false);

    }

    public static void main8() {

        String currentDirectory = System.getProperty("user.dir");
        System.out.println(currentDirectory);

    }

    public static void main9() {

        DataForStatFiles.createValueFiles();

    }

    public static void mainA() {
        float[][] arr = new float[][]{
            {1f, 2f, 3f},
            {2f, 3f, 4f},
            {3f, 4f, 5f}

        };
        System.out.println(DataForStatFiles.sum(arr));
        DataForStatFiles.unitarize(arr, 9);
        System.out.println(DataForStatFiles.sum(arr));
        System.out.println(arr[0][0]);
        System.out.println(arr[0][1]);
        System.out.println(arr[0][2]);

    }

    public static void mainB() {
        System.out.println("Frenégençe");
        String filename = "C:\\Users\\desha\\Documents\\NetBeansProjects\\CribbageNWT\\translation\\fra\\small.txt";
        String content = GeneralMeths.fileToString(filename);
        System.out.println(content);
    }

    public static void main(String[] args) {
        CentralSettings.doPreOperations();
        try {
            mainB();
        } catch (Exception e) {
            System.out.println(e.getClass());
            System.out.println(e.getMessage());
            StackTraceElement[] stes = e.getStackTrace();
            for (StackTraceElement ste : stes) {
                System.out.println(ste);
            }
        }

    }

    public static void print(int[] tab) {
        for (int i = 0; i < tab.length; i++) {
            System.out.print(tab[i] + " ");
        }
    }

    public static void println(int[] tab) {
        print(tab);
        System.out.println();
    }
}
