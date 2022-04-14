package fr.miage.toulouse.projetjavas6.fichiers;

import fr.miage.toulouse.projetjavas6.App;
import fr.miage.toulouse.projetjavas6.etudiant.*;
import static fr.miage.toulouse.projetjavas6.etudiant.Etudiant.getEtudiants;
import fr.miage.toulouse.projetjavas6.exceptions.*;
import fr.miage.toulouse.projetjavas6.formation.*;
import static fr.miage.toulouse.projetjavas6.formation.Mention.getMentions;
import static fr.miage.toulouse.projetjavas6.formation.Parcours.getParcours;
import static fr.miage.toulouse.projetjavas6.formation.Ues.getUe;
import fr.miage.toulouse.projetjavas6.postes.Directeur;
import fr.miage.toulouse.projetjavas6.postes.Referent;
import static fr.miage.toulouse.projetjavas6.postes.Referent.getReferents;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Iterator;

/**
 *
 * @author Coumba
 */
public class Fichiers {
    private String chemin;
    private static boolean premierPassageEffectue = false;
    
    public Fichiers(){
        this.chemin = App.getRepertoire();
    }
    
    /**
     * Methode pour voir si on est déjà rentré une premier fois dans l'application 
     * @return un boolean 
     */
    
    public static boolean getPremierPassageEffectue (){
        return Fichiers.premierPassageEffectue;
    }
    
    /**
     * Cette méthode confirme le premier passage
     * @param valeur true ou false en fonction que cela soit ou non le premier passage
     */
    public static void setPremierPassageEffectue(boolean valeur){
        Fichiers.premierPassageEffectue = valeur;
    }
    
    /**
     * Cette methode sert à recuperer le chemin d'un fichier CSV.
     * @param fich le nom di fichier
     * @return 
     */
    public InputStream recupererCheminFichier(String fich){
        return Thread.currentThread().getContextClassLoader().getResourceAsStream("fichiersCsv/"+fich);
    }
    
    /**
     * Cette méthode permet de récupérer les mentions du fichier CSV et de creer les nouveaux objets correspondants.
     * @param fich nom du fichier
     * @throws FileNotFoundException
     * @throws ValeurUniqueException
     * @throws IOException
     * @throws luiMemePreException 
     */
    public void recupererMentions(String fich) throws FileNotFoundException, ValeurUniqueException, IOException, luiMemePreException{
        BufferedReader br = new BufferedReader(new FileReader(fich));
        String ligne = null;
        while ((ligne = br.readLine()) != null)
        {
            // Retourner la ligne dans un tableau
            String[] data = ligne.split(";");
            String nom=data[0];
            String tParcours=data[1];
            String tUe = data[2];
           
            Mention mtn = new Mention(nom);
            String [] tab1 = tParcours.split(",");
            for (int j=0; j<tab1.length;j++){
                Parcours p= new Parcours (tab1[j]);
                mtn.ajouterParcours(p);
            }
        }
        br.close();
    }
    
    /**
     * Cette méthode permet de récupérer les ue d'un fichier CSV et de creer les nouveaux objets correspondants.
     * @param fich nom du fichier
     * @throws FileNotFoundException
     * @throws ValeurUniqueException
     * @throws IOException
     * @throws luiMemePreException 
     */
    public void recupererUe (String fich) throws FileNotFoundException, ValeurUniqueException, IOException, luiMemePreException{
        BufferedReader br2 = new BufferedReader(new FileReader(fich));

        String ligne2 = null;
        while ((ligne2 = br2.readLine()) != null)
        {
            // Retourner la ligne dans un tableau
            String[] data2 = ligne2.split(";");
            int id = Integer.parseInt(data2[1]);
            int coef = Integer.parseInt(data2[2]);
            Ues ue;
            if (data2[3].equals("null")){
                ue= new UeOuverture(data2[0],id,coef);
            }
            else{
                String mention = data2[3];
                ue = new UeClassique(data2[0],id,coef);
                for(int i = 0; i<getMentions().size();i++){
                    if (getMentions().get(i).getNom().equals(mention)){
                        getMentions().get(i).ajouterUe((UeClassique) ue);
                    }
                }
            }
        }
        br2 = new BufferedReader(new FileReader(fich));
        
        //Pour ajouter les prerequis
        while ((ligne2 = br2.readLine()) != null)
        {
            // Retourner la ligne dans un tableau
            String[] data2 = ligne2.split(";");
            int id = Integer.parseInt(data2[1]);
            
            if (data2.length>4){
                String [] tPre = data2[4].split(",");
                for (int j=0; j<tPre.length;j++){
                    int idPre = Integer.parseInt(tPre[j]);
                    Ues.getUe(id).ajouterPrerequie(Ues.getUe(idPre));
                } 
            }
        }
        br2.close();   
    }
    
