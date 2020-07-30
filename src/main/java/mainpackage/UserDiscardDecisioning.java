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
import java.util.Scanner;
import graphics.FrameMain;




/**
 * 
 * Main class for user. 
 * 
 * The user must run this file with appropriate arguments (or none for interactive mode) to
 * get the analysis of discarding choices he could make.
 * 
 * @author desharnc27
 * 
 */
public class UserDiscardDecisioning {
    
    /**
     * The main
     * @param args arguments (hand for single request, nothing for interactive mode)
     */
    
    public static void doPreOperations(){
        Langu.initialize();
        DataForStrings.proceed();
        DataForNumbers.proceed();
        DataForStatFiles.proceed();
    }
    public static void main(String []args) {
        doPreOperations();
        if (args.length!=0){
            
            try {
                SuitedMeths.printDiscardReport(args);
            } catch (CribbageException ex) {
                System.out.println(ex.getErrorMessage());
                printInputGuide();
            }
            return;
        }
        interactiveProgram();        
        
    }
    /**
     * Prints the input guide
     */
    public static String getInputGuide() {
        String text = GeneralMeths.fileToString("inputUserGuide.txt");
        return text;
    }
    /**
     * Prints the input guide
     */
    public static void printInputGuide() {
        System.out.println(getInputGuide());
    }
    /**
     * Launches the interactive program where the user can ask cribbage hand analysis, 
     * read the input guide and exit when desired.
     */
    public static void interactiveProgram(){
        Scanner scan = new Scanner(System.in);
        while (true) {
            System.out.print(Langu.smallText(0x1));
            System.out.println("("+Langu.smallText(0x20)+")");
            String input = scan.next();
            if (input.equals("exit"))
                break;
            if (input.equals("input-guide")){
                //printInputGuide();
                System.out.println(Langu.inputGuide());
                continue;
            }
            if (input.equals("about")){
                //System.out.println(GeneralMeths.fileToString("about_pl.txt"));
                System.out.println(Langu.about());
                continue;
            }
            if (input.startsWith("lang-")){
                //System.out.println(GeneralMeths.fileToString("about_pl.txt"));
                String lang=input.substring(5);
                boolean success=Langu.setLanguage(lang);
                if (! success){
                    System.out.println(Langu.smallTextX(0x25,new String[]{lang}));
                }
                continue;
            }
            try{
                
                String [] param=input.split(",");
                UserReport ur=SuitedMeths.printDiscardReport(param);
                String s=String.join("\n",ur.computeStringArr(true, false));
                System.out.println(s);
                ur=SuitedMeths.printDiscardReport(param,false);
                s=String.join("\n",ur.computeStringArr(true, false));
                System.out.println(s);
            }catch(CribbageException e){
                System.out.println(e.getErrorMessage());
                System.out.println(Langu.smallText(0x23));
                System.out.println(Langu.smallText(0x21));
                System.out.println(Langu.smallText(0x22));
                System.out.println(Langu.smallText(0x24));
                
            }
            
            System.out.println("----------------------");
        }
    }
}
