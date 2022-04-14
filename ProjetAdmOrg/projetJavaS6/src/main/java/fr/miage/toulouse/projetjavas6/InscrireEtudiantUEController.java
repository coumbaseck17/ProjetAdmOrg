package fr.miage.toulouse.projetjavas6;
import fr.miage.toulouse.projetjavas6.etudiant.Etudiant;
import fr.miage.toulouse.projetjavas6.exceptions.DepassementEctsException;
import fr.miage.toulouse.projetjavas6.exceptions.PrerequieException;
import fr.miage.toulouse.projetjavas6.formation.Ues;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.text.Text;

/**
 *
 * @author Lydia
 */
public class InscrireEtudiantUEController {
    
    private Etudiant etu;
    private Ues ue;
    
    @FXML
    private Button retouraccueil;
    
    @FXML
    private Button ouiButton;
    @FXML
    private Button annuler;
      
    @FXML
    private Text texte;
    
    @FXML
    private Text vue;
    @FXML
    private Text question;
    @FXML
    private CheckBox certifie;
          

    @FXML
    void retourAccueil() {
        App.setRoot("Accueil");

    }
    @FXML
    void PageEtudiant(){
        App.setRoot("PageEtudiant");

    }

    public void initialize(){
        
        this.etu=App.getEtu();
        this.ue=App.getUes();
        String nom=this.etu.getNom();
        String prenom=this.etu.getPrenom();
        
        String nomue = this.ue.getNom();
        
        if ((this.etu.getCreditsPotentielSemestre() + ue.getCredits() <= 30)|| (this.etu.getDerogation() && (this.etu.getCreditsPotentielSemestre() + ue.getCredits() <= 39)) ){
            texte.setText("Etes vous sur de vouloir inscire "+prenom+" "+nom+" a l'UE : "+nomue);
            vue.setText("Ue : "+nomue);
        }
        else{
            this.ouiButton.setDisable(true);
            if (!this.etu.getDerogation() && (this.etu.getCreditsPotentielSemestre()+ ue.getCredits() <= 39)){
                texte.setText("Vous ne pouvez pas inscrire l'étudiant "+prenom+" "+nom+" à l'ue "+nomue+" car cela dépasserait son nombre maximum de crédit pour le semestre");
                this.ouiButton.setVisible(true);
                this.certifie.setVisible(true);
                this.question.setVisible(true);
            }
            else {
                texte.setText("Vous ne pouvez pas inscrire l'étudiant "+prenom+" "+nom+" à l'ue "+nomue+" car cela dépasserait son nombre maximum de crédit pour le semestre.");
                this.ouiButton.setVisible(false);
                this.certifie.setVisible(false);
                this.question.setVisible(false);
            }
        }
        
        
        
    }
    
    @FXML
    void inscrire() throws IOException {
        try{
            this.etu.inscrire(this.ue);
            App.setRoot("PageEtudiant");
        }
        catch(DepassementEctsException e){
            e.printStackTrace();
        } catch (PrerequieException ex) {
            Logger.getLogger(InscrireEtudiantUEController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    @FXML
    public void certifie(){
        if (this.certifie.isSelected()){
            this.etu.setDerogation(true);
            this.ouiButton.setDisable(false);
        }
        else {
            this.etu.setDerogation(false);
            this.ouiButton.setDisable(true);
        }
    }
    
}