    /**
     * Cette méthode permet de récupérer les étudiants d'un fichier CSV et de creer les nouveaux objets correspondants.
     * @param fich nom du fichier
     * @throws FileNotFoundException
     * @throws ValeurUniqueException
     * @throws IOException
     * @throws luiMemePreException
     * @throws CodeErroneException
     * @throws DepassementEctsException 
     */
    public void recupererEtudiants(String fich) throws FileNotFoundException, ValeurUniqueException, IOException, luiMemePreException, CodeErroneException, DepassementEctsException, PrerequieException{
        BufferedReader br3 = new BufferedReader(new FileReader(fich));
        String ligne3 = null;
        Parcours par = null;
        
        while ((ligne3 = br3.readLine()) != null)
        {
            // Retourner la ligne dans un tableau
            String[] data3 = ligne3.split(";");
            int idEtu = Integer.parseInt(data3[0]);
            String prenom=data3[1];
            String nom=data3[2];
            int credits = Integer.parseInt(data3[3]);
            for (int i=0; i<getParcours().size();i++){
                if (getParcours().get(i).getNom().equals(data3[4]))
                    par= getParcours().get(i);
            }
            String annee=data3[5];
            //transformer string dans type enum
            String semestre=data3[6];
            EtatSemestre sem;
            //TODO GÉRER ERREURS
            if (semestre.equals("Pair") || semestre.equals("PAIR"))
                sem = EtatSemestre.PAIR;
            else
                sem = EtatSemestre.IMPAIR;
            //TODO Gérer si vide : null
            String [] validees = data3[7].split(",");
            String [] echouees = data3[8].split(",");
            
            Etudiant etu = new Etudiant(idEtu,prenom,nom,credits,par,annee,sem);
            int ectsSemestre = 0;
            //traitement tab validee, echouees et suivies
            for (int i=0; i<validees.length;i++){
                if (!validees[i].equals("null")){
                    etu.setCreditsPotentielSemestre(0);
                    int idUe =Integer.parseInt(validees[i]);
                    Ues ueV = getUe(idUe);
                    String anneeAGarder = etu.getAnnee();
                    EtatSemestre semestreAGarder = etu.getSemestre();
                    etu.setAnnee(annee);
                    etu.setSemestre(sem);
                    if(anneeAGarder.equals(annee)){
                        if (semestreAGarder.equals(sem)){
                            ectsSemestre += ueV.getCredits();
                        }
                    }
                    etu.inscrire(ueV);
                    etu.validerUe(ueV.getCodeId());
                    etu.setAnnee(anneeAGarder);
                    etu.setSemestre(semestreAGarder);
                }
                
            }
            for (int j=0; j<echouees.length;j++){
                if (!echouees[j].equals("null")){
                    etu.setCreditsPotentielSemestre(0);
                    int idUe =Integer.parseInt(echouees[j]);
                    Ues ueE = getUe(idUe);
                    String anneeAGarder = etu.getAnnee();
                    EtatSemestre semestreAGarder = etu.getSemestre();
                    etu.setAnnee(annee);
                    etu.setSemestre(sem);
                    if(anneeAGarder.equals(annee)){
                        if (semestreAGarder.equals(sem)){
                            ectsSemestre += ueE.getCredits();
                        }
                    }
                    etu.inscrire(ueE);
                    etu.echouerUe(ueE.getCodeId());
                    etu.setAnnee(anneeAGarder);
                    etu.setSemestre(semestreAGarder);
                }
                
            }
            etu.setCreditsPotentielSemestre(ectsSemestre);
            if (!data3[9].equals("null")){
                String [] suivies = data3[9].split(",");
                for (int y=0; y<suivies.length;y++){
                    if (!suivies[y].equals("null")){
                        int idUe =Integer.parseInt(suivies[y]);
                        Ues ueS = getUe(idUe);
                        etu.inscrire(ueS);
                    } 
                }
            }
            
            
        }
        br3.close();
    }
    
    /**
     * Cette méthode permet de récupérer les directeurs d'un fichier CSV et de creer les nouveaux objets correspondants.
     * @param fich nom du fichier
     * @throws FileNotFoundException
     * @throws ValeurUniqueException
     * @throws IOException
     * @throws luiMemePreException 
     */
    public void recupererDirecteurs(String fich) throws FileNotFoundException, ValeurUniqueException, IOException, luiMemePreException{
        BufferedReader br4 = new BufferedReader(new FileReader(fich));
        
        String ligne4 = null;
        while ((ligne4 = br4.readLine()) != null)
        {
            // Retourner la ligne dans un tableau
            String[] data4 = ligne4.split(";");
            String mtn=data4[0];
            String nom=data4[1];
            String prenom = data4[2];
            Mention mention=Mention.getMention(mtn);
            Directeur drc = new Directeur(mention,nom,prenom);
        }
        br4.close();
    }
    
