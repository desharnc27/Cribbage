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




/**
 * 
 * Main class for user. 
 * 
 * The must run this file with appropriate arguments (or none for interactive mode) to
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
    public static void main(String []args) {
        DataForStrings.proceed();
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
    public static void printInputGuide() {
        String filename="inputUserGuide.txt";
        File file;
        BufferedReader br;
        file = new File(filename);
        String line;
        try {
            br = new BufferedReader(new FileReader(file));
            while ((line = br.readLine()) != null) {
                System.out.println(line);

            }

        } catch (FileNotFoundException ex) {
            System.out.println("The input guide is unavailabe because the file "+file+" is missing");
        } catch (IOException ex) {
            System.out.println("The input guie is unavailable for an unknown reason.");
       
        }
    }
    /**
     * Launches the interactive program where the user can ask cribbage hand analysis, 
     * read the input guide and exit when desired.
     */
    public static void interactiveProgram(){
        Scanner scan = new Scanner(System.in);
        while (true) {
            System.out.println("What hand do you want to analyse?");
            String input = scan.next();
            if (input.equals("exit"))
                break;
            if (input.equals("input-guide")){
                printInputGuide();
                continue;
            }
            try{
                String [] param=input.split(",");
                SuitedMeths.printDiscardReport(param);
            }catch(CribbageException e){
                System.out.println(e.getErrorMessage());
                System.out.println("type \"input-guide\" for help about the input");
                System.out.println("type \"exit\" to exit");
                
            }
            
            System.out.println("----------------------");
        }
    }
}
