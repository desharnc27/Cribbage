/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package weightreportmaking;

import mainpackage.CentralSettings;
import mainpackage.DataForStatFiles;

/**
 *
 * @author desharnc27
 */
public class WeightReportMaker {
    public static void main(String args[]) {
       CentralSettings.doPreOperations();
       DataForStatFiles.megaAnalysis(7);
    }
}
