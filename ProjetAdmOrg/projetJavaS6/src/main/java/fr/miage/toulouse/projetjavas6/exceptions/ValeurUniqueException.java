package fr.miage.toulouse.projetjavas6.exceptions;

/**
 *
 * @author thalya
 */
public class ValeurUniqueException extends Exception {
    public ValeurUniqueException(String message){
        super(message);
    }
    public ValeurUniqueException(){
        super();
    }
}
