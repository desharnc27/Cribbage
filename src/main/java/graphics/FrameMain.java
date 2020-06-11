package graphics;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import javax.swing.JFrame;
import mainpackage.DataForStatFiles;
import mainpackage.UserDiscardDecisioning;

/**
 *
 * @author desharnc27
 */
public class FrameMain {
    
    private static boolean pegAvail=false;
    //private static int topStatLevel;
    private static boolean interfaceProg=false;
    /*private static void setTopStatLevel(){
        topStatLevel=DataForStatFiles.getLatestLevel();
    }
    public static int getTopStatLevel(){
        return topStatLevel;
    }*/
    public static boolean pegAvailable(){
        return pegAvail;
    }
    public static boolean onInterfaceProg(){
        return  interfaceProg;
    }
    
    
    
    public static void main(String[] args) {
        UserDiscardDecisioning.doPreOperations();
        DataForStatFiles.loadLatestStats();
        //interProg=true;
        MyFrame frame = new MyFrame();
        frame.setResizable(true);
        frame.setVisible(true);
        frame.setSize(800, 800);
        System.out.println("TestGraphics run");

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        
    }
}
