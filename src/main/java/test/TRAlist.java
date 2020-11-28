/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author desha
 */
public class TRAlist {
    ArrayList<TRA> li =new ArrayList<TRA>();
    public void sort(int comId){
        TRAcompa tc=new TRAcompa(comId);
        Collections.sort(li,tc);
    }
    
}
