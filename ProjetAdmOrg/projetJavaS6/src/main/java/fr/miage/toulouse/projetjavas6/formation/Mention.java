package fr.miage.toulouse.projetjavas6.formation;

import fr.miage.toulouse.projetjavas6.exceptions.ValeurUniqueException;
import java.util.ArrayList;

/**
 * @author thalya
 * Classe Mention
 */
public class Mention {
    private String nom;
    private ArrayList<UeClassique> ueM;
    private ArrayList<Parcours> parcours;
    /**
     * Attribut de classe permettant de contenir toutes les mentions créées
     */
    private static ArrayList<Mention> mentions = new ArrayList<>();
    
    /**
     * Méthode static qui vérifie si le nom donné en parmaètre correspond à une mention
     * @param nomVoulu : Nom que l'on souhaite vérifier
     * @return trouve : booléen qui informe si la mention existe ou non 
     * 
     * (à voir si l'on aura besoin de réccupérer son indice pr réccupérer la mention) 
     */
    public static boolean mentionExisteDeja(String nomVoulu){
        boolean trouve = false;
        int i = 0;
        while ((i < Mention.mentions.size()) && (!trouve)){
            if (Mention.mentions.get(i).nom.equals(nomVoulu)){
                trouve = true;
            }
            i++;
        }
        return trouve;
    }
    /** Constructeur qui permet de créer une Mention en lui donnant directement la liste des UE liée à elle et la liste des parcours qu'elle contient
     * @param nom : Nom que l'on souhaite donner à la mention
     * @param ueM : Liste des Ues liées à la mention
     * @param p : Liste des parcours liés à la mention
     * @throws fr.miage.toulouse.projetjavas6.exceptions.ValeurUniqueException : Si la Mention que l'on souhaite créer existe déjà
    */
    public Mention(String nom, ArrayList<UeClassique> ueM, ArrayList<Parcours> p) throws ValeurUniqueException {
        this.nom = nom;
        this.ueM = new ArrayList<>(ueM);
        this.parcours = new ArrayList<>(p);
        if (mentionExisteDeja(nom)){
            throw new ValeurUniqueException("Mention de nom "+ nom +" existe déjà");
        }
        Mention.mentions.add(this);
    }
    /**
     * Constructeur qui permet de créer une Mention en ne lui donnant qu'un nom
     * @param nom : Nom que l'on souhaite donner à la mention
     * @throws fr.miage.toulouse.projetjavas6.exceptions.ValeurUniqueException : Si la Mention que l'on souhaite créer existe déjà
     */
    public Mention(String nom) throws ValeurUniqueException{
        this.nom = nom;
        this.ueM = new ArrayList<>();
        this.parcours = new ArrayList<>();
        if (mentionExisteDeja(nom)){
            throw new ValeurUniqueException("Mention de nom "+ nom +" existe déjà");
        }
        Mention.mentions.add(this);
    }
    
    /**
     * Méthode static qui vérifie si l'ue donné en parmaètre est déjà associée à la mention
     * @param ue : Nom que l'on souhaite vérifier
     * @return trouve : booléen qui informe si la mention existe ou non 
     * 
     * (à voir si l'on aura besoin de réccupérer son indice pr réccupérer la mention) 
     */
    public boolean ueExisteDeja(UeClassique ue){
        boolean trouve = false;
        int i = 0;
        while ((i < this.ueM.size()) && (!trouve)){
            if (this.ueM.get(i) == ue){
                trouve = true;
            }
            i++;
        }
        return trouve;
    }
    /**
     * Méthode ajouterUe qui permet d'ajouter une Ues à une Mention
     * @param ue : Ue que l'on souhaite ajouter
     * @throws ValeurUniqueException : Si l'ue est déjà lié à la Mention
     */
    public void ajouterUe(UeClassique ue) throws ValeurUniqueException{
        if (!ueExisteDeja(ue)){
            this.ueM.add(ue);
        }
        else{
            throw new ValeurUniqueException("Ue déjà ajoutée dans cette mention");
        }
    }
    /**
     * Méthode parcoursExisteDeja qui retourne vrai si le parcours existe déjà dans la Mention (s'il est déjà lié à la Mention)
     * @param p : Parcours dont on souhaite vérifier l'existante dans la Mention
     * @return vrai si le parcours est lié à la Mention, faux sinon
     */
    public boolean parcoursExisteDeja(Parcours p){
        boolean trouve = false;
        int i = 0;
        while ((i < this.parcours.size()) && (!trouve)){
            if (this.parcours.get(i) == p){
                trouve = true;
            }
            i++;
        }
        return trouve;
    }
    /**
     * méthode ajouterParcours : ajoute un parcours à une mention
     * @param p
     * @throws ValeurUniqueException : Si le parcours que l'on souhaite lié à la Mention est déjà lié
     */
    public void ajouterParcours(Parcours p) throws ValeurUniqueException {
        if (!parcoursExisteDeja(p)){
            this.parcours.add(p);
        }
        else{
            throw new ValeurUniqueException("Parcours déjà ajouté dans cette mention");
        }
        
    }

    public String getNom(){
        return this.nom;
    }
    /**
     * Méthode de classe permettant de réccupérer toutes les mentions créées
     * @return liste copie de toutes les mentions
     */
    public static ArrayList<Mention> getMentions(){
        return new ArrayList<>(Mention.mentions);
    }
    /**
     * Méthode getUes qui permet de récupérer toutes les Ues de la Mention
     * @return liste des Ues de la Mention
     */
    public ArrayList<UeClassique> getUes(){
        return new ArrayList<>(this.ueM);
    }
    /**
     * Méthode getParcours qui permet de récupérer la liste des Parcours liés à la Mention
     * @return liste des parcours
     */
    public ArrayList<Parcours> getParcours(){
        return new ArrayList<>(this.parcours);
    }
    /**
     * Méthode getMention qui permet de récupérer une Mention par son nom
     * @param nom
     * @return Mention : mention de nom : nom
     */
    public static Mention getMention(String nom){
        boolean trouve = false;
        Mention m = null;
        int i = 0;
        while ((!trouve) && (i < Mention.mentions.size())){
            if (Mention.mentions.get(i).getNom().equals(nom)){
                trouve = true;
                m = Mention.mentions.get(i);
            }
            i++;
        }
        return m;
    }
}
