package fr.miage.toulouse.projetjavas6.formation;

import fr.miage.toulouse.projetjavas6.exceptions.ValeurUniqueException;
import  fr.miage.toulouse.projetjavas6.exceptions.ParcoursNonSuiviException;
import java.util.ArrayList;
import fr.miage.toulouse.projetjavas6.etudiant.*;
/**
 *
 * @author thalya
 * Classe Parcours
 */
public class Parcours {
    private String nomP;
    
    private static ArrayList<Parcours> parcours = new ArrayList<Parcours>();
    
    
    /**
     * Méthode static qui vérifie si le nom donné en parmaètre correspond à un Parcours
     * @param nomVoulu : Nom que l'on souhaite vérifier
     * @return trouve : booléen qui informe si la mention existe ou non 
     */
    public static boolean parcoursExisteDeja(String nomVoulu){
        boolean trouve = false;
        int i = 0;
        while ((i < Parcours.parcours.size()) && (!trouve)){
            if (Parcours.parcours.get(i).nomP.equals(nomVoulu)){
                trouve = true;
            }
            i++;
        }
        return trouve;
    }
     /** Constructeur qui permet de créer un Parcours
     * @param nomP : Nom que l'on souhaite donner au parcours
     * @throws fr.miage.toulouse.projetjavas6.exceptions.ValeurUniqueException : Si le parcours que l'on souhaite créer existe déjà
    */
    public Parcours (String nomP) throws ValeurUniqueException {
        this.nomP = nomP;
        if (parcoursExisteDeja(nomP)){
            throw new ValeurUniqueException("Parcours de nom "+nomP+" existe déjà");
        }
        Parcours.parcours.add(this);
    }

    public String getNom(){
        return this.nomP;
    }
    
    /**
     * Méthode permettant à un Parcours de délivrer un diplome à un étudiant
     * @param e : L'étudiant qui va recevoir le diplome
     * @throws fr.miage.toulouse.projetjavas6.exceptions.ParcoursNonSuiviException : Si on essaye de délivrer un diplôme à un étudiant qui ne suit pas se Parcours.
     */
    public void delivrerDiplome(Etudiant e) throws ParcoursNonSuiviException {
        e.recevoirDiplome(this.nomP, this);
    }
    
    public static ArrayList<Parcours> getParcours(){
        return new ArrayList<>(Parcours.parcours);
    }

    /**
     * Méthode qui retrouve un parcours par son nom
     * @param nom
     * @return Parcours : parcours de nom : nom.
     */
    public static Parcours recupererParcours(String nom){
        boolean trouve = false;
        Parcours p = null;
        int i = 0;
        while ((!trouve)&& (i < Parcours.getParcours().size())){
            if (Parcours.getParcours().get(i).getNom().equals(nom)){
                trouve = true;
                p = Parcours.getParcours().get(i);
            }
            i++;
        }
        return p;
    }    
}



