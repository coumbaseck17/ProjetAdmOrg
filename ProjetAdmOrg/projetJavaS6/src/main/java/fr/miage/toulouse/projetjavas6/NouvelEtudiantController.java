package fr.miage.toulouse.projetjavas6;
import fr.miage.toulouse.projetjavas6.etudiant.Etudiant;
import fr.miage.toulouse.projetjavas6.formation.Mention;
import fr.miage.toulouse.projetjavas6.formation.Parcours;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 *
 * @author Thalya
 */
public class NouvelEtudiantController {
    @FXML
    private ComboBox listeMentions;
    @FXML
    private ComboBox listeParcours;
    @FXML
    private Label texteErreur;
    @FXML
    private TextField champNom;
    @FXML
    private TextField champPrenom;
    @FXML
    private TextField champNumEt;
        
    
    
    @FXML
    private void switchToAccueil() {
       App.setRoot("Accueil");
    }
    
    /**
     * Méthode qui réactualiser les données des parcours et des Mentions.
     */
    @FXML
    private void choixMention(){
        String s = this.listeMentions.getSelectionModel().getSelectedItem().toString(); 
        if (s.equals("Choix mention :")){
            //Si on n'a pas choisit (ou déchoisit) une mention, les parcours sont réinitialisés à vide et la combobox est (re)désactivée.
            ObservableList<String> list2 = FXCollections.observableArrayList();
            list2.add("Choix parcours :");
            this.listeParcours.setItems(list2);
            this.listeParcours.getSelectionModel().select("Choix parcours :");
            this.listeParcours.setDisable(true);
        }
        else{
            //Si une mention a été choisie, on actualise les parcours aux parcours de la Mention.
            Mention m = Mention.getMention(s);
            ObservableList<String> list2 = FXCollections.observableArrayList();
            list2.add("Choix parcours :");
            for(Parcours p : m.getParcours()){
                list2.add(p.getNom());
            }
            this.listeParcours.setItems(list2);
            this.listeParcours.getSelectionModel().select("Choix parcours :");
            this.listeParcours.setDisable(false);
        }
        
    }
    
    /**
     * Méthode privée qui vérifie si une chaine de caractère est un entier, afin de vérifier si le numéro étudiant est bien un nombre.
     * @param str
     * @return boolean : vrai si la chaine est un entier, faux sinon
     */
    private static boolean estEntier(String str)  
    {  
       for (char c : str.toCharArray())
       {
           if (!Character.isDigit(c)) return false;
       }
       return true; 
    }
    
    /**
     * Méthode privée qui vérifie la validité des valeurs entrées (non vide, non que des espaces et le numéro d'étudiant est un entier).
     * @return boolean : vrai si les valeurs sont valides, faux sinon
     */
    private boolean verifierValeurs(){
        boolean verif1 = ! this.champNom.getText().isBlank();
        boolean verif2 = ! this.champPrenom.getText().isBlank();
        boolean verif3 = ! this.champNumEt.getText().isBlank();
        boolean verif4 = estEntier(this.champNumEt.getText().toString());
        if (verif1 && verif2 && verif3 && verif4){
            return true;
        }
        return false;
    }
    
    /**
     * Méthode qui permet de sauvegarder les valeurs en créant un nouvel étudiant, après avoir vérifier que les valeurs étaient valide en faisant appel à la méthode verifierValeurs().
     * Une fois l'enregistrement des données effectué, cette méthode permet de retourner à l'accueil.
     */
    public void sauvegarder(){
        if (verifierValeurs()){
            try {
                Parcours p = Parcours.recupererParcours(this.listeParcours.getSelectionModel().getSelectedItem().toString());
                if (p != null){
                    Etudiant etu = new Etudiant(Integer.parseInt(this.champNumEt.getText().toString()), this.champPrenom.getText().toString(), this.champNom.getText().toString(), 0, p ,App.getAnneeCourante(), App.getEtatS());
                    this.switchToAccueil();
                }
            }
            catch (Exception e){
                this.texteErreur.setText("Erreur lors de la créatio d'un étudiant.");
                e.printStackTrace();
                texteErreur.setVisible(true);
            }
        }
        else {
            this.texteErreur.setText("Des données sont érronées ou vous n'avez pas rentré toutes les données necessaires.");
            texteErreur.setVisible(true);
        }
    }

        
    
    @FXML
    public void initialize(){
        ObservableList<String> list = FXCollections.observableArrayList();
        //Initialisation des mention que l'utilisateur peut choisir pour le nouvel étudiant.
        list.add("Choix mention :");
        for(Mention m : Mention.getMentions()){
            list.add(m.getNom());
        }
        this.listeMentions.setItems(list);
        
        
        //Initialisation des parcours que l'utilisateur peut choisir pour le nouvel étudiant. Tant qu'une mention n'a pas été sélectionné, on a pas accès aux parcours, pour faciliter la recherche du parcours
        ObservableList<String> list2 = FXCollections.observableArrayList();
        list2.add("Choix parcours :");
        this.listeParcours.setItems(list2);
        this.listeParcours.setDisable(true);
    }
    
}