    /**
     * Cette méthode permet de récupérer les referents d'un fichier CSV et de creer les nouveaux objets correspondants.
     * @param fich nom du fichier
     * @throws FileNotFoundException
     * @throws ValeurUniqueException
     * @throws IOException
     * @throws luiMemePreException 
     */
    
    public void recupererReferents(String fich ) throws FileNotFoundException, ValeurUniqueException, IOException, luiMemePreException, ParcoursNonSuiviException{
        BufferedReader br5 = new BufferedReader(new FileReader(fich));
        
        String ligne5 = null;
        while ((ligne5 = br5.readLine()) != null)
        {
            // Retourner la ligne dans un tableau
            String[] data5 = ligne5.split(";");
            String mtn=data5[0];
            String nom=data5[1];
            String prenom = data5[2];
            int nbRef =Integer.parseInt(data5[3]);
            String [] tabEtu = data5[4].split(",");
            Mention mention=Mention.getMention(mtn);
            Referent ref = new Referent(mention,nom,prenom,nbRef);
            if (tabEtu.length>0){
                for (int i=0; i<tabEtu.length; i++){
                    int nbEtu =Integer.parseInt(tabEtu[i]);
                    for(int j=0;j<getEtudiants().size();j++){
                        if (getEtudiants().get(j).getNumeroEt()== nbEtu){
                            ref.suivreEtudiant(getEtudiants().get(j));
                            getEtudiants().get(j).setR(ref);
                        }
                    }
                }
            }
        }
        br5.close();
        
    }
    
    /**
     * Cette méthode recopie les fichiers dans le répertoire par défault
     * @param source
     * @param dest
     * @return boolean
     */
    public static boolean copier(InputStream source, File dest) {
    try (InputStream sourceFile = source; 
            OutputStream destinationFile = new FileOutputStream(dest)) {
        // Lecture par segment de 0.5Mo 
        byte buffer[] = new byte[512 * 1024];
        int nbLecture;
        while ((nbLecture = sourceFile.read(buffer)) != -1){
            destinationFile.write(buffer, 0, nbLecture);
        }
        destinationFile.close();
        sourceFile.close();
    } catch (IOException e){
        e.printStackTrace();
        return false; // Erreur
    }
    return true; // Résultat OK  
    }
    
