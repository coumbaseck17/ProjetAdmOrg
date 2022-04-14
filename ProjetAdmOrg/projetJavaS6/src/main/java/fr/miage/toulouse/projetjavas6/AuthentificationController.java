package fr.miage.toulouse.projetjavas6;
import fr.miage.toulouse.projetjavas6.formation.Mention;
import fr.miage.toulouse.projetjavas6.postes.Directeur;
import fr.miage.toulouse.projetjavas6.postes.Referent;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;

/**
 *
 * @author Lydia
 */
public class AuthentificationController {
    
    @FXML
    private ComboBox identifiant;

    @FXML
    private ComboBox mention;

    @FXML
    private ComboBox poste;
    
    @FXML
    private Button btnAuthentification;
    
    @FXML
    private Label affiche;
    
    public void initialize(){
        
        ObservableList<String> list = FXCollections.observableArrayList();
        list.add("Administrateur ");
        list.add("Directeur ");
        list.add("Réferent ");
        
        poste.setItems(list);
        
        this.affiche.setVisible(false);
        
        
        
       /* ObservableList<String> list3 = FXCollections.observableArrayList();
        for(Referent r : Referent.getMentions()){
            list2.add(m.getNom());
        } 
        mention.setItems(list2); */
     
        mention.setDisable(true);
        identifiant.setDisable(true);
        btnAuthentification.setDisable(true);
        
        
        
    }
    
    public void choixPoste() throws IOException{
        
        String s = this.poste.getSelectionModel().getSelectedItem().toString();
        
        if (s.equals("Administrateur ")){
            
           /* mention.setDisable(false);
            identifiant.setDisable(false);*/
          this.mention.setDisable(true);
          this.identifiant.setDisable(true);
          btnAuthentification.setDisable(false);
          this.affiche.setText("Vous vous connectez en tant qu'administrateur");
          this.affiche.setVisible(true);
            
        }
        else if (s.equals("Directeur ")) {
            ObservableList<String> list2 = FXCollections.observableArrayList();
        
            for(Mention m : Mention.getMentions()){
                if (Directeur.getDirecteur(m) != null){
                    list2.add(m.getNom());
                }
            } 
            mention.setItems(list2); 
            this.mention.setDisable(false);
            this.identifiant.setDisable(true);
            btnAuthentification.setDisable(true);
            this.affiche.setVisible(false);
        }
        else{
             ObservableList<String> list2 = FXCollections.observableArrayList();
        
            for(Mention m : Mention.getMentions()){
                if (!Referent.getReferentParMention(m).isEmpty()){
                    list2.add(m.getNom());
                }
            } 
            mention.setItems(list2);
            this.mention.setDisable(true);//Pour réinitialisé
            this.mention.setDisable(false);
            btnAuthentification.setDisable(true);
            this.affiche.setVisible(false);
        }
          
    }
    
    public void choixMention() {
        
        String s = this.poste.getSelectionModel().getSelectedItem().toString();
        String nomMention = this.mention.getSelectionModel().getSelectedItem().toString();
        Mention m = Mention.getMention((nomMention));
        if (s.equals("Directeur ")){
            btnAuthentification.setDisable(false);
            String nom = Directeur.getDirecteur(m).getNom();
            this.affiche.setText("Vous vous connectez en tant que directeur de la mention "+nomMention+" : "+ nom);
            this.affiche.setVisible(true);
        }
        else {
            ObservableList<Integer> list = FXCollections.observableArrayList();
            for(Referent r: Referent.getReferentParMention(m)){
                list.add(r.getNumRef());
            } 
            identifiant.setItems(list);
            this.identifiant.setDisable(false); 
            this.affiche.setVisible(false);
        }
    
    }
    
    public void choixId(){
        btnAuthentification.setDisable(false);
        String nomMention = this.mention.getSelectionModel().getSelectedItem().toString();
        Mention m = Mention.getMention((nomMention));
        int id = Integer.parseInt(this.identifiant.getSelectionModel().getSelectedItem().toString());
        Referent ref = Referent.getReferent(id);
        String nomPrenom = ref.getNom() + " " + ref.getPrenom();
        this.affiche.setText("Vous vous connectez en tant qu'un référent de la mention "+nomMention+", Monsieur/Madame "+ nomPrenom);
        this.affiche.setVisible(true);
    }
    
    @FXML
    void retourAccueil() throws IOException {
        
        String s = this.poste.getSelectionModel().getSelectedItem().toString();
        if (s.equals("Réferent ")){
             int id =Integer.parseInt(this.identifiant.getSelectionModel().getSelectedItem().toString());
            App.setPoste(Referent.getReferent(id));
            
        }
        else if (s.equals("Directeur ")){
            String nomMention = this.mention.getSelectionModel().getSelectedItem().toString();
            Mention m = Mention.getMention((nomMention));
            App.setPoste(Directeur.getDirecteur(m));
        }
        App.setRoot("Accueil");   
    }
    
    
}
