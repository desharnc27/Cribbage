/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mainpackage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * This class manages all the reading/writing on discarding behavior statistics.
 *
 * @author desharnc27
 */
public class DataForStatFiles {
    private static String REPORT_PATH="weightReports"+File.separator;

    private static final float[][] cribWeightsSelf = new float[52][52];
    private static final float[][] cribWeightsOpp = new float[52][52];
    
    private static final float[][] cribWeightsLissorSelf = new float[52][52];
    private static final float[][] cribWeightsLissorOpp = new float[52][52];

    //private static boolean ssFill=false;
    //private static boolean osFill=false;
    private static String filenameSelf = REPORT_PATH + "cribWeightsSelf";
    private static String filenameOpp = REPORT_PATH + "cribWeightsOpp";

    private static final float[][] cribWeightsSelfUnsuited = new float[13][13];
    private static final float[][] cribWeightsOppUnsuited = new float[13][13];

    //private static boolean suFill=false;
    //private static boolean ouFill=false;
    private static String filenameSelfUnsuited = REPORT_PATH + "cribWeightsSelfUnsuited";
    private static String filenameOppUnsuited = REPORT_PATH + "cribWeightsOppUnsuited";

    private static String filenameSelfSingles = REPORT_PATH + "cribWeightsSelfSingles";
    private static String filenameOppSingles = REPORT_PATH + "cribWeightsOppSingles";

    //static boolean vetoUseDataForReal = false;
    static boolean trollWriter = false;

    private static String reportInfo = REPORT_PATH + "reportInfo";
    private static final String rootName ="CribbageNWT";
    private static String rootPath;
    
    private static void  setRoot(){
        String current = System.getProperty("user.dir");
        int idx = current.indexOf(rootName);
        if (idx<0){
            System.out.println("Major problem: wrong root name");
            return;
        }
        rootPath= current.substring(0, idx+rootName.length());
        System.out.println("root path: "+rootPath+"\\");
        
    }
    
    

    /**
     * Fills all the statistic files up to stop exclusively.
     *
     * Each call to analysisOfAll6Combos takes a minute to complete, which means
     * that The this function takes a bit more than 2*stop minutes to complete.
     *
     * If user interrupts (not recommended) the execution, it would do less harm
     * to interrupt it shortly after the program prints that an iteration was
     * completed (to be sure to not interrupt in the middle of the writing of a
     * file)
     *
     * Nevertheless, interruption will surly cause the reportInfo file to
     * contain wrong information.
     *
     * @param stop analysis stops just before filling files of level stop
     */
    public static void megaAnalysis(int stop) {
        for (int i = 0; i < stop; i++) {
            float[][] weights;
            if (i > 0) {

                DataForStatFiles.readUnsuitedCribSelfFile(i - 1);
                weights = UnsuitedMeths.analysisOfAll6Combos(cribWeightsSelfUnsuited, false, i);

            } else {
                weights = UnsuitedMeths.analysisOfAll6Combos(null, false, i);
            }
            
            
            float [][]suitedWeights = new float [52][52];
            DataForStatFiles.transUnsuitedToSuited(weights, suitedWeights);
            unitarize(suitedWeights,26*51);
            DataForStatFiles.writeCribUnsuitedFile(weights, false, i);
            DataForStatFiles.writeCribFile(suitedWeights, false, i);
            
            //DataForStatFiles.writeCribUnsuitedFile(unsuitedScorePairs, myCrib, level);
            //DataForStatFiles.writeCribSinglesFile(singleWeight, myCrib, level);

            DataForStatFiles.readUnsuitedCribOppFile(i);
            weights = UnsuitedMeths.analysisOfAll6Combos(cribWeightsOppUnsuited, true, i);
            
            suitedWeights = new float [52][52];
            DataForStatFiles.transUnsuitedToSuited(weights, suitedWeights);
            unitarize(suitedWeights,26*51);
            DataForStatFiles.writeCribUnsuitedFile(weights, true, i);
            DataForStatFiles.writeCribFile(suitedWeights, true, i);
        }
        editIterFile(stop);
    }

