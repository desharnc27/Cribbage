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

    /**
     * Sets index to previous command and returns  it
     */
    String prec() {
        if (canPrec()) {
            idx--;
            return commands.get(idx);
        }
        return null;
    }
    /**
     * Sets index to next command and returns  it
     */
    String next() {
        if (canNext()) {
            idx++;
            return commands.get(idx);
        }
        return null;
    }
    /**
     * Returns true there exists a previous command, false otherwise
     * @return true there exists a previous command, false otherwise
     */
    public boolean canPrec() {
        return idx > 0;
    }
    /**
     * Returns true there exists a next command, false otherwise
     * @return true there exists a next command, false otherwise
     */
    public boolean canNext() {
        return idx < lastIdx();
    }
    /**
     * Returns the index of the last command
     * @return the index of the last command
     */
    public int lastIdx() {
        return commands.size() - 1;
    }
    /**
     * Adds a new command at the end of the command list and sets the index on it.
     * @param s the new command
     */
    void add(String s) {
        if (lastIdx() >= 0 && s.equals(commands.get(lastIdx()))) {
            idx = lastIdx();
            return;
        }
        idx = commands.size();
        commands.add(s);
    }

}
