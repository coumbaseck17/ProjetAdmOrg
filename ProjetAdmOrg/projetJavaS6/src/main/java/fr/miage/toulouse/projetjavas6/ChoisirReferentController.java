package fr.miage.toulouse.projetjavas6;
import fr.miage.toulouse.projetjavas6.etudiant.Etudiant;
import fr.miage.toulouse.projetjavas6.exceptions.ParcoursNonSuiviException;
import fr.miage.toulouse.projetjavas6.tableauAffichages.TableauReferents;
import fr.miage.toulouse.projetjavas6.postes.Referent;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author coumbaseck
 */
public class ChoisirReferentController {

    @FXML
    private TableView<TableauReferents> tabRef;
    @FXML
    private TableColumn<TableauReferents,String> mention;
    @FXML
    private TableColumn<TableauReferents,String> nom;
    @FXML
    private TableColumn<TableauReferents,String> prenom;
    @FXML
    private TableColumn<TableauReferents,Integer> numRef;
    @FXML
    private TableColumn<TableauReferents,Button> voir;
    
    public void retourPageEtu() {
        App.setRoot("ModifierEtudiant");
    }
    /**
     * Initializes the controller class.
     */
    @FXML
   
    public void initialize() {
        Etudiant etu =  App.getEtu();
        ObservableList<TableauReferents> list = FXCollections.observableArrayList();
        for (Referent r : Referent.getReferents()){
            if (r.getM() == etu.getMention()){
                TableauReferents tr = new TableauReferents(r.getM().getNom(),r.getNom(),r.getPrenom(),r.getNumRef());
                list.add(tr);
                tr.getB().setOnAction(event -> {
                if (etu.getR() != null){
                    try {
                    etu.getR().nePlusSuivreEtudiant(etu);
                    } catch (ParcoursNonSuiviException ex) {ex.printStackTrace();}
                }
                
                etu.setR(r);
                try {
                    r.suivreEtudiant(etu);
                } catch (ParcoursNonSuiviException ex) {
                    Logger.getLogger(ChoisirReferentController.class.getName()).log(Level.SEVERE, null, ex);
                }
                this.retourPageEtu();
            });
            }
            
        }    
            
            mention.setCellValueFactory(new PropertyValueFactory<TableauReferents,String>("nomMention"));
            nom.setCellValueFactory(new PropertyValueFactory<TableauReferents,String>("nom"));
            prenom.setCellValueFactory(new PropertyValueFactory<TableauReferents,String>("prenom"));
            numRef.setCellValueFactory(new PropertyValueFactory<TableauReferents,Integer>("numRef"));
            voir.setCellValueFactory(new PropertyValueFactory<TableauReferents,Button>("b"));
            
            tabRef.setItems(list);
           
            
            
                 
    }   
    
    
}