    /**
     * Edits the reportInfo file. Must be called call only at the end of
     * megaAnalyis(...)
     *
     * @param stop last level overwritten by megaAnalysis(...)
     */
    private static void editIterFile(int stop) {
        String filename = DataForStatFiles.reportInfo;
        PrintWriter writer;

        //Write 
        try {
            writer = new PrintWriter(filename, "UTF-8");
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            Date date = new Date();
            writer.println("Last iterating analysis time: " + formatter.format(date));
            writer.println("Last iterating analysis number: " + (stop - 1));

            writer.close();
        } catch (FileNotFoundException | UnsupportedEncodingException ex) {
            System.out.println("Error somewhere");
        }

    }
    /**
     * Translates a statistic 2D array formatted as unsuited.
     * to a statistic 2D array formatted as suited.
     * @param src
     * @param dest 
     */
    public static void transUnsuitedToSuited(float[][]src, float[][] dest){
        for (int i=0;i<52;i++){
            for (int j=0;j<52;j++){
                dest[i][j]=0;
            }
        }
        for (int i=0;i<52;i++){
            Card c0=new Card(i);
            int n0=c0.getNum()-1;
            int s0=c0.getSuit();
            for (int j=i+1;j<52;j++){
                
                Card c1=new Card(j);              
                int n1=c1.getNum()-1;
                int s1=c1.getSuit();
                
                int coeff = 6;
                float val = src[n0][n0];
                if (n0 != n1) {
                    if (s0 == s1) {
                        coeff = 4;
                    } else {
                        coeff = 12;
                    }
                    if ((s0 != s1) == (n0 < n1)) {
                        val = src[n0][n1];
                    } else {
                        val = src[n1][n0];
                    }
                }
                dest[i][j]=val/coeff;

            }
        }
        
    }
    /**
     * Initialize static variables of this class. Usually called by CentralSettings.doPreOperations()
     */
    public static void proceed() {
        setRoot();
        String projectPath;
        String differ = "/src/main/java/mainpackage";
        projectPath = System.getProperty("user.dir");
        if (projectPath.endsWith(differ)) {
            projectPath = projectPath.substring(0, projectPath.length() - differ.length());
        }

        REPORT_PATH = projectPath + "/weightReports/";

        filenameSelf = REPORT_PATH + "cribWeightsSelf";
        filenameOpp = REPORT_PATH + "cribWeightsOpp";

        filenameSelfUnsuited = REPORT_PATH + "cribWeightsSelfUnsuited";
        filenameOppUnsuited = REPORT_PATH + "cribWeightsOppUnsuited";

        filenameSelfSingles = REPORT_PATH + "cribWeightsSelfSingles";
        filenameOppSingles = REPORT_PATH + "cribWeightsOppSingles";

        reportInfo = REPORT_PATH + "reportInfo";
        
        loadLatestStats();

    }
    /**
     * Loads the latest stats in the DataForStatFiles
     *
     * Latest stats are in the files that have the level indicated in the info
     * report
     */
    public static void loadLatestStats() {
        int levMax = getLatestLevel();
        readCribSelfFile(levMax);
        readCribOppFile(levMax);
        readUnsuitedCribSelfFile(levMax);
        readUnsuitedCribOppFile(levMax);
        buildLissor();
    }

    /**
     * Returns the highest iteration level reached by the last mega analysis
     *
     * @return the highest iteration level reached by the last mega analysis
     */
    public static int getLatestLevel() {
        String filename = reportInfo;
        File file;
        BufferedReader br;
        file = new File(filename);
        String line;
        int res = 0;
        try {
            br = new BufferedReader(new FileReader(file));
            int idLine = 0;
            while ((line = br.readLine()) != null) {
                String[] sep = line.split(": ");
                if (idLine == 1) {
                    res = Integer.parseInt(sep[sep.length - 1]);
                    break;

                }
                idLine++;

            }

        } catch (FileNotFoundException ex) {
            System.out.println("file " + filename + " not found");
        } catch (IOException ex) {
            System.out.println("problem while reading " + filename);
        }
        return res;
    }

    /**
     * Reads a cribstat file (self), both stores its data in the
     * DataForStatFiles and returns it.
     *
     * @param level the level of the file to read
     * @return the read data
     */
    public static float[][] readCribSelfFile(int level) {
        return readCribFile(cribWeightsSelf, level);
        //ssFill=true;
    }

