package fr.miage.toulouse.projetjavas6.tableauAffichages;

import javafx.scene.control.Button;

/**
 *
 * @author Lydia
 */
public class TableauAccueil {

    
    private String prenom;
    private String nom;
    private int numEtu;
    private String nomMention;
    private String nomParcours;
    
    private Button b;
    
    public TableauAccueil(String prenom,String nom,int numEtu,String nomMention,String nomParcours) {
        this.prenom=prenom;
        this.nom=nom;
        this.numEtu=numEtu;
        this.nomMention=nomMention;
        this.nomParcours=nomParcours; 
        this.b= new Button("Voir Etu");
    }
    
    public String getPrenom(){
        return this.prenom;
    }
    
    public String getNom(){
        return this.nom;
    }
    
    public int getNumEtu(){
        return this.numEtu;
    }
    
    public String getNomMention(){
        return this.nomMention;
    }
    
    public String getNomParcours(){
        return this.nomParcours;
    }
    
    public Button getB(){
        return this.b;
    }
       

    
}
