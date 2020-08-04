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
        MyFrame frame = new MyFrame();
        frame.setResizable(true);
        frame.setVisible(true);
        frame.setSize(800, 800);
        System.out.println("TestGraphics run");

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        
    }
}