    /**
     * Reads a cribstat file (opp), both stores its data in the DataForStatFiles
     * and returns it.
     *
     * @param level the level of the file to read
     * @return the read data
     */
    public static float[][] readCribOppFile(int level) {
        return readCribFile(cribWeightsOpp, level);
        //osFill=true;
    }

    /**
     * Reads a cribstat file and fills weights.
     *
     * The file that is read is the self one if weights is the self one. The
     * file that is read is the opp one if weights is the opp one.
     *
     * @param weights array to be filled with the read statistics
     * @param level the iteration level of the file to be read
     */
    private static float[][] readCribFile(float[][] weights, int level) {
        String filename;
        if (weights == cribWeightsSelf) {
            filename = filenameSelf + level + ".txt";
        } else {
            filename = filenameOpp + level + ".txt";
        }
        File file;
        BufferedReader br;
        file = new File(filename);
        String line;
        try {
            br = new BufferedReader(new FileReader(file));
            int idx = 0;
            int i = 0;
            int j = 1;
            while ((line = br.readLine()) != null) {

                String[] sArr = line.split(" ");
                float weight = Float.valueOf(sArr[1]);
                weights[i][j] = weight;
                idx++;
                j++;
                if (j == 52) {
                    i++;
                    j = i + 1;
                }

            }

        } catch (FileNotFoundException ex) {
            System.out.println("file " + filename + " not found");
        } catch (IOException ex) {
            System.out.println("problem while reading " + filename);
        }

        /*Card []cards=new Card[52];
        for (int i=0;i<52;i++)         
            cards[i]=new Card(i);
        for (int i = 0; i < 52; i++) {
            for (int j = i + 1; j < 52; j++) {

                System.out.print(cards[i].verbos() + " " + cards[j].verbos() + ": ");
                System.out.print(weights[i][j]);
                System.out.println();
            }
        }*/
        //fillUnsuitedCrib(weights);
        return weights;
    }

    /**
     * Reads an unsuited cribstat file (self), both stores its data in the
     * DataForStatFiles and returns it.
     *
     * @param level the level of the file to read
     * @return the read data
     */
    public static float[][] readUnsuitedCribSelfFile(int level) {
        return readUnsuitedCribFile(cribWeightsSelfUnsuited, level);
        //suFill=true;
    }

    /**
     * Reads an unsuited cribstat file (opp), both stores its data in the
     * DataForStatFiles and returns it.
     *
     * @param level the level of the file to read
     * @return the read data
     */
    public static float[][] readUnsuitedCribOppFile(int level) {
        return readUnsuitedCribFile(cribWeightsOppUnsuited, level);
        //ouFill=true;
    }

    private static float[][] readUnsuitedCribFile(float[][] weights, int level) {
        String filename;
        if (weights == cribWeightsSelfUnsuited) {
            filename = filenameSelfUnsuited + level + ".txt";
        } else {
            filename = filenameOppUnsuited + level + ".txt";
        }
        File file;
        BufferedReader br;
        file = new File(filename);
        String line;
        try {
            br = new BufferedReader(new FileReader(file));
            int idx = 0;
            int i = 0;
            int j = 0;
            while ((line = br.readLine()) != null) {

                String[] sArr = line.split(" ");
                float weight = Float.valueOf(sArr[1]);
                if (trollWriter) {
                    if (i == j) {
                        weights[i][j] = 6.0f;
                    }
                    if (i < j) {
                        weights[i][j] = 12.0f;
                    }
                    weights[i][j] = 4.0f;
                } else {
                    weights[i][j] = weight;
                }
                idx++;
                j++;
                if (j == 13) {
                    i++;
                    j = 0;
                }

            }

        } catch (FileNotFoundException ex) {
            System.out.println("file " + filename + " not found");
        } catch (IOException ex) {
            System.out.println("problem while reading " + filename);
        }
        return weights;

        /*Card []cards=new Card[52];
        for (int i=0;i<52;i++)         
            cards[i]=new Card(i);
        for (int i = 0; i < 52; i++) {
            for (int j = i + 1; j < 52; j++) {

                System.out.print(cards[i].verbos() + " " + cards[j].verbos() + ": ");
                System.out.print(weights[i][j]);
                System.out.println();
            }
        }*/
        //fillUnsuitedCrib(weights);
    }

