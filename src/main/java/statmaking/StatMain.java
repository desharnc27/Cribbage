package statmaking;

import core.CentralSettings;
import core.DataForStatFiles;

/**
 *
 * @author desharnc27
 */
public class StatMain {

    public static void main(String[] args) {
        CentralSettings.doPreOperations();
        int nbIter;
        try {
            nbIter = Integer.parseInt(args[0]);
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
            System.out.println("Missing or not-a-number argument. Will be set at: 4");
            nbIter = 4;
        }
        if (nbIter < 1) {
            System.out.println("Number of iterations too small. Will be raised at: 1");
            nbIter = 1;
        }
        if (nbIter > 7) {
            System.out.println("Number of iterations too big. Will be reduced at: 7");
            nbIter = 7;
        }

        DataForStatFiles.megaAnalysis(nbIter);
    }
}
