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
    
    
    private boolean initError=false;
    CribbageException(String message) {
        
        super(message);
    }
    CribbageException(String message,boolean bool) {       
        super(message);
        initError=bool;
    }
    public String getErrorMessage(){
        //return "Invalid cribbage hand input: "+ this.getMessage();
        return Langu.smallTextX(0xe0, new String[]{this.getMessage()});
    }
    public boolean isInitError(){
        return initError;
    }
}
