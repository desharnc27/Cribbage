package graphics;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import javax.swing.JFrame;
import mainpackage.CentralSettings;
import mainpackage.DataForStatFiles;
import cmdline.UserDiscardDecisioning;

/**
 *
 * @author desharnc27
 */
public class FrameMain {
    
    
    
    
    public static void main(String[] args) {
        CentralSettings.doPreOperations();
        DataForStatFiles.loadLatestStats();
        //interProg=true;
        MyFrame mainFrame = new MyFrame("CribApp");
        mainFrame.setResizable(true);
        mainFrame.setVisible(true);
        mainFrame.setSize(800, 800);
        System.out.println("TestGraphics run");
        
        JFrame textFrame=new JFrame();
        
        
        
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        
    }
}
