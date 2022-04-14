package fr.miage.toulouse.projetjavas6;

import fr.miage.toulouse.projetjavas6.etudiant.EtatSemestre;
import fr.miage.toulouse.projetjavas6.etudiant.Etudiant;
import fr.miage.toulouse.projetjavas6.fichiers.Fichiers;
import fr.miage.toulouse.projetjavas6.formation.Ues;
import fr.miage.toulouse.projetjavas6.postes.Poste;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Parent;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;
    /*Répertoire à partir duquel on récupère les données*/
    private static String repertoire;
    
    /*Etudiant courant (pour les page d'étudiant, de modification d'un étudiant, ses Ues passées etc*/
    private static Etudiant etu;
    /*Ue, pour avoir accès à l'ue lors de l'inscription d'un étudiant à celle-ci*/
    private static Ues ue;
    
    /*poste de la personne se rendant sur l'application :
    null :  administrateur, accès comme avant
    instance de Directeur : directeur pour une Mention
                            -> Ne voit que les étudiants de sa Mention
    instance de Referent : référent pour une Mention (peut y avoir plusieurs référent pour une même Mention)
                            -> Ne voit que les étudiants qu'il suit
    */
    private static Poste poste = null;
    
    private static String anneeCourante;
    private static EtatSemestre etatS;
    
    /*instance de la classe Fichiers, qui s'occupe de récupérer les données dans les csv et de les retranscrire au moment de l'enregistrement des données*/
    private static Fichiers f;
    
    
    /**
     * Méthode recuppererDonnes : va faire appelle à la methode de récupéreration des données de l'instance de Fichier : App.f
     */
    public static void recupererDonnees(){
        App.f = new Fichiers();
        try{
            App.f.recupererDonnees();
            App.setRoot("Authentification");
        }catch(Exception e){
            e.printStackTrace();
            App.setRoot("ChoixRepertoireCSV");
        }
    
    }
    /**
     * Méthode enregistrerDonnes : va faire appelle à la methode d'enregistrement des données de l'instance de Fichier : App.f
     */
    public static void enregistrerDonnes(){
        try{
            App.f.transcrireDonnes();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public static String getRepertoire(){
        return App.repertoire;
    }
    public static void setRepertoire(String repertoire){
        App.repertoire = repertoire;
    }
    
    public static String getAnneeCourante(){
        return App.anneeCourante;
    }
    public static void setAnneeCourante(String annee){
        App.anneeCourante= annee;
    }
    public static EtatSemestre getEtatS(){
        return App.etatS;
    }
    public static void setEtatS(EtatSemestre e){
        App.etatS = e;
    }
    public static Etudiant getEtu(){
        return App.etu;
    }
    public static void setEtu(Etudiant etu){
        App.etu = etu;
    }
    public static Ues getUes(){
        return App.ue;
    }
    public static void setUe(Ues ue){
        App.ue = ue;
    }
    public static Poste getPoste(){
        return App.poste;
    }
    public static void setPoste(Poste p){
        App.poste = p;
    }
    
    @Override
    public void start(Stage stage) throws IOException {
            scene = new Scene(loadFXML("ChoixRepertoireCSV"), 1000, 500);
        stage.setTitle("nomApp");

        stage.setScene(scene);
        stage.show();
         
    }

    static void setRoot(String fxml){
        try{
            scene.setRoot(loadFXML(fxml));
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
    
    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args){
        App.anneeCourante = "2021/2022";
        App.etatS = EtatSemestre.PAIR;
        launch(args);
    }

}
