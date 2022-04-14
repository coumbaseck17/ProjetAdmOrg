package fr.miage.toulouse.projetjavas6.etudiant;
import fr.miage.toulouse.projetjavas6.exceptions.CodeErroneException;
import fr.miage.toulouse.projetjavas6.exceptions.DepassementEctsException;
import fr.miage.toulouse.projetjavas6.exceptions.ParcoursNonSuiviException;
import fr.miage.toulouse.projetjavas6.exceptions.PrerequieException;
import fr.miage.toulouse.projetjavas6.exceptions.ValeurUniqueException;
import fr.miage.toulouse.projetjavas6.formation.*;
import fr.miage.toulouse.projetjavas6.postes.Referent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

/**
 *
 * @author thalya
 * Classe Etudiant
 */
public class Etudiant {
    /**
     * Attribut de classe permettant de garder en mémoire tous les étudiants.
     */
    private static ArrayList<Etudiant> etudiants = new ArrayList<>();
    
    private int numeroEt;
    private String prenom;
    private String nom;
    private int creditsAcquis;
    private int creditsPotentielSemestre;
    private Parcours p;
    private Referent r;
    private boolean derogation;
    
    private String annee;//année universitaire (ex : 2021/2022)
    private EtatSemestre semestre;//pair ou impair
    private HashSet<String> diplomes;

    
    private ArrayList<Echouees> echouees;
    private ArrayList<Validees> validees;
    
    private HashMap<Integer,Ues> suivies;
    
    public void setCreditsPotentielSemestre(int c){
        this.creditsPotentielSemestre = c;
    }
    public int getCreditsPotentielSemestre(){
        return this.creditsPotentielSemestre;
    }
    
    public void setDerogation(boolean der){
        this.derogation = der;
    }
    public boolean getDerogation(){
        return this.derogation;
    }
    public ArrayList<Echouees> getEchouees() {
        return new ArrayList<Echouees>(echouees);
    }

    public ArrayList<Validees> getValidees() {
        return new ArrayList<Validees>(validees);
    }

    public HashMap<Integer, Ues> getSuivies() {
        return new HashMap<Integer,Ues>(this.suivies);
    }
    public boolean ueValidee(Ues u){
        boolean trouve = false;
        int i =0;
        while((!trouve) && (i < this.validees.size())){
            if (this.validees.get(i).getUe() == u){
                trouve = true;
            }
            i++;
        }
        return trouve;
    }
    /**
     * Méthode de classe permettant d'accéder à la liste de tous les étudiants.
     */
    public static ArrayList<Etudiant> getEtudiants() {
        return new ArrayList<>(Etudiant.etudiants);
    }
    
    /**
     * Méthode statique getEtudiant qui permet de récupérer un étudiant selon son numéro d'étudiant
     * @param numeroEt
     * @return Etudiant : l'étudiant qui a pour numéro d'étudiant : numeroEt
     */
    public Etudiant getEtudiant(int numeroEt){
        boolean trouve = false;
        int i = 0;
        Etudiant etu = null;
        while ((i < Etudiant.etudiants.size()) && (!trouve)){
            if (Etudiant.etudiants.get(i).numeroEt == numeroEt){
                trouve = true;
                etu = Etudiant.etudiants.get(i);
            }
            i++;
        }
        return etu;
    }
    
    public String getPrenom() {
        return this.prenom;
    }
    public String getNom() {
        return this.nom;
    }
    
    public int getNumeroEt(){
        return this.numeroEt;
    }
    public Parcours getP(){
        return this.p;
    }
    
    public Referent getR(){
        return this.r;
    }
    
    public void setNom(String nom){
        this.nom = nom;
    }
    public void setPrenom(String prenom){
        this.prenom = prenom;
    }
    public void setP(Parcours p){
        this.p = p;
    }
    
    public void setR(Referent r){
        this.r=r;
    }
    public void setAnnee(String annee){
        this.annee= annee;
    }
    public void setSemestre(EtatSemestre semestre){
        this.semestre = semestre;
    }
     public int getCreditsAcquis() {
        return this.creditsAcquis;
    }
    public String getAnnee(){
        return this.annee;
    }
    public EtatSemestre getSemestre(){
        return this.semestre;
    }
    public HashSet<String> getDiplomes() {
        return new HashSet<String>(this.diplomes);
    }
    
