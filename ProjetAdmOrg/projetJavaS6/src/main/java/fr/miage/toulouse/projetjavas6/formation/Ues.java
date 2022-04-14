package fr.miage.toulouse.projetjavas6.formation;

import fr.miage.toulouse.projetjavas6.exceptions.luiMemePreException;
import fr.miage.toulouse.projetjavas6.exceptions.ValeurUniqueException;
import java.util.ArrayList;

/**
 *
 * @author thalya
 * Classe Ues
 */
public abstract class Ues {
    private String nom;
    private int codeId;
    private int credits;
    private ArrayList<Ues> prerequies;
    private static ArrayList<Ues> uesT = new ArrayList<Ues>();
    
    
    public int getCredits(){
        return this.credits;
    }
    public ArrayList<Ues> getPrerequies(){
        return new ArrayList<Ues>(this.prerequies);
    }
    public int getCodeId(){
        return this.codeId;
    }
    public String getNom(){
        return this.nom;
    }
    public static ArrayList<Ues> getUes(){
        return new ArrayList<>(uesT);
    }
    /**
     * Méthode statique permettant de récupérer une Ue par son code d'id
     * @param codeId : code d'id de l'ue que l'on souhaite récupérer
     * @return Ues : l'ue que l'on cherche
     * @throws NullPointerException : Si l'Ue n'existe pas
     */
    public static Ues getUe(int codeId) throws NullPointerException {
        Ues ue = null;
        boolean trouve = false;
        int i = 0;
        while ((!trouve) && (i < Ues.uesT.size())){
            if (codeId == Ues.uesT.get(i).codeId){
                trouve = true;
                ue = Ues.uesT.get(i);
            }
            i++;
        }
        if (trouve){
            return ue;
        }
        else{
            throw new NullPointerException("Valeur n'existe pas");
        }
        
    }
    /**
     * Constructeur d'Ues à partir d'un tableau d'ue prérequies
     * @param nom : nom de l'ue
     * @param codeId : code d'id de l'ue
     * @param credits : crédits que rapporte l'ue
     * @param prerequies : liste des ues prérequies à cette ue
     * @throws ValeurUniqueException : Si l'ue que l'on souhaite ajouter existe déjà
     */
    public Ues(String nom, int codeId, int credits, ArrayList<Ues> prerequies) throws ValeurUniqueException{
        if (!Ues.dejaExistant(codeId)){
            this.nom = nom;
            this.codeId = codeId;
            this.credits = credits;
            this.prerequies = prerequies;
            Ues.uesT.add(this);
        }
        else{
            throw new ValeurUniqueException("Code ID déjà existant : " + codeId);
        }
    }
    /**
     * Constructeur d'Ues sans le tableau des ues prérequis
     * @param nom : nom de l'ue
     * @param codeId : code d'id de l'ue
     * @param credits : crédits que rapporte l'ue
     * @throws ValeurUniqueException : Si l'ue que l'on souhaite ajouter existe déjà
     */
    public Ues(String nom, int codeId, int credits) throws ValeurUniqueException{
        if (!Ues.dejaExistant(codeId)){
            this.nom = nom;
            this.codeId = codeId;
            this.credits = credits;
            this.prerequies = new ArrayList<>();
            Ues.uesT.add(this);
        }
        else{
            throw new ValeurUniqueException("Code ID déjà existant : " + codeId);
        }
    }
    
    /**
     * Methode ajouterPrerequie : permet d'ajouter une ue en tant que prérequie de l'ue actuelle
     * @param ue : ue que l'ont souhaite ajouter en tant que prérequie
     * @throws luiMemePreException : Si on essaye d'ajouter une Ue en tant que prérequie d'elle-même
     */
    public void ajouterPrerequie(Ues ue) throws luiMemePreException {
        if (ue == this){
            throw new luiMemePreException("Copie de lui même UE :"+this.codeId);
        }
        this.prerequies.add(ue);
    }
    /**
     * Méthode statique dejaExistant : vérifie si le code donné en paramètre est déjà rattaché à une Ue
     * @param codeId : code d'id que l'on souhaite vérifier
     * @return vrai si l'ue de code codeId existe déjà, faux sinon
     */
    public static boolean dejaExistant(int codeId){
        boolean trouve = false;
        int i = 0;
        while ((!trouve) && (i < Ues.uesT.size())){
            if (Ues.uesT.get(i).codeId== codeId){
                trouve = true;
            }
            i++;
        }
        return trouve;
    }
    
    
    
}
