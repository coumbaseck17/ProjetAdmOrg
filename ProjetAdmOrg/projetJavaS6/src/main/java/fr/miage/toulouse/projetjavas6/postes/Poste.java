package fr.miage.toulouse.projetjavas6.postes;

import fr.miage.toulouse.projetjavas6.formation.Mention;

/**
 *
 * @author thalya
 */
public class Poste {
    private Mention m;
    private String nom;
    private String prenom;
    
    public Poste(Mention m, String nom, String prenom){
        this.m = m;
        this.nom = nom;
        this.prenom = prenom;
    }
    
    public Mention getM() {
        return m;
    }
    public String getNom() {
        return nom;
    }
    public String getPrenom() {
        return prenom;
    }
}
