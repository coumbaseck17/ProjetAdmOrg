package fr.miage.toulouse.projetjavas6.etudiant;
import fr.miage.toulouse.projetjavas6.formation.*;

/**
 *
 * @author thalya
 */
public abstract class UesEtat {
    private String annee;
    private EtatSemestre semestre;
    private Ues ue;
    private Etudiant etudiant;

    public String getAnnee() {
        return annee;
    }

    public EtatSemestre getSemestre() {
        return semestre;
    }

    public Ues getUe() {
        return ue;
    }

    public Etudiant getEtudiant() {
        return etudiant;
    }
    
    /**
     * Constructeur d'UesEtat (parent de Validees et Echouees)
     * @param annee : année à laquelle on valide ou échoue l'ue
     * @param semestre : semestre à laquelle on valide ou échoue l'ue
     * @param ue :  ue que l'ont a passé
     * @param e : étudiant qui a passé cette Ues
     */
    public UesEtat(String annee, EtatSemestre semestre, Ues ue, Etudiant e){
        this.annee = annee;
        this.semestre = semestre;
        this.ue = ue;
        this.etudiant = e;
    }

    
    
}
