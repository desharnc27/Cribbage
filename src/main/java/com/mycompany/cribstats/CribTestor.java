/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.cribstats;



/**
 * 
 *  Testing class
 * 
 * @author desharnc27
 * 
 */
public class CribTestor {
    public static void main(String args[]) {
        
        if (args.length==0){
            args = new String[]{"y h1 h3 h4 h5 c2 dK"};
        }
        
        Labels.proceed();
        DecisionMaker.printDiscardReport(args);
    }
}
