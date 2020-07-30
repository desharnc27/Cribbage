/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mainpackage;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.Collections;

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

    private static String[] inputGuide;
    private static String[] about;
    private static String[] langSt;
    private static int actualLang = 0;

    private static final ArrayList<TranslateNode> bank = new ArrayList<>();

    private final static String langFolder = "translation" + fsl;

    public static String getLang(int i) {
        return langSt[i];
    }

    public static String getLang() {
        return langSt[actualLang];
    }

    public static int nbOfLang() {
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

    public static int getIdx(String tag) {
        //Chelou create node to binarysearch
        TranslateNode temp = new TranslateNode(tag);
        return Collections.binarySearch(bank, temp);
    }

    public static String smallText(String tag) {
        int idx = getIdx(tag);
        if (idx < 0) {
            System.out.println("Warning: unknown tag");
            return null;
        }
        TranslateNode node = bank.get(idx);
        String res = node.get(actualLang);
        //String res = bank[i][actualLang];
        if (res==null)
            res=node.get(0);     
        if (res == null) {
            return "mis. transl.";
        }
        return res;
    }

    public static String smallTextX(String tag, int[] args) {
        String[] arr = new String[args.length];
        for (int i = 0; i < args.length; i++) {
            arr[i] = Integer.toString(args[i]);
        }
        return smallTextX(tag, arr);
    }

    public static String smallTextX(String tag, String[] args) {
        int idx = getIdx(tag);
        if (idx < 0) {
            System.out.println("Warning: unknown tag");
            return null;
        }
        TranslateNode node = bank.get(idx);
        String res = node.get(actualLang);
        //String res = bank[i][actualLang];
        if (res==null)
            res=node.get(0);     
        if (res == null) {
            return "mis. transl.";
        }
        //TODO:manage exceptions of diff lengths

        String[] resArr = res.split("\\$", -1);
        if (args.length != resArr.length - 1) {
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
        String res=inputGuide[actualLang];
        if (res==null)
            res=inputGuide[0];
        return res;
    }

    public static String about() {
        String res=about[actualLang];
        if (res==null)
            res=about[0];
        return res;
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
                if (!f.isDirectory()) {
                    return false;
                }
                return (f.getName().length() == 3);
            }
        });
        //Bring english to 0 (default)
        for (int i = 0; i < folders.length; i++) {
            if (folders[i].getName().equals("eng")) {
                File temp = folders[0];
                folders[0] = folders[i];
                folders[i] = temp;
                break;
            }
        }

        int nbLang = folders.length;
        langSt = new String[nbLang];

        System.out.println("Folders count: " + folders.length);

        inputGuide = new String[nbLang];
        about = new String[nbLang];
        /*for (int i = 0; i < bank.length; i++) {
            bank[i] = new String[nbLang];
        }*/

        for (int lang = 0; lang < nbLang; lang++) {

            langSt[lang] = folders[lang].getName();
            String partPath = langFolder + langSt[lang] + fsl;

            String fileText = GeneralMeths.fileToString(partPath + "small.txt");
            if (fileText==null)
                continue;
            String[] lines = fileText.split("\n");
            for (int i = 0; i < lines.length; i++) {
                if (lines[i].length() < 3 || lines[i].charAt(0) == commentChar) {
                    continue;
                }
                String[] sep = lines[i].split(" " + tDelimiter + " ");
                String tag = sep[0];
                if (lang == 0) {
                    TranslateNode tn = new TranslateNode(tag);
                    bank.add(tn);
                    tn.set(sep[1], lang);
                    continue;
                }
                int idx = Langu.getIdx(tag);
                if (idx < 0) {
                    System.out.println("Warning: unknown tag");
                    continue;
                }
                bank.get(idx).set(sep[1], lang);

                /*String val = null;
                if (sep.length == 2) {
                    val = sep[1];
                }
                bank[idx][lang] = val;*/
            }
            if (lang == 0) {
                Collections.sort(bank);
            }
            inputGuide[lang] = GeneralMeths.fileToString(partPath + "inputUserGuide.txt");
            about[lang] = GeneralMeths.fileToString(partPath + "about_pl.txt");
        }
    }
}
