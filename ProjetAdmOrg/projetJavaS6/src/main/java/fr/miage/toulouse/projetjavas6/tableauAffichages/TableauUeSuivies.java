package fr.miage.toulouse.projetjavas6.tableauAffichages;

import javafx.scene.control.Button;

/**
 *
 * @author thalya
 */
public class TableauUeSuivies {
    private String nom;
    private int codeId;
    private int credits;
    private Button echouer;
    private Button valider;
    
    public String getNom() {
        return nom;
    }

    public int getCodeId() {
        return codeId;
    }

    public int getCredits() {
        return credits;
    }

    public Button getEchouer() {
        return echouer;
    }

    public Button getValider() {
        return valider;
    }

    
    public TableauUeSuivies(String nom, int codeId, int credits) {
        this.nom = nom;
        this.codeId = codeId;
        this.credits = credits;
        this.echouer = new Button("Echouer");
        this.valider = new Button("Valider");
    }
    
    
}
