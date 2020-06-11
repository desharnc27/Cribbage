/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mainpackage;



/**
 *
 * This class contains labels and methods for translating objects 
 * into their string representation and vice-versa.
 * 
 * @author desharnc27
 * 
 * 
 */
public class DataForStrings {
    
    /**
     * String representation of all card numbers
     */
    static String [] strNum;
    /**
     * String representation of all card suits
     */
    static String [] strSuit;
    
    /**
     * Method to execute to fill the static arrays, must be run at the beginning of any main.
     */
    public static void proceed(){
        strNum=new String [14];
        strSuit=new String []{"sp","cl","di","he"};
        for (int i=2;i<10;i++)
            strNum[i]=Integer.toString(i);
        strNum[1]="A";
        strNum[10]="T";
        strNum[11]="J";
        strNum[12]="Q";
        strNum[13]="K";
        
        
    }
    /**
     * Prints a String representation of a hand (with specified flip) and its score
     * @param commo the flip
     * @param hand the cards of the hand
     * @param score the score
     */
    public static void print(Card commo, Card[] hand, int score) {
        System.out.print(commo.verbos()+",,");
        for (int i=0;i<hand.length;i++)
            System.out.print(hand[i].verbos()+",");
        System.out.print(" "+score);
        System.out.println();
        
    }
    /**
     * Prints a String representation of a set of cards.
     * @param hand the cards of the hand
     */
    public static void print(Card[] hand) {
        for (int i=0;i<hand.length;i++)
            System.out.print(hand[i].verbos()+",");
        System.out.println();
        
    }
    /**
     * Returns a String representation of a set of cards.
     * @param arr an array of cards
     * @return a String representation of a set of cards.
     */
    public static String verboArr(Card[] arr){
        String res=arr[0].verbos();
        for (int i=1;i<arr.length;i++)
            res+=(","+arr[i].verbos());
        return res;
        
    }
    /**
     * Returns a String representation of a set of unsuited cards.
     * @param arr the array containing the unsuited cards
     * @return a String representation of a set of unsuited cards.
     */
    public static String verboArr(int[] arr){
        String res=arr[0]+"";
        for (int i=1;i<arr.length;i++)
            res+=(","+arr[i]);
        return res;
        
    }
    
    /**
     * Returns the suit represented by user input
     *
     * @param c user input
     * @return the suit represented by user input.
     */

    public static int suitEncode(char c) {
        int suit;
        if (c > 'Z') {
            c = (char) (c - 'z' + 'Z');
        }
        switch (c) {
            case 'S':
                suit = 0;
                break;
            case 'C':
                suit = 1;
                break;
            case 'D':
                suit = 2;
                break;
            case 'H':
                suit = 3;
                break;
            default:
                return -1;
        }
        return suit;
    }

    /**
     * Returns the number represented by user input
     *
     * @param c user input
     * @return the number represented by user input.
     */
    public static int numEncode(char c) {
        int num;
        if (c > 'Z') {
            c = (char) (c - 'z' + 'Z');
        }
        switch (c) {
            case 'A':
                num = 1;
                break;
            case 'T':
                num = 10;
                break;
            case 'J':
                num = 11;
                break;
            case 'Q':
                num = 12;
                break;
            case 'K':
                num = 13;
                break;
            default:
                num = c - '0';
                if (num < 1 || num > 9) {
                    return -1;
                }

        }
        return num;
    }
    /**
     * Converts a number into a card symbol
     * @param i a number
     * @return its card symbol representation, as a char
     * 
     * Ex: symbolOfNum(4) return '4' and symbolOfNum(12) returns 'Q';
     */
    public static String symbolOfNum(int i) {
        return strNum[i];
    }

    /**
     * Converts a String representation of many cards in an array of int (id's)
     * of the same size.
     *
     * @param cs the String array
     * @return the array of int containing the id's of each card.
     */
    public static int[] verboToIds(String[] cs) {
        int nc = cs.length;
        int[] ids = new int[nc];
        for (int i = 0; i < nc; i++) {
            char cha = cs[i].charAt(0);
            char nuc = cs[i].charAt(cs[i].length() - 1);
            int suit = suitEncode(cha);
            int num = numEncode(nuc);
            /*if (suit==-1){
                String message="Invalid suit "+cha+" for card "+cs[i]+"";
                throw(new Exception(message));
            }*/
            if (suit==-1||num==-1)
                ids[i]=-1;
            else
                ids[i] = Card.suitNumToId(suit, num);

        }
        return ids;
    }
    /**
     * Returns a String representation a score average in a way that it will align correctly.
     * @param i useless parameter (was suppose to adjust the indent of d, not working)
     * @param d a score average
     * @return a String representation the score average
     */
    public static String df(int i, double d) {
        //return String.valueOf(d);
        if (d < 0) {
            return "-" + dfx(i, -d);
        } else {
            return " " + dfx(i, d);
        }

    }

    private static String dfx(int i, double d) {
        //return String.valueOf(d);
        
        if (d < 1) {
            return " " + String.format("%.7g", d);
        } else if (d < 10) {
            return " " + String.format("%.8g", d);
        } else {
            return String.format("%.9g", d);
        }
        /*
        if (d < 1) {
        } else if (d < 10) {
            i++;
        } else {
            i+=2;
        }
        String stFormat = "%."+i+"g";
        return String.format(stFormat, d);*/
    }
    
}
