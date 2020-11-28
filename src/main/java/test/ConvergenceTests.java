/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import mainpackage.CentralSettings;
import mainpackage.DataForStatFiles;

/**
 *
 * @author desha
 */
public class ConvergenceTests {
    public static void main1(){
        DataForStatFiles.convergenceTest();
    }
    public static void main(String [] args){
        CentralSettings.doPreOperations();
        main1();
    }
}
