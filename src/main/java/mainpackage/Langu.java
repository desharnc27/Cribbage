/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mainpackage;

import java.io.File;
import java.io.FileFilter;

/**
 *
 * @author desharnc27
 */
public class Langu {

    //TODO: chelou id in code
    //TODO: chelou fixed nb ids
    //TODO: chelou sep
    //TODO: reinsert default english
    private static boolean initialized = false;

    private static final String fsl = System.getProperty("os.name").equals("Windows") ? "\\" : "/";

    private static final String tDelimiter = "@";
    private static final char commentChar = '*';
    private static String[][] bank = new String[256][];
    private static String[] inputGuide;
    private static String[] about;
    private static String[] langSt;
    private static int actualLang = 0;

    private final static String langFolder = "translation" + fsl;
    
    public static String getLang(int i){
        return langSt[i];
    }
    public static String getLang(){
        return langSt[actualLang];
    }
    public static int nbOfLang(){
        return langSt.length;
    }
    public static boolean setLanguage(String lang) {
        for (int i = 0; i < langSt.length; i++) {
            if (langSt[i].equals(lang)) {
                actualLang = i;
                return true;
            }
        }
        return false;
    }

    public static boolean setLanguage(int i) {
        if (i >= langSt.length || i < 0) {
            return false;
        }
        actualLang = i;
        return true;
    }

    public static String smallText(int i) {
        String res = bank[i][actualLang];

        if (res == null) {
            res = bank[i][0];
        }
        if (res == null) {
            return "mis. transl.";
        }
        return res;
    }

    public static String smallTextX(int tag, int[] args) {
        String[] arr = new String[args.length];
        for (int i = 0; i < args.length; i++) {
            arr[i] = Integer.toString(args[i]);
        }
        return smallTextX(tag, arr);
    }

    public static String smallTextX(int tag, String[] args) {
        String res = bank[tag][actualLang];
        if (res == null) {
            res = bank[tag][0];
        }
        if (res == null) {
            return "?missing tag!";
        }
        //TODO:manage exceptions of diff lengths
        
        String[] resArr = res.split("\\$",-1);
        if (args.length!=resArr.length-1){
            System.out.println("Warning: problem of args in tags of translation: length mismatch");
        }
        String[] fill = new String[resArr.length * 2 - 1];
        for (int i = 0; i < resArr.length; i++) {
            fill[2 * i] = resArr[i];
        }
        for (int i = 0; i < resArr.length - 1; i++) {
            fill[2 * i + 1] = args[i];
        }

        return String.join("", fill);
    }

    public static String inputGuide() {
        return inputGuide[actualLang];
    }

    public static String about() {
        return about[actualLang];
    }

    public static void initialize() {
        if (initialized) {
            return;
        }
        initialized = true;
        File file = new File("translation");
        File[] folders = file.listFiles(new FileFilter() {
            @Override
            public boolean accept(File f) {
                return f.isDirectory();
            }
        });

        int nbLang = folders.length;
        langSt = new String[nbLang];

        System.out.println("Folders count: " + folders.length);

        inputGuide = new String[nbLang];
        about = new String[nbLang];
        for (int i = 0; i < bank.length; i++) {
            bank[i] = new String[nbLang];
        }

        for (int lang = 0; lang < nbLang; lang++) {

            langSt[lang] = folders[lang].getName();
            String partPath = langFolder + langSt[lang] + fsl;

            String fileText = GeneralMeths.fileToString(partPath + "small.txt");
            String[] lines = fileText.split("\n");
            for (int i = 0; i < lines.length; i++) {
                if (lines[i].charAt(0) == commentChar) {
                    continue;
                }
                String[] sep = lines[i].split(" " + tDelimiter + " ");
                int idx = Integer.parseInt(sep[0], 16);
                String val = null;
                if (sep.length == 2) {
                    val = sep[1];
                }
                bank[idx][lang] = val;
            }

            inputGuide[lang] = GeneralMeths.fileToString(partPath + "inputGuide.txt");
            about[lang] = GeneralMeths.fileToString(partPath + "about_pl.txt");
        }
    }
}
