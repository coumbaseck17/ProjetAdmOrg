package fr.miage.toulouse.projetjavas6;
import fr.miage.toulouse.projetjavas6.tableauAffichages.TableauUePassees;
import fr.miage.toulouse.projetjavas6.tableauAffichages.TableauUeSuivies;
import fr.miage.toulouse.projetjavas6.tableauAffichages.TableauUeAccessibles;
import fr.miage.toulouse.projetjavas6.tableauAffichages.TableauUeMention;
import fr.miage.toulouse.projetjavas6.etudiant.Etudiant;
import fr.miage.toulouse.projetjavas6.etudiant.Validees;
import fr.miage.toulouse.projetjavas6.exceptions.CodeErroneException;
import fr.miage.toulouse.projetjavas6.formation.Mention;
import fr.miage.toulouse.projetjavas6.formation.Ues;
import fr.miage.toulouse.projetjavas6.postes.Referent;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
/**
 *
 * @author Thalya
 */
public class PageEtudiantController {
    //Etudiant de la classe
    private Etudiant etu;
    
    //Champ de text pour les informations de l'étudiant.
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
    private Text annee;
    @FXML
    private Text semestre;
    @FXML
    private TableView ueEnCours;
    @FXML
    private Label referent;
    
    @FXML
    private Button pageModifier;

    //Attribut concernant le tableau des UEs suivies (colonnes du tableau)
    @FXML
    private TableColumn<TableauUeSuivies, Integer> credits;
    @FXML
    private TableColumn<TableauUeSuivies, Integer> codeId;
    @FXML
    private TableColumn<TableauUeSuivies, String> nom;
    @FXML
    private TableColumn<TableauUeSuivies, Button> valider;
    @FXML 
    private TableColumn<TableauUeSuivies, Button> echouer;

    //Attribut concernant le tableau des UEs nécessaire pour la mention (colonnes du tableau)
    @FXML
    private TableView tableauUeMention;
    @FXML
    private TableColumn nomUeMention;
    @FXML
    private TableColumn codeIdUeMention;
    @FXML
    private TableColumn creditsUeMention;
    @FXML
    private TableColumn statutUeMention;
    @FXML
    private TableColumn prerequiesUeMentionV;
    @FXML
    private TableColumn prerequiesUeMentionM;
    
    //Attribut concernant le tableau des UEs acessibles (selon les ues déjà validées)
    @FXML
    private TableView ueAcc;
    @FXML
    private TableColumn nomUeAcc;
    @FXML
    private TableColumn codeIdUeAcc;
    @FXML
    private TableColumn creditsUeAcc;
    @FXML
    private TableColumn prerequiesUeAcc;
    @FXML
    private TableColumn mentionUeAcc;
    @FXML
    private TableColumn inscrireUeAcc;
    
    //Affichage du progrès par rapport à l'obtention du diplôme de la Mention
    @FXML
    private ProgressIndicator completion;
    @FXML
    private Pane backgroundEcts;
    
    
    private int nbUeM;
    private int nbUeValidees;
    
    @FXML
    private Rectangle rectangleValidee;
    @FXML
    private Label labelValidee;
    
    @FXML
    private void switchToAccueil(){
       App.setRoot("Accueil");
   }
    @FXML  
    public void modifierEtudiant(){
        App.setRoot("ModifierEtudiant");
    }
    @FXML
    public void switchToUePassees(){
        App.setRoot("UesPassees");
    }
    public void recupererEtudiant(){
        this.etu=App.getEtu();
    }
    
