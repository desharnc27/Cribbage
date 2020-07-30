/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mainpackage;

/**
 *
 * @author desharnc27
 */
public class TranslateNode implements Comparable<TranslateNode>{
    private final String [] translations;
    private final String tag;
    public TranslateNode(String s){
        translations=new String[Langu.nbOfLang()];
        tag=s;
    }
    void set(String s, int lang){
        translations[lang]=s;
    }
    public String get(int lang){
        //TODO: default?
        return translations[lang];
    }
    @Override
    public int compareTo(TranslateNode t) {
        int res=tag.compareTo(t.tag);
            
        return res;
    }
}
