package fr.miage.toulouse.projetjavas6.tableauAffichages;


import javafx.scene.control.TextField;

/**
 *
 * @author thalya
 */
public class TableauUeMention {
    private int codeId;
    private String nom;
    private int ects;
    private TextField statut;
    private String uePreV;
    private String uePreM;
        public int getCodeId() {
        return codeId;
    }

    public String getNom() {
        return this.nom;
    }

    public int getEcts() {
        return this.ects;
    }

    public TextField getStatut() {
        return this.statut;
    }

    public String getUePreV() {
        return this.uePreV;
    }

    public String getUePreM() {
        return this.uePreM;
    }
    public TableauUeMention(int codeId, String nom, int ects, String statut, String uePreV, String uePreM){
        this.codeId = codeId;
        this.nom = nom;
        this.ects = ects;
        this.statut = new TextField(statut);
        this.setStatut();
        this.uePreM = uePreM;
        this.uePreV = uePreV;
    }
    /**
     * Méthode setStatut permettant d'initaliser les champs de texte du tableau des ue de la Mention de la page étudiant afin d'obtenir une représentation visuelle plus parlante
     */
    public void setStatut(){
        this.statut.setEditable(false);
        this.statut.setMouseTransparent(true);
        if (this.statut.getText().equals("Validée")){
            this.statut.setStyle("-fx-control-inner-background: #008000");
        }
        else{
            this.statut.setStyle("-fx-control-inner-background: #fbe5e4");
        }
    }

}
