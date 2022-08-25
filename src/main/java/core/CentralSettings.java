package core;

import tools.RootFinder;

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
    public static void doPreOperations() {
        RootFinder.initialize("CribbageNWT");
        Langu.initialize();
        DataForStrings.proceed();
        DataForNumbers.proceed();
        DataForStatFiles.proceed();
    }
}
