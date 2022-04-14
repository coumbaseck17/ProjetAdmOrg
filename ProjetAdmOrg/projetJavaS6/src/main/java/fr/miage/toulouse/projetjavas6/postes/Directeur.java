package fr.miage.toulouse.projetjavas6.postes;

import fr.miage.toulouse.projetjavas6.formation.Mention;
import java.util.HashMap;

/**
 *
 * @author thalya
 */
public class Directeur extends Poste {
    private Mention m;
    private String nom;
    private String prenom;
    
    /**
     * Dictionnaire de classe, contient tous les directeurs en les liant à leur Mention.
    */
    private static HashMap<Mention, Directeur> directeurs = new HashMap<Mention, Directeur>();
    
    /**
     * Constructeur de Directeurs.
     * @param m : Mention à laquelle est liée le directeur
     * @param nom : nom du directeur
     * @param prenom : prénom du directeur
     */
    public Directeur (Mention m, String nom, String prenom){
        super(m, nom, prenom);
        Directeur.directeurs.put(m, this);
    }  

    public static Directeur getDirecteur(Mention m){
        return Directeur.directeurs.get(m);
    }  
}
