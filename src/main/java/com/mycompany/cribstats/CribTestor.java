/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.cribstats;



/**
 * 
 * Main class for tests
 * 
 * @author desharnc27
 * 
 */
public class CribTestor {
    public static void main(String args[]) {
        
        if (args.length==0){
            args = new String[]{"y c4 d4 d5 s5 s7 h7"};
        }
        
        Labels.proceed();
        StatMaker.printDiscardReport(args);
    }
}