    /**
     * Cette méthode récuper les donnes des fichiers CSV disponibles
     * @throws FileNotFoundException
     * @throws ValeurUniqueException
     * @throws IOException
     * @throws luiMemePreException
     * @throws CodeErroneException
     * @throws DepassementEctsException 
     */
    public void recupererDonnees () throws FileNotFoundException, ValeurUniqueException, IOException, luiMemePreException, CodeErroneException, DepassementEctsException, ParcoursNonSuiviException, PrerequieException {
        String rep = System.getProperty("user.dir")+"/applicationJavaGroupe6/fichiersCsv/";
        String f1 = "";
        String f2 = "";
        String f3 = "";
        String f4 = "";
        String f5 = "";
        if(!Fichiers.premierPassageEffectue)
        {
            File destinationPath = new File(System.getProperty("user.dir")+"/applicationJavaGroupe6/fichiersCsv/");
            destinationPath.mkdirs();
            InputStream source = null;
            try {
                source = getClass().getResource("/fichiersCsv/Etudiants.csv").openStream();
                File destination = new File(System.getProperty("user.dir")+"/applicationJavaGroupe6/fichiersCsv/Etudiants.csv");
                copier(source, destination);
                
                source = getClass().getResource("/fichiersCsv/Directeurs.csv").openStream();
                destination = new File(System.getProperty("user.dir")+"/applicationJavaGroupe6/fichiersCsv/Directeurs.csv");
                copier(source, destination);
                
                source = getClass().getResource("/fichiersCsv/Mentions.csv").openStream();
                destination = new File(System.getProperty("user.dir")+"/applicationJavaGroupe6/fichiersCsv/Mentions.csv");
                copier(source, destination);
                
                source = getClass().getResource("/fichiersCsv/Referents.csv").openStream();
                destination = new File(System.getProperty("user.dir")+"/applicationJavaGroupe6/fichiersCsv/Referents.csv");
                copier(source, destination);
                
                source = getClass().getResource("/fichiersCsv/UEs.csv").openStream();
                destination = new File(System.getProperty("user.dir")+"/applicationJavaGroupe6/fichiersCsv/UEs.csv");
                copier(source, destination);
                
            } catch (IOException ex) {
                ex.printStackTrace();
            } finally {
                try {
                    if(source!=null) source.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
        
        if (this.chemin == null){
            f1 = rep +"Mentions.csv"; 
            f2 = rep + "UEs.csv";
            f3 = rep + "Etudiants.csv";
            f4 = rep + "Directeurs.csv";
            f5 = rep + "Referents.csv"; 
            
        }else{
            f1 = this.chemin + "/Mentions.csv";
            f2 = this.chemin + "/UEs.csv";
            f3 = this.chemin + "/Etudiants.csv";
            f4 = this.chemin + "/Directeurs.csv";
            f5 = this.chemin + "/Referents.csv";
        } 
        
        //pour fichier Mentions
        this.recupererMentions(f1);
        
        //Pour les UEs...
        this.recupererUe(f2);
        
        //Les etudiants...
        this.recupererEtudiants(f3);
        
        //Les directeurs...
        this.recupererDirecteurs(f4);
        
        //Les referents...
        this.recupererReferents(f5);
    }

    /**
     * Cette méthode retranscrit les donnes des étudiants ajoutées sur le fichier CSV
     * @throws FileNotFoundException 
     */
    public void transcrireDonnes () throws FileNotFoundException{
        String f1;
        if( this.chemin == null){
             f1 = System.getProperty("user.dir")+"/applicationJavaGroupe6/fichiersCsv/Etudiants.csv";
        }
        else {
            f1 = this.chemin + "/Etudiants.csv";
        }
       
        PrintWriter writer = new PrintWriter(f1);
              
        for (int i=0; i< getEtudiants().size();i++){
            Etudiant etu = getEtudiants().get(i);
            writer.print(etu.getNumeroEt()+";");
            writer.print(etu.getPrenom()+";");
            writer.print(etu.getNom()+";");
            writer.print(etu.getCreditsAcquis()+";");
            writer.print(etu.getP().getNom()+";");
            writer.print(etu.getAnnee()+";");
            writer.print(etu.getSemestre()+";");

            //recuperer les valeurs des listes des ue 
            if (etu.getValidees().isEmpty()){
                writer.print("null;");
            }
            else{
                for(int j=0; j< etu.getValidees().size() - 1;j++){
                    writer.print(etu.getValidees().get(j).getUe().getCodeId()+",");
                }
                writer.print(etu.getValidees().get(etu.getValidees().size()-1).getUe().getCodeId()+";");
            }
            
            if (etu.getEchouees().isEmpty()){
                writer.print("null;");
            }
            else{
                for(int j=0; j< etu.getEchouees().size() - 1;j++){
                writer.print(etu.getEchouees().get(j).getUe().getCodeId()+",");
            }
            writer.print(etu.getEchouees().get(etu.getEchouees().size()-1).getUe().getCodeId()+";");
            }
            
            Iterator it = (Iterator) etu.getSuivies().values().iterator();
            
            if (!it.hasNext()){
                writer.print("null\n");
            }
            else{
                while(it.hasNext()){
                    Ues entree = (Ues) it.next();
                    if (!it.hasNext()){
                        writer.print(entree.getCodeId());
                        writer.print("\n");
                    }
                    else{
                        writer.print(entree.getCodeId()+",");
                    }
                }
                
            }
            
            
        
        }
        transcrirDonnesRef();
        writer.close();
    }
    
    /**
     * Cette méthode retranscrit les donnes des referents ajoutées sur le fichier CSV
     * @throws FileNotFoundException 
     */
    
    public void transcrirDonnesRef()
        throws FileNotFoundException{
        String f1;
        if( this.chemin == null){
             f1 = System.getProperty("user.dir")+"/applicationJavaGroupe6/fichiersCsv/Referents.csv";
        }
        else {
            f1 = this.chemin + "/Referents.csv";
        }
        PrintWriter writer = new PrintWriter(f1);
              
        for (int i=0; i< getReferents().size();i++){
            Referent r = getReferents().get(i);
            writer.print(r.getM().getNom()+";");
            writer.print(r.getNom()+";");
            writer.print(r.getPrenom()+";");
            writer.print(r.getNumRef()+";");
            
            //recuperer les valeurs des listes des etudiants
            if (getReferents().get(i).getEtudiants().isEmpty()){
                writer.print("null;");
            }
            else{
                for(int j=0; j< r.getEtudiants().size() - 1;j++){
                    writer.print(r.getEtudiants().get(j).getNumeroEt()+",");
                }
                writer.print(r.getEtudiants().get(r.getEtudiants().size()-1).getNumeroEt()+";");
            }
            writer.print("\n");
                 
        }
        writer.close();
    }
}

