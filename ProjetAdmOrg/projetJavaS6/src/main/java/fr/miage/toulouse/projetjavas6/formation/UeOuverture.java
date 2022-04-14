package fr.miage.toulouse.projetjavas6.formation;

import fr.miage.toulouse.projetjavas6.exceptions.ValeurUniqueException;
import java.util.ArrayList;

/**
 *
 * @author thalya
 * Classe UeOuverture qui hérite d'Ues
 */
public class UeOuverture extends Ues {
    public UeOuverture(String nom, int codeId, int credits, ArrayList<Ues> prerequies) throws ValeurUniqueException{
        super(nom,codeId,credits,prerequies);
    }
    public UeOuverture(String nom, int codeId, int credits) throws ValeurUniqueException{
        super(nom, codeId, credits);
    }
}