    /*private static void fillUnsuitedCrib(float[][] weights){
        float [][]unSuitedW;
        if (weights == cribWeightsSelf) {
            unSuitedW = cribWeightsSelfUnsuited;
        } else {
            unSuitedW = cribWeightsOppUnsuited;
        }
        for (int i=0;i<13;i++){
            //unSuitedW[]=
        }
        
    }*/

    /**
     * Writes the data of array weights in a cribstat file
     *
     * @param weights the weights of all 2-combos
     * @param self true if it is statistics about discarding in YOUR crib, false
     * i your opponent crib
     * @param level the level of the file where the data must be written.
     */
    public static void writeCribFile(float[][] weights, boolean self, int level) {
        String filename;
        if (self) {
            filename = filenameSelf + level + ".txt";
        } else {
            filename = filenameOpp + level + ".txt";
        }

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

    }

    /**
     * Writes the data of array weights in an unsuited cribstat file
     *
     * @param weights the weights of all unsuited 2-combos.
     * @param self true if it is statistics about discarding in YOUR crib, false
     * i your opponent crib
     * @param level the level of the file where the data must be written.
     */
    public static void writeCribUnsuitedFile(float[][] weights, boolean self, int level) {
        String filename;
        if (self) {
            filename = filenameSelfUnsuited + level + ".txt";
        } else {
            filename = filenameOppUnsuited + level + ".txt";
        }

        PrintWriter writer;

        //Write 
        try {
            writer = new PrintWriter(filename, "UTF-8");
            for (int i = 0; i < 13; i++) {
                for (int j = 0; j < 13; j++) {
                    if (i > j) {
                        writer.print("sameSuit,");
                    } else {
                        writer.print("diffSuit,");
                    }
                    writer.print(i + "," + j + ": ");
                    writer.println(weights[i][j]);
                }
            }
            writer.close();
        } catch (FileNotFoundException | UnsupportedEncodingException ex) {
            System.out.println("Error somewhere");
        }

    }

    /**
     * Writes the data of array weights in an cribstat file (singles)
     *
     * @param weights the weights of all unsuited cards.
     * @param self true if it is statistics about discarding in YOUR crib, false
     * i your opponent crib
     * @param level the level of the file where the data must be written.
     */
    public static void writeCribSinglesFile(float[] weights, boolean self, int level) {
        String filename;
        if (self) {
            filename = filenameSelfSingles + level + ".txt";
        } else {
            filename = filenameOppSingles + level + ".txt";
        }

        PrintWriter writer;

        //Write 
        try {
            writer = new PrintWriter(filename, "UTF-8");
            for (int i = 0; i < 13; i++) {
                String num = DataForStrings.symbolOfNum(i + 1);
                writer.println(num + ": " + weights[i]);
            }
            writer.close();
        } catch (FileNotFoundException | UnsupportedEncodingException ex) {
            System.out.println("Error somewhere");
        }

    }

