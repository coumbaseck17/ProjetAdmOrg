package fr.miage.toulouse.projetjavas6.tableauAffichages;


import fr.miage.toulouse.projetjavas6.formation.Mention;
import fr.miage.toulouse.projetjavas6.formation.Parcours;
import java.util.ArrayList;
import javafx.scene.control.Button;

/**
 *
 * @author coumbaseck
 */
public class TableauReferents {
   
    private String nomMention;
    private String nom;
    private String prenom;
    private int numRef;
  
    private Button b;
    
    public TableauReferents(String nomMention,String nom,String prenom, int numRef) {
        this.nomMention=nomMention;
        this.nom=nom;
        this.prenom=prenom;
        this.numRef=numRef;
        this.b= new Button("Choisir");
    }
    
    public String getPrenom(){
        return this.prenom;
    }
    
    public String getNom(){
        return this.nom;
    }
    
    public int getNumRef(){
        return this.numRef;
    }
    
    public String getNomMention(){
        return this.nomMention;
    }
     
    public Button getB(){
        return this.b;
    }
}
