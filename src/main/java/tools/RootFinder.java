package tools;

import java.io.File;
import java.util.Arrays;

/**
 *
 * @author desha
 */
public class RootFinder {

    private static String ROOT_NAME;
    private static String rootPath;

    /*public static String [] fileToArray(String filename){
        filename = rootPath+"paramFiles"+File.separator+filename;
        return FileUtils.toStringArr(filename);
    }
    public static void arrayToFile(String filename, String [] arr){
        filename = rootPath+"paramFiles"+File.separator+filename;
        FileUtils.toFile(filename,arr);
    }*/
    public static void initialize(String rootName) {
        ROOT_NAME = rootName;
        setRootPath();
    }

    private static void setRootPath() {
        String path = System.getProperty("user.dir") + File.separator;
        //System.out.println(path);
        int index = path.indexOf(ROOT_NAME);
        rootPath = path.substring(0, index + ROOT_NAME.length() + 1);
        System.out.println("Root was set to: " + rootPath);
    }

    public static String getRootPath() {
        return rootPath;
    }

}