    /**
     * Create files containing values of 5all -combos (score5) , average values 
     * (score4) of all 4-combos  (score1), and average average value of every individual card.
     * 
     * Executing this function is necessary for neither the user program nor the cribstat generator.
     * The relevancy of this function is also questionable since it produces horribly large files.
     */
    public static void createValueFiles() {

        String filename4 = REPORT_PATH + "score4.txt";
        String filename5 = REPORT_PATH + "score5.txt";
        String filename1 = REPORT_PATH + "score1.txt";
        PrintWriter writer4;
        PrintWriter writer5;
        PrintWriter writer1;
        
        Card[] cards = new Card[52];
        for (int i = 0; i < 52; i++) {
            cards[i] = new Card(i);
            System.out.println(cards[i].verbos());
        }

        //DataForStrings.proceed();
        int nbHandCombo = 52*50*49*51/24;

        float[] avScore1 = new float[14];
        int[] score5 = new int[nbHandCombo*48];
        float[] avScore4 = new float[nbHandCombo];

        int idx4 = 0;
        int idx5 = 0;
        int abdul=0;
        
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
                score5[idx5++] = score;
                sumScore += score;

            }
            avScore4[idx4++] = sumScore;
            for (int i = 0; i < 4; i++) {
                if (hand[i].getSuit()==0){
                    avScore1[hand[i].getNum()] += sumScore;
                    if (hand[i].getNum()==1)
                        abdul++;
                }
                    
                
            }

        } while (CombMeths.combIter(iter, 52));
        System.out.println(debugCount);
        for (int i = 0; i < avScore4.length;i++) {
            avScore4[i] /= 48;
        }
        for (int i = 1; i < 14; i++) {
            avScore1[i] /= abdul*48;
        }

        //Write 
        try {
            writer4 = new PrintWriter(filename4, "UTF-8");
            writer5 = new PrintWriter(filename5, "UTF-8");
            writer1 = new PrintWriter(filename1, "UTF-8");
            idx4 = 0;
            idx5 = 0;

            iter = new int[]{0, 1, 2, 3};
            hand = new Card[4];
            do {
                String handStr="";
                
                for (int i = 0; i < 4; i++) {
                    hand[i] = cards[iter[i]];
                    handStr+=hand[i].verbos();
                    if (i<3)
                        handStr+=",";
                    //else
                    //    handStr+=";";
                }
                int idxTaken = 0;
                for (int i = 0; i < 52; i++) {
                    if (idxTaken < 4 && iter[idxTaken] == i) {
                        idxTaken++;
                        continue;

                    }
                    String commStr=cards[i].verbos();
                    writer5.println(handStr+";"+commStr+": "+score5[idx5++]);
                }
                writer4.println(handStr+": "+avScore4[idx4++]);
                

            } while (CombMeths.combIter(iter, 52));
            writer5.close();
            writer4.close();
            
            for (int i=1;i<14;i++)
                writer1.println(DataForStrings.symbolOfNum(i)+": "+avScore1[i]);
            writer1.close();
        } catch (FileNotFoundException | UnsupportedEncodingException ex) {
            System.out.println("Error somewhere");
        }
    }

    public static float[][] getCopyOfSuitedCribData(boolean selfCrib) {

        if (selfCrib) {
            //if (ssFill)
            return cribWeightsSelf;
            //return null;
        }
        //if (osFill)
        return cribWeightsOpp;
        //return null;
    }

    /**
     * returns the unsuited cribstats currently loaded in DataForStatFiles.
     *
     * @param selfCrib true to return self stats, false to return opp stats
     * @return the unsuited cribstats currently loaded in DataForStatFiles.
     */
    static float[][] getCopyOfUnsuitedCribData(boolean selfCrib) {
        if (selfCrib) {
            //if (suFill)
            return cribWeightsSelfUnsuited;
            //return null;
        }
        //if (ouFill)
        return cribWeightsOppUnsuited;
        //return null;     
    }
    /**
     * Returns crib statistics modified by lissor algorithm
     * @param selfCrib true if it's the statistics from the player who has the crib, false otherwise
     * @return 2D array where the value at [i][j] is proportional to 
     */
    static float [][] getCopyOfSuitedLissorCribData(boolean selfCrib){
        if (selfCrib) {
            //if (suFill)
            return cribWeightsLissorSelf;
            //return null;
        }
        //if (ouFill)
        return cribWeightsLissorOpp;
        //return null;
    }
    /**
     * Prints an analysis of convergence tests for both opp and self stat files
     * @see convergenceTest(boolean)
     */
    public static void convergenceTest(){
        convergenceTest(true);
        convergenceTest(false);
    }
    /**
     * Prints an analysis of the numerical differences between each stat file and its preceding file. 
     * @param selfCrib true if self stats are analyzed, false if opp stats are analyzed
     */
    public static void convergenceTest(boolean selfCrib){
        String s=selfCrib?"self":"opp";
        System.out.println("Convergence test for "+s+" statistics");
        
        int levTop=getLatestLevel()+1;
        float [][][] allStats=new float[levTop][52][52];
        float [] convSum=new float[levTop-1];
        for (int i=0;i<levTop;i++){
            float [][]temp;
            if (selfCrib){
                temp=DataForStatFiles.readCribSelfFile(i);
                
            }else{
                temp=DataForStatFiles.readCribOppFile(i);
            }
            for (int c0=0;c0<52;c0++)
                System.arraycopy(temp[c0], c0+1, allStats[i][c0], c0+1, 52 - (c0+1));
            if (i==0)
                continue;
            for (int c0=0;c0<52;c0++)
                for (int c1=c0+1;c1<52;c1++){
                    float v0 =allStats[i][c0][c1];
                    float v1 = allStats[i - 1][c0][c1];
                    v0 -= v1;
                    if (v0 < 0) {
                        convSum[i-1] -= v0;
                    } else {
                        convSum[i-1] += v0;
                    }
                }
            convSum[i-1]/=(26*51);
            System.out.println("From "+(i-1)+" to "+i+": "+convSum[i-1]);
            
        }
        System.out.println("--------");
    }
    
    /**
     * Builds stat files, modified by lissor Algorithm. 
     */
    private static void buildLissor(){
        transLissor(cribWeightsOppUnsuited,cribWeightsLissorOpp);
        transLissor(cribWeightsSelfUnsuited,cribWeightsLissorSelf);
    }
    private static void transLissor(float [][] inStats, float[][] outStats){
        double [] singleSumUp=new double [13];
        double [] compareDiffSame =new double [3];
        
        for (int i=0;i<13;i++)
            for (int j=0;j<13;j++){
                
                int tempIdx;
                if (i<j){
                    tempIdx=0;
                }else if (i>j){
                    tempIdx=1;
                }else{
                    tempIdx=2;
                }
                
                double wei = inStats[i][j];
                singleSumUp[i]+=wei;
                singleSumUp[j]+=wei;
                compareDiffSame[tempIdx]+=wei;
                //compareDiffSame[tempIdx]+=wei;
            }
        
        //adjust
        
        compareDiffSame[0]/=36;
        compareDiffSame[1]/=12;
        compareDiffSame[2]/=3;
        
        double dval= compareDiffSame[0];
        double sval= compareDiffSame[1];
        double commodiv= 51 *  singleSumUp[4];
        for (int i=0;i<13;i++)
            for (int j=0;j<13;j++){
                double res=singleSumUp[i]*singleSumUp[j]/commodiv;
                double adjust;              
                if (i<j){
                    //colorStrategy
                    adjust= (4/(1+sval/dval))/3;
                    
                }else if (i>j){
                    //colorStrategy
                    adjust= 4/(1+dval/sval);
                }else{
                    //sameNumcoeff
                    adjust=6.0/16;
                }
                res *= adjust;

                for (int s0 = 0; s0 < 4; s0++) {
                    for (int s1 = 0; s1 < 4; s1++) {
                        if ((i > j) == (s0 != s1)) {
                            continue;
                        }
                        if (i == j && s0 > s1) {
                            continue;
                        }
                        int card0 = i + s0 * 13;
                        int card1 = j + s1 * 13;
                        if (card0 > card1) {
                            int temp = card0;
                            card0 = card1;
                            card1 = temp;
                        }
                        outStats[card0][card1] = (float) res;
                    }
                }


            }
        
    }
    
    
    //TODO: Following method could be in a more general class. Is not in GeneralMeths because reason 313
    /**
     * Divides all values of array by the same divisor, in such a way that all values sum to 1. 
     * @param weights 
     */
    public static void unitarize(float[][] arr, float targetSum) {
        float sum= sum(arr);
        float coeff=targetSum/sum;
        for (int i=0;i<arr.length;i++){
            for (int j=0;j<arr[i].length;j++)
                arr[i][j]*=coeff;
        }
    }
    /**
     * Returns the sum of all values in  2D float array
     * @param arr 2D float array
     * @return the sum of all values in  2D float array
     */
    public static float sum(float[][] arr) {
        float sum= 0;
        for (float[] arr1 : arr) {
            sum += sum(arr1);
        }
        return sum;
    }
    /**
     * Returns the sum of all values in  float array
     * @param arr float array
     * @return the sum of all values in  float array
     */
    public static float sum(float[] arr) {
        return sumX(arr,0,arr.length);
    }
    /**
     * Sums values in a certain interval of an array. 
     * 
     * Note: it works by DivideAndConquer, to lower the risk of numerical imprecision amplification.
     * @param arr float array
     * @param low the lower index , included
     * @param high the upper index, excluded
     * @return sum of all the values at indexes between low and high
     */
    private static float sumX(float[] arr, int low, int high) {
        if (low==high)
            return 0;
        if (low==high-1){
            return arr[low];
        }
        int mid=(low+high)/2;
        return sumX(arr,low,mid)+sumX(arr,mid,high);
    }
    
}
