/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mainpackage;




/**
 * 
 * Main class for user. 
 * 
 * The must run this file with appropriate arguments (see readme) to
 * get the analysis of discarding choices he could make.
 * 
 * @author desharnc27
 * 
 */
public class UserDiscardDecisioning {
    public static void main(String []args) {
        
        if (args.length==0){
            args = new String[]{"y s2 d3 c3 h9 sJ dJ"};
        }
        
        Labels.proceed();
        SuitedMeths.printDiscardReport(args);
    }
}