    /**
     * Méthode getMention qui récupère la mention de l'étudiant
     * @return Mention : la mention à laquelle est inscrit l'étudiant
     */
    public Mention getMention(){
        Mention m= null;
        boolean trouve = false;
        int i = 0;
        Mention mTmp;
        while((!trouve)&&(i < Mention.getMentions().size())){
            mTmp = Mention.getMentions().get(i);
            if (mTmp.getParcours().contains(this.p)){
                trouve = true;
                m = mTmp;
            }
            i++;
        }
        return m;
    }
    /**
     * Méthode static qui vérifie si le numéro donné en parmaètre correspond à un étudiant
     * @param numeroEt : Numéro étudiant que l'on souhaite vérifier
     * @return trouve : booléen qui informe si la mention existe ou non 
     */
    public static boolean etudiantExisteDeja(int numeroEt){
        boolean trouve = false;
        int i = 0;
        while ((i < Etudiant.etudiants.size()) && (!trouve)){
            if (Etudiant.etudiants.get(i).numeroEt == numeroEt){
                trouve = true;
            }
            i++;
        }
        return trouve;
    }
    
    /**
     * Constructeur permettant de créer un étudiant sans lui préciser ses diplomes
     * @param prenom
     * @param nom
     * @param credits
     * @param p : parcours auquel est inscrit l'étudiant
     * @param annee
     * @param semestre
     * @param numeroEt
     * @throws fr.miage.toulouse.projetjavas6.exceptions.ValeurUniqueException : Si l'étudiant que l'on souhaite créer existe déjà
    */
    public Etudiant(int numeroEt, String prenom, String nom, int credits,Parcours p, String annee, EtatSemestre semestre) throws ValeurUniqueException {
        this.prenom = prenom;
        this.nom = nom;
        this.creditsAcquis = credits;
        this.annee = annee;
        this.semestre = semestre;
        this.diplomes = new HashSet<>();
        this.p = p;
        this.creditsPotentielSemestre = 0;
        this.derogation = false;
        this.echouees = new ArrayList<>();
        this.validees = new ArrayList<>();
        
        this.suivies = new HashMap<>();
        this.numeroEt = numeroEt;
        this.r = null;
        if (!etudiantExisteDeja(numeroEt)){
           Etudiant.etudiants.add(this); 
        }
        else{
            throw new ValeurUniqueException("Etudiant existe déjà");
        }
    }
    /**
     * Constructeur permettant de créer un étudiant en lui précisant ses diplomes
     * @param numeroEt
     * @param prenom
     * @param nom
     * @param credits
     * @param p
     * @param annee
     * @param semestre
     * @param dip
     * @throws fr.miage.toulouse.projetjavas6.exceptions.ValeurUniqueException : Si l'étudiant que l'on souhaite créer existe déjà
    */
    public Etudiant(int numeroEt, String prenom, String nom, int credits,Parcours p, String annee, EtatSemestre semestre, ArrayList<String> dip) throws ValeurUniqueException {
        this.prenom = prenom;
        this.nom = nom;
        this.creditsAcquis = credits;
        this.p = p;
        this.annee = annee;
        this.semestre = semestre;
        this.diplomes = new HashSet<>(dip);
        this.creditsPotentielSemestre = 0;
        this.derogation = false;
        
        this.echouees = new ArrayList<>();
        this.validees = new ArrayList<>();
        
        this.suivies = new HashMap<>();
        
        this.numeroEt = numeroEt;
        this.r = null;
        if (!etudiantExisteDeja(numeroEt)){
           Etudiant.etudiants.add(this); 
        }
        else{
            throw new ValeurUniqueException("Etudiant existe déjà");
        }
    }
    /**
     * Méthode recevoirDiplome permettant d'ajouter le diplome à un étudiant
     * @param dip : diplome en question
     * @param p : parcours qui délivre le diplome
     * @throws fr.miage.toulouse.projetjavas6.exceptions.ParcoursNonSuiviException : Si le parcours que l'on souhaite valider n'est pas suivi par l'étudiant*/
    public void recevoirDiplome(String dip, Parcours p) throws ParcoursNonSuiviException {
        if (this.p != p){
            throw new ParcoursNonSuiviException("L'étudiant "+this.nom+" "+this.prenom+" ne suit pas le parcours "+p.toString());
        }
        this.diplomes.add(dip);
    }
    /**
     * Méthode inscrire qui inscrit l'étudiant à une UE
     * @param ue : l'ue en question
     * @throws fr.miage.toulouse.projetjavas6.exceptions.DepassementEctsException : Si on essaye d'inscrire l'étudiant à une Ue alors que l'étudiant a déjà tous les crédit possible pour le semestre en cours
     * @throws fr.miage.toulouse.projetjavas6.exceptions.PrerequieException : Si on essaye d'inscrire un étudiant à une ue pour laquelle il n'a pas les prérequies.
     */
    public void inscrire(Ues ue) throws DepassementEctsException, PrerequieException{
        //intialisation du nombre de crédit maximum autorisé pour ce semestre
        int creditsMax = this.derogation ? 39 : 30;
        //Si on peut ajouter cette Ue sans dépasser le nombre de créditsMax du semestre
        if (this.creditsPotentielSemestre + ue.getCredits() <= creditsMax ){
            boolean prerequiesValides = true;
            String pasValides = "";
            for(Ues u :  ue.getPrerequies()){
                boolean trouve = false;
                int i = 0;
                while ((!trouve) && ( i< this.validees.size())){
                    if (this.validees.get(i).getUe() == u){
                        trouve = true;
                    }
                    i++;
                }
                if(!trouve){
                    prerequiesValides = false;
                    pasValides += "["+ u.getNom() + " "+u.getCodeId()+"]";
                }
            }
            if (prerequiesValides){
                this.suivies.put(ue.getCodeId(), ue);
                this.creditsPotentielSemestre += ue.getCredits();
            }
            else {
                throw new PrerequieException("On ne peut pas inscire l'étudiant "+this.nom+" "+ this.getNumeroEt()+" à "+ue.getNom()+" "+ ue.getCodeId()+" car il n'a pas validé "+pasValides);

            }
            
        }
        else {
            throw new DepassementEctsException("On ne peut pas inscire l'étudiant"+this.nom+ " "+ this.getNumeroEt()+" car il a déjà "+ this.creditsPotentielSemestre +" crédits ce semestre");
        }
    }
    /**
     * Méthode validerUe permettant de valider une UE à l'étudiant à partir du code de l'ue
     * @param codeUe : code de l'ue à valider
     * @throws CodeErroneException : si le codeUe ne correspond à aucune UE suivie
    */
    public void validerUe(int codeUe) throws CodeErroneException{
        Ues u = this.suivies.get(codeUe);
        this.suivies.remove(codeUe);
        if (u != null){
            this.validees.add(new Validees(this.annee, this.semestre, u, this));
            this.creditsAcquis= this.creditsAcquis + u.getCredits();
        }
        else{
            throw new CodeErroneException("Ue "+codeUe+" n'est pas suivie actuellement par l'étudiant "+ this.nom);
        }
    }
    /**
     * Méthode echouerUe permettant de valider une UE à l'étudiant à partir du code de l'ue
     * @param codeUe : code de l'ue à valider
     * @throws CodeErroneException : si le codeUe ne correspond à aucune UE suivie
    */
    public void echouerUe(int codeUe) throws CodeErroneException{
        Ues u = this.suivies.get(codeUe);
        this.suivies.remove(codeUe);
        if (u != null){
            this.echouees.add(new Echouees(this.annee, this.semestre, u, this));
        }
        else{
            throw new CodeErroneException("Ue n'est pas suivie actuellement");
        }
    }
   
    
    /**
     * Méthode privée incrementerAnnee qui permet d'incrémenter l'année universitaire en cours .
     */
    
