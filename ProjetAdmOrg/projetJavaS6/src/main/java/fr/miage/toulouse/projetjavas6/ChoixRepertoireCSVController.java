package fr.miage.toulouse.projetjavas6;
import fr.miage.toulouse.projetjavas6.fichiers.Fichiers;
import java.io.File;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
/**
 *
 * @author Thalya
 */
public class ChoixRepertoireCSVController {
    @FXML
    private Circle afficherExplications;
    @FXML
    private TextArea textExplicatifFichiers;
    @FXML
    private TextField cheminFichier;
    
    @FXML
    private AnchorPane anchorid;
    
    final DirectoryChooser directoryChooser = new DirectoryChooser();
    
    /**
     * Méthode qui crée la fenêtre de choix de répertoire.
    */
    private void configuringDirectoryChooser(DirectoryChooser directoryChooser) {
        directoryChooser.setTitle("Selectionner dossier");
        directoryChooser.setInitialDirectory(new File(System.getProperty("user.home")));
    }
    
    /**
     * Méthode de choix de répertoire de récupération des données de csv.
     */
    public void choisirRepertoire(){
        
        final DirectoryChooser dirchooser = new DirectoryChooser();
        
        Stage stage = (Stage) anchorid.getScene().getWindow();
        File file = dirchooser.showDialog(stage);
        
        if(file != null){
            //S'il existe un fichier tel que l'utilisateur a selectionné, on affiche le chemin dans le champ de texte
            this.cheminFichier.setText(file.getAbsolutePath());
        }
        
        this.configuringDirectoryChooser(directoryChooser);
    }
    
    /**
     * Méthode switchToAccueil qui permet de repartir sur l'accueil après avoir ou non choisi un répertoire
     * Si l'utilisateur n'a pas choisit de répertoire : on met le chemin à null, on ira récupérer les fichiers dans le jar
     * Si l'utilisateur a choisit un répertoire : il est contenu dans le chemin
     * Dans tous les cas, on met ce chemin dans l'attribut de classe "repertoire" de la classe App, puis on appelle la méthode "récupererDonnees()" de la classe App.
     */
    @FXML
    private void switchToAccueil() {
       String chemin = cheminFichier.getText();
        if (chemin.equals("Fichiers par défaut contenu dans le jar") || (chemin.equals(System.getProperty("user.dir")+"/applicationJavaGroupe6/fichiersCsv/"))){
            chemin = null;
        }
        App.setRepertoire(chemin);
        App.recupererDonnees();
    }
    
    /**
     * Si l'utilisateur clique sur "annuler" sur la page de choix de répertoire, on quitte l'application.
     */
    @FXML
    private void quitter(){
        System.exit(0);
    }
    
    /**
     * Si l'utilisateur passe sa sourie devant le symbole "?", une explication des fichiers csv lui sera montrée.
     */
    @FXML
    public void afficherExplicationCSV(){
        this.textExplicatifFichiers.setVisible(true);
    }
    /**
     * Dès que l'utilisateur retire sa sourie de devant le symbole "?", l'explication des fichiers csv sera cachée.
     */
    @FXML
    public void cacherExplicationCSV(){
        this.textExplicatifFichiers.setVisible(false);
    }
    /**
     * Méthode initialize() : initialise le répertoire de base.
     */
    @FXML
    public void initialize(){
        File f = new File(System.getProperty("user.dir")+"/applicationJavaGroupe6/fichiersCsv/Etudiants.csv");
        File f2 = new File(System.getProperty("user.dir")+"/applicationJavaGroupe6/fichiersCsv/Mentions.csv");
        File f3 = new File(System.getProperty("user.dir")+"/applicationJavaGroupe6/fichiersCsv/Directeurs.csv");
        File f4 = new File(System.getProperty("user.dir")+"/applicationJavaGroupe6/fichiersCsv/Referents.csv");
        File f5 = new File(System.getProperty("user.dir")+"/applicationJavaGroupe6/fichiersCsv/UEs.csv");
        if(f.isFile() && f2.isFile() && f3.isFile() && f4.isFile() && f5.isFile()){
            Fichiers.setPremierPassageEffectue(true);
            this.cheminFichier.setText(System.getProperty("user.dir")+"/applicationJavaGroupe6/fichiersCsv/");
        }       
        else{
            //Sinon, on affiche "Fichiers par défaut contenu dans le jar"
            this.cheminFichier.setText("Fichiers par défaut contenu dans le jar");
        }
    }
}
