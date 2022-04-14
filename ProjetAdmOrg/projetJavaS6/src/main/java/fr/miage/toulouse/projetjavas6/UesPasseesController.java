package fr.miage.toulouse.projetjavas6;

import fr.miage.toulouse.projetjavas6.tableauAffichages.TableauUePassees;
import fr.miage.toulouse.projetjavas6.etudiant.Echouees;
import fr.miage.toulouse.projetjavas6.etudiant.Etudiant;
import fr.miage.toulouse.projetjavas6.etudiant.Validees;
import fr.miage.toulouse.projetjavas6.formation.Ues;
import fr.miage.toulouse.projetjavas6.postes.Referent;
import java.util.ArrayList;
import java.util.Collections;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;

/**
 *
 * @author Coumba
 */
public class UesPasseesController {
    
    private Etudiant etu= App.getEtu();
    @FXML
    private Text champPrenomNom;
    @FXML
    private Text numeroEt;
    @FXML
    private Text mentionParcours;
    @FXML
    private Text creditsSemestre;
    @FXML
    private Text creditsAcquis;
    @FXML
    private Label referent;
    @FXML
    private Text annee;
    @FXML
    private Text semestre;
    
    @FXML
    private TableView uePassees;
    @FXML
    private TableColumn nomUePassee;
    @FXML
    private TableColumn codeIdUePassee;
    @FXML
    private TableColumn creditsUePassee;
    @FXML
    private TableColumn parcoursUePassee;
    @FXML
    private TableColumn anneeUePassee;
    @FXML
    private TableColumn semestreUePassee;
    @FXML
    private TableColumn statutUePassee;
    
    public void retourPageEtu() {
        App.setRoot("PageEtudiant");
    }
    
    /**
     * Initializes the controller class.
     */
      
    public void initialize(){
        this.champPrenomNom.setText(this.etu.getNom()+" "+this.etu.getPrenom());
        String champ = "Numéro étudiant : "+this.etu.getNumeroEt();
        this.numeroEt.setText(champ);
        this.annee.setText("Année universitaire : "+this.etu.getAnnee());
        this.mentionParcours.setText("Mention "+this.etu.getMention().getNom()+ " parcours "+ this.etu.getP().getNom());
        this.semestre.setText("Semestre acutel : "+this.etu.getSemestre().toString());
        champ="Crédits : "+this.etu.getCreditsPotentielSemestre()+" ECTS";
        this.creditsSemestre.setText(champ);
        Referent ref = this.etu.getR();
        if (ref == null){
            this.referent.setVisible(false);
        }
        else{
            this.referent.setText("Référent : "+ref.getNom() +" "+ ref.getPrenom() +" "+ ref.getNumRef());
            this.referent.setVisible(true);
        }
        ObservableList<TableauUePassees> listPassees = FXCollections.observableArrayList();
        ArrayList<Validees> validees = this.etu.getValidees();
        Collections.reverse(validees);
        ArrayList<Echouees> echouees = this.etu.getEchouees();
        Collections.reverse(echouees);
        for (Validees v : validees){
            Ues u = v.getUe();
            TableauUePassees t = new TableauUePassees(u.getCodeId(), u.getNom(), u.getCredits(),v.getEtudiant().getP().getNom(), v.getAnnee(), v.getSemestre(), "Validée" );
            listPassees.add(t);
        }
        for (Echouees v : echouees){
            Ues u = v.getUe();
            TableauUePassees t = new TableauUePassees(u.getCodeId(), u.getNom(), u.getCredits(),v.getEtudiant().getP().getNom(), v.getAnnee(), v.getSemestre(), "Echouée" );
            listPassees.add(t);
        }
        this.nomUePassee.setCellValueFactory(new PropertyValueFactory<TableauUePassees, String>("nom"));
        this.codeIdUePassee.setCellValueFactory(new PropertyValueFactory<TableauUePassees, String>("codeId"));
        this.creditsUePassee.setCellValueFactory(new PropertyValueFactory<TableauUePassees, String>("ects"));
        this.parcoursUePassee.setCellValueFactory(new PropertyValueFactory<TableauUePassees, String>("p"));
        this.anneeUePassee.setCellValueFactory(new PropertyValueFactory<TableauUePassees, String>("annee"));
        this.semestreUePassee.setCellValueFactory(new PropertyValueFactory<TableauUePassees, String>("semestre"));
        this.statutUePassee.setCellValueFactory(new PropertyValueFactory<TableauUePassees, String>("statut"));
        this.uePassees.setItems(listPassees);
    }
}
