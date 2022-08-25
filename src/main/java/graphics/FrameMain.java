package graphics;

import javax.swing.JFrame;
import core.CentralSettings;
import core.DataForStatFiles;

/**
 *
 * @author desharnc27
 */
public class FrameMain {

    public static void main0() {
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

    public static void main(String[] args) {
        main0();
    }
}
