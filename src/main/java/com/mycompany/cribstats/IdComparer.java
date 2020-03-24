/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.cribstats;

import java.util.Comparator;

/**
 * Comparator that orders cards by their id's (useless up to now).
 * @author desharnc27
 */
public class IdComparer implements Comparator<Card>{

    @Override
    public int compare(Card t, Card t1) {
        int comp= t.getId()-t1.getId();
        if (comp < 0)
            return -1;
        if (comp > 0)
            return 1;
        return 0;
    }
    
}
