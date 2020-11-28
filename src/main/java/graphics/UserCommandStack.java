/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphics;

import java.util.ArrayList;

/**
 *
 * @author desha
 */
public class UserCommandStack {

    private final ArrayList<String> commands;
    private int idx;

    public UserCommandStack() {
        commands = new ArrayList<>();
        idx = 0;
    }

    String prec() {
        if (canPrec()) {
            idx--;
            return commands.get(idx);
        }
        return null;
    }

    String next() {
        if (canNext()) {
            idx++;
            return commands.get(idx);
        }
        return null;
    }

    boolean canPrec() {
        return idx > 0;
    }

    boolean canNext() {
        return idx < lastIdx();
    }

    int lastIdx() {
        return commands.size() - 1;
    }

    void add(String s) {
        if (lastIdx() >= 0 && s.equals(commands.get(lastIdx()))) {
            idx = lastIdx();
            return;
        }
        idx = commands.size();
        commands.add(s);
    }

}
