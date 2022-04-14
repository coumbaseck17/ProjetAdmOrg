package fr.miage.toulouse.projetjavas6.postes;

import fr.miage.toulouse.projetjavas6.etudiant.Etudiant;
import fr.miage.toulouse.projetjavas6.exceptions.ParcoursNonSuiviException;
import fr.miage.toulouse.projetjavas6.formation.Mention;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author thalya
 */
public class Referent extends Poste {
    private int numRef;
    private ArrayList<Etudiant> etudiants;
    
    private static ArrayList<Referent> referents = new ArrayList<Referent>();
    private static HashMap<Mention, ArrayList<Referent>> referentsParMention = new HashMap<Mention, ArrayList<Referent>>();
    
    public int getNumRef() {
        return numRef;
    }
    /**
     * Constructeur de Referent.
     * @param m : Mention à laquelle est lié le Referent
     * @param nom : nom du referent
     * @param prenom : prenom du référent
     * @param numRef : numero d'identification du referent
     */
    public Referent(Mention m, String nom, String prenom, int numRef){
        super(m, nom, prenom);
        this.numRef = numRef;
        this.etudiants = new ArrayList<Etudiant>();
        if (Referent.referentsParMention.containsKey(m)){
            Referent.referentsParMention.get(m).add(this);
        }
        else{
            ArrayList <Referent> ref = new ArrayList<Referent>();
            ref.add(this);
            Referent.referentsParMention.put(m, ref);
        }
        Referent.referents.add(this);
    }
    /**
     * Méthode statique getReferents permettant de récupérer tous les référents
     * @return liste de tous les référent
     */
    public static ArrayList<Referent> getReferents(){
        return new ArrayList<Referent>(Referent.referents);
    }
    /**
     * Méthode statique getReferentParMention permettant de récupérer tous les référents d'une mention
     * @param m : Mention
     * @return liste de tous les référents liés à cette mention
     */
    public static ArrayList<Referent> getReferentParMention(Mention m){
        if (Referent.referentsParMention.containsKey(m)){
            return new ArrayList<Referent>(Referent.referentsParMention.get(m));
        }
        else {
            return new ArrayList<Referent> ();
        }
    }
    /**
     * Méthode suivreEtudiant permettant d'assigner des étudiant au référent
     * @param e : étudiant que l'on souhaite faire suivre par le référent
     */
    public void suivreEtudiant(Etudiant e) throws ParcoursNonSuiviException{
        if (e.getMention() == this.getM()){
            this.etudiants.add(e);
            e.setR(this);
        }
        else {
            String msg = "L'étudiant "+ e.getNom() +" ne fait pas partie de la mention "+ this.getM().getNom();
            throw new ParcoursNonSuiviException(msg);
        }
        
    }
    /**
     * Méthode nePlusSuivreEtudiant permettant d'arrêter de faire suivre un étudiant par un référent
     * @param e : étudiant que l'on souhaite ne plus faire suivre par ce référent
     * @throws ParcoursNonSuiviException : L'étudiant n'est pas suivi par ce Référent
     */
    public void nePlusSuivreEtudiant(Etudiant e) throws ParcoursNonSuiviException {
        boolean trouve = false;
        int i = 0;
        while ((i < this.etudiants.size()) && (!trouve)){
            if (this.etudiants.get(i) == e){
                trouve = true;
            }
            i++;
        }
        if (trouve){
            this.etudiants.remove(e);
        }
        else{
            throw new ParcoursNonSuiviException("Erreur, le référent ne suit pas cet étudiant");
        }
    }
    
    public ArrayList<Etudiant> getEtudiants(){
        return new ArrayList<Etudiant> (this.etudiants);
    }
    
    /**
     * Méthode statique permettant de récupérer un référent par son numéro d'identification
     * @param numRef : numéro d'identification du référent que l'on cherche
     * @return Referent ou null si aucun référent n'est trouvé
     */
    public static Referent getReferent(int numRef){
        boolean trouve = false;
        int i = 0;
        Referent ref = null;
        while ((i < Referent.referents.size()) && (!trouve)){
            if (Referent.referents.get(i).numRef == numRef){
                trouve = true;
                ref = Referent.referents.get(i);
            }
            i++;
        }
        return ref;
    }
}
