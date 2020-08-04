/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mainpackage;

/**
 *
 * @author desharnc27
 */
public class CentralSettings {

    private static boolean pegAvail = false;

    //private static int topStatLevel;
    //private static boolean interfaceProg=false;
    /*private static void setTopStatLevel(){
        topStatLevel=DataForStatFiles.getLatestLevel();
    }
    public static int getTopStatLevel(){
        return topStatLevel;
    }*/
    public static boolean pegAvailable() {
        return pegAvail;
    }
    /*public static boolean onInterfaceProg(){
        return  interfaceProg;
    }*/
        public static void doPreOperations(){
        Langu.initialize();
        DataForStrings.proceed();
        DataForNumbers.proceed();
        DataForStatFiles.proceed();
    }
}
