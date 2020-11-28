/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import java.util.Comparator;

/**
 *
 * @author desha
 */
public class TRAcompa implements Comparator<TRA>{
    
    
            
    int compaId;
    @Override
    public int compare(TRA o1, TRA o2) {
        int comp =o1.vals[compaId]-o2.vals[compaId];
        return comp;
    }
    public TRAcompa(int id){
        compaId=id;
    }
    
}
