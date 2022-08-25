package test;

import core.CentralSettings;
import core.DataForStatFiles;

/**
 *
 * @author desha
 */
public class ConvergenceTests {

    public static void main1() {
        DataForStatFiles.convergenceTest();
    }

    public static void main(String[] args) {
        CentralSettings.doPreOperations();
        main1();
    }
}
