package fr.miage.toulouse.projetjavas6.tableauAffichages;

import fr.miage.toulouse.projetjavas6.etudiant.EtatSemestre;

/**
 *
 * @author thalya
 */
public class TableauUePassees {
    private int codeId;
    private String nom;
    private int ects;
    private String p;
    private String annee;
    private EtatSemestre semestre;
    private String statut;

    public TableauUePassees(int codeId, String nom, int ects, String p, String annee, EtatSemestre semestre, String Statut) {
        this.codeId = codeId;
        this.nom = nom;
        this.ects = ects;
        this.p = p;
        this.annee = annee;
        this.semestre = semestre;
        this.statut = Statut;
    }
    
    public int getCodeId() {
        return codeId;
    }

    public String getNom() {
        return nom;
    }

    public int getEcts() {
        return ects;
    }

    public String getP() {
        return p;
    }

    public String getAnnee() {
        return annee;
    }

    public EtatSemestre getSemestre() {
        return semestre;
    }

    public String getStatut() {
        return statut;
    }
}