    private void incrementerAnnee(){
        String [] annees = this.annee.split("/");
        //On récupérer la première partie de la chaine "annee" (avant le caractère '/'), qu'on converti en Integer puis que l'on incrémente de 1.
        int annee1 = Integer.parseInt(annees[0]) + 1;
        //On récupérer la deuxième partie de la chaine "annee" (avant le caractère '/'), qu'on converti en Integer puis que l'on incrémente de 1.
        int annee2 = Integer.parseInt(annees[1]) + 1;
        //On reconcatène ces deux résultats
        this.annee = Integer.toString(annee1) +"/"+ Integer.toString(annee2);
    }
    
    /**
     * Méthode passerAuSemestre qui permet d'échouer les UE qui n'ont pas été validé (d'un coup) et d'incrémenter le semestre et/ou l'année en cours de l'étudiant.
     */
    public void passerAuSemestre(){
        //S'il reste des ues dont on n'a pas précisé le statut (que l'on a ni validé, ni échoué)
        if (!this.suivies.isEmpty()){
            //Pour chacune de ces Ues, on récupère leur code 
            for(int code : this.suivies.keySet()){
                //On récupère l'ue en question
                Ues u = this.suivies.get(code);
                //On échoue l'ue à ce semestre (avant incrémentation du semestre et de l'année)
                this.echouees.add(new Echouees(this.annee, this.semestre, u, this));
                //On supprimer l'instance de suivies concernant cette ue
                this.suivies.remove(code);
            }
        }
        //On modifie le semetre
        this.semestre = this.semestre == EtatSemestre.PAIR ? EtatSemestre.IMPAIR : EtatSemestre.PAIR;
        //On remet les crédits potentiels du semestre à 0
        this.creditsPotentielSemestre = 0;
        //On repasse la dérogation à faux
        this.derogation = false;
    }
}
