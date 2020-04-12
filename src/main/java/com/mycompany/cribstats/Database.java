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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Data handler
 *
 * @author desharnc27
 */
class Database {

    private static int[][] pascalTriangle;
    private static NumCombo[] list5;
    
    private static float[][] cribWeightsSelf=new float [52][52];
    private static float[][] cribWeightsOpp=new float [52][52];
    private static String filenameSelf = "cribWeightsSelf.txt";
    private static String filenameOpp = "cribWeightsOpp.txt";
    
    private static float[][] cribWeightsSelfUnsuited=new float [13][13];
    private static float[][] cribWeightsOppUnsuited=new float [13][13];
    
    private static boolean vetoUseDataForReal = true;
    

    public static void proceed() {
        createPascal();
        create5ScoreList();
        //readCribFile(cribWeightsSelf);
        //readCribFile(cribWeightsOpp); 
    }
    public static void readCribSelfFile(){
        readCribFile(cribWeightsSelf);
    }
    public static void readCribOppFile(){
        readCribFile(cribWeightsOpp);
    }
    public static void readCribFile(float[][] weights) {
        String filename;
        if (weights == cribWeightsSelf) {
            filename = filenameSelf;
        } else {
            filename = filenameOpp;
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
        
        Card []cards=new Card[52];
        for (int i=0;i<52;i++)         
            cards[i]=new Card(i);
        for (int i = 0; i < 52; i++) {
            for (int j = i + 1; j < 52; j++) {

                System.out.print(cards[i].verbos() + " " + cards[j].verbos() + ": ");
                System.out.print(weights[i][j]);
                System.out.println();
            }
        }
        fillUnsuitedCrib(weights);

    }
    private static void fillUnsuitedCrib(float[][] weights){
        float [][]unSuitedW;
        if (weights == cribWeightsSelf) {
            unSuitedW = cribWeightsSelfUnsuited;
        } else {
            unSuitedW = cribWeightsOppUnsuited;
        }
        for (int i=0;i<13;i++){
            //unSuitedW[]=
        }
        
    }

    public static void writeCribFile(float[][] weights, boolean self) {
        String filename;
        if (self) {
            filename = filenameSelf;
        } else {
            filename = filenameOpp;
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
        list5 = Ambitious.fill5();
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
        if(!Database.vetoUseDataForReal)
            return null;
        if (selfCrib){
            return cribWeightsSelf;           
        }
        return cribWeightsOpp;
        
    }
}
