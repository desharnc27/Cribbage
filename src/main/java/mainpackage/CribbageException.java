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
public class CribbageException extends Exception {

    CribbageException(String message) {
        super(message);
    }
    public String getErrorMessage(){
        return "Invalid cribbage hand input: "+ this.getMessage();
    }
}
