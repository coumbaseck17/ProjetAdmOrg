package fr.miage.toulouse.projetjavas6.tableauAffichages;

import javafx.scene.control.Button;

/**
 *
 * @author thalya
 */
public class TableauUeAccessibles {
    private String nom;
    private int codeId;
    private int credits;
    private String prerequies;
    private String mention;
    private Button inscrire;
    
    public TableauUeAccessibles(String nom, int codeId, int credits, String prerequies, String m) {
        this.nom = nom;
        this.codeId = codeId;
        this.credits = credits;
        this.prerequies = prerequies;
        this.mention = m;
        this.inscrire = new Button("Inscrire");
    }
    public String getNom() {
        return this.nom;
    }

    public int getCodeId() {
        return this.codeId;
    }

    public int getCredits() {
        return this.credits;
    }

    public String getPrerequies() {
        return this.prerequies;
    }

    public String getMention() {
        return this.mention;
    }

    public Button getInscrire() {
        return this.inscrire;
    }

}