    /**
     * Méthode permettant de faire valider une Ue à l'étudiant.
     * @param ue 
     */
    public void validerUe(Ues ue){
        try {
            this.etu.validerUe(ue.getCodeId());
            //Appel la méthode qui raffraichit la page
            refresh();
        } catch (CodeErroneException ex) {
            //Exception levée si le code de l'ue n'est pas valide
            Logger.getLogger(PageEtudiantController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    /**
     * Méthode permettant de faire échouer une Ue à l'étudiant.
     * @param ue 
     */
    public void echouerUe(Ues ue){
        try {
            this.etu.echouerUe(ue.getCodeId());
            //Appel la méthode qui raffraichit la page
            refresh();
        } catch (CodeErroneException ex) {
            Logger.getLogger(PageEtudiantController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    //TODO FINIR JAVADOC À PARTIR D'ICI
    /**
     * tableauUeSuivies : méthode qui met à jour ou intilialise le tableau des Ues suivies.
     */
    private void tableauUeSuivies(){
        ObservableList<TableauUeSuivies> list = FXCollections.observableArrayList();
        for(Ues u : this.etu.getSuivies().values()){
            TableauUeSuivies tab =  new TableauUeSuivies(u.getNom(), u.getCodeId(), u.getCredits());
            list.add(tab);
            tab.getValider().setOnAction(event -> {
                validerUe(u);
            });
            tab.getEchouer().setOnAction(event -> {
                echouerUe(u);
            });
        }
        this.nom.setCellValueFactory(new PropertyValueFactory<TableauUeSuivies, String>("nom"));
        this.codeId.setCellValueFactory(new PropertyValueFactory<TableauUeSuivies, Integer>("codeId"));
        this.credits.setCellValueFactory(new PropertyValueFactory<TableauUeSuivies, Integer>("credits"));
        this.valider.setCellValueFactory(new PropertyValueFactory<TableauUeSuivies, Button>("valider"));
        this.echouer.setCellValueFactory(new PropertyValueFactory<TableauUeSuivies, Button>("echouer"));
        
        this.ueEnCours.setItems(list);
    }
    
    /**
     * tableauUeMention : méthode qui met à jour ou intilialise le tableau des Ues de la mention.
     */
    public void tableauUeMention(){
        ObservableList<TableauUeMention> listUeMention = FXCollections.observableArrayList();
        
        for (Ues u : this.etu.getMention().getUes()){
            String statut = this.etu.ueValidee(u) ? "Validée" : "Pas encore validée";
            String uePreV = "";
            String uePreM = "";
            for(Ues ue : u.getPrerequies()){
                if (this.etu.ueValidee(ue)){
                    uePreV += ue.getNom() + ", ";
                }
                else {
                    uePreM += ue.getNom() + ", ";
                }
            }
            TableauUeMention t = new TableauUeMention(u.getCodeId(), u.getNom(), u.getCredits(),statut, uePreV, uePreM );
            listUeMention.add(t);
        }

        this.nomUeMention.setCellValueFactory(new PropertyValueFactory<TableauUePassees, String>("nom"));
        this.codeIdUeMention.setCellValueFactory(new PropertyValueFactory<TableauUePassees, Integer>("codeId"));
        this.creditsUeMention.setCellValueFactory(new PropertyValueFactory<TableauUePassees, Integer>("ects"));
        this.statutUeMention.setCellValueFactory(new PropertyValueFactory<TableauUePassees, String>("statut"));
        this.prerequiesUeMentionV.setCellValueFactory(new PropertyValueFactory<TableauUePassees, String>("uePreV"));
        this.prerequiesUeMentionM.setCellValueFactory(new PropertyValueFactory<TableauUePassees, String>("uePreM"));
        this.tableauUeMention.setItems(listUeMention);
        
    }
    
    /**
     * Methode getMention qui récupère la mention d'une Ues.
     * @param ue : ue dont on soubaite récupérer le nom
     * @return String : le nom de la mention
     */
    public String getMention(Ues ue){
        boolean trouve = false;
        int i=0;
        Mention m;
        String nomM = "Ouverture";
        while ((!trouve)&&(i < Mention.getMentions().size())){
            m=Mention.getMentions().get(i);
            if (m.getUes().contains(ue)){
                trouve = true;
                nomM=m.getNom();
            }
            i++;
        }
        return nomM;
    }
    /**
     * méthode ueDejaValidee :  retourne vrai si l'ue a déjà été validée par l'étudiant, faux sinon
     * @param ue
     * @return 
     */
    public boolean ueDejaValidee(Ues ue){
        for (Validees v : this.etu.getValidees()){
            if (v.getUe()== ue){
                return true;
            }
        }
        return false;
    }
    /**
     * Méthode inscrire qui redirige l'application sur la page inscrireEtudiantUE
     * @param ue : ue à laquelle on souhaite inscire un étudiant
     */
    public void inscrire(Ues ue){
        App.setUe(ue);
        App.setRoot("InscrireEtudiantUE");
    }
    /**
     * méthode tableauUeAccessibles méthode qui met à jour ou intilialise le tableau des Ues accessibles par l'étudiant.
     */
    public void tableauUeAccessibles(){
        ObservableList<TableauUeAccessibles> listAccessible = FXCollections.observableArrayList();
        boolean trouve;
        int i;
        for(Ues ue : Ues.getUes()){
            trouve=false;
            i=0;
            while ((!trouve)&& (i< ue.getPrerequies().size())){
                Ues uePre = ue.getPrerequies().get(i);
                if (!this.ueDejaValidee(uePre)){
                    trouve = true;
                }
                i++;
            }
            if (!trouve){
                if ((!ueDejaValidee(ue))&& (! this.etu.getSuivies().values().contains(ue))){
                    String prerequise = "";
                    for (Ues uePre : ue.getPrerequies()){
                        prerequise += uePre.getNom()+" ";
                    }  
                    TableauUeAccessibles t = new TableauUeAccessibles(ue.getNom(), ue.getCodeId(), ue.getCredits(), prerequise ,getMention(ue) );
                    listAccessible.add(t);
                    t.getInscrire().setOnAction(event -> {
                    inscrire(ue);
            });
                }
            }
        }
        listAccessible.sort((a, b) -> a.getNom().compareTo(b.getNom()));
        this.nomUeAcc.setCellValueFactory(new PropertyValueFactory<TableauUeAccessibles, String>("nom"));
        this.codeIdUeAcc.setCellValueFactory(new PropertyValueFactory<TableauUeAccessibles, String>("codeId"));
        this.creditsUeAcc.setCellValueFactory(new PropertyValueFactory<TableauUeAccessibles, String>("credits"));
        this.prerequiesUeAcc.setCellValueFactory(new PropertyValueFactory<TableauUeAccessibles, String>("prerequies"));
        this.mentionUeAcc.setCellValueFactory(new PropertyValueFactory<TableauUeAccessibles, String>("mention"));
        this.inscrireUeAcc.setCellValueFactory(new PropertyValueFactory<TableauUeAccessibles, String>("inscrire"));
        this.ueAcc.setItems(listAccessible);
    }
    
    /**
     * méthode completionInit qui met à jour le pourcentage de complétion de la formation d'un étudiant en therme d'ues de la mention validées.
     */
    public void completionInit(){
        this.nbUeValidees = 0;
        for(Validees v : this.etu.getValidees()){
            if (this.etu.getMention() != null){
                if (this.etu.getMention().getUes().contains(v.getUe())){
                    this.nbUeValidees ++;
                }
            }
        }
        float progress = (float)this.nbUeValidees/(float)this.nbUeM;
        this.completion.setProgress(progress);
    }
    /**
     * Méthode appellé pour raffraichir la page et mettre à jour les données lors de changement.
     */
    public void refresh(){
        this.champPrenomNom.setText(this.etu.getNom()+" "+this.etu.getPrenom());
        String champ = "Numéro étudiant : "+this.etu.getNumeroEt();
        this.numeroEt.setText(champ);
        this.annee.setText("Année universitaire : "+this.etu.getAnnee());
        this.mentionParcours.setText("Mention "+this.etu.getMention().getNom()+ " parcours "+ this.etu.getP().getNom());
        this.semestre.setText("Semestre acutel : "+this.etu.getSemestre().toString());
        champ="Crédits : "+this.etu.getCreditsPotentielSemestre()+" ECTS";
        this.creditsSemestre.setText(champ);
        int creditsAcEtu = this.etu.getCreditsAcquis();
        

        tableauUeSuivies();
        tableauUeMention();
        tableauUeAccessibles();
        completionInit();
        
        if (creditsAcEtu >= 180){
            this.creditsAcquis.setText("L'étudiant a réunit assez d'ECTS pour valider son diplôme");
            if (this.completion.getProgress() == 1){
                this.creditsAcquis.setText("L'étudiant a finit sa formation.");
                this.labelValidee.setVisible(true);
                this.rectangleValidee.setVisible(true);
            }
        }
        else{
            this.creditsAcquis.setText("Quantité d'ECTS pour valider le diplôme :\n"+this.etu.getCreditsAcquis()+" / 180");
        }
    }
    
    
    @FXML
    public void passerAuProchainSemestre(){
        this.etu.passerAuSemestre();
        refresh();
    }
    
    @FXML
    public void initialize(){
        this.recupererEtudiant();
        this.champPrenomNom.setText(this.etu.getNom()+" "+this.etu.getPrenom());
        String champ = "Numéro étudiant : "+this.etu.getNumeroEt();
        this.numeroEt.setText(champ);
        this.annee.setText("Année universitaire : "+this.etu.getAnnee());
        this.mentionParcours.setText("Mention "+this.etu.getMention().getNom()+ " parcours "+ this.etu.getP().getNom());
        this.semestre.setText("Semestre acutel : "+this.etu.getSemestre().toString());
        champ="Crédits : "+this.etu.getCreditsPotentielSemestre()+" ECTS";
        this.creditsSemestre.setText(champ);
        int creditsAcEtu = this.etu.getCreditsAcquis();
        
        
        this.nbUeM = this.etu.getMention().getUes().size();
        
        Referent ref = this.etu.getR();
        if (ref == null){
            this.referent.setVisible(false);
        }
        else{
            this.referent.setText("Référent : "+ref.getNom() +" "+ ref.getPrenom() +" "+ ref.getNumRef());
            this.referent.setVisible(true);
        }
        tableauUeSuivies();
        tableauUeMention();
        tableauUeAccessibles();
        completionInit();
        if (creditsAcEtu >= 180){
            this.creditsAcquis.setText("L'étudiant a réunit assez d'ECTS pour valider son diplôme");
            if (this.completion.getProgress() == 1){
                this.creditsAcquis.setText("L'étudiant a finit sa formation.");
                this.labelValidee.setVisible(true);
                this.rectangleValidee.setVisible(true);
            }
        }
        else{
            this.creditsAcquis.setText("Quantité d'ECTS pour valider le diplôme :\n"+this.etu.getCreditsAcquis()+" / 180");
        }
    }
    
}
