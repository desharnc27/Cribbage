/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.cribstats;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Data handler. This class also manage most statistic reading/writing in files
 * 
 * TODO: this class needs a lot of clean up
 *
 * @author desharnc27
 */
class Database {
    
    private static final String REPORT_PATH="weightReports/";
    private static int[][] pascalTriangle;
    private static NumCombo[] list5;
    
    private static final float[][] cribWeightsSelf=new float [52][52];
    private static final float[][] cribWeightsOpp=new float [52][52];
    
    //private static boolean ssFill=false;
    //private static boolean osFill=false;
    
    private static String filenameSelf = REPORT_PATH+"cribWeightsSelf";
    private static String filenameOpp = REPORT_PATH+"cribWeightsOpp";
    
    private static final float[][] cribWeightsSelfUnsuited=new float [13][13];
    private static final float[][] cribWeightsOppUnsuited=new float [13][13];
    
    //private static boolean suFill=false;
    //private static boolean ouFill=false;
    
    private static String filenameSelfUnsuited = REPORT_PATH+"cribWeightsSelfUnsuited";
    private static String filenameOppUnsuited = REPORT_PATH+"cribWeightsOppUnsuited";
    
    
    //static boolean vetoUseDataForReal = false;
    static boolean trollWriter=false;
    
    private static String reportInfo = REPORT_PATH+"reportInfo";
    
    
    
    public static void megaAnalysis(int stop){
        for (int i=0;i<stop;i++){
            int a=i-1;
            float[][] weights;
            if (i>0){
                
                Database.readUnsuitedCribSelfFile(a);
                weights=UnsuitedMeths.bigShitt(cribWeightsSelfUnsuited,false,i);
                
            }else
                weights=UnsuitedMeths.bigShitt(null,false,i);
         
            Database.writeCribUnsuitedFile(weights, false, i);
            
            if (i>0){
                
                Database.readUnsuitedCribOppFile(a);
                weights=UnsuitedMeths.bigShitt(cribWeightsOppUnsuited,true,i);
            }else
                weights=UnsuitedMeths.bigShitt(null,true,i);
            Database.writeCribUnsuitedFile(weights, true, i);
        }
        editIterFile(stop);
    }
    private static void editIterFile(int stop){
        String filename=Database.reportInfo;
        PrintWriter writer;


        //Write 
        try {
            writer = new PrintWriter(filename, "UTF-8");
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");  
            Date date = new Date();  
            writer.println("Last iterating analysis time: "+formatter.format(date));
            writer.println("Last iterating analysis number: "+(stop-1));

            writer.close();
        } catch (FileNotFoundException | UnsupportedEncodingException ex) {
            System.out.println("Error somewhere");
        }

    }

    public static void proceed() {
        createPascal();
        create5ScoreList();
    }
    public static void loadLatestStats() {
        int levMax=getLatestLevel();
        readCribSelfFile(levMax);
        readCribOppFile(levMax);
        readUnsuitedCribSelfFile(levMax);
        readUnsuitedCribOppFile(levMax);      
    }
    //Change please!!!
    public static int getLatestLevel(){
        String filename=reportInfo;
        File file;
        BufferedReader br;
        file = new File(filename);
        String line;
        int res=0;
        try {
            br = new BufferedReader(new FileReader(file));
            int idLine=0;
            while ((line = br.readLine()) != null) {
                String [] sep=line.split(": ");
                if (idLine==1){
                    res=Integer.parseInt(sep[sep.length-1]);
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
    public static float [][] readCribSelfFile(int level){
        return readCribFile(cribWeightsSelf,level);
        //ssFill=true;
    }
    public static float [][] readCribOppFile(int level){
        return readCribFile(cribWeightsOpp,level);
        //osFill=true;
    }
    public static float [][] readCribFile(float[][] weights,int level) {
        String filename;
        if (weights == cribWeightsSelf) {
            filename = filenameSelf+level+".txt";
        } else {
            filename = filenameOpp+level+".txt";
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
                    j = i+1;
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
    public static float [][] readUnsuitedCribSelfFile(int level){
        return readUnsuitedCribFile(cribWeightsSelfUnsuited,level);
        //suFill=true;
    }
    public static float [][] readUnsuitedCribOppFile(int level){
        return readUnsuitedCribFile(cribWeightsOppUnsuited,level);
        //ouFill=true;
    }
    public static float [][] readUnsuitedCribFile(float[][] weights,int level) {
        String filename;
        if (weights == cribWeightsSelfUnsuited) {
            filename = filenameSelfUnsuited+level+".txt";
        } else {
            filename = filenameOppUnsuited+level+".txt";
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
                if (trollWriter){
                    if (i==j)
                        weights[i][j]=6.0f;
                    if (i<j)
                        weights[i][j]=12.0f;
                    weights[i][j]=4.0f;
                }else{
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

    public static void writeCribFile(float[][] weights, boolean self, int level) {
        String filename;
        if (self) {
            filename = filenameSelf+level+".txt";
        } else {
            filename = filenameOpp+level+".txt";
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
    public static void writeCribUnsuitedFile(float[][] weights, boolean self, int level) {
        String filename;
        if (self) {
            filename = filenameSelfUnsuited+level+".txt";
        } else {
            filename = filenameOppUnsuited+level+".txt";
        }

        PrintWriter writer;

        //Write 
        try {
            writer = new PrintWriter(filename, "UTF-8");
            for (int i = 0; i < 13; i++) {
                for (int j = 0; j < 13; j++) {
                    if (i>j)
                        writer.print("sameSuit,");
                    else
                        writer.print("diffSuit,");
                    writer.print(i + "," + j + ": ");
                    writer.println(weights[i][j]);
                }
            }
            writer.close();
        } catch (FileNotFoundException | UnsupportedEncodingException ex) {
            System.out.println("Error somewhere");
        }

    }

    public static void createPascal() {
        int max = 25;
        pascalTriangle = new int[max][max];
        pascalTriangle[0][0] = 1;
        for (int i = 1; i < max; i++) {
            pascalTriangle[i][i] = 1;
            pascalTriangle[i][0] = 1;
            for (int j = 1; j < i; j++) {
                pascalTriangle[i][j] = pascalTriangle[i - 1][j - 1] + pascalTriangle[i - 1][j];
            }
        }
    }

    public static void create5ScoreList() {
        list5 = UnsuitedMeths.fill5();
    }

    public static int pascal(int i, int j) {
        return pascalTriangle[i][j];
    }

    public static float getScore5(int idx) {
        return list5[idx].score;
    }

    public static float getScore5WithoutFlush(int idx) {
        return list5[idx].scoreWithoutFlush;
    }

    static float[][] getCopyOfSuitedCribData(boolean selfCrib) {
        
        if (selfCrib){
            //if (ssFill)
                return cribWeightsSelf; 
            //return null;
        }
        //if (osFill)
            return cribWeightsOpp;
        //return null;
    }
    static float[][] getCopyOfUnsuitedCribData(boolean selfCrib) {
        if (selfCrib){
            //if (suFill)
                return cribWeightsSelfUnsuited; 
            //return null;
        }
        //if (ouFill)
            return cribWeightsOppUnsuited;
        //return null;     
    }
}
