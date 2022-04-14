
package fr.miage.toulouse.projetjavas6;

import fr.miage.toulouse.projetjavas6.tableauAffichages.TableauAccueil;
import fr.miage.toulouse.projetjavas6.etudiant.Etudiant;
import fr.miage.toulouse.projetjavas6.formation.Mention;
import fr.miage.toulouse.projetjavas6.formation.Parcours;
import fr.miage.toulouse.projetjavas6.postes.Directeur;
import fr.miage.toulouse.projetjavas6.postes.Poste;
import fr.miage.toulouse.projetjavas6.postes.Referent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TitledPane;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author Lydia
 */
public class AccueilController {
    
    Poste poste;
    
    @FXML
    private Button ajouterEtu;

    @FXML
    private TableColumn <TableauAccueil,String> mention;

    @FXML
    private TableColumn  <TableauAccueil,String> nom;

    @FXML
    private TableColumn <TableauAccueil,Integer> numEtu;

    @FXML
    private TableColumn  parcours;

    @FXML
    private TableColumn  prenom;

    @FXML
    private TableView<TableauAccueil> table;
   
   @FXML
    private Accordion acc;
   
    @FXML
    private TableColumn <TableauAccueil,String> voir;
   
    private ArrayList<TitledPane> listeAMentions;
    private ArrayList<TitledPane> listeAParcours;
    public void goToPageEtu(Etudiant etu) throws IOException{
        App.setEtu(etu);

       App.setRoot("PageEtudiant");
   }
    @FXML
    public void enregistrerDonnes(){
        App.enregistrerDonnes();
        App.setRoot("Accueil");
    }
    public void clicParcours(TitledPane t, Parcours p){
        for (TitledPane ti : this.listeAParcours){
            ti.setDisable(false);
        }
        t.setDisable(true);
        refreshTriTableauP(p);
    }
    public void refreshTriTableauP(Parcours p){
        ObservableList<TableauAccueil> list = FXCollections.observableArrayList();
        if (this.poste instanceof Referent){
            Referent r = (Referent) this.poste;
            for (Etudiant e : r.getEtudiants()){
                if ((e.getP() == p)){
                    TableauAccueil ta = new TableauAccueil(e.getPrenom(),e.getNom(),e.getNumeroEt(),e.getMention().getNom(),e.getP().getNom());
                    list.add(ta);
                    ta.getB().setOnAction(event -> {
                        try {
                            this.goToPageEtu(e);
                        } catch (IOException ex) {
                            Logger.getLogger(AccueilController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    });
                }
            } 
        }
        else if (this.poste instanceof Directeur) {
            for (Etudiant e : Etudiant.getEtudiants()){
                Mention m = this.poste.getM();
                if ((e.getP() == p)&& (e.getMention() == m)){
                    TableauAccueil ta = new TableauAccueil(e.getPrenom(),e.getNom(),e.getNumeroEt(),e.getMention().getNom(),e.getP().getNom());
                    list.add(ta);
                    ta.getB().setOnAction(event -> {
                        try {
                            this.goToPageEtu(e);
                        } catch (IOException ex) {
                            Logger.getLogger(AccueilController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    });
                }
            } 
        }
        else {
            for (Etudiant e : Etudiant.getEtudiants()){
                if (e.getP() == p){
                    TableauAccueil ta = new TableauAccueil(e.getPrenom(),e.getNom(),e.getNumeroEt(),e.getMention().getNom(),e.getP().getNom());
                    list.add(ta);
                    ta.getB().setOnAction(event -> {
                        try {
                            this.goToPageEtu(e);
                        } catch (IOException ex) {
                            Logger.getLogger(AccueilController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    });
                }
            } 
        }
              
            prenom.setCellValueFactory(new PropertyValueFactory<TableauAccueil,String>("prenom"));
            nom.setCellValueFactory(new PropertyValueFactory<TableauAccueil,String>("nom"));
            numEtu.setCellValueFactory(new PropertyValueFactory<TableauAccueil,Integer>("numEtu"));
            mention.setCellValueFactory(new PropertyValueFactory<TableauAccueil,String>("nomMention"));
            parcours.setCellValueFactory(new PropertyValueFactory<TableauAccueil,String>("nomParcours"));
            voir.setCellValueFactory(new PropertyValueFactory<TableauAccueil,String>("b"));
            
            table.setItems(list);
    }
    
    public void clicMention(TitledPane t, Mention m){
        //Si on a cliqué dessus pour l'ouvrir
        
        if (poste == null){
            for (TitledPane ti : this.listeAParcours){
                ti.setDisable(false);
                ti.setExpanded(false);
            }
            if (t.isExpanded()){
                refreshTriTableauM(m);
            }
            else{
                refreshTriTableau();
            }
        }
        else {
            for (TitledPane ti : this.listeAParcours){
                ti.setDisable(false);
                ti.setExpanded(false);
            }
            refreshTriTableauM(m);
        }
    }
    public void refreshTriTableau(){
        ObservableList<TableauAccueil> list = FXCollections.observableArrayList();
            for (Etudiant e : Etudiant.getEtudiants()){
                    TableauAccueil ta = new TableauAccueil(e.getPrenom(),e.getNom(),e.getNumeroEt(),e.getMention().getNom(),e.getP().getNom());
                    list.add(ta);
                    ta.getB().setOnAction(event -> {
                        try {
                            this.goToPageEtu(e);
                        } catch (IOException ex) {
                            Logger.getLogger(AccueilController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    });
            }       
            prenom.setCellValueFactory(new PropertyValueFactory<TableauAccueil,String>("prenom"));
            nom.setCellValueFactory(new PropertyValueFactory<TableauAccueil,String>("nom"));
            numEtu.setCellValueFactory(new PropertyValueFactory<TableauAccueil,Integer>("numEtu"));
            mention.setCellValueFactory(new PropertyValueFactory<TableauAccueil,String>("nomMention"));
            parcours.setCellValueFactory(new PropertyValueFactory<TableauAccueil,String>("nomParcours"));
            voir.setCellValueFactory(new PropertyValueFactory<TableauAccueil,String>("b"));
            
            table.setItems(list);
    }
    public void refreshTriTableauM(Mention m){
        ObservableList<TableauAccueil> list = FXCollections.observableArrayList();

        if (this.poste instanceof Referent){
            Referent ref = (Referent) this.poste;
            for (Etudiant e : ref.getEtudiants()){
                if (e.getMention() == m){
                    TableauAccueil ta = new TableauAccueil(e.getPrenom(),e.getNom(),e.getNumeroEt(),e.getMention().getNom(),e.getP().getNom());
                    list.add(ta);
                    ta.getB().setOnAction(event -> {
                        try {
                            this.goToPageEtu(e);
                        } catch (IOException ex) {
                            Logger.getLogger(AccueilController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    });
                }
            }
        }else{
            for (Etudiant e : Etudiant.getEtudiants()){
                if (e.getMention() == m){
                    TableauAccueil ta = new TableauAccueil(e.getPrenom(),e.getNom(),e.getNumeroEt(),e.getMention().getNom(),e.getP().getNom());
                    list.add(ta);
                    ta.getB().setOnAction(event -> {
                        try {
                            this.goToPageEtu(e);
                        } catch (IOException ex) {
                            Logger.getLogger(AccueilController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    });
                }
            }   
        }
            
            prenom.setCellValueFactory(new PropertyValueFactory<TableauAccueil,String>("prenom"));
            nom.setCellValueFactory(new PropertyValueFactory<TableauAccueil,String>("nom"));
            numEtu.setCellValueFactory(new PropertyValueFactory<TableauAccueil,Integer>("numEtu"));
            mention.setCellValueFactory(new PropertyValueFactory<TableauAccueil,String>("nomMention"));
            parcours.setCellValueFactory(new PropertyValueFactory<TableauAccueil,String>("nomParcours"));
            voir.setCellValueFactory(new PropertyValueFactory<TableauAccueil,String>("b"));
            
            table.setItems(list);
    }
    
    
    
    public void intializeAdministrateur(){
        this.listeAMentions = new ArrayList<TitledPane>();
        this.listeAParcours = new ArrayList<TitledPane>();
       
        for (Mention m : Mention.getMentions()){
            TitledPane content = new TitledPane();
            content.setText(m.getNom());
            Accordion a = new Accordion();
            content.setContent(a);
            
            content.setOnMouseClicked(event -> {
                clicMention(content, m);
            });
            
            this.listeAMentions.add(content);
            acc.getPanes().add(content);
            for(Parcours p : Parcours.getParcours()){
                if (m.getParcours().contains(p)){
                    TitledPane content2 = new TitledPane();
                    content2.setText(p.getNom());
                    content2.setContent(new AnchorPane());
                    content2.setOnMouseClicked(event -> {
                        clicParcours(content2, p);
                    });
                    this.listeAParcours.add(content2);
                    a.getPanes().add(content2);                         
                }
            }          
        } 
        ObservableList<TableauAccueil> list = FXCollections.observableArrayList();
            for (Etudiant e : Etudiant.getEtudiants()){
                
                TableauAccueil ta = new TableauAccueil(e.getPrenom(),e.getNom(),e.getNumeroEt(),e.getMention().getNom(),e.getP().getNom());
                list.add(ta);
                ta.getB().setOnAction(event -> {
                    try {
                        this.goToPageEtu(e);
                    } catch (IOException ex) {
                        Logger.getLogger(AccueilController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                });
 
            }       
            prenom.setCellValueFactory(new PropertyValueFactory<TableauAccueil,String>("prenom"));
            nom.setCellValueFactory(new PropertyValueFactory<TableauAccueil,String>("nom"));
            numEtu.setCellValueFactory(new PropertyValueFactory<TableauAccueil,Integer>("numEtu"));
            mention.setCellValueFactory(new PropertyValueFactory<TableauAccueil,String>("nomMention"));
            parcours.setCellValueFactory(new PropertyValueFactory<TableauAccueil,String>("nomParcours"));
            voir.setCellValueFactory(new PropertyValueFactory<TableauAccueil,String>("b"));
            
            table.setItems(list);  
        
        
    }
    
    public void initializeDirecteur(Directeur d){
        
        this.listeAMentions = new ArrayList<TitledPane>();
        this.listeAParcours = new ArrayList<TitledPane>();
        Mention m = d.getM();
        TitledPane content = new TitledPane();
            content.setText(m.getNom());
            Accordion a = new Accordion();
            content.setContent(a);
            content.setExpanded(true);
            content.setOnMouseClicked(event -> {
                clicMention(content, m);
            });
            
            this.listeAMentions.add(content);
            acc.getPanes().add(content);
            for(Parcours p : Parcours.getParcours()){
                if (m.getParcours().contains(p)){
                    TitledPane content2 = new TitledPane();
                    content2.setText(p.getNom());
                    content2.setContent(new AnchorPane());
                    content2.setOnMouseClicked(event -> {
                        clicParcours(content2, p);
                    });
                    this.listeAParcours.add(content2);
                    a.getPanes().add(content2);                         
                }
            }
            
            ObservableList<TableauAccueil> list = FXCollections.observableArrayList();
            for (Etudiant e : Etudiant.getEtudiants()){ 
               if (e.getMention()==m){
        TableauAccueil ta = new TableauAccueil(e.getPrenom(),e.getNom(),e.getNumeroEt(),e.getMention().getNom(),e.getP().getNom());
                list.add(ta);
                ta.getB().setOnAction(event -> {
                    try {
                        this.goToPageEtu(e);
                    } catch (IOException ex) {
                        Logger.getLogger(AccueilController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                });
 
            }     }  
            prenom.setCellValueFactory(new PropertyValueFactory<TableauAccueil,String>("prenom"));
            nom.setCellValueFactory(new PropertyValueFactory<TableauAccueil,String>("nom"));
            numEtu.setCellValueFactory(new PropertyValueFactory<TableauAccueil,Integer>("numEtu"));
            mention.setCellValueFactory(new PropertyValueFactory<TableauAccueil,String>("nomMention"));
            parcours.setCellValueFactory(new PropertyValueFactory<TableauAccueil,String>("nomParcours"));
            voir.setCellValueFactory(new PropertyValueFactory<TableauAccueil,String>("b"));
            
            table.setItems(list);  
        } 
        
    
    
    public void initializedReferent(Referent r){
        this.listeAMentions = new ArrayList<TitledPane>();
        this.listeAParcours = new ArrayList<TitledPane>();
        Mention m = r.getM();
        TitledPane content = new TitledPane();
            content.setText(m.getNom());
            Accordion a = new Accordion();
            content.setContent(a);
            content.setCursor(Cursor.HAND);
            content.setOnMouseClicked(event -> {
                clicMention(content, m);
            });
            content.setExpanded(true);
            this.listeAMentions.add(content);
            acc.getPanes().add(content);
            for(Parcours p : Parcours.getParcours()){
                if (m.getParcours().contains(p)){
                    TitledPane content2 = new TitledPane();
                    content2.setText(p.getNom());
                    content2.setContent(new AnchorPane());
                    content2.setCursor(Cursor.HAND);
                    content2.setOnMouseClicked(event -> {
                        clicParcours(content2, p);
                    });
                    this.listeAParcours.add(content2);
                    a.getPanes().add(content2);                         
                }
            }
            
            ObservableList<TableauAccueil> list = FXCollections.observableArrayList();
            for (Etudiant e : r.getEtudiants()){ 
                TableauAccueil ta = new TableauAccueil(e.getPrenom(),e.getNom(),e.getNumeroEt(),e.getMention().getNom(),e.getP().getNom());
                list.add(ta);
                ta.getB().setOnAction(event -> {
                    try {
                        this.goToPageEtu(e);
                    } catch (IOException ex) {
                        Logger.getLogger(AccueilController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                });
 
            }       
            prenom.setCellValueFactory(new PropertyValueFactory<TableauAccueil,String>("prenom"));
            nom.setCellValueFactory(new PropertyValueFactory<TableauAccueil,String>("nom"));
            numEtu.setCellValueFactory(new PropertyValueFactory<TableauAccueil,Integer>("numEtu"));
            mention.setCellValueFactory(new PropertyValueFactory<TableauAccueil,String>("nomMention"));
            parcours.setCellValueFactory(new PropertyValueFactory<TableauAccueil,String>("nomParcours"));
            voir.setCellValueFactory(new PropertyValueFactory<TableauAccueil,String>("b"));
            
            table.setItems(list);  
            this.ajouterEtu.setVisible(false);
        } 
    

    @FXML
    public void initialize(){
        
        this.poste = App.getPoste();
        if(poste == null){
            intializeAdministrateur();
        }else if (poste instanceof Directeur) {
            initializeDirecteur((Directeur) poste);            
        }
        else if(poste instanceof Referent){
            initializedReferent((Referent) poste);           
        }          
    }
    
    @FXML
    void ajouterEtudiant() throws IOException {
        App.setRoot("NouvelEtudiant");
        
    }
}
