package fr.miage.toulouse.projetjavas6;
import fr.miage.toulouse.projetjavas6.etudiant.Etudiant;
import fr.miage.toulouse.projetjavas6.exceptions.ParcoursNonSuiviException;
import fr.miage.toulouse.projetjavas6.formation.Mention;
import fr.miage.toulouse.projetjavas6.formation.Parcours;
import fr.miage.toulouse.projetjavas6.postes.Referent;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
/**
 *
 * @author Lydia
 */
public class ModifierEtudiantController {
    
    private Etudiant e1 = App.getEtu();
    
    @FXML     
    private Button retourAccueil;
    
    @FXML
    private Label texteErreur;
    
    @FXML
    private Button save;
    @FXML
    private Button supprimerRef;
    @FXML
    private Button modifierRef;
    
    @FXML
    private TextField nom;
    
    @FXML
    private TextField prenom;
    
    @FXML
    private ComboBox mention;
    
    @FXML
    private ComboBox parcours;
    
    @FXML
    private Text etu;
    
    @FXML
    private Text numEtu;
    
    @FXML
    private Label referent;
    
    @FXML
    public void modifierRef(){
        App.setRoot("ChoisirReferent");
    }
    
    @FXML
    public void supprimerRef(){
        try {
            this.e1.getR().nePlusSuivreEtudiant(e1);
        } catch (ParcoursNonSuiviException ex) {
            ex.printStackTrace();
        }
        this.e1.setR(null);
        App.setRoot("ModifierEtudiant");
    }
    
    public void initialize(){
        etu.setText(e1.getNom()+" "+e1.getPrenom());
        
        numEtu.setText(Integer.toString(e1.getNumeroEt()));
        
        prenom.setText(e1.getPrenom());
        nom.setText(e1.getNom());

        Referent ref = e1.getR();
        if (ref != null){
            referent.setText("Référent : "+ref.getNom()+" "+ ref.getPrenom()+" "+ ref.getNumRef());
            supprimerRef.setDisable(false);
            modifierRef.setText("Modifier Référent");
        }
        else{
            referent.setText("Référent : Aucun");
            supprimerRef.setDisable(true);
            modifierRef.setText("Ajouter Référent");
        }
        if (App.getPoste() instanceof Referent){
            this.modifierRef.setVisible(false);
            this.supprimerRef.setVisible(false);
            this.mention.setVisible(false);
            this.parcours.setVisible(false);
        } 
        else {
            ObservableList<String> list = FXCollections.observableArrayList();
        list.add("Choix mention :");
        for(Mention m : Mention.getMentions()){
            list.add(m.getNom());
        }
        
        mention.setItems(list); 
        
        ObservableList<String> list2 = FXCollections.observableArrayList();
        list2.add("Choix parcours :");
        for(Parcours p : Parcours.getParcours()){
            list2.add(p.getNom());
        }
        this.parcours.setItems(list2);
        parcours.setValue(e1.getP().getNom());
        mention.setValue(e1.getMention().getNom());
        }
        
        
        
        
          
    }  
    
    @FXML
    public void retourAccueil() {
        App.setRoot("Accueil");   
    }
    @FXML
    public void retour() {
        App.setRoot("PageEtudiant");   
    }
    
    @FXML
    private void choixMention(){
        String s = this.mention.getSelectionModel().getSelectedItem().toString();
        if (s.equals("Choix mention :")){
            ObservableList<String> list2 = FXCollections.observableArrayList();
            list2.add("Choix parcours :");
            for(Parcours p : Parcours.getParcours()){
                list2.add(p.getNom());
            }
            this.parcours.setItems(list2);
            this.parcours.getSelectionModel().select("Choix parcours :");
            this.parcours.setDisable(true);
        }
        else{
            Mention m = Mention.getMention(s);
            ObservableList<String> list2 = FXCollections.observableArrayList();
            list2.add("Choix parcours :");
            for(Parcours p : m.getParcours()){
                list2.add(p.getNom());
            }
            this.parcours.setItems(list2);
            this.parcours.getSelectionModel().select("Choix parcours :");
            this.parcours.setDisable(false);
        }
        
    }
    
    public boolean verifierValeurs(){
        boolean verif1 = ! this.nom.getText().isBlank();
        boolean verif2 = ! this.prenom.getText().isBlank();
        
        if (verif1 && verif2 ){
            return true;
        }
        return false;
    }
    
    public Parcours recupererParcours(String nom){
        boolean trouve = false;
        Parcours p = null;
        int i = 0;
        while ((!trouve)&& (i < Parcours.getParcours().size())){
            if (Parcours.getParcours().get(i).getNom().equals(nom)){
                trouve = true;
                p = Parcours.getParcours().get(i);
            }
            i++;
        }
        return p;
    }

    @FXML
    public void sauvegarder()  {
        
        if (verifierValeurs()){
            if (App.getPoste() instanceof Referent){
                this.e1.setNom(this.nom.getText().toString());
                this.e1.setPrenom(this.prenom.getText().toString());
                App.setRoot("PageEtudiant");
            }
            else {
                try {
                Parcours p = recupererParcours(this.parcours.getSelectionModel().getSelectedItem().toString());
                    if (p != null){
                        this.e1.setNom(this.nom.getText().toString());
                        this.e1.setPrenom(this.prenom.getText().toString());
                        this.e1.setP(p);
                   
                    
                    App.setRoot("PageEtudiant");
                  
                    }
                    else {
                        this.texteErreur.setText("Aucun parcours n'a été selectionné");
                        this.texteErreur.setVisible(true);
                    }
                }
                catch (Exception e){
                    this.texteErreur.setText("Erreur lors de la modification d'un étudiant");
                    this.texteErreur.setVisible(true);
                        e.printStackTrace();

                }
            }
            
        }
        else {
            this.texteErreur.setText("Vous n'avez pas rentré toutes les données\n necessaires");
            this.texteErreur.setVisible(true);
        }      
    }
       
   } 
