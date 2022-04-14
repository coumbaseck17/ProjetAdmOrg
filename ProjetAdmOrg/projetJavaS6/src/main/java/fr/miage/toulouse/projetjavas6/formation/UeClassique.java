package fr.miage.toulouse.projetjavas6.formation;

import fr.miage.toulouse.projetjavas6.exceptions.ValeurUniqueException;
import java.util.ArrayList;

/**
 *
 * @author thalya
 Classe UeClassique qui hérite d'Ues
 */
public class UeClassique extends Ues {
    private Mention mention;
    public UeClassique(String nom, int codeId, int credits, ArrayList<Ues> prerequies) throws ValeurUniqueException{
        super(nom,codeId,credits,prerequies);
        this.mention = null;
    }
    public UeClassique(String nom, int codeId, int credits) throws ValeurUniqueException{
        super(nom, codeId, credits);
        this.mention = null;
    }
    
    /**
     * Méthode lierMention, qui permet de lier une Mention à notre UeClassique
     * @param m : Mention à laquelle on souhaite rattacher notre ue
     */
    public void lierMention(Mention m){
        this.mention = m;
    }
    
   

}
