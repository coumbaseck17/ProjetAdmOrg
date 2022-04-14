package fr.miage.toulouse.projetjavas6.exceptions;

/**
 *
 * @author thalya
 */
public class CodeErroneException extends Exception {
    public CodeErroneException(String message){
        super(message);
    }
    public CodeErroneException(){
        super();
    }
}
